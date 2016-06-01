package com.sva.common.conf;

import java.util.ArrayList;

public abstract class GlobalConf
{
    public static String ip;

    public static String user;

    public static String password;

    /**
     * 对接SVA数据线程池
     */
    private static ArrayList<Thread> threadPool = new ArrayList<Thread>(10);

    /**
     * 对接IBMBluemixClient线程池
     */
    private static ArrayList<Thread> bluemixClientThreadPool = new ArrayList<Thread>(
            10);

    /**
     * 加入SVA数据线程池
     * 
     * @param e
     */
    public static synchronized void addThreadPool(Thread e)
    {
        threadPool.add(e);
    }

    /**
     * 移除SVA数据线程
     * 
     * @param e
     */
    public static synchronized void removeThreadPool(Thread e)
    {
        threadPool.remove(e);
    }

    /**
     * 获取SVA数据线程池长度
     * 
     * @return
     */
    public static synchronized int getThreadPoolSize()
    {
        return threadPool.size();
    }

    /**
     * 根据索引获取对于线程
     * 
     * @param i
     * @return
     */
    public static synchronized Thread getThreadPool(int i)
    {
        return threadPool.get(i);
    }

    /**
     * 添加对接IBMBluemixClient线程
     */
    public static synchronized void addBluemixClientThreadPool(Thread e)
    {
        bluemixClientThreadPool.add(e);
    }

    /**
     * 移除IBMBluemixClient线程
     */
    public static synchronized void removeBluemixClientThreadPool(Thread e)
    {
        bluemixClientThreadPool.remove(e);
    }

    /**
     * 获取IBMBluemixClient线程池长度
     */
    public static synchronized int getBluemixClientThreadPoolSize()
    {
        return bluemixClientThreadPool.size();
    }

    /**
     * 根据索引获取IBMBluemixClient线程
     */
    public static synchronized Thread getBluemixClientThreadPool(int i)
    {
        return bluemixClientThreadPool.get(i);
    }
}
