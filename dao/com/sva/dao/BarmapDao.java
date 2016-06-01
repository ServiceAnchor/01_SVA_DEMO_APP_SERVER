package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.BarmapModel;

@SuppressWarnings("all")
public class BarmapDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<BarmapModel> getData(String placeId, String start,
            String end)
    {
        String sql = "select a.z,b.floor, count(distinct a.userID) number from statisticfloor a join maps b on a.z = b.floorNo "
                + "where b.placeId = ? and a.time < ? and a.time >= ? group by a.z order by a.z asc ";
        return jdbcTemplate.query(sql, new Object[]{placeId, end, start},
                new BarmapMapper());
    }

    public int getTotalCount(String placeId, String start, String end)
    {
        String sql = "select count(distinct a.userID) number from statisticfloor a join maps b on a.z = b.floorNo "
                + "where b.placeId = ? and a.time < ? and a.time >= ? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{placeId, end,
                start,}, Integer.class);
    }

    private class BarmapMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            BarmapModel pcm = new BarmapModel();
            pcm.setZ(rs.getBigDecimal("Z"));
            pcm.setFloor(rs.getString("FLOOR"));
            pcm.setNumber(rs.getInt("NUMBER"));

            return pcm;
        }
    }
}
