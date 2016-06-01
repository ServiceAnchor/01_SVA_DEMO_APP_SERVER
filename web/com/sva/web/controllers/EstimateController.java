package com.sva.web.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.EstimateDao;
import com.sva.model.EstimateModel;

@Controller
@RequestMapping(value = "/estimate")
public class EstimateController
{

    @Autowired
    private EstimateDao dao;

    private static Logger log = Logger.getLogger(EstimateController.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getMsgData(HttpServletRequest request,
            Model model)
    {

        Collection<EstimateModel> ResultList = new ArrayList<EstimateModel>(10);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<EstimateModel> store = new ArrayList<EstimateModel>(10);
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if ("admin".equals(userName))
        {
            ResultList = dao.doquery();
        }
        else
        {
            if (storeides.size() > 0)
            {
                String storeid = storeides.get(0);
                String[] stores = storeid.split(",");
                for (int i = 0; i < stores.length; i++)
                {
                    store = dao.doqueryByStoreid(Integer.parseInt(stores[i]));
                    if (store != null)
                    {
                        if (ResultList == null)
                        {
                            ResultList = store;
                        }
                        else if (!store.isEmpty())
                        {
                            ResultList.addAll(store);
                        }
                    }
                }
            }
        }
        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData")
    public String saveData(HttpServletRequest request, ModelMap model,
            EstimateModel esModel)
    {
        String nu = "";
        double d = calcD(esModel);
        double scene = esModel.getType().doubleValue();
        esModel.setD(BigDecimal.valueOf(d));
        esModel.setDeviation(BigDecimal.valueOf(d * scene));

        // 保存
        int id = 0;
        try
        {
            if (!esModel.getId().equals(nu))
            {
                id = Integer.parseInt(esModel.getId());
            }

            if (id > 0)
            {
                dao.updateInfo(esModel);
            }
            else
            {
                dao.saveInfo(esModel);
            }
        }
        catch (SQLException e)
        {
            log.info(e.getStackTrace());
        }

        return "redirect:/home/showEstimate";
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteMsgData(Model model,
            @RequestParam("id") String id)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = dao.deleteInfo(id);
        }
        catch (SQLException e)
        {
            log.info(e.getStackTrace());
        }
        if (result > 0)
        {
            modelMap.put("error", null);
        }
        else
        {
            modelMap.put("error", true);
        }

        return modelMap;
    }

    /**
     * 计算d值
     * 
     * @param em
     * @return
     */
    private double calcD(EstimateModel em)
    {
        double result = 0;
        double a = em.getA().doubleValue();
        double b = em.getB().doubleValue();
        int n = em.getN();
        if (n > 1)
        {
            result = (a + b + 2 * Math.sqrt(a * b * n)) / (2 * n - 2);
        }
        return result;
    }

    @RequestMapping(value = "/api/checkFloorNo", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkFloorNo(Model model,
            @RequestParam("id") String id, @RequestParam("floor") String floor)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String nu = "";
        int i = 0;
        if (id == nu)
        {

            i = dao.checkByFloorNo(floor);
            if (i > 0)
            {
                modelMap.put("data", false);
                return modelMap;
            }
            else
            {
                modelMap.put("data", true);
                return modelMap;
            }
        }
        else
        {
            i = dao.checkByFloorNo1(floor, id);
            if (i > 0)
            {
                modelMap.put("data", false);
                return modelMap;
            }
            else
            {
                modelMap.put("data", true);
                return modelMap;
            }
        }
    }

}
