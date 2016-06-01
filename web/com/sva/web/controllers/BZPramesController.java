package com.sva.web.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
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
import com.sva.common.ConvertUtil;
import com.sva.dao.BZPramesDao;
import com.sva.model.BZPramesModel;

@Controller
@RequestMapping(value = "/content")
public class BZPramesController
{

    @Autowired
    private BZPramesDao bzPramesDao;

    private static Logger log = Logger.getLogger(ParamController.class);

    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(Model model)
    {
        log.info("ParamController:getTableData");

        Collection<BZPramesModel> ResultList = bzPramesDao.doquery();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(
            @RequestParam("densitySel") int densitySel,
            @RequestParam("radiusSel") int radiusSel,
            @RequestParam("densitySel1") int densitySel1,
            @RequestParam("radiusSel1") int radiusSel1,
            @RequestParam("densitySel2") int densitySel2,
            @RequestParam("radiusSel2") int radiusSel2,
            @RequestParam("densitySel3") int densitySel3,
            @RequestParam("radiusSel3") int radiusSel3,
            @RequestParam("densitySel4") int densitySel4,
            @RequestParam("radiusSel4") int radiusSel4,
            @RequestParam("densitySel5") int densitySel5,
            @RequestParam("radiusSel5") int radiusSel5,
            @RequestParam("densitySel6") int densitySel6,
            @RequestParam("radiusSel6") int radiusSel6,
            @RequestParam("densitySel7") int densitySel7,
            @RequestParam("radiusSel7") int radiusSel7,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("floorNo2") String floorNo2,
            @RequestParam("floorNo3") String floorNo3,
            @RequestParam("floorNo4") String floorNo4,
            @RequestParam("floorNo5") String floorNo5,
            @RequestParam("floorNo6") String floorNo6,
            @RequestParam("floorNo7") String floorNo7,
            @RequestParam("floorNo8") String floorNo8,
            @RequestParam("periodSel") int periodSel,
            @RequestParam("coefficient") Double coefficient,
            @RequestParam("startTime") String startTime)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        BigDecimal bd = new BigDecimal(floorNo);
        BigDecimal bd2 = new BigDecimal(floorNo2);
        BigDecimal bd3 = new BigDecimal(floorNo3);
        BigDecimal bd4 = new BigDecimal(floorNo4);
        BigDecimal bd5 = new BigDecimal(floorNo5);
        BigDecimal bd6 = new BigDecimal(floorNo6);
        BigDecimal bd7 = new BigDecimal(floorNo7);
        BigDecimal bd8 = new BigDecimal(floorNo8);
        BZPramesModel sm = new BZPramesModel();
        sm.setDensitySel(densitySel);
        sm.setRadiusSel(radiusSel);
        sm.setDensitySel1(densitySel1);
        sm.setRadiusSel1(radiusSel1);
        sm.setDensitySel2(densitySel2);
        sm.setRadiusSel2(radiusSel2);
        sm.setDensitySel3(densitySel3);
        sm.setRadiusSel3(radiusSel3);
        sm.setDensitySel4(densitySel4);
        sm.setRadiusSel4(radiusSel4);
        sm.setDensitySel5(densitySel5);
        sm.setRadiusSel5(radiusSel5);
        sm.setDensitySel6(densitySel6);
        sm.setRadiusSel6(radiusSel6);
        sm.setDensitySel7(densitySel7);
        sm.setRadiusSel7(radiusSel7);
        sm.setFloorNo(bd);
        sm.setFloorNo2(bd2);
        sm.setFloorNo3(bd3);
        sm.setFloorNo4(bd4);
        sm.setFloorNo5(bd5);
        sm.setFloorNo6(bd6);
        sm.setFloorNo7(bd7);
        sm.setFloorNo8(bd8);
        sm.setPeriodSel(periodSel);
        sm.setCoefficient(coefficient);
        startTime = "2016-02-15 " + startTime;
        sm.setStartTime(ConvertUtil.dateStringFormat(startTime,
                "yyyy-MM-dd HH:mm:ss"));
        sm.setId(1);
        try
        {
            bzPramesDao.updateBZInfo(sm);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        modelMap.put("data", null);
        return modelMap;
    }

}
