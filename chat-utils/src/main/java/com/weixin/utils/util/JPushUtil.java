package com.weixin.utils.util;

import cn.jpush.api.JPushClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:
 */
@Component("JPushUtil")
public class JPushUtil {
    protected final Logger logger = LoggerFactory.getLogger(JPushUtil.class);

    private static  JPushClient jPushClient;


    
//
//    /**
//     * IOS 安卓  推送对战消息
//     *
//     * @param  noticeModel 消息对象
//     *          推送的对象
//     * @param isalias
//     *          boolean值 true 表示推送别名 false 推送标签
//     */
//    public  PushResult sendNoticeModelToAllPlatform(NoticeModel noticeModel, boolean isalias) {
//        int msgCount=3;
//        long dateTime  = new Date().getTime();
//        PushResult result =null;
//        PushPayload.Builder builder = PushPayload.newBuilder();
//        JPushClient jpushClient = getJpushClient();
//        Map<String,String> sendMap=new HashMap<>();
//        sendMap.put("content",noticeModel.getContent());
//        sendMap.put("title", noticeModel.getTitle());
//        sendMap.put("type",noticeModel.getType());
//        sendMap.put("messageId", noticeModel.getMessageId());
//        sendMap.put("sendType",noticeModel.getSendType());
//        sendMap.put("object",noticeModel.getObject());
//        builder
//                .setPlatform(Platform.android_ios())
//                .setNotification(
//                        Notification
//                                .newBuilder().setAlert(noticeModel.getTitle())
//                                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(noticeModel.getTitle()).addExtras(sendMap).addExtra("time",dateTime).build())
//                                .addPlatformNotification(IosNotification.newBuilder().setBadge(msgCount).setSound("default").addExtras(sendMap).addExtra("time",dateTime).build())
//                                .build())
//                .setMessage(Message.newBuilder().setMsgContent(noticeModel.getContent()).addExtras(sendMap).build())
//                .setOptions(Options.newBuilder().setTimeToLive(86400 * 3).build());
//        if (isalias){
//            builder.setAudience(Audience.alias(noticeModel.getObject()));
//        }
//        else{
//            builder.setAudience(Audience.tag(noticeModel.getObject()));
//            System.out.println(builder.build().toJSON());
//        }
//
//        try {
//             result = jpushClient.sendPush(builder.build());
//        } catch (APIConnectionException e) {
//            logger.error("jpush send message error!" ,e);
//        } catch (APIRequestException e) {
//            logger.error("jpush send message error!" ,e);
//        }
//        return  result;
//    }

//
//    /**
//     * 安卓  推送对战消息
//     *
//     * @param  noticeModel 消息对象
//     *          推送的对象
//     * @param isalias
//     *          boolean值 true 表示推送别名 false 推送标签
//     */
//    public  PushResult sendNoticeModelToAndriod(NoticeModel noticeModel, boolean isalias) {
//        long dateTime  = new Date().getTime();
//        PushResult result =null;
//        PushPayload.Builder builder = PushPayload.newBuilder();
//        JPushClient jpushClient = getJpushClient();
//        Map<String,String> sendMap=new HashMap<>();
//        sendMap.put("content",noticeModel.getContent());
//        sendMap.put("title", noticeModel.getTitle());
//        sendMap.put("type",noticeModel.getType());
//        sendMap.put("messageId", noticeModel.getMessageId());
//        sendMap.put("sendType",noticeModel.getSendType());
//        sendMap.put("object",noticeModel.getObject());
//        builder
//                .setPlatform(Platform.android())
//                .setNotification(
//                        Notification
//                                .newBuilder().setAlert(noticeModel.getTitle())
//                                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(noticeModel.getTitle()).addExtras(sendMap).addExtra("time",dateTime).build())
//                                .build())
//                .setMessage(Message.newBuilder().setMsgContent(noticeModel.getContent()).addExtras(sendMap).build())
//                .setOptions(Options.newBuilder().setTimeToLive(86400 * 3).build());
//        if (isalias){
//            builder.setAudience(Audience.alias(noticeModel.getObject()));
//        }
//        else{
//            builder.setAudience(Audience.tag(noticeModel.getObject()));
//            System.out.println(builder.build().toJSON());
//        }
//        try {
//            result = jpushClient.sendPush(builder.build());
//        } catch (APIConnectionException e) {
//            logger.error("jpush send to andriod message error!" ,e);
//        } catch (APIRequestException e) {
//            logger.error("jpush send  to andriod message error!" ,e);
//        }
//        return  result;
//    }

//
//    /**
//     * 安卓  推送对战消息
//     *
//     * @param  noticeModel 消息对象
//     *          推送的对象
//     * @param isalias
//     *          boolean值 true 表示推送别名 false 推送标签
//     */
//    public  PushResult sendNoticeModelToIos(NoticeModel noticeModel, boolean isalias) {
//        int msgCount=3;
//        long dateTime  = new Date().getTime();
//        PushResult result =null;
//        PushPayload.Builder builder = PushPayload.newBuilder();
//        JPushClient jpushClient = getJpushClient();
//        Map<String,String> sendMap=new HashMap<>();
//        sendMap.put("content",noticeModel.getContent());
//        sendMap.put("title", noticeModel.getTitle());
//        sendMap.put("type",noticeModel.getType());
//        sendMap.put("messageId", noticeModel.getMessageId());
//        sendMap.put("sendType",noticeModel.getSendType());
//        sendMap.put("object",noticeModel.getObject());
//        builder
//                .setPlatform(Platform.ios())
//                .setNotification(
//                        Notification
//                                .newBuilder().setAlert(noticeModel.getTitle())
//                                .addPlatformNotification(IosNotification.newBuilder().setBadge(msgCount).setSound("default").addExtras(sendMap).addExtra("time",dateTime).build())
//                                .build())
//                .setMessage(Message.newBuilder().setMsgContent(noticeModel.getContent()).addExtras(sendMap).build())
//                .setOptions(Options.newBuilder().setTimeToLive(86400 * 3).build());
//        if (isalias){
//            builder.setAudience(Audience.alias(noticeModel.getObject()));
//        }
//        else{
//            builder.setAudience(Audience.tag(noticeModel.getObject()));
//            System.out.println(builder.build().toJSON());
//        }
//        try {
//            result = jpushClient.sendPush(builder.build());
//        } catch (APIConnectionException e) {
//            logger.error("jpush send to ios message error!" ,e);
//        } catch (APIRequestException e) {
//            logger.error("jpush send to ios message error!" ,e);
//        }
//        return  result;
//    }

//    public synchronized JPushClient getJpushClient(){
////        String masterSecret = "bf0eab6a8f5babb7dd9efe4f";
////        String appKey = "b365629ce0fcf79de10be53c";
//        if(jPushClient==null){
////            jPushClient = new JPushClient(masterSecret,appKey);
//            jPushClient = new JPushClient(JPush_sercet, JPush_appKey);
//        }
//        return  jPushClient;
//    }

//    public static void main(String[] args) {
//        //9e3582a4071a482196e1e565fb704053   fightId:585f8297069e43c7ba33481e789f729a
//        NoticeModel noticeModel=new NoticeModel();
//        noticeModel.setTitle(NoticeEnum.NoticeType.BATTLE_NOTICE.getDesc());//对战通知
//        noticeModel.setObject("9e3582a4071a482196e1e565fb704053");
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("fightUUID", "585f8297069e43c7ba33481e789f729a");
//        map.put("receiver","9e3582a4071a482196e1e565fb704053");
//        map.put("fightResult", 0);
//        map.put("fightDate", "2016-10-24");
//        map.put("fightValue", 0);
//        map.put("integral", 0);
//        map.put("opponentNickName","iwrong13833682");
//        map.put("opponentUserCode", "eeduspacerobot112");
//        map.put("unitCode", "66580002");
//        String content1=new Gson().toJson(map);
//        noticeModel.setContent(content1);
//        noticeModel.setSendType(NoticeEnum.NoticeType.BATTLE_NOTICE.getCode());//对战通知
//        noticeModel.setType(NoticeEnum.Type.ALIAS.getCode());
//        noticeModel.setMessageId(UUID.randomUUID().toString());
////        new JPushUtil().sendNoticeModelToAllPlatform(noticeModel,true);
//    }
//


}
