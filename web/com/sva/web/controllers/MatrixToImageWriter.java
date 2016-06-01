package com.sva.web.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public final class MatrixToImageWriter
{

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private static Logger log = Logger.getLogger(ParamController.class);

    private MatrixToImageWriter()
    {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix)
    {

        int width = matrix.getWidth();

        int height = matrix.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++)
        {

            for (int y = 0; y < height; y++)
            {

                bufferedImage.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);

            }

        }
        return bufferedImage;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException
    {

        BufferedImage image = toBufferedImage(matrix);

        if (!ImageIO.write(image, format, file))
        {

            throw new IOException("Could not write an image of format "
                    + format + " to " + file);

        }

    }

    public static void writeToStream(BitMatrix matrix, String format,
            OutputStream stream) throws IOException
    {

        BufferedImage image = toBufferedImage(matrix);

        if (!ImageIO.write(image, format, stream))
        {

            throw new IOException("Could not write an image of format "
                    + format);

        }

    }

    // ��ɶ�ά��
    @SuppressWarnings("unchecked")
    public static void generrate(HttpServletRequest request)
    {
        // 1.3 ��д��ɶ�ά���ʵ�ִ���
        try
        {

            String content = "120605181003;.cnblogs.com/jtmjx";

            // String path = "C:/Users/lwx274026/Pictures";
            String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload");

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            @SuppressWarnings("rawtypes")
            Map hints = new HashMap(2);

            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, 400, 400, hints);

            File file1 = new File(path, "weweima.jpg");

            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);

        }
        catch (Exception e)
        {

            log.info(e.getMessage());
        }

    }

}
