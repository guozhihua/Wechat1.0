package xchat.listeners;



import xchat.sys.MessageType;
import xchat.sys.ProException;
import xchat.sys.SpringBeanUtils;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public class ConsumerProxyFactory implements WorkerFactory {

   public static List<Worker> workerList= new Vector();

    public  void shutDownWorker(){
        if(workerList.size()>0){
            for(Worker worker:workerList){
                worker.stop();
            }
        }

    }

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
    public Worker getWorker(String messageType)  throws ProException{
        Worker consumerProxy=null;
        Map<String,Worker> consumerMap =comsumers.get();
        if(consumerMap.containsKey(messageType)){
            consumerProxy = consumerMap.get(messageType);
        }else{
            //需要找到对应messageType的处理类
            Worker consumer =null;
            if(MessageType.HUANG_JIN_DAREN.getTypeCode().equals(messageType)){
                consumer= (Worker) SpringBeanUtils.getBean("HJDRWorker");
                workerList.add(consumer);
            }else if(MessageType.HUANG_JIN_DR_ANSWER.getTypeCode().equals(messageType)){
                consumer= (Worker) SpringBeanUtils.getBean("HJDRWorkerAnswer");
                workerList.add(consumer);
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
