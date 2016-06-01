package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.BluemixModel;

@SuppressWarnings("all")
public class BluemixDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 查询所有配置
    public Collection<BluemixModel> doquery()
    {
        String sql = "SELECT * FROM bluemix where status <> 2";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new BluemixMapper());
    }

    // 查询所有配置
    public Collection<BluemixModel> queryAllStatus()
    {
        String sql = "SELECT * FROM bluemix";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new BluemixMapper());
    }

    // 保存信息
    public void saveInfo(BluemixModel bm) throws SQLException
    {
        String sql = "INSERT INTO bluemix(ip,svaUser,svaPassword,url,site,ibmUser,ibmPassword,status,updateTime,createTime,tokenPort,brokerPort) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, bm.getIp(), bm.getSvaUser(),
                bm.getSvaPassword(), bm.getUrl(), bm.getSite(),
                bm.getIbmUser(), bm.getIbmPassword(), bm.getStatus(),
                bm.getUpdateTime(), bm.getCreateTime(), bm.getTokenProt(),
                bm.getBrokerProt());
    }

    public void updateInfo(BluemixModel bm) throws SQLException
    {
        String sql = "UPDATE bluemix SET ip=?, svaUser=?, svaPassword=?, url=?, site=?, ibmUser=?, ibmPassword=?, updateTime=?,tokenPort=?,brokerPort=? WHERE id=?";
        this.jdbcTemplate.update(sql, bm.getIp(), bm.getSvaUser(),
                bm.getSvaPassword(), bm.getUrl(), bm.getSite(),
                bm.getIbmUser(), bm.getIbmPassword(), bm.getUpdateTime(),
                bm.getTokenProt(), bm.getBrokerProt(), bm.getId());
    }

    // 删除信息
    public void deleteInfo(String id) throws SQLException
    {
        String sql = "UPDATE bluemix SET status=2 WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }

    // 删除信息
    public void deleteById(String id) throws SQLException
    {
        String sql = "DELETE from bluemix WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }

    // 改变状态
    public void changeStatus(String id, String status) throws SQLException
    {
        String sql = "UPDATE bluemix SET status=? WHERE id=?";
        this.jdbcTemplate.update(sql, status, id);
    }

    private class BluemixMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            BluemixModel bm = new BluemixModel();
            bm.setId(rs.getInt("ID"));
            bm.setIp(rs.getString("IP"));
            bm.setSvaUser(rs.getString("SVAUSER"));
            bm.setSvaPassword(rs.getString("SVAPASSWORD"));
            bm.setUrl(rs.getString("URL"));
            bm.setSite(rs.getString("SITE"));
            bm.setIbmUser(rs.getString("IBMUSER"));
            bm.setIbmPassword(rs.getString("IBMPASSWORD"));
            bm.setStatus(rs.getInt("STATUS"));
            bm.setUpdateTime(rs.getTimestamp("UPDATETIME"));
            bm.setCreateTime(rs.getTimestamp("CREATETIME"));
            bm.setBrokerProt(rs.getString("BROKERPORT"));
            bm.setTokenProt(rs.getString("TOKENPORT"));

            return bm;
        }
    }
}
