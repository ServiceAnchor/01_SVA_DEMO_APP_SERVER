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

import com.sva.dao.ElectronicDao;
import com.sva.model.ElectronicModel;

@Controller
@RequestMapping(value = "/electronic")
public class ElectronicController
{

    @Autowired
    private ElectronicDao dao;

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getMsgData(HttpServletRequest request,
            Model model)
    {
        Collection<ElectronicModel> ResultList = new ArrayList<ElectronicModel>(
                10);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<ElectronicModel> store = new ArrayList<ElectronicModel>(10);
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
    public String saveMsgData(HttpServletRequest request, ModelMap model,
            ElectronicModel electronicModel,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "file1", required = false) MultipartFile file1)
    {
        // 保存
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload");
        String nu = "";
        Calendar calendar = Calendar.getInstance();
        Date d = calendar.getTime();
        if (electronicModel.getId().equals(nu))
        {
            saveFile(electronicModel, file, file1, path, nu, d);

            return "redirect:/home/showElectronic";

        }
        else
        {
            saveFile1(electronicModel, file, file1, path, nu, d);

            return "redirect:/home/showElectronic";
        }
        //
        // if (fileName!="")
        // {
        // _ext = fileName.substring(fileName.lastIndexOf('.'));
        //
        // }
        // if (fileName1!="")
        // {
        // _ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
        //
        // }
        // fileName = msgMngModel.getPlace() + "_"
        // + msgMngModel.getFloor() + _ext;
        // fileName1 = msgMngModel.getPlace() + "_"
        // + msgMngModel.getFloor() + _ext1;
        // msgMngModel.setPictruePath(fileName);
        // msgMngModel.setMoviePath(fileName1);
        // Logger.getLogger(MapController.class).debug(path);
        // File targetFile = new File(path, fileName);
        // File targetFile1 = new File(path, fileName1);
        // if (!targetFile.exists())
        // {
        // targetFile.mkdirs();
        // }
        // if (!targetFile1.exists())
        // {
        // targetFile1.mkdirs();
        // }
        // // 保存
        // try
        // {
        // BufferedImage sourceImg = javax.imageio.ImageIO
        // .read(file.getInputStream());
        //
        // file.transferTo(targetFile);
        // file1.transferTo(targetFile1);
        // dao.saveMsgInfo(msgMngModel);
        // }
        // catch (Exception e)
        // {
        // Logger.getLogger(MessageController.class).info(e.getStackTrace());
        // }
        //
        // return "redirect:/home/showMsgMng";
    }

    private void saveFile1(ElectronicModel msgMngModel, MultipartFile file,
            MultipartFile file1, String path, String nu, Date d)
    {
        try
        {
            String fileName = file.getOriginalFilename();
            String fileName1 = file1.getOriginalFilename();
            if (fileName.equals(nu) && fileName1.equals(nu))
            {
                dao.updateMsgInfo(msgMngModel);
            }
            if (fileName.equals(nu) && !fileName1.equals(nu))
            {
                String _ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + _ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                dao.updateMsgInfo1(msgMngModel);
            }
            if (!fileName.equals(nu) && fileName1.equals(nu))
            {
                String _ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + _ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                dao.updateMsgInfo2(msgMngModel);
            }
            if (!fileName.equals(nu) && !fileName1.equals(nu))
            {
                String _ext = fileName.substring(fileName.lastIndexOf('.'));
                fileName = d.getTime() + _ext;
                msgMngModel.setPictruePath(fileName);
                File targetFile = new File(path, fileName);
                getFile(targetFile);
                file.transferTo(targetFile);
                String _ext1 = fileName1.substring(fileName1.lastIndexOf('.'));
                fileName1 = d.getTime() + _ext1;
                msgMngModel.setMoviePath(fileName1);
                File targetFile1 = new File(path, fileName1);
                getFile(targetFile1);
                file1.transferTo(targetFile1);
                dao.updateMsgInfo3(msgMngModel);
            }

        }
        catch (Exception e)
        {
            Logger.getLogger(MapController.class).info(e.getMessage());
        }
    }

    private void getFile(File targetFile1)
    {
        if (!targetFile1.exists())
        {
            targetFile1.mkdirs();
        }
    }

    private void saveFile(ElectronicModel msgMngModel, MultipartFile file,
            MultipartFile file1, String path, String nu, Date d)
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
            msgMngModel.setPictruePath(fileName);
        }
        if (_ext1 != null)
        {
            msgMngModel.setMoviePath(fileName1);
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
            dao.saveElectronic(msgMngModel);

        }
        catch (Exception e)
        {
            Logger.getLogger(MapController.class).info(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteMsgData(Model model,
            @RequestParam("id") String id)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        try
        {
             dao.deleteElectronic(Integer.valueOf(id));
        }
        catch (Exception e)
        {
            Logger.getLogger(ElectronicController.class)
                    .info(e.getStackTrace());
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
        ModelAndView model = new ModelAndView("redirect:/home/showMsgMng");
        model.addObject("info", info);
        return model;

    }

}
