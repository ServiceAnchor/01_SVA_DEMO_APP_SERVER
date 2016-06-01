package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.CodeModel;
import com.sva.web.models.CodeMngModel;

@SuppressWarnings("all")
public class CodeDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<CodeModel> getData()
    {
        String sql = "SELECT * FROM code";
        return this.jdbcTemplate.query(sql, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                CodeModel user = new CodeModel();
                user.setName(rs.getString("NAME"));
                user.setPassword(rs.getString("PASSWORD"));
                return user;
            }
        });
    }

    public void saveCodeInfo(CodeMngModel cmm) throws SQLException
    {
        String sql = "INSERT INTO code (name,password) VALUES(?, ?)";
        this.jdbcTemplate.update(sql, cmm.getName(), cmm.getPassword());
    }

    public int deleteCode(String name, String password) throws SQLException
    {
        String sql = "DELETE FROM code where name = ? and password = ?";
        return this.jdbcTemplate.update(sql, name, password);
    }

    public int checkIsValid(String name, String password)
    {
        String sql = "SELECT count(*) res FROM code where name = ? and password = ?";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{name,
                password}, Integer.class);
    }

    public int checkByName(String name)
    {
        String sql = "SELECT count(*) res FROM code where name = ? ";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{name},
                Integer.class);
    }
}