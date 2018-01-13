/**
 * Created by 志华 on 2017/8/2.
 */
var ajaxObject = {};
var token =402167221;
//var contextUrl="https://wchat.5awo.com/xchat";
var contextUrl="http://localhost:8080/xchat";

ajaxObject.postFormAjax = function ajaxPostForm(url, datas, callbak, dataType) {
    url = contextUrl + url;
    $.ajax({
        url: url, data: datas, type: "POST", asyc: true, dataType: dataType,
        success: callbak,
        //beforeSend:beforeSend,
        //headers: {'Passport_ticket':$.cookie('passport_ticket')},
        error: error,
        statusCode: {
            702: function () {
                window.location.href="../x_htmls/platform.html";
            }
        }

    });

}
ajaxObject.getFormAjax = function ajaxGetForm(url, datas, callbak, dataType) {
    url = contextUrl + url;
    $.ajax({
        url: url, data: datas, type: "GET", asyc: true, dataType: dataType,
        success: callbak,
        //beforeSend:beforeSend,
        //headers: {'Passport_ticket':$.cookie('passport_ticket')},
        error: error,
        statusCode: {
            702: function () {
                window.location.href="../x_htmls/platform.html";
            }
        }

    });

}

var callback = function success(data) {
    if(data!=null&&data!='undefine'){
        alert(data);
    }
}
var error = function (jqXHR, textStatus, errorMsg) {
}
