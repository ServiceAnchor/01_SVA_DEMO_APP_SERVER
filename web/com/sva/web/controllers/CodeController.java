package com.sva.web.controllers;

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

import com.sva.dao.CodeDao;
import com.sva.model.CodeModel;
import com.sva.web.models.CodeMngModel;

@Controller
@RequestMapping(value = "/code")
public class CodeController
{

    @Autowired
    private CodeDao daoCode;

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(Model model)
    {

        List<CodeMngModel> list = new ArrayList<CodeMngModel>(10);
        Collection<CodeModel> ResultList = new ArrayList<CodeModel>(10);
        ResultList = daoCode.getData();
        CodeMngModel cmm = null;
        for (CodeModel l : ResultList)
        {
            cmm = new CodeMngModel();
            cmm.setName(l.getName());
            cmm.setPassword(l.getPassword());
            list.add(cmm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    public String saveMsgData(HttpServletRequest request, ModelMap model,
            CodeMngModel codeMngModel)
    {
        // 保存
        // Collection<CodeModel> codeModels = null;
        // int i = daoCode.checkByName(codeMngModel.getName());
        // if (i < 1)
        // {
        try
        {
            daoCode.saveCodeInfo(codeMngModel);
        }
        catch (Exception e)
        {
            Logger.getLogger(CodeController.class).info(e.getMessage());
        }
        return "redirect:/home/showCodeMng";
        // }
        // else
        // {
        // codeModels = daoCode.getData();
        // for (CodeModel l : codeModels)
        // {
        // if (l.getName().equals(codeMngModel.getName()))
        // {
        // model.addAttribute("info", "same");
        // return "redirect:/home/showCodeMng";
        // }
        // }
        // }
        // try
        // {
        // daoCode.saveCodeInfo(codeMngModel);
        //
        // }
        // catch (Exception e)
        // {
        // Logger.getLogger(CodeController.class).info(e.getMessage());
        // }
        // return "redirect:/home/showCodeMng";
    }

    @RequestMapping(value = "/api/checkName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkName(@RequestParam("name") String name)
    {
        Map<String, Object> ma = new HashMap<String, Object>(2);
        Collection<CodeModel> codeModels = null;
        int i = daoCode.checkByName(name);
        codeModels = daoCode.getData();
        if (i > 0)
        {
            for (CodeModel l : codeModels)
            {
                if (l.getName().equals(name))
                {
                    ma.put("data", false);
                    return ma;
                }
            }
        }
        else
        {
            ma.put("data", true);
            return ma;
        }
        return ma;

    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteMsgData(Model model,
            @RequestParam("name") String name,
            @RequestParam("password") String password)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = daoCode.deleteCode(name, password);
        }
        catch (Exception e)
        {
            Logger.getLogger(CodeController.class).info(e.getMessage());
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
}
