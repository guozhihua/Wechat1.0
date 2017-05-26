package com.chat.sso.queue;



import com.chat.sso.exception.ProException;
import com.weixin.utils.util.SpringBeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public class ConsumerProxyFactory implements WorkerFactory {

    public static class ConsumerProxyFactoryHoder{
         public static ConsumerProxyFactory consumerProxyFactory=new ConsumerProxyFactory();
    }

    private static ThreadLocal<Map<String,Worker>> comsumers = new ThreadLocal<Map<String,Worker>>(){
        public Map<String,Worker> initialValue(){
            return  new ConcurrentHashMap<>();
        }

    };

    public  static    ConsumerProxyFactory getInstance(){
          return  ConsumerProxyFactoryHoder.consumerProxyFactory;
    }

    @Override
    public Worker getWorker(String messageType)  throws ProException {
        Worker consumerProxy=null;
        Map<String,Worker> consumerMap =comsumers.get();
        if(consumerMap.containsKey(messageType)){
            consumerProxy = consumerMap.get(messageType);
        }else{
            //需要找到对应messageType的处理类
            Worker consumer =null;
            if(MessageType.CLASS_MODULE.getTypeCode().equals(messageType)){
                consumer= (Worker) SpringBeanUtils.getBean("classModuleWorker");
            }else  if(MessageType.CLASS_REPORT.getTypeCode().equals(messageType)){
                consumer= (Worker)SpringBeanUtils.getBean("classReportWorker");
            }else  if(MessageType.ClASS_WRONG.getTypeCode().equals(messageType)){
                consumer= (Worker)SpringBeanUtils.getBean("classWrongWorker");
            }else  if(MessageType.ClASS_WRONG_KNOWLEDGE.getTypeCode().equals(messageType)){
                consumer= (Worker)SpringBeanUtils.getBean("classWrongKnowledgeWorker");
            }else{
                throw  new ProException("50000","message type :"+messageType+"非法..");
            }
            if(consumer!=null){
                consumerMap.put("messageType",consumer) ;
                consumerProxy=consumer;
                comsumers.set(consumerMap);
            }
        }
        if(consumerProxy==null){
            throw  new ProException("50000","message type :"+messageType+"没有指定相应的业务处理类,");
        }
        return  consumerProxy;
    }
}
