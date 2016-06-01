package com.sva.common;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.sva.common.conf.Params;
import com.sva.dao.AreaDao;
import com.sva.dao.CommonDao;
import com.sva.dao.LinemapDao;
import com.sva.dao.MapsDao;
import com.sva.dao.MessageDao;
import com.sva.dao.StoreDao;
import com.sva.model.AreaModel;
import com.sva.model.MessageModel;
import com.sva.model.StoreModel;

public class QuartzJob
{

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private CommonDao dao;

    @Autowired
    private MapsDao daoMaps;

    @Autowired
    private MessageDao daoMsg;

    @Autowired
    private AreaDao daoArea;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private LinemapDao linemapDao;

    @Value("${mysql.db}")
    private String db;

    private static Logger log = Logger.getLogger(QuartzJob.class);

    public void doCreateTable()
    {
        // 今日表
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        // 明日表
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String tableNameTommorrow = Params.LOCATION + time;
        log.info(db + ':' + tableName);
        String sqlDelete = "delete from allpeople";
        String sqlDelete1 = "delete from nowpeople";
        try
        {
            dao.doUpdate(sqlDelete);
            dao.doUpdate(sqlDelete1);
            // 如果今日表不存在则创建
            if (dao.isTableExist(tableName, db) < 1)
            {
                Logger.getLogger(QuartzJob.class).info("create");
                // 动态创建表
                dao.createTable(tableName);
            }
            // 如果明日表不存在则创建
            if (dao.isTableExist(tableNameTommorrow, db) < 1)
            {
                Logger.getLogger(QuartzJob.class).info("create");
                // 动态创建表
                dao.createTable(tableNameTommorrow);
            }
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }

    }

    public void doStatisticData()
    {
        long startTime = System.currentTimeMillis() - 3600000;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + time;
        String sqlTest = "select count(*) from statisticday where time = '"
                + time + '\'';
        String sqlHour = "insert into statistichour "
                + "(SELECT b.placeId placeId,FROM_UNIXTIME(a.timestamp/1000,'%Y%m%d%H0000') time,COUNT(distinct a.userID) number "
                + "FROM " + tableName
                + " a join maps b on a.z = b.floorNo and a.timestamp> "
                + startTime + " GROUP BY b.placeId,time)";

        String sqlDay = "replace into statisticday "
                + "(SELECT b.placeId placeId,"
                + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD)
                + " time,COUNT(distinct a.userID) number " + "FROM "
                + tableName
                + " a join maps b on a.z = b.floorNo GROUP BY b.placeId)";

        String sqlFloor = "replace into statisticfloor "
                + "(SELECT userID, FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H0000') time ,z "
                + "FROM " + tableName + " group by userID,time,z) ";
        try
        {
            int result = dao.doTest(sqlTest);
            log.info(result);
            if (result == 0)
            {
                dao.doUpdate(sqlDay);
                dao.doUpdate(sqlHour);
                dao.doUpdate(sqlFloor);
            }
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }

    }

    public void doDeleteInfo()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.MONTH, -6);
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        List<String> list = dao.doDeleteInfo("location" + sdf.format(date));
        int si = list.size();
        if (si > 0)
        {
            for (int i = 0; i < si; i++)
            {
                dao.deleteTable(list.get(i));
            }
        }
    }

    public void doStatisticOneHour()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        String time = ConvertUtil
                .dateFormat(cal.getTime(), Params.YYYYMMDDHHMM) + "00";
        log.info(time);
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String sqlInsert = "insert into locationtemp(x,y,z,idType,dataType,timestamp,userID)"
                + "select a.x,a.y,a.z,a.idType,a.dataType,a.timestamp,a.userID from "
                + "(SELECT x,y,z,idType,dataType,timestamp,userID,FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H%i00') time "
                + "FROM "
                + tableName
                + " where FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H%i00') = "
                + time + " GROUP BY userID,time) a;";

        cal.add(Calendar.HOUR, -1);
        time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDDHHMM)
                + "00";
        log.info(time);
        log.info(sqlInsert);
        String sqlDelete = "delete from locationtemp "
                + "where FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H%i00') < "
                + time;

        try
        {
            dao.doUpdate(sqlInsert);
            dao.doUpdate(sqlDelete);
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }

    }

    public void doStatisticDataPerHalfHour()
    {
        // 获取一个小时之前的时间戳
        long startTime = System.currentTimeMillis() - 3600000;
        Calendar cal = Calendar.getInstance();
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String tableName = Params.LOCATION + time;
        log.info("doStatisticDataPerHalfHour:"
                + ConvertUtil.dateFormat(cal.getTime(), "yyyyMMddHHmmSS"));
        String sqlHour = "insert into statistichour "
                + "(SELECT b.placeId placeId,FROM_UNIXTIME(a.timestamp/1000,'%Y%m%d%H0000') time,COUNT(distinct a.userID) number "
                + "FROM " + tableName
                + " a join maps b on a.z = b.floorNo and a.timestamp> "
                + startTime + " GROUP BY b.placeId,time)";

        String sqlDay = "replace into statisticday "
                + "(SELECT b.placeId placeId,"
                + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD)
                + " time,COUNT(distinct a.userID) number " + "FROM "
                + tableName
                + " a join maps b on a.z = b.floorNo GROUP BY b.placeId)";

        String sqlFloor = "replace into statisticfloor "
                + "(SELECT userID, FROM_UNIXTIME(timestamp/1000,'%Y%m%d%H0000') time ,z "
                + "FROM " + tableName + " group by userID,time,z) ";

        try
        {
            dao.doUpdate(sqlDay);
            dao.doUpdate(sqlHour);
            dao.doUpdate(sqlFloor);
            // refreshRangeStat();
            // addLineStat();
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }

    }

    public void refreshRangeStat()
    {
        String sqlDelete = "delete from statistictemp";
        String sqlInsert = "";
        String areaName = "";
        String areaNumber = "";
        Collection<MessageModel> ResultList = daoMsg.doquery();
        try
        {
            dao.doUpdate(sqlDelete);
            for (MessageModel l : ResultList)
            {
                areaName = l.getShopName();
                areaNumber = String.valueOf(daoMsg.getNumberByArea(l));
                sqlInsert = "insert into statistictemp(name,number) values ('"
                        + areaName + "'," + areaNumber + ')';
                dao.doUpdate(sqlInsert);
            }
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }
    }

    public void addLineStat()
    {
        long time = System.currentTimeMillis() - 30 * 60 * 1000;
        Calendar cal = Calendar.getInstance();
        String time2 = ConvertUtil.dateFormat(cal.getTime(),
                Params.YYYYMMDDHHMM) + "00";
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        String sqlInsert = "insert into statisticlinetemp(number,time,placeId) "
                + "(select count(distinct a.userID) number,"
                + time2
                + " time,b.placeId from "
                + tableName
                + " a join maps b on a.z = b.floorNo where timestamp > "
                + time
                + " group by b.placeId)";
        log.info("addLineStat:" + sqlInsert);
        try
        {
            dao.doUpdate(sqlInsert);
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }
    }

    public void doStatisticArea()
    {
//        long nowTime = System.currentTimeMillis() + 10000;
        // 一小时间之前的时间戳
        long startTime = System.currentTimeMillis() - 3600000;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        String time = ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDDHH)
                + "0000";
//        String tableName = Params.LOCATION
//                + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        log.info("doStatisticArea:"
                + ConvertUtil.dateFormat(cal.getTime(), "yyyyMMddHHmmSS"));
        String sqlInsert = "";
        String areaName = "";
        int areaNumber = 0;
        Collection<AreaModel> ResultList = daoArea.doquery();
        try
        {
            for (AreaModel l : ResultList)
            {
                areaName = l.getId();
                areaNumber = linemapDao
                        .getAreaNumberByHour(areaName, startTime);
//                areaNumber = (daoArea.getNumberByArea(l, nowTime, startTime,
//                        tableName));
                sqlInsert = "insert into statisticarea(time,areaId,number) values ('"
                        + time + "','" + areaName + "'," + areaNumber + ')';
                dao.doUpdate(sqlInsert);
            }
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }
    }

    public void doStatisticAreaByDay()
    {
        Calendar currentDate1 = new GregorianCalendar();
        currentDate1.set(Calendar.HOUR_OF_DAY, 0);
        currentDate1.set(Calendar.MINUTE, 0);
        currentDate1.set(Calendar.SECOND, 0);
        // String time = ConvertUtil.dateFormat(currentDate1.getTime(),
        // Params.YYYYMMDDHH) + "0000";
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, -24);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        // currentDate.set(Calendar.HOUR_OF_DAY,-2 );
        // currentDate.set(Calendar.HOUR, 0);
        // currentDate.set(Calendar.MINUTE, 0);
        String time2 = ConvertUtil.dateFormat(currentDate.getTime(),
                Params.YYYYMMDDHH) + "0000";
        // String tableName = Params.LOCATION
        // + ConvertUtil
        // .dateFormat(currentDate.getTime(), Params.YYYYMMDD);
        String sqlInsert = "";
        String areaName = "";
        String areaNumber = "";
        Collection<AreaModel> ResultList = daoArea.doquery();
        try
        {
            for (AreaModel l : ResultList)
            {
                areaName = l.getId();
                areaNumber = String.valueOf(linemapDao
                        .getAreaNumberByDay(areaName));
                // areaNumber = String.valueOf(daoArea.getNumberByAreaDay(l,
                // time,
                // time2, tableName));
                sqlInsert = "insert into statisticareaDay(time,areaId,number) values ('"
                        + time2 + "','" + areaName + "'," + areaNumber + ')';
                dao.doUpdate(sqlInsert);
            }
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }
    }

    public void doStatisticVisitTime()
    {
        Collection<AreaModel> ResultList = daoArea.doquery();
        // 获取当前时间前半个小时的时间戳
        long time = System.currentTimeMillis() - 1800000;

        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        String tableName = Params.LOCATION
                + ConvertUtil
                        .dateFormat(currentDate.getTime(), Params.YYYYMMDD);
        String visitDay = ConvertUtil.dateFormat(currentDate.getTime(),
                "yyyy-MM-dd");

        String areaId = null;

        InputStream in = getClass().getClassLoader().getResourceAsStream(
                "db.properties");
        Properties properties = new Properties();
        try
        {
            properties.load(in);
        }
        catch (IOException e)
        {
            log.error("load properties ERROR.", e);
        }
        String dbValue = properties.getProperty("mysql.jdbcurl");
        String name = properties.getProperty("mysql.user");
        String password = properties.getProperty("mysql.password");
        String sva = properties.getProperty("mysql.db");
        String url = null;
        String[] dbs = dbValue.split("sva");
        String db1 = "autoReconncet=true&useUnicode=true&characterEncoding=utf8";
        url = dbs[0] + sva + '?' + "user=" + name + "&password=" + password
                + '&' + db1;
        Connection conn = null;
        CallableStatement cs = null;
        BigDecimal x = new BigDecimal(0);
        BigDecimal x1 = new BigDecimal(0);
        BigDecimal y = new BigDecimal(0);
        BigDecimal y1 = new BigDecimal(0);
        BigDecimal val = new BigDecimal(10);
        try
        {

            DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
            // 获得连接
            conn = DriverManager.getConnection(url);
            int size = 0;
            List<Map<String, Object>> nowAllTime = null;
            int floorNo = 0;
            String allTime = "0";
            int numbers = 0;
            BigDecimal xspot;
            BigDecimal xspot1;
            BigDecimal yspot;
            BigDecimal yspot1;
            for (AreaModel l : ResultList)
            {
                xspot = new BigDecimal(0);
                xspot1 = new BigDecimal(0);
                yspot = new BigDecimal(0);
                yspot1 = new BigDecimal(0);
                areaId = l.getId();
                nowAllTime = areaDao.getNowAllTime(areaId, visitDay);
                size = nowAllTime.size();
                if (size < 1)
                {
                    allTime = "0";
                }
                else
                {
                    Map<String, Object> map = nowAllTime.get(0);
                    allTime = map.get("allTime").toString();
                }
                xspot = l.getxSpot();
                xspot1 = l.getX1Spot();
                yspot = l.getySpot();
                yspot1 = l.getY1Spot();
                x = xspot.multiply(val);
                x1 = xspot1.multiply(val);
                y = yspot.multiply(val);
                y1 = yspot1.multiply(val);
                floorNo = l.getFloorNo().intValue();
                numbers = areaDao.getNumberByTime(floorNo, tableName);
                cs = conn.prepareCall("{CALL areaData('" + allTime + "',"
                        + numbers + ',' + time + ',' + floorNo + ",'"
                        + tableName + "'," + x + ',' + x1 + ',' + y + ',' + y1
                        + ',' + areaId + ",'" + visitDay + "')}");
                cs.execute();
            }

        }
        catch (SQLException e1)
        {

            log.info("jdbc:" + e1.getMessage());
        }

        // for (AreaModel l : ResultList)
        // {
        // List<Map<String, Object>> oneData = daoArea.getVisitTIme(l,
        // tableName);
        // areaId = l.getId();
        // int allPeople1 = 0;
        // long stayTime1 = 0;
        // String tempUser1 = "";
        // long tempTime1 = 0;
        // String averageTime = null;
        //
        // for (int i = 0; i < oneData.size(); i++)
        // {
        // String currentUser = oneData.get(i).get("userID").toString();
        // long currentTime = Long.parseLong(oneData.get(i)
        // .get("timestamp").toString());
        //
        // if ("".equals(tempUser1))
        // {
        // tempUser1 = currentUser;
        // tempTime1 = currentTime;
        // }
        //
        // if (currentUser.equals(tempUser1))
        // {
        // if (tempTime1 + 30000 > currentTime)
        // {
        // stayTime1 += currentTime - tempTime1;
        // }
        // }
        // else
        // {
        // tempUser1 = currentUser;
        // allPeople1++;
        // }
        //
        // tempTime1 = currentTime;
        // }
        // averageTime = getMinute(stayTime1, allPeople1);
        // try
        // {
        // daoArea.saveVisitData(areaId, visitDay, stayTime1, allPeople1,
        // averageTime);
        // }
        // catch (SQLException e)
        // {
        // log.error("saveAeara:", e);
        // }
        //
        // }
        // for (AreaModel l : ResultList)
        // {
        // long oneTime = 0;
        // long allVisitTime = 0;
        // String averageTime = null;
        // users = daoArea.getVisitUser(l, tableName);
        // areaId = l.getId();
        // size = users.size();
        // for (int j = 0; j < size; j++)
        // {
        // times = daoArea.getVisitTimes(l, users.get(j), tableName);
        // oneTime = getUserTime(times);
        // allVisitTime = allVisitTime + oneTime;
        // }
        // averageTime = getMinute(allVisitTime, size);
        // try
        // {
        // daoArea.saveVisitData(areaId, visitDay, allVisitTime, size,
        // averageTime);
        // }
        // catch (SQLException e)
        // {
        // e.printStackTrace();
        // }
        //
        // }
    }

    @SuppressWarnings("unused")
    private long getUserTime(List<Long> list)
    {
        long time = 0;
        int size = list.size();
        for (int i = 0; i < size - 1; i++)
        {
            if (list.get(i) + 4000 < list.get(i + 1))
            {
                time = time + list.get(i + 1) - list.get(i);
            }
        }
        long visitTime = list.get(list.size() - 1) - list.get(0) - time;

        return visitTime;
    }

    public void doMinuteAllPeople()
    {
        Collection<StoreModel> stores = storeDao.doStores();
        int placeId = 0;
        Calendar currentDate = new GregorianCalendar();
        String tableName = Params.LOCATION
                + ConvertUtil
                        .dateFormat(currentDate.getTime(), Params.YYYYMMDD);
        Calendar currentDate1 = new GregorianCalendar();
        currentDate1.set(Calendar.HOUR_OF_DAY, -24);
        currentDate1.set(Calendar.MINUTE, 0);
        currentDate1.set(Calendar.SECOND, 0);
        String yesTableName = Params.LOCATION
                + ConvertUtil.dateFormat(currentDate1.getTime(),
                        Params.YYYYMMDD);
        String nowTime = ConvertUtil.dateFormat(currentDate.getTime(),
                "yyyy-MM-dd HH:mm:ss");
        String yesterDay = ConvertUtil.dateFormat(currentDate1.getTime(),
                "yyyy-MM-dd");
        String visitDay = ConvertUtil.dateFormat(currentDate.getTime(),
                "yyyy-MM-dd");
        String yestimeDay = yesterDay + ' ' + nowTime.split(" ")[1];
        long yesTimes = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            yesTimes = sdf.parse(yestimeDay).getTime();
            if (dao.isTableExist(yesTableName, db) < 1)
            {
                Logger.getLogger(QuartzJob.class).info("create");
                // 动态创建表
                dao.createTable(yesTableName);
            }
        }
        catch (Exception e)
        {
            log.info(e.getMessage());
        }
        int number = 0;
        int yesNumber = 0;
        int nowNumber = 0;
        String areaName = null;
        long time = 0;
        int count = 0;
        String areaId = null;
        AreaModel area = null;
        int size = 0;
        List<AreaModel> models = null;
        for (StoreModel store : stores)
        {
            time = System.currentTimeMillis() - 10 * 1000;
            placeId = store.getId();
            nowNumber = daoMaps.getNowPeople(tableName,
                    String.valueOf(placeId), String.valueOf(time));
            number = daoMaps.getNumberByMinute(tableName,
                    String.valueOf(placeId));
            yesNumber = daoMaps.getYesterdayNumber(yesTableName,
                    String.valueOf(placeId), yesTimes);
            daoMaps.saveAllPeople(placeId, yesNumber, number, nowNumber,
                    visitDay);
            models = areaDao.getAreaByPlaceId(String.valueOf(placeId));
            size = models.size();
            for (int i = 0; i < size; i++)
            {
                area = models.get(i);
                areaName = area.getAreaName();
                areaId = area.getId();
                count = areaDao.getNumberByFloorNo(area, String.valueOf(time),
                        tableName);
                daoMaps.saveNowPeople(placeId, count, areaName, areaId);
            }
        }

    }

    public static String getMinute(long time, int size)
    {
        if (size == 0)
        {
            return "0";
        }
        else
        {

            float b = Float.valueOf((time / 1000) / 60);
            float averageTime = b / size;
            DecimalFormat df = new DecimalFormat("0.00");
            String min = df.format(averageTime);
            return min;
        }

    }

    public void districtDuringStatistic()
    {
        // 读取配置文件
        InputStream in = getClass().getClassLoader().getResourceAsStream(
                "sva.properties");
        Properties properties = new Properties();
        try
        {
            properties.load(in);
        }
        catch (IOException e)
        {
            log.error("load properties ERROR.", e);
        }
        long visitTime = Long.valueOf(properties.getProperty("visitTime"));
        List<Map<String, Object>> lis = new ArrayList<Map<String, Object>>();
        // 取出1分钟内的人
        List<Map<String, Object>> allPeople = dao.getAllPeople();
        log.info("people:" + allPeople.size());
        // 取出所有的区域信息
        List<Map<String, Object>> allArea = dao.getAllArea();
        log.info("area:" + allArea.size());
        // 将人按区域更新到区域统计信息表中
        for (int i = 0; i < allPeople.size(); i++)
        {
            int x = Integer.parseInt(allPeople.get(i).get("x").toString());
            int y = Integer.parseInt(allPeople.get(i).get("y").toString());
            int z = Integer.parseInt(allPeople.get(i).get("z").toString());
            String idType = (String) allPeople.get(i).get("idType");
            String dataType = (String) allPeople.get(i).get("dataType");
            String userID = (String) allPeople.get(i).get("userID");
            long timestamp = Long.parseLong(allPeople.get(i).get("timestamp")
                    .toString());

            for (int j = 0; j < allArea.size(); j++)
            {
                int floorNo = (int) (Float.parseFloat(allArea.get(j)
                        .get("floorNo").toString()));
                int x0 = (int) (Float.parseFloat(allArea.get(j).get("xSpot")
                        .toString()) * 10);
                int y0 = (int) (Float.parseFloat(allArea.get(j).get("ySpot")
                        .toString()) * 10);
                int x1 = (int) (Float.parseFloat(allArea.get(j).get("x1Spot")
                        .toString()) * 10);
                int y1 = (int) (Float.parseFloat(allArea.get(j).get("y1Spot")
                        .toString()) * 10);
                int disId = Integer.parseInt(allArea.get(j).get("id")
                        .toString());
                log.info("x:" + x + " y:" + y + " x0:" + x0 + " y0:" + y0
                        + " x1:" + x1 + " y1:" + y1 + " areaId:" + disId
                        + " floorNO:" + floorNo + " z:" + z);
                // 判断该人是否在该区域内
                if (x >= x0 && x <= x1 && y >= y0 && y <= y1 && z == floorNo)
                {
                    String sqlChk = "select count(*) from district_during where userID = '"
                            + userID + "' and district_id = '" + disId + "'";

                    long time_local = System.currentTimeMillis();

                    try
                    {
                        // 判断该人是否之前在该区域出现过，是则更新，否则插入
                        int result = dao.doTest(sqlChk);
                        log.info(result);
                        if (result == 0)
                        {
                            log.info("insert:start");
                            String sqlIns = "insert into district_during(idType,timestamp,time_begin,"
                                    + "time_local,during,dataType,district_id,userID,loc_count) values ('"
                                    + idType
                                    + "',"
                                    + timestamp
                                    + ","
                                    + timestamp
                                    + ","
                                    + time_local
                                    + ",0,'"
                                    + dataType
                                    + "',"
                                    + disId
                                    + ",'"
                                    + userID
                                    + "',1)";
                            dao.doUpdate(sqlIns);
                        }
                        else
                        {
                            String sqlStime = "select departure_time,timestamp,time_local from district_during "
                                    + " where district_id=? and userID=?";
                            lis = dao.getLastTime(sqlStime, disId, userID);
                            long departure_time = Long.valueOf(lis.get(0)
                                    .get("departure_time").toString());
                            long lastTime = Long.valueOf(lis.get(0)
                                    .get("time_local").toString());
                            if (lastTime + visitTime * 60 * 1000 < time_local)
                            {
                                departure_time = departure_time + time_local
                                        - lastTime;
                            }
                            String sqlUpd = "update district_during set departure_time=?, timestamp=?, time_local=?"
                                    + " where district_id=? and userID=?";
                            dao.updateDistrict(sqlUpd, departure_time,
                                    timestamp, time_local, disId, userID);
                        }
                    }
                    catch (Exception e)
                    {
                        log.info(e.getStackTrace());
                    }
                    continue;
                }
            }
        }

    }

    public void saveVisitiTime()
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        List<Map<String, Object>> allArea = dao.getAllArea();
        String areaId = null;// 区域ID
        long size = 0;// 人数
        String visitDay = ConvertUtil.dateFormat(currentDate.getTime(),
                "yyyy-MM-dd");// 当前日期
        List<Map<String, Object>> lis = null;
        for (int i = 0; i < allArea.size(); i++)
        {
            long visiTime = 0;// 所有人的驻留时间
            areaId = (allArea.get(i).get("id").toString());
            lis = dao.getAreaVisitTime(areaId);
            size = lis.size();
            if (lis.size() > 0)
            {
                for (int j = 0; j < size; j++)
                {
                    long beginTime = 0;// 第一次插入时间
                    long LastTime = 0;// 最后时间
                    long likaiTime = 0;// 跑出区域总时间
                    beginTime = Long.valueOf(lis.get(j).get("time_begin")
                            .toString());
                    LastTime = Long.valueOf(lis.get(j).get("timestamp")
                            .toString());
                    likaiTime = Long.valueOf(lis.get(j).get("departure_time")
                            .toString());
                    visiTime = visiTime + LastTime - beginTime - likaiTime;
                }
            }
            String insetSql = "REPLACE INTO staticvisit(areaId,time,allTime,number,averageTime) VALUES("
                    + areaId
                    + ",'"
                    + visitDay
                    + "',"
                    + visiTime
                    + ","
                    + size
                    + "," + visiTime + ")";
            dao.saveVisitiTime(insetSql);

        }

    }

    public void emptyPhoneData()
    {
        String sqlDeletePhone = "delete from locationphone";
        String sqlDeleteDistrict = "delete from district_during";

        try
        {
            dao.doUpdate(sqlDeletePhone);
            dao.doUpdate(sqlDeleteDistrict);
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
        }
    }

}
