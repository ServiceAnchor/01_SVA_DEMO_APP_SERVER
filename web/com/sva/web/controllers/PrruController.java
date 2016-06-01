package com.sva.web.controllers;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.sva.common.XmlParser;
import com.sva.dao.MapsDao;
import com.sva.dao.PrruDao;
import com.sva.model.MapsModel;
import com.sva.model.PrruModel;

@Controller
@RequestMapping(value = "/pRRU")
public class PrruController
{
    private static Logger logger = Logger.getLogger(PrruController.class);

    @Autowired
    private PrruDao dao;

    @Autowired
    private MapsDao mapDao;

    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request,
            Model model)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Object userName = request.getSession().getAttribute("username");
        Collection<MapsModel> store = new ArrayList<MapsModel>(10);
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        Collection<MapsModel> c = new ArrayList<MapsModel>(10);
        if ("admin".equals(userName))
        {
            c = mapDao.getMapDataFromPrru();
        }
        else
        {
            // storeides = accountDao.selectStore(userName);
            if (storeides.size() > 0)
            {
                String storeid = storeides.get(0);
                String[] stores = storeid.split(",");
                for (int i = 0; i < stores.length; i++)
                {
                    store = mapDao.getMapDataFromPrruByStoreid(Integer
                            .parseInt(stores[i]));
                    if (store != null)
                    {
                        if (c == null)
                        {
                            c = store;
                        }
                        else if (!store.isEmpty())
                        {
                            c.addAll(store);
                        }
                    }
                }
            }
        }

        modelMap.put("error", null);
        modelMap.put("data", c);
        return modelMap;
    }

    @RequestMapping(value = "/api/getPrruInfo", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getPrruInfo(Model model,
            @RequestParam("floorNo") String floorNo)
    {
        Collection<PrruModel> ResultList = dao.getPrruInfoByflooNo(floorNo);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("error", null);
        modelMap.put("data", ResultList);
        return modelMap;
    }

    @RequestMapping(value = "/api/saveData")
    public String saveData(
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request, PrruModel msgMngModel, ModelMap model,
            @RequestParam("floor") String floorNo)
    {
        String fileName = file.getOriginalFilename();
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
        File targetFile = new File(path, fileName);
        List<PrruModel> str = null;
        File f = null;
        String nu = "";
        if (!msgMngModel.getFloorNo().equals(nu))
        {
            if (fileName != nu)
            {
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                try
                {
                    file.transferTo(targetFile);

                }
                catch (Exception e)
                {
                    logger.error(e.getMessage());
                }
                f = new File(path + '/' + fileName);
                try
                {
                    XmlParser xml = new XmlParser();
                    xml.init(f);
                    str = xml.getAttrVal(floorNo, msgMngModel.getPlaceId(),
                            "//Project/Floors/Floor/NEs/NE", "id", "name", "x",
                            "y", "neCode");
                    dao.deleteInfo(msgMngModel.getFloorNo());
                    int st = str.size();
                    for (int i = 0; i < st; i++)
                    {
                        dao.saveInfo(str.get(i));
                    }
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage());
                }

            }
            else
            {
                try
                {
                    dao.updateInfo(msgMngModel.getFloorNo(), floorNo);
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage());
                }
            }
        }
        else
        {
            if (!targetFile.exists())
            {
                targetFile.mkdirs();
            }
            try
            {
                file.transferTo(targetFile);
            }
            catch (Exception e)
            {
                logger.error(e.getMessage());
            }
            f = new File(path + '/' + fileName);
            try
            {
                XmlParser xml = new XmlParser();
                xml.init(f);
                str = xml.getAttrVal(floorNo, msgMngModel.getPlaceId(),
                        "//Project/Floors/Floor/NEs/NE", "id", "name", "x",
                        "y", "neCode");
                int st = str.size();
                for (int i = 0; i < st; i++)
                {
                    dao.saveInfo(str.get(i));
                }
            }
            catch (Exception e)
            {
                logger.error(e.getMessage());
            }

            return "redirect:/home/showpRRU";
        }

        return "redirect:/home/showpRRU";
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteMsgData(Model model,
            @RequestParam("floorNo") String floorNo)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
            dao.deleteInfo(floorNo);
        }
        catch (Exception e)
        {
            Logger.getLogger(MessageController.class).info(e.getStackTrace());
        }
        return modelMap;
    }

    @RequestMapping(value = "/api/getSignal", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getSignal()
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        List<Map<String, Object>> res = dao.getSignal();

        modelMap.put("data", res);

        return modelMap;
    }

    @RequestMapping(value = "/api/checkStore", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> checkStore(Model model,
            @RequestParam("id") String id, @RequestParam("floor") String floor)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String nu = "";
        int i = 0;
        if (id == nu)
        {
            i = dao.getPrruInfoByflooNo(floor).size();
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
            i = dao.checkByFloorNo(floor, id);
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
