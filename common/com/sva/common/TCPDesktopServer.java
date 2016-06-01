package com.sva.common;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.sva.web.controllers.ApiController;

public class TCPDesktopServer implements Runnable
{
    private static Logger log = Logger.getLogger(ApiController.class);

    private static DatagramSocket udpSocket;

    private static byte[] data = new byte[256];

    private static DatagramPacket udpPacket = new DatagramPacket(data,
            data.length);

    public void run()
    {
        try
        {
            udpSocket = new DatagramSocket(48086);
            while (true)
            {
                try
                {
                    udpSocket.receive(udpPacket);
                    log.debug("socket is ok");
                }
                catch (Exception e)
                {
                }

                if (null != udpPacket.getAddress())
                {
                    String codeString = new String(data, 0,
                            udpPacket.getLength());
                    codeString += udpPacket.getAddress();
                    System.out.println(codeString);
                    try
                    {
                        String ip = udpPacket.getAddress().toString()
                                .substring(1);
                        log.debug("socket IP:" + ip);
                        System.out.println(ip);
                        // socket = new Socket(ip, 8080);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            // socket.close();
                        }
                        catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (SocketException e)
        {
        }
    }

}
