import org.springframework.util.StringUtils;

import java.lang.reflect.Array;

/**
 * Created by :Guozhihua
 * Date： 2017/7/24.
 */
public class Test {

    public static void main(String[] args) {
        String[] array= new String[]{"123123","12312"};
        System.out.println(StringUtils.arrayToCommaDelimitedString(array));


    }
}
