package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.ParamModel;

@SuppressWarnings("all")
public class ParamDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 查询所有配置
    public Collection<ParamModel> doquery()
    {
        String sql = "SELECT * FROM param";
        return this.jdbcTemplate.query(sql, new ParamMapper());
    }

    // 查询参数更新时间，用于判断是时间是都变化
    public String paramUpdateTime()
    {
        String sql = "select updateTime from param ";
        return this.jdbcTemplate.queryForObject(sql, String.class);
    }

    // 更新信息
    // public void updateParam(ParamModel sm) throws SQLException
    // {
    // String sql =
    // "UPDATE param SET errorAngle=?,positioningError=?,isEnable=?,gaussVariance=?,filterNumber=?, banThreshold=?, weight=?,maxDeviation=?,exceedMaxDeviation=?,biasSpeed=?,SPR=?,updateTime=? WHERE id=?";
    // this.jdbcTemplate.update(sql, sm.getErrorAngle(),
    // sm.getPositioningError(), sm.getIsEnable(),
    // sm.getGaussVariance(), sm.getFilterNumber(),
    // sm.getBanThreshold(), sm.getWeight(), sm.getMaxDeviation(),
    // sm.getExceedMaxDeviation(), sm.getBiasSpeed(), sm.getSPR(),
    // sm.getUpdateTime(), sm.getId());
    // }

    public void saveData(ParamModel sm) throws SQLException
    {
        String sql = "REPLACE INTO param( banThreshold,filterLength,horizontalWeight,ongitudinalWeight,maxDeviation, exceedMaxDeviation, correctMapDirection,restTimes,filterPeakError,updateTime,peakWidth,step,id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, sm.getBanThreshold(),
                sm.getFilterLength(), sm.getHorizontalWeight(),
                sm.getOngitudinalWeight(), sm.getMaxDeviation(),
                sm.getExceedMaxDeviation(), sm.getCorrectMapDirection(),
                sm.getRestTimes(), sm.getFilterPeakError(), sm.getUpdateTime(),
                sm.getPeakWidth(), sm.getStep(), sm.getId());
    }

    private class ParamMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            ParamModel sm = new ParamModel();
            sm.setId(rs.getInt("ID"));
            sm.setBanThreshold(rs.getDouble("banThreshold"));
            sm.setFilterLength(rs.getDouble("filterLength"));
            sm.setHorizontalWeight(rs.getDouble("horizontalWeight"));
            sm.setOngitudinalWeight(rs.getDouble("ongitudinalWeight"));
            sm.setMaxDeviation(rs.getDouble("maxDeviation"));
            sm.setExceedMaxDeviation(rs.getDouble("exceedMaxDeviation"));
            sm.setUpdateTime(rs.getLong("updateTime"));
            sm.setCorrectMapDirection(rs.getDouble("correctMapDirection"));
            sm.setRestTimes(rs.getDouble("restTimes"));
            sm.setFilterPeakError(rs.getDouble("filterPeakError"));
            sm.setPeakWidth(rs.getDouble("peakWidth"));
            sm.setStep(rs.getDouble("step"));
            return sm;
        }
    }
}
