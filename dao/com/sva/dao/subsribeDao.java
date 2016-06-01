package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.SubsribeModel;

@SuppressWarnings("all")
public class subsribeDao {
	private JdbcTemplate jdbcTemplate;

	// 注入DataSource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// 此方法把表对应的字段查询出来依次放入model中
	public Collection<SubsribeModel> getData() {
		String sql = "SELECT * FROM location_subscribe";
		return this.jdbcTemplate.query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				SubsribeModel subsribe = new SubsribeModel();
				subsribe.setTimestamp(rs.getLong("timestamp"));
				subsribe.setTime_begin(rs.getLong("time_begin"));
				subsribe.setLast_sub_time(rs.getLong("last_sub_time"));
				subsribe.setSub_type(rs.getInt("sub_type"));
				subsribe.setSub_count(rs.getInt("sub_count"));
				subsribe.setRequest_count(rs.getInt("request_count"));
				subsribe.setUserID(rs.getString("userID"));
				subsribe.setStoreID(rs.getInt("storeID"));
				return subsribe;
			}
		});
	}

	// 条件查询返回条数
	public int getuser_storeID(String userID, String storeID) {
		String sql = "SELECT count(*) FROM location_subscribe WHERE userID=? AND storeID=?";

		return jdbcTemplate.queryForObject(sql,
				new Object[] { userID, storeID }, Integer.class);
	}

	// 条件查询返回条数的数据
	public Collection<SubsribeModel> getuser_storeIDInfo(String userID,
			String storeID) {
		String[] tar = { userID, storeID };
		String sql = "SELECT * FROM location_subscribe WHERE userID=? AND storeID=?";

		return jdbcTemplate.query(sql, tar, new SubsribeMapper());
	}

	private class SubsribeMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int num) throws SQLException {
			SubsribeModel subsribe = new SubsribeModel();
			subsribe.setTimestamp(rs.getLong("timestamp"));
			subsribe.setTime_begin(rs.getLong("time_begin"));
			subsribe.setLast_sub_time(rs.getLong("last_sub_time"));
			subsribe.setSub_type(rs.getInt("sub_type"));
			subsribe.setSub_count(rs.getInt("sub_count"));
			subsribe.setRequest_count(rs.getInt("request_count"));
			subsribe.setUserID(rs.getString("userID"));
			subsribe.setStoreID(rs.getInt("storeID"));
			return subsribe;
		}
	}

	// 保存信息
	// timestamp,time_begin,last_sub_time,app_id,sub_count,request_count,userID
	public void saveInfo(SubsribeModel sub) throws SQLException {
		String sql = "INSERT INTO location_subscribe(timestamp,time_begin,last_sub_time,sub_type,sub_count,request_count,userID,storeID) VALUES(?,?,?,?,?,?,?,?)";
		this.jdbcTemplate.update(sql, sub.getTimestamp(), sub.getTime_begin(),
				sub.getLast_sub_time(), sub.getSub_type(), sub.getSub_count(),
				sub.getRequest_count(), sub.getUserID(), sub.getStoreID());
	}

	// 更新信息
	public void updateInfo(SubsribeModel sub) throws SQLException {
		String sql = "UPDATE location_subscribe SET last_sub_time=?, sub_type=?, sub_count=?, request_count=? WHERE userID=? AND storeID=?";
		this.jdbcTemplate.update(sql, sub.getLast_sub_time(),
				sub.getSub_type(), sub.getSub_count(), sub.getRequest_count(),
				sub.getUserID(), sub.getStoreID());
	}
	
	// 更新状态
		public void updateForUnsubscription(SubsribeModel sub) throws SQLException {
			String sql = "UPDATE location_subscribe SET sub_type=?, timestamp=?, last_sub_time=? WHERE userID=? AND storeID=?";
			this.jdbcTemplate.update(sql, sub.getSub_type(), sub.getTimestamp(), sub.getLast_sub_time(), sub.getUserID(),
					sub.getStoreID());
		}

	// 更新状态
	public void updatetypeInfo(SubsribeModel sub) throws SQLException {
		String sql = "UPDATE location_subscribe SET sub_type=? WHERE userID=? AND storeID=?";
		this.jdbcTemplate.update(sql, sub.getSub_type(), sub.getUserID(),
				sub.getStoreID());
	}

}
