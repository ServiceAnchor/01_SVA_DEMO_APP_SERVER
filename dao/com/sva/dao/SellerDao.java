package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.SellerModel;
import com.sva.web.models.SellerMngModel;

@SuppressWarnings("all")
public class SellerDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<SellerModel> doquery()
    {
        String sql = "SELECT a.*,b.floor,c.name place FROM seller a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id;";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new SellerMapper());
    }

    /*
     * 通过用户名获取相对应的商场权限
     */
    public Collection<SellerModel> doqueryByStoreid(int storeid)
    {
        String sql = "SELECT a.*,b.floor,c.name place FROM seller a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id where a.placeId ="
                + storeid;
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new SellerMapper());
    }

    public Collection<SellerModel> getInfoByFloorNo(String floorNo)
    {
        String sql = "SELECT a.*,b.floor,0 place FROM seller a join maps b on a.floorNo = b.floorNo where b.floorNo = ? and isEnable = '开启'";

        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new Object[]{floorNo},
                new SellerMapper());
    }

    public Collection<SellerModel> getVipByFloorNo(String floorNo)
    {
        String sql = "SELECT a.*,b.floor,0 place FROM seller a join maps b on a.floorNo = b.floorNo where b.floorNo = ? and isEnable = '开启' and isVip='是'";

        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new Object[]{floorNo},
                new SellerMapper());
    }

    public void saveSellerInfo(SellerMngModel smm) throws SQLException
    {
        String sql = "INSERT INTO seller(placeId,floorNo,xSpot,ySpot,info,isEnable,pictruePath,moviePath,isVip) VALUES(?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, smm.getPlaceId(), smm.getFloorNo(),
                smm.getxSpot(), smm.getySpot(), smm.getInfo(),
                smm.getIsEnable(), smm.getPictruePath(), smm.getMoviePath(),
                smm.getIsVip());
    }

    public int deleteSeller(String id) throws SQLException
    {
        String sql = "DELETE FROM seller where id = ?;";
        return this.jdbcTemplate.update(sql, id);
    }

    private class SellerMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            SellerModel sm = new SellerModel();
            sm.setPlace(rs.getString("PLACE"));
            sm.setPlaceId(rs.getInt("PLACEID"));
            sm.setFloor(rs.getString("FLOOR"));
            sm.setxSpot(rs.getBigDecimal("XSPOT"));
            sm.setySpot(rs.getBigDecimal("YSPOT"));
            sm.setInfo(rs.getString("INFO"));
            sm.setIsEnable(rs.getString("ISENABLE"));
            sm.setPictruePath(rs.getString("PICTRUEPATH"));
            sm.setMoviePath(rs.getString("MOVIEPATH"));
            sm.setIsVip(rs.getString("ISVIP"));
            sm.setId(rs.getString("ID"));
            sm.setFloorNo(rs.getString("FLOORNO"));
            return sm;
        }
    }

    public void updateSeller(SellerMngModel smm) throws SQLException
    {
        String sql = "UPDATE SELLER SET placeId=?,xSpot=?,ySpot=?,info=?,isEnable=?,isVip=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, smm.getPlaceId(), smm.getxSpot(),
                smm.getySpot(), smm.getInfo(), smm.getIsEnable(),
                smm.getIsVip(), smm.getFloorNo(), smm.getId());
    }

    public void updateSeller1(SellerMngModel smm) throws SQLException
    {
        String sql = "UPDATE SELLER SET placeId=?,xSpot=?,ySpot=?,info=?,isEnable=?,moviePath=?,isVip=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, smm.getPlaceId(), smm.getxSpot(),
                smm.getySpot(), smm.getInfo(), smm.getIsEnable(),
                smm.getMoviePath(), smm.getIsVip(), smm.getFloorNo(),
                smm.getId());
    }

    public void updateSeller2(SellerMngModel smm) throws SQLException
    {
        String sql = "UPDATE SELLER SET placeId=?,xSpot=?,ySpot=?,info=?,isEnable=?,pictruePath=?,isVip=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, smm.getPlaceId(), smm.getxSpot(),
                smm.getySpot(), smm.getInfo(), smm.getIsEnable(),
                smm.getPictruePath(), smm.getIsVip(), smm.getFloorNo(),
                smm.getId());
    }

    public void updateSeller3(SellerMngModel smm) throws SQLException
    {
        String sql = "UPDATE SELLER SET placeId=?,xSpot=?,ySpot=?,info=?,isEnable=?,pictruePath=?,moviePath=?,isVip=?,floorNo=? where id=?";
        this.jdbcTemplate.update(sql, smm.getPlaceId(), smm.getxSpot(),
                smm.getySpot(), smm.getInfo(), smm.getIsEnable(),
                smm.getPictruePath(), smm.getMoviePath(), smm.getIsVip(),
                smm.getFloorNo(), smm.getId());
    }

    public String getFloorByFloorNo(String floorNo)
    {
        String sql = "SELECT floor FROM maps where floorNo=? limit 1";
        String[] params = {floorNo};
        return this.jdbcTemplate.queryForObject(sql, params, String.class);
    }
}
