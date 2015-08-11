// 创建一个闭包    
(function($) {
	// 插件的定义    
	$.fn.hilight = function(options) {
		debug(this);
		// build main options before element iteration    
		var opts = $.extend({}, $.fn.hilight.defaults, options);
		// iterate and reformat each matched element    
		return this.each(function() {
			$this = $(this);
			// build element specific options    
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
			// update element styles    
			$this.css({
				backgroundColor : o.background,
				color : o.foreground
			});
			var markup = $this.html();
			// call our format function    
			markup = $.fn.hilight.format(markup);
			$this.html(markup);
		});
	};
	// 私有函数：debugging    
	function debug($obj) {
		if (window.console && window.console.log)
			window.console.log('hilight selection count: ' + $obj.size());
	};
	// 定义暴露format函数    
	$.fn.hilight.format = function(txt) {
		return '<strong>' + txt + '</strong>';
	};
	// 插件的defaults    
	$.fn.hilight.defaults = {
		foreground : 'red',
		background : 'yellow'
	};
	// 闭包结束    
})(jQuery);

$(function() {
	var divId = "currentDataDiv";
	var $div = $("#" + divId);
	var action = "manager"//$div.attr("action");
	function init() {
		//保存按钮
		$(".save").click(
				function() {
					var $form = $(this).closest(".modal-content").find("form");
					if ($form.valid() == false) {
						return false;
					}

					var url = action + "/save";
					var data = $form.serializeObjectForm();
					$.ajax({
						url : url,
						data : data,
						success : function(d) {
							var id = d.result.id;
							if (id == '-2') {
								//重复
								$.jBox.alert("用户名重复，请重新输入", "提示");
								$form.find("input[name='name']").closest( ".form-group").removeClass("has-success").addClass("has-error");
							} else if (d != '') {
								//保存成功
								$div.find(".queryForm").find(".search").trigger("click");
							}
						}
					});
				});

		//取消按钮
		$(".cancel").click(function() {
			var $form = $(this).closest(".modal-content").find("form");
			$div.find(".queryForm").find(".add").trigger("click");
		});

		//新增按钮
		$div.find(".add").click(function(e) {
			var targetDiv = $(this).attr("data-target");
			var $form = $(targetDiv).find("form");
			$form[0].reset();
			$form.validation();
		});

		//删除按钮
		$div.find(".del").click(function() {
			var $tr = $div.find(".table-list tbody .selected_tr");
			if ($tr && $tr.length > 0) {
				var id = $tr.attr("value");
				var url = action + "/deleteById?id=" + id;
				function submit(v, h, f) {
					if (v == 'ok') {
						//删除
						$.ajax({
							url : url,
							success : function(d) {
								if (d) {
									$div.find(".search").trigger("click");
								}
							}
						});
					}
					return true; //close
				}
				$.jBox.confirm("确认要删除该数据?", "提示", submit);
			} else {
				$.jBox.alert('请先选择数据', '提示');
				return false;
			}
		});

		//修改按钮
		$div.find(".edit").click(function() {
			var $tr = $div.find(".table-list tbody .selected_tr");
			var targetDiv = $(this).attr("data-target");
			var $form = $(targetDiv).find("form");
			if ($tr && $tr.length > 0) {
				var id = $tr.attr("value");
				var url = action + "/findById?id=" + id;
				$.ajax({
					url : url,
					success : function(d) {
						if (d.result) {
							$form.resetObjectForm(d.result);
						}
					}
				});
			} else {
				$.jBox.alert('请先选择数据', '提示');
				return false;
			}
		});

		//查询按钮
		$div.find(".search").click(function() {
			var $form = $div.find(".queryForm");
			var data = getQueryFormData($form);
			var url =  "manager/queryPage";
			//var data = $form.serializeObjectForm();
			$.ajax({
				url : url,
				data : data,
				success : function(d) {
					var data = d.result;
					if (data.items) {
						var tbody = $div.find(".table-list tbody");
						tbody.empty();
						$("#listTmpl").tmpl(data.items).appendTo(tbody);
						var html = getPaginationHtml(data, divId);
						$div.find(".pagination").html(html);
					}
				}
			});
		});

		$(".validation").validation();
	}

	init();
	$div.find(".search").trigger("click");
});