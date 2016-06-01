package com.sva.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sva.common.ConvertUtil;
import com.sva.common.QuartzJob;
import com.sva.common.conf.Params;

public class CommonDao
{
    private JdbcTemplate jdbcTemplate;

    private static Logger log = Logger.getLogger(QuartzJob.class);

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createTable(String tableName)
    {
        String sql = "CREATE TABLE "
                + tableName
                + "(idType VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci', "
                + "timestamp BIGINT(20) NULL DEFAULT NULL, "
                + "time_begin BIGINT(20) NULL DEFAULT NULL, "
                + "time_local BIGINT(20) NULL DEFAULT NULL, "
                + "during BIGINT(20) NOT NULL DEFAULT 0, "
                + "dataType VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci', "
                + "x DECIMAL(10,0) NULL DEFAULT NULL, "
                + "y DECIMAL(10,0) NULL DEFAULT NULL, "
                + "z DECIMAL(10,0) NULL DEFAULT NULL, "
                + "loc_count BIGINT(20) NOT NULL DEFAULT 1, "
                + "userID VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',"
                + "INDEX `Index 1` (`userID`), "
                + "INDEX `Index 2` (`timestamp`) "
                + ")COLLATE='utf8_unicode_ci' ENGINE=InnoDB;";

        try
        {
            jdbcTemplate.update(sql);
            return 1;
        }
        catch (Exception e)
        {
            Logger.getLogger(CommonDao.class).info(e.getStackTrace());
        }
        return 0;

    }

    public int isTableExist(String tableName, String schema)
    {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ? and TABLE_SCHEMA = ?;";
        try
        {
            return jdbcTemplate.queryForObject(sql, new Object[]{tableName,
                    schema}, Integer.class);
        }
        catch (Exception e)
        {
            Logger.getLogger(CommonDao.class).info(e.getStackTrace());
        }
        return 0;
    }

    public int doUpdate(String sql) throws SQLException
    {
        return this.jdbcTemplate.update(sql);
    }

    public int updateDistrict(String sql, long departure_time, long timestamp,
            long time_local, int disId, String userID) throws SQLException
    {
        return this.jdbcTemplate.update(sql, departure_time, timestamp,
                time_local, disId, userID);
    }

    public List<Map<String, Object>> getLastTime(String sql, int disId,
            String userID) throws SQLException
    {
        return this.jdbcTemplate.queryForList(sql, disId, userID);
    }

    public int doTest(String sql) throws SQLException
    {
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<String> doDeleteInfo(String time)
    {
        String sql = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA = 'sva' AND TABLE_NAME LIKE '%location2%' and table_name<'"
                + time + "';";
        return jdbcTemplate.queryForList(sql, String.class);

    }

    public void deleteTable(String tableName)
    {
        String sql = " DROP TABLE IF EXISTS " + tableName + ';';
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getStatisticTemp()
    {
        String sql = "select * from statistictemp";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getDataToday()
    {
        String time = ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD2)
                + "%'";
        String sql = "select * from statisticlinetemp where placeId = 1 and time like '"
                + time + " order by time asc ";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getAllPeople()
    {
        long time = System.currentTimeMillis() - 60 * 1000;
        log.info("times:" + time);
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        log.info("tableName:" + tableName);
        String sql = "select * from " + tableName + " where timestamp > ? ";
        // String[] params = {String.valueOf(time)};

        return jdbcTemplate.queryForList(sql, String.valueOf(time));
    }

    public List<Map<String, Object>> getAllArea()
    {
        String sql = "select * from area ";
        // String[] params = {String.valueOf(time)};

        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getAreaVisitTime(String areaId)
    {
        String sql = "select *from district_during where district_id = ?";
        return jdbcTemplate.queryForList(sql, areaId);
    }

    public void saveVisitiTime(String sql)
    {
        this.jdbcTemplate.update(sql);
    }

}
