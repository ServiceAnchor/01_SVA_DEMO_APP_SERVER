package com.sva.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public abstract class Util
{
    /**
     * 测试是否能ping通
     * 
     * @param server
     * @param timeout
     * @return
     */
    public static Map<String, Object> ping(String ip, int pingnumber,
            int packtsize, int timeOut)
    {
        List<String> out = new ArrayList<String>(10);
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("ip", ip);
        if ("".equals(ip))
        {
            result.put("error", true);
            result.put("data", "remoteIpAddress is empty!");
            return result;
        }

        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ip + " -n " + pingnumber + "-l"
                + packtsize + " -w " + timeOut;
        try
        {
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null)
            {
                result.put("error", true);
                result.put("data", "exec command failed!");
                return result;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(),
                    "gbk"));
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null)
            {
                out.add(line);
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            if (connectedCount == pingnumber)
            {
                result.put("error", false);
                result.put("data", out);
            }
            else
            {
                result.put("error", true);
                result.put("data", out);
            }
            return result;
        }
        catch (Exception ex)
        {
            Logger.getLogger(QuartzJob.class).info(ex.getStackTrace());
            // 出现异常则返回假
            result.put("error", true);
            result.put("data", ex.toString());
            return result;
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                Logger.getLogger(QuartzJob.class).info(e.getStackTrace());
            }
        }
    }

    /**
     * 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
     * 
     * @param line
     * @return
     */
    private static int getCheckResult(String line)
    {
        // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        boolean b = matcher.find();
        while (b)
        {
            b = matcher.find();
            return 1;
        }
        return 0;
    }

    /**
     * 
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param flag
     *            时间段标识，1：hour，2：day
     * @return
     */
    public static Map<String, Object> getPeriodList(String startTime,
            String endTime, int flag)
    {
        Map<String, Object> result = new HashMap<String, Object>(2);

        Date start = ConvertUtil.dateStringFormat(startTime,
                "yyyy-MM-dd HH:mm:ss");
        Date end = ConvertUtil.dateStringFormat(endTime, "yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        Date tmpDate = cal.getTime();
        long endTimeInSec = end.getTime();

        if (flag == 1)
        {
            long time = tmpDate.getTime();
            String keyTime = null;
            Boolean b = tmpDate.before(end);
            while (b || time == endTimeInSec)
            {
                b = tmpDate.before(end);
                keyTime = ConvertUtil.dateFormat(cal.getTime(),
                        "yyyy-MM-dd HH:mm:ss");
                result.put(keyTime, "");
                cal.add(Calendar.HOUR, 1);
                tmpDate = cal.getTime();
            }
        }
        else if (flag == 2)
        {
            long time = tmpDate.getTime();
            String keyTime = null;
            Boolean b = tmpDate.before(end);
            while (b || time == endTimeInSec)
            {
                b = tmpDate.before(end);
                keyTime = ConvertUtil.dateFormat(cal.getTime(), "yyyy-MM-dd");
                result.put(keyTime, "");
                cal.add(Calendar.DATE, 1);
                tmpDate = cal.getTime();
            }
        }
        return result;
    }
}
