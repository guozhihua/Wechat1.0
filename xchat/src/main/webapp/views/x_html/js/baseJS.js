/**
 * Created by 志华 on 2017/8/2.
 */
var ajaxObject = {};
//var contextUrl = "http://dev.edu.cn:8086/xchat";
var token =402167221;
var contextUrl="http://101.200.55.143/xchat";

ajaxObject.postFormAjax = function ajaxPostForm(url, datas, callbak, dataType) {
    url = contextUrl + url;
    alert($.cookie('passport_ticket'));
    $.ajax({
        url: url, data: datas, type: "POST", asyc: true, dataType: dataType,
        success: callbak,
        //beforeSend:beforeSend,
        headers: {'Passport_ticket':$.cookie('passport_ticket')},
        error: error,
        statusCode: {
            702: function () {
                alert("Token 失效");
                window.location.href="../x_htmls/platform.html";
            }
        }

    });

}
var callback = function success(data) {
    console.log(data);
}
var error = function (jqXHR, textStatus, errorMsg) {
}
