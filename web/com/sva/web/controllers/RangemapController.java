package com.sva.web.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.ConvertUtil;
import com.sva.dao.CommonDao;
import com.sva.dao.RangemapDao;
import com.sva.model.LinemapModel;

@Controller
@RequestMapping(value = "/rangemap")
public class RangemapController
{

    @Autowired
    private RangemapDao dao;

    @Autowired
    private CommonDao daoCom;

    @Value("${mysql.db}")
    private String db;

    @RequestMapping(value = "/api/getChartData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getChartData(
            @RequestParam("placeId") String placeId,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("time") String time, @RequestParam("x1") String x1,
            @RequestParam("y1") String y1, @RequestParam("x2") String x2,
            @RequestParam("y2") String y2)
    {
        Collection<LinemapModel> res = null;
        Date d = ConvertUtil.dateStringFormat(time, "yyyy-MM-dd");
        String dStr = ConvertUtil.dateFormat(d, "yyyyMMdd");
        String table = "location" + dStr;
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (daoCom.isTableExist(table, db) > 0)
        {
            res = dao.getData(placeId, floorNo, time, x1, y1, x2, y2);
            modelMap.put("error", false);
            modelMap.put("data", res);
        }
        else
        {
            modelMap.put("error", false);
            modelMap.put("data", res);
        }
        return modelMap;
    }

    @RequestMapping(value = "/api/getInfoData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getInfoData(
            @RequestParam("placeId") String placeId,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("time") String time, @RequestParam("x1") String x1,
            @RequestParam("y1") String y1, @RequestParam("x2") String x2,
            @RequestParam("y2") String y2)
    {
        int count = 0;
        Date d = ConvertUtil.dateStringFormat(time, "yyyy-MM-dd");
        String dStr = ConvertUtil.dateFormat(d, "yyyyMMdd");
        String table = "location" + dStr;
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        if (daoCom.isTableExist(table, db) > 0)
        {
            count = dao.getTotalCount(placeId, floorNo, time, x1, y1, x2, y2);
            modelMap.put("error", false);
            modelMap.put("total", count);
        }
        else
        {
            modelMap.put("error", true);
            modelMap.put("total", count);
        }

        return modelMap;
    }

}
