/*
 * 文件名：LocationDelayController.java
 * 版权：Copyright 2013-2016 Huawei Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LocationDelayController.java
 * 修改人：wwx283823
 * 修改时间：2016-3-16
 * 修改内容：新增
 */
package com.sva.web.controllers;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.sva.common.conf.Params;
import com.sva.dao.PhoneDao;
import com.sva.model.PhoneModel;

/**
 * TODO 添加类的一句话简单描述。
 * 
 * @author wwx283823
 * @version iSoftStone 2016-3-16
 * @since iSoftStone
 */
@Controller
@RequestMapping(value = "/phone")
public class PhoneController
{

    private static Logger log = Logger.getLogger(AccuracyController.class);

    @Autowired
    private PhoneDao phoneDao;

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request,
            Model model)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<PhoneModel> ResultList = new ArrayList<PhoneModel>(10);
        Object userName = request.getSession().getAttribute("username");
        if ("admin".equals(userName))
        {
            ResultList = phoneDao.getAllData();
        }
        modelMap.put("error", null);
        modelMap.put("data", ResultList);
        return modelMap;
    }

    /*
     * 导出excel Time：2015/10/12 lwx274026
     */
    @RequestMapping(value = "/api/exportCodeTemplate")
    @ResponseBody
    public void exportCodeTemplate(HttpServletRequest request,
            HttpServletResponse response)
    {
        RequestContext requestContext = new RequestContext(request);
        Locale myLocale = requestContext.getLocale();

        String phoneNumber = "phoneNumber";
        String Ip = "Ip";
        String timestamp = Params.delayTime;
        String phoneData = "Phone Data";

        if (!Locale.CHINA.equals(myLocale))
        {
            timestamp = "delayTime";
        }
        Cell phoneNumberTitle = null;
        Cell IpTitle = null;
        Cell timestampTitle = null;
        Cell typeTitle = null;
        String logInfo = "data is null!";
        try
        {
            // 1,得到所有的精度汇总信息
            List<PhoneModel> summaryInformation = phoneDao.getAllData();
            if (summaryInformation == null || summaryInformation.size() == 0)
            {
                log.info(logInfo);
            }
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(phoneData);
            CellStyle titleStyle = getCellStyle(workbook, 0);
            CellStyle contentStyle = getCellStyle(workbook, 1);
            // 创建第一行的（标题）样式
            Row row = sheet.getRow(0);
            if (row == null)
            {
                row = sheet.createRow(0);
                // 第一行第1列显示地点

                exportTitle(titleStyle, phoneNumber, phoneNumberTitle, row, 0);

                // 第一行第2列显示楼层

                exportTitle(titleStyle, Ip, IpTitle, row, 1);
                // 第一行第三列显示坐标X米

                exportTitle(titleStyle, timestamp, timestampTitle, row, 2);

                row.setHeight((short) 600);
                // 设置特性列宽度为10000
                sheet.setColumnWidth(0, 4000);
                sheet.setColumnWidth(1, 4000);
                sheet.setColumnWidth(2, 5000);

            }
            // 从第二行开始遍历所有的数据写入excel内容中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PhoneModel accuracyModel = null;
            int sumSize = summaryInformation.size();
            for (int i = 1; i < sumSize + 1; i++)
            {
                accuracyModel = summaryInformation.get(i - 1);

                row = sheet.getRow(i);
                if (row == null)
                {
                    row = sheet.createRow(i);
                }
                typeTitle = inputValue(phoneNumberTitle, IpTitle,
                        timestampTitle, typeTitle, titleStyle, contentStyle,
                        row, sdf, accuracyModel);
            }

            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + URLEncoder.encode(phoneNumber, "utf-8") + ""
                    + new SimpleDateFormat("yyyyMMdd").format(new Date())
                    + ".xlsx");// 设定输出文件头
            workbook.write(response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("Exception.", e);
        }
    }

    private Cell inputValue(Cell phoneNumberTitle, Cell IpTitle,
            Cell timestampTitle, Cell typeTitle, CellStyle titleStyle,
            CellStyle contentStyle, Row row, SimpleDateFormat sdf,
            PhoneModel accuracyModel)
    {
        // 第i行第0列赋值地点值

        exportContent(contentStyle, phoneNumberTitle, row, 0,
                accuracyModel.getPhoneNumber());

        // 第二行第1列赋值楼层值

        exportContent(contentStyle, IpTitle, row, 1, accuracyModel.getIp());
        // 第二行第2列赋值坐标X米值

        exportContent(contentStyle, timestampTitle, row, 2,
                sdf.format(accuracyModel.getTimestamp()));
        // 第二行第3列赋值结束时间值

        return typeTitle;
    }

    /*
     * 设置excel的样式 Time：2015/10/12 lwx274026
     */
    private CellStyle getCellStyle(Workbook workbook, int type)
    {
        CellStyle style = null;
        Font font = null;
        style = workbook.createCellStyle();
        // 1,设置excel单元格标题
        if (type == 0)
        {
            font = workbook.createFont();
            font.setFontHeightInPoints((short) 14);// 设置字体大小
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            style.setWrapText(true);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            style.setWrapText(true);
            style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        }
        // 设置数据内容单元格样式
        else
        {
            font = workbook.createFont();
            font.setFontHeightInPoints((short) 11);// 设置字体大小
            font.setColor(HSSFColor.BLACK.index);

            style.setWrapText(true);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
            style.setFont(font);
        }
        return style;
    }

    private void exportTitle(CellStyle titleStyle, String name, Cell column,
            Row row, int number)
    {
        column = row.getCell(number);
        if (column == null)
        {
            column = row.createCell(number);
        }
        column.setCellStyle(titleStyle);
        column.setCellValue(name);
    }

    private void exportContent(CellStyle titleStyle, Cell column, Row row,
            int number, String name)
    {

        if (column == null)
        {
            column = row.createCell(number);
        }
        column.setCellStyle(titleStyle);
        column.setCellValue(name);

    }

}
