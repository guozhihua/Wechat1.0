/**
 * Created by Administrator on 2018/1/25.
 */

function showTime() {
    var show_day = new Array('星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六');
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
    var now_time = '' + year + '年' + month + '月' + date + '日' + ' ' + show_day[day] + '';
    document.getElementById('timer').innerHTML = now_time;
}
function setSelectedPages() {
    $(".page-title").each(function () {
        $(this).hover(function () {
            if (!$(this).hasClass("page-title-selected")) {
                $(this).css({"background-color": "#16c14c"});
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
            }
        } else {
            alert(data.msg);
        }
    }, "JSON");
}
function getGandPageListInfo(id) {
    alert(id);
}
