package com.sva.web.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.ConvertUtil;
import com.sva.dao.LinemapDao;
import com.sva.model.LinemapModel;

@Controller
@RequestMapping(value = "/linemap")
public class LinemapController
{

    @Autowired
    private LinemapDao dao;

    @RequestMapping(value = "/api/getChartData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getChartData(
            @RequestParam("placeId") String placeId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("dim") String dim)
    {
        TreeMap<String, Integer> map = getPeriodList(startTime, endTime,
                Integer.parseInt(dim));
        String time = startTime.split(" ")[0] + ' ' + "00:00:00";
        Collection<LinemapModel> res = new ArrayList<LinemapModel>(10);
        Collection<LinemapModel> res1 = new ArrayList<LinemapModel>(10);
        if ("1".equals(dim))
        {
            res = dao.getDataByHour(placeId, startTime, endTime);
            for (LinemapModel l : res)
            {
                map.remove(l.getTime().substring(0, 19));
                map.put(l.getTime().substring(0, 19), l.getNumber());
            }
        }
        else
        {
            res = dao.getDataByDay(placeId, time, endTime);
            for (LinemapModel l : res)
            {
                map.remove(l.getTime().split(" ")[0]);
                map.put(l.getTime().split(" ")[0], l.getNumber());
            }
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
        LinemapModel mod = null;
        boolean b = i1.hasNext();
        while (b)
        {
            mod = new LinemapModel();
            mod.setTime(i1.next());
            mod.setNumber(li.get(j));
            j++;
            res1.add(mod);
            b = i1.hasNext();
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", false);
        modelMap.put("data", res1);

        return modelMap;
    }

    @RequestMapping(value = "/api/getInfoData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getInfoData(
            @RequestParam("placeId") String placeId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime)
    {
        Collection<LinemapModel> resMaxDay = null;
        Collection<LinemapModel> resMaxHour = null;
        int count = dao.getTotalCount(placeId, startTime, endTime);

        resMaxDay = dao.getMaxDay(placeId, startTime, endTime);
        resMaxHour = dao.getMaxTime(placeId, startTime, endTime);

        Map<String, Object> modelMap = new HashMap<String, Object>(4);
        modelMap.put("error", false);
        modelMap.put("total", count);
        modelMap.put("maxDay", resMaxDay);
        modelMap.put("maxHour", resMaxHour);

        return modelMap;
    }

    public static TreeMap<String, Integer> getPeriodList(String startTime,
            String endTime, int flag)
    {
        TreeMap<String, Integer> result = new TreeMap<String, Integer>();

        Date start = ConvertUtil.dateStringFormat(startTime,
                "yyyy-MM-dd HH:mm:ss");
        Date end = ConvertUtil.dateStringFormat(endTime, "yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        Date tmpDate = cal.getTime();
        // long endTimeInSec = end.getTime();

        if (flag == 1)
        {
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
        }
        else if (flag == 2)
        {
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
        }
        return result;
    }

}
