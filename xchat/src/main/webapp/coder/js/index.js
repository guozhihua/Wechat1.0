function login() {
    var obj=new Object();
    obj.userName= $("#username").val();
    obj.password=$("#password").val();
    var params = {
        "params": JSON.stringify(obj)
    };
    ajaxObject.getFormAjax(login_submit,params , function (data) {
        if(data.code==200){
            window.sessionStorage.setItem("userinfo",JSON.stringify(data.datas))
            window.location.href="./platform_index.html";
        }else{
            alert(data.msg)
        }

    }, "JSON");
}

//全局回车
//$(document).keypress(function(e) {
//    var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
//    if (eCode == 13){
//        alert('您按了回车键')
//        //自己写判断操作
//    }
//});


$(document).ready(function () {
    //回车提交事件
    $("#username").keypress(function(event) {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $("#loginBtn").click();
        }
    });

    $("#password").keypress(function(event) {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $("#loginBtn").click();
        }
    });
});
