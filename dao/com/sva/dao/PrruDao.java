package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.PrruModel;

@SuppressWarnings("all")
public class PrruDao
{
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<PrruModel> getPrruInfoByflooNo(String floorNo)
    {
        String sql = "SELECT * from pRRU where floorNo=?";
        String[] params = {floorNo};
        return this.jdbcTemplate.query(sql, params, new pRRUMapper());
    }

    public void saveInfo(PrruModel bm) throws SQLException
    {
        String sql = "INSERT INTO pRRU(floorNo,x,y,pRRUid,name,placeId,neCode) VALUES(?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, bm.getFloorNo(), bm.getX(), bm.getY(),
                bm.getNeId(), bm.getNeName(), bm.getPlaceId(), bm.getNeCode());
    }

    public void deleteInfo(String floorNo) throws SQLException
    {
        String sql = "delete from pRRU  where floorNo=?";
        this.jdbcTemplate.update(sql, floorNo);
    }

    public int checkByFloorNo(String floorNo, String id)
    {
        String sql = "SELECT count(*) res FROM pRRU where floorNo=? and floorNo != ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{floorNo, id},
                Integer.class);
    }

    public void updateInfo(String floorNo, String newfloorNo)
            throws SQLException
    {
        String sql = "update  pRRU set floorNo=? where floorNo=?";
        String[] params = {newfloorNo, floorNo};
        this.jdbcTemplate.update(sql, params);
    }

    public List<Map<String, Object>> getSignal()
    {
        String sql = "select * from prrusignal;";
        return this.jdbcTemplate.queryForList(sql);
    }

    private class pRRUMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            PrruModel bm = new PrruModel();
            bm.setFloorNo(rs.getString("FLOORNO"));
            bm.setNeCode(rs.getString("NECODE"));
            bm.setNeId(rs.getString("PRRUID"));
            bm.setNeName(rs.getString("NAME"));
            bm.setX(rs.getString("X"));
            bm.setY(rs.getString("Y"));
            bm.setPlaceId(rs.getInt("PLACEID"));
            return bm;
        }
    }

}
