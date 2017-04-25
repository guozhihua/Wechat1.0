<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title>聊天室</title>
    <link rel="stylesheet"   href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet"   href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <style>
        body{
            background-color: #FFF;
            padding:60px;
        }
        #message{
            background-color: pink;
            border:10px;
            solid: #00F;
        }

    </style>

</head>
<body>

<div class="container">
    <h1>欢迎光临聊天室 </h1>

    <div id="message"></div>
    <label>昵称：</label>
    <input id="username" type="text"/>
    <label>内容：</label>
    <input id="text" type="text" style="width:300px"/>
    <button onclick="send()">发送</button>
    <button onclick="closeWebSocket()">退出聊天室</button>

</div>

</body>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        websocket = new WebSocket("ws://192.168.1.71:8080/websocket");
    }
    else{
        alert('您的浏览器不支持 websocket！')
    }

    //连接发生错误的回调方法
    websocket.onerror = function(){
        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function(event){
        alert("链接成功,欢迎加入聊天室！");
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function(){
        var username=document.getElementById('username').value;
        setMessageInnerHTML(getNowFormatDate()+" "+username+" 退出了聊天室！");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        document.getElementById('message').innerHTML += innerHTML + '';
    }

    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }

    //发送消息
    function send(){
        var username=document.getElementById('username').value;
        var message = document.getElementById('text').value;
        websocket.send(getNowFormatDate()+" "+username+"说: "+message);
        document.getElementById('text').value="";
    }

    function getNowFormatDate() {
        var myDate = new Date();
        myDate.getYear();        //获取当前年份(2位)
        myDate.getFullYear();    //获取完整的年份(4位,1970-????)
        myDate.getMonth();       //获取当前月份(0-11,0代表1月)
        myDate.getDate();        //获取当前日(1-31)
        myDate.getDay();         //获取当前星期X(0-6,0代表星期天)
        myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
        myDate.getHours();       //获取当前小时数(0-23)
        myDate.getMinutes();     //获取当前分钟数(0-59)
        myDate.getSeconds();     //获取当前秒数(0-59)
        myDate.getMilliseconds();    //获取当前毫秒数(0-999)
        myDate.toLocaleDateString();     //获取当前日期
        var mytime=myDate.toLocaleTimeString();     //获取当前时间
        return myDate.toLocaleString( );        //获取日期与时间
    }

</script>
</html>