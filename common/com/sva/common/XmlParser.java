/*
 * 文件名：XmlParser.java
 * 版权：Copyright 2013-2013 Huawei Tech. Co. Ltd. All Rights Reserved. 
 * 描述： XmlParser.java
 * 修改人：dWX182800
 * 修改时间：2013-11-4
 * 修改内容：新增
 */
package com.sva.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.sva.model.PrruModel;

/**
 * 解析xml
 * <p>
 * 详细描述
 * <p>
 * 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author dWX182800
 * @version iSoftStone VDS V100R001C04 2013-11-4
 * @since iSoftStone VDS V100R001C04
 */
public class XmlParser
{
    /**
     * 调测日志记录器。
     */
    private static Logger logger = Logger.getLogger(XmlParser.class);

    private Document xmldoc = null;

    public XmlParser()
    {
    }

    private Element root = null;

    public void init(File file)
    {
        SAXReader reader = new SAXReader();
        try
        {
            xmldoc = reader.read(file);
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
        }

        root = xmldoc.getRootElement();
    }

    /**
     * 获取属性集合
     * 
     * @param xPath
     * @param values
     * @return
     */
    public List<PrruModel> getAttrVal(String flooNo, int plceId, String xPath,
            String... values)
    {
        if (values == null || values.length == 0)
        {
            return null;
        }
        @SuppressWarnings("unchecked")
        List<Node> node = root.selectNodes(xPath);
        List<PrruModel> results = new ArrayList<PrruModel>(10);
        String result = null;
        PrruModel pm = null;
        int si = node.size();
        String id = "id";
        String name = "name";
        String x = "x";
        String y = "y";
        String necode = "neCode";
        for (int i = 0; i < si; i++)
        {
            pm = new PrruModel();
            pm.setPlaceId(plceId);
            for (String value : values)
            {
                if (node == null)
                {
                    logger.error(xPath + " hasn't node");
                    continue;
                }
                result = node.get(i).valueOf('@' + value);
                if (value.equals(id))
                {
                    pm.setNeId(result);
                }
                if (value.equals(necode))
                {
                    pm.setNeCode(result);
                }
                if (value.equals(name))
                {
                    pm.setNeName(result);
                }
                if (value.equals(x))
                {
                    pm.setX(result);
                }
                if (value.equals(y))
                {
                    pm.setY(result);
                }
                if (result == null || "".equals(result))
                {
                    logger.error(" not find data:" + value);
                }

            }
            pm.setFloorNo(flooNo);
            results.add(pm);
        }
        return results;
    }

    public Node getNode(String xPath)
    {
        return root.selectSingleNode(xPath);
    }

    @SuppressWarnings("unchecked")
    public List<Node> getNodes(String xPath)
    {
        return root.selectNodes(xPath);
    }
}
