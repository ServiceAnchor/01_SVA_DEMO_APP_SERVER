package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.StoreModel;

@SuppressWarnings("all")
public class StoreDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<StoreModel> doquery(String storeid)
    {

        String sql = "SELECT * FROM store where id =" + storeid;
        return this.jdbcTemplate.query(sql, new StoreMapper());
    }

    // 查询所有配置
    public Collection<StoreModel> doStores()
    {

        String sql = "SELECT * FROM store";
        return this.jdbcTemplate.query(sql, new StoreMapper());
    }

    // 查询所有配置
    public List<String> getStoreNameBySva(int svaId)
    {
        String sql = "SELECT b.name FROM svastoremap a left join store b on a.storeId = b.id where a.svaId = ?;";
        return this.jdbcTemplate.queryForList(sql, new Object[]{svaId},
                String.class);
    }

    // 保存信息
    public void saveInfo(StoreModel sm) throws SQLException
    {
        String sql = "INSERT INTO store(name,updateTime,createTime) VALUES(?,?,?)";
        this.jdbcTemplate.update(sql, sm.getName(), sm.getUpdateTime(),
                sm.getCreateTime());
    }

    public int selectStoreid(String name) throws SQLException
    {
        String sql = "select id from store where name = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{name},
                Integer.class);
    }

    // 查询商场名称是否相同
    public int selectStoreSame(String name, int id) throws SQLException
    {
        String sql = "SELECT COUNT(*) FROM store WHERE name = ? and id !=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{name, id},
                Integer.class);
    }

    public int selectStoreSame1(String name) throws SQLException
    {
        String sql = "SELECT COUNT(*) FROM store WHERE name = ? ";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{name},
                Integer.class);
    }

    // 更新信息
    public void updateInfo(StoreModel sm) throws SQLException
    {
        String sql = "UPDATE store SET name=?, updateTime=? WHERE id=?";
        this.jdbcTemplate.update(sql, sm.getName(), sm.getUpdateTime(),
                sm.getId());
    }

    // 删除信息
    public void deleteById(String id) throws SQLException
    {
        String sql = "DELETE from store WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }

    private class StoreMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            StoreModel sm = new StoreModel();
            sm.setId(rs.getInt("ID"));
            sm.setName(rs.getString("NAME"));
            sm.setUpdateTime(rs.getTimestamp("UPDATETIME"));
            sm.setCreateTime(rs.getTimestamp("CREATETIME"));

            return sm;
        }
    }
}
