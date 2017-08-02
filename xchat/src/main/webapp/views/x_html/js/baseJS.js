/**
 * Created by 志华 on 2017/8/2.
 */

var ajaxObject=new Object();

var contextUrl="http://101.200.55.143/xchat";

ajaxObject.postFormAjax=function ajaxPostForm(url,datas,callbak,dataType){
    url=contextUrl+url;
    $.post(url,datas,function(data,status){
        callbak.callback(data)
    },"json");
}
var  callback=new Object();
callback.callback=function success(data){
    alert(data.code);
    alert(data.msg);
}