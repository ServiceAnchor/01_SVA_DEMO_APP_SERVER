package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.BZPramesModel;

@SuppressWarnings("all")
public class BZPramesDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<BZPramesModel> doquery()
    {
        // String sql =
        // "SELECT a.*,b.floor,c.name place FROM BZPrames a left join maps b on a.floorNo = b.floorNo left join store c on a.placeId = c.id ";
        String sql = "select * from mwcprames";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new BZPramesMapper());
    }

    public void updateBZInfo(BZPramesModel mmm) throws SQLException
    {
        String sql = "REPLACE INTO mwcprames(densitySel1,radiusSel1,densitySel2,radiusSel2,densitySel3,radiusSel3,densitySel4,radiusSel4,densitySel5,radiusSel5,densitySel6,radiusSel6,densitySel7,radiusSel7,densitySel8,radiusSel8,floorNo1,floorNo2,floorNo3,floorNo4,floorNo5,floorNo6,floorNo7,floorNo8,periodSel,startTime,coefficient,id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, mmm.getDensitySel(), mmm.getRadiusSel(),
                mmm.getDensitySel1(), mmm.getRadiusSel1(),
                mmm.getDensitySel2(), mmm.getRadiusSel2(),
                mmm.getDensitySel3(), mmm.getRadiusSel3(),
                mmm.getDensitySel4(), mmm.getRadiusSel4(),
                mmm.getDensitySel5(), mmm.getRadiusSel5(),
                mmm.getDensitySel6(), mmm.getRadiusSel6(),
                mmm.getDensitySel7(), mmm.getRadiusSel7(), mmm.getFloorNo(),
                mmm.getFloorNo2(), mmm.getFloorNo3(), mmm.getFloorNo4(),
                mmm.getFloorNo5(), mmm.getFloorNo6(), mmm.getFloorNo7(),
                mmm.getFloorNo8(), mmm.getPeriodSel(), mmm.getStartTime(),
                mmm.getCoefficient(), mmm.getId());
    }

    private class BZPramesMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            BZPramesModel bzPramesModel = new BZPramesModel();
            bzPramesModel.setDensitySel(rs.getInt("densitySel1"));
            bzPramesModel.setRadiusSel(rs.getInt("radiusSel1"));
            bzPramesModel.setDensitySel1(rs.getInt("densitySel2"));
            bzPramesModel.setRadiusSel1(rs.getInt("radiusSel2"));
            bzPramesModel.setDensitySel2(rs.getInt("densitySel3"));
            bzPramesModel.setRadiusSel2(rs.getInt("radiusSel3"));
            bzPramesModel.setDensitySel3(rs.getInt("densitySel4"));
            bzPramesModel.setRadiusSel3(rs.getInt("radiusSel4"));
            bzPramesModel.setDensitySel4(rs.getInt("densitySel5"));
            bzPramesModel.setRadiusSel4(rs.getInt("radiusSel5"));
            bzPramesModel.setDensitySel5(rs.getInt("densitySel6"));
            bzPramesModel.setRadiusSel5(rs.getInt("radiusSel6"));
            bzPramesModel.setDensitySel6(rs.getInt("densitySel7"));
            bzPramesModel.setRadiusSel6(rs.getInt("radiusSel7"));
            bzPramesModel.setDensitySel7(rs.getInt("densitySel8"));
            bzPramesModel.setRadiusSel7(rs.getInt("radiusSel8"));
            bzPramesModel.setFloorNo(rs.getBigDecimal("FLOORNO1"));
            bzPramesModel.setFloorNo2(rs.getBigDecimal("FLOORNO2"));
            bzPramesModel.setFloorNo3(rs.getBigDecimal("FLOORNO3"));
            bzPramesModel.setFloorNo4(rs.getBigDecimal("FLOORNO4"));
            bzPramesModel.setFloorNo5(rs.getBigDecimal("FLOORNO5"));
            bzPramesModel.setFloorNo6(rs.getBigDecimal("FLOORNO6"));
            bzPramesModel.setFloorNo7(rs.getBigDecimal("FLOORNO7"));
            bzPramesModel.setFloorNo8(rs.getBigDecimal("FLOORNO8"));
            bzPramesModel.setPeriodSel(rs.getInt("periodSel"));
            bzPramesModel.setCoefficient(rs.getDouble("coefficient"));
            bzPramesModel.setStartTime(rs.getTimestamp("startTime"));
            String a = bzPramesModel.getStartTime().toString()
                    .substring(11, 19);
            bzPramesModel.setStartTimeEmp(a);
            bzPramesModel.setId(rs.getInt("ID"));

            return bzPramesModel;
        }
    }

    public List<Map<String, Object>> getBzData(String placeId)
    {
        String sql = "select floorNo,startTime from bzprames where placeId=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {placeId};
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> getBzAllData(String placeId)
    {
        String sql = "select floorNo,startTime,floorNo2,floorNo3,periodSel,coefficient from bzprames where id=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {placeId};
        return this.jdbcTemplate.queryForList(sql, params);
    }
    
    public List<Map<String, Object>> getAllFloorNo(String id)
    {
        String sql = "select floorNo1,startTime,floorNo2,floorNo3,floorNo4,floorNo5,floorNo6,floorNo7,floorNo8,periodSel,coefficient from mwcprames where id=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {id};
        return this.jdbcTemplate.queryForList(sql, params);
    }

}
