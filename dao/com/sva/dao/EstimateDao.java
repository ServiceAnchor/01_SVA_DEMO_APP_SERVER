package com.sva.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.EstimateModel;

@SuppressWarnings("all")
public class EstimateDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<EstimateModel> doquery()
    {
        String sql = "SELECT a.*,b.floor,c.name place FROM estimatedev a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id;";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new EstimateMapper());
    }

    /*
     * 通过用户名获取相对应的商场权限
     */
    public Collection<EstimateModel> doqueryByStoreid(int storeid)
    {
        String sql = "SELECT a.*,b.floor,c.name place FROM estimatedev a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id where a.placeId = "
                + storeid;
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new EstimateMapper());
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public BigDecimal getEstimate(String floorNo)
    {
        String sql = "SELECT deviation FROM estimatedev where floorNo = ?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.queryForObject(sql, new Object[]{floorNo},
                BigDecimal.class);
    }

    public void saveInfo(EstimateModel em) throws SQLException
    {
        String sql = "INSERT INTO estimatedev(placeId,floorNo,a,b,n,type,d,deviation) VALUES(?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, em.getPlaceId(), em.getFloorNo(),
                em.getA(), em.getB(), em.getN(), em.getType(), em.getD(),
                em.getDeviation());
    }

    public int deleteInfo(String id) throws SQLException
    {
        String sql = "DELETE FROM estimatedev where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }

    public int updateInfo(EstimateModel em) throws SQLException
    {
        String sql = "UPDATE estimatedev SET placeId=?,floorNo=?,a=?,b=?,n=?,type=?,d=?,deviation=? where id=?";
        return this.jdbcTemplate.update(sql, em.getPlaceId(), em.getFloorNo(),
                em.getA(), em.getB(), em.getN(), em.getType(), em.getD(),
                em.getDeviation(), em.getId());
    }

    private class EstimateMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            EstimateModel es = new EstimateModel();
            es.setId(rs.getString("ID"));
            es.setA(rs.getBigDecimal("A"));
            es.setB(rs.getBigDecimal("B"));
            es.setD(rs.getBigDecimal("D"));
            es.setDeviation(rs.getBigDecimal("DEVIATION"));
            es.setType(rs.getBigDecimal("TYPE"));
            es.setFloorNo(rs.getBigDecimal("FLOORNO"));
            es.setFloor(rs.getString("FLOOR"));
            es.setN(rs.getInt("N"));
            es.setPlace(rs.getString("PLACE"));
            es.setPlaceId(rs.getInt("PLACEID"));
            return es;
        }
    }

    public Collection<EstimateModel> selectID1(BigDecimal floorNo, String id)
            throws SQLException
    {
        String sql = "SELECT * FROM estimatedev WHERE floorNo=? and id !=?";
        return this.jdbcTemplate.query(sql, new Object[]{floorNo, id},
                new EstimateMapper());
    }

    public String getFloorByFloorNo(String floorNo)
    {
        String sql = "SELECT floor FROM maps where floorNo=? limit 1";
        String[] params = {floorNo};
        return this.jdbcTemplate.queryForObject(sql, params, String.class);
    }

    public int checkByFloorNo(String floorNo)
    {
        String sql = "SELECT count(*) res FROM  estimatedev where floorNo=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{floorNo},
                Integer.class);
    }

    public int checkByFloorNo1(String floorNo, String id)
    {
        String sql = "SELECT count(*) res FROM  estimatedev where floorNo=? and id !=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{floorNo, id},
                Integer.class);
    }
}
