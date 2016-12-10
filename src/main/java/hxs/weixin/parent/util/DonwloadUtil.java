package hxs.weixin.parent.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class DonwloadUtil {

	public static void download(String path,HttpServletResponse response) {
		try {
			String suffix = path.substring(path.indexOf("."));//后缀
			String name = UUID.randomUUID().toString() + suffix;//文件名加后缀
			InputStream is = new FileInputStream(path);
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");//不同类型的文件对应不同的MIME类型
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename="+ name);
			ServletOutputStream outputStream = response.getOutputStream();
			byte[] b = new byte[2048];
			int length = 0;
			while ((length = is.read(b)) != -1) {
				outputStream.write(b, 0, length);
				outputStream.flush();
			}
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
