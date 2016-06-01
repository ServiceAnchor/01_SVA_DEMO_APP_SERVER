package com.sva.web.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.ConvertUtil;
import com.sva.dao.AreaDao;
import com.sva.dao.LocationDao;
import com.sva.dao.MapsDao;
import com.sva.model.LocationModel;
import com.sva.model.MapsModel;
import com.sva.web.models.HeatmapModel;

@Controller
@RequestMapping(value = "/heatmap")
public class HeatmapController
{
    @Autowired
    private MapsDao daoMaps;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LocationDao locationDao;

    @RequestMapping(value = "/api/getMapInfoByPosition", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getHeatmapInfoData(Model model,
            @RequestParam("floorNo") String floorNo)
    {

        String bgImg = "";
        int bgImgWidth = 0;
        int bgImgHeight = 0;
        String xo = "";
        String yo = "";
        String scale = "";
        String coordinate = "";
        Collection<MapsModel> ResultList = new ArrayList<MapsModel>(10);
        ResultList = daoMaps.getMapDetail(floorNo);

        for (MapsModel l : ResultList)
        {
            bgImg = l.getPath();
            bgImgWidth = l.getImgWidth();
            bgImgHeight = l.getImgHeight();
            xo = l.getXo();
            yo = l.getYo();
            scale = l.getScale();
            coordinate = l.getCoordinate();
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(8);

        modelMap.put("error", null);
        modelMap.put("bg", bgImg);
        modelMap.put("bgWidth", bgImgWidth);
        modelMap.put("bgHeight", bgImgHeight);
        modelMap.put("xo", xo);
        modelMap.put("yo", yo);
        modelMap.put("scale", scale);
        modelMap.put("coordinate", coordinate);

        return modelMap;
    }

    @RequestMapping(value = "/api/getData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getHeatmapData(Model model,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("times") int times)
    {

        List<HeatmapModel> list = new ArrayList<HeatmapModel>(10);
        Collection<LocationModel> ResultList = new ArrayList<LocationModel>(10);
        ResultList = locationDao.queryHeatmap(floorNo, times);
        HeatmapModel hm = null;
        for (LocationModel l : ResultList)
        {
            hm = new HeatmapModel();
            hm.setX(l.getX());
            hm.setY(l.getY());
            list.add(hm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/getData5", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getHeatmapData5(Model model,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("period") int period)
    {

        List<HeatmapModel> list = new ArrayList<HeatmapModel>(10);
        Collection<LocationModel> ResultList = new ArrayList<LocationModel>(10);
        ResultList = locationDao.queryHeatmap5(floorNo, period);
        HeatmapModel hm = null;
        for (LocationModel l : ResultList)
        {
            hm = new HeatmapModel();
            hm.setX(l.getX());
            hm.setY(l.getY());
            list.add(hm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/getFloorsByMarket", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getFloors(Model model,
            @RequestParam("placeId") String placeId)
    {

        Collection<MapsModel> ResultList = new ArrayList<MapsModel>(10);
        ResultList = daoMaps.getFloors(placeId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/getFlooNo", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getFlooNo(Model model,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("time") String time)
    {

        List<LocationModel> list = new ArrayList<LocationModel>(10);
        // Collection<MapsModel> ResultList = new ArrayList<MapsModel>(10);
        // ResultList = daoMaps.getFloorid(floor);
        // MapMngModel mmm = null;

        Collection<LocationModel> location = locationDao.getUserId(floorNo,
                time);
        LocationModel mm = null;
        for (LocationModel l : location)
        {
            mm = new LocationModel();
            mm.setDataType(l.getDataType());
            mm.setIdType(l.getIdType());
            mm.setX(l.getX());
            mm.setY(l.getY());
            mm.setZ(l.getZ());
            mm.setTimestamp(l.getTimestamp());
            mm.setUserID(l.getUserID());
            list.add(mm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/getMark", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getMark(Model model,
            @RequestParam("userId") String userId,
            @RequestParam("time") String time)
    {

        List<LocationModel> list = new ArrayList<LocationModel>(10);
        Collection<LocationModel> location = locationDao.getMark(userId, time);
        LocationModel mm = null;
        for (LocationModel l : location)
        {
            mm = new LocationModel();
            mm.setDataType(l.getDataType());
            mm.setIdType(l.getIdType());
            mm.setX(l.getX());
            mm.setY(l.getY());
            mm.setZ(l.getZ());
            mm.setTimestamp(l.getTimestamp());
            mm.setUserID(l.getUserID());
            list.add(mm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/getOverData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getOverData(Model model,
            @RequestParam("store") String placeId)
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, -24);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        // long time3 = 0;
        Calendar currentDate1 = new GregorianCalendar();
        currentDate1.set(Calendar.HOUR_OF_DAY, 0);
        currentDate1.set(Calendar.MINUTE, 0);
        currentDate1.set(Calendar.SECOND, 0);
        // long time2 = 0;
        String visitDay = ConvertUtil.dateFormat(currentDate1.getTime(),
                "yyyy-MM-dd");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // try
        // {
        // // time2 = sdf.parse(
        // // ConvertUtil.dateFormat(currentDate1.getTime(),
        // // Params.YYYYMMDDHH) + "0000").getTime();
        // // time3 = sdf.parse(
        // // ConvertUtil.dateFormat(currentDate.getTime(),
        // // Params.YYYYMMDDHH) + "0000").getTime();
        // }
        // catch (ParseException e)
        // {
        // e.printStackTrace();
        // }
        List<Map<String, Object>> list = areaDao.getNowNumber(placeId);
        Map<String, Object> neirong = new HashMap<String, Object>(2);
        // List<AreaModel> models = areaDao.getAreaByPlaceId(placeId);
        List<String> areaNames = new ArrayList<String>(10);
        List<String> areaIds = new ArrayList<String>(10);
        List<Integer> numbers = new ArrayList<Integer>(10);
        List<String> visitTime = new ArrayList<String>(10);
        List<Map<String, Object>> mapTime = null;
        long allTime = 0;
        int size = 0;
        String averageTime = null;
        // Calendar cal = Calendar.getInstance();
        // String tableName = Params.LOCATION
        // + ConvertUtil.dateFormat(cal.getTime(), Params.YYYYMMDD);
        // long time = System.currentTimeMillis() - 10 * 1000;
        int lisSize = list.size();
        for (int i = 0; i < lisSize; i++)
        {
            numbers.add(Integer.parseInt(list.get(i).get("number").toString()));
            areaIds.add(list.get(i).get("areaId").toString());
            areaNames.add(areaDao.getAreaNameByAreaId(
                    list.get(i).get("areaId").toString()).get(0));
        }
        int areaIdSize = areaIds.size();
        for (int i = 0; i < areaIdSize; i++)
        {
            // areaNames.add(models.get(i).getAreaName());
            // numbers.add(areaDao.getNumberByFloorNo(models.get(i),
            // String.valueOf(time), tableName));
            mapTime = areaDao.getOverAverageTime(areaIds.get(i), visitDay);
            if (mapTime.size() > 0)
            {
                allTime = Long.parseLong(mapTime.get(0).get("allTime")
                        .toString());
                size = Integer
                        .parseInt(mapTime.get(0).get("number").toString());
                averageTime = getMinute(allTime, size);
                visitTime.add(averageTime);
            }
            else
            {
                visitTime.add("0");
            }
        }
        // Collection<LocationModel> resultList = null;
        List<Map<String, Object>> peopleList = daoMaps.getAllPeopleByPlaceId(
                placeId, visitDay);
        int count = 0;
        int allcount = 0;
        int yesterdayAll = 0;
        if (peopleList.size() > 0)
        {
            allcount = Integer.parseInt(peopleList.get(0).get("nowNumber")
                    .toString());
            yesterdayAll = Integer.parseInt(peopleList.get(0)
                    .get("yesterNumber").toString());
            count = Integer
                    .parseInt(peopleList.get(0).get("number").toString());
        }

        neirong.put("names", areaNames);
        neirong.put("cout", count);
        neirong.put("number", numbers);
        neirong.put("allcount", allcount);
        neirong.put("error", null);
        // Collections.sort(numbers);
        // if (numbers.size() > 0)
        // {
        // neirong.put("Max", numbers.get(numbers.size() - 1));
        // }
        // else
        // {
        // neirong.put("Max", 0);
        // }
        neirong.put("yesterdayAllcount", yesterdayAll);
        neirong.put("visitList", visitTime);

        return neirong;
    }

    public static String getMinute(long time, int size)
    {
        if (size == 0)
        {
            return "0";
        }
        else
        {

            float b = Float.valueOf((time / 1000) / 60);
            float averageTime = b / size;
            DecimalFormat df = new DecimalFormat("0.00");
            String min = df.format(averageTime);
            return min;
        }

    }
}
