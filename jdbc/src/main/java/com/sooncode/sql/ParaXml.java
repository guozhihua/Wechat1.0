package com.sooncode.sql;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * 解析xml字符串 
 * 注意. 根节点为:"xml"
 * @author pc
 *
 */
class ParaXml {
	private  static final Logger logger=Logger.getLogger(ParaXml.class);
	private String xmlString;

	private Document document;

	public ParaXml(String xmlString) {
		this.xmlString = xmlString;

		InputSource inputSource = new InputSource(new StringReader(this.xmlString));
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error("ParaXml error "+e);
//			e.printStackTrace();
		}

	}

	 
	
	
	public  String getValue(String id) {
		
		NodeList root = document.getElementsByTagName("sqls");
		 
		Node node = root.item(0); // NodeList中的某一个节点
		NodeList list =node.getChildNodes();
		
		for(int i = 0;i<list.getLength();i++){
			
			    String ke = list.item(i).getNodeName();
			    if( ! ke.equals("#text")){
			    	
			    	Element element = (Element) list.item(i); 
			    	String thisId = element.getAttribute("id");
			    	if(id.equals(thisId)){
			    		String value =  list.item(i).getTextContent();
			    		return value;
			    	}
			    }
		}
		return null;
	}
	public  String getValue(String id,Object... objes) {
		
		NodeList root = document.getElementsByTagName("sqls");
		
		Node node = root.item(0); // NodeList中的某一个节点
		NodeList list =node.getChildNodes();
		
		for(int i = 0;i<list.getLength();i++){
			
			String ke = list.item(i).getNodeName();
			if( ! ke.equals("#text")){
				
				Element element = (Element) list.item(i); 
				String thisId = element.getAttribute("id");
				if(id.equals(thisId)){
					String value =  list.item(i).getTextContent();
					
					return value;
				}
			}
		}
		return null;
	}
 
}
