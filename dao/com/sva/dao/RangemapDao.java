package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.common.ConvertUtil;
import com.sva.model.LinemapModel;

@SuppressWarnings("all")
public class RangemapDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<LinemapModel> getData(String placeId, String floorNo,
            String time, String x1, String y1, String x2, String y2)
    {
        Date d = ConvertUtil.dateStringFormat(time, "yyyy-MM-dd");
        String dStr = ConvertUtil.dateFormat(d, "yyyyMMdd");
        String table = "location" + dStr;
        String sql = "SELECT FROM_UNIXTIME(timestamp/1000,'%Y/%m/%d %H:00:00') time, count(distinct a.userID) number "
                + "FROM "
                + table
                + " a join maps b on a.z = b.floorNo "
                + "where b.placeId = ? and b.floorNo = ? and a.x > ? and a.x <? and a.y >? and a.y<? "
                + "GROUP BY time order by time asc ";
        return jdbcTemplate.query(sql, new Object[]{placeId, floorNo, x1, x2,
                y1, y2}, new RangemapMapper());
    }

    public int getTotalCount(String placeId, String floorNo, String time,
            String x1, String y1, String x2, String y2)
    {
        Date d = ConvertUtil.dateStringFormat(time, "yyyy-MM-dd");
        String dStr = ConvertUtil.dateFormat(d, "yyyyMMdd");
        String table = "location" + dStr;
        String sql = "SELECT count(distinct a.userID) number "
                + "FROM "
                + table
                + " a join maps b on a.z = b.floorNo "
                + "where b.placeId = ? and b.floorNo = ? and a.x > ? and a.x <? and a.y >? and a.y<? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{placeId, floorNo,
                x1, x2, y1, y2}, Integer.class);
    }

    public List<Map<String, Object>> getDataForArea(String areaId,
            String startTime, String endTime)
    {
        String sql = "SELECT time, number FROM statisticarea "
                + "where areaId = ? and time >= ? and time <= ? order by time asc ";
        return jdbcTemplate.queryForList(sql, new Object[]{areaId, startTime,
                endTime});
    }

    public List<Map<String, Object>> getDataForAreaByDay(String areaId,
            String startTime, String endTime)
    {
        String sql = "SELECT time, number FROM statisticareaDay "
                + "where areaId = ? and time >= ? and time <= ? order by time asc ";
        return jdbcTemplate.queryForList(sql, new Object[]{areaId, startTime,
                endTime});
    }

    public List<Map<String, Object>> getAreaName()
    {
        String sql = "select areaName,id from area ";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.queryForList(sql);
    }

    private class RangemapMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            LinemapModel pcm = new LinemapModel();
            pcm.setTime(rs.getString("Time"));
            pcm.setNumber(rs.getInt("NUMBER"));

            return pcm;
        }
    }

    public String getNameById(String areaId)

    {
        String sql = "SELECT areaName FROM area where  id=? limit 1";
        String[] params = {areaId};
        return this.jdbcTemplate.queryForObject(sql, params, String.class);

    }

    public List<Map<String, Object>> getVisitData(String areaId,
            String startTime, String endTime)

    {
        String sql = "SELECT time,averageTime,number FROM staticVisit where areaId=? AND time>=? AND time<=?";
        String[] params = {areaId, startTime, endTime};
        return this.jdbcTemplate.queryForList(sql, params);

    }
}
