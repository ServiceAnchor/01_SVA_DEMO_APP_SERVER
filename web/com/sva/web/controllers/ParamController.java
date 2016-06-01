package com.sva.web.controllers;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.ParamDao;
import com.sva.model.ParamModel;

@Controller
@RequestMapping(value = "/paramconfig")
public class ParamController
{

    @Autowired
    private ParamDao dao;

    private static Logger log = Logger.getLogger(ParamController.class);

    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(Model model)
    {
        log.info("ParamController:getTableData");

        Collection<ParamModel> ResultList = dao.doquery();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(
            @RequestParam(value = "id", required = false) int id,
            @RequestParam("banThreshold") Double banThreshold,
            @RequestParam("filterLength") Double filterLength,
            @RequestParam("horizontalWeight") Double horizontalWeight,
            @RequestParam("ongitudinalWeight") Double ongitudinalWeight,
            @RequestParam("maxDeviation") Double maxDeviation,
            @RequestParam("exceedMaxDeviation") Double exceedMaxDeviation,
            @RequestParam("correctMapDirection") Double correctMapDirection,
            @RequestParam("restTimes") Double restTimes,
            @RequestParam("filterPeakError") Double filterPeakError,
            @RequestParam("peakWidth") Double peakWidth,
            @RequestParam("step") Double step

    )
    {
        log.info("ParamController:saveData:: " + id + ',' + banThreshold + ','
                + filterLength + ',' + horizontalWeight + ',' + ongitudinalWeight + ','
                + maxDeviation + ',' + exceedMaxDeviation);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Date current = new Date();
        ParamModel sm = new ParamModel();
        sm.setBanThreshold(banThreshold);
        sm.setFilterLength(filterLength);
        sm.setHorizontalWeight(horizontalWeight);
        sm.setOngitudinalWeight(ongitudinalWeight);
        sm.setMaxDeviation(maxDeviation);
        sm.setExceedMaxDeviation(exceedMaxDeviation);
        sm.setUpdateTime(current.getTime());
        sm.setId(id);
        sm.setCorrectMapDirection(correctMapDirection);
        sm.setRestTimes(restTimes);
        sm.setFilterPeakError(filterPeakError);
        sm.setPeakWidth(peakWidth);
        sm.setStep(step);

        try
        {
            dao.saveData(sm);
        }
        catch (SQLException e)
        {
            log.info(e.getMessage());
        }
        modelMap.put("data", null);
        return modelMap;
    }

}
