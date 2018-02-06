/**
 * Created by 志华 on 2017/8/2.
 */
var domain ="http://dev.edu.cn/";
document.write("<script type='text/javascript' src='"+domain+"coder/coder/jquery-m/jquery.min-1.4.5.js'></script>");
document.write("<link  rel='stylesheet' href='"+domain+"coder/bootstrap/css/bootstrap.min.css'/>");

document.write("<script type='text/javascript' src='"+domain+"coder/coder/js/url.js'></script>");
document.write("<script type='text/javascript' src='"+domain+"coder/coder/js/jquery.cookie.js'></script>");
document.write("<script type='text/javascript' src='"+domain+"coder/bootstrap/js/bootstrap.min.js'></script>");
var ajaxObject = {};

ajaxObject.postFormAjax = function ajaxPostForm(url, datas, callbak, dataType) {
    $.ajax({
        url: url, data: datas, type: "POST", asyc: true, dataType: dataType,
        success: callbak,
        beforeSend:function(request){
            request.setRequestHeader("passport-ticket", $.cookie('passport-ticket'));
        },
        //headers: {'passport_ticket':$.cookie('passport_ticket')},
        error: error,
        statusCode: {
            709: function () {
                window.location.href="../coder/index.html";
            }
        }

    });

}
ajaxObject.getFormAjax = function ajaxGetForm(url, datas, callbak, dataType) {
    $.ajax({
        url: url, data: datas, type: "GET", asyc: true, dataType: dataType,
        success: callbak,
        //beforeSend:beforeSend,
        headers: {'passport_ticket':$.cookie('passport_ticket')},
        error: error,
        statusCode: {
            702: function () {
                window.location.href="../coder/index.html";
            }
        }

    });

}

var callback = function success(data) {
    if(data!=null&&data!='undefine'){
       alertCommon(data.msg);
    }
}
var alertCommon=function(msg){
    var obj={
        type:"layer-spread",
        title:"提示信息",
        content:msg,
        area:"auto",
        option:1,
        close:"true",
        closeTime:2500
    };
    method.msg_layer(obj);
}
var alertError=function(msg){
    var obj={
        type:"layer-spread",
        title:"出错了！",
        content:msg,
        area:"auto",
        option:2,
        close:"true"
    };
    method.msg_layer(obj);
}
var alertSuccess=function(msg){
    var obj={
        type:"layer-spread",
        title:"成功信息",
        content:msg,
        area:"auto",
        option:3,
        close:"true",
        closeTime:2500
    };
    method.msg_layer(obj);
}
var error = function (jqXHR, textStatus, errorMsg) {
    alertError(errorMsg);
}
