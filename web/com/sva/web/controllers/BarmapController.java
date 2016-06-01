package com.sva.web.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.BarmapDao;
import com.sva.model.BarmapModel;

@Controller
@RequestMapping(value = "/barmap")
public class BarmapController
{

    @Autowired
    private BarmapDao dao;

    @RequestMapping(value = "/api/getChartData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getChartData(
            @RequestParam("placeId") String placeId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime)
    {

        Collection<BarmapModel> res = dao.getData(placeId, startTime, endTime);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", false);
        modelMap.put("data", res);

        return modelMap;
    }

    @RequestMapping(value = "/api/getInfoData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getInfoData(
            @RequestParam("placeId") String placeId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime)
    {

        int count = dao.getTotalCount(placeId, startTime, endTime);
        Collection<BarmapModel> res = dao.getData(placeId, startTime, endTime);
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("error", false);
        modelMap.put("total", count);
        modelMap.put("data", res);

        return modelMap;
    }

}
