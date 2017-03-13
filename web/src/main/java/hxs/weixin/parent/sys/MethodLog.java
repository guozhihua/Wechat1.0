package hxs.weixin.parent.sys;


import java.lang.annotation.*;

/**
 * Created by :Guozhihua
 * Date： 2016/12/3.
 */
@Target({ElementType.PARAMETER , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
    String remark() default "";
    String operType() default "0";
}