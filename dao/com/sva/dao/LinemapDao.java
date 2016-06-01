package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.LinemapModel;

@SuppressWarnings("all")
public class LinemapDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<LinemapModel> getDataByDay(String placeId, String start,
            String end)
    {
        String sql = "select number, time from statisticday where time < ? and time >= ? "
                + "and placeId=? order by time asc ";
        return jdbcTemplate.query(sql, new Object[]{end, start, placeId},
                new LinemapMapper());
    }

    public Collection<LinemapModel> getDataByHour(String placeId, String start,
            String end)
    {
        String sql = "select number, time from statistichour where time < ? and time >= ? "
                + "and placeId= ? order by time asc ";
        return jdbcTemplate.query(sql, new Object[]{end, start, placeId},
                new LinemapMapper());
    }

    public int getTotalCount(String placeId, String start, String end)
    {
        String sql = "select count(distinct a.userID) number from statisticfloor a join maps b on a.z = b.floorNo "
                + "where b.placeId = ? and a.time < ? and a.time >= ? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{placeId, end,
                start}, Integer.class);
    }

    public Collection<LinemapModel> getMaxTime(String placeId, String start,
            String end)
    {
        // String sql =
        // "select distinct(time) time, 0 number from statistichour a join "
        // +
        // "(select max(number) number from statistichour where time < ? and time >= ? and place= ?) b "
        // + "on a.number = b.number ";
        String sql = "select distinct(time) time, 0 number from statistichour where number = "
                + "(select max(number) number from statistichour "
                + "where time < ? and time >= ? and placeId= ?) and "
                + "time < ? and time >= ? and placeId= ?";

        return jdbcTemplate.query(sql, new Object[]{end, start, placeId, end,
                start, placeId}, new LinemapMapper());
    }

    public Collection<LinemapModel> getMaxDay(String placeId, String start,
            String end)
    {
        // String sql =
        // "select distinct(time) time, 0 number from statisticday a join "
        // +
        // "(select max(number) number from statisticday where time < ? and time >= ? and place= ?) b "
        // + "on a.number = b.number ";

        String sql = "select distinct(time) time, 0 number from statisticday where number = "
                + "(select max(number) number from statisticday "
                + "where time < ? and time >= ? and placeId= ?) and "
                + "time < ? and time >= ? and placeId= ?";

        return jdbcTemplate.query(sql, new Object[]{end, start, placeId, end,
                start, placeId}, new LinemapMapper());
    }

    private class LinemapMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            LinemapModel pcm = new LinemapModel();
            pcm.setTime(rs.getString("TIME"));
            pcm.setNumber(rs.getInt("NUMBER"));

            return pcm;
        }
    }

    public int getAreaNumberByDay(String areaId)
    {
        String sql = "select count(distinct userID) t from district_during where district_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{areaId},
                Integer.class);
    }
    
    public int getAreaNumberByHour(String areaId,long time)
    {
        String sql = "select count(distinct userID) t from district_during where district_id = ? and timestamp >?";
        return jdbcTemplate.queryForObject(sql, new Object[]{areaId,time},
                Integer.class);
    }
}
