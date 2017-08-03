/**
 * Created by 志华 on 2017/8/2.
 */

var ajaxObject = new Object();

var contextUrl = "http://localhost:8086/xchat";
//var contextUrl="http://101.200.55.143/xchat";
//headers: {
//    'Access-Token':$.cookie('access_token')
//},

ajaxObject.postFormAjax = function ajaxPostForm(url, datas, callbak, dataType) {
    url = contextUrl + url;
    $.ajax({
        url: url, data: datas, type: "POST", asyc: true, dataType: dataType,
        success: callbak,
        //beforeSend:beforeSend,
        headers: {'Passport_ticket':"123456"},
        error: error,
        statusCode: {
            702: function () {
                alert("Token 失效");
            }
        }

    });

}
var callback = function success(data) {
    alert(data.msg);
}
var error = function (jqXHR, textStatus, errorMsg) {
}
