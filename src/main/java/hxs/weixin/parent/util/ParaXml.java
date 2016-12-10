package hxs.weixin.parent.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * 解析xml字符串 
 * 注意. 根节点为:"xml"
 * @author pc
 *
 */
public class ParaXml {
	private String xmlString;

	private Document document;

	public ParaXml(String xmlString) {
		this.xmlString = xmlString;

		InputSource inputSource = new InputSource(new StringReader(this.xmlString));
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
		} catch (SAXException | IOException | ParserConfigurationException e) {

			e.printStackTrace();
		}

	}

	public String getValue(String key) {

		NodeList root = document.getElementsByTagName("xml");
		 
		Node node = root.item(0); // NodeList中的某一个节点
		NodeList list =node.getChildNodes();
		
		for(int i = 0;i<list.getLength();i++){
			
			String ke = list.item(i).getNodeName();
			if(ke.equals(key)){
				String value =  list.item(i).getTextContent();
				return value;
			}
		}
        
		 return null;

		 
	}

	public static void main(String[] args) {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wxfd22896c1e4be407]]></appid><mch_id><![CDATA[1335354601]]></mch_id><nonce_str><![CDATA[Zo7BjtKgoDlHZfng]]></nonce_str><sign><![CDATA[7406F7227EB8694E40307C32FBCC9B67]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx2016050414584832bfa27f840031993394]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>";
		ParaXml paraXml = new ParaXml(xml);

		//String va = paraXml.getValue("return_code");

	}
}
