jQuery.ajaxSetup({
			cache : false
		});

function loadDiv(url, params, callback) {
	$("#pageInfo").load(url + "?timestamp=" + new Date().getTime(), params,
			callback);
}

// 角色列表（包含查询）
function reloadResourceList(offset) {
	// 总分页数目
	var totalpage = $("#totalpage").val();
	// 角色名称
	var resourcename = $("#resourcename").val();
	var status = $("#status").val();
	var typeid = $("#typeid").val();

	if (offset == 0) { // 点击go触发
		offset = $("#offset").val();
	}
	if (offset > parseInt(totalpage) && parseInt(totalpage) != 0) {
		offset = totalpage;
	}
	// 拼json参数
	var params = {
		offset : offset,
		name : resourcename,
		status : status,
		typeid : typeid
	};
	// return;
	loadDiv("resourceListGrid", params, null);
}

function reloadResourceList_clickTree(parent_id) {
	console.log(parent_id);
	// 拼json参数
	var params = {
		menu_id : "'" + parent_id + "'"
	};
	// return;
	loadDiv("resourceListGrid", params, null);
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
// 选择其中一行
function addResource() {
	$("#gj-select").fancybox({
				'width' : 700,
				'height' : '98%',
				'autoScale' : false,
				'transitionIn' : 'fade',
				'transitionOut' : 'fade',
				'type' : 'iframe',
				'hideOnOverlayClick' : false
			});
}

var deleteById = function() {
	var str = "";
	$(".check_box").each(function() {
				if ($(this).attr("checked")) {
					if ($(this).val()) {
						str += "'" + $(this).val() + "',";
					}
				}
			});
	if (str == "") {
		alert("请选择功能！");
		return;
	}

	if (confirm('您确认删除功能吗？')) {
		var data = {};
		data["data"] = {};
		str = str.substr(0, str.length - 1);

		$.ajax({
					url : "delete",
					type : "post",
					data : {
						resourceids : str
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							loadDiv("resourceListGrid");
						} else {
							alert(data.msg);
						}
					}
				});
	}
}

// 添加部门UI
function addResourceUI() {
	if ($("#tree_id").val() == -1) {
		alert("请选择菜单树节点！");
		return;
	}
	$.post('addResourceUI', null, function(data) {
				$.dialog({
							id : 'addresourceid',
							title : '添加功能',
							content : data,
							lock : true
						});
			});
}

var saveResource = function() {

	/*
	 * var data = $("#dataDatail").serialize(); var data1 = {"data":data};
	 * 
	 * var dataStr = JSON.stringify(data1);
	 * 
	 * console.log(data); console.log(data1); console.log(dataStr);
	 */

	// console.log($("#dataDatail").serializeJson())
	var name = $("#name").val().trim();
	if (name == "") {
		alert("功能名称不能为空！");
		return;
	}

	var data = $("#dataDatail").serializeObjectForm();
	data.menu_id = $("#tree_id").val();
	// console.log(__data);
	// return;

	var url = "add";
	$.ajax({
				url : url,
				type : "post",
				data : data,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						loadDiv("resourceListGrid", function() {
									art.dialog({
												id : 'addresourceid'
											}).close();
								});
					} else {
						alert(data.msg);
					}
				}
			});
}

// 修改資源UI
function editResourceUI() {
	var str = "";
	var i = 0;
	$("input[name='resourceids']:checked").each(function() {
				if ($(this).val()) {
					str += "" + $(this).val() + ",";
				}
				i = (++i);
			});
	if (i != 1) {
		alert("请选中一条数据!");
		return false;
	}
	str = str.substr(0, str.length - 1);

	$.ajax({
		url : "findResourceById",
		type : "post",
		data : {
			id : str
		},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				var __data = data;
				$.post('editResourceUI', {
							"resourceid" : str
						}, function(data) {
							$.dialog({
										id : 'updateresourceid',
										title : '修改功能',
										content : data,
										lock : true,
										init : function() {
											console.log(__data);
											$("#edit_name")
													.val(__data.data.resourceName);
											$("#edit_url").val(__data.data.url);
											$("#edit_status")
													.val(__data.data.status);
											$("#edit_memo")
													.val(__data.data.memo);
										}
									});
						});
			} else {
				alert(data.msg);
			}
		}
	});
}

var updateResource = function() {
	// console.log($("#dataDatail").serializeJson())

	var name = $("#edit_name").val().trim();
	if (name == "") {
		alert("功能名称不能为空！");
		return;
	}

	var str = "";
	$("input[name='resourceids']:checked").each(function() {
				if ($(this).val()) {
					str = $(this).val();
				}
			});
	var data = $("#dataDatail").serializeObjectForm();
	data.id = str;

	var url = "edit";
	$.ajax({
				url : url,
				type : "post",
				data : data,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						loadDiv("resourceListGrid", function() {
									art.dialog({
												id : 'updateresourceid'
											}).close();
								});
					} else {
						alert(data.msg);
					}
				}
			});
}
