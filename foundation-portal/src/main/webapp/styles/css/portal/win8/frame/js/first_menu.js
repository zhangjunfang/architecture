var d = document;
var l = window.location.href;
var f = l.substring(l.lastIndexOf('/') + 1);
var timer = false;

var flash_params = {
	wmode : 'opaque',
	menu : false,
	allowFullscreen : "true",
	scale : 'Scale'
};
var flashvars = {};

var lang = window.location.href.match(/\.com\/([a-z]{2})\//);
if (lang && lang.length && lang.length > 1 && lang[1] != '')
	lang = lang[1];
else
	lang = 'en';

$(document).ready(function() {
			autoMaxWidth.ini();
		});

$(window).load(function() {

	autoMaxWidth.ini();
	// var ifRemote = $("#iframeSon").get(0);
	// alert($(ifRemote.contentWindow.document).find("#worldWide"))
	// lightbox.worldwide =
	// $(ifRemote.contentWindow.document).find("#worldWide")[0];

	lightbox.ini();
	menuPopup.ini();
	menuHighlight.exec();

		/* Edit by Kevin start */

		// alert(g_HttpRelativeWebRoot);
		/* Edit by Kevin end */

	});

var menuHighlight = {};
menuHighlight.exec = function() {
	if (l.indexOf('solutions') > -1) {
		$('#menu .solutions a').addClass('active');
		return $('#menu').data("normal", "solutions");
	}
};

// ----------------------------------------------
// add by bob 2011-04-07 (start)
var mouse_events = {};

$(document).ready(function() {
			// $('.text.png').children().attr('class','IE6png');
			// $('.text.png').children().children().attr('class','IE6png');
			// mouse_events;
			if (!iPx()) {
				mouse_events.mouse_over();
				mouse_events.mouse_out();
			}
		});

var timeout;
mouse_events.mouse_over = function() {
	$("#menu li").mouseover(function() {
				// onmouseover&#26102;&#38388;
				dateIn = new Date();
				timeIn = dateIn.getTime();
				timeOut = 0;
				flag = true;

				var p = this.className;
				timeout = setTimeout(function() {
							mouse_events.aShow(p)
						}, 200);
			});
};

mouse_events.mouse_out = function() {
	$("#menu li").mouseout(function() {
				// onmouseout&#26102;&#38388;
				dateOut = new Date();
				timeOut = dateOut.getTime();
				timeIn = 0;
				flag = false;

				if (divShow_flag != true) {
					$("#menu li a").removeClass('hover');
				}
				// alert("sdf");
				clearTimeout(timeout);
				clearTimeout(time_temp);
			});
};

mouse_events.aShow = function(classname) {

	$('#menu .' + classname + ' a').addClass('hover');
};

var dateOut;
var timeOut;
var dateIn
var timeIn;
var flag = false;
var divShow_flag = false;
var $target;
var target;
var css;
var time_temp;

function showDiv() {

	if (flag = true && timeIn > 0) {

		$target = menuPopup.obj.css(css).find('.' + target).show();
	}

	flag = false;
}

// add by bob 2011-04-07 (end)
// -----------------------------------------------

var menuPopup = {};
menuPopup.timer = false;
menuPopup.ini = function() {
	menuPopup.obj = $('#menu-popup');

	$('#menu li a').hover(function() {

				menuPopup.stop();
				$('#menu li a').removeClass('active');
				$('#menu li a').removeClass('hover');
				menuPopup.obj.find('.popup').hide();

				// alter by bob 2011-04-07
				// var target = this.parentNode.className;
				target = this.parentNode.className;

				var position = $(this).offset();
				var shadowOffset = 2;
				if ($.browser.msie && parseInt($.browser.version) == 6)
					shadowOffset = 0;

				// alter by bob 2011-04-07
				// var css = {
				css = {
					top : position.top + 30,
					left : $('#menu li a').eq(0).offset().left - shadowOffset
				};

				// ---------------------------------------------------
				// flag&#21028;&#26029;&#26159;&#21542;&#40736;&#26631;&#22312;MENU&#19978;&#20572;&#30041;
				// (add by bob 2011-04-07) (start)
				if (iPx()) {

					var $target = menuPopup.obj.css(css).find('.' + target)
							.show();

					if ($target.length > 0)
						$(this).addClass('hover');

				} else {
					if (flag = true) {
						time_temp = setTimeout(showDiv, 200);
					}
				}

				/*
				 * var $target = menuPopup.obj.css(css).find('.' +
				 * target).show();
				 * 
				 * 
				 * if ($target.length > 0) $(this).addClass('hover');
				 */
				// (end)
				// ---------------------------------------------------
				if ($(this).parent().attr('class') != $('#menu').data("normal")) {
					menuHighlight.exec();
				}

			}, menuPopup.hide);

	menuPopup.obj.hover(menuPopup.stop, menuPopup.hide);

};
menuPopup.stop = function() {

	// add by bob 2011-04-07
	divShow_flag = true;

	if (menuPopup.timer)
		clearTimeout(menuPopup.timer);
}
menuPopup.hide = function() {
	menuPopup.stop();
	menuPopup.timer = setTimeout(function() {
				menuPopup.obj.css('top', '-1000px');
				$('#menu a').removeClass('hover');
				menuHighlight.exec();
			}, 10);
}

var autoMaxWidth = {};
autoMaxWidth.ini = function() {

	var winW = $(window).width();
	if (winW < 980)
		winW = 980;

	$('.autoMaxWidth').each(function() {
				$(this).width(winW);

				var $img = $('img', this).first();
				var imgW = $img.width();
				var left = (winW - imgW) / 2;
				$img.css({
							"left" : left + "px",
							"position" : "relative"
						});

				if ($.browser.msie && parseInt($.browser.version) == 6) {
					$(this).css({
								'overflow' : 'hidden',
								'position' : 'relative'
							}).width(winW);
					$('#banner').css({
								'overflow' : 'hidden',
								'position' : 'relative'
							}).width(winW);
				}

			});
}

jQuery.fn.center = function() {
	// alert(this.className);
	var top = ($(window).height() - this.height()) / 2 + $(window).scrollTop();
	var left = ($(window).width() - this.width()) / 2 + $(window).scrollLeft();
	// alert("$(window).height()=="+$(window).height()+"/"+"this.height()="+this.height()+"$(window).scrollTop()="+
	// $(window).scrollTop());

	// alert("window.width:"+$(window).width()+"------------this.width:"+this.width()+"--------------window.scrollLeft:"+$(window).scrollLeft());
	this.css("position", "absolute");
	this.css("top", top + "px");
	this.css("left", left + "px");
	return this;
};

jQuery.fn.calCenter = function(minW) {
	var winW = $(window).width();
	if (minW && winW < minW)
		winW = minW;
	var left = (winW - this.width()) / 2 + $(window).scrollLeft();
	return {
		'left' : left
	};
};

jQuery.fn.cleanOuterHTML = function() {
	return $("<p/>").append(this.eq(0).clone()).html();
};

jQuery.fn.loadthumb = function(options) {
	options = $.extend({
				src : ""
			}, options);
	var _self = this;
	_self.hide();
	var img = new Image();
	$(img).load(function() {
				_self.attr("src", options.src);
				_self.fadeIn("slow");
			}).attr("src", options.src); // .atte("src",options.src)&#35201;&#25918;&#22312;load&#21518;&#38754;&#65292;
	return _self;
}

function replaceSwfWithEmptyDiv(targetID) {
	var el = document.getElementById(targetID);
	if (el) {
		var div = document.createElement("div");
		el.parentNode.insertBefore(div, el);
		swfobject.removeSWF(targetID);
		div.setAttribute("id", targetID);
	};
}

var lightbox = {};
lightbox.status = 0;
lightbox.contentId = false;
lightbox.selector = 'a[rel=lightbox]';
lightbox.width = 706;
lightbox.height = 560;
lightbox.ini = function() {

	$(lightbox.selector).click(function(e) {

		e.preventDefault();

		lightbox.status = 1;
		lightbox.overlay();
		var hrefObj = this.href.substring(this.href.indexOf('#') + 1);
		var srcArray = hrefObj.split('&');
		var srcPath = srcArray[0];
		lightbox.width = srcArray[1];
		lightbox.height = srcArray[2];

		if (lightbox.width == null && lightbox.width == null) {

			var video_width = 640;
			var video_height = 400;
		} else {
			var video_width = lightbox.width;
			var video_height = lightbox.height;

		}

		// launch flv player
		if (this.href.indexOf('.mp4') > -1 || this.href.indexOf('.flv') > -1) {
			// alert(lightbox.width):
			$('<div id="lightbox" />')
					.appendTo('body')
					.css({
								'width' : lightbox.width,
								'height' : lightbox.height,
								'z-index' : 99
							})
					.append('<div class="videoHead"><div class="Close"></div></div>')
					.center();

			$('#lightbox .Close').click(lightbox.close);

			$('#lightbox')
					.append('<div class="video"><div id="lightbox_flash"/></div>');

			if (iPx()) {

				// alert("<video width='"+video_width+"'
				// height='"+video_height+"' controls='controls'><source
				// type='video/mp4' src='"+srcPath+"'></source></video>");

				$('#lightbox_flash').append(["<video width='", video_width,
						"' height='", video_height,
						"' controls='controls'><source type='video/mp4' src='",
						srcPath, "'></source></video>"].join(""));

			} else {
				$('#lightbox .heading div').removeClass("title");

				$('#lightbox').css({
							'width' : parseInt(video_width) + 10,
							'height' : parseInt(video_height) + 50
						}).find('.heading').css('width', video_width);

				var flashvars = {
					srcPath : srcPath,
					downloadPath : "",
					imgPath : "",
					videoW : video_width,
					videoH : video_height,
					autoLoop : "false",
					controlBarAutoHide : "false",
					autoPlay : "true"
				};
				var flash_params = {
					menu : "false",
					scale : "Scale",
					allowFullscreen : "true",
					allowScriptAccess : "always",
					bgcolor : "#FFFFFF"
				};
				/* Edit by Kevin */
				// alert(video_width);
				var skinSrc = g_HttpRelativeWebRoot
						+ 'groups/public/documents/webasset/glow.zip'
				var swfSrc = g_HttpRelativeWebRoot
						+ 'groups/public/documents/webasset/player.swf'

				jwplayer("lightbox_flash").setup({
							width : video_width,
							height : video_height,
							skin : skinSrc,
							stretching : "exactfit",
							flashplayer : swfSrc,
							file : srcPath,
							autoStart : "true",
							plugins : {
								gapro : {
									accountid : "UA-7728030-4"
								}
							}
						});
				// swfobject.embedSWF(g_HttpRelativeWebRoot +
				// 'groups/public/documents/webasset/hw_000357.swf',
				// 'lightbox_flash', video_width, video_height, '9.0.0',
				// "expressInstall.swf", flashvars, flash_params);
				;

			};

			$(window).resize(lightbox.relocate);

		}
		// add by kevin 03-22-11(start)
		else if (this.href.indexOf('.swf') > -1) {
			$('<div id="lightbox" />')
					.appendTo('body')
					.css({
								'width' : lightbox.width,
								'height' : lightbox.height,
								'z-index' : 99
							})
					.append('<div class="videoHead"><div class="Close"></div></div>')
					.center();

			$('#lightbox .Close').click(lightbox.close);

			$('#lightbox')
					.append('<div class="video"><div id="lightbox_flash"/></div>');

			if (iPx()) {

				window.location = srcPath;
				lightbox.close();

			} else {
				$('#lightbox .heading div').removeClass("title");
				$('#lightbox').css({
							'width' : parseInt(video_width) + 10,
							'height' : parseInt(video_height) + 50
						}).find('.heading').css('width', video_width);

				var flashvars = {};
				var flash_params = {
					menu : "false",
					scale : "Scale",
					allowFullscreen : "true",
					allowScriptAccess : "always",
					bgcolor : "#FFFFFF"
				};

				swfobject.embedSWF(srcPath, 'lightbox_flash', video_width,
						video_height, '9.0.0', "expressInstall.swf", flashvars,
						flash_params);

			}

		}
		// add by kevin 03-22-11(end)
		else {
			$('<div id="lightbox" />')
					.appendTo('body')
					.css({
								'width' : lightbox.width,
								'height' : lightbox.height,
								'z-index' : 99
							})
					.append('<div class="heading"><div class="rightBg"><div class="title"></div><div class="close"><div class="CloseIcon"></div></div></div></div>')
					.center();

			$('#lightbox .close').click(lightbox.close);

			if (this.title)
				$('#lightbox .title').html(this.title);

			$('#lightbox')
					.append('<div class="container" id="lightbox_content" /><div class="bottom"/>');

			if (this.href.indexOf('#') > -1) {

				var id = this.href.substring(this.href.indexOf('#') + 1);
				lightbox.contentId = id;

				var $content = $('#' + id);
				if ($content.height() > 410) {

					$('#lightbox').width($content.width() + 4).height(460)
							.center();
					$content.children(".content").height(388);

				} else {

					$('#lightbox').width($content.width() + 4).height($content
							.height()
							+ 50).center();

				}
				$content.appendTo('#lightbox_content').show();
				$(window).unbind('scroll').resize(lightbox.relocate);

			};

		}

	});

};

lightbox.open = function(id, title) {

	lightbox.status = 1;
	lightbox.overlay();

	$('<div id="lightbox" />')
			.appendTo('body')
			.css({
						'width' : lightbox.width,
						'height' : lightbox.height,
						'z-index' : 99
					})
			.append('<div class="heading"><div class="title"></div><div class="close">Close X</div></div>')
			.center();

	$('#lightbox .close').click(lightbox.close);

	$('#lightbox .title').html(title);

	$lightbox = $('#lightbox').append('<div id="lightbox_content" />');

	lightbox.contentId = id;

	var $content = $('#' + id);

	$lightbox.width($content.width()).height($content.height() + 50).center();

	if (parseInt($lightbox.css('top')) < 0)
		$lightbox.css('top', '0px');

	$content.appendTo('#lightbox_content').show();

	if (id == 'inquiry_popup') {
		$(window).scroll(lightbox.relocateShift).resize(lightbox.relocateShift);
		lightbox.relocateShift();
	} else {
		$(window).unbind('scroll').resize(lightbox.relocate);
	};

}

lightbox.relocate = function() {
	if (lightbox.status == 1) {
		lightbox.overlay();
		var $lightbox = $('#lightbox').center();
		if (parseInt($lightbox.css('top')) < 0)
			$lightbox.css('top', '0px');
	};
}

lightbox.relocateShift = function() {
	if (lightbox.status == 1) {
		lightbox.overlay();
		if (iPx())
			var top = $(window).scrollTop() + 30;
		else
			var top = $(window).scrollTop() + 100;
		var $lightbox = $('#lightbox').center().css('top', top);
		if (parseInt($lightbox.css('top')) < 0)
			$lightbox.css('top', '0px');
	};
}

lightbox.overlay = function() {

	var w_width = $(document).width();
	var w_height = $(document).height();

	if ($.browser.msie && parseInt($.browser.version) == 6)
		w_width = w_width - scrollbarWidth() - 8;

	$('#overlay').remove();
	$('<div id="overlay" />').appendTo('body').css({
				'width' : w_width,
				'height' : w_height
			}).click(lightbox.close);

	// IE6 select box fix
	if ($.browser.msie && parseInt($.browser.version) <= 6) {
		$('select').css('visibility', 'hidden');
		$('.popup select').css('visibility', 'visible');
	};

}

lightbox.close = function() {

	if (lightbox.contentId != false) {
		$('#' + lightbox.contentId).hide().appendTo('body');
		lightbox.contentId = false;
	};

	$('#overlay, #lightbox').remove();

	// IE6 select box fix
	if ($.browser.msie && parseInt($.browser.version) <= 6)
		$('select').css('visibility', 'visible');

	lightbox.status = 0;

}

function replaceSwfWithEmptyDiv(targetID) {
	var el = document.getElementById(targetID);
	if (el) {
		var div = document.createElement("div");
		el.parentNode.insertBefore(div, el);
		swfobject.removeSWF(targetID);
		div.setAttribute("id", targetID);
	};
}

function scrollbarWidth() {
	var div = $('<div style="width:50px;height:50px;overflow:hidden;position:absolute;top:-200px;left:-200px;"><div style="height:100px;"/></div>');
	// Append our div, do our calculation and then remove it
	$('body').append(div);
	var w1 = $('div', div).innerWidth();
	div.css('overflow-y', 'scroll');
	var w2 = $('div', div).innerWidth();
	$(div).remove();
	return (w1 - w2);
}

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regexS = "[\\?&]" + name + "=([^&#]*)";
	var regex = new RegExp(regexS);
	var results = regex.exec(l);
	if (results == null)
		return "";
	else
		return decodeURIComponent(results[1].replace(/\+/g, " "));
}

function iPx() {
	if ((navigator.userAgent.match(/iPhone/i))
			|| (navigator.userAgent.match(/iPod/i))
			|| (navigator.userAgent.match(/iPad/i)))
		return true;
	return false;
}

/*
 * 
 * Object.prototype.clone = function() {
 * 
 * var newObj = (this instanceof Array) ? [] : {};
 * 
 * for (i in this) {
 * 
 * if (i == 'clone') continue;
 * 
 * if (this[i] && typeof this[i] == "object") {
 * 
 * newObj[i] = this[i].clone();
 *  } else newObj[i] = this[i]
 *  } return newObj;
 *  };
 * 
 */

$(document).ready(function() {
			$("img").removeAttr("alt");
		});

var flag = false;
function lightbox_worldwide(obj) {
	flag = true;

	lightbox.ini();
	lightbox.status = 1;
	lightbox.overlay();

	var hrefObj = g_HttpRelativeWebRoot
			+ 'groups/public/documents/webasset/hw_047292.swf&590&310';
	var srcArray = hrefObj.split('&');
	var srcPath = srcArray[0];
	lightbox.width = srcArray[1];
	lightbox.height = srcArray[2];

	$('<div id="lightbox" />').appendTo('body').css({
				'width' : lightbox.width,
				'height' : lightbox.height,
				'z-index' : 99
			}).append('<div class="videoHead"><div class="Close"></div></div>')
			.center();

	$('#lightbox .Close').click(lightbox.close);
	$('#lightbox')
			.append('<div class="video"><div id="lightbox_flash"><div style="text-align:center;"><img src=g_HttpRelativeWebRoot + "groups/public/documents/webasset/hw_076709.gif" /></div></div></div>');
	$('#lightbox .heading div').removeClass("title");
	$('#lightbox').css({
				'width' : parseInt(lightbox.width) + 10,
				'height' : parseInt(lightbox.height) + 50
			}).find('.heading').css('width', lightbox.width);
	var flashvars = {};
	var flash_params = {
		menu : "false",
		scale : "noScale",
		allowFullscreen : "true",
		allowScriptAccess : "always",
		bgcolor : "#FFFFFF"
	};

	swfobject.embedSWF(srcPath, 'lightbox_flash', lightbox.width,
			lightbox.height, '9.0.0', "expressInstall.swf", flashvars,
			flash_params);

	$(window).unbind('scroll').resize(lightbox.relocate);
}
