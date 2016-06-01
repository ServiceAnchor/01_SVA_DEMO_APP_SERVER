package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.AccuracyModel;
import com.sva.web.models.AccuracyApiModel;

@SuppressWarnings("all")
public class AccuracyDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<AccuracyModel> getData(int start, int length,
            String sortCol, String sortDir)
    {
        String sql = "select a.*,b.floor,c.deviation, d.name place from accuracy a "
                + "left join maps b on a.floorNo = b.floorNo "
                + "left join store d on a.placeId = d.id "
                + "left join estimatedev c on b.floorNo = c.floorNo "
                + " order by " + sortCol + ' ' + sortDir + " limit ?,?";
        return jdbcTemplate.query(sql, new Object[]{start, length},
                new AccuracyMapper());
    }

    /*
     * 通过用户名获取相对应的商场权限
     */
    public Collection<AccuracyModel> getDataByStoreid(int start, int length,
            String sortCol, String sortDir, int storeid)
    {
        String sql = "select a.*,b.floor,c.deviation, d.name place from accuracy a "
                + "left join maps b on a.floorNo = b.floorNo "
                + "left join store d on a.placeId = d.id "
                + "left join estimatedev c on b.floorNo = c.floorNo "
                + " order by "
                + sortCol
                + ' '
                + sortDir
                + "where a.placeId = "
                + storeid + " limit ?,?";
        return jdbcTemplate.query(sql, new Object[]{start, length},
                new AccuracyMapper());
    }

    public int getDataLength()
    {
        String sql = "select count(*) a from accuracy";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int saveTestInfo(AccuracyApiModel aam) throws SQLException
    {
        String sql = "INSERT INTO accuracy(placeId,floorNo,origin,destination,start_date,end_date,triggerIp,offset,variance,count_3,count_5,count_10,count_10p,detail,type,averDevi) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return this.jdbcTemplate.update(sql, aam.getPlaceId(),
                aam.getFloorNo(), aam.getOrigin(), aam.getDestination(),
                aam.getStartdate(), aam.getEnddate(), aam.getTriggerIp(),
                aam.getOffset(), aam.getVariance(), aam.getCount3(),
                aam.getCount5(), aam.getCount10(), aam.getCount10p(),
                aam.getDetail(), aam.getType(), aam.getAverDevi());
    }

    // 查询所有的信息
    public List<AccuracyModel> allQueryAccuracy()
    {
        String sql = "select a.*,b.name place,d.floor,c.deviation from accuracy a "
                + "left join store b on a.placeId = b.id "
                + "left join maps d on a.floorNo = d.floorNo "
                + "left join estimatedev c on a.floorNo = c.floorNo";
        return this.jdbcTemplate.query(sql, new AccuracyMapper());
    }

    private class AccuracyMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            AccuracyModel am = new AccuracyModel();
            am.setId(rs.getInt("ID"));
            am.setPlace(rs.getString("PLACE"));
            am.setPlaceId(rs.getInt("PLACEID"));
            am.setFloor(rs.getString("FLOOR"));
            am.setFloorNo(rs.getBigDecimal("FLOORNO"));
            am.setTriggerIp(rs.getString("TRIGGERIP"));
            am.setOrigin(rs.getString("ORIGIN"));
            am.setDestination(rs.getString("DESTINATION"));
            am.setStartdate(rs.getTimestamp("START_DATE"));
            am.setEnddate(rs.getTimestamp("END_DATE"));
            am.setOffset(rs.getBigDecimal("OFFSET"));
            am.setVariance(rs.getBigDecimal("VARIANCE"));
            am.setDeviation(rs.getBigDecimal("DEVIATION"));
            am.setCount3(rs.getInt("COUNT_3"));
            am.setCount5(rs.getInt("COUNT_5"));
            am.setCount10(rs.getInt("COUNT_10"));
            am.setCount10p(rs.getInt("COUNT_10P"));
            am.setDetail(rs.getString("DETAIL"));
            am.setType(rs.getString("TYPE"));
            am.setAverDevi(rs.getBigDecimal("AVERDEVI"));

            return am;
        }
    }

}
