package xchat.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties= new Properties();

    /*properties文件名*/
    private static final String PROPERTIES_FILE_NAME= "/code.properties";
    /*键*/
    private static final String KEY_LASTID="zhishicode";


    /**
     * 初始化properties，即载入数据
     */
    private static void initProperties(){
        try {
            InputStream ips = PropertiesUtil.class.getResourceAsStream(PROPERTIES_FILE_NAME);
            properties.load(ips);
            ips.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**将数据载入properties，并返回"lastid"的值
     * @return
     */
    public static int getPrimaryKey(){
        if(properties.isEmpty())//如果properties为空，则初始化一次。
            initProperties();
        return Integer.valueOf(properties.getProperty(KEY_LASTID)); //properties返回的值为String，转为整数
    }

    /**修改lastid的值，并保存
     * @param id
     */
    public static void saveLastKey(int id){
        if(properties.isEmpty())
            initProperties();
        //修改值
        properties.setProperty(KEY_LASTID, id+"");
        //保存文件
        try {
            URL fileUrl = PropertiesUtil.class.getResource(PROPERTIES_FILE_NAME);//得到文件路径
            FileOutputStream fos = new FileOutputStream(new File(fileUrl.toURI()));
            properties.store(fos, KEY_LASTID);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.println(getPrimaryKey());
        saveLastKey(1);
        System.out.println(getPrimaryKey());

    }
}