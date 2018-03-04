package com.weixin.utils.util;

import main.java.com.UpYun;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

/**
 * Created by :Guozhihua
 * Date： 2017/9/25.
 */
public class UpYunUtils {
    public static final String BUCKET_NAME = "e-edusunshine";
    public static final String OPERATION_NAME = "adminsunshine";
    public static final String OPERATION_PASSWORD = "adminsunshine2018";
    public static final String HTTPBASE = "http://e-edusunshine.b0.upaiyun.com";

    // form Api secret
    public static final String form_api_secret = "TdZFwS5Q2b32IreTneJDQiQJOcw=";


    public static final UpYun getUpyunCient() {
        return new UpYun(BUCKET_NAME, OPERATION_NAME, OPERATION_PASSWORD);
    }


    /**
     * 上传本地图片
     *
     * @param upanYunPath 例如  /test/image/
     * @param file        例如 ：xxxx.jpg
     * @return
     */
    public static final String uploadFile(String dirpath, String upanYunPath, File file) throws Exception {
        String result = null;
        String fileName = file.getName();

        boolean isOk = getUpyunCient().writeFile(upanYunPath + fileName, file);
        if (isOk) {
            result = HTTPBASE + upanYunPath + fileName;
        }
        return result;

    }


    /**
     * 以文件流的方式上传图片
     *
     * @param inputStream
     * @param upFilePath  例如  /teset/image/
     * @param fileName    upYun 上存储的文件名   uuid 最好是
     * @return
     * @throws Exception
     */
    public static final String uploadFileStream(InputStream inputStream, String upFilePath, String fileName) throws Exception {
        String result = null;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String userPath = upFilePath + year + "/" + month + "/" + day + "/" + fileName;
        boolean isOk = getUpyunCient().writeFile(userPath, IOUtils.toByteArray(inputStream), true);
        if (isOk) {
            result = HTTPBASE + userPath;
        }
        return result;

    }

    /**
     * 以文件流的方式上传图片
     *
     * @param productCode 产品Code 如 boss  ,textBook AI
     * @param fileSuffix  文件后缀   .jpg
     * @return
     * @throws Exception
     */
    public static final String getProductUrlPath(String productCode, String fileSuffix) throws Exception {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String fileUri = "/" + BUCKET_NAME + "/" + productCode + "/" + year + "/" + month + "/" + day + "/" + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
        return fileUri;

    }


    /**
     * 删除文件
     *
     * @param upFilePath /test/imgage/1111.jpg
     * @return
     * @throws Exception
     */
    public static final boolean deleteFile(String upFilePath) throws Exception {
        return getUpyunCient().deleteFile(upFilePath);

    }

    /**
     * 创建目录
     *
     * @param dirpath
     * @return
     * @throws Exception
     */
    public static final boolean mkdir(String dirpath) throws Exception {
        return getUpyunCient().mkDir(dirpath, true);

    }

    /**
     * 删除目录
     *
     * @param dirpath
     * @return
     * @throws Exception
     */
    public static final boolean rmdir(String dirpath) throws Exception {
        return getUpyunCient().rmDir(dirpath);

    }

    /**
     * 获取文件信息
     *
     * @param filepath 文件在又拍云的地址信息
     */
    public static Map<String, String> getFileInfo(String filepath) throws Exception {
        return getUpyunCient().getFileInfo(filepath);
    }

    /**
     * 获取bucket使用的空间大小 单位为 byte
     *
     * @return
     */
    public static long getBucketUsage() {
        return getUpyunCient().getBucketUsage();
    }

    public static void main(String[] args) {
        File file =new File("g:/video.html");
        try {
            System.out.println(uploadFile("g:video.html","/test/html/",file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}