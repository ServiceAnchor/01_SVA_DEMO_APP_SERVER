package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.common.ConvertUtil;
import com.sva.common.conf.Params;
import com.sva.model.LocationModel;
import com.sva.model.MessageModel;
import com.sva.web.models.MsgMngModel;

@SuppressWarnings("all")
public class MessageDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<MessageModel> doquery()
    {
        String sql = "SELECT a.*,b.floor,c.name place,d.areaName  FROM message a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id left join area d on a.shopId=d.id;";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper1());
    }

    /*
     * 通过用户名获取相对应的商场权限
     */
    public Collection<MessageModel> doqueryByStoreid(int storeid)
    {
        String sql = "SELECT a.*,b.floor,c.name place,d.areaName  FROM message a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id left join area d on a.shopId=d.id where a.placeId ="
                + storeid;
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper1());
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<MessageModel> queryByLocation(LocationModel loc)
    {
        String sql = "select t.*,m.floor,0 place from message t join maps m on t.floorNo = m.floorNo where m.floorNo = "
                + loc.getZ()
                + " and (t.xSpot-t.rangeSpot) < "
                + loc.getX().doubleValue()
                / 10
                + " and (t.xSpot+t.rangeSpot) > "
                + loc.getX().doubleValue()
                / 10
                + " and (t.ySpot-t.rangeSpot) < "
                + loc.getY().doubleValue()
                / 10
                + " and (t.ySpot+t.rangeSpot) > "
                + loc.getY().doubleValue()
                / 10 + " and t.isEnable = '开启'";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper());
    }

    public Collection<MessageModel> queryByLocation1(LocationModel loc)
    {
        String sql = "select t.*,m.floor,0 place from message t join maps m on t.floorNo = m.floorNo where m.floorNo = "
                + loc.getZ()
                + " and (t.xSpot) < "
                + loc.getX().doubleValue()
                / 10
                + " and (t.x1Spot) > "
                + loc.getX().doubleValue()
                / 10
                + " and (t.ySpot) < "
                + loc.getY().doubleValue()
                / 10
                + " and (t.y1Spot) > "
                + loc.getY().doubleValue()
                / 10
                + " and t.isEnable = '开启'";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper());
    }

    public Collection<MessageModel> queryByLocation2(LocationModel loc)
    {
        String sql = "SELECT a.*,b.*,p.* FROM electronic a left join area b on a.shopId=b.id  left join locationphone p on a.floorNo=p.z where a.floorNo="
                + loc.getXo()
                + " and b.xSpot < (p.x)/10 and b.x1Spot > (p.x)/10 and b.ySpot < (p.y)/10 and b.y1Spot >  (p.y)/10";
        // String sql =
        // "select t.*,m.floor,0 place from electronic t join maps m on t.floorNo = m.floorNo where m.floorNo = "
        // + loc.getZ()
        // + " and (t.xSpot) < "
        // + loc.getX().doubleValue()
        // / 10
        // + " and (t.x1Spot) > "
        // + loc.getX().doubleValue()
        // / 10
        // + " and (t.ySpot) < "
        // + loc.getY().doubleValue()
        // / 10
        // + " and (t.y1Spot) > "
        // + loc.getY().doubleValue()
        // / 10
        // + " and t.isEnable = '开启'";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper());
    }

    public void saveMsgInfo(MsgMngModel mmm) throws SQLException
    {
        String sql = "INSERT INTO message(placeId,shopName,xSpot,ySpot,floorNo,rangeSpot,message,timeInterval,isEnable,pictruePath,moviePath) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, mmm.getPlaceId(), mmm.getShopName(),
                mmm.getxSpot(), mmm.getySpot(), mmm.getFloorNo(),
                mmm.getRangeSpot(), mmm.getMessage(), mmm.getTimeInterval(),
                mmm.getIsEnable(), mmm.getPictruePath(), mmm.getMoviePath());
    }

    public void saveMsgInfo1(MsgMngModel mmm) throws SQLException
    {
        String sql = "INSERT INTO message(shopId,x1Spot,y1Spot,placeId,shopName,xSpot,ySpot,floorNo,rangeSpot,message,timeInterval,isEnable,pictruePath,moviePath) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, mmm.getShopId(), mmm.getX1Spot(),
                mmm.getY1Spot(), mmm.getPlaceId(), mmm.getShopName(),
                mmm.getxSpot(), mmm.getySpot(), mmm.getFloorNo(),
                mmm.getRangeSpot(), mmm.getMessage(), mmm.getTimeInterval(),
                mmm.getIsEnable(), mmm.getPictruePath(), mmm.getMoviePath());
    }

    public int deleteMessage(String xSpot, String ySpot, String floorNo)
            throws SQLException
    {
        String sql = "DELETE FROM message where xSpot = ? and ySpot = ? and floorNo = ?";
        return this.jdbcTemplate.update(sql, xSpot, ySpot, floorNo);
    }

    public void updateMsgInfo(MsgMngModel mmm) throws SQLException
    {
        String sql = "UPDATE message SET shopId=?,x1Spot=?,y1Spot=?,placeId=?,shopName=?,xSpot=?,ySpot=?,rangeSpot=?,message=?,timeInterval=?,isEnable=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getShopId(), mmm.getX1Spot(),
                mmm.getY1Spot(), mmm.getPlaceId(), mmm.getShopName(),
                mmm.getxSpot(), mmm.getySpot(), mmm.getRangeSpot(),
                mmm.getMessage(), mmm.getTimeInterval(), mmm.getIsEnable(),
                mmm.getFloorNo(), mmm.getId());
    }

    public void updateMsgInfo1(MsgMngModel mmm) throws SQLException
    {
        String sql = "UPDATE message SET shopId=?,x1Spot=?,y1Spot=?, placeId=?,shopName=?,xSpot=?,ySpot=?,rangeSpot=?,message=?,timeInterval=?,isEnable=?,moviePath=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getShopId(), mmm.getX1Spot(),
                mmm.getY1Spot(), mmm.getPlaceId(), mmm.getShopName(),
                mmm.getxSpot(), mmm.getySpot(), mmm.getRangeSpot(),
                mmm.getMessage(), mmm.getTimeInterval(), mmm.getIsEnable(),
                mmm.getMoviePath(), mmm.getFloorNo(), mmm.getId());
    }

    public void updateMsgInfo2(MsgMngModel mmm) throws SQLException
    {
        String sql = "UPDATE message SET shopId=?,x1Spot=?,y1Spot=?,placeId=?,shopName=?,xSpot=?,ySpot=?,rangeSpot=?,message=?,timeInterval=?,isEnable=?,pictruePath=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getShopId(), mmm.getX1Spot(),
                mmm.getY1Spot(), mmm.getPlaceId(), mmm.getShopName(),
                mmm.getxSpot(), mmm.getySpot(), mmm.getRangeSpot(),
                mmm.getMessage(), mmm.getTimeInterval(), mmm.getIsEnable(),
                mmm.getPictruePath(), mmm.getFloorNo(), mmm.getId());
    }

    public void updateMsgInfo3(MsgMngModel mmm) throws SQLException
    {
        String sql = "UPDATE message SET shopId=?,x1Spot=?,y1Spot=?,placeId=?,shopName=?,xSpot=?,ySpot=?,rangeSpot=?,message=?,timeInterval=?,isEnable=?,pictruePath=?,moviePath=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getShopId(), mmm.getX1Spot(),
                mmm.getY1Spot(), mmm.getPlaceId(), mmm.getShopName(),
                mmm.getxSpot(), mmm.getySpot(), mmm.getRangeSpot(),
                mmm.getMessage(), mmm.getTimeInterval(), mmm.getIsEnable(),
                mmm.getPictruePath(), mmm.getMoviePath(), mmm.getFloorNo(),
                mmm.getId());
    }

    public String getFloorByFloorNo(String floorNo)
    {
        String sql = "SELECT floor FROM maps where floorNo=? limit 1";
        String[] params = {floorNo};
        return this.jdbcTemplate.queryForObject(sql, params, String.class);
    }

    public Integer getNumberByArea(MessageModel mm)
    {
        String tableName = Params.LOCATION
                + ConvertUtil.dateFormat(new Date(), Params.YYYYMMDD);
        String sql = "select count(*) from "
                + tableName
                + " la join "
                + "(select a.userID, max(a.timestamp) timestamp, max(a.z) z from "
                + "(select lb.* from "
                + tableName
                + " lb join "
                + "(select distinct(timestamp) from "
                + tableName
                + " where z = ? order by timestamp desc limit 4) t "
                + " on lb.timestamp = t.timestamp where z = ? "
                + ") a group by a.userID) b "
                + "where la.timestamp= b.timestamp and la.userID = b.userID and la.z = b.z "
                + "and la.x > ? and la.x <? and la.y >? and la.y<? ";
        String[] params = {
                String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getFloorNo()),
                String.valueOf(mm.getxSpot().longValue() * 10
                        - mm.getRangeSpot().longValue() * 10),
                String.valueOf(mm.getxSpot().longValue() * 10
                        + mm.getRangeSpot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10
                        - mm.getRangeSpot().longValue() * 10),
                String.valueOf(mm.getySpot().longValue() * 10
                        + mm.getRangeSpot().longValue() * 10)};
        return this.jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    private class MessageMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            MessageModel msg = new MessageModel();
            msg.setPlaceId(rs.getInt("PLACEID"));
            msg.setPlace(rs.getString("PLACE"));
            msg.setShopName(rs.getString("SHOPNAME"));
            msg.setxSpot(rs.getBigDecimal("XSPOT"));
            msg.setySpot(rs.getBigDecimal("YSPOT"));
            msg.setX1Spot(rs.getBigDecimal("X1SPOT"));
            msg.setY1Spot(rs.getBigDecimal("Y1SPOT"));
            msg.setFloorNo(rs.getBigDecimal("FLOORNO"));
            msg.setFloor(rs.getString("FLOOR"));
            msg.setRangeSpot(rs.getBigDecimal("RANGESPOT"));
            msg.setMessage(rs.getString("MESSAGE"));
            msg.setTimeInterval(rs.getInt("timeInterval"));
            msg.setIsEnable(rs.getString("ISENABLE"));
            msg.setPictruePath(rs.getString("PICTRUEPATH"));
            msg.setMoviePath(rs.getString("MOVIEPATH"));
            msg.setId(rs.getString("ID"));
            msg.setShopId(rs.getInt("SHOPID"));
            return msg;
        }
    }

    private class MessageMapper1 implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            MessageModel msg = new MessageModel();
            msg.setPlaceId(rs.getInt("PLACEID"));
            msg.setPlace(rs.getString("PLACE"));
            msg.setShopName(rs.getString("SHOPNAME"));
            msg.setAreaName(rs.getString("areaName"));
            msg.setxSpot(rs.getBigDecimal("XSPOT"));
            msg.setySpot(rs.getBigDecimal("YSPOT"));
            msg.setX1Spot(rs.getBigDecimal("X1SPOT"));
            msg.setY1Spot(rs.getBigDecimal("Y1SPOT"));
            msg.setFloorNo(rs.getBigDecimal("FLOORNO"));
            msg.setFloor(rs.getString("FLOOR"));
            msg.setRangeSpot(rs.getBigDecimal("RANGESPOT"));
            msg.setMessage(rs.getString("MESSAGE"));
            msg.setTimeInterval(rs.getInt("timeInterval"));
            msg.setIsEnable(rs.getString("ISENABLE"));
            msg.setPictruePath(rs.getString("PICTRUEPATH"));
            msg.setMoviePath(rs.getString("MOVIEPATH"));
            msg.setId(rs.getString("ID"));
            msg.setShopId(rs.getInt("SHOPID"));
            return msg;
        }
    }

    public Collection<MessageModel> getAllMessageData()
    {
        String sql = "SELECT a.*,b.floor,c.name place FROM message a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id where a.isEnable='开启'";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper());
    }
}
