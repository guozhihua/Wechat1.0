package hxs.weixin.web.controller.test;

import com.weixin.queue.message.MessageQueueName;
import com.weixin.queue.message.MessageType;
import com.weixin.utils.responsecode.BaseResponse;
import com.weixin.utils.sys.MethodLog;
import com.weixin.yj.search.ESHelper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/4/24.
 */

@Controller
@RequestMapping(value = "/search/")
public class TestSearch {

    /**
     * 发送消息
     */
    @RequestMapping(value="send",method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse saveUserVoucher(){
        BaseResponse baseResponse = new BaseResponse();
        try{
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            boolQuery.must(QueryBuilders.termQuery("houseName", "唐槐园"));
            List<String> partyIndexs = new ArrayList<String>();
            ESHelper.getInstance().searchQuery("house","price_info",boolQuery,null,null,1,1000);
        }catch (Exception ex){
            ex.printStackTrace();
        }



        return baseResponse;
    }
}
