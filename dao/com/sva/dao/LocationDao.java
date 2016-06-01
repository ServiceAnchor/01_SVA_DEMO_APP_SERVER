package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.common.ConvertUtil;
import com.sva.common.conf.Params;
import com.sva.model.LocationModel;

@SuppressWarnings("all")
public class LocationDao
{
    private JdbcTemplate jdbcTemplate;

    private static Logger log = Logger.getLogger(LocationDao.class);

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<LocationModel> doquery()
    {
        String sql = "SELECT * FROM location";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setDataType(rs.getString("DATATYPE"));
                user.setIdType(rs.getString("IDTYPE"));
                user.setX(rs.getBigDecimal("X"));
                user.setY(rs.getBigDecimal("Y"));
                user.setZ(rs.getBigDecimal("Z"));
                user.setTimestamp(rs.getBigDecimal("TIMESTAMP"));
                user.setUserID(rs.getString("USERID"));
                return user;
            }
        });
    }

    public Collection<LocationModel> doquery1(String userId, String flooNo)
    {
        String sql = "select a.*,b.* from locationphone a left join maps b on a.z = b.floorNo where a.userID=? and a.z=?";
        // String sql =
        // "select * from locationphone where userID = ? and z =? ;";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] parm = {userId, flooNo};
        return this.jdbcTemplate.query(sql, parm, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setDataType(rs.getString("DATATYPE"));
                user.setIdType(rs.getString("IDTYPE"));
                user.setX(rs.getBigDecimal("X"));
                user.setY(rs.getBigDecimal("Y"));
                user.setZ(rs.getBigDecimal("Z"));
                user.setTimestamp(rs.getBigDecimal("TIMESTAMP"));
                user.setUserID(rs.getString("USERID"));
                user.setXo(rs.getString("xo"));
                user.setYo(rs.getString("yo"));
                user.setScale(rs.getString("scale"));
                user.setPath(rs.getString("path"));
                return user;
            }
        });
    }

    public Collection<LocationModel> queryHeatmap(String floorNo, int times)
    {
        // String sql =
        // "select location.* from location inner join (select userID, max(timestamp) as time from location group by userID) B on location.userID=B.userID AND location.timestamp=B.time";
        // JdbcTemplate tem = this.getJdbcTemplate();
        // String sql =
        // "select a.*, maps.path, maps.xo, maps.yo, maps.scale from maps left join (select * from location where timestamp = (select MAX(timestamp) from location)) as a on maps.floorNo = a.z where maps.floor=?";
        // String sql ="select a.*, maps.path, maps.xo, maps.yo, maps.scale "+
        // "from maps left join (select * from location where timestamp = (select MAX(timestamp) from location where z = (select max(floorNo) from maps where floor = ?) )) as a "
        // +
        // "on maps.floorNo = a.z " +
        // "where maps.floor= ? ";
        // String sql = "select * from maps left join " +
        // "(select * from location where location.timestamp=" +
        // "(select MAX(timestamp) from location where timestamp not in(select MAX(timestamp) from location join maps on maps.floorNo = location.z where maps.floor = ?))) as a"
        // +
        // "on maps.floorNo = a.z where maps.floor = ?";
        long time = System.currentTimeMillis() - times * 1000;
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        String sql = "select f.userID, max(f.timestamp) timestamp, max(f.z) z,max(f.dataType) dataType,"
                + " max(f.idType) idType,max(f.x) x,max(f.y) y from "
                + "(select la.* from "
                + tableName
                + " la join "
                + "(select a.userID, max(a.timestamp) timestamp, max(a.z) z from "
                + "(select lb.* from "
                + tableName
                + " lb "
                + "join (select distinct(timestamp) from "
                + tableName
                + " where z = ? and timestamp > ? order by timestamp desc limit 4) t on lb.timestamp = t.timestamp where z = ? "
                + ") a group by a.userID) b "
                + "where la.timestamp= b.timestamp and la.userID = b.userID and la.z = b.z) f group by f.userID";
        String[] params = {floorNo, String.valueOf(time), floorNo};
        log.info("sql:" + sql + ";parmas:" + params[0] + '/' + params[1]);
        return this.jdbcTemplate.query(sql, params, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setDataType(rs.getString("DATATYPE"));
                user.setIdType(rs.getString("IDTYPE"));
                user.setX(rs.getBigDecimal("X"));
                user.setY(rs.getBigDecimal("Y"));
                user.setZ(rs.getBigDecimal("Z"));
                user.setTimestamp(rs.getBigDecimal("TIMESTAMP"));
                user.setUserID(rs.getString("USERID"));
                return user;
            }
        });
    }

    public Collection<LocationModel> queryHeatmap5(String floorNo, int minute)
    {
        long time = System.currentTimeMillis() - minute * 60 * 1000;
        log.info("current time:" + time);
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);

        // String sql = "select * from " + tableName + " where timestamp > ?";
        // String sql =
        // "select t.userID, max(t.timestamp) timestamp, max(t.z) z,max(t.dataType) dataType,"
        // + " max(t.idType) idType,max(t.x) x,max(t.y) y from "
        // + "(select a.* from "
        // + tableName
        // + " a join "
        // + "(select max(timestamp) time, userID, z "
        // + "from "
        // + tableName
        // + " where z = ? and timestamp > ? group by userID) b "
        // +
        // "on a.timestamp = b.time and a.userID = b.userID and a.z = b.z) t group by t.userID";
        String sql = "select * from "
                + "(select * from "
                + tableName
                + " where z = ? and timestamp > ? order by timestamp desc) a group by a.userID";

        String[] params = {floorNo, String.valueOf(time)};
        log.info("sql:" + sql + ";parmas:" + params[0] + '/' + params[1]);

        return this.jdbcTemplate.query(sql, params, new RowMapper()
        {
            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setDataType(rs.getString("DATATYPE"));
                user.setIdType(rs.getString("IDTYPE"));
                user.setX(rs.getBigDecimal("X"));
                user.setY(rs.getBigDecimal("Y"));
                user.setZ(rs.getBigDecimal("Z"));
                user.setTimestamp(rs.getBigDecimal("TIMESTAMP"));
                user.setUserID(rs.getString("USERID"));
                return user;
            }
        });
    }

    /**
     * locationPhone为非匿名订阅，提高数据查询效率
     * 
     * @param userId
     * @return
     */
    public Collection<LocationModel> queryLocationByUseId(String userId)
    {
        String sql = "select locationPhone.* from locationPhone where userID='"
                + userId
                + "' and  timestamp = (select max(timestamp) from locationPhone where userID='"
                + userId + "')";
        // JdbcTemplate tem = this.getJdbcTemplate();

        return this.jdbcTemplate.query(sql, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setDataType(rs.getString("DATATYPE"));
                user.setIdType(rs.getString("IDTYPE"));
                user.setX(rs.getBigDecimal("X"));
                user.setY(rs.getBigDecimal("Y"));
                user.setZ(rs.getBigDecimal("Z"));
                user.setTimestamp(rs.getBigDecimal("TIMESTAMP"));
                user.setUserID(rs.getString("USERID"));
                return user;
            }
        });
    }

    public Collection<LocationModel> getUserId(String floorid, String time)
    {
        // String sql =
        // "select location.* from location inner join (select userID, max(timestamp) as time from location group by userID) B on location.userID=B.userID AND location.timestamp=B.time";
        // JdbcTemplate tem = this.getJdbcTemplate();
        // String sql =
        // "select a.*, maps.path, maps.xo, maps.yo, maps.scale from maps left join (select * from location where timestamp = (select MAX(timestamp) from location)) as a on maps.floorNo = a.z where maps.floor=?";
        // String sql ="select a.*, maps.path, maps.xo, maps.yo, maps.scale "+
        // "from maps left join (select * from location where timestamp = (select MAX(timestamp) from location where z = (select max(floorNo) from maps where floor = ?) )) as a "
        // +
        // "on maps.floorNo = a.z " +
        // "where maps.floor= ? ";
        // String sql = "select * from maps left join " +
        // "(select * from location where location.timestamp=" +
        // "(select MAX(timestamp) from location where timestamp not in(select MAX(timestamp) from location join maps on maps.floorNo = location.z where maps.floor = ?))) as a"
        // +
        // "on maps.floorNo = a.z where maps.floor = ?";
        String time1 = getChooseTime(time);
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        String sql = "select distinct(userID) from " + tableName
                + " where z=? and timestamp>?";
        String[] params = {floorid, time1};
        log.info(sql);
        return this.jdbcTemplate.query(sql, params, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setUserID(rs.getString("USERID"));
                return user;
            }
        });
    }

    public Collection<LocationModel> getMark(String userId, String time)
    {
        String time1 = getChooseTime(time);
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        String sql = "select * from " + tableName
                + " where userID=? and timestamp>? order by timestamp";
        String[] params = {userId, time1};
        log.info(sql);
        return this.jdbcTemplate.query(sql, params, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setDataType(rs.getString("DATATYPE"));
                user.setIdType(rs.getString("IDTYPE"));
                user.setX(rs.getBigDecimal("X"));
                user.setY(rs.getBigDecimal("Y"));
                user.setZ(rs.getBigDecimal("Z"));
                user.setTimestamp(rs.getBigDecimal("TIMESTAMP"));
                user.setUserID(rs.getString("USERID"));
                return user;
            }
        });
    }

    public String getChooseTime(String time)
    {
        String t = "";
        String sTime = null;
        int iTime = 0;
        if (time.equals(t))
        {
            iTime = 60;
        }
        else
        {
            iTime = Integer.parseInt(time);
        }

        long time2 = System.currentTimeMillis() - iTime * 60 * 1000;
        sTime = String.valueOf(time2);

        return sTime;

    }

    public Collection<LocationModel> queryOverData(int floorNo)
    {
        // String sql =
        // "select location.* from location inner join (select userID, max(timestamp) as time from location group by userID) B on location.userID=B.userID AND location.timestamp=B.time";
        // JdbcTemplate tem = this.getJdbcTemplate();
        // String sql =
        // "select a.*, maps.path, maps.xo, maps.yo, maps.scale from maps left join (select * from location where timestamp = (select MAX(timestamp) from location)) as a on maps.floorNo = a.z where maps.floor=?";
        // String sql ="select a.*, maps.path, maps.xo, maps.yo, maps.scale "+
        // "from maps left join (select * from location where timestamp = (select MAX(timestamp) from location where z = (select max(floorNo) from maps where floor = ?) )) as a "
        // +
        // "on maps.floorNo = a.z " +
        // "where maps.floor= ? ";
        // String sql = "select * from maps left join " +
        // "(select * from location where location.timestamp=" +
        // "(select MAX(timestamp) from location where timestamp not in(select MAX(timestamp) from location join maps on maps.floorNo = location.z where maps.floor = ?))) as a"
        // +
        // "on maps.floorNo = a.z where maps.floor = ?";
        long time = System.currentTimeMillis() - 10 * 1000;
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        String sql = "select f.userID, max(f.timestamp) timestamp, max(f.z) z,max(f.dataType) dataType,"
                + " max(f.idType) idType,max(f.x) x,max(f.y) y from "
                + "(select la.* from "
                + tableName
                + " la join "
                + "(select a.userID, max(a.timestamp) timestamp, max(a.z) z from "
                + "(select lb.* from "
                + tableName
                + " lb "
                + "join (select distinct(timestamp) from "
                + tableName
                + " where z = ? and timestamp > ? order by timestamp desc limit 4) t on lb.timestamp = t.timestamp where z = ? "
                + ") a group by a.userID) b "
                + "where la.timestamp= b.timestamp and la.userID = b.userID and la.z = b.z) f group by f.userID";
        String[] params = {String.valueOf(floorNo), String.valueOf(time),
                String.valueOf(floorNo)};
        log.info("sql:" + sql + ";parmas:" + params[0] + '/' + params[1]);
        return this.jdbcTemplate.query(sql, params, new RowMapper()
        {
            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                LocationModel user = new LocationModel();
                user.setDataType(rs.getString("DATATYPE"));
                user.setIdType(rs.getString("IDTYPE"));
                user.setX(rs.getBigDecimal("X"));
                user.setY(rs.getBigDecimal("Y"));
                user.setZ(rs.getBigDecimal("Z"));
                user.setTimestamp(rs.getBigDecimal("TIMESTAMP"));
                user.setUserID(rs.getString("USERID"));
                return user;
            }
        });
    }

}
