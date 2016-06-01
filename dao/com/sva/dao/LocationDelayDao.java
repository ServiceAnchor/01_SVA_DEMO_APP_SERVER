package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.LocationDelay;
import com.sva.model.MessagePush;

/**
 * 
 * 
 * @author wwx283823
 * @version iSoftStone 2016-3-16
 * @since iSoftStone
 */
@SuppressWarnings("all")
public class LocationDelayDao
{
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private class LocationDelayMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            LocationDelay l = new LocationDelay();
            l.setId(rs.getInt("ID"));
            l.setPlaceId(rs.getInt("PLACEID"));
            l.setFloorNo(rs.getBigDecimal("FLOORNO"));
            l.setDataDelay(rs.getDouble("DATADELAY"));
            l.setPositionDelay(rs.getDouble("POSITIONDELAY"));
            l.setPlace(rs.getString("place"));
            l.setFloor(rs.getString("floor"));
            l.setUpdateTime(rs.getLong("updateTime"));
            return l;
        }
    }

    public List<LocationDelay> getAllData()
    {
        String sql = "select a.*,b.name place,d.floor from locationdelay a "
                + "left join store b on a.placeId = b.id "
                + "left join maps d on a.floorNo = d.floorNo ";
        return this.jdbcTemplate.query(sql, new LocationDelayMapper());
    }
    public List<LocationDelay> getAllDataByStoreid( int storeid)
    {
    	String sql = "select a.*,b.name place,d.floor from locationdelay a "
    			+ "left join store b on a.placeId = b.id "
    			+ "left join maps d on a.floorNo = d.floorNo where a.placeId ="+ storeid;
    	return this.jdbcTemplate.query(sql, new LocationDelayMapper());
    }

    public int savaMessagePush(LocationDelay aam) throws SQLException
    {
        String sql = "INSERT INTO locationdelay(placeId,floorNo,dataDelay,positionDelay,updateTime) VALUES(?,?,?,?,?)";
        return this.jdbcTemplate.update(sql, aam.getPlaceId(),
                aam.getFloorNo(), aam.getDataDelay(), aam.getPositionDelay(),
                aam.getUpdateTime());
    }

}
