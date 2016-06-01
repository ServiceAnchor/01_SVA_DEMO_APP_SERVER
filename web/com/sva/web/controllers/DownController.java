package com.sva.web.controllers;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.sva.model.VersionModel;

@Controller
@RequestMapping(value = "/down")
public class DownController
{

    private static Logger log = Logger.getLogger(AccountController.class);

    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(Model model,
            HttpServletRequest request)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<VersionModel> list = new ArrayList<VersionModel>(10);
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/version/WAR");
        VersionModel v = null;
        long l = 0;
        String pa = null;
        String p = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        File file = new File(path);
        if (file.exists())
        {
            File[] files = file.listFiles();
            if (files.length == 0)
            {
                modelMap.put("error", null);
                modelMap.put("data", list);
                return modelMap;
            }
            else
            {
                for (File file2 : files)
                {
                    if (file2.isDirectory())
                    {
                        modelMap.put("error", null);
                        modelMap.put("data", list);
                        return modelMap;
                    }
                    else
                    {
                        v = new VersionModel();
                        l = file2.lastModified();
                        v.setCreatTime(f.format(new Date(l)));
                        pa = file2.getAbsolutePath();
                        v.setName(file2.getName());
                        p = pa.substring(pa.lastIndexOf("\\"));
                        v.setPath(p);
                        list.add(v);
                    }
                }
                modelMap.put("error", null);
                modelMap.put("data", list);
            }
        }
        else
        {
            modelMap.put("error", null);
            modelMap.put("data", list);
        }
        return modelMap;
    }

    /*
     * 生成二维码APK图片
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/generrateQrcode", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> generrateQrcode(HttpServletRequest request,
            Model model)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String apkName = null;
        String content = null;
        try
        {

            // 二维码内容
            String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/version/APK");
            String QRcodePath = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
            // int port = request.getLocalPort();

            // InetAddress ia=null;
            // http://localhost:8081/sva/home/uploadQRcode
            String url = request.getRequestURL().toString();
            // http://10.169.77.131:8081/sva/down/api/generrateQrcode

            String a = url.split("//")[1];
            String ips = a.split(":")[0];
            String p = a.split(":")[1];
            String ports = p.split("/")[0];
            // String content =
            // "http://10.169.77.131:8082/sva/version/APK/QRcode.jpg";

            // String ip = request.getLocalAddr();
            // String localip = "";
            // try
            // {
            // ia=ia.getLocalHost();
            // localip=ia.getHostAddress();
            //
            //
            // }
            // catch (Exception e)
            // {
            // e.printStackTrace();
            // }

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            File newPath = new File(path);
            // 列出该目录下所有文件和文件夹
            File[] files = newPath.listFiles();
            if (files.length != 0)
            {
                // 按照文件最后修改日期倒序排序
                Arrays.sort(files, new Comparator<File>()
                {
                    @Override
                    public int compare(File file1, File file2)
                    {
                        return (int) (file2.lastModified() - file1
                                .lastModified());
                    }
                });
                // 取出第一个(即最新修改的)文件，打印文件名
                apkName = files[0].getName();
                String prefix = apkName.substring(apkName.lastIndexOf(".") + 1)
                        .toUpperCase();
                if (prefix.equals("APK"))
                {
                    content = "http://" + ips + ':' + ports
                            + "/sva/version/downloadAPK.html";
                }
                else
                {
                    content = "The latest file is not apk package";
                }
            }
            else
            {
                content = "No File";
            }

            @SuppressWarnings("rawtypes")
            Map hints = new HashMap(2);

            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, 200, 200, hints);

            File file1 = new File(QRcodePath, "QRcode.jpg");

            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);

        }
        catch (Exception e)
        {

            log.info(e.getMessage());

        }
        modelMap.put("error", null);
        modelMap.put("data", apkName);
        return modelMap;
    }

    /*
     * 生成二维码IOS图片
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/iosQrcode", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> iosQrcode(HttpServletRequest request, Model model)
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String apkName = null;
        String content = null;
        try
        {

            // 二维码内容
            String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/version/IOS");
            String QRcodePath = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");
            // int port = request.getLocalPort();

            // InetAddress ia=null;
            // http://localhost:8081/sva/home/uploadQRcode
            String url = request.getRequestURL().toString();
            // http://10.169.77.131:8081/sva/down/api/generrateQrcode

            String a = url.split("//")[1];
            String ips = a.split(":")[0];
            String p = a.split(":")[1];
            String ports = p.split("/")[0];
            // String content =
            // "http://10.169.77.131:8082/sva/version/APK/QRcode.jpg";

            // String ip = request.getLocalAddr();
            // String localip = "";
            // try
            // {
            // ia=ia.getLocalHost();
            // localip=ia.getHostAddress();
            //
            //
            // }
            // catch (Exception e)
            // {
            // e.printStackTrace();
            // }

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            File newPath = new File(path);
            // 列出该目录下所有文件和文件夹
            File[] files = newPath.listFiles();
            if (files.length != 0 && files != null)
            {
                // 按照文件最后修改日期倒序排序
                Arrays.sort(files, new Comparator<File>()
                {
                    @Override
                    public int compare(File file1, File file2)
                    {
                        return (int) (file2.lastModified() - file1
                                .lastModified());
                    }
                });
                // 取出第一个(即最新修改的)文件，打印文件名
                apkName = files[0].getName();
                String prefix = apkName.substring(apkName.lastIndexOf('.') + 1)
                        .toUpperCase();
                if ("IPA".equals(prefix))
                {
                    content = "http://" + ips + ':' + ports
                            + "/sva/version/downloadIOS.html";
                }
                else
                {
                    content = "The latest file is not IPA package";
                }
            }
            else
            {
                content = "No File";
            }

            @SuppressWarnings("rawtypes")
            Map hints = new HashMap(2);

            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, 200, 200, hints);

            File file1 = new File(QRcodePath, "IOSQRcode.jpg");

            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);

        }
        catch (Exception e)
        {

            log.info(e.getMessage());

        }
        modelMap.put("error", null);
        modelMap.put("data", apkName);
        return modelMap;
    }

    @RequestMapping(value = "/contentShow", method = {RequestMethod.GET})
    // public String contentShow(HttpServletRequest
    // request,@RequestParam("stores") String stores,
    // @RequestParam("floor") String floor,
    // @RequestParam("startTime") String startTime,
    // @RequestParam("stores") String endTime) throws SQLException
    public String contentShow(HttpServletRequest request) throws SQLException
    {
        // request.getSession().setAttribute("show", null);
        request.getSession().removeAttribute("show");
        return "/home/contentShowindex";
    }
}
