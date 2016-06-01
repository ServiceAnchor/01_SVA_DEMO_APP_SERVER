package com.sva.web.controllers;

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

import com.sva.common.HttpUtil;
import com.sva.common.conf.Params;
import com.sva.dao.SvaDao;
import com.sva.model.SvaModel;

@Controller
@RequestMapping(value = "/svalist")
public class SvaController
{

    @Autowired
    private SvaDao dao;

    private static Logger log = Logger.getLogger(SvaController.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request, Model model)
    {

        List<SvaModel> list = new ArrayList<SvaModel>(10);
        List<SvaModel> listes = new ArrayList<SvaModel>(10);
        List<SvaModel> listTemp = new ArrayList<SvaModel>(10);
        Collection<SvaModel> ResultList = new ArrayList<SvaModel>(10);
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        ResultList = dao.doquery();
        SvaModel sm = null;
        List<String> store = null;
        for (SvaModel l : ResultList)
        {
            sm = new SvaModel();
            sm.setId(l.getId());
            // 赋值商场地点的值
            store = dao.storeBySva(l.getId());
            sm.setPosition(store);
            sm.setIp(l.getIp());
            sm.setName(l.getName());
            sm.setUsername(l.getUsername());
            sm.setPassword(l.getPassword());
            sm.setStatus(l.getStatus());
            sm.setType(l.getType());
            sm.setTokenProt(l.getTokenProt());
            sm.setBrokerProt(l.getBrokerProt());
            list.add(sm);
        }
        int liSize = list.size();
        for (int i = 0; i < liSize; i++)
        {
            if (list.get(i).getPosition().size() > 0)
            {
                listes.add(list.get(i));
            }
        }
        if (!("admin").equals(userName))
        {
            String place = null;
            String placeid = null;
            int listSize = listes.size();
            for (int i = 0; i < listSize; i++)
            {
                place = listes.get(i).getPosition().get(0);
                placeid = dao.storeIdByName(place);
                if (storeides.size() > 0)
                {
                    String storeid = storeides.get(0);
                    String[] stores = storeid.split(",");
                    for (int j = 0; j < stores.length; j++)
                    {
                        if (placeid.equals(stores[j]))
                        {
                            listTemp.add(listes.get(i));
                        }
                    }
                }
            }
            listes = listTemp;
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", listes);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData")
    public String saveSvaData(HttpServletRequest request, ModelMap model,
            SvaModel svaModel)
    {
        Integer id = Params.ZERO;
        Integer newId = Params.ZERO;
        String st = "";
        if (svaModel.getId() != st)
        {
            id = Integer.parseInt(svaModel.getId());
        }
        // 保存
        try
        {
            if (id > 0)
            {
                newId = id;
                dao.updateSvaInfo(svaModel);
            }
            else
            {
                newId = dao.saveSvaInfo(svaModel);
            }
            dao.deleteMapBySva(newId);
            dao.addMapper(newId, svaModel.getPosition());
        }
        catch (SQLException e)
        {
            log.info(e.getStackTrace());
        }

        return "redirect:/home/showSvaMng";
    }

    @RequestMapping(value = "/api/disableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> disableData(Model model,
            @RequestParam("id") String id)
    {
        log.info("Params[id]:" + id);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = dao.disableSva(id);
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
     * 启用sva
     * 
     * @param model
     * @param id
     *            要启用的sva的id
     * @return
     */
    @RequestMapping(value = "/api/enableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> enableData(Model model,
            @RequestParam("id") String id)
    {
        log.info("Params[id]:" + id);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = dao.enableSva(id);
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
     * 删除sva
     * 
     * @param model
     * @param id
     *            要删除的sva的id
     * @return
     */
    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(Model model,
            @RequestParam("id") String id)
    {
        log.info("Params[id]:" + id);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = dao.deleteSvaTemp(id);
//            dao.deleteMapBySva(Integer.parseInt(id));
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

    @RequestMapping(value = "/api/getEnabled", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getStartData(Model model)
    {

        List<SvaModel> list = new ArrayList<SvaModel>(10);
        Collection<SvaModel> ResultList = new ArrayList<SvaModel>(10);
        ResultList = dao.getEnabled();
        SvaModel sm = null;
        for (SvaModel l : ResultList)
        {
            sm = new SvaModel();
            sm.setId(l.getId());
            sm.setIp(l.getIp());
            sm.setName(l.getName());
            sm.setUsername(l.getUsername());
            sm.setPassword(l.getPassword());
            sm.setStatus(l.getStatus());
            list.add(sm);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", list);

        return modelMap;
    }

    @RequestMapping(value = "/api/checkName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkName(@RequestParam("id") String id,
            @RequestParam("svaName") String name,
            @RequestParam("ip") String ip,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("token") String tokenNumber)
    {
        HttpUtil capi = null;
        String token = null;
        String url = "https://" + ip + ':' + tokenNumber + "/v3/auth/tokens";
        String content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                + userName + "\",\"password\": \"" + password + "\"}}}}}";
        String charset = "UTF-8";
        log.debug("from ip:" + ip + ",getToken url:" + url);
        capi = new HttpUtil();

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int i = 0;
        String nu = "";
        try
        {
            token = capi.httpsPost(url, content, charset);
            if (id == nu)
            {
                i = dao.checkSvaByName(name);
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
                i = dao.checkSvaByName1(name, id);
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
        catch (Exception e)
        {
            String st = e.getMessage();
            if ("Connection timed out: connect".equals(st))
            {
                modelMap.put("ip", true);
                return modelMap;
            }
            else
            {
                modelMap.put("error", true);
                return modelMap;
            }
        }
    }
}
