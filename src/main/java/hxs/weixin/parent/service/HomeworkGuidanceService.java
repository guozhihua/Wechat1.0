package hxs.weixin.parent.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface HomeworkGuidanceService {

	/**
	 * 根据学科，教材版本获取产品列表
	 * @param baseResourceModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<Map<String, Object>> getProductList(String subjectCode,String editionCode) throws ClientProtocolException, IOException;
	
	/**
	 * 根据ctbCode获取节点信息和视频
	 * @param ctbCode
	 * @return
	 */
	public List<Map<String, Object>> getInformationAndVideo(String ctbCode) throws IOException;
}
