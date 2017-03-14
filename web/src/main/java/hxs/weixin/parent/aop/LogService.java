package hxs.weixin.parent.aop;

import com.alibaba.fastjson.JSONObject;
import com.weixin.utils.sys.MethodLog;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2016/12/3.
 */

@Component
@Aspect
public class LogService {
    private static Map<String, Object> paramsMap = new HashMap<>();
    private static Logger logger = Logger.getLogger(LogService.class);
    private static JSONObject json = new JSONObject();
    private static LocalVariableTableParameterNameDiscoverer u =
            new LocalVariableTableParameterNameDiscoverer();
    @Pointcut("@annotation(com.weixin.utils.sys.MethodLog)")
    public void methodLogPointcut() {
    }
   /* @After(value = "methodLogPointcut()")
    public void after(JoinPoint joinPoint){

    }*/

    @Pointcut("execution(public * hxs.weixin.parent.service.impl..*.*(..))")
    public void serviceLog(){

    }

    @Around(value = "methodLogPointcut()")
    public Object advice(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            handleLog(joinPoint);
            logger.info("Method response is :"+json.toJSONString(obj));
            logger.info(" ==========================================================================================");
        } catch (Throwable e) {
            logger.error("Aspect aop log error:", e);
            logger.info(" Exception------------------------------------------------------------------------------");
        } finally {
            paramsMap.clear();
            return obj;
        }
    }

    @Around(value = "serviceLog()")
    public Object serViceAdvice(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[]  arguments = joinPoint.getArgs();
            logger.info(  targetName+"."+methodName+"  params is "+json.toJSONString(arguments));
            logger.info("Service Method response is :"+json.toJSONString(obj));
        } catch (Throwable e) {
            logger.error("Aspect aop log error:", e);
            logger.info(" Exception------------------------------------------------------------------------------");
        } finally {
            paramsMap.clear();
            return obj;
        }
    }


    private void handleLog(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = "";
        String methodName = "";
        Object[] arguments = null;
        String description = "";
        targetName = joinPoint.getTarget().getClass().getName();
        methodName = joinPoint.getSignature().getName();
        arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(MethodLog.class).remark();
                    String[] paramNames = u.getParameterNames(method);
                    for (int i = 0; i < arguments.length; i++) {
                        Object object = arguments[i];
                        if(object instanceof HttpServletRequest||object instanceof HttpServletResponse){
                            continue;
                        }
                        paramsMap.put(paramNames[i],object);
                    }
                }
                break;
            }
        }
        logger.info("Aop 后置通知【"+targetName + "." + methodName + " remark:[" + description + "],params = " + json.toJSONString(paramsMap) + "】");

    }



}
