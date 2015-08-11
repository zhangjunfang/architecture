$(function() {
	// 初始化 mainIframe 高度
	var init_h = function() {

		var body_h = $(window).height() - 80 - 60 - 4;
		$('.menu-wrap,.main-wrap').height(body_h);
		$('#mainIframe').height(body_h - 4);
	}

	$(window).resize(function() {
		init_h();
	});

	 $(window).scroll(function() {
		 init_h();
	 });
	init_h();

	// 菜单选择
	$('.menu-wrap dt').click(function() {
		var self = $(this);

		// 获取一级菜单是否有url
		var first_url = self.find('a').attr('href');
		if (first_url) {
			// $('#mainIframe').load(modelPath + "/" + first_url);
			// $('#mainIframe').attr('src', first_url);
		}

		// alert(first_url);
		if (self.hasClass('disabled')) {
			return false;
		}

		if (self.closest('dl').hasClass('active')) {
			self.closest('dl').find('dd').slideUp(function() {
				$(this).find('a.active').removeClass('active');
				self.closest('dl').removeClass('active');
				init_h();
			})
		} else if ($('.menu-wrap dl.active').length > 0) {
			$('.menu-wrap dl.active').find('dd').hide();
			$('.menu-wrap dl').removeClass('active');
			self.closest('dl').addClass('active');

			self.closest('dl').find('dd').slideDown(function() {
				if (!first_url) {
					// self.closest('dl').find('dd:first a')[0].click();
				}
				init_h();
			});
		} else {
			self.closest('dl').addClass('active').slideDown();
			self.closest('dl').find('dd').slideDown(function() {
				if (!first_url) {
					// self.closest('dl').find('dd:first a')[0].click();
				}
				init_h();
			});
		}
		return false;
	})

	// 菜单子项点击事件
	$('.menu-wrap dd a').click(function() {
		var self = $(this);
		$('.menu-wrap dd a').removeClass('active');
		self.addClass('active');

		var first_url = self.attr('href');

		function activeHtml() {
			$('#mainIframe').hide();
			var a = $('#mainIframe').html();
			if(a.startWith("{")&& a.endWith("}")){
				var aa = $.evalJSON(a);
				if(aa.status==false && aa.result=='0'){
					$.jBox.alert(aa.msg, '提示');
					setTimeout(function(){
						$(".icon-signout").closest("a").trigger("click");
					},1500);
					return false;
				}
			}
			
			//$("#mainIframe").find("div[id='currentDataDiv']").crud({});
			$('#mainIframe').show();
			$(".body-container .breadcrumb .active").html(self.html());
		}

		$('#mainIframe').load(modelPath + "/" + first_url, null, activeHtml);

		return false;
	})
});

$(function() {
	//退出
	$(".icon-signout").closest("a").click(function() {
		var url = modelPath + "/doLogout";
		$.ajax({
			url : url,
			success : function(d) {
				window.location.reload();
			}
		});
	});
	
	//加载菜单树
});

$(function() {
	// 点击标题排序
	$('table thead th a.sort').live(
			'click',
			function() {
				var self = $(this);
				var i_class = $('i', self).attr('class');
				$('table thead th a.sort i').removeClass('icon-caret-up')
						.removeClass('icon-caret-down');

				if (i_class == 'icon-caret-down') {
					$('i', self).removeClass('icon-caret-down').addClass(
							'icon-caret-up');
				} else {
					$('i', self).removeClass('icon-caret-up').addClass(
							'icon-caret-down');
				}
				return false;
			})

	// table行选中
	$('.table-single > tbody > tr').live('click', function() {
		var self_tr = $(this);
		if (self_tr.hasClass('selected_tr')) {
			self_tr.removeClass('selected_tr');
			return;
		}

		self_tr.parent().find('tr').removeClass('selected_tr');
		self_tr.addClass('selected_tr');
	})

	$('.table-double > tbody > tr').live('click', function() {
		var self_tr = $(this);
		if (self_tr.hasClass('selected_tr')) {
			self_tr.removeClass('selected_tr');
			return;
		}
		self_tr.addClass('selected_tr');
	})

	// 移除table的一行
	$('body').on('click', '.remove_tr', function() {
		$(this).parent().parent().remove();
	})
	
	 $("th .sort").live('click',function(){
			var $this =$(this);
			var classes = $this.find("i").attr("class");
			var sortname = $this.parent().attr("name");
			//sortname = sortname.underscoreName();
			var str = "down";
			var sortorder = "asc";
			if(classes.endWith(str)){
				//降序
				sortorder = "desc";
			}
			var $form = $this.parents("table").siblings(".queryForm");
			var $inputsortName = $form.find("input[name='sort']");
			var $inputsortorder = $form.find("input[name='order']");
			
			if($inputsortName.length==0){
				$inputsortName = $("<input name='sort' type='hidden'>")		
				$inputsortName.appendTo($form);
			}
			if($inputsortorder.length==0){
				$inputsortorder = $("<input name='order' type='hidden'>")		
				$inputsortorder.appendTo($form);
			}
			
			if($.trim(sortname)!=''&& $.trim(sortorder)!=''){
	    		$inputsortName.val(sortname);
	    		$inputsortorder.val(sortorder);
	    		$form.find(".search").trigger("click");
			}else{
				return false;
			}
		});
});

/** *遍历分页数据从渲染到页面中** */
function getPaginationHtml(data, divId) {
	var result = "";
	var pageNow = data.pageIndex;
	var pageSize = data.pageSize;
	var total = data.total;
	var totalPage = (total + pageSize - 1) / pageSize;

	totalPage = Math.floor(totalPage);
	if (pageNow > 1) {
	}
	result += "<li class='' onclick='goPage(1,\"" + divId + "\"," + pageNow
			+ "," + totalPage + ")'><a href='javascript:void(0);'>首页</a></li>";
	result += "<li class=''onclick='goPage(" + (pageNow - 1) + ",\"" + divId
			+ "\"," + pageNow + "," + totalPage
			+ ")'><a href='javascript:void(0);'>上一页</a></li>";

	if (pageNow < totalPage) {
	}
	result += "<li class=''><a href='javascript:void(0);'  onclick='goPage("
			+ (pageNow + 1) + ",\"" + divId + "\"," + pageNow + "," + totalPage
			+ ")'>下一页</a></li>";
	result += "<li class=''><a href='javascript:void(0);'  onclick='goPage("
			+ totalPage + ",\"" + divId + "\"," + pageNow + "," + totalPage
			+ ")'>末页</a></li>";

	result += "<li>&nbsp;第 <input type='text' onkeyup=\"value=value.replace(/[^\\d]/g,'')\" name='pagenum' class='pagenum'  onblur='queryPageNumChange(this,\""
			+ divId
			+ "\","
			+ totalPage
			+ ","
			+ pageNow
			+ ")' value='"
			+ pageNow + "'/>/" + totalPage + " 页</li>";

	function selected(size) {
		var result = "";
		if (size == pageSize) {
			result = "selected";
		}
		return result;
	}

	result += "<li>&nbsp;每页 <select onchange='queryPageSizeChange(this,\""
			+ divId + "\")'><option " + selected(5) + ">5</option><option "
			+ selected(10) + ">10</option><option " + selected(20)
			+ ">20</option><option " + selected(50)
			+ ">50</option></select> 条</li>";
	return result;
}

function queryPageNumChange(input, div, total, oldPageNow) {
	var pageNow = $(input).val();
	pageNow = parseInt(pageNow);

	if (pageNow > total) {
		$.jBox.alert('不能大于总页数！', '提示');
		$(input).val(oldPageNow);
		return false;
	} else if (pageNow == oldPageNow) {
		return false;
	}
	goPage(pageNow, div, oldPageNow, total);
}

function queryPageSizeChange(select, div) {
	var $div = $("#" + div);
	$div.find(".queryForm").find("input[name='pageSize']").val($(select).val());
	$div.find(".queryForm").find(".search").trigger("click");
}

function goPage(pageNo, div, pageNow, totalPage) {
	var $div = $("#" + div);
	if (pageNo == 0) {
		pageNo = 1;
	}
	if (pageNo == pageNow) {
		$.jBox.alert('当前页不需要跳转', '提示');
		return false;
	}

	if (pageNo > totalPage) {
		$.jBox.alert('不能大于总页数！', '提示');
		//$div.find(".queryForm").find("input[name='pagenum']").val(pageNo);
		return false;
	}

	$div.find(".queryForm").find("input[name='pagenum']").val(pageNo);
	$div.find(".queryForm").find(".search").trigger("click");
}

function getQueryFormData($form){
	var result = {};
	
	var $inputPagenum = $form.find("input[name='pagenum']");
	var $inputPageSize = $form.find("input[name='pageSize']");
	
	if($inputPagenum.length==0){
		$inputPagenum = $("<input name='pagenum' type='hidden' value='1'>")		
		$inputPagenum.appendTo($form);
	}
	if($inputPageSize.length==0){
		$inputPageSize = $("<input name='pageSize' type='hidden' >")		
		$inputPageSize.appendTo($form);
	}
	
	if($form){
		 var data = $form.serializeObjectForm();
		 var conditions = [];
		 var conditionsIndex = 0;
		 $.each(data,function(key,value){
			 var $e = $form.find("*[name='"+key+"']");
			 var opr = $e.attr("opr");
			 if($.trim(value)!=''){
				 if($.trim(opr)==''){
					 result[key] = value;
				 }else{
					 result["conditions["+conditionsIndex+"].name"] = key;
					 result["conditions["+conditionsIndex+"].value"] = value;
					 result["conditions["+conditionsIndex+"].opr"] = opr;
					 conditionsIndex ++ ;
				 }
			 }
		 });
		 result.conditions = conditions;
	}
	return result;
}


//带附件的ajax	
/**
 * form ：表单jquery对象或者dom对象
 * fileDivId: 上传文件所在的div id
 * url：请求路径
 * callback：回调函数
 * fileType：空会根据fileDivId 去上传，上传一个文件，class 时，会根据file的class去长传，可以多个
 * pri: form 前缀
 */
var $zFileFormAjax = function(form,fileDivId,url,callback,fileType,pri){
	try{
		form = $(form);
		if (url.indexOf("?") == -1) {
			url +="?";
		}else{
			url += "&";
		}
		 var saveData = $(form).serializeObjectForm(pri);
		$(":input[type='button']", form).attr("disabled", true);
		$(":input", form).attr("readonly","readonly");
		
		 $.ajaxFileUpload({
		        url: url,
		        secureuri: false,
		        fileElementId: fileDivId,
		        dataType: 'text',
		        data: saveData,
		        fileType:fileType,
		        success: function(returnData, status){
		        	$(":input", form).attr("readonly",false);
		         	$(":input[type='button']", form).attr("disabled", false);
		            if (returnData.indexOf("<pre") != -1) {
		                var errorInfo = returnData.replace(/<pre[^>]*>/, "");
		                errorInfo = errorInfo.replace("</pre>", "");
		                returnData = $.secureEvalJSON(errorInfo);
		            }//ie正常返回
		            else {
		                returnData = $.secureEvalJSON(returnData);
		            }
		            
	            	if(callback){
		            	callback(returnData);
	            	}
		        },
		        error: function(returnData, status, e){
		            alert(e);
		        }
		    }); 
			return false;
	}catch(e){
		alert(e);
	}
};
