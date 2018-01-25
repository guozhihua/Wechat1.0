package xchat.aop;

import java.lang.annotation.*;

/**
 * Created by :Guozhihua
 * Date： 2017/5/9.
 */
@Target({ElementType.PARAMETER , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {
    /**
     * 接口描述信息
     * @return
     */
    String remark() default "";

}
