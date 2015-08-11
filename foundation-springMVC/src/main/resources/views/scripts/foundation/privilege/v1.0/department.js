/**
 * 约定优于规范 特别说明 凡是以_开头的变量以及方法【函数】，表明是private变量或者方法【函数】。
 */
/**
 * 清理页面缓存  主要这对IE浏览器
 * 
 * 原生js方法：
　　1、在服务端加 header(“Cache-Control: no-cache, must-revalidate”);(如php中)
　　2、在ajax发送请求前加上 anyAjaxObj.setRequestHeader(“If-Modified-Since”,”0″);
　　3、在ajax发送请求前加上 anyAjaxObj.setRequestHeader(“Cache-Control”,”no-cache”);
　　4、在 Ajax 的 URL 参数后加上 “?fresh=” + Math.random(); //当然这里参数 fresh 可以任意取了
　　5、第五种方法和第四种类似，在 URL 参数后加上 “?timestamp=” + new Date().getTime();
　　6、用POST替代GET：不推荐
 * 
 * 
 * 
 * 
 * **/
jQuery.ajaxSetup ({cache:false}); 

function loadDiv(url, params, callback) {
	$("#pageInfo").load(url, params, callback);
}

/**
 * 部门查询列表（包括查询）
 * 
 * @param offset
 */
function reloadDepartmentList() {

	var offset = arguments[0];
	// 部门名称
	var departmentName = $("#oceanDepartmentId").val();
	// 部门状态
	var oceanStatus = $("#oceanStatus").val();
	// 总分页数目
	var totalpage = $("#totalpage").val();
	if (offset == 0) { // 点击go触发
		offset = $("#offset").val();
	}
	if (offset > parseInt(totalpage) && parseInt(totalpage) != 0) {
		offset = totalpage;
	}
	// 组装json数据格式
	var params = {
		offset : offset,
		name : departmentName,
		status : oceanStatus
	};
	loadDiv("departmentListGrid",
			params, null);
	// 部门名称
	var departmentName = $("#oceanDepartmentId").val("");
	// 部门状态
	var oceanStatus = $("#oceanStatus").val("");
}

// 添加部门UI
function addDepartmentUI() {
	var treeObj = $.fn.zTree.getZTreeObj("departmentTree");
	var nodes = treeObj.getNodes();
	if (nodes.length == 0) {
		$("#addDepartmentUI").val("-1");
		_addDepartmentUI();
	} else {
		var addDepartmentIsParent = $("#addDepartmentIsParentUI").val();
		var addDepartmentNameUI = $("#addDepartmentNameUI").val();
		var parentDepartmentIsParentUI = $("#parentDepartmentIsParentUI").val();
		// alert(parentDepartmentIsParentUI+","+addDepartmentIsParent+","+addDepartmentNameUI);
		if ((addDepartmentIsParent == "false" && addDepartmentNameUI != "" && parentDepartmentIsParentUI == "true")) {
			alert("部门层次不允许添加两层以上！！");
			return false;
		} else {
			_addDepartmentUI();
		}
	}
	$("#addDepartmentIsParentUI").val("");
	//$("#addDepartmentNameUI").val("");
	$("#parentDepartmentIsParentUI").val("");
}
// 添加部门视图展示
function _addDepartmentUI() {
	var addId = $("#addDepartmentUI").val();
	if (addId == "" || addId.length == 0) {
		if (confirm('您确认添加一级部门吗？')) {
			$("#addDepartmentUI").val("-1");
		} else {
			alert("请选择上级部门");
			return false;
		}
	}
	$("#addDepartment").val(addId);
	$.post('addDepartmentUI', null,
			function(data) {
				$.dialog({
							id : 'adddepartmentid',
							title : '添加部门',
							content : data,
							lock : false
						});
			});
}
// 添加部门
function addDepartment() {
	var bool = validate_required("addName", "部门名称不能为空！");
	if (bool == false) {
		return false;
	}
	var data = $("#dataDatail").serializeObjectForm();
	var data1 = {
		"data" : data
	};
	var dataStr = JSON.stringify(data1);
	var url = "addDepartment";
	$.ajax({
		url : url,
		type : "post",
		data : dataStr,
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			loadDiv('departmentListGrid');
			if (data.success) {
				art.dialog({
							id : 'adddepartmentid'
						}).close();
			} else {
				alert(data.msg);
			}
		}
	});
	$(document).ready(function() {
				init.departmentTree();
			});
}
// 修改部门UI
function editDepartmentUI() {
	var strs = _isSelectOneData();
	if (strs == false) {
		return false;
	}
	var addId = $("#addDepartmentUI").val();
	if (addId == "" || addId.length == 0) {
		//if (confirm('确认不需要修改上级部门？')) {
			_editDepartmentUI(strs);
		//} else {
		//	return false;
		//}
	} else {
		//if(confirm('确认需要修改上级部门？')){
		   _editDepartmentUI(strs);
		//}else{
		// return false;
		//}
	}
	_editDepartmentUI(strs);
}

// 提炼修改部门UI的方法，不建议外部使用此方法
function _editDepartmentUI(strs) {
	$.post('findDepartment', {
				"departmentid" : strs
			}, function(data) {
				$.dialog({
							id : 'updatedepartmentid',
							title : '修改部门',
							content : data,
							lock : false
						});
			});
}
// 判断是否选中一条数据
function _isSelectOneData() {
	var i = 0;
	var strs = "";
	$("input[name='checkbox']:checked").each(function() {
				if ($(this).val()) {
					strs += "" + $(this).val() + ",";
				}
				i = (++i);
			});
	if (i != 1) {
		alert("请选中一条数据!");
		return false;
	}
	strs = strs.substr(0, strs.length - 1);
	return strs;
}
// 提交修改部门数据
function updateDepartment() {
	var bool = validate_required("updateName", "部门名称不能为空！");
	if (bool == false) {
		return false;
	}
	var data = $("#dataDatail").serializeObjectForm();
	var data1 = {
		"data" : data
	};
	var dataStr = JSON.stringify(data1);
	var url = 'updateDepartment';
	$.ajax({
		url : url,
		type : "post",
		data : dataStr,
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			loadDiv('departmentListGrid');
			if (data.success) {
				art.dialog({
							id : 'updatedepartmentid'
						}).close();
			} else {
				alert(data.msg);
			}
		}
	});
	$(document).ready(function() {
				init.departmentTree();
			});
}
// 删除部门
function deleteDepartmentById() {
	var strs = _isSelectData();
	if (strs == false) {
		return false;
	}
	if (!confirm('您确认删除部门吗？')) {
		return false;
	}
	var data = {};
	data["data"] = {};
	var url = "deleteDepartment";
	var str = "";
	$("input[type='checkbox']").each(function() {
				if ($(this).attr("checked")) {
					if ($(this).val()) {
						str += "'" + $(this).val() + "',";
					}
				}
			});
	str = str.substr(0, str.length - 1);
	data["data"]["departmentIds"] = str;
	$.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv("departmentListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
	$(document).ready(function() {
				init.departmentTree();
			});
}
// 判断是否选中数据
function _isSelectData() {
	var i = 0;
	var strs = "";
	$("input[name='checkbox']:checked").each(function() {
				if ($(this).val()) {
					strs += "" + $(this).val() + ",";
				}
				i = (++i);
			});
	if (i <= 0) {
		alert("请选中一条数据!");
		return false;
	}
	strs = strs.substr(0, strs.length - 1);
	return strs;
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
// 点击左侧树 节点 动态显示右侧列表数据
function dynamicLoadDepartmentList(treeId, treeNode) {
	// 拼json参数
	var params = {
		parent_id : treeNode.id
	};
	loadDiv(
			"departmentListGrid",
			params, null);
}

// 获取当前节点的所有子节点
// treeNode 当前节点

function getAllChildrenNodes(treeNode) {
	var result = "";
	if (treeNode.isParent) {
		alert(treeNode.children);
		var childrenNodes = treeNode.getNodes();

		if (childrenNodes) {
			for (var i = 0; i < childrenNodes.length; i++) {
				result += ',' + childrenNodes[i].id;
				// result = getChildNodes(childrenNodes[i], result);
			}
		}
	}
	return result;
}
// 部门验证
function validate_required(fieldId, alerttxt) {
	var value = $("#" + fieldId).val();
	if ("" == value || value.length == 0) {
		alert(alerttxt);
		fieldId.focus();
		return false;
	} else {
		return true;
	}
}
// 部门更新界面UI，需要获取父页面数据 ，赋值给子页面以及隐藏的数据【包含html元素】，使用结束,清除父页面缓存数据
function updateDepartmentUIAndData_show_hide() {
	var idname = $("#addDepartmentUI").val();
	if (idname.length != 0 || idname != '') {
		$("#parentId").val($("#addDepartmentUI").val());
		$("#parentName").val($("#addDepartmentNameUI").val());
		$("#addDepartmentUI").val("");
		$("#addDepartmentNameUI").val("");
		$("#parentDepartmentSign").show();
		$("#parentDepartmentContent").show();
	} else {
		$("#parentDepartmentSign").hide();
		$("#parentDepartmentContent").hide();
	}
}
// 部门添加界面UI，需要获取父页面数据 ，赋值给子页面，使用结束，清除父页面缓存数据
function addDepartmentUIAndAssignment() {
	if ($("#addDepartmentUI").val() == "0") {
		$("#addParentView").hide();
		$("#addParent").hide();
	}
	$("#addDepartment").val($("#addDepartmentUI").val());
	$("#parentIdName").val($("#addDepartmentNameUI").val());
	$("#addDepartmentUI").val("");
	$("#addDepartmentNameUI").val("");
}
