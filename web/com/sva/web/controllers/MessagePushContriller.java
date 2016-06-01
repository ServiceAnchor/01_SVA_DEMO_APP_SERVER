/*
 * 文件名：MessagePushContriller.java
 * 版权：Copyright 2013-2016 Huawei Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MessagePushContriller.java
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
import com.sva.dao.MessagePushDao;
import com.sva.model.MessagePush;

/**
 * 
 * @author wwx283823
 * @version iSoftStone 2016-3-16
 * @since iSoftStone
 */
@Controller
@RequestMapping(value = "/push")
public class MessagePushContriller
{

    private static Logger log = Logger.getLogger(AccuracyController.class);

   
    @Autowired
    private MessagePushDao pushDao;

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request,
            Model model)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<MessagePush> ResultList = new ArrayList<MessagePush>(10);
        Collection<MessagePush> store = new ArrayList<MessagePush>(10);
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if ("admin".equals(userName))
        {

        	ResultList = pushDao.getAllData();
        }
        else
        {
            if (storeides.size() > 0)
            {
                String storeid = storeides.get(0);
                String[] stores = storeid.split(",");
                for (int i = 0; i < stores.length; i++)
                {
                    store = pushDao.getAllDataByStoreId(Integer.parseInt(stores[i]));
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

    @RequestMapping(value = "/api/exportCodeTemplate")
    @ResponseBody
    public void exportCodeTemplate(HttpServletRequest request,
            HttpServletResponse response)
    {
        RequestContext requestContext = new RequestContext(request);
        Locale myLocale = requestContext.getLocale();
        String messageRight = Params.messageRight;
        String messageInfo = Params.messageInfo;
        String messageName = Params.messageName;
        String excelPlace = Params.excelPlace;
        String excelfloor = Params.excelfloor;
        String pushRight = Params.pushRight;
        String pushWrong = Params.pushWrong;
        String notPush = Params.notPush;
        String centerRadius = Params.centerRadius;
        String centerReality = Params.centerReality;
        String isRigth = Params.isRigth;
        String time = Params.delayTime;
        if (!Locale.CHINA.equals(myLocale))
        {
            messageRight = "messagePush information";
            messageInfo = "No messagePush information, please add nmessagePush information, please add...";
            messageName = "messagePush right information ";
            excelPlace = "place";
            excelfloor = "floor";
            pushRight = "pushRight";
            pushWrong = "pushWrong";
            notPush = "notPush";
            centerRadius = "centerRadius";
            centerReality = "centerReality";
            isRigth = "isRigth";
            time = "uploadTime";

        }
        Cell placeTitle = null;
        Cell floorTitle = null;
        Cell xTitle = null;
        Cell yTitle = null;
        Cell startDateTitle = null;
        Cell endDateTitle = null;
        Cell triggerIpTitle = null;
        Cell typeTitle = null;
        Cell offsetTitle = null;
        Cell averDeviTitle = null;
        Cell threeMTitle = null;
        Cell tfiveMTitle = null;
        Cell ftenMTitle = null;
        Cell tenMTitle = null;
        try
        {
            // 1,得到所有的精度汇总信息
            List<MessagePush> summaryInformation = pushDao.getAllData();
            if (summaryInformation == null || summaryInformation.size() == 0)
            {
                log.info(messageInfo);
            }
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(messageName);
            CellStyle titleStyle = getCellStyle(workbook, 0);
            CellStyle contentStyle = getCellStyle(workbook, 1);
            // 创建第一行的（标题）样式
            Row row = sheet.getRow(0);
            if (row == null)
            {
                row = sheet.createRow(0);
                // 第一行第1列显示地点

                exportTitle(titleStyle, excelPlace, placeTitle, row, 0);

                // 第一行第2列显示楼层

                exportTitle(titleStyle, excelfloor, floorTitle, row, 1);
                // 第一行第三列显示坐标X米

                exportTitle(titleStyle, pushRight, xTitle, row, 2);
                // 第一行第四列显示坐标y米

                exportTitle(titleStyle, pushWrong, yTitle, row, 3);
                // 第一行第五列显示开始时间

                exportTitle(titleStyle, notPush, startDateTitle, row, 4);
                // 第一行第六列显示结束时间

                exportTitle(titleStyle, centerRadius, endDateTitle, row, 5);
                // 第一行第七列显示触发者

                exportTitle(titleStyle, centerReality, triggerIpTitle, row, 6);
                // 第一行第八列显示类型

                exportTitle(titleStyle, isRigth, typeTitle, row, 7);
                // 第一行第八列显示偏差

                exportTitle(titleStyle, time, offsetTitle, row, 8);
                // 第一行第九列显示平均误差

                row.setHeight((short) 600);
                // 设置特性列宽度为10000
                sheet.setColumnWidth(0, 2000);
                sheet.setColumnWidth(1, 2000);
                sheet.setColumnWidth(2, 3000);
                sheet.setColumnWidth(3, 3000);
                sheet.setColumnWidth(4, 3000);
                sheet.setColumnWidth(5, 3000);
                sheet.setColumnWidth(6, 3000);
                sheet.setColumnWidth(7, 3000);
                sheet.setColumnWidth(8, 5000);

            }
            // 从第二行开始遍历所有的数据写入excel内容中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            MessagePush accuracyModel = null;
            int sumSize = summaryInformation.size();
            for (int i = 1; i < sumSize + 1; i++)
            {
                accuracyModel = summaryInformation.get(i - 1);

                row = sheet.getRow(i);
                if (row == null)
                {
                    row = sheet.createRow(i);
                }
                typeTitle = inputValue(placeTitle, floorTitle, xTitle, yTitle,
                        startDateTitle, endDateTitle, triggerIpTitle,
                        typeTitle, offsetTitle, averDeviTitle, threeMTitle,
                        tfiveMTitle, ftenMTitle, tenMTitle, titleStyle,
                        contentStyle, row, sdf, accuracyModel);
            }

            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + URLEncoder.encode(messageRight, "utf-8") + ""
                    + new SimpleDateFormat("yyyyMMdd").format(new Date())
                    + ".xlsx");// 设定输出文件头
            workbook.write(response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("Exception.", e);
        }
    }

    private Cell inputValue(Cell placeTitle, Cell floorTitle, Cell xTitle,
            Cell yTitle, Cell startDateTitle, Cell endDateTitle,
            Cell triggerIpTitle, Cell typeTitle, Cell offsetTitle,
            Cell averDeviTitle, Cell threeMTitle, Cell tfiveMTitle,
            Cell ftenMTitle, Cell tenMTitle, CellStyle titleStyle,
            CellStyle contentStyle, Row row, SimpleDateFormat sdf,
            MessagePush accuracyModel)
    {
        // 第i行第0列赋值地点值

        exportContent(contentStyle, placeTitle, row, 0,
                accuracyModel.getPlace());

        // 第二行第1列赋值楼层值

        exportContent(contentStyle, floorTitle, row, 1,
                accuracyModel.getFloor());
        // 第二行第2列赋值坐标X米值

        exportContent(contentStyle, xTitle, row, 2,
                String.valueOf(accuracyModel.getPushRight()));
        // 第二行第3列赋值坐标Y米值

        exportContent(contentStyle, yTitle, row, 3,
                String.valueOf((accuracyModel.getPushWrong())));
        // 第二行第4列赋值开始时间值

        exportContent(contentStyle, startDateTitle, row, 4,
                String.valueOf(accuracyModel.getNotPush()));
        // 第二行第5列赋值结束时间值

        exportContent(contentStyle, endDateTitle, row, 5,
                String.valueOf((accuracyModel.getCenterRadius())));
        // 第二行第6列赋值触发者

        exportContent(contentStyle, triggerIpTitle, row, 6,
                String.valueOf(accuracyModel.getCenterReality()));

        // 第二行第7列赋值类型值

        // 第二行第8列赋值偏差值
        // if (typeTitle == null)
        // {
        // typeTitle = row.createCell(7);
        // }
        // typeTitle.setCellStyle(titleStyle);
        // if (accuracyModel.getIsRigth() == 0)
        // {
        // typeTitle.setCellValue(Params.messageNot);
        // }
        // else
        // {
        // typeTitle.setCellValue(Params.messageIs);
        // }

        typeTitle = row.createCell(7);

        typeTitle.setCellStyle(titleStyle);

        if (accuracyModel.getIsRigth() == 0)
        {
            typeTitle.setCellValue(Params.messageNot);
        }
        else
        {
            typeTitle.setCellValue(Params.messageIs);
        }

        // exportContent(contentStyle, offsetTitle, row, 7,
        // String.valueOf(accuracyModel.getIsRigth()));
        // 第二行第9列赋值偏差值

        exportContent(contentStyle, averDeviTitle, row, 8,
                sdf.format(accuracyModel.getUpdateTime()));

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
