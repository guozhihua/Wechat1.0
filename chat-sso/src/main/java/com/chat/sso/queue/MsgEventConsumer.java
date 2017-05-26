package com.chat.sso.queue;


/**
 * Created by :Guozhihua
 * Date： 2017/5/12.
 */
public   class MsgEventConsumer extends BaseConsumer {

    private  String consumerNum;

    public MsgEventConsumer(MsgQueue msgQueue,String consumerNum) {
        super(msgQueue);
        this.consumerNum=consumerNum;
    }

    @Override
    public void run() {
        do{
            try{
                if(msgQueue.getQueueSize()>0){
                    MsgEvent msgEvent = msgQueue.takeOut();
                    //消息拿出来之后给worker，不同的类型给不同的worker.
                    ConsumerProxyFactory.getInstance().getWorker(msgEvent.getMsgType()).execute(msgEvent);
                }else{
                    Thread.sleep(2000);
                }
            }catch (InterruptedException inter){

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }while (MsgConsumerIniter.flag);
    }

}
