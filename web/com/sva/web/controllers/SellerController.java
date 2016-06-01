package com.sva.web.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

import com.sva.dao.SellerDao;
import com.sva.model.SellerModel;
import com.sva.web.models.SellerMngModel;

@Controller
@RequestMapping(value = "/seller")
public class SellerController
{

    @Autowired
    private SellerDao dao;

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request,
            Model model)
    {

        Collection<SellerModel> ResultList = new ArrayList<SellerModel>(10);

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<SellerModel> store = new ArrayList<SellerModel>(10);
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

    @RequestMapping(value = "/api/saveData", method = {RequestMethod.POST})
    public String saveSellerData(HttpServletRequest request, ModelMap model,
            SellerMngModel sellerMngModel,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "file1", required = false) MultipartFile file1)
    {
        // 保存
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        String nu = "";
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
        if (sellerMngModel.getId().equals(nu))
        {
            saveFile(sellerMngModel, file, file1, d, nu, path);

            return "redirect:/home/showSellerMng";

        }
        else
        {
            savaFile1(sellerMngModel, file, file1, d, nu, path);

            return "redirect:/home/showSellerMng";
        }

    }

    private void savaFile1(SellerMngModel sellerMngModel, MultipartFile file,
            MultipartFile file1, Date d, String nu, String path)
    {
        try
        {
            String fileName = file.getOriginalFilename();
            String fileName1 = file1.getOriginalFilename();
            if (fileName.equals(nu) && fileName1.equals(nu))
            {
                dao.updateSeller(sellerMngModel);
            }
            if (fileName.equals(nu) && !fileName1.equals(nu))
            {
                String _ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + _ext1;
                sellerMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                dao.updateSeller1(sellerMngModel);
            }
            if (!fileName.equals(nu) && fileName1.equals(nu))
            {
                String _ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + _ext;
                sellerMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                dao.updateSeller2(sellerMngModel);
            }
            if (!fileName.equals(nu) && !fileName1.equals(nu))
            {
                String _ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + _ext;
                sellerMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                String _ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + _ext1;
                sellerMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                dao.updateSeller3(sellerMngModel);
            }

        }
        catch (Exception e)
        {
            Logger.getLogger(MapController.class).info(e.getMessage());
        }
    }

    private void getFile(File targetFile)
    {
        if (!targetFile.exists())
        {
            targetFile.mkdirs();
        }
    }

    private void saveFile(SellerMngModel sellerMngModel, MultipartFile file,
            MultipartFile file1, Date d, String nu, String path)
    {
        String fileName = file.getOriginalFilename();
        String fileName1 = file1.getOriginalFilename();
        String _ext = null;
        String _ext1 = null;
        if (fileName != nu)
        {
            _ext = fileName.substring(fileName.lastIndexOf('.'));

        }
        if (fileName1 != nu)
        {
            _ext1 = fileName1.substring(fileName1.lastIndexOf('.'));

        }
        fileName = d.getTime() + _ext;
        fileName1 = d.getTime() + _ext1;
        if (_ext != null)
        {
            sellerMngModel.setPictruePath(fileName);
        }
        if (_ext1 != null)
        {
            sellerMngModel.setMoviePath(fileName1);
        }
        Logger.getLogger(MapController.class).debug(path);
        File targetFile = new File(path, fileName);
        File targetFile1 = new File(path, fileName1);
        getFile(targetFile);
        getFile(targetFile1);
        // 保存
        try
        {
            file.transferTo(targetFile);
            file1.transferTo(targetFile1);
            dao.saveSellerInfo(sellerMngModel);

        }
        catch (Exception e)
        {
            Logger.getLogger(MapController.class).info(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteSellerData(Model model,
            @RequestParam("id") String id)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        try
        {
            result = dao.deleteSeller(id);
        }
        catch (Exception e)
        {
            Logger.getLogger(SellerController.class).info(e.getStackTrace());
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
}
