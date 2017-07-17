package com.weixin.utils.QRcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.weixin.utils.AESUtils;
import com.weixin.utils.util.DBHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
* @Author zhusen
* @Date 2016/8/27
*/
public class ZXingUtils {

    private static final MatrixToLogoImageConfig DEFAULT_CONFIG = new MatrixToLogoImageConfig();

    /**
     * 公司logo，放在项目根目录下
     */
    private static final String LOGO_NAME = "companyLogo.jpg";

    private static final DBHelper dbHelper = new DBHelper();

    private static final String ENCRYPT_KEY = "e-edusky_20161230";

    /**
     * 生成二维码
     *
     * @param str      待生成二维码的字符串
     * @param width    二维码宽度
     * @param height   二维码高度
     * @param savePath 二维码保存路径
     * @param format   生成文件格式（jpg、png）
     */
    public static void enCode(String str, int width, int height, String savePath, String format) {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            File file = new File(savePath);
            // 写入文件
            MatrixToImageWriter.writeToFile(matrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析二维码
     *
     * @param filePath
     * @return
     */
    public static String deCode(String filePath) {
        File file = new File(filePath);
        BufferedImage bufferedImage;
        Result result = null;
        try {
            bufferedImage = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            result = new MultiFormatReader().decode(bitmap, hints);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();

    }

    /**
     * 生成带公司logo二维码
     *
     * @param realPath  文件路径
     * @param encodeStr 要生成二维码的链接地址
     * @param width
     * @param height
     */
    public static void createQRCodeWithLogo(String realPath, String encodeStr, int width, int height) {
        String filePath = realPath + System.currentTimeMillis() + "_" + new Random().nextInt(10) + ".jpg";
        try {
            FileUtils.forceMkdir(new File(realPath));
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 0);//设置生成的二维码没有白边
            BitMatrix matrix = new MultiFormatWriter().encode(encodeStr, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            String logoPath = ZXingUtils.class.getClassLoader().getResource(LOGO_NAME).getPath();
            writeToFile(matrix, "jpg", filePath, logoPath, DEFAULT_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入二维码、以及将照片logo写入二维码中
     *
     * @param matrix     要写入的二维码
     * @param format     二维码照片格式
     * @param imagePath  二维码照片保存路径
     * @param logoPath   logo路径
     * @param logoConfig logo配置对象
     * @throws IOException
     */
    private static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath,
                                    MatrixToLogoImageConfig logoConfig) throws IOException {
        MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath), new MatrixToImageConfig());

        // 添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
        BufferedImage img = ImageIO.read(new File(imagePath));
        overlapImage(img, format, imagePath, logoPath, logoConfig);
    }

    private static void overlapImage(BufferedImage image, String formate, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) {
        try {
            BufferedImage logo = ImageIO.read(new File(logoPath));
            Graphics2D g = image.createGraphics();
            // 考虑到logo照片贴到二维码中，建议大小不要超过二维码的1/5;
            int width = image.getWidth() / logoConfig.getLogoPart();
            int height = image.getHeight() / logoConfig.getLogoPart();
            // logo起始位置，此目的是为logo居中显示
            int x = (image.getWidth() - width) / 2;
            int y = (image.getHeight() - height) / 2;
            // 绘制图
            g.drawImage(logo, x, y, width, height, null);

            // 给logo画边框
            // 构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, width, height);

            g.dispose();
            // 写入logo照片到二维码
            ImageIO.write(image, formate, new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成辽师大二维码
     */
    public static void creatLsdCode() {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"自主学\",\"gradeCode\":13,\"subjectCode\":3,\"bookType\":\"辽师大版\"}", ENCRYPT_KEY));
        object.put("name", "自主学三年级英语辽师大版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"自主学\",\"gradeCode\":15,\"subjectCode\":3,\"bookType\":\"辽师大版\"}", ENCRYPT_KEY));
        object.put("name", "自主学五年级英语辽师大版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"新课程新教材导航\",\"gradeCode\":21,\"subjectCode\":3,\"bookType\":\"外研版\"}", ENCRYPT_KEY));
        object.put("name", "新课程新教材导航七年级英语外研版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"新课程新教材导航\",\"gradeCode\":22,\"subjectCode\":3,\"bookType\":\"外研版\"}", ENCRYPT_KEY));
        object.put("name", "新课程新教材导航八年级英语外研版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"新课程新教材导航\",\"gradeCode\":21,\"subjectCode\":2,\"bookType\":\"北师大版\"}", ENCRYPT_KEY));
        object.put("name", "新课程新教材导航七年级数学北师大版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"新课程新教材导航\",\"gradeCode\":22,\"subjectCode\":2,\"bookType\":\"北师大版\"}", ENCRYPT_KEY));
        object.put("name", "新课程新教材导航八年级数学北师大版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"新课程新教材导航\",\"gradeCode\":23,\"subjectCode\":2,\"bookType\":\"北师大版\"}", ENCRYPT_KEY));
        object.put("name", "新课程新教材导航九年级数学北师大版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"中考全程复习训练（1、2、3轮）DL\",\"gradeCode\":24,\"subjectCode\":2,\"bookType\":\"通用版\"}", ENCRYPT_KEY));
        object.put("name", "中考全程复习训练（1、2、3轮）DL中考数学通用版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"中考全程复习训练（1、2、3轮）DL\",\"gradeCode\":24,\"subjectCode\":3,\"bookType\":\"通用版\"}", ENCRYPT_KEY));
        object.put("name", "中考全程复习训练（1、2、3轮）DL中考英语通用版");
        array.add(object);
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"2017年中考必备\",\"gradeCode\":24,\"subjectCode\":2,\"bookType\":\"辽宁版\"}", ENCRYPT_KEY));
        object.put("name", "2017年中考必备中考数学辽宁版");
        object = new JSONObject();
        object.put("baseData", AESUtils.encrypt("{\"type\":\"2017年中考必备\",\"gradeCode\":22,\"subjectCode\":2,\"bookType\":\"北师大版\"}", ENCRYPT_KEY));
        object.put("name", "2017年中考必备八年级数学北师大版");
        array.add(object);

        for (int i = 0; i < array.size(); i++) {
            JSONObject ob = array.getJSONObject(i);
            enCode(ob.getString("baseData"), 200, 200, "C:\\Users\\yjkj\\Desktop\\二维码\\" + ob.getString("name") + ".jpg", "jpg");
        }
//		enCode("222", 200, 200, "d:\\123.jpg", "jpg");

//		String url = deCode("d:\\123.jpg");
//		System.out.println(url);

//        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        hints.put(EncodeHintType.MARGIN, 0);
//
//        try {
//            BitMatrix matrix = new MultiFormatWriter().encode("呵呵呵", BarcodeFormat.QR_CODE, 300, 300, hints);// 生成矩阵
//            // 写入文件
//            writeToFile(matrix, "jpg", "C:/Users/WG/Desktop/二维码/80.jpg", "C:/Users/WG/Desktop/二维码/90.jpg", DEFAULT_CONFIG);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    public static List<SBook> getBook() {
        String sql = "SELECT * FROM s_book WHERE book_id >= 98 AND  parent_id = 0";
        ResultSet ret = dbHelper.executeQuery(sql);
        PreparedStatement st = null;
        List<SBook> list = null;
        try {
            try {
                st = dbHelper.conn.prepareStatement(sql);
                ret = st.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            list = new ArrayList<>();
            while (ret.next()) {
                SBook book = new SBook();
                Integer bookId = ret.getInt(1);
                book.setBookId(bookId);
                String bookName = ret.getString(2);
                book.setBookName(bookName);
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void creatBookCode() {
        List<SBook> list = getBook();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                SBook book = list.get(i);
                String bookName = book.getBookName().replaceAll(" ", "_").replaceAll(" ", "_");
                String filePath = "C:\\Users\\yjkj\\Desktop\\正式二维码\\" + bookName + "_" + book.getBookId();
                File file = new File(filePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String studentfileName = book.getBookName() + "_" + book.getBookId() + "_" + "学生端" + ".jpg";
                String teacherfileName = book.getBookName() + "_" + book.getBookId() + "_" + "教师端" + ".jpg";
                String studentUrl = "http://textbook.e-edusky.com/sync_coach/html/student/book/detail.html?id=" + book.getBookId() + "&userType=1";
                String teacherUrl = "http://textbook.e-edusky.com/sync_coach/html/student/book/detail.html?id=" + book.getBookId() + "&userType=2";
                enCode(studentUrl, 200, 200, filePath + "\\" + studentfileName, "jpg");
                enCode(teacherUrl, 200, 200, filePath + "\\" + teacherfileName, "jpg");
            }
        }
    }

    static class SBook {

        private Integer bookId;

        private String bookName;

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public Integer getBookId() {

            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }
    }

    public static void main(String[] args) {
        creatBookCode();
    }
}
