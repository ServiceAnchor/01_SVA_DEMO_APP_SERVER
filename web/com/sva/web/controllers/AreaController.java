package com.sva.web.controllers;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.ConvertUtil;
import com.sva.common.HttpUtil;
import com.sva.dao.AreaDao;
import com.sva.dao.LocationDao;
import com.sva.dao.MapsDao;
import com.sva.dao.SvaDao;
import com.sva.model.AreaModel;
import com.sva.model.MapsModel;
import com.sva.model.SvaModel;

@Controller
@RequestMapping(value = "/input")
public class AreaController
{

    @Autowired
    private AreaDao dao;
    
    @Autowired
    private SvaDao svaDao;
    
    @Autowired
    private MapsDao mapDao;

    private static Logger log = Logger.getLogger(LocationDao.class);

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getInputData(HttpServletRequest request,
            Model model) throws SQLException
    {
        Collection<AreaModel> ResultList = new ArrayList<AreaModel>(10);
        // List<String> storeides = new ArrayList<String>();
        Collection<AreaModel> store = new ArrayList<AreaModel>(10);
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if (("admin").equals(userName))
        {

            ResultList = dao.doquery();
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
                    store = dao.doqueryAll(Integer.parseInt(stores[i]));
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
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("data", ResultList);

        return modelMap;
    }

    @RequestMapping(value = "/api/saveData")
    public String saveInputData(HttpServletRequest request, ModelMap model,
            AreaModel inputModel)
    {
        // 保存
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
        String nu = "";
        if (inputModel.getId().equals(nu))
        {

            Logger.getLogger(MapController.class).debug(path);

            // 保存
            try
            {
                dao.saveAreaInfo(inputModel);
            }
            catch (Exception e)
            {
                Logger.getLogger(MapController.class).info(e.getMessage());
            }

            return "redirect:/home/showInputMng";

        }
        else
        {
            try
            {
                dao.updateAreaInfo(inputModel);
            }
            catch (SQLException e)
            {
                log.info(e.getMessage());
            }

            return "redirect:/home/showInputMng";
        }

    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteInputData(Model model,
            @RequestParam("xSpot") String xSpot,
            @RequestParam("ySpot") String ySpot,
            @RequestParam("x1Spot") String x1Spot,
            @RequestParam("y1Spot") String y1Spot,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("categoryId") String categoryId)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = dao.deleteArea(xSpot, ySpot, x1Spot, y1Spot, floorNo,
                    categoryId);
        }
        catch (Exception e)
        {
            Logger.getLogger(AreaController.class).info(e.getStackTrace());
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

    @RequestMapping(value = "/api/getArea", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getArea(HttpServletRequest request,
            ModelMap model, AreaModel areaMode,
            @RequestParam("zSel") String zSel)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<AreaModel> areaes = new ArrayList<AreaModel>(10);
        try
        {
            areaes = dao.selectArea(zSel);
        }
        catch (Exception e)
        {
            log.info(e.getMessage());
        }
        modelMap.put("error", null);
        modelMap.put("data", areaes);
        return modelMap;
    }

    @RequestMapping(value = "/api/disableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> disableData(
            @RequestParam("areaId") String areaId)
    {
        log.info("Params[areaId]:" + areaId);
        List<AreaModel> areaList = dao.getAreaByAreaId(areaId);
        if(areaList == null || areaList.isEmpty())
        {
            return null;
        }
        AreaModel area = areaList.get(0);
        
        Collection<MapsModel> mapList = mapDao.getMapDetail(String.valueOf(area.getFloorNo()));
        String mapId = "";
        for(MapsModel map : mapList)
        {
            mapId = String.valueOf(map.getMapid());
            break;
        }
       
        Collection<SvaModel> svaList = svaDao.queryByStoreId2(String.valueOf(area.getPlaceId()));
        String token = null;
        HttpUtil capi = null;
        boolean result = true;
        Exception ex = null;
        String jsonStr = null;
        String charset = null;
        String content = null;
        String url = null;
        Double x1 = area.getxSpot().doubleValue() * 10;
        Double y1 = area.getySpot().doubleValue() * 10;
        Double x2 = area.getX1Spot().doubleValue() * 10;
        Double y2 = area.getY1Spot().doubleValue() * 10;
        try
        {
            for (SvaModel sva : svaList)
            {

                url = "https://" + sva.getIp() + ':' + sva.getTokenProt()
                        + "/v3/auth/tokens";
                content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                        + sva.getUsername()
                        + "\",\"password\": \""
                        + sva.getPassword() + "\"}}}}}";
                charset = "UTF-8";
                log.debug("from ip:" + sva.getIp() + ",getToken url:" + url);
                capi = new HttpUtil();

                token = capi.httpsPost(url, content, charset);
                // subscribe url:
                // https://sva_server_ip:9001/enabler/catalog/locationstreamreg/json/v1.0
                // anoymous subscribe url:
                // https://sva_server_ip:9001/enabler/catalog/locationstreamanonymousreg/json/v1.0
                //3.4   区域定义
                url = "https://" + sva.getIp() + ':' + sva.getTokenProt()
                        + "/enabler/zonedef/json/v1.0";
                if(area.getZoneId() < 0)
                {
                    log.debug("area Id:"+area.getId()+",zoneid is null");
                }
                else
                {
                    content = "{\"appid\":\"" + sva.getUsername()
                            + "\",\"zoneid\":"+area.getZoneId()+"}";
                    log.debug("zonedef ip:" + sva.getIp() + ",unsubscription url:" + url
                            + " content:" + content);
                    jsonStr = capi.subscription(url, content, token, "DELETE");
                    log.debug("area Id:"+area.getId()+",Zone definition unSubscription respone:" + jsonStr);
                }
                
                dao.updateZoneIdToNull(area.getId());
                dao.updateStatus(areaId);
                
                //电子围栏订阅
                url = "https://" + sva.getIp() + ':' + sva.getTokenProt()
                        + "/enabler/catalog/geofencingcancel/json/v1.0";
                content = "{\"appid\":\"" + sva.getUsername()+ "\"}";
                jsonStr = capi.subscription(url, content, token, "DELETE");
                log.debug("area Id:"+area.getId()+",geofencing unSubscription respone:" + jsonStr);
            }
        }
        catch (KeyManagementException e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("NoSuchAlgorithmException.", e);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("IOException.", e);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("Exception.", e);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/api/enableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> enableData(@RequestParam("areaId") String areaId)
    {
        log.info("Params[areaId]:" + areaId);
        List<AreaModel> areaList = dao.getAreaByAreaId(areaId);
        if(areaList == null || areaList.isEmpty())
        {
            return null;
        }
        AreaModel area = areaList.get(0);
        
        Collection<MapsModel> mapList = mapDao.getMapDetail(String.valueOf(area.getFloorNo()));
        String mapId = "";
        for(MapsModel map : mapList)
        {
            mapId = String.valueOf(map.getMapid());
            break;
        }
       
        Collection<SvaModel> svaList = svaDao.queryByStoreId2(String.valueOf(area.getPlaceId()));
        String token = null;
        HttpUtil capi = null;
        boolean result = true;
        Exception ex = null;
        String jsonStr = null;
        String charset = null;
        String content = null;
        String url = null;
        Double x1 = area.getxSpot().doubleValue() * 10;
        Double y1 = area.getySpot().doubleValue() * 10;
        Double x2 = area.getX1Spot().doubleValue() * 10;
        Double y2 = area.getY1Spot().doubleValue() * 10;
        try
        {
            for (SvaModel sva : svaList)
            {

                url = "https://" + sva.getIp() + ':' + sva.getTokenProt()
                        + "/v3/auth/tokens";
                content = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\": {\"user\": {\"domain\": \"Api\",\"name\": \""
                        + sva.getUsername()
                        + "\",\"password\": \""
                        + sva.getPassword() + "\"}}}}}";
                charset = "UTF-8";
                log.debug("from ip:" + sva.getIp() + ",getToken url:" + url);
                capi = new HttpUtil();

                token = capi.httpsPost(url, content, charset);
                // subscribe url:
                // https://sva_server_ip:9001/enabler/catalog/locationstreamreg/json/v1.0
                // anoymous subscribe url:
                // https://sva_server_ip:9001/enabler/catalog/locationstreamanonymousreg/json/v1.0
                //3.4   区域定义
                url = "https://" + sva.getIp() + ':' + sva.getTokenProt()
                        + "/enabler/zonedef/json/v1.0";
                if(area.getZoneId() > 0)
                {
                    content = "{\"appId\":\"" + sva.getUsername()
                            + "\",\"zone\":{"
                            + "\"mapid\":"+mapId+",\"zoneid\":"+area.getZoneId()+",\"zonetype\":\"rectangle\",\"point\":[{\"x\":"+x1.intValue()+",\"y\":"+y1.intValue()+"},{\"x\":"+x2.intValue()+",\"y\":"+y2.intValue()+"}]}" + "}";

                    log.debug("zonedef ip:" + sva.getIp() + ",subscription url:" + url
                            + " content:" + content);
                }
                else
                {
                    content = "{\"appId\":\"" + sva.getUsername()
                            + "\",\"zone\":{"
                            + "\"mapid\":"+mapId+",\"zonetype\":\"rectangle\",\"point\":[{\"x\":"+x1.intValue()+",\"y\":"+y2.intValue()+"},{\"x\":"+x2.intValue()+",\"y\":"+y1.intValue()+"}]}" + "}";
                        log.debug("zonedef ip:" + sva.getIp() + ",subscription url:" + url
                            + " content:" + content);
                }
                jsonStr = capi.subscription(url, content, token, "POST");
                JSONObject jsonObj = JSONObject.fromObject(jsonStr);
                log.debug("area Id:"+area.getId()+",Zone definition respone:" + jsonStr);
                String zoneId ="";
                if(jsonObj.containsKey("ADD ZONE"))
                {
                    JSONArray list = jsonObj.getJSONArray("ADD ZONE");
                    JSONObject object = (JSONObject)list.get(0);
                    zoneId = object.getString("ZONEID");
                }
                else if(jsonObj.containsKey("\u589e\u52a0\u533a\u57df"))
                {
                    JSONArray list = jsonObj.getJSONArray("\u589e\u52a0\u533a\u57df");
                    JSONObject object = (JSONObject)list.get(0);
                    zoneId = object.getString("\u533a\u57dfID");
                }
                dao.updateZoneId(area.getId(), zoneId);
                dao.updateStatus1(areaId);
                
                //电子围栏订阅
                url = "https://" + sva.getIp() + ':' + sva.getTokenProt()
                        + "/enabler/catalog/geofencing/json/v1.0";
                content = "{\"appid\":\"" + sva.getUsername()
                        + "\"}";
                jsonStr = capi.subscription(url, content, token, "POST");
                log.debug("area Id:"+area.getId()+",geofencing Subscription respone:" + jsonStr);
            }
        }
        catch (KeyManagementException e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("KeyManagementException.", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("NoSuchAlgorithmException.", e);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("IOException.", e);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            result = false;
            ex = e;
            log.error("Exception.", e);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>(2);

        modelMap.put("error", null);
        modelMap.put("result", result);
        return modelMap;
    }

}
