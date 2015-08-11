var timerToastID;
// 模拟android的toast，msg表示要提示的文字，dlay为toast消失的时间
function toastAlert(msg, dlay, ismove) {
	var toast = document.createElement('div');

	// 消失时间默认为2000毫秒
	if (dlay == undefined || dlay == "") {
		dlay = 2000;
	}
	if (ismove == undefined || ismove == "") {
		ismove = true;
	}

	$(toast).css("display", "none");
	$(toast).css("position", "absolute");
	$(toast).css("z-index", "9999");
	$(toast).css("top", "0");
	$(toast).css("left", "0");
	$(toast).css("width", "200px");
	// $(toast).css("height","40px");
	$(toast).css("word-break", "break-all");
	$(toast).css("word-wrap", "break-word");
	$(toast).css("background-color", "#ffa703");
	$(toast).css("text-align", "center");
	$(toast).css("line-height", "40px");
	$(toast).css("border-radius", "8px");

	$(toast).html(msg);

	var _scrollHeight = $(document).scrollTop();// 获取当前窗口距离页面顶部高度
	var _windowHeight = $(window).height();// 获取当前窗口高度
	var _windowWidth = $(window).width();// 获取当前窗口宽度
	var _popupHeight = $(toast).height();// 获取弹出层高度
	var _popupWeight = $(toast).width();// 获取弹出层宽度
	// alert(_popupHeight);

	var _posiTop = (_windowHeight - _popupHeight) / 2 + _scrollHeight;
	var _posiLeft = (_windowWidth - _popupWeight) / 2;

	$(toast).css("top", _posiTop + "px");
	$(toast).css("left", _posiLeft + "px");

	$(document.body).append(toast);
	$(toast).css("display", "block");

	if (ismove) {
		timerToastID = setInterval(_movefloat($(toast)), 10);
	}

	$(toast).fadeOut(dlay, function() {
				if (ismove) {
					clearTimeout(timerToastID);
				}

			});

}

function movefloat(toastJquery) {
	var oldTop = toastJquery.offset().top;
	toastJquery.css("top", (oldTop - 1) + "px");
	// alert(oldTop+30+"px");
	// alert(toastJquery.css("top"));
	// toastJquery.css("top",);

}

function _movefloat(toastJquery) {
	return function() {
		movefloat(toastJquery);
	}
}