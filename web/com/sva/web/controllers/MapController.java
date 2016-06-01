package com.sva.web.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sva.dao.MapsDao;
import com.sva.model.MapsModel;
import com.sva.web.models.MapMngModel;

@Controller
@RequestMapping(value = "/map")
public class MapController
{

    @Autowired
    private MapsDao dao;

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request,
            Model model)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<MapsModel> ResultList = new ArrayList<MapsModel>(10);
        Collection<MapsModel> store = new ArrayList<MapsModel>(10);
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

    @RequestMapping(value = "/api/deleteByFloor", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteTableData(Model model,
            HttpServletRequest request, @RequestParam("floorNo") String floorNo)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        int result1 = 0;
        int result2 = 0;
        try
        {
            // 删除图片
            String name = dao.getMapName(floorNo);
            // 文件路径
            String filedir = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
            File file = new File(filedir, name);
            if (!file.exists())
            {
                // 文件不存在;
                modelMap.put("error", true);
            }
            else
            {
                if (file.isFile())
                {
                    file.delete();
                }
                else
                {
                    modelMap.put("error", true);
                }
            }

            // 删除数据
            result = dao.deleteMapByFloor(floorNo);
        }
        catch (Exception e)
        {
            Logger.getLogger(MapController.class).info(e.getMessage());
        }
        if (result > 0 || result1 > 0 || result2 > 0)
        {
            modelMap.put("error", null);
        }
        else
        {
            modelMap.put("error", true);
        }

        return modelMap;
    }

    @RequestMapping(value = "/api/upload")
    public String upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "svgfile", required = false) MultipartFile svg,
            @RequestParam(value = "pathroutefile", required = false) MultipartFile pathFile,
            @RequestParam(value = "routefile", required = false) MultipartFile route,
            HttpServletRequest request, ModelMap model, MapMngModel mapMngModel)
            throws Exception
    {
        // mapMngModel.setPlace(svaname + "_" + mapMngModel.getPlace());
        // Collection<SvaModel> svam = svaDao.selectID(svaname);
        long updateTime = System.currentTimeMillis();
        mapMngModel.setUpdateTime(String.valueOf(updateTime));
        BigDecimal id = null;
        String nu = "";
        BigDecimal sid = (BigDecimal) mapMngModel.getFloorid();
        // 通过placeid查询出需要的商场的值
        String placeName = dao.placeByPlaceId(mapMngModel.getPlaceId());
        mapMngModel.setPlace(placeName);
        int valueid = sid.intValue();
        // for (SvaModel s : svam)
        // {
        id = new BigDecimal(10000 * mapMngModel.getPlaceId());
        if (valueid > 0)
        {
            mapMngModel.setFloorNo(id.add(mapMngModel.getFloorid()));
        }
        else
        {
            mapMngModel.setFloorNo(id.add(
                    mapMngModel.getFloorid().multiply(new BigDecimal(-1))).add(
                    new BigDecimal(5000)));
        }
        // }

        if (!mapMngModel.getId().equals(nu))
        {
            String fileName = file.getOriginalFilename();
            String svgName = svg.getOriginalFilename();
            String routeName = route.getOriginalFilename();
            String pathfileName = pathFile.getOriginalFilename();
            if (!fileName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String _ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = mapMngModel.getFloorNo() + "_"
                        + mapMngModel.getFloor() + _ext;
                mapMngModel.setPath(fileName);
                Logger.getLogger(MapController.class).debug(path);
                File targetFile = new File(path, fileName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    BufferedImage sourceImg = javax.imageio.ImageIO.read(file
                            .getInputStream());
                    file.transferTo(targetFile);

                    mapMngModel.setImgWidth(sourceImg.getWidth());
                    mapMngModel.setImgHeight(sourceImg.getHeight());
                }
                catch (Exception e)
                {
                    Logger.getLogger(MapController.class).info(e.getMessage());
                }
            }

            if (!svgName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String _ext = svgName.substring(svgName.lastIndexOf('.'));
                svgName = mapMngModel.getFloorNo() + "_"
                        + mapMngModel.getFloor() + _ext;
                mapMngModel.setSvg(svgName);
                Logger.getLogger(MapController.class).debug(svgName);
                File targetFile = new File(path, svgName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    svg.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    Logger.getLogger(MapController.class).info(e.getMessage());
                }
            }
            if (!routeName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String _ext = routeName.substring(routeName.lastIndexOf('.'));
                routeName = mapMngModel.getFloorNo() + "_"
                        + mapMngModel.getFloor() + _ext;
                mapMngModel.setRoute(routeName);
                Logger.getLogger(MapController.class).debug(svgName);
                File targetFile = new File(path, routeName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    route.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    Logger.getLogger(MapController.class).info(e.getMessage());
                }
            }
            
            if (!pathfileName.equals(nu))
            {
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/upload");
                String _ext = pathfileName.substring(pathfileName.lastIndexOf('.'));
                pathfileName = mapMngModel.getUpdateTime() + "_"
                        + mapMngModel.getFloor() + _ext;
                mapMngModel.setPathFile(pathfileName);
                Logger.getLogger(MapController.class).debug(pathfileName);
                File targetFile = new File(path, pathfileName);
                if (!targetFile.exists())
                {
                    targetFile.mkdirs();
                }
                // 修改
                try
                {
                    pathFile.transferTo(targetFile);
                }
                catch (Exception e)
                {
                    Logger.getLogger(MapController.class).info(e.getMessage());
                }
            }

            dao.updateMap(mapMngModel);
            return "redirect:/home/showMapMng";
        }
        else
        {
            Logger.getLogger(MapController.class).debug(mapMngModel.getFloor());
            String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
            saveFile(mapMngModel, file, svg, path, nu, route,pathFile);

            return "redirect:/home/showMapMng";
        }

    }

    private void getFile(File targetFile1)
    {
        if (!targetFile1.exists())
        {
            targetFile1.mkdirs();
        }
    }

    private void saveFile(MapMngModel mapMngModel, MultipartFile file,
            MultipartFile svg, String path, String nu, MultipartFile route,MultipartFile pathFile)
    {
        String fileName = file.getOriginalFilename();
        String svgName = svg.getOriginalFilename();
        String routeName = route.getOriginalFilename();
        String pathfileName = pathFile.getOriginalFilename();
        String _ext = null;
        String _ext1 = null;
        String _ext2 = null;
        String _ext3 = null;
        if (fileName != nu)
        {
            _ext = fileName.substring(fileName.lastIndexOf('.'));

        }
        if (routeName != nu)
        {
            _ext2 = routeName.substring(routeName.lastIndexOf('.'));

        }
        if (svgName != nu)
        {
            _ext1 = svgName.substring(svgName.lastIndexOf('.'));

        }
        if (pathfileName != nu)
        {
            _ext3 = pathfileName.substring(pathfileName.lastIndexOf('.'));
            
        }

        fileName = mapMngModel.getFloorNo() + "_" + mapMngModel.getFloor()
                + _ext;
        svgName = mapMngModel.getFloorNo() + "_" + mapMngModel.getFloor()
                + _ext1;
        routeName = mapMngModel.getFloorNo() + "_" + mapMngModel.getFloor()
                + _ext2;
        pathfileName = mapMngModel.getUpdateTime() + "_" + mapMngModel.getFloor()
        + _ext3;
        if (_ext != null)
        {
            mapMngModel.setPath(fileName);
        }
        if (_ext1 != null)
        {
            mapMngModel.setSvg(svgName);
        }
        if (_ext2 != null)
        {
            mapMngModel.setRoute(routeName);
        }
        if (_ext3 != null)
        {
            mapMngModel.setPathFile(pathfileName);
        }

        File targetFile = new File(path, fileName);
        File targetFile1 = new File(path, svgName);
        File targetFile2 = new File(path, routeName);
        File targetFile3 = new File(path, pathfileName);
        getFile(targetFile);
        getFile(targetFile1);
        getFile(targetFile2);
        getFile(targetFile3);
        // 保存
        try
        {
            BufferedImage sourceImg = javax.imageio.ImageIO.read(file
                    .getInputStream());
            file.transferTo(targetFile);
            svg.transferTo(targetFile1);
            route.transferTo(targetFile2);
            pathFile.transferTo(targetFile3);

            mapMngModel.setImgWidth(sourceImg.getWidth());
            mapMngModel.setImgHeight(sourceImg.getHeight());
            // svg.transferTo(targetFile1);
            dao.saveMapInfo(mapMngModel);

        }
        catch (Exception e)
        {
            Logger.getLogger(MapController.class).info(e.getMessage());
        }

    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex)
    {
        String info = "";
        if (ex instanceof MaxUploadSizeExceededException)
        {
            info = "Max";
        }
        else
        {
            info = "未知错误: " + ex.getMessage();
        }
        ModelAndView model = new ModelAndView("redirect:/home/showMapMng");
        model.addObject("info", info);
        return model;

    }

    @RequestMapping(value = "/api/check", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> check(Model model,
            @RequestParam("id") String id, @RequestParam("place") String place,
            @RequestParam("floor") String floor,
            @RequestParam("floorNo") String floorId)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String nu = "";
        int floorid = Integer.parseInt(floorId);
        int floorNo = 0;
        int placeId = Integer.parseInt(place);
        if (floorid > 0)
        {
            floorNo = 10000 * placeId + floorid;
        }
        else
        {
            floorNo = 10000 * placeId + 5000 - floorid;
        }

        if (id == nu)
        {
            return check1(place, floor, floorNo, modelMap);
        }
        else
        {
            return check2(id, place, floorNo, floor, modelMap);
        }
    }

    private Map<String, Object> check2(String id, String place, int floorNo,
            String floor, Map<String, Object> modelMap)
    {
        int i = 0;
        int j = 0;
        i = dao.chekByFloorNo1(floorNo, id);
        if (i > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "0");
            return modelMap;
        }
        j = dao.checkByPlace1(place, floor, id);
        if (j > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "1");
            return modelMap;
        }
        else
        {
            modelMap.put("data", true);
            return modelMap;
        }
    }

    private Map<String, Object> check1(String place, String floor, int floorNo,
            Map<String, Object> modelMap)
    {
        int i = 0;
        int j = 0;
        i = dao.chekByFloorNo(floorNo);
        if (i > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "0");
            return modelMap;
        }
        j = dao.checkByPlace(place, floor);
        if (j > 0)
        {
            modelMap.put("data", false);
            modelMap.put("same", "1");
            return modelMap;
        }
        else
        {
            modelMap.put("data", true);
            return modelMap;
        }
    }

}
