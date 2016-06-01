package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.AccountModel;

@SuppressWarnings("all")
public class AccountDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 通过登陆名称查询出对应权限的商场
    public List<String> selectStore(Object userName) throws SQLException
    {
        String sql = "select r.storesid from account a left join role r on r.id = a.roleid where a.username = ?";
        Object[] params = {userName};
        return this.jdbcTemplate.queryForList(sql, params, String.class);
    }

    public List<String> selMenuKey(String username) throws SQLException
    {
        String sql = "select r.menukey from account a left join role r on r.id = a.roleid where a.username = ?";
        String[] params = {username};
        return this.jdbcTemplate.queryForList(sql, params, String.class);
    }

    // 查询所有配置
    public Collection<AccountModel> doquery()
    {
        String sql = "SELECT a.*,b.name FROM account a left join role b on a.roleid = b.id where a.id != 1 ";
        return this.jdbcTemplate.query(sql, new AccountMapper());
    }

    // 查询名称是否相同
    public int selectCategorySame(String name, int id) throws SQLException
    {
        String sql = "SELECT COUNT(*) FROM account WHERE name = ? and id !=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{name, id},
                Integer.class);
    }

    // 保存信息
    public void saveInfo(AccountModel sm) throws SQLException
    {
        String sql = "INSERT INTO account(username,password,roleid,updateTime) VALUES(?,?,?,?)";
        this.jdbcTemplate.update(sql, sm.getUsername(), sm.getPassword(),
                sm.getRoleId(), sm.getUpdateTime());
    }

    // 更新信息
    public void updateInfo(AccountModel sm) throws SQLException
    {
        String sql = "UPDATE account SET username = ?, password = ?,roleid = ?,updateTime = ? WHERE id=?";
        this.jdbcTemplate.update(sql, sm.getUsername(), sm.getPassword(),
                sm.getRoleId(), sm.getUpdateTime(), sm.getId());
    }

    // 删除信息
    public void deleteById(String id) throws SQLException
    {
        String sql = "DELETE from account WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }

    private class AccountMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            AccountModel sm = new AccountModel();
            sm.setId(rs.getInt("ID"));
            sm.setUsername(rs.getString("USERNAME"));
            sm.setPassword(rs.getString("PASSWORD"));
            sm.setRoleId(rs.getInt("roleid"));
            sm.setType(rs.getString("name"));
            sm.setUpdateTime(rs.getTimestamp("updateTime"));
            return sm;
        }
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<AccountModel> findUser(String username, String password)
    {
        String sql = "SELECT a.*,r.name FROM account a left join role r on  a.roleid = r.id  where username =binary? and password =binary?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {username, password};
        return this.jdbcTemplate.query(sql, params, new AccountMapper1());

    }

    private class AccountMapper1 implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            AccountModel user = new AccountModel();
            user.setUsername(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setRoleId(rs.getInt("roleid"));
            user.setUpdateTime(rs.getTimestamp("updateTime"));
            return user;
        }
    }

}
