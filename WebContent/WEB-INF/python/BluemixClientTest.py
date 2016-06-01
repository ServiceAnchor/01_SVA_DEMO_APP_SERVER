#!/usr/bin/env python
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#

import time
import requests
import json
import sys
from qpid.messaging import *

happy = 0
sad = 0

ids = {}


def send(payload):
    r = requests.post('https://presenceinsights.ng.bluemix.net/conn-huawei-smallcell/v1/tenants/mk3l078f/orgs/l61b0ipq', auth=('1a1d0zn2','aXpgYAnl5hLJ'), headers = { 'content-type': 'application/json' }, data = payload)
    global happy
    global sad
    if (r.status_code == 204):
        happy = happy + 1
    else:
        print "sad %d : %r" % (r.status_code,payload)
        sad = sad + 1
    total = happy + sad
    if ((total % 10) == 0):
        print "%s unique:%d total:%d happy:%d sad:%d" % (time.strftime("%Y:%m:%d %H:%M:%S"),len(ids),total,happy,sad)
    if ((total % 99) == 0):
        print payload
        print r.content
    sys.stdout.flush()

if __name__ == "__main__":
    if len(sys.argv) < 5:
        sys.exit(-1)
    print 'app name {}, broke ip {}, broker port {}, queue id {}'.format(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4])
    sys.stdout.flush()
    #broker = "{}/xxxx@{}:{}".format(sys.argv[1], sys.argv[2], sys.argv[3])
    broker = "{}:{}".format(sys.argv[2], sys.argv[3])
    #broker = "{}/User%40123456@{}:{}".format(sys.argv[1], sys.argv[2], sys.argv[3])
    print broker
    sys.stdout.flush()
    address = "{}".format(sys.argv[4])
    print address
    sys.stdout.flush()
    conn_options = {
                    'username'                : 'app7',
                    'password'                : 'User@123456',
                    'transport'               : 'ssl',
                    'ssl_keyfile'             : "ssl_cert_file/MSP.Key.pem",
                    'ssl_certfile'            : "ssl_cert_file/MSP.pem.cer",
                    'ssl_trustfile'           : "ssl_cert_file/Wireless Root CA.pem.cer",
                    'ssl_skip_hostname_check' : True,
                    }
    connection = Connection(broker, **conn_options)

    try:
        #print "sending sample"
        #sys.stdout.flush()
        #send('{ "site": "3x6s0r8l", "locationstream":[ { "IdType":"IP", "Timestamp":1432108029000, "datatype":"coordinates", "location":{"x":448.0,"y":181.0,"z":1}, "userid":["0a8340e5"] } ] }')
        print "open"
        sys.stdout.flush()
        connection.open()
        print "open done"
        sys.stdout.flush()
        session = connection.session()
        print "has a session"
        sys.stdout.flush()
        receiver = session.receiver(address)
        print "session create success"
        sys.stdout.flush()
        while True:
            message = str(receiver.fetch().content)
            #print type(message)
            #print message
            #sys.stdout.flush()
            session.acknowledge()
            asJson = json.loads(message)
            asJson["site"] = "227w01b0"
            ip = asJson["locationstream"][0]['userid'][0]
            if (ip in ids):
                ids[ip] = ids[ip]+1
            else:
                print "new ip %s" % ip
                ids[ip] = 1
            asStr = json.dumps(asJson)
            #print "ZZZ %r" % asStr
            send(asStr)

    except MessagingError, m:
        print "MessagingError", m
        sys.stdout.flush()
    connection.close()
