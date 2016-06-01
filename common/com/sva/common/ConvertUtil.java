package com.sva.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class ConvertUtil
{
    /**
     * 判断是否是MAC地址格式
     * 
     * @param mac
     * @return
     */
    public static boolean isMac(String mac)
    {
        boolean result = false;
        // 正则校验MAC合法性
        String patternMac = "^[A-F0-9]{2}([:-]{1}[A-F0-9]{2}){5}$";
        if (Pattern.compile(patternMac).matcher(mac).find())
        {
            result = true;
        }
        return result;
    }

    /**
     * 根据格式判断是ip或者mac进行转换
     * 
     * @param macOrIp
     * @return
     */
    public static String convertMacOrIp(String macOrIp)
    {
        String result = "";
        if (!StringUtils.isEmpty(macOrIp))
        {
            macOrIp = macOrIp.toUpperCase();
            if (isMac(macOrIp))
            {
                result = convertMac(macOrIp);
            }
            else
            {
                result = convertIp(macOrIp);
            }
        }
        return result;
    }

    /**
     * mac转换16进制
     * 
     * @param mac
     * @return
     */
    public static String convertMac(String mac)
    {
        String result = "";
        result = mac.replaceAll("-", "");
        result = result.replaceAll(":", "");
        result = result.toLowerCase();
        return result;
    }

    /**
     * 16进制转换成ip地址
     * 
     * @param ip
     * @return
     */
    public static String convert(String ip)
    {
        Integer te = Integer.valueOf(ip, 16);
        int i = te.intValue();

        return ((i >> 24 & 0xFF) + '.' + ((i >> 16) & 0xFF)
                + String.valueOf('.') + ((i >> 8) & 0xFF) + '.' + (i & 0xFF));
    }

    /**
     * ip地址转换16进制
     * 
     * @param ip
     * @return
     */
    public static String convertIp(String ip)
    {
        String[] iplist = ip.split("\\.");
        int ip0 = Integer.parseInt(iplist[0]);
        int ip1 = Integer.parseInt(iplist[1]);
        int ip2 = Integer.parseInt(iplist[2]);
        int ip3 = Integer.parseInt(iplist[3]);
        String result = Integer.toHexString((ip0 << 24) + (ip1 << 16)
                + (ip2 << 8) + ip3);
        for (int i = result.length(); i < 8; i++)
        {
            result = '0' + result;
        }
        return result;
    }

    /**
     * 日期型转指定格式字符型
     * 
     * @param date
     *            日期
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(Date date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 字符型日期转指定格式字符型
     * 
     * @param date
     *            日期字符串
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date dateStringFormat(String date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date result = null;
        try
        {
            result = formatter.parse(date);
        }
        catch (Exception e)
        {
            Logger.getLogger(ConvertUtil.class).info(e.getStackTrace());
        }
        return result;
    }

}