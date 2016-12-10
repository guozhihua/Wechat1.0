package hxs.weixin.parent.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * http 请求 其他资源系统服务器
 * 
 * @author hechen
 *
 */
public class HttpRequest {

	/**
	 * 发送Http get 请求
	 * 
	 * @param url
	 *            资源路径
	 * @return 成功:返回结果数据 错误则返回 ： ERROR
	 */
	public static String getRequest(String url) {

		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 
	 * @param url
	 *            资源路径
	 * @param requestTpye
	 *            请求方式
	 * @param JsonData
	 *            json类型的数据
	 * @return
	 */
	public static String postRequest(String url, String jsonData) {

		try {

			URL uRL = new URL(url);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) uRL.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			if (jsonData != null) {
				out.append(jsonData);
			}
			out.flush();
			out.close();
			// 读取响应

			InputStream is = connection.getInputStream();

			byte[] data = new byte[1000 * 1000];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
			String result = new String(data, "UTF-8").trim(); // utf-8编码
			return result;

		} catch (IOException e) {
			//e.printStackTrace();
			return null; // 自定义错误信息
		}

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param parameter
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String postRequest(String url, Map<String, String> parameter) {

		String parameterString = "";
		int n = 0;
		for (Entry<String, String> entry : parameter.entrySet()) {
			if (n != 0) {
				parameterString = parameterString + "&";
			}
			parameterString = parameterString + entry.getKey() + "=" + entry.getValue();
			n++;
		}

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(parameterString);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 
	 * @param url
	 *            资源路径
	 * @param JsonData
	 *            xml类型的数据
	 * @return
	 */
	public static String postRequest4xml(String url, String xml) {

		try {

			// 把XML的数据转成字符串
			String str = xml;
			System.out.println(str);
			byte[] bb = str.getBytes();
			// 请求地址

			URL uRL = new URL(url);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) uRL.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(5 * 1000);// 设置超时的时间
			connection.setDoInput(true);
			connection.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
			connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			connection.setRequestProperty("Content-Length", String.valueOf(bb.length));
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(str); // 写入请求的字符串
			out.flush();
			out.close();
			// 请求返回的状态
			if (connection.getResponseCode() == 200) {
				System.out.println("yes++");
				// 请求返回的数据
				InputStream in = connection.getInputStream();
				String a = null;
				try {
					byte[] data1 = new byte[in.available()];
					in.read(data1);
					// 转成字符串
					a = new String(data1, "utf-8");
					System.out.println(a);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return a;
			} else {
				System.out.println("no++");
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		String url = "http://218.240.38.108/uutool/sms/getDetail";
		// 发送 POST 请求
		Map<String, String> map = new HashMap<>();
		map.put("productId", "32bdd1c5-8eef-4843-9a9a-228dc0f3b617");
		map.put("type", "day365");
		String sr = HttpRequest.postRequest(url, map);
		System.out.println(sr);

	}

}