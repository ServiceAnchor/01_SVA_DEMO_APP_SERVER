package com.sva.web.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.CategoryDao;
import com.sva.model.CategoryModel;

@Controller
@RequestMapping(value = "/category")
public class CategoryController
{

    @Autowired
    private CategoryDao dao;

    private static Logger log = Logger.getLogger(CategoryController.class);

    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(Model model)
    {
        log.info("CategoryController:getTableData");

        Collection<CategoryModel> ResultList = dao.doquery();
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam("name") String name)
    {
        log.info("CategoryController:saveData:: " + id + ',' + name);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Date current = new Date();
        CategoryModel sm = new CategoryModel();
        sm.setName(name);
        sm.setCreateTime(current);
        sm.setUpdateTime(current);
        // 保存
        try
        {
            if ("".equals(id))
            {
                dao.saveInfo(sm);
            }
            else
            {
                sm.setId(Integer.parseInt(id));
                dao.updateInfo(sm);
            }
        }
        catch (DuplicateKeyException e)
        {
            log.info(e.getStackTrace());
            modelMap.put("error", "category name can not be the same");
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
            modelMap.put("error", e.getStackTrace());
        }

        modelMap.put("data", null);
        return modelMap;
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(@RequestParam("id") String id)
    {
        log.info("CategoryController:deleteData::" + id);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
            dao.deleteById(id);
            // dao.deleteMapByStore(id);
        }
        catch (Exception e)
        {
            log.info(e.getStackTrace());
            modelMap.put("error", e.getStackTrace());
        }

        return modelMap;
    }
}
