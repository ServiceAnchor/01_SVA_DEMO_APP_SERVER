package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.AreaModel;
import com.sva.model.PhoneModel;

@SuppressWarnings("all")
public class PhoneDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void savePhone(PhoneModel phoneModel)
    {
        String sql = "replace into phonenumber(ip,phoneNumber,timestamp) values(?,?,?)";
        this.jdbcTemplate.update(sql, phoneModel.getIp(),
                phoneModel.getPhoneNumber(), phoneModel.getTimestamp());
    }

    public List<PhoneModel> getAllData()
    {
        String sql = "select * from phonenumber;";
        return this.jdbcTemplate.query(sql, new PhoneMapper());
    }

    private class PhoneMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            PhoneModel phone = new PhoneModel();
            phone.setIp(rs.getString("ip"));
            phone.setPhoneNumber(rs.getString("phoneNumber"));
            phone.setTimestamp(rs.getLong("timestamp"));

            return phone;
        }
    }

}
