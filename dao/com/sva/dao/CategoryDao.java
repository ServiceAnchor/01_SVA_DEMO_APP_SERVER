package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.CategoryModel;

@SuppressWarnings("all")
public class CategoryDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 查询所有配置
    public Collection<CategoryModel> doquery()
    {
        String sql = "SELECT * FROM category";
        return this.jdbcTemplate.query(sql, new CategoryMapper());
    }

    // 查询类别名称是否相同
    public int selectCategorySame(String name, int id) throws SQLException
    {
        String sql = "SELECT COUNT(*) FROM category WHERE name = ? and id !=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{name, id},
                Integer.class);
    }

    // 保存信息
    public void saveInfo(CategoryModel sm) throws SQLException
    {
        String sql = "INSERT INTO category(name,updateTime,createTime) VALUES(?,?,?)";
        this.jdbcTemplate.update(sql, sm.getName(), sm.getUpdateTime(),
                sm.getCreateTime());
    }

    // 更新信息
    public void updateInfo(CategoryModel sm) throws SQLException
    {
        String sql = "UPDATE category SET name=?, updateTime=? WHERE id=?";
        this.jdbcTemplate.update(sql, sm.getName(), sm.getUpdateTime(),
                sm.getId());
    }

    // 删除信息
    public void deleteById(String id) throws SQLException
    {
        String sql = "DELETE from category WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }

    private class CategoryMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            CategoryModel sm = new CategoryModel();
            sm.setId(rs.getInt("ID"));
            sm.setName(rs.getString("NAME"));
            sm.setUpdateTime(rs.getTimestamp("UPDATETIME"));
            sm.setCreateTime(rs.getTimestamp("CREATETIME"));

            return sm;
        }
    }
}
