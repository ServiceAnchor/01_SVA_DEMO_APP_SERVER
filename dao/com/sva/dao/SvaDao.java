package com.sva.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.sva.model.SvaModel;

@SuppressWarnings("all")
public class SvaDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 查询所有sva
    public Collection<SvaModel> doquery()
    {
        String sql = "SELECT * FROM svalist where status != 2";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new SvaMapper());
    }

    // 根据floorNo查询所有sva
    public Collection<SvaModel> queryByFloorNo(String floorNo)
    {
        String sql = "SELECT * FROM svalist a "
                + "join svastoremap b on a.id = b.svaId "
                + "join store c on b.storeId = c.id "
                + "join maps d on c.id = d.placeId where c.floorNo = ? and a.status != 2";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new Object[]{floorNo},
                new SvaMapper());
    }

    // 查询所有sva
    public Collection<SvaModel> queryActive()
    {
        String sql = "SELECT * FROM svalist where status = 1 and type <> 0 ";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new SvaMapper());
    }

    // 通过svaid查询出store的id
    public List<String> storeBySva(String svaid)
    {
        String sql = "select name from store WHERE id IN(select storeid from svastoremap where svaid = ?)";
        return this.jdbcTemplate.queryForList(sql, new Object[]{svaid},
                String.class);
    }

    // 通过svaid查询出store的id
    public String storeIdByName(String storeName)
    {
        String sql = "select id from store WHERE name = ?";
        String[] params = {storeName};
        return this.jdbcTemplate.queryForObject(sql, params, String.class);
    }

    // 查询所有sva
    public Collection<SvaModel> doqueryByAll()
    {
        String sql = "SELECT * FROM svalist a left join svastoremap b on a.id = b.svaId";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new RowMapper()
        {

            public Object mapRow(ResultSet rs, int num) throws SQLException
            {
                SvaModel sva = new SvaModel();
                sva.setId(rs.getString("ID"));
                sva.setIp(rs.getString("IP"));
                sva.setName(rs.getString("NAME"));
                sva.setUsername(rs.getString("USERNAME"));
                sva.setPassword(rs.getString("PASSWORD"));
                sva.setStatus(rs.getInt("STATUS"));
                sva.setStoreId(rs.getString("storeId"));
                sva.setType(rs.getInt("TYPE"));
                sva.setTokenProt(rs.getString("tokenPort"));
                sva.setBrokerProt(rs.getString("brokerPort"));
                return sva;
            }
        });
    }

    // 查询所有sva
    public Collection<SvaModel> queryByStoreId(String storeId)
    {
        String sql = "SELECT * FROM svalist a left join svastoremap b on a.id = b.svaId where a.status = 1 and a.type <> 0 and b.storeId=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new Object[]{storeId},
                new RowMapper()
                {

                    public Object mapRow(ResultSet rs, int num)
                            throws SQLException
                    {
                        SvaModel sva = new SvaModel();
                        sva.setId(rs.getString("ID"));
                        sva.setIp(rs.getString("IP"));
                        sva.setName(rs.getString("NAME"));
                        sva.setUsername(rs.getString("USERNAME"));
                        sva.setPassword(rs.getString("PASSWORD"));
                        sva.setStatus(rs.getInt("STATUS"));
                        sva.setStoreId(rs.getString("storeId"));
                        sva.setType(rs.getInt("TYPE"));
                        sva.setTokenProt(rs.getString("tokenPort"));
                        sva.setBrokerProt(rs.getString("brokerPort"));
                        return sva;
                    }
                });
    }
    
    /**
     * 查询sva列表，类型为非匿名化和匿名化，不包含指定用户
     * @param storeId
     * @return
     */
    public Collection<SvaModel> queryByStoreId2(String storeId)
    {
        String sql = "SELECT * FROM svalist a left join svastoremap b on a.id = b.svaId where a.status = 1 and a.type <> 2 and b.storeId=?";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new Object[]{storeId},
                new RowMapper()
                {

                    public Object mapRow(ResultSet rs, int num)
                            throws SQLException
                    {
                        SvaModel sva = new SvaModel();
                        sva.setId(rs.getString("ID"));
                        sva.setIp(rs.getString("IP"));
                        sva.setName(rs.getString("NAME"));
                        sva.setUsername(rs.getString("USERNAME"));
                        sva.setPassword(rs.getString("PASSWORD"));
                        sva.setStatus(rs.getInt("STATUS"));
                        sva.setStoreId(rs.getString("storeId"));
                        sva.setType(rs.getInt("TYPE"));
                        sva.setTokenProt(rs.getString("tokenPort"));
                        sva.setBrokerProt(rs.getString("brokerPort"));
                        return sva;
                    }
                });
    }

    // 查询所有启用的sva
    public Collection<SvaModel> getEnabled()
    {
        String sql = "SELECT * FROM svalist where status = 1";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new SvaMapper());
    }

    // 保存sva信息
    public int saveSvaInfo(final SvaModel mmm) throws SQLException
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator()
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                String sql = "INSERT INTO svalist(ip,name,username,password,status,type,tokenPort,brokerPort) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, mmm.getIp());
                ps.setString(2, mmm.getName());
                ps.setString(3, mmm.getUsername());
                ps.setString(4, mmm.getPassword());
                ps.setInt(5, mmm.getStatus());
                ps.setInt(6, mmm.getType());
                ps.setString(7, mmm.getTokenProt());
                ps.setString(8, mmm.getBrokerProt());
                return ps;
            }
        }, keyHolder);
        int generatedId = keyHolder.getKey().intValue();
        return generatedId;

    }

    public void updateSvaInfo(SvaModel sm) throws SQLException
    {
        String sql = "UPDATE svalist SET ip=?, name=?, username=?, password=?, status=?, type=?,tokenPort=?,brokerPort=? WHERE id=?";
        this.jdbcTemplate
                .update(sql, sm.getIp(), sm.getName(), sm.getUsername(),
                        sm.getPassword(), sm.getStatus(), sm.getType(),
                        sm.getTokenProt(), sm.getBrokerProt(), sm.getId());
    }

    // 禁用sva
    public int disableSva(String id) throws SQLException
    {
        String sql = "UPDATE svalist SET status = 0 where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }

    // 启用sva
    public int enableSva(String id) throws SQLException
    {
        String sql = "UPDATE svalist SET status = 1 where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }

    // 删除sva
    public int deleteSvaTemp(String id) throws SQLException
    {
        String sql = "UPDATE svalist SET status = 2 where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }

    // 删除sva在映射表中的数据
    public void deleteMapBySva(int id) throws SQLException
    {
        String sql = "DELETE from svastoremap WHERE svaId=?";
        this.jdbcTemplate.update(sql, id);
    }

    // 删除sva的数据
    public void deleteSva(String id) throws SQLException
    {
        String sql = "DELETE from svalist WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }

    // 添加sva在映射表中的映射关系
    public void addMapper(int id, List<String> store) throws SQLException
    {
        String sql = null;
        for (String s : store)
        {
            sql = "INSERT INTO svastoremap(svaId,storeId) VALUES(?,?)";
            this.jdbcTemplate.update(sql, id, s);
        }
    }

    private class SvaMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            SvaModel sva = new SvaModel();
            sva.setId(rs.getString("ID"));
            sva.setIp(rs.getString("IP"));
            sva.setName(rs.getString("NAME"));
            sva.setUsername(rs.getString("USERNAME"));
            sva.setPassword(rs.getString("PASSWORD"));
            sva.setStatus(rs.getInt("STATUS"));
            sva.setType(rs.getInt("TYPE"));
            sva.setTokenProt(rs.getString("tokenPort"));
            sva.setBrokerProt(rs.getString("brokerPort"));

            return sva;
        }
    }

    public Collection<SvaModel> selectID(String svaName) throws SQLException
    {
        String sql = "SELECT * FROM svalist WHERE name = ?";
        return this.jdbcTemplate.query(sql, new Object[]{svaName},
                new SvaMapper());
    }

    public Collection<SvaModel> selectID1(String svaName, String id)
            throws SQLException
    {
        String sql = "SELECT * FROM svalist WHERE name = ? and id !=?";
        return this.jdbcTemplate.query(sql, new Object[]{svaName, id},
                new SvaMapper());
    }

    public int checkSvaByName(String svaName) throws SQLException
    {
        String sql = "SELECT count(*) res FROM svalist WHERE name = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{svaName},
                Integer.class);
    }

    public int checkSvaByName1(String svaName, String id) throws SQLException
    {
        String sql = "SELECT count(*) res FROM svalist WHERE name = ? and id !=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{svaName, id},
                Integer.class);
    }
}
