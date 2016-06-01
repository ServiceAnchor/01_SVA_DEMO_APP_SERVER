package com.sva.web.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.ConvertUtil;
import com.sva.dao.AreaDao;
import com.sva.dao.RangemapDao;

@Controller
@RequestMapping(value = "/areamap")
public class AreamapController
{

    @Autowired
    private RangemapDao dao;

    @Autowired
    private AreaDao areaDao;

    @RequestMapping(value = "/api/getChartData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getChartData(
            @RequestParam("areaName") String areaName,
            @RequestParam("timeBegin") String timeBegin,
            @RequestParam("timeEnd") String timeEnd)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        TreeMap<String, Integer> map = getPeriodList(timeBegin, timeEnd);
        List<Map<String, Object>> res = dao.getDataForArea(areaName, timeBegin,
                timeEnd);
        TreeMap<String, Integer> res1 = new TreeMap<String, Integer>();
        Map<String, Object> maps = new HashMap<String, Object>(2);
        String mapvalue = null;
        int size = res.size();
        for (int i = 0; i < size; i++)
        {
            maps = res.get(i);
            mapvalue = maps.values().toString();
            map.remove(mapvalue.substring(1, 20));
            map.put(mapvalue.substring(1, 20),
                    Integer.parseInt(mapvalue.substring(24, maps.values()
                            .toString().length() - 1)));
        }

        List<Integer> li = new ArrayList<Integer>(10);
        Collection<Integer> value = new ArrayList<Integer>(10);
        value = map.values();
        for (Integer i : value)
        {
            li.add(i);
        }
        Set<String> key1 = map.keySet();
        Iterator<String> i1 = key1.iterator();
        int j = 0;
        boolean hasNext = i1.hasNext();
        while (hasNext)
        {
            res1.put(i1.next(), li.get(j));
            j++;
            hasNext = i1.hasNext();
        }
        modelMap.put("error", false);
        modelMap.put("data", res1);
        return modelMap;
    }

    @RequestMapping(value = "/api/getRangeData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getRangeData(@RequestParam("id") String id,
            @RequestParam("areaName") String areaName,
            @RequestParam("timeBegin") String timeBegin,
            @RequestParam("timeEnd") String timeEnd)
    {
        // TreeMap<String, Integer> map = getPeriodList(timeBegin, timeEnd);
        TreeMap<String, Integer> timemap = getTime(id, timeBegin, timeEnd);
        List<Map<String, Object>> res = null;
        if ("1".equals(id))
        {
            res = dao.getDataForArea(areaName, timeBegin, timeEnd);
        }
        else
        {
            res = dao.getDataForAreaByDay(areaName, timeBegin, timeEnd);
        }
        String areaName1 = dao.getNameById(areaName);
        TreeMap<String, Integer> res1 = new TreeMap<String, Integer>();
        Map<String, Object> maps = new HashMap<String, Object>(2);
        String mapvalue = null;
        Map<String, Object> neirong = new HashMap<String, Object>(2);
        int size = res.size();
        for (int i = 0; i < size; i++)
        {
            maps = res.get(i);
            mapvalue = maps.values().toString();
            if ("1".equals(id))
            {
                timemap.remove(mapvalue.substring(1, 20));
                timemap.put(
                        mapvalue.substring(1, 20),
                        Integer.parseInt(mapvalue.substring(24, maps.values()
                                .toString().length() - 1)));
            }
            else
            {
                timemap.remove(mapvalue.substring(1, 11));
                timemap.put(
                        mapvalue.substring(1, 11),
                        Integer.parseInt(mapvalue.substring(24, maps.values()
                                .toString().length() - 1)));
            }
        }

        List<Integer> li = new ArrayList<Integer>(10);
        Collection<Integer> value = new ArrayList<Integer>(10);
        value = timemap.values();
        for (Integer i : value)
        {
            li.add(i);
        }
        Set<String> key1 = timemap.keySet();
        Iterator<String> i1 = key1.iterator();
        int j = 0;
        boolean hasNext = i1.hasNext();
        while (hasNext)
        {

            res1.put(i1.next(), li.get(j));
            neirong.put("name", areaName1);
            neirong.put("type", "line");
            neirong.put("data", value);
            j++;
            hasNext = i1.hasNext();
        }
        return neirong;
    }

    @RequestMapping(value = "/api/getAreaName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getChartData1(@RequestParam("id") String Id,
            @RequestParam("placeId") String areaName,
            @RequestParam("timeBegin") String timeBegin,
            @RequestParam("timeEnd") String timeEnd)
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        // String visitDay = ConvertUtil.dateFormat(currentDate.getTime(),
        // "yyyy-MM-dd");
        Map<String, Object> modelMap = null;
        // Map<String, Object> visitModel = null;
        Map<String, Object> modelMap1 = new HashMap<String, Object>(2);
        // TreeMap<String, Integer> visitMap = null;
        List<Map<String, Object>> lis = areaDao.getAreaName(areaName);
        List<Object> id = new ArrayList<Object>(10);
        List<Object> name = new ArrayList<Object>(10);
        List<String> time = new ArrayList<String>(10);
        time = getTimes(Id, timeBegin, timeEnd);
        List<String> visitTime1 = new ArrayList<String>(10);
        visitTime1 = getVisitTime(timeBegin, timeEnd);
        List<Object> ob = new ArrayList<Object>(10);
        // List<Object> visitTime = new ArrayList<Object>();
        // getPeriodList(timeBegin, timeEnd);
        int size = lis.size();
        Set<String> sets = null;
        for (int i = 0; i < size; i++)
        {
            sets = lis.get(i).keySet();
            for (String key : sets)
            {
                name.add(lis.get(i).get(key));
            }
        }
        int nameSize = name.size();
        for (int i = 0; i < nameSize; ++i)
        {
            // visitMap = getTime("0", timeBegin, timeEnd);
            id.add(name.get(i));
            modelMap = new HashMap<String, Object>(2);
            // visitModel = new HashMap<String, Object>(2);
            // visitModel = getVisitData(visitMap, name.get(i).toString(),
            // timeBegin, timeEnd);
            modelMap = getRangeData(Id, name.get(i).toString(), timeBegin,
                    timeEnd);
            ob.add(modelMap);
            // visitTime.add(visitModel);
            name.remove(i);
            nameSize = name.size();
        }
        modelMap1.put("error", false);
        modelMap1.put("data", ob);
        modelMap1.put("name", name);
        modelMap1.put("time", time);
        // modelMap1.put("visitData", visitTime);
        modelMap1.put("visitTime", visitTime1);
        return modelMap1;
    }

    @RequestMapping(value = "/api/getVisitTime", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getVisitTime(
            @RequestParam("placeId") String areaName,
            @RequestParam("timeBegin") String timeBegin,
            @RequestParam("timeEnd") String timeEnd)
    {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        Map<String, Object> visitModel = null;
        Map<String, Object> modelMap1 = new HashMap<String, Object>(2);
        TreeMap<String, Integer> visitMap = null;
        List<Map<String, Object>> lis = areaDao.getAreaName(areaName);
        List<Object> id = new ArrayList<Object>(10);
        List<Object> name = new ArrayList<Object>(10);
        List<String> time = new ArrayList<String>(10);
        List<Object> visitTime = new ArrayList<Object>(10);
        time = getTimes("0", timeBegin, timeEnd);
        // getPeriodList(timeBegin, timeEnd);
        int size = lis.size();
        Set<String> sets = null;
        for (int i = 0; i < size; i++)
        {
            sets = lis.get(i).keySet();
            for (String key : sets)
            {
                name.add(lis.get(i).get(key));
            }
        }
        int size1 = name.size();
        for (int i = 0; i < size1; i++)
        {
            visitMap = getTime("0", timeBegin, timeEnd);
            id.add(name.get(i));
            visitModel = new HashMap<String, Object>(2);
            visitModel = getVisitData(visitMap, name.get(i).toString(),
                    timeBegin, timeEnd);
            visitTime.add(visitModel);
            name.remove(i);
            size1 = name.size();
        }
        modelMap1.put("error", false);
        modelMap1.put("name", name);
        modelMap1.put("time", time);
        modelMap1.put("visitData", visitTime);
        return modelMap1;
    }

    private TreeMap<String, Integer> getPeriodList(String timeBegin,
            String timeEnd)
    {
        TreeMap<String, Integer> result = new TreeMap<String, Integer>();

        Date start = ConvertUtil.dateStringFormat(timeBegin,
                "yyyy-MM-dd HH:mm:ss");
        Date end = ConvertUtil.dateStringFormat(timeEnd, "yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        Date tmpDate = cal.getTime();
        // long endTimeInSec = end.getTime();
        String keyTime = null;
        boolean b = tmpDate.before(end);
        while (b)
        {

            keyTime = ConvertUtil.dateFormat(cal.getTime(),
                    "yyyy-MM-dd HH:mm:ss");
            result.put(keyTime, 0);
            cal.add(Calendar.HOUR, 1);
            tmpDate = cal.getTime();
            b = tmpDate.before(end);
        }

        return result;

    }

    private TreeMap<String, Integer> getTime(String type, String timeBegin,
            String timeEnd)
    {
        TreeMap<String, Integer> result = new TreeMap<String, Integer>();
        if ("1".equals(type))
        {
            Date start = ConvertUtil.dateStringFormat(timeBegin,
                    "yyyy-MM-dd HH:mm:ss");
            Date end = ConvertUtil.dateStringFormat(timeEnd,
                    "yyyy-MM-dd HH:mm:ss");

            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            Date tmpDate = cal.getTime();
            // long endTimeInSec = end.getTime();
            String keyTime = null;
            boolean b = tmpDate.before(end);
            while (b)
            {

                keyTime = ConvertUtil.dateFormat(cal.getTime(),
                        "yyyy-MM-dd HH:mm:ss");
                result.put(keyTime, 0);
                cal.add(Calendar.HOUR, 1);
                tmpDate = cal.getTime();
                b = tmpDate.before(end);
            }
            return result;
        }
        else
        {
            Date start = ConvertUtil.dateStringFormat(timeBegin, "yyyy-MM-dd");
            Date end = ConvertUtil.dateStringFormat(timeEnd, "yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            Date tmpDate = cal.getTime();
            // long endTimeInSec = end.getTime();
            String keyTime = null;
            boolean b = tmpDate.before(end);
            while (b)
            {

                keyTime = ConvertUtil.dateFormat(cal.getTime(), "yyyy-MM-dd");
                result.put(keyTime, 0);
                cal.add(Calendar.DATE, 1);
                tmpDate = cal.getTime();
                b = tmpDate.before(end);
            }
            return result;

        }

    }

    public List<String> getTimes(String type, String timeBegin, String timeEnd)
    {
        List<String> lis = new ArrayList<String>(10);
        if ("1".equals(type))
        {
            Date start = ConvertUtil.dateStringFormat(timeBegin,
                    "yyyy-MM-dd HH:mm:ss");
            Date end = ConvertUtil.dateStringFormat(timeEnd,
                    "yyyy-MM-dd HH:mm:ss");

            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            Date tmpDate = cal.getTime();
            // long endTimeInSec = end.getTime();
            String keyTime = null;
            boolean b = tmpDate.before(end);
            while (b)
            {

                keyTime = ConvertUtil.dateFormat(cal.getTime(),
                        "yyyy-MM-dd HH:mm:ss");
                lis.add(keyTime);
                cal.add(Calendar.HOUR, 1);
                tmpDate = cal.getTime();
                b = tmpDate.before(end);
            }
            return lis;
        }
        else
        {
            Date start = ConvertUtil.dateStringFormat(timeBegin, "yyyy-MM-dd");
            Date end = ConvertUtil.dateStringFormat(timeEnd, "yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            Date tmpDate = cal.getTime();
            // long endTimeInSec = end.getTime();
            String keyTime = null;
            boolean b = tmpDate.before(end);
            while (b)
            {

                keyTime = ConvertUtil.dateFormat(cal.getTime(), "yyyy-MM-dd");
                lis.add(keyTime);
                cal.add(Calendar.DATE, 1);
                tmpDate = cal.getTime();
                b = tmpDate.before(end);
            }
            return lis;

        }

    }

    @RequestMapping(value = "/api/getAreaNames", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getAreaNames(Model model)
    {

        List<Map<String, Object>> ResultList = null;
        ResultList = dao.getAreaName();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/checkName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkName(@RequestParam("id") String id,
            @RequestParam("name") String name)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String nu = "";
        int i = 0;
        if (id != nu)
        {
            i = areaDao.checkByName1(name, id);
            if (i > 0)
            {
                modelMap.put("error", true);
                return modelMap;
            }
            else
            {
                modelMap.put("error", false);
                return modelMap;
            }
        }
        else
        {
            i = areaDao.checkByName(name);
            if (i > 0)
            {
                modelMap.put("error", true);
                return modelMap;
            }
            else
            {
                modelMap.put("error", false);
                return modelMap;
            }
        }
    }

    private Map<String, Object> getVisitData(TreeMap<String, Integer> map,
            String areaName, String startTime, String endTime)
    {
        Map<String, Object> neirong = new HashMap<String, Object>(10);
        String areaName1 = dao.getNameById(areaName);
        String time1 = startTime.substring(0, 10);
        String time2 = endTime.substring(0, 10);
        List<Integer> visitData = new ArrayList<Integer>(10);
        List<Map<String, Object>> visitDatas = dao.getVisitData(areaName,
                time1, time2);
        int size = visitDatas.size();
        String averageTime = null;
        Integer number = 0;
        long allTime = 0;
        String time = null;
        Map<String, Object> str = null;
        for (int i = 0; i < size; i++)
        {
            str = visitDatas.get(i);
            time = str.get("time").toString();
            allTime = Long.parseLong(str.get("averageTime").toString());
            number = Integer.parseInt(str.get("number").toString());
            map.remove(time);
            averageTime = getMinute(allTime, number);
            map.put(time, Integer.parseInt(averageTime));
        }
        Collection<Integer> value = new ArrayList<Integer>(10);
        value = map.values();
        for (Integer i : value)
        {
            visitData.add(i);
        }

        neirong.put("name", areaName1);
        neirong.put("type", "line");
        neirong.put("data", visitData);
        return neirong;

    }

    private List<String> getVisitTime(String timeBegin, String timeEnd)
    {
        List<String> lis = new ArrayList<String>(10);
        Date start = ConvertUtil.dateStringFormat(timeBegin, "yyyy-MM-dd");
        Date end = ConvertUtil.dateStringFormat(timeEnd, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        Date tmpDate = cal.getTime();
        // long endTimeInSec = end.getTime();
        String keyTime = null;
        boolean b = tmpDate.before(end);
        while (b)
        {

            keyTime = ConvertUtil.dateFormat(cal.getTime(), "yyyy-MM-dd");
            lis.add(keyTime);
            cal.add(Calendar.DATE, 1);
            tmpDate = cal.getTime();
            b = tmpDate.before(end);
        }
        keyTime = ConvertUtil.dateFormat(cal.getTime(), "yyyy-MM-dd");
        lis.add(keyTime);
        return lis;
    }

    public static String getMinute(long time, int size)
    {
        if (size == 0 || time == 0)
        {
            return "0";
        }
        else
        {
            float b = Float.valueOf((time / 1000) / 60);
            float averageTime = b / size;
            DecimalFormat df = new DecimalFormat("0");
            String min = df.format(averageTime);
            return min;
        }

    }
}
