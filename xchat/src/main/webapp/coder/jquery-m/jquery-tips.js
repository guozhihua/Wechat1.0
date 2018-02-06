/**
 *  type(表示切换显示效果):slideFromTop、slideFromBottom、showSweetAlert、none、layerFadeIn、layer-fadeInUpBig、layer-rollIn、layer-shake、layer-spread、layer-fadeIn

 title(弹框标题):如不需要标题，则不需要传此参数

 close(弹框关闭按钮):close属性为false为没有关闭按钮，close属性为true或无close属性则有关闭按钮

 content/url:(弹框主要内容)：如果内容简洁，可将内容放到content中。如内容比较复杂，可另建一个html文件(如a.html)，

 a.html中只需要放内容标签（你需要放html和body标签等），如我是弹框内容。配置参数中只需要传url:"a.html"

 注：content和url必须选择其一，并且只能选择其中一项

 btn表示按钮组，值为""的时候，则表示不显示该按钮。否则显示该按钮，如["","保存"]

 callBack1表示点击完第一个按钮执行的回调函数

 callBack2表示点击完第二个按钮执行的回调函数

 area表示弹框的宽度和高度。如不传值则宽高自适应
 1、type值用来切换显示效果

 type值：slideFromTop、slideFromBottom、showSweetAlert、none、layerFadeIn、layer-fadeInUpBig、layer-rollIn、layer-shake、layer-spread、layer-fadeIn

 如：type:"slideFromBottom"

 2、title用来设置标题，如不添加title属性，则弹框无标题。如title:"弹框标题"

 3、close为关闭按钮，close值为false则没有关闭按钮，close值为true或者不添加close属性，则显示关闭按钮。如： close:"false"

 4、content为弹框主要内容

 5、area为弹框尺寸，如无area属性，则弹框尺寸自适应。如有area，则第一个值为宽，第二个值为高。如area:["500px","200px"]

 6、btn为取消按钮和提交按钮组合，值为0则表示不显示。如： btn:[0,"保存"]
 * @type {{msg_layer: method.msg_layer, msg_close: method.msg_close, msg_fade: method.msg_fade}}
 */


var method = {
    msg_layer: function (obj) {
        //弹框
        $(".msg-layer-bg,.msg-layer").remove();
        var parent= $("#main",window.parent.document);
        if(!parent){
            parent=$("body");
        }
        parent.append('<div class="msg-layer-bg"></div><div class="msg-layer showAlert">' +
            '<h5 class="titleH5"></h5>' +
            '<div class="msg-con"></div><div class="layer-close"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></div>' +
            '<div class="layer-btn">' +
            '<input type="button" class="layer-cancel"/>' +
            '<input type="button" class="layer-commit"/></div></div>');
        var _layerBg = $(".msg-layer-bg"), _layer = $(".msg-layer"), _close = $(".layer-close"), _cansel = $(".layer-cancel"), _commit = $(".layer-commit");
        _layer.attr("data-animation", obj.type);
         var  titleH5=$(".titleH5");
        var winH = $(window).height(), winW = $(window).width();
        if (obj.title) {
            var titleHtml = '';
            if(obj.option) {
                //1 代表一般弹框  2-代表错误弹框 3-正确
                if (obj.option == 1) {
                    titleHtml = '<span class="title-common ">' + obj.title + '</span>'
                    if(titleH5){
                        titleH5.css({"background-color": "#f3f3f3"});
                    }

                }
                if (obj.option == 2) {
                    titleHtml = '<span class="title-error">' + obj.title + '</span>'
                    titleH5.css({"background-color": "#d29393"});
                }
                if (obj.option == 3) {
                    titleHtml = '<span class="title-success">' + obj.title + '</span>'
                    titleH5.css({"background-color": "#4bd263"});
                }
            } else{
                titleHtml=obj.title;
            }
            _layer.find("h5").html(titleHtml);
        } else {
            _layer.find("h5").css("display", "none")
        }
        if (obj.content) {
            _layer.find($(".msg-con")).html(obj.content);
        } else if (obj.url) {
            $.get(obj.url, function (data) {
                _layer.find($(".msg-con")).html(data);
            });
        }
        _layer.css({"display": "block"});
        _layerBg.css({"display": "block"});
        if (!obj.close || obj.close == "true") {
            //关闭按钮
            _close.css("display", "block");
            _close.on("click", function () {
                method.msg_close();
            })
        } else {
            _close.css("display", "none");
        }
        if (obj.btn) {
            //按钮
            if (obj.btn[0] != "") {
                _cansel.css("display", "inline-block");
                _cansel.val(obj.btn[0]);
                _cansel.on("click", function () {
                    if (obj.callBack1) {
                        obj.callBack1();
                        method.msg_close();
                    } else {
                        method.msg_close();
                    }
                })

            }
            if (obj.btn[1] != "") {
                _commit.css("display", "inline-block");
                _commit.val(obj.btn[1]);
                _commit.on("click", function () {
                    if (obj.callBack2) {
                        obj.callBack2();
                    }
                })
            }
        }
        if (obj.area == "auto") {
            obj.area = [200, 150];
        }
        if (obj.area) {
            //宽高
            if (obj.area[0] != "auto" && obj.area[1] != "auto") {
                _layer.css({
                    "width": obj.area[0],
                    "height": obj.area[1],
                    "left": winW / 2 - parseFloat(obj.area[0]) / 2,
                    "top": winH / 3 - parseFloat(obj.area[1]) / 2
                });
            } else if (obj.area[0] != "auto" && obj.area[1] === "auto") {
                _layer.css({
                    "width": obj.area[0],
                    "height": obj.area[1],
                    "left": winW / 3 - parseFloat(obj.area[0]) / 3,
                    "top": winH / 3 - (_layer.height() + 20) / 2
                });
            } else if (obj.area[0] === "auto" && obj.area[1] != "auto") {
                _layer.css({
                    "width": _layer.width() + 20,
                    "height": obj.area[1],
                    "left": winW / 2 - (_layer.width() + 20) / 2,
                    "top": winH /3  - parseFloat(obj.area[1]) / 4
                });
            }
        } else {
            //_layer.css({"width":_layer.width()+20,"height":_layer.height()+40,"left":winW/2-(_layer.width()+20)/2,"top":winH/2-(_layer.height()+30)/2});
            _layer.css({
                width: _layer.width() + 20 + "px",
                "margin": "-" + ((_layer.height() + 10) / 2) + "px 0 0 -" + ((_layer.width() + 20) / 2) + "px"
            });
        }
        if(obj.closeTime||obj.closeTime>1000){
            window.setTimeout(function(){
                method.msg_close();
            },obj.closeTime);
        }
    },
    msg_close: function () {
        //关闭弹框
        var timer = null;
        $(".msg-layer").removeClass('showAlert').addClass("hideAlert");
        timer = setTimeout(function () {
            clearTimeout(timer);
            $(".msg-layer-bg").remove();
            $(".msg-layer").remove();
        }, 200);
    },
    msg_fade: function (obj) {
        if ($(".msy-alert").length > 0) {
            $(".msy-alert").remove();
        }
        $("body").append('<div class="msy-alert showAlert" data-animation="layerFadeIn">' + obj.content + '</div>');
        var msg_timer = null, $msg = $(".msy-alert");
        //$msg.attr("data-animation",obj.type);
        var winH = $(window).height(), winW = $(window).width();
        if (obj.area) {
            if (obj.area[0] != "auto" && obj.area[1] != "auto") {
                $msg.css({
                    "width": obj.area[0],
                    "height": obj.area[1],
                    "left": winW / 2 - parseFloat(obj.area[0]) / 2,
                    "top": winH / 2 - parseFloat(obj.area[1]) / 2
                });
            } else if (obj.area[0] != "auto" && obj.area[1] === "auto") {
                $msg.css({
                    "width": obj.area[0],
                    "height": obj.area[1],
                    "left": winW / 2 - parseFloat(obj.area[0]) / 2,
                    "top": winH / 2 - ($msg.height()) / 2
                });
            } else if (obj.area[0] === "auto" && obj.area[1] != "auto") {
                $msg.css({
                    "width": $msg.width() + 20,
                    "height": obj.area[1],
                    "left": winW / 2 - ($msg.width()) / 2,
                    "top": winH / 2 - parseFloat(obj.area[1]) / 2
                });
            }

        } else {
            $msg.css({
                width: $msg.width() + 20 + "px",
                "margin": "-" + (($msg.height() + 10) / 2) + "px 0 0 -" + (($msg.width() + 20) / 2) + "px"
            });
        }
        if (obj.line) {
            $msg.css("line-height", obj.line)
        }
        msg_timer = window.setTimeout(function () {
            msg_timer = null;
            clearTimeout(msg_timer);
            $(".msy-alert").removeClass('showAlert').addClass("hideAlert").remove();
        }, obj.time)
    }


};
