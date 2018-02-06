/**
 * Created by 志华 on 2017/8/2.
 */
var domain ="http://dev.edu.cn/";
document.write("<script type='text/javascript' src='"+domain+"coder/coder/jquery-m/jquery.min-1.4.5.js'></script>");
document.write("<link  rel='stylesheet' href='"+domain+"coder/bootstrap/css/bootstrap.min.css'/>");
document.write("<link  rel='stylesheet' href='"+domain+"coder/coder/jquery-m/jquery-tips.css'/>");
document.write("<script type='text/javascript' src='"+domain+"coder/coder/jquery-m/jquery-tips.js'></script>");
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
        var obj={
            type:"layer-fadeIn",
            title:"标题",
            content:data.msg,
            area:"auto"
        };
        method.msg_layer(obj);
    }
}
var error = function (jqXHR, textStatus, errorMsg) {
}
