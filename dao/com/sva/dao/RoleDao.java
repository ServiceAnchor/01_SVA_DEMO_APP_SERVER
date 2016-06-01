package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.RoleModel;

@SuppressWarnings("all")
public class RoleDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<RoleModel> doquery()
    {
        String sql = "select * from role where id != 1";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new RoleMapper());
    }

    // 查询菜单栏中所有的菜单
    public List<Map<String, Object>> selectMenu()
    {
        String sql = "select * from menuname";
        return this.jdbcTemplate.queryForList(sql);
    }

    // 查询菜单栏中所有的英文菜单
    public List<Map<String, Object>> selectMenuEnglish()
    {
        String sql = "select * from menuenglish";
        return this.jdbcTemplate.queryForList(sql);
    }

    // 查询菜单栏中所有的菜单
    public List<String> selectMenuid()
    {
        String sql = "select m.id from menuname m";
        return this.jdbcTemplate.queryForList(sql, String.class);
    }

    public List<String> selectMenuidEN()
    {
        String sql = "select m.id from menuenglish m";
        return this.jdbcTemplate.queryForList(sql, String.class);
    }

    // 通过菜单id查询菜单queryMenu
    public List<String> queryMenu(int menuId) throws SQLException
    {
        String sql = "select m.name from menuname m where id = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menuId},
                String.class);
    }

    public List<String> queryMenuEN(int menuId) throws SQLException
    {
        String sql = "select m.name from menuenglish m where id = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menuId},
                String.class);
    }

    public List<String> selmenu(String menukey) throws SQLException
    {
        String sql = "select m.name from menuname m where keyZH = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menukey},
                String.class);
    }

    public List<String> selmenuEN(String menukey) throws SQLException
    {

        String sql = "select m.name from menuenglish m where keyEN = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menukey},
                String.class);
    }

    public List<String> queryMenuKey(int menuId) throws SQLException
    {
        String sql = "select m.keyZH from menuname m where id = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menuId},
                String.class);
    }

    public List<String> queryMenuKeyEN(int menuId) throws SQLException
    {
        String sql = "select m.keyEN from menuenglish m where id = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menuId},
                String.class);
    }

    // 通过菜单name查询菜单menuid
    public List<String> queryMenuId(String menuName) throws SQLException
    {
        String sql = "select id from menuname where name = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menuName},
                String.class);
    }

    public List<String> queryMenuIdEN(String menuName) throws SQLException
    {
        String sql = "select id from menuenglish where name = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{menuName},
                String.class);
    }

    // 通过商场name查询菜单storeid
    public List<String> queryStoreId(String storeName) throws SQLException
    {
        String sql = "select id from store where name = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{storeName},
                String.class);
    }

    // 查询菜单栏中所有的菜单
    public List<String> selectStoreid()
    {
        String sql = "select s.id from store s";
        return this.jdbcTemplate.queryForList(sql, String.class);
    }

    // 通过菜单id查询商场名称
    public List<String> queryStore(int storeId) throws SQLException
    {
        String sql = "select name from Store where id = ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{storeId},
                String.class);
    }

    // 查询类别名称是否相同
    public int selectRoleSame(String name, int id) throws SQLException
    {
        String sql = "SELECT COUNT(*) FROM role WHERE name = ? and id !=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{name, id},
                Integer.class);
    }

    // 通过用户名查询商场id
    public String queryStoreid(String username) throws SQLException
    {
        String sql = "SELECT r.storesid FROM role r left join  account a on a.roleid = r.id WHERE a.username = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{username},
                String.class);
    }

    // 通过用户名查询商场id
    public String queryStore(String username) throws SQLException
    {
        String sql = "SELECT r.stores FROM role r left join  account a on a.roleid = r.id WHERE a.username = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{username},
                String.class);
    }

    // 通过用户名查询角色id
    public int selectRoleid(String username) throws SQLException
    {
        String sql = "SELECT roleid FROM account  WHERE username = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{username},
                Integer.class);
    }

    // 保存信息
    public void saveInfo(RoleModel sm) throws SQLException
    {
        String sql = "INSERT INTO ROLE(name,updateTime,menus,stores,menukey,storesid) VALUES(?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, sm.getName(), sm.getUpdateTime(),
                sm.getMenues(), sm.getStores(), sm.getMenukey(),
                sm.getStoresid());
    }

    // 更新信息
    public void updateInfo(RoleModel sm) throws SQLException
    {
        String sql = "UPDATE ROLE SET name=?, menus=?, stores=?,updateTime=?,storesid=?,menukey=? WHERE id=?";
        this.jdbcTemplate.update(sql, sm.getName(), sm.getMenues(),
                sm.getStores(), sm.getUpdateTime(), sm.getStoresid(),
                sm.getMenukey(), sm.getId());
    }

    /*
     * 添加商场时自动更新到角色商场权限
     */
    public void updateInfoStore(String storeid, String stores, int id)
            throws SQLException
    {
        String sql = "UPDATE ROLE SET storesid=?,stores = ? WHERE id=?";
        this.jdbcTemplate.update(sql, storeid, stores, id);
    }

    // 删除信息
    public void deleteById(String id) throws SQLException
    {
        String sql = "DELETE from ROLE WHERE id=?";
        this.jdbcTemplate.update(sql, id);
    }

    private class RoleMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            RoleModel roleModel = new RoleModel();
            roleModel.setId(rs.getInt("ID"));
            roleModel.setName(rs.getString("NAME"));
            roleModel.setMenukey(rs.getString("menukey"));
            roleModel.setStores(rs.getString("stores"));
            roleModel.setUpdateTime(rs.getTimestamp("updatetime"));
            return roleModel;
        }

    }
}