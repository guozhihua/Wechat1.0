package hxs.weixin.web.controller;

import com.weixin.queue.message.MessageQueueName;
import com.weixin.queue.message.MessageType;
import com.weixin.queue.message.QueueMessageUtils;
import com.weixin.utils.responsecode.BaseResponse;
import com.weixin.utils.sys.MethodLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/3/14.
 */
@Controller
@RequestMapping(value = "/message/")
public class TestMessageController extends ABaseController{
    private static Logger logger = Logger.getLogger(TestMessageController.class);

    @Autowired
    private QueueMessageUtils queueMessageUtils;
    /**
     * 发送消息
     */
    @RequestMapping(value="send",method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse saveUserVoucher(){
        BaseResponse baseResponse = new BaseResponse();
        try{
            HashMap<String,Object> messageDate=new HashMap<>();
            messageDate.put("le","234");
            queueMessageUtils.sendSerializableMessage(MessageQueueName.textMessageQueue,messageDate, MessageType.CLASS_MODULE);
        }catch (Exception ex){
            logger.error(ex);
        }



        return baseResponse;
    }

}
