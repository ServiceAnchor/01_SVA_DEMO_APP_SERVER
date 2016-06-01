package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.AreaModel;

@SuppressWarnings("all")
public class AreaDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<AreaModel> doquery()
    {
        String sql = "SELECT a.*,b.floor,c.name place,ca.name category FROM area a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id left join category ca on a.categoryId = ca.id";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new AreaMapper());
    }

    public Collection<AreaModel> doqueryAll(int storeid)
    {
        String sql = "SELECT a.*,b.floor,c.name place,ca.name category FROM area a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id left join category ca on a.categoryId = ca.id where a.placeId ="
                + storeid;
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new AreaMapper());
    }

    public List<AreaModel> selectArea(String zSel)
    {
        String sql = "SELECT xSpot,ySpot,x1Spot,y1Spot from area where floorNo = ?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {zSel};
        return this.jdbcTemplate.query(sql, params, new AreaMapper1());
    }
    
    public List<AreaModel> getAreaDatAById(String id)
    {
        String sql = "SELECT xSpot,ySpot,x1Spot,y1Spot from area where id = ?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {id};
        return this.jdbcTemplate.query(sql, params, new AreaMapper1());
    }

    public List<AreaModel> getAreaByPlaceId(String zSel)
    {
        String sql = "SELECT xSpot,ySpot,x1Spot,y1Spot,areaName,floorNo,id from area where placeId = ?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {zSel};
        return this.jdbcTemplate.query(sql, params, new AreaMapper2());
    }

    private class AreaMapper1 implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            AreaModel area = new AreaModel();

            // area.setPlace(rs.getString("PLACE"));
            area.setxSpot(rs.getBigDecimal("XSPOT"));
            area.setySpot(rs.getBigDecimal("YSPOT"));
            area.setX1Spot(rs.getBigDecimal("X1SPOT"));
            area.setY1Spot(rs.getBigDecimal("Y1SPOT"));

            return area;
        }
    }

    private class AreaMapper2 implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            AreaModel area = new AreaModel();

            // area.setPlace(rs.getString("PLACE"));
            area.setxSpot(rs.getBigDecimal("XSPOT"));
            area.setySpot(rs.getBigDecimal("YSPOT"));
            area.setX1Spot(rs.getBigDecimal("X1SPOT"));
            area.setY1Spot(rs.getBigDecimal("Y1SPOT"));
            area.setAreaName(rs.getString("areaName"));
            area.setFloorNo(rs.getBigDecimal("FLOORNO"));
            area.setId(rs.getString("ID"));

            return area;
        }
    }

    public void saveAreaInfo(AreaModel area) throws SQLException
    {
        String sql = "INSERT INTO area(placeId,areaName,xSpot,ySpot,x1Spot,y1Spot,floorNo,categoryId) VALUES(?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, area.getPlaceId(), area.getAreaName(),
                area.getxSpot(), area.getySpot(), area.getX1Spot(),
                area.getY1Spot(), area.getFloorNo(), area.getCategoryId());
    }

    public int deleteArea(String xSpot, String ySpot, String x1Spot,
            String y1Spot, String floorNo, String categoryId)
            throws SQLException
    {
        String sql = "DELETE FROM area where xSpot = ? and ySpot = ? and x1Spot = ? and y1Spot = ? and floorNo = ? and categoryid = ?";
        return this.jdbcTemplate.update(sql, xSpot, ySpot, x1Spot, y1Spot,
                floorNo, categoryId);
    }

    public void updateAreaInfo(AreaModel mmm) throws SQLException
    {
        String sql = "UPDATE area SET placeId=?,areaName = ?,xSpot=?,ySpot=?,x1Spot=?,y1Spot=?,floorNo=?,categoryId=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getPlaceId(), mmm.getAreaName(),
                mmm.getxSpot(), mmm.getySpot(), mmm.getX1Spot(),
                mmm.getY1Spot(), mmm.getFloorNo(), mmm.getCategoryId(),
                mmm.getId());
    }

    public Integer getNumberByArea(AreaModel mm, long nowTime, long startTime,
            String tableName)
    {
        String sql = "SELECT count(distinct userID) number " + " FROM "
                + tableName + " where timestamp > " + startTime
                + " and timestamp <  " + nowTime
                + " and z = ? and x > ? and x <? and y >? and y<? ";
        String[] params = {String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10)};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Integer getNumberByAreaDay(AreaModel mm, String time, String time2,
            String tableName)
    {
        String sql = "SELECT count(distinct userID) number " + " FROM "
                + tableName
                + " where z = ? and x > ? and x <? and y >? and y<? ";
        String[] params = {String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10)};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Integer getNumberByTime(int floorNo, String tableName)
    {
        String sql = "SELECT count(distinct userID) " + " FROM " + tableName
                + " where z = ? ";
        String[] params = {String.valueOf(floorNo)};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Integer getYesterdayNumber(int floorNo, String tableName, String time)
    {
        String sql = "SELECT count(distinct userID) " + " FROM " + tableName
                + " where z = ? and timestamp<= ?";
        String[] params = {String.valueOf(floorNo), time};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Integer getNumberByFloorNo(AreaModel mm, String time,
            String tableName)
    {
        String sql = "SELECT count(distinct userID) number " + " FROM "
                + tableName + " where timestamp > " + time
                + " and z = ? and x > ? and x <? and y >? and y<? ";
        String[] params = {String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10)};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    private class AreaMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            AreaModel area = new AreaModel();
            area.setPlaceId(rs.getInt("PLACEID"));
            area.setPlace(rs.getString("PLACE"));
            area.setxSpot(rs.getBigDecimal("XSPOT"));
            area.setySpot(rs.getBigDecimal("YSPOT"));
            area.setX1Spot(rs.getBigDecimal("X1SPOT"));
            area.setY1Spot(rs.getBigDecimal("Y1SPOT"));
            area.setAreaName(rs.getString("AREANAME"));
            area.setFloorNo(rs.getBigDecimal("FLOORNO"));
            area.setFloor(rs.getString("FLOOR"));
            area.setCategoryId(rs.getInt("CATEGORYID"));
            area.setCategory(rs.getString("CATEGORY"));
            area.setId(rs.getString("ID"));
            area.setStatus(rs.getInt("STATUS"));
            area.setZoneId(rs.getInt("ZONEID"));
            return area;
        }
    }

    private class AreaMapper3 implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            AreaModel area = new AreaModel();
            area.setPlaceId(rs.getInt("PLACEID"));
            area.setxSpot(rs.getBigDecimal("XSPOT"));
            area.setySpot(rs.getBigDecimal("YSPOT"));
            area.setX1Spot(rs.getBigDecimal("X1SPOT"));
            area.setY1Spot(rs.getBigDecimal("Y1SPOT"));
            area.setAreaName(rs.getString("AREANAME"));
            area.setFloorNo(rs.getBigDecimal("FLOORNO"));
            area.setCategoryId(rs.getInt("CATEGORYID"));
            area.setId(rs.getString("ID"));
            area.setStatus(rs.getInt("STATUS"));
            return area;
        }
    }

    public int checkByName(String name)
    {
        String sql = "SELECT count(*) res FROM area where areaName = ?";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{name},
                Integer.class);
    }

    public int checkByName1(String name, String id)
    {
        String sql = "SELECT count(*) res FROM area where areaName = ? and id !=?";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{name, id},
                Integer.class);
    }

    public List<Map<String, Object>> getAreaName(String placeId)
    {
        String sql = "select id,areaName from area where placeId=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {placeId};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<String> getAreaNames(String placeId)
    {
        String sql = "select areaName from area where placeId=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {placeId};
        return this.jdbcTemplate.queryForList(sql, params, String.class);
    }

    public List<String> getAreaNameByAreaId(String areaId)
    {
        String sql = "select areaName from area where id=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {areaId};
        return this.jdbcTemplate.queryForList(sql, params, String.class);
    }

    public List<String> getVisitUser(AreaModel mm, String tableName)
    {
        String sql = "SELECT T.userID FROM (SELECT userId FROM "
                + tableName
                + " where z = ? and x > ? and x <? and y >? and y<?) T GROUP BY T.userID";
        String[] params = {String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10)};
        return this.jdbcTemplate.queryForList(sql, params, String.class);
    }

    public List<Long> getVisitTimes(AreaModel mm, String userId,
            String tablename)
    {
        String sql = "SELECT timestamp "
                + " FROM "
                + tablename
                + " where z = ? and x > ? and x <? and y >? and y<? and userID=? order by timestamp asc";
        String[] params = {String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10), userId};
        return this.jdbcTemplate.queryForList(sql, params, Long.class);
    }

    public void saveVisitData(String areaId, String time, Long allTime,
            int number, String averageTime) throws SQLException
    {
        String sql = "REPLACE INTO staticVisit(areaId,time,allTime,number,averageTime) VALUES(?,?,?,?,?)";
        this.jdbcTemplate.update(sql, areaId, time, allTime, number,
                averageTime);
    }

    public Integer getBaShowVisitUser(AreaModel mm, String tableName,
            String time)
    {
        String sql = "SELECT count(distinct userID)  FROM "
                + tableName
                + " where z = ? and timestamp > ? and x > ? and x <? and y >? and y<?";
        String[] params = {String.valueOf(mm.getFloorNo()), time,
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10)};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public List<String> getAllBaShowVisitUser(String floorNo, String tableName,
            String time)
    {
        String sql = "SELECT T.userID FROM (SELECT userId FROM " + tableName
                + " where z = ? and timestamp > ?) T GROUP BY T.userID";
        String[] params = {floorNo, time};
        return this.jdbcTemplate.queryForList(sql, params, String.class);
    }

    public List<String> getUserByFloorNo(String floorNo, String tableName,
            String time)
    {
        String sql = "SELECT T.userID FROM (SELECT userId FROM " + tableName
                + " where z = ? and timestamp > ?) T GROUP BY T.userID";
        String[] params = {floorNo, time};
        return this.jdbcTemplate.queryForList(sql, params, String.class);
    }

    public List<Long> getBaShowVisitTimes(AreaModel mm, String userId,
            String tablename, String time)
    {
        String sql = "SELECT timestamp "
                + " FROM "
                + tablename
                + " where z = ? and timestamp > ? and x > ? and x <? and y >? and y<? and userID=? order by timestamp asc";
        String[] params = {String.valueOf(mm.getFloorNo()), time,
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10), userId};
        return this.jdbcTemplate.queryForList(sql, params, Long.class);
    }

    public List<Long> getBaShowAllVisitTimes(String floorNo, String userId,
            String tablename, String time)
    {
        String sql = "SELECT timestamp "
                + " FROM "
                + tablename
                + " where z = ? and timestamp > ? and userID=? order by timestamp asc";
        String[] params = {floorNo, time, userId};
        return this.jdbcTemplate.queryForList(sql, params, Long.class);
    }

    public void svaeBaShowByAeare(String areaId, String time, Long allTime,
            int number, String averageTime) throws SQLException
    {
        String sql = "REPLACE INTO staticVisitbashow(areaId,time,allTime,number,averageTime) VALUES(?,?,?,?,?)";
        this.jdbcTemplate.update(sql, areaId, time, allTime, number,
                averageTime);
    }

    public void svaeBaShowByAllAeare(String floorNo, String time, Long allTime,
            int number, String averageTime) throws SQLException
    {
        String sql = "REPLACE INTO staticVisitbashowall(floorNO,time,allTime,number,averageTime) VALUES(?,?,?,?,?)";
        this.jdbcTemplate.update(sql, floorNo, time, allTime, number,
                averageTime);
    }

    public List<AreaModel> selectAeareBaShow(String zSel)
    {
        String sql = "SELECT * FROM area where floorNo = ?";
        String[] params = {zSel};
        return this.jdbcTemplate.query(sql, params, new AreaMapper3());
    }

    public List<AreaModel> getAreaByAreaId(String id)
    {
        String sql = "SELECT * FROM area where id = ?";
        String[] params = {id};
        return this.jdbcTemplate.query(sql, params, new AreaMapper3());
    }

    public Map<String, Object> getAverageTimeByAreaId(String areaId,
            String visiday)
    {
        // String sql =
        // "SELECT averageTime,number FROM staticvisitbashow where areaId = ? and time=?";
        // String[] params = {areaId, time};
        // return this.jdbcTemplate.queryForList(sql, params);
        String sql = "select * from staticvisit where areaId = ? and time=? ";
        Object[] params = {areaId, visiday};
        List<Map<String, Object>> res = this.jdbcTemplate.queryForList(sql,
                params);
        long allTime = 0;
        int size = 0;
        for (int i = 0; i < res.size(); i++)
        {
            allTime = Long.parseLong(res.get(i).get("allTime").toString());
            size = Integer.parseInt(res.get(i).get("number").toString());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("timePeriod", allTime);
        result.put("count", size);
        return result;
    }

    public List<Map<String, Object>> getAllAverageTimeByAreaId(String floorNo,
            String time)
    {
        String sql = "SELECT averageTime,number FROM staticvisitbashowall where floorNo = ? and time=?";
        String[] params = {floorNo, time};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getOverAverageTime(String areaId,
            String time)
    {
        String sql = "SELECT allTime,number FROM staticvisit where areaId = ? and time=?";
        String[] params = {areaId, time};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getBaShowAllData(String floorNo,
            String time, String tableName)
    {
        String sql = "select timestamp,userID  from " + tableName
                + " where z=? and timestamp >? order by userID,timestamp";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {floorNo, time};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getBaShowData(AreaModel mm, String time,
            String tableName)
    {
        String sql = "select timestamp,userID  from "
                + tableName
                + " where z=? and timestamp >? and x > ? and x <? and y >? and y<? order by userID,timestamp";
        String[] params = {String.valueOf(mm.getFloorNo()), time,
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10)};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getVisitTIme(AreaModel mm, String tableName)
    {
        String sql = "select timestamp,userID  from "
                + tableName
                + " where z=? and x > ? and x <? and y >? and y<? order by userID,timestamp";
        String[] params = {String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getxSpot().longValue() * 10),
                String.valueOf(mm.getX1Spot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10),
                String.valueOf(mm.getY1Spot().longValue() * 10)};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getNowNumber(String placeId)
    {
        String sql = "select areaName,number,areaId  from nowpeople where placeId = ?";
        String[] params = {placeId};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getNowAllTime(String areaId,
            String visitDay)
    {
        String sql = "select allTime  from staticvisit where areaId = ? and time=?";
        String[] params = {areaId, visitDay};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public void updateStatus(String areaId)
    {
        String sql = "update area set status = 0 where id = ?";
        this.jdbcTemplate.update(sql, areaId);
    }

    public void updateStatus1(String areaId)
    {
        String sql = "update area set status = 1 where id = ?";
        this.jdbcTemplate.update(sql, areaId);
    }

    public void updateZoneId(String areaId, String zoneId)
    {
        String sql = "update area set zoneId = ? where id = ?";
        String[] params = {zoneId, areaId};
        this.jdbcTemplate.update(sql, params);
    }

    /**
     * 更新zoneid为空
     * 
     * @param areaId
     */
    public void updateZoneIdToNull(String areaId)
    {
        String sql = "update area set zoneId = null where id = ?";
        this.jdbcTemplate.update(sql, areaId);
    }

    public Integer getBaShowVisitUser(String areaId, String time)
    {
        String sql = "select count(distinct userID) from district_during where district_id=? and timestamp>?";
        String[] params = {areaId, time};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Integer getAllPeoples(String floorNo, String tableName, long bzTime)
    {
        String sql = "select count(distinct userID) from " + tableName
                + " where z=? and timestamp>?";
        String[] params = {floorNo, String.valueOf(bzTime)};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }
    
    public Integer getAllArea(String areaId )
    {
        String sql = "select count(distinct userID) from district_during where district_id=? " ;
        String[] params = {areaId};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Map<String, Object> getAllAverageTimeByAreaId(String floorNo,
            long time, String tableName)
    {
        // String sql =
        // "SELECT averageTime,number FROM staticvisitbashowall where floorNo = ? and time=?";
        // String[] params = {floorNo, time};
        // return this.jdbcTemplate.queryForList(sql, params);
        String sql = "select * from " + tableName
                + " where z=? and timestamp>?";
        Object[] params = {floorNo, time};
        List<Map<String, Object>> res = this.jdbcTemplate.queryForList(sql,
                params);
        long timePeriod = 0;
        long tempTime = 0;
        for (int i = 0; i < res.size(); i++)
        {
            long timeNow = Long.parseLong(res.get(i).get("timestamp")
                    .toString());
            long timeBegin = Long.parseLong(res.get(i).get("time_begin")
                    .toString());
            if (timeBegin > time)
            {
                tempTime = timeBegin;
            }
            else
            {
                tempTime = time;
            }

            /** 添加需求当每个人统计的时长超过2小时则按两小时计算 */
            long timePeriodTemp = timeNow - tempTime;
            if (timePeriodTemp > 7200000)
            {
                timePeriodTemp = 7200000;
            }
            timePeriod += timePeriodTemp;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("timePeriod", timePeriod);
        result.put("count", res.size());
        return result;
    }

    public long getMaxTimestamp()
    {
        /*
         * String tableName = Params.LOCATION + ConvertUtil.dateFormat(new
         * Date(), Params.YYYYMMDD);
         */
        String tableName = "district_during";

        String sqlSel = "select timestamp from " + tableName
                + " order by timestamp desc limit 1";
        List<Map<String, Object>> res = this.jdbcTemplate.queryForList(sqlSel);
        long timestamp = 0;
        if (res.size() != 1)
        {
            return timestamp;
        }
        else
        {
            timestamp = Long.parseLong(res.get(0).get("timestamp").toString());
        }
        return timestamp;
    }
}
