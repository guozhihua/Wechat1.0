function login() {
    var obj=new Object();
    obj.username= $("#username").val();
    obj.pwd=$("#password").val();
    var params = {
        "params": JSON.stringify(obj)
    };
    ajaxObject.getFormAjax(login_submit,params , function (data) {
        if(data.code==200&&data.datas==88888888){
            window.location.href="./platform_index.html";
        }else{
            alert(data.msg)
        }

    }, "JSON");
}