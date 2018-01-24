function login() {
    var obj=new Object();
    obj.username= $("#username").val();
    obj.pwd=$("#password").val();
    var params = {
        "params": JSON.stringify(obj)
    };
    ajaxObject.getFormAjax(login_submit,params , function (data) {
    }, "JSON");
}