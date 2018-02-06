/**
 * Created by Administrator on 2018/1/25.
 */
function showTime() {
    var show_day = new Array('周日', '周一', '周二', '周三', '周四', '周五', '周六');
    var time = new Date();
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var date = time.getDate();
    var day = time.getDay();
    var hour = time.getHours();
    var minutes = time.getMinutes();
    var second = time.getSeconds();
    /*  month<10?month='0'+month:month;  */
    hour < 10 ? hour = '0' + hour : hour;
    minutes < 10 ? minutes = '0' + minutes : minutes;
    second < 10 ? second = '0' + second : second;
    var now_time = '<br/>'+ month + '月' + date + '日  ' +  hour+': '+minutes+":"+second+"  " + show_day[day] + '';
    document.getElementById('timer').innerHTML = now_time;
}
function  setSelectedPages() {
    $(".page-title").each(function () {
        $(this).hover(function () {
            if (!$(this).hasClass("page-title-selected")) {
                $(this).css({"background-color": "#b6e0da"});
            }
        }, function () {
            if (!$(this).hasClass("page-title-selected")) {
                $(this).css({"background-color": ""});
            }
        });
        ;
        $(this).click(function () {
            var selected = $(".page-title-selected");
            if (selected != undefined || selected != null) {
                selected.removeClass("page-title-selected")
            }
            $(this).css({"background-color": ""});
            $(this).addClass("page-title-selected")
            getGandPageListInfo($(this).attr('data-id'));
        });
    });
}
function getUserBaseInfo() {
    ajaxObject.postFormAjax(getUserInfo, {}, function (data) {
        if (data.code == 200) {
            var adminInfo = data.datas.realName + "   ";
            $("#AdminUser").text(adminInfo);

            $("#passport_token").val($.cookie('passport-ticket'))
        } else {
            window.location.href = contextUrl + "/index.html";
        }
    }, "JSON");
}
function getHeaderPages() {
    var pages1 = new Object();
    pages1.parentId = 0;
    ajaxObject.postFormAjax(childPage, {'params': JSON.stringify(pages1)}, function (data) {
        if (data.code == 200) {
            if (data.datas != null) {
                var html = $("#header_tr").html();
                for (var i = 0; i < data.datas.length; i++) {
                    html += "<td class='td-border-header'>" +
                        " <div class='page-title' data-id='" + data.datas[i].pageId + "'>"
                        + data.datas[i].pageName + "</div></td>";
                }
                $("#header_tr").html(html);
                setSelectedPages();
                defaultSelcctHead();
            }
        } else {
            alert(data.msg);
        }
    }, "JSON");
}
function getGandPageListInfo(id) {
    var pages1 = new Object();
    pages1.parentId = id;
    ajaxObject.postFormAjax(grandPage, {'params': JSON.stringify(pages1)}, function (data) {
        if (data.code == 200) {
            if (data.datas != null) {
                var json =data.datas;
                var html ="";
                $("#leftpages").empty();
                $("#content").empty();
                $("#title").empty();
                html+="  <div class='accordion-group'>";
                for(var k in json){
                    html += "<div  data-id='" + k + "' class='accordion-heading left-head-p'><div  " +
                        "class='accordion-toggle' data-toggle='collapse' data-parent='#accorde2' href='#"+k.substr(k.indexOf("@")+1)+"'><a href='javascript:void(0)'>" +
                          k.substr(0, k.indexOf("@"))+"</a></div></div>";
                    if(json[k].length>0){
                        html+="<div  id='"+ k.substr(k.indexOf("@")+1)+"'  class='accordion-body collapse'>";
                        for(var i = 0;i<json[k].length;i++){
                            var c1 =json[k][i];
                            html+="<div data-id='"+c1['childId']+"'  data-name='"+c1['childName']+"' data-url='"+c1['childUrl']+"' " +
                                "p-name='"+c1['pageName']+"'  class='accordion-inner left-head-c' href='#'><a href='#'>"
                                +c1['childName']+"</a></div>";
                        }
                        html+= "</div>";
                    }
                }
                html+="</div>";
                $("#leftpages").html(html);
                $("#content").attr("src","");
                $("#title").empty();
                leftAddClick();
            }
        } else {
            alert(data.msg);
        }
    }, "JSON");
}

function leftAddClick(){
$(".left-head-c").each(function(){
        $(this).click(function(){
            //var dataId=$(this).attr('data-id');
            var dataName=$(this).attr('data-name');
            var dataURL=$(this).attr('data-url');
            if(dataURL==null||dataURL==undefined){
                $("#content").attr("src",notfound);
                return;
            }
            var pName=$(this).attr('p-name');
            $("#content").empty();
            $("#title").empty();
            var html='<span style="font-weight: 400;font-size: 15px">'+pName+"-"+dataName+'</span> <input type="button" class="btn btn-primary btn-left" onclick="hideAll($(this))" value="隐藏头部" />';
            $("#title").append(html);
            $("#content").attr("src",dataURL);


        });
    });
}
function defaultSelcctHead(){
    $("#header_tr").find("td").eq(1).find(".page-title").click();

}
function hideAll(obj){
    if($("#header").css("display")=="none"){
        $("#header").show();
        obj.val("隐藏头部");

    }else{
        $("#header").hide();
        obj.val("显示头部");
    }
}

function quit(){
    ajaxObject.postFormAjax(login_out,{},function(data){
        if (data.code == 200) {
            window.location.href = contextUrl + "/index.html";
        }else{

        }
    },"JSON");
}