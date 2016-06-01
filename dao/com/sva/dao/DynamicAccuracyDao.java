package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.AccuracyModel;
import com.sva.model.DynamicAccuracyModel;
import com.sva.model.StaticAccuracyModel;
import com.sva.web.models.AccuracyApiModel;
import com.sva.web.models.DynamicAccuracyApiModel;

@SuppressWarnings("all")
public class DynamicAccuracyDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<DynamicAccuracyModel> getData(int start, int length,
            String sortCol, String sortDir)
    {
        String sql = "select a.*,b.floor,c.deviation, d.name place from dynamicaccuracy a "
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
    public Collection<DynamicAccuracyModel> getStaticDataByStoreid(int start,
            int length, String sortCol, String sortDir, int storeid)
    {
        String sql = "select a.*,b.floor,c.deviation, d.name place from dynamicaccuracy a "
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
        String sql = "select count(*) a from dynamicaccuracy";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int dynamicSaveTestInfo(DynamicAccuracyApiModel aam)
            throws SQLException
    {
        String sql = "INSERT INTO dynamicaccuracy(triggerIp,placeId,floorNo,origin,destination,start_date,end_date,avgeOffset,maxOffset,offset,count_3,count_5,count_10,count_10p,detail) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return this.jdbcTemplate.update(sql, aam.getTriggerIp(),
                aam.getPlaceId(), aam.getFloorNo(), aam.getOrigin(),
                aam.getDestination(), aam.getStartdate(), aam.getEnddate(),
                aam.getAvgeOffset(), aam.getMaxOffset(), aam.getOffset(),
                aam.getCount3(), aam.getCount5(), aam.getCount10(),
                aam.getCount10p(), aam.getDetail());
    }

    // 查询所有的信息
    public List<DynamicAccuracyModel> allQueryDynamicAccuracy()
    {
        String sql = "select a.*,b.name place,d.floor,c.deviation from dynamicaccuracy a "
                + "left join store b on a.placeId = b.id "
                + "left join maps d on a.floorNo = d.floorNo "
                + "left join estimatedev c on a.floorNo = c.floorNo";
        return this.jdbcTemplate.query(sql, new AccuracyMapper());
    }

    private class AccuracyMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            DynamicAccuracyModel am = new DynamicAccuracyModel();
            am.setId(rs.getInt("ID"));
            am.setPlace(rs.getString("PLACE"));
            am.setPlaceId(rs.getInt("PLACEID"));
            am.setFloor(rs.getString("FLOOR"));
            am.setFloorNo(rs.getBigDecimal("FLOORNO"));
            am.setOrigin(rs.getString("ORIGIN"));
            am.setDestination(rs.getString("DESTINATION"));
            am.setStartdate(rs.getTimestamp("START_DATE"));
            am.setEnddate(rs.getTimestamp("END_DATE"));
            am.setAvgeOffset(rs.getBigDecimal("avgeOffset"));
            am.setMaxOffset(rs.getBigDecimal("maxOffset"));
            am.setOffset(rs.getBigDecimal("offset"));
            am.setCount3(rs.getInt("COUNT_3"));
            am.setCount5(rs.getInt("COUNT_5"));
            am.setCount10(rs.getInt("COUNT_10"));
            am.setCount10p(rs.getInt("COUNT_10P"));
            am.setDetail(rs.getString("DETAIL"));
            am.setTriggerIp(rs.getString("TRIGGERIP"));
            return am;
        }
    }

}
