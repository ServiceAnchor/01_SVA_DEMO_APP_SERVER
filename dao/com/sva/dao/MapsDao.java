package com.sva.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sva.model.MapsModel;
import com.sva.web.models.MapMngModel;

@SuppressWarnings("all")
public class MapsDao
{
    private JdbcTemplate jdbcTemplate;

    // 注入DataSource
    public void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 此方法把表对应的字段查询出来依次放入model中
    public Collection<MapsModel> doquery()
    {
        String sql = "SELECT a.*,b.name place FROM maps a left join store b on a.placeId = b.id";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MapsMapper());
    }

    /*
     * 通过用户名获取相对应的商场权限
     */
    public Collection<MapsModel> doqueryByStoreid(int storeid)
    {
        String sql = "SELECT a.*,b.name place FROM maps a left join store b on a.placeId = b.id where a.placeId = "
                + storeid;
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MapsMapper());
    }

    public Collection<MapsModel> getMapDetail(String floorNo)
    {
        String sql = "SELECT * FROM maps where floorNo=?";
        String[] params = {floorNo};
        return this.jdbcTemplate.query(sql, params, new MapsMapper2());
    }

    public String getMapName(String floorNo)
    {
        String sql = "SELECT path FROM maps where floorNo=? limit 1";
        String[] params = {floorNo};
        return this.jdbcTemplate.queryForObject(sql, params, String.class);
    }

    public String getFloorid(String floorNo)
    {
        String sql = "SELECT floorid FROM maps where  floorNo=? limit 1";
        String[] params = {floorNo};
        return this.jdbcTemplate.queryForObject(sql, params, String.class);
    }

    public void saveMapInfo(MapMngModel mmm) throws SQLException
    {
        String sql = "INSERT INTO maps(mapId,floor,xo,yo,scale,coordinate,angle,path,svg,route,pathfile,imgWidth,imgHeight,floorNo,placeId,floorid,updateTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql, mmm.getMapId(), mmm.getFloor().trim(),
                mmm.getX().trim(), mmm.getY().trim(), mmm.getScale().trim(),
                mmm.getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getSvg(), mmm.getRoute(), mmm
                        .getPathFile(), mmm.getImgWidth(), mmm.getImgHeight(),
                mmm.getFloorNo(), mmm.getPlaceId(), mmm.getFloorid(), mmm
                        .getUpdateTime());
    }

    public int deleteMapByFloor(String floorNo) throws SQLException
    {
        String sql = "DELETE FROM maps where floorNo = ?";
        return this.jdbcTemplate.update(sql, floorNo);
    }

    public Collection<MapsModel> getFloors(String placeId)
    {
        String sql = "select * from maps where placeId = ?;";
        // JdbcTemplate tem = this.getJdbcTemplate();
        String[] params = {placeId};
        return this.jdbcTemplate.query(sql, params, new MapsMapper2());
    }

    public Collection<MapsModel> getMapDataFromPrru()
    {
        String sql = "SELECT a.*,b.name place FROM maps a left join store b on a.placeId = b.id where a.floorNo in (select distinct(floorNo) from prru)";
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MapsMapper());
    }

    /*
     * 通过信商场登录名称查询对应的商场权限
     */
    public Collection<MapsModel> getMapDataFromPrruByStoreid(int storeid)
    {
        String sql = "SELECT a.*,b.name place FROM maps a left join store b on a.placeId = b.id where a.floorNo in (select distinct(floorNo) from prru ) and a.placeId = "
                + storeid;
        // JdbcTemplate tem = this.getJdbcTemplate();
        return this.jdbcTemplate.query(sql, new MapsMapper());
    }

    private class MapsMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            MapsModel maps = new MapsModel();
            maps.setFloor(rs.getString("FLOOR"));
            maps.setXo(rs.getString("XO"));
            maps.setYo(rs.getString("YO"));
            maps.setScale(rs.getString("SCALE"));
            maps.setPath(rs.getString("PATH"));
            maps.setSvg(rs.getString("SVG"));
            maps.setCoordinate(rs.getString("COORDINATE"));
            maps.setAngle(rs.getFloat("ANGLE"));
            maps.setImgWidth(rs.getInt("IMGWIDTH"));
            maps.setImgHeight(rs.getInt("IMGHEIGHT"));
            maps.setFloorNo(rs.getBigDecimal("FLOORNO"));
            maps.setPlace(rs.getString("PLACE"));
            maps.setPlaceId(rs.getInt("PLACEID"));
            maps.setFloorid(rs.getBigDecimal("FLOORID"));
            maps.setUpdateTime(rs.getString("UPDATETIME"));
            maps.setRoute(rs.getString("ROUTE"));
            maps.setPathFile(rs.getString("PATHFILE"));
            maps.setId(rs.getString("ID"));
            maps.setMapid(rs.getInt("MAPID"));

            return maps;
        }
    }

    private class MapsMapper2 implements RowMapper
    {
        public Object mapRow(ResultSet rs, int num) throws SQLException
        {
            MapsModel maps = new MapsModel();
            maps.setFloor(rs.getString("FLOOR"));
            maps.setXo(rs.getString("XO"));
            maps.setYo(rs.getString("YO"));
            maps.setScale(rs.getString("SCALE"));
            maps.setPath(rs.getString("PATH"));
            maps.setSvg(rs.getString("SVG"));
            maps.setCoordinate(rs.getString("COORDINATE"));
            maps.setAngle(rs.getFloat("ANGLE"));
            maps.setImgWidth(rs.getInt("IMGWIDTH"));
            maps.setImgHeight(rs.getInt("IMGHEIGHT"));
            maps.setFloorNo(rs.getBigDecimal("FLOORNO"));
            maps.setPlaceId(rs.getInt("PLACEID"));
            maps.setFloorid(rs.getBigDecimal("FLOORID"));
            maps.setUpdateTime(rs.getString("UPDATETIME"));
            maps.setRoute(rs.getString("ROUTE"));
            maps.setPathFile(rs.getString("PATHFILE"));
            maps.setId(rs.getString("ID"));
            maps.setMapid(rs.getInt("MAPID"));

            return maps;
        }
    }

    public int checkByPlace1(String placeId, String floor, String id)
    {
        String sql = "SELECT count(*) res FROM maps where placeId = ? and floor = ? and id != ?";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{placeId,
                floor, id}, Integer.class);
    }

    // 通过placeId查询出商城地点的名称
    public String placeByPlaceId(int placeId)
    {
        String sql = "SELECT name FROM store where id = ? ";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{placeId},
                String.class);
    }

    public List<Integer> getFloorByPlaceId(String placeId)
    {
        String sql = "SELECT floorNo FROM maps where placeId = ? ";

        return this.jdbcTemplate.queryForList(sql, new Object[]{placeId},
                Integer.class);
    }

    public List<Map<String, Object>> getFloorNo()
    {
        String sql = "SELECT floorNo,placeId FROM maps ";

        return this.jdbcTemplate.queryForList(sql);
    }

    public int chekByFloorNo1(int floorNo, String id)
    {
        String sql = "SELECT count(*) res FROM maps where floorNo = ? and id != ?";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{floorNo, id},
                Integer.class);
    }

    public int checkByPlace(String placeId, String floor)
    {
        String sql = "SELECT count(*) res FROM maps where placeId = ? and floor = ?";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{placeId,
                floor}, Integer.class);
    }

    public int chekByFloorNo(int floorNo)
    {
        String sql = "SELECT count(*) res FROM maps where floorNo = ? ";

        return this.jdbcTemplate.queryForObject(sql, new Object[]{floorNo},
                Integer.class);
    }

    public void updateMap(MapMngModel mmm) throws SQLException
    {
        if (mmm.getPathFile() == null)
        {

            if (mmm.getPath() != null && mmm.getSvg() != null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?,floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,svg=?,route=?,updateTime=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=?,isEnable=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getSvg(), mmm.getRoute(), mmm
                        .getUpdateTime(), mmm.getImgWidth(),
                        mmm.getImgHeight(), mmm.getFloorNo(), mmm.getPlaceId(),
                        mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() != null && mmm.getSvg() == null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getImgWidth(), mmm.getImgHeight(), mmm
                        .getFloorNo(), mmm.getPlaceId(), mmm.getFloorid(), mmm
                        .getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() == null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?,floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm
                        .getFloorNo(), mmm.getPlaceId(), mmm.getFloorid(), mmm
                        .getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() != null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,svg=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getSvg(),
                        mmm.getFloorNo(), mmm.getPlaceId(), mmm.getFloorid(),
                        mmm.getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() == null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,route=?,updateTime=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(),
                        mmm.getRoute(), mmm.getUpdateTime(), mmm.getFloorNo(),
                        mmm.getPlaceId(), mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() != null && mmm.getSvg() != null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,svg=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getSvg(), mmm.getImgWidth(), mmm
                        .getImgHeight(), mmm.getFloorNo(), mmm.getPlaceId(),
                        mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() != null && mmm.getSvg() == null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,route=?,updateTime=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getRoute(), mmm.getUpdateTime(), mmm
                        .getImgWidth(), mmm.getImgHeight(), mmm.getFloorNo(),
                        mmm.getPlaceId(), mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() != null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,svg=?,route=?,updateTime=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getSvg()
                        .trim(), mmm.getRoute(), mmm.getUpdateTime(), mmm
                        .getFloorNo(), mmm.getPlaceId(), mmm.getFloorid(), mmm
                        .getId());
            }
        }
        if (mmm.getPathFile() != null)
        {
            if (mmm.getPath() != null && mmm.getSvg() != null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,svg=?,route=?,pathfile=?,updateTime=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=?,isEnable=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getSvg(), mmm.getRoute(), mmm
                        .getPathFile(), mmm.getUpdateTime(), mmm.getImgWidth(),
                        mmm.getImgHeight(), mmm.getFloorNo(), mmm.getPlaceId(),
                        mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() != null && mmm.getSvg() == null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,pathfile=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getPathFile(), mmm.getImgWidth(), mmm
                        .getImgHeight(), mmm.getFloorNo(), mmm.getPlaceId(),
                        mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() == null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,pathfile=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm
                        .getPathFile(), mmm.getFloorNo(), mmm.getPlaceId(), mmm
                        .getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() != null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,svg=?,pathfile=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getSvg(),
                        mmm.getPathFile(), mmm.getFloorNo(), mmm.getPlaceId(),
                        mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() == null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,route=?,pathfile=?,updateTime=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(),
                        mmm.getRoute(), mmm.getPathFile(), mmm.getUpdateTime(),
                        mmm.getFloorNo(), mmm.getPlaceId(), mmm.getFloorid(),
                        mmm.getId());
            }
            else if (mmm.getPath() != null && mmm.getSvg() != null
                    && mmm.getRoute() == null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,svg=?,pathfile=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getSvg(), mmm.getPathFile(), mmm
                        .getImgWidth(), mmm.getImgHeight(), mmm.getFloorNo(),
                        mmm.getPlaceId(), mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() != null && mmm.getSvg() == null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,path=?,route=?,pathfile=?,updateTime=?,imgWidth=?,imgHeight=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getPath()
                        .trim(), mmm.getRoute(), mmm.getPathFile(), mmm
                        .getUpdateTime(), mmm.getImgWidth(),
                        mmm.getImgHeight(), mmm.getFloorNo(), mmm.getPlaceId(),
                        mmm.getFloorid(), mmm.getId());
            }
            else if (mmm.getPath() == null && mmm.getSvg() != null
                    && mmm.getRoute() != null)
            {
                String sql = "UPDATE maps set mapId=?, floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,svg=?,route=?,pathfile=?,updateTime=?,floorNo=?,placeId=?,floorid=? where id=? ";
                this.jdbcTemplate.update(sql,mmm.getMapId(), mmm.getFloor().trim(), mmm.getX()
                        .trim(), mmm.getY().trim(), mmm.getScale().trim(), mmm
                        .getCoordinate().trim(), mmm.getAngle(), mmm.getSvg()
                        .trim(), mmm.getRoute(), mmm.getPathFile(), mmm
                        .getUpdateTime(), mmm.getFloorNo(), mmm.getPlaceId(),
                        mmm.getFloorid(), mmm.getId());
            }
        }

    }

    public void updateMap1(MapMngModel mmm) throws SQLException
    {
        String sql = "UPDATE maps set floor=?,xo=?,yo=?,scale=?,coordinate=?,angle=?,floorNo=?,placeId=?,floorid=? where id=? ";
        this.jdbcTemplate.update(sql, mmm.getFloor().trim(), mmm.getX().trim(),
                mmm.getY().trim(), mmm.getScale().trim(), mmm.getCoordinate()
                        .trim(), mmm.getAngle(), mmm.getFloorNo(), mmm
                        .getPlaceId(), mmm.getFloorid(), mmm.getId());
    }

    public int getNumberByMinute(String tableName, String placeId)
    {
        String sql = " select count(distinct a.userID) number from "
                + tableName
                + " a join maps b on a.z = b.floorNo where b.placeId = ?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{placeId},
                Integer.class);
    }

    public int getYesterdayNumber(String tableName, String placeId, long time)
    {
        String sql = " select count(distinct a.userID) number from "
                + tableName
                + " a join maps b on a.z = b.floorNo where b.placeId = ? and a.timestamp<?";
        return this.jdbcTemplate.queryForObject(sql,
                new Object[]{placeId, time}, Integer.class);
    }

    public void saveAllPeople(int placeId, int yesNumber, int number,
            int nowNumber, String visiday)
    {
        String sql = "replace into allpeople(time,nowNumber,number,yesterNumber,placeId) values(?,?,?,?,?)";
        this.jdbcTemplate.update(sql, visiday, number, nowNumber, yesNumber,
                placeId);
    }

    public void saveNowPeople(int placeId, int number, String areaName,
            String areaId)
    {
        String sql = "replace into nowpeople(placeId,number,areaName,areaId) values(?,?,?,?)";
        this.jdbcTemplate.update(sql, placeId, number, areaName, areaId);
    }

    public List<Map<String, Object>> getAllPeopleByPlaceId(String placeId,
            String visiday)
    {
        String sql = "SELECT nowNumber,yesterNumber,number FROM allpeople where placeId = ? and time = ? ";

        return this.jdbcTemplate.queryForList(sql, new Object[]{placeId,
                visiday});
    }

    public int getNowPeople(String tableName, String placeId, String time)
    {
        String sql = " select count(distinct a.userID) number from "
                + tableName
                + " a join maps b on a.z = b.floorNo and b.placeId = ? and a.timestamp >?";
        return this.jdbcTemplate.queryForObject(sql,
                new Object[]{placeId, time}, Integer.class);
    }
}
