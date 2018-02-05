package xchat.listeners;

import org.apache.log4j.Logger;

/**
 * Created by :Guozhihua
 * Date： 2017/5/12.
 */
public   class MsgEventConsumer extends BaseConsumer {

    private static Logger logger = Logger.getLogger(MsgEventConsumer.class);

    private  String consumerNum;

    public MsgEventConsumer(MsgQueue msgQueue,String consumerNum) {
        super(msgQueue);
        this.consumerNum=consumerNum;
    }

    @Override
    public void run() {
        while (MsgConsumerIniter.flag){
            try{
                if(msgQueue.getQueueSize()>0){
                    MsgEvent msgEvent = msgQueue.takeOut();
                    //消息拿出来之后给worker，不同的类型给不同的worker.
                    ConsumerProxyFactory.getInstance().getWorker(msgEvent.getMsgType()).execute(msgEvent);
                }else{
                    Thread.sleep(2000);
                }
            }catch (InterruptedException inter){
                logger.error(consumerNum+":MsgEventConsumer is InterruptedException "+inter.getMessage());

            }catch (Exception ex){
                logger.error(consumerNum+":MsgEventConsumer found exception",ex);
            }
        }
        if(!MsgConsumerIniter.flag){
            logger.info(consumerNum+"MsgEventConsumer Thread is run over ...");
        }
    }

}
