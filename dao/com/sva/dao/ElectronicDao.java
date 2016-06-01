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
import com.sva.model.ElectronicModel;
import com.sva.model.LocationModel;
import com.sva.model.MessageModel;
import com.sva.web.models.MsgMngModel;

@SuppressWarnings("all")
public class ElectronicDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<ElectronicModel> doquery()
    {
        String sql = "SELECT a.*,b.floor,c.name place,d.areaName shopName FROM electronic a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id left join area d on a.shopId = d.id;";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper());
    }

    /*
     * 通过用户名获取相对应的商场权限
     */
    public Collection<ElectronicModel> doqueryByStoreid(int storeid)
    {
        String sql = "SELECT a.*,b.floor,c.name place,d.areaName shopName FROM electronic a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id left join area d on a.shopId = d.id where a.placeId ="
                + storeid;
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MessageMapper());
    }

    public void saveElectronic(ElectronicModel mmm) throws SQLException
    {
        String sql = "INSERT INTO electronic(placeId,electronicName,floorNo,message,pictruePath,moviePath,shopId) VALUES(?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, mmm.getPlaceId(),
                mmm.getElectronicName(), mmm.getFloorNo(), mmm.getMessage(),
                mmm.getPictruePath(), mmm.getMoviePath(), mmm.getAreaId());
    }

    public int deleteMessage(String xSpot, String ySpot, String floorNo)
            throws SQLException
    {
        String sql = "DELETE FROM message where xSpot = ? and ySpot = ? and floorNo = ?";
        return this.jdbcTemplate.update(sql, xSpot, ySpot, floorNo);
    }

    public void updateMsgInfo(ElectronicModel mmm) throws SQLException
    {
        String sql = "UPDATE electronic SET placeId=?,shopId=?,message=?,floorNo=?,electronicName=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getPlaceId(), mmm.getAreaId(),
                mmm.getMessage(), mmm.getFloorNo(), mmm.getElectronicName(),
                mmm.getId());
    }

    public void updateMsgInfo1(ElectronicModel mmm) throws SQLException
    {
        String sql = "UPDATE electronic SET moviePath=?,placeId=?,shopId=?,message=?,floorNo=?,electronicName=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getMoviePath(), mmm.getPlaceId(),
                mmm.getAreaId(), mmm.getMessage(), mmm.getFloorNo(),
                mmm.getElectronicName(), mmm.getId());
    }

    public void updateMsgInfo2(ElectronicModel mmm) throws SQLException
    {
        String sql = "UPDATE electronic SET pictruePath=?,placeId=?,shopId=?,message=?,floorNo=?,electronicName=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getPictruePath(), mmm.getPlaceId(),
                mmm.getAreaId(), mmm.getMessage(), mmm.getFloorNo(),
                mmm.getElectronicName(), mmm.getId());
    }

    public void updateMsgInfo3(ElectronicModel mmm) throws SQLException
    {
        String sql = "UPDATE electronic SET moviePath=?,pictruePath=?,placeId=?,shopId=?,message=?,floorNo=?,electronicName=? where id=?";
        this.jdbcTemplate.update(sql, mmm.getMoviePath(), mmm.getPictruePath(),
                mmm.getPlaceId(), mmm.getAreaId(), mmm.getMessage(),
                mmm.getFloorNo(), mmm.getElectronicName(), mmm.getId());
    }

    public Collection<ElectronicModel> queryByLocation2(LocationModel loc)
    {
        String sql = "SELECT a.*,b.*,p.* FROM electronic a left join area b on a.shopId=b.id  left join locationphone p on a.floorNo=p.z where a.floorNo="
                + loc.getZ()
                + " and p.userID='"
                + loc.getUserID()
                + "' and b.xSpot < (p.x)/10 and b.x1Spot > (p.x)/10 and b.ySpot < (p.y)/10 and b.y1Spot >  (p.y)/10 ";

        return this.jdbcTemplate.query(sql, new MessageMapper1());
    }

    public int deleteElectronic(int id) throws SQLException
    {
        String sql = "delete from electronic where id = ?;";
        return this.jdbcTemplate.update(sql, id);
    }

    private class MessageMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            ElectronicModel msg = new ElectronicModel();
            msg.setPlaceId(rs.getInt("PLACEID"));
            msg.setPlace(rs.getString("PLACE"));
            msg.setShopName(rs.getString("SHOPNAME"));
            msg.setFloorNo(rs.getBigDecimal("FLOORNO"));
            msg.setFloor(rs.getString("FLOOR"));
            msg.setMessage(rs.getString("MESSAGE"));
            msg.setElectronicName(rs.getString("electronicName"));
            msg.setPictruePath(rs.getString("PICTRUEPATH"));
            msg.setMoviePath(rs.getString("MOVIEPATH"));
            msg.setAreaId(rs.getInt("shopId"));
            msg.setId(rs.getString("ID"));

            return msg;
        }
    }

    private class MessageMapper1 implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            ElectronicModel msg = new ElectronicModel();
            msg.setPlaceId(rs.getInt("PLACEID"));
            // msg.setShopName(rs.getString("SHOPNAME"));s
            msg.setFloorNo(rs.getBigDecimal("FLOORNO"));
            // msg.setFloor(rs.getString("FLOOR"));
            msg.setMessage(rs.getString("MESSAGE"));
            msg.setElectronicName(rs.getString("electronicName"));
            msg.setPictruePath(rs.getString("PICTRUEPATH"));
            msg.setMoviePath(rs.getString("MOVIEPATH"));
            msg.setAreaId(rs.getInt("shopId"));
            msg.setId(rs.getString("ID"));
            msg.setShopName(rs.getNString("electronicName"));
            msg.setxSpot(rs.getBigDecimal("XSPOT"));
            msg.setySpot(rs.getBigDecimal("YSPOT"));
            msg.setX1Spot(rs.getBigDecimal("X1SPOT"));
            msg.setY1Spot(rs.getBigDecimal("Y1SPOT"));
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
