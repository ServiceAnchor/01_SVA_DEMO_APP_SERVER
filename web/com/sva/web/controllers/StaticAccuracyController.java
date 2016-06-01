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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.sva.common.conf.Params;
import com.sva.dao.StaticAccuracyDao;
import com.sva.model.StaticAccuracyModel;
import com.sva.web.models.AccuracyTableModel;

@Controller
@RequestMapping(value = "/staticAccuracy")
public class StaticAccuracyController
{
    private static Logger log = Logger
            .getLogger(StaticAccuracyController.class);

    @Autowired
    private StaticAccuracyDao dao;

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request,
            @RequestBody AccuracyTableModel atm)
    {
        String echo = atm.getsEcho();
        int start = atm.getiDisplayStart();
        int length = atm.getiDisplayLength();
        int sortColNo = atm.getiSortCol0();
        List<String> colNames = atm.columnToArray();
        String sortCol = colNames.get(sortColNo);
        String sortDir = atm.getsSortDir0();
        if (("startdate").equals(sortCol))
        {
            sortCol = "start_date";
        }
        if (("enddate").equals(sortCol))
        {
            sortCol = "end_date";
        }
        Collection<StaticAccuracyModel> res = new ArrayList<StaticAccuracyModel>(
                10);
        Collection<StaticAccuracyModel> store = new ArrayList<StaticAccuracyModel>(
                10);
        Object userName = request.getSession().getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> storeides = (List<String>) request.getSession()
                .getAttribute("storeides");
        if (("admin").equals(userName))
        {
            res = dao.getData(start, length, sortCol, sortDir);
        }
        else
        {
            if (storeides.size() > 0)
            {
                String storeid = storeides.get(0);
                String[] stores = storeid.split(",");
                for (int i = 0; i < stores.length; i++)
                {
                    store = dao.getStaticDataByStoreid(start, length, sortCol,
                            sortDir, Integer.parseInt(stores[i]));
                    if (store != null)
                    {
                        if (res == null)
                        {
                            res = store;
                        }
                        else if (!store.isEmpty())
                        {
                            res.addAll(store);
                        }
                    }
                }
            }
        }

        int resLength = dao.getDataLength();

        Map<String, Object> modelMap = new HashMap<String, Object>(4);
        modelMap.put("sEcho", echo);
        modelMap.put("iTotalRecords", resLength);
        modelMap.put("iTotalDisplayRecords", resLength);
        modelMap.put("aaData", res);

        return modelMap;
    }

    /*
     * 导出excel Time：2015/10/12 lwx274026
     */
    @RequestMapping(value = "/api/staticExportCodeTemplate")
    @ResponseBody
    public void staticExportCodeTemplate(HttpServletRequest request,
            HttpServletResponse response)
    {
        RequestContext requestContext = new RequestContext(request);
        Locale myLocale = requestContext.getLocale();

        String logInfo = Params.logInfo;
        String sheetName = Params.sheetName;
        String excelPlace = Params.excelPlace;
        String excelfloor = Params.excelfloor;
        String excelX = Params.excelX;
        String excelY = Params.excelY;
        String excelStartDate = Params.excelStartDate;
        String excelEndDate = Params.excelEndDate;
        String avgeOffset = Params.avgeOffset;
        String maxOffset = Params.maxOffset;
        String staicAccuracy = Params.staicAccuracy;
        String offsetCenter = Params.offsetCenter;
        String offsetNumber = Params.offsetNumber;
        String stability = Params.stability;
        String excel3m = Params.excel3m;
        String excel3or5m = Params.excel3or5m;
        String excel5or10m = Params.excel5or10m;
        String excel10m = Params.excel10m;
        String triggerIp = Params.triggerIp;
        String AccuracySummary = Params.staticAccuracy;
        String detail = Params.Detail;
        if (!Locale.CHINA.equals(myLocale))
        {
            logInfo = "No summary information, please add no summary information, please add...";
            sheetName = "Accuracy aggregated results";
            excelPlace = "place";
            excelfloor = "floor";
            excelX = "Starting point (m)";
            excelY = "End point (m)";
            excelStartDate = "startDate";
            excelEndDate = "endDate";
            avgeOffset = "avgeOffset";
            maxOffset = "max Offset";
            staicAccuracy = "staic Accuracy";
            offsetCenter = "Offset center of gravity ";
            offsetNumber = "The offset";
            stability = "stability";
            excel3m = "Within 3m";
            excel3or5m = "3 to 5 meters";
            excel5or10m = "5 to 10 meters";
            excel10m = "10m outside";
            AccuracySummary = "AccuracySummary";
            triggerIp = "Trigger";
            detail = "Detail";
        }
        Cell placeTitle = null;
        Cell floorTitle = null;
        Cell xTitle = null;
        Cell yTitle = null;
        Cell startDateTitle = null;
        Cell endDateTitle = null;
        Cell avgeOffsetTitle = null;
        Cell maxOffsetTitle = null;
        Cell staicAccuracyTitle = null;
        Cell offsetCenterTitle = null;
        Cell offsetNumberTitle = null;
        Cell stabilityMTitle = null;
        Cell typeTitle = null;
        Cell threeMTitle = null;
        Cell tfiveMTitle = null;
        Cell ftenMTitle = null;
        Cell tenMTitle = null;
        Cell triggerIpTitle = null;
        Cell detailTitle = null;
        try
        {
            // 1,得到所有的精度汇总信息
            List<StaticAccuracyModel> summaryInformation = dao
                    .allQueryStaicAccuracy();
            if (summaryInformation == null || summaryInformation.size() == 0)
            {
                log.info(logInfo);
            }
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);
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
                // 第一行第3列显示坐标X米

                exportTitle(titleStyle, excelX, xTitle, row, 2);
                // 第一行第4列显示坐标y米

                exportTitle(titleStyle, excelY, yTitle, row, 3);
                // 第一行第5列显示开始时间

                exportTitle(titleStyle, excelStartDate, startDateTitle, row, 4);
                // 第一行第6列显示结束时间

                exportTitle(titleStyle, excelEndDate, endDateTitle, row, 5);
                // 第一行第7列显示平均误差

                exportTitle(titleStyle, triggerIp, triggerIpTitle, row, 6);

                exportTitle(titleStyle, avgeOffset, avgeOffsetTitle, row, 7);
                // 第一行第8列显示最大误差

                exportTitle(titleStyle, maxOffset, maxOffsetTitle, row, 8);
                // 第一行第9列显示静态精度

                exportTitle(titleStyle, staicAccuracy, staicAccuracyTitle, row,
                        9);
                // 第一行第10列显示偏移重心

                exportTitle(titleStyle, offsetCenter, offsetCenterTitle, row,
                        10);
                // 第一行第11列显示偏移量

                exportTitle(titleStyle, offsetNumber, offsetNumberTitle, row,
                        11);
                // 第一行第12列显示稳定度

                exportTitle(titleStyle, stability, stabilityMTitle, row, 12);
                // 第一行第13列显示3m以内

                exportTitle(titleStyle, excel3m, threeMTitle, row, 13);
                // 第一行第14列显示3m-5m

                exportTitle(titleStyle, excel3or5m, tfiveMTitle, row, 14);
                // 第一行第15列显示5m-10m

                exportTitle(titleStyle, excel5or10m, ftenMTitle, row, 15);
                // 第一行第16列显示10m以外
                exportTitle(titleStyle, excel10m, tenMTitle, row, 16);
//                exportTitle(titleStyle, detail, detailTitle, row, 17);

                row.setHeight((short) 600);
                // 设置特性列宽度为10000
                sheet.setColumnWidth(0, 3000);
                sheet.setColumnWidth(1, 2000);
                sheet.setColumnWidth(2, 2000);
                sheet.setColumnWidth(3, 2000);
                sheet.setColumnWidth(4, 5000);
                sheet.setColumnWidth(5, 5000);
                sheet.setColumnWidth(6, 2000);
                sheet.setColumnWidth(7, 2000);
                sheet.setColumnWidth(8, 2000);
                sheet.setColumnWidth(9, 2000);
                sheet.setColumnWidth(10, 2000);
                sheet.setColumnWidth(11, 2000);
                sheet.setColumnWidth(12, 2000);
                sheet.setColumnWidth(13, 2000);
                sheet.setColumnWidth(14, 2000);
                sheet.setColumnWidth(15, 3000);
                sheet.setColumnWidth(16, 3000);
//                sheet.setColumnWidth(17, 3000);
            }
            // 从第二行开始遍历所有的数据写入excel内容中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StaticAccuracyModel staticAccuracyModel = null;
            int sumSize = summaryInformation.size();
            for (int i = 1; i < sumSize + 1; i++)
            {
                staticAccuracyModel = summaryInformation.get(i - 1);

                row = sheet.getRow(i);
                if (row == null)
                {
                    row = sheet.createRow(i);
                }
                typeTitle = inputValue(typeTitle, placeTitle, floorTitle,
                        xTitle, yTitle, startDateTitle, endDateTitle,
                        triggerIpTitle, avgeOffsetTitle, maxOffsetTitle,
                        staicAccuracyTitle, offsetCenterTitle,
                        offsetNumberTitle, stabilityMTitle, threeMTitle,
                        tfiveMTitle, ftenMTitle, tenMTitle, titleStyle,
                        contentStyle, row, sdf, staticAccuracyModel,
                        detailTitle);
            }

            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + URLEncoder.encode(AccuracySummary, "utf-8") + ""
                    + new SimpleDateFormat("yyyyMMdd").format(new Date())
                    + ".xlsx");// 设定输出文件头
            workbook.write(response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("Exception.", e);
        }
    }

    private Cell inputValue(Cell typeTitle, Cell placeTitle, Cell floorTitle,
            Cell xTitle, Cell yTitle, Cell startDateTitle, Cell endDateTitle,
            Cell triggerIpTitle, Cell avgeOffsetTitle, Cell maxOffsetTitle,
            Cell staicAccuracyTitle, Cell offsetCenterTitle,
            Cell offsetNumberTitle, Cell stabilityMTitle, Cell threeMTitle,
            Cell tfiveMTitle, Cell ftenMTitle, Cell tenMTitle,
            CellStyle titleStyle, CellStyle contentStyle, Row row,
            SimpleDateFormat sdf, StaticAccuracyModel staticAccuracyModel,
            Cell detailTitle)
    {
        // Cell deviationTitle = null;
        // 第i行第0列赋值地点值

        exportContent(contentStyle, placeTitle, row, 0,
                staticAccuracyModel.getPlace());

        // 第二行第1列赋值楼层值

        exportContent(contentStyle, floorTitle, row, 1,
                staticAccuracyModel.getFloor());
        // 第二行第2列赋值坐标X米值

        exportContent(contentStyle, xTitle, row, 2,
                staticAccuracyModel.getOrigin());
        // 第二行第3列赋值坐标Y米值

        exportContent(contentStyle, yTitle, row, 3,
                staticAccuracyModel.getDestination());
        // 第二行第4列赋值开始时间值

        exportContent(contentStyle, startDateTitle, row, 4,
                sdf.format(staticAccuracyModel.getStartdate()));
        // 第二行第5列赋值结束时间值

        exportContent(contentStyle, endDateTitle, row, 5,
                sdf.format(staticAccuracyModel.getEnddate()));

        exportContent(contentStyle, triggerIpTitle, row, 6,
                staticAccuracyModel.getTriggerIp());
        // 第二行第6列赋值平均误差

        exportContent(contentStyle, avgeOffsetTitle, row, 7,
                staticAccuracyModel.getAvgeOffset().toString());
        // 第二行第7列赋值最大误差

        exportContent(contentStyle, maxOffsetTitle, row, 8, staticAccuracyModel
                .getMaxOffset().toString());

        // 第二行第8列赋值静态精度
        exportContent(contentStyle, staicAccuracyTitle, row, 9,
                staticAccuracyModel.getStaicAccuracy().toString());

        // 第二行第9列赋值偏移重心

        exportContent(contentStyle, offsetCenterTitle, row, 10,
                staticAccuracyModel.getOffsetCenter().toString());

        // 第二行第10列赋值偏移量
        exportContent(contentStyle, offsetNumberTitle, row, 11,
                staticAccuracyModel.getOffsetNumber().toString());
        // 第二行第11列赋值稳定度

        exportContent(contentStyle, stabilityMTitle, row, 12,
                staticAccuracyModel.getStability().toString());
        // 第二行第12列赋值3m以内值

        exportContent(contentStyle, threeMTitle, row, 13,
                staticAccuracyModel.getCount3() + "");
        // 第二行第13列赋值3m-5m值

        exportContent(contentStyle, tfiveMTitle, row, 14,
                staticAccuracyModel.getCount5() + "");
        // 第二行第14列赋值5m-10m值

        exportContent(contentStyle, ftenMTitle, row, 15,
                staticAccuracyModel.getCount10() + "");
        // 第二行第15列赋值10m以外值

        exportContent(contentStyle, tenMTitle, row, 16,
                staticAccuracyModel.getCount10p() + "");

//        exportContent(contentStyle, detailTitle, row, 17,
//                staticAccuracyModel.getDetail() + "");

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
