/**
 * 设置iframe的高度,让其自适应
 */
function TuneHeight() {
    var frm = document.getElementById("topFrame");
    var subWeb = document.frames ? document.frames
        ["topFrame"].document : frm.contentDocument;
    if (frm != null && subWeb != null) {
        frm.height = subWeb.body.scrollHeight;
    }
}
String.prototype.Trim = function() {

    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 把长度大于len的字符串 截断成 span
 *
 * @param len
 * @returns {String}
 */
String.prototype.formatStrLength = function(len) {
    var result = this;
    if (result.length > len) {
        result = "<span title='" + this + "'>";
        result += this.substring(0, len) + "...";
        result += "</span>";
    }
    return result;
};

function getlength(strTemp) {

    var i, unLen, bLen;
    var str;
    str = strTemp;// 获得当前域的值

    unLen = str.length;
    bLen = str.length;
    for (i = 0; i < unLen; i++) {
        if (str.charCodeAt(i) > 255)
            bLen++;
    }

    return bLen;

}
/**
 * 是不是数字
 */
function isNumber(text) {
    var newPar = /^\d+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}

function isChineseWord(text) {
    var newPar = /^[\x00-\xff]+$/;
    if (newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}
function isPunctuation(text) {
    var newPar = /^[0-9a-zA-Z\u4E00-\u9FA5]+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}
function isEnglishWord(text) {
    var newPar = /^[a-zA-Z]+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}

/**
 * 校验是否为邮箱
 * @param text
 * @returns {Boolean}
 */
function isEmail(text){
    var newPar = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-
z0-9]+)*\.[A-Za-z0-9]+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}

function keyDownAction(method, ascCode) {
    if (ascCode == 13) {
        eval(method);
        return false;
    }
    return true;
}
function showWaitDiv(state) {
    if (state) {
        document.body.scroll = "no";
        var width = document.body.clientWidth;
        var height = document.body.clientHeight;
        var myheigth = document.body.clientHeight;
        var showSetupIframe = document.getElementById
            ("waitIframe");
        var showSetupDiv = document.getElementById("apdiv");
        showSetupIframe.style.display = "block";
        showSetupIframe.style.height = myheigth + "px";
        var left = width / 2 - 200;
        var top = height / 2 - 100;
        showSetupDiv.style.left = left + "px";
        showSetupDiv.style.top = top + "px";
        showSetupDiv.style.display = "block"
        if (document.all) {
            showSetupIframe.style.filter = "Alpha
                (Opacity=75)"; // for IE .
        } else {
            showSetupIframe.style.opacity = 75 / 100; //
            for FF
                }
        } else {
            document.body.scroll = "yes";
            var showSetupIframe = document.getElementById
                ("waitIframe");
            var showSetupDiv = document.getElementById("apdiv");
            showSetupDiv.style.display = "none"
            showSetupIframe.style.display = "none";
        }
    }
//弹出DIV，type:渐现状态，1,0；isClose:是否点击旁边消失，true or false;
    function showCustomDiv(state, myDiv, type,isClose) {
        if (state) {
            var width = $(document).width();
            var height = $(document).height();
            var myheigth = $(window).height();
            $("body").append("<div id='boxDiv' />");
            $("body").append("<iframe id='boxFrame' />");
            var showSetupIframe = $("#boxFrame");

            var box = $("#boxDiv");
            box.attr("style","top:0; left:0;border:none;
            position:absolute; z-index:3; display:none;");
            showSetupIframe.attr("style","top:0;background-
            color:#cccccc;left:0;border:none; position:absolute; z-index:2;
            display:none;");
            box.animate({
                opacity : 0
            }, 0);
            showSetupIframe.animate({
                opacity : 0.68
            }, 0);

            var showSetupDiv = $("#" + myDiv);


//		showSetupIframe.show();
            showSetupIframe.height(height);
            showSetupIframe.width(width);
            showSetupIframe.fadeIn("slow",function(){
            });
            box.height(height);
            box.width(width);
            box.show();

            var left = (width - parseInt(showSetupDiv.width())) /
                2;
            var top = (myheigth - parseInt(showSetupDiv.height()))
                / 2 + $(window).scrollTop();

            var _startTop = top - 50;
            showSetupDiv.stop();
            showSetupDiv.css("top", _startTop );
            showSetupDiv.css("opacity", 0 );
            showSetupDiv.css("left", left);
            showSetupDiv.css("display","block");

            showSetupDiv.animate({
                top:top,
                opacity:1
            },'',function(){
                if(isClose){
                    box.click( function(){
                        showCustomDiv(0, myDiv, 0,0);
                    });
                }
            });
            $("body").css({"overflow-y":"hidden"});
//		showSetupDiv.slideDown("fast");

        } else {
            $("body").css({"overflow-y":"auto"});
            var showSetupIframe = $("#boxFrame");
            var box = $("#boxDiv");

            var showSetupDiv = $("#" + myDiv);
            if (type == 1) {
                showSetupDiv.hide();
                showSetupIframe.hide();
                remove(box,showSetupIframe);
            } else {
                showSetupDiv.animate({
                    top:showSetupDiv.offset().top - 50,
                    opacity:0
                },"normal",function(){
                    showSetupDiv.hide();
                });
//			showSetupDiv.fadeOut("slow");
                showSetupIframe.fadeOut("normal",function(){
                    remove(box,showSetupIframe);
                });
            }
        }

        function remove(box,showSetupIframe){
            box.unbind("click");
            box.remove();
            showSetupIframe.remove();
        }
    }
//偏移量DIV，可以更改坐标
    function showRelativeDiv(state, relativeId, divId,offleft,offtop,type)
    {
        if(offleft==undefined){
            offleft = 0;
        }
        if(offtop==undefined){
            offtop = 0;
        }
        var showSetupDiv = $("#" + divId);
        var width = $(document).width();
        var height = $(document).height();

        if (state) {
            $("body").append("<div id='passwordIframe' />");
            var myIframe = $("#passwordIframe");
            myIframe.attr("style","top:0; left:0;border:none;
            position:absolute; z-index:2; display:none;");
            myIframe.animate({
                opacity : 0
            }, 0);
            myIframe.click( function(){
                showRelativeDiv(0, relativeId,
                    divId,offleft,offtop)
            });
            myIframe.show();
            myIframe.height(height);
            myIframe.width(width);

            var left = $("#" + relativeId).offset().left;
            var top = $("#" + relativeId).offset().top;
            showSetupDiv.css("top", top+offtop);
            showSetupDiv.css("left", left+offleft);
            showSetupDiv.fadeIn("slow");
        } else {
            var myIframe = $("#passwordIframe");
            myIframe.unbind("click");
            myIframe.remove();
            if(type==1){
                showSetupDiv.fadeOut("slow");
            }else{
                showSetupDiv.hide();
            }

        }
    }
    function encodeText(text) {
        if (text != null && text != "") {
            return encodeURIComponent(text);
        } else {
            return null;
        }
    }

    function ckDigital(event) {
        // style="ime-mode:disabled" 禁用输入法
        // onparse="return false;"禁用粘贴
        // onkeyup="value=value.replace(/[^\d/.]/g,'')" 只输入数字
        var keycode = event.keyCode || event.charCode; // 火狐的是
        charCode
        if ((keycode < 48 || keycode > 57)) // 判断是否为数字
            event.preventDefault();
        // event.returnValue=false;
        return false;
    }



//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
//例子：
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02
    08:09:04.423
//(new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
    Date.prototype.Format = function(fmt) { // author: meizz
        var o = {
            "M+" : this.getMonth() + 1, // 月份
            "d+" : this.getDate(), // 日
            "h+" : this.getHours(), // 小时
            "m+" : this.getMinutes(), // 分
            "s+" : this.getSeconds(), // 秒
            "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
            "S" : this.getMilliseconds()
            // 毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for ( var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length
                    == 1) ? (o[k])
                    : (("00" + o[k]).substr(("" +
                    o[k]).length)));
        return fmt;
    }


    var app= {
        //分页
        paging:function(pageView,gopageFunc){
            if(!pageView){
                return "";
            }
            var result = "<div>";
            result += "当前页:第[" + pageView.currentpage +"]页  ";
            result += "| 总记录数:" + pageView.totalrecord +"条  ";
            result += "| 每页显示:" + pageView.maxresult +"条  ";
            result += "| 总记页数:" + pageView.totalpage +"页  ";

            if(pageView.currentpage != 1) {
                result += '<a href="javascript:void(0)"
                onclick="'+gopageFunc + '('+1+')' +'" title="首页" class="nav"><span>首
                页&nbsp;</span></a>';
                result += '<a href="javascript:void(0)"
                onclick="'+gopageFunc + '('+(pageView.currentpage-1)+')' +'" title="上
                一页" class="nav"><span>上一页&nbsp;</span></a>';
            }else  {
                result += '<span>首页&nbsp;</span>';
                result += '<span>上一页&nbsp;</span>';
            }

            if (pageView.currentpage != pageView.totalpage &&
                pageView.totalpage> 0) {
                result += '<a href="javascript:void(0)"
                onclick="'+gopageFunc + '('+(pageView.currentpage+1)+')' +'" title="下
                一页" class="nav"><span>下一页&nbsp;</span></a>';
                result += '<a href="javascript:void(0)"
                onclick="'+gopageFunc + '('+pageView.totalpage+')' +'" title="末页"
            class="nav"><span>末页</span></a>';
            } else {
                result += '<span>下一页&nbsp;</span>';
                result += '<span>末页</span>';
            }

            result  += "</div>";
            return result;
        }
    }

//浮点数加法运算
    function FloatAdd(arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch(e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch(e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        return (arg1 * m + arg2 * m) / m;
    }

//浮点数减法运算
    function FloatSub(arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch(e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch(e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        //动态控制精度长度
        n = (r1 >= r2) ? r1: r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }

//浮点数乘法运算
    function FloatMul(arg1, arg2) {
        var m = 0,
            s1 = arg1.toString(),
            s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length;
        } catch(e) {}
        try {
            m += s2.split(".")[1].length;
        } catch(e) {}
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) /
            Math.pow(10, m);
    }

//浮点数除法运算
    function FloatDiv(arg1, arg2) {
        var t1 = 0,
            t2 = 0,
            r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length;
        } catch(e) {}
        try {
            t2 = arg2.toString().split(".")[1].length;
        } catch(e) {}
        with(Math) {
            r1 = Number(arg1.toString().replace(".", ""));
            r2 = Number(arg2.toString().replace(".", ""));
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }


    /*
     *
     *公共调用js验证包
     *
     */
    function checkNullStr(obj, msg) {
        var el_name = document.getElementById(obj);
        if (Trim(el_name.value).length==0){
            alert(msg);
            el_name.focus();
            return true;
        }
        return	false;
    }
