package hxs.weixin.parent.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import net.sf.json.JSONObject;

public class XmlToString {

	/**
	 * 将xml转成String
	 * @param parameters
	 * @return
	 */
	public static String getRequestXml(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
	
	
	/**
	 * xml转json
	 * @return
	 */
	public static String xml2JSON(String xml) {  
        JSONObject obj = new JSONObject();  
        try {  
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(xml);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return obj.toString();  
        }  
    }  
	
	 /** 
     * 一个迭代方法 
     * @param element 
     * @return java.util.Map 实例 
     */  
    @SuppressWarnings("unchecked")  
	 private static Map  iterateElement(Element element) {  
        List jiedian = element.getChildren();  
        Element et = null;  
        Map obj = new HashMap();  
        List list = null;  
        for (int i = 0; i < jiedian.size(); i++) {  
            list = new LinkedList();  
            et = (Element) jiedian.get(i);  
            if (et.getTextTrim().equals("")) {  
                if (et.getChildren().size() == 0)  
                    continue;  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(iterateElement(et));  
                obj.put(et.getName(), list);  
            } else {  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(et.getTextTrim());  
                obj.put(et.getName(), list);  
            }  
        }  
        return obj;  
    }  
}
