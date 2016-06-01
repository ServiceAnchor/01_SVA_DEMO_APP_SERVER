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
import os
import json
import ssl
import sys
from datetime import datetime 
from qpid.messaging import *
import socket
import logging
from logging.handlers import RotatingFileHandler
import threading

class GetSvaData():
    isError = False
    nowTime = datetime.now()
    appname = ""
    brokeip = ""
    brokerport = ""
    queueid = ""
    url = ""
    site = ""
    IbmUser = ""
    IbmPassword = ""
    companyid = ""
    log = object


    def __init__(self,appName,brokeIP,brokerPort,queueID,Url,Site,ibmUser,ibmPassword,companyID,LOG):
        self.appname = appName
        self.brokeip = brokeIP
        self.brokerport = brokerPort
        self.queueid = queueID
        self.url = Url
        self.site = Site
        self.IbmUser = ibmUser
        self.IbmPassword = ibmPassword
        self.companyid = companyID
        self.log = LOG

    def CheckSvaError(self):
        if(self.isError == True):
            return True
        messageTime = self.nowTime
        nowTime = datetime.now()
        if((nowTime - messageTime).seconds > 60 * 5):
           return True 
        return False 
    
    def send(self,payload):
        r = requests.post(self.url, auth=(self.IbmUser,self.IbmPassword), headers = { 'content-type': 'application/json' }, data = payload)  
        LOG.info("requests.post payload:"+str(payload))
        LOG.info("requests.post status_code:"+str(r.status_code))
        sys.stdout.flush()
     
    def Run(self):
        broker = "{}/xxxx@{}:{}".format(self.appname, self.brokeip, self.brokerport)
        LOG.info("broker {}".format(broker))
        address = "{}".format(self.queueid)
        conn_options = {
                    'transport'               : 'ssl',
                    'ssl_keyfile'             : "ssl_cert_file/MSP.Key.pem",
                    'ssl_certfile'            : "ssl_cert_file/MSP.pem.cer",
                    'ssl_trustfile'           : "ssl_cert_file/Wireless Root CA.pem.cer",
                    'ssl_skip_hostname_check' : True,
                    }
        connection = Connection(broker, **conn_options)
        LOG.info("Connection")
        ssl._create_default_https_context = ssl._create_unverified_context
        context = ssl._create_unverified_context()
        try:
            connection.open()
            session = connection.session()
            receiver = session.receiver(address)
            receiver._set_capacity(50);

            LOG.info("SsessionCreateSuccess")   
            while True:
                message = str(receiver.fetch().content)
                #print type(message)
                #print message
                #sys.stdout.flush()
                session.acknowledge()
                jsonData = json.loads(message)
                asJson = {}
                asJson["site"] = self.site
                if jsonData.keys()[0] == 'locationstream':
                    asJson["locationstream"] = jsonData["locationstream"]
                if jsonData.keys()[0] == 'locationstreamanonymous':
                    asJson["locationstream"] = jsonData["locationstreamanonymous"]
                asStr = json.dumps(asJson)
                asStr = asStr.replace("user id","IP")
                self.send(asStr)

        except Exception as m: 
             LOG.info("Exception" + m.message)
             self.isError = True
             connection.close()
             #sys.exit(-1)

if __name__ == "__main__":
    #appid + IP + port + queue + url + Site + IbmUser + IbmPassword + appid
    if len(sys.argv) < 9:
        sys.exit(-1)
    print 'app name {}, broke ip {}, broker port {}, queue id {}, url {}, Site {}, IbmUser{}, IbmPassword {}, '.format(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6], sys.argv[7], sys.argv[8])
    appName = sys.argv[1] #app0
    brokeIP = sys.argv[2] #182.138.104.35
    brokerPort = sys.argv[3] #4703
    queueID = sys.argv[4] #app0.7ce75a30c6184ef08b20994bdcb53dcb.66fc8841
    url = sys.argv[5] #http://www.baidu.com
    site = sys.argv[6] #sd458rgd
    IbmUser = sys.argv[7] #strd854
    IbmPassword = sys.argv[8] #stddf25
    companyID = sys.argv[9]
    
    logDir = sys.path[0]
    if(not os.path.exists(logDir + "/Log")):
        os.makedirs(logDir + "/Log") 
    LOGFILE = "Log/" + companyID  + "_logging.log"
    MAXLOGSIZE = 10 * 1024 * 1024 #Bytes
    BACKUPCOUNT = 10
    FORMAT = "%(asctime)s %(message)s"
    LOGLVL = logging.NOTSET # CRITICAL 50 ERROR 40 WARNING 30 INFO 20 DEBUG 10 NOTSET 0

    handler = RotatingFileHandler(LOGFILE,
                                  mode='a',
                                  maxBytes=MAXLOGSIZE,
                                  backupCount=BACKUPCOUNT)
    formatter = logging.Formatter(FORMAT)
    handler.setFormatter(formatter)
    LOG = logging.getLogger()
    LOG.setLevel(LOGLVL)
    LOG.addHandler(handler)
    
    getSvaData = GetSvaData(appName,brokeIP,brokerPort,queueID,url,site,IbmUser,IbmPassword,companyID,LOG)
    try:
        thread1 = threading.Thread(target=getSvaData.Run)
        thread1.setDaemon(True) 
        thread1.start()
    except Exception as e:
        LOG.info("getSvaData.Run Error")
        sys.exit(-1)

    while True:
        try:
            time.sleep(200)
            if(getSvaData.CheckSvaError()):
                LOG.info("getSvaData TimeOut")
                sys.exit(-1) 
        except Exception as e:  
            sys.exit(-1)
