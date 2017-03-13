package hxs.weixin.parent.util.messages;


import hxs.weixin.parent.sys.MessageType;
import hxs.weixin.parent.sys.exceptions.ProBaseException;
import hxs.weixin.parent.util.SpringBeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public class ConsumerProxyFactory {

    private static ThreadLocal<Map<String,Consumer>> comsumers = new ThreadLocal<Map<String,Consumer>>(){
        public Map<String,Consumer> initialValue(){
            return  new ConcurrentHashMap<>();
        }

    };

    public static Consumer getConsumer(String messageType) throws ProBaseException {
        Consumer consumerProxy=null;
        Map<String,Consumer> consumerMap =comsumers.get();
        if(consumerMap.containsKey(messageType)){
            consumerProxy = consumerMap.get(messageType);
        }else{
            //需要找到对应messageType的处理类
            Consumer consumer =null;
            if(MessageType.CLASS_MODULE.getTypeCode().equals(messageType)){
                 consumer= (Consumer) SpringBeanUtils.getBean("classModuleWorker");
            }else  if(MessageType.CLASS_REPORT.getTypeCode().equals(messageType)){
                consumer= (Consumer)SpringBeanUtils.getBean("classReportWorker");
            }else  if(MessageType.ClASS_WRONG.getTypeCode().equals(messageType)){
                consumer= (Consumer)SpringBeanUtils.getBean("classWrongWorker");
            }else  if(MessageType.ClASS_WRONG_KNOWLEDGE.getTypeCode().equals(messageType)){
                consumer= (Consumer)SpringBeanUtils.getBean("classWrongKnowledgeWorker");
            }else{
                throw  new ProBaseException("message type :"+messageType+"非法..");
            }
            if(consumer!=null){
                consumerMap.put("messageType",consumer) ;
                consumerProxy=consumer;
                comsumers.set(consumerMap);
            }
        }
        if(consumerProxy==null){
            throw  new ProBaseException("message type :"+messageType+"没有指定相应的业务处理类,");
        }
        return  consumerProxy;

    }


}
