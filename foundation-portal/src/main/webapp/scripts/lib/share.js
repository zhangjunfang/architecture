function loadData(id, url) {
	var _obj = $('#' + id);
	if (_obj) {
		// 增加等待效果
		_obj
				.after("<div class='loadingImg' align='center' style='left: 55%;position: absolute;top: 40%;'><img title='加载信息'src='/styles/images/loading.gif'/></div>");
		// 加载数据
		_obj.load(url, function() {
					// 清除等待效果
					jQuery(".loadingImg").remove();
				});
	}
}

function loadUrl(objId, url) {
	setTimeout(function() {
				var _obj = $(top.document).find('#' + objId);
				_obj.load(url, function() {
							top.Portal.relayout(objId);
						});
			}, 10);
}

// 提示小窗口，2秒后自己消失
function initTime() {
	var strText, strClass;
	if (arguments.length < 1) {
		strText = '操作成功！';
	} else {
		strText = arguments[0];
	}

	if (arguments.length > 1) {
		strClass = 'initTimeDiv ' + arguments[1];
	} else {
		strClass = 'initTimeDiv';
	}

	var str = '<div class="' + strClass + '">' + strText + '</div>';
	$(str).appendTo(document.body);
	setTimeout(function() {
				$('.initTimeDiv').remove();
			}, 2000)

}

/**
 * 确认提示对话框
 * 
 * @param handler
 *            json对象，参数见行注释
 */
function confirmWin(handler) {
	var parentWindow = top.Ext ? window : top.window;
	var settings = $.extend({
		msg1 : "",// 主要提示信息，如”是否确认删除？“
		msg2 : "",// 辅助提示信息;可选
		w : 350,// width;可选
		h : 240,// height;可选
		okFn : function() {
		},// 点击确定按钮回调方法
		cancelFn : function() {
		},// 点击取消或关闭按钮回调方法;可选
		scope : null
			// 作用域;可选
		}, handler);

	var isFN = function(obj) {
		return Object.prototype.toString.call(obj) == "[object Function]"
				? true
				: false;
	}
	var removeThisWin = function() {
		mask_.removeMask();
		$(parentWindow.document).find("#msgTipWin").remove();
		$(parentWindow.document).unbind('keydown');
	}
	var fn = function(f) {
		return function() {
			settings.scope ? f.call(settings.scope) : f();
			removeThisWin();
		};
	};

	parentWindow.CLICK_OK = function() {
	};
	parentWindow.CLICK_CANCEL = removeThisWin;
	var otherMsg = "";
	if (isFN(settings.okFn))
		parentWindow.CLICK_OK = fn(settings.okFn);
	if (isFN(settings.cancelFn))
		parentWindow.CLICK_CANCEL = fn(settings.cancelFn);

	var winStr = '<div style="left:'
			+ ((parentWindow.window.innerWidth - settings.w) / 2)
			+ 'px;top:'
			+ ((parentWindow.window.innerHeight - settings.h) / 2)
			+ 'px;width:'
			+ settings.w
			+ 'px;height:'
			+ settings.h
			+ 'px; display: block; z-index: 1001;" class="x-window popUp popUpFrame" id="msgTipWin">'
			+ '<div class="x-window-tl">'
			+ '<div class="x-window-tr">'
			+ '<div class="x-window-tc"> <span class="popClose" onclick="CLICK_CANCEL();"> </span>'
			+ '<h2 style="cursor:move;">提示</h2>'
			+ '</div>'
			+ '</div>'
			+ '</div>'
			+ '<div class="x-window-ml">'
			+ '<div class="x-window-mr">'
			+ '<div class="x-window-mc">'
			+ '<div class="ws_pop_centerC_content">'
			+ '<div class="ws_confirm"> <b>'
			+ settings.msg1
			+ '</b>'
			+ '<p>'
			+ settings.msg2
			+ '</p>'
			+ '</div>'
			+ '</div>'
			+ '<div class="ws_pop_centerC_bottom">'
			+ '<div class="fr">'
			+ '<div class="btn_blue"><span><a class="msg-tip-ok" onclick="CLICK_OK();" href="javascript:;">确定</a></span></div>'
			+ '<div class="btn_gray"><span><a onclick="CLICK_CANCEL();" href="javascript:;">取消</a></span></div>'
			+ '</div>' + '</div>' + '</div>' + '</div>' + '</div>'
			+ '<div class="x-window-bl">' + '<div class="x-window-br">'
			+ '<div class="x-window-bc"> </div>' + '</div>' + '</div>'
			+ '</div>';

	jQuery(parentWindow.document.body).append(winStr);
	$(parentWindow.document).find(".msg-tip-ok").focus();
	$(parentWindow.document).bind('keydown', function(event) {
				if (event.keyCode == 27) {
					$(this).find(".popClose").click();
				}
			})

	var mask_ = new IframeMask();
	$(mask_.maskObj).click(function() {
				flashObject({
							obj : top.$('#msgTipWin')
						});
			});
}

/**
 * 闪烁对象
 * 
 * @param handler
 *            json对象，参数见行注释
 */
function flashObject(handler) {
	if (!handler || !handler.obj)
		return;// json参数必须传递obj（闪烁对象），否则方法返回
	// 以下参数都是可选
	var settings = $.extend({
				times : 2,// 闪烁次数
				showTime : 100,// 闪烁显示时间
				flashTime : 100,// 闪烁隐藏时间
				showCss : {
					'opacity' : ''
				},// 闪烁显示css
				flashCss : {
					'opacity' : '0.1'
				},// 闪烁隐藏css，默认使用透明度闪烁
				beforeFlash : function() {
				},// 闪烁前回调
				completeFlash : function() {
				},// 闪烁完成回调
				scope : null
			}, handler);

	settings.scope ? settings.beforeFlash.call(settings.scope) : settings
			.beforeFlash();
	var this_times = 0;
	setTimeout(function() {
				this_times++;
				if (this_times > settings.times) {
					settings.scope ? settings.completeFlash
							.call(settings.scope) : settings.completeFlash();
					return;
				}
				handler.obj.css(settings.flashCss);
				setTimeout((function(f) {
							return function() {
								handler.obj.css(settings.showCss);
								setTimeout(f, settings.showTime);
							}
						})(arguments.callee), settings.flashTime);
			}, 150);
}

// xml中特殊字符的转义
function escapeXml(str) {
	if ($.trim(str) == "") {
		return "";
	}
	str = str.replace(/&/g, "&amp;");
	str = str.replace(/</g, "&lt;");
	return str;
}

// html中特殊字符的转义
function escapeHtml(str) {
	if ($.trim(str) == "") {
		return "";
	}

	str = str.replace(/&/g, "&amp;");
	str = str.replace(/</g, "&lt;");
	str = str.replace(/"/g, "&quot;");
	str = str.replace(/'/g, "&apos;");

	return str;
}

// 内容原样输出
function preHtml(str) {
	if ($.trim(str) == "") {
		return "";
	}
	str = str.replace(/ /g, "&nbsp;");
	str = str.replace(/\n/g, "<br/>");
	return str;
}
