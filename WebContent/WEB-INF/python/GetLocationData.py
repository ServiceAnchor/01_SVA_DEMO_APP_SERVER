import sys
import os
import ssl
import time
import json
import MySQLdb
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
    companyid = ""
    db = ""
    log = object


    def __init__(self,appName,brokeIP,brokerPort,queueID,companyID,LOG,dbName):
        self.appname = appName
        self.brokeip = brokeIP
        self.brokerport = brokerPort
        self.queueid = queueID
        self.companyid = companyID
        self.db = dbName
        self.log = LOG

    def CheckSvaError(self):
        if(self.isError == True):
            return True
        messageTime = self.nowTime
        nowTime = datetime.now()
        if((nowTime - messageTime).seconds > 60 * 5):
           return True 
        return False 
     
    def Run(self):
        broker = "{}/xxxx@{}:{}".format(self.appname, self.brokeip, self.brokerport)
        address = "{}".format(self.queueid)
        conn_options = {
                    'transport'               : 'ssl',
                    'ssl_keyfile'             : "ssl_cert_file/MSP.Key.pem",
                    'ssl_certfile'            : "ssl_cert_file/MSP.pem.cer",
                    'ssl_trustfile'           : "ssl_cert_file/Wireless Root CA.pem.cer",
                    'ssl_skip_hostname_check' : True,
                    }
        connection = Connection(broker, **conn_options)
        ssl._create_default_https_context = ssl._create_unverified_context
        context = ssl._create_unverified_context()
        try:
            connection.open()
            session = connection.session()
            receiver = session.receiver(address)

            LOG.info("SsessionCreateSuccess")   
            while True:
                LOG.info("GetOne  Begin")
                message = receiver.fetch()
                LOG.info("GetOne  End")
                #{"locationstream":[{"IdType":"IP","Timestamp":1427560872000,"datatype":"coordinates","location":{"x":1133.0,"y":492.0,"z":1},"userid":["0a86e48a"]}]}
                try:
                    LOG.info("Receive: {}".format(message.content))                       
                    jsonData = json.loads(message.content)
                    conn=MySQLdb.connect(host='127.0.0.1',user='root',passwd='123456',port=3306)
                    cursor = conn.cursor()   
                    conn.select_db(self.db)
                    if jsonData.keys()[0] == 'locationstream':
                        jsonList = jsonData["locationstream"]
                        for index in range(len(jsonList)):                            
                            IdType = jsonList[index]["IdType"]
                            Timestamp = jsonList[index]["Timestamp"]
                            datatype = jsonList[index]["datatype"]
                            x = jsonList[index]["location"]["x"]
                            y = jsonList[index]["location"]["y"]
                            z = jsonList[index]["location"]["z"]
                            if z > 0:
                                z = z + int(self.companyid)*10000
                            else:
                                z = abs(z) + 5000 + int(self.companyid)*10000
                            if len(jsonList[index]["userid"]) < 1:
                                continue
                            userid = jsonList[index]["userid"][0]   
                            sqlparam = [IdType,Timestamp,datatype,x,y,z,userid]                       
                            LOG.info(sqlparam)
                            time_begin = Timestamp       
                            loc_count= 1       
                            during = 0      
                            time_local = time.time() * 1000
                            cursor.execute ("select loc_count, time_begin,timestamp,userid  from locationPhone where userid=%s",[userid])  
                            row = cursor.fetchone ()
                            if row != None:
                                sqlparam = [IdType,Timestamp,time_local,datatype,x,y,z,userid] 
                                cursor.execute("update locationphone set IdType=%s, Timestamp = %s,time_local= %s,datatype= %s,x=%s, y =%s, z = %s where userid = %s",sqlparam)
                            else:
                                sqlparam = [IdType,Timestamp,time_begin,time_local,loc_count,during,datatype,x,y,z,userid] 
                                cursor.execute("replace into locationPhone (IdType,Timestamp,time_begin,time_local,loc_count,during,datatype,x,y,z,userid) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",sqlparam)
                            #cursor.execute("replace into locationPhone (IdType,Timestamp,datatype,x,y,z,userid) values (%s,%s,%s,%s,%s,%s,%s)",sqlparam)
                    if jsonData.keys()[0] == 'locationstreamanonymous':
                        jsonList = jsonData["locationstreamanonymous"]
                        dataStr = ""
                        for index in range(len(jsonList)):                            
                            IdType = jsonList[index]["IdType"]
                            Timestamp = jsonList[index]["Timestamp"]
                            datatype = jsonList[index]["datatype"]
                            x = jsonList[index]["location"]["x"]
                            y = jsonList[index]["location"]["y"]
                            z = jsonList[index]["location"]["z"]
                            if z > 0:
                                z = z + int(self.companyid)*10000
                            else:
                                z = abs(z) + 5000 + int(self.companyid)*10000
                            if len(jsonList[index]["userid"]) < 1:
                                continue
                            userid = jsonList[index]["userid"][0]                      
                            sqlparam = [IdType,Timestamp,datatype,x,y,z,userid]
                            if dataStr == "":
                                ltime=time.localtime(Timestamp/1000)
                                dataStr=time.strftime("%Y%m%d", ltime)
                            LOG.info(sqlparam)
                            time_begin = Timestamp       
                            loc_count= 1       
                            during = 0      
                            time_local = time.time() * 1000
                            cursor.execute ("select loc_count, time_begin,timestamp,userid  from location"+dataStr+" where userid=%s and z = %s",[userid,z])  
                            row = cursor.fetchone ()
                            if row != None:
                                loc_count = loc_count + int(row[0])
                                during = Timestamp - int(row[1]);
                                sqlparam = [IdType,Timestamp,time_local,loc_count,during,datatype,x,y,z,userid]
                                print sqlparam
                                cursor.execute("update location"+dataStr+" set IdType=%s, Timestamp = %s,time_local=%s,loc_count=%s, during=%s,datatype=%s,x=%s, y =%s where z = %s and userid = %s ",sqlparam)
                            else:
                                sqlparam = [IdType,Timestamp,time_begin,time_local,loc_count,during,datatype,x,y,z,userid] 
                                cursor.execute("insert into location"+dataStr+" (IdType,Timestamp,time_begin,time_local,loc_count,during,datatype,x,y,z,userid) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",sqlparam)
                            #cursor.execute("insert into location"+dataStr+" (IdType,Timestamp,datatype,x,y,z,userid) values (%s,%s,%s,%s,%s,%s,%s)",sqlparam)
                    if jsonData.keys()[0] == 'networkinfo':
                        jsonList = jsonData["networkinfo"][0]["lampsiteinfo"]["prrusignal"]
                        for index in range(len(jsonList)):                            
                            gpp = jsonList[index]["gpp"]
                            rsrp = jsonList[index]["rsrp"]                   
                            sqlparam = [gpp,self.brokeip,rsrp]                       
                            LOG.info(sqlparam)
                            cursor.execute("replace into prrusignal (gpp,svaIp,rsrp) values (%s,%s,%s)",sqlparam)
                    if jsonData.keys()[0] == 'geofencing':
                        jsonList = jsonData["geofencing"]
                        for index in range(len(jsonList)):                            
                            IdType = jsonList[index]["IdType"]
                            userid = jsonList[index]["userid"] 
                            mapid = jsonList[index]["mapid"]    
                            zoneid = jsonList[index]["zoneid"] 
                            zone_event = jsonList[index]["enter"]
                            Timestamp = jsonList[index]["Timestamp"]
                            time_local = time.time() * 1000
                            sqlparam = [IdType,userid,mapid,zoneid,zone_event,Timestamp,time_local]                       
                            LOG.info(sqlparam)
                            cursor.execute("into geofencing (IdType,userid,mapid,zoneid,enter,Timestamp,time_local) values (%s,%s,%s,%s,%s,%s,%s)",sqlparam)
                    conn.commit() 
                    self.nowTime = datetime.now()
                    cursor.close()  
                    conn.close()     
                except Exception as e:  
                    LOG.info("MessagingError:" + e.message)
                    LOG.info("MessagingError:" + e)
                    
                session.acknowledge()

        except Exception as m: 
             LOG.info("Exception" + m.message)
             self.isError = True
             connection.close()
             #sys.exit(-1)


if __name__ == "__main__":
    #appName = "app0"
    #brokeIP = "182.138.104.35"
    #brokerPort = "4703"
    #queueID = "app0.7ce75a30c6184ef08b20994bdcb53dcb.66fc8841"
    #companyID = "861300010010300005"
    appName = sys.argv[1] #app0
    brokeIP = sys.argv[2] #182.138.104.35
    brokerPort = sys.argv[3] #4703
    queueID = sys.argv[4] #app0.7ce75a30c6184ef08b20994bdcb53dcb.66fc8841
    companyID = sys.argv[5] #861300010010300005
    dbName = sys.argv[6] #sva
    if(companyID == '861300010160500001'):
       brokeIP='182.138.104.35'   

    logDir = sys.path[0]
    if(not os.path.exists(logDir + "/Log")):
        os.makedirs(logDir + "/Log") 
    LOGFILE = "Log/" + appName +"_"+ companyID + "_logging.log"
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
    
    getSvaData = GetSvaData(appName,brokeIP,brokerPort,queueID,companyID,LOG,dbName)
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

 