/**
 * Created by 志华 on 2017/8/2.
 */
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
        alert(data.msg);
    }
}
var error = function (jqXHR, textStatus, errorMsg) {
}
