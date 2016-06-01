package com.sva.web.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.AccountDao;
import com.sva.dao.RoleDao;
import com.sva.dao.StoreDao;
import com.sva.model.StoreModel;

@Controller
@RequestMapping(value = "/store")
public class StoreController
{

    @Autowired
    private StoreDao dao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AccountDao accountDao;

    private static Logger log = Logger.getLogger(StoreController.class);

    /*
     * getTableData--查询部分商场 selectStore--通过用户名查询有哪些商场权限
     */
    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request,
            Model model)
    {
        log.info("StoreController:getTableData");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<String> storeides = new ArrayList<String>(10);
        Collection<StoreModel> ResultList = null;
        Collection<StoreModel> store = null;
        Object userName = request.getSession().getAttribute("username");
        // @SuppressWarnings("unchecked")
        // List<String> storeides = (List<String>)
        // request.getSession().getAttribute("storeides");
        if ("admin".equals(userName))
        {
            ResultList = dao.doStores();
        }
        else
        {
            try
            {
                storeides = accountDao.selectStore(userName);
                if (storeides.size() != 0)
                {
                    String storeid = storeides.get(0);
                    String[] stores = storeid.split(",");

                    for (int i = 0; i < stores.length; i++)
                    {
                        store = dao.doquery(stores[i]);
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
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                log.debug(e.getMessage());
            }

        }
        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    // 查询所有的商场
    @RequestMapping(value = "/api/stores", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getStores(Model model)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<StoreModel> ResultList = dao.doStores();
        modelMap.put("error", null);
        modelMap.put("data", ResultList);
        return modelMap;
    }

    @RequestMapping(value = "/api/getStoreBySvaId", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getStoreBySvaId(Model model,
            @RequestParam("svaId") String svaId)
    {
        log.info("StoreController:getStoreBySvaId::" + svaId);
        List<String> ResultList = dao
                .getStoreNameBySva(Integer.parseInt(svaId));
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(HttpServletRequest request,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam("name") String name)
    {
        log.info("StoreController:saveData:: " + id + ',' + name);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Object userName = request.getSession().getAttribute("username");
        Date current = new Date();
        StoreModel sm = new StoreModel();
        sm.setName(name);
        sm.setCreateTime(current);
        sm.setUpdateTime(current);

        // 保存
        try
        {
            if ("".equals(id))
            {
                String storeName = sm.getName();
                dao.saveInfo(sm);
                // 添加商场时自动更新到角色商场权限
                int storeId = dao.selectStoreid(storeName);
                String storeid = roleDao.queryStoreid(userName.toString())
                        + ',' + storeId;
                String stores = roleDao.queryStore(userName.toString()) + ','
                        + storeName;
                int roleid = roleDao.selectRoleid(userName.toString());
                roleDao.updateInfoStore(storeid, stores, roleid);

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
            modelMap.put("error", "sva name can not be the same");
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
        log.info("StoreController:deleteData::" + id);
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

    // @RequestMapping(value = "/api/checkName", method = { RequestMethod.POST
    // })
    // @ResponseBody
    // public Map<String, Object> checkName(HttpServletRequest request,
    // @RequestParam("param")
    // String p,@RequestParam("name") String name)
    // {
    // RequestContext requestContext = new RequestContext(request);
    // Locale myLocale = requestContext.getLocale();
    // Map<String, Object> modelMap = new HashMap<String, Object>(2);
    // try
    // {
    // dao.selectStoreSame1(name);
    //
    // }
    // catch (Exception e)
    // {
    // // TODO: handle exception
    // }
    // if (!Locale.CHINA.equals(myLocale))
    // {
    // modelMap.put("info", "same");
    // modelMap.put("status", "y");
    // }else
    // {
    // modelMap.put("info", "相同");
    // modelMap.put("status", "y");
    // }
    //
    //
    //
    // return modelMap;
    // }
}
