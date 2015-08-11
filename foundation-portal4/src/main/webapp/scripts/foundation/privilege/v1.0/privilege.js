function loadDiv(url, params, callback) {
	$("#pageInfo").load(url, params, callback);
}

// 全选
function selectAll(obj) {
	var str = "";
	if ($(obj).attr("checked")) {
		$("input[type='checkbox']").attr("checked", true);
		$(".selectFocus").each(function() {
					if ($(this).val()) {
						str += "'" + $(this).val() + "',";
					}
				});
		str = str.substr(0, str.length - 1);
		$("#ids").val(str);
	} else {
		$("input[type='checkbox']").attr("checked", false);
	}
}

// 选择其中一行
function selectFocus(obj) {
	var b = true;
	$(".selectFocus").each(function() {
				if (!$(this).attr("checked")) {
					b = false;
					return false;
				}
			});
	if (b) {
		$("input[type='checkbox']").attr("checked", true);
	} else {
		$("input[type='checkbox']:first").attr("checked", false);
		$("input[type='checkbox']:last").attr("checked", false);
	}
}
