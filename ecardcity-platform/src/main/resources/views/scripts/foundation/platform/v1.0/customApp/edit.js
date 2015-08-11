/**
 * 约定优于规范 特别说明 凡是以_开头的变量以及方法【函数】，表明是private变量或者方法【函数】。
 */
/*******************************************************************************
 * 清理页面缓存 主要这对IE浏览器
 *
 * 原生js方法： 1、在服务端加 header(“Cache-Control: no-cache, must-revalidate”);(如php中)
 * 2、在ajax发送请求前加上 anyAjaxObj.setRequestHeader(“If-Modified-Since”,”0″);
 * 3、在ajax发送请求前加上 anyAjaxObj.setRequestHeader(“Cache-Control”,”no-cache”); 4、在
 * Ajax 的 URL 参数后加上 “?fresh=” + Math.random(); //当然这里参数 fresh 可以任意取了
 * 5、第五种方法和第四种类似，在 URL 参数后加上 “?timestamp=” + new Date().getTime();
 * 6、用POST替代GET：不推荐
 *
 *
 *
 *
 ******************************************************************************/
jQuery.ajaxSetup({
	cache : false
});
/**
 * 自定义应用包查询
 *
 * @param ids
 *            实体标识符
 */
function editCusApp(ids) {
	// var data = $("#dataDatail").serializeObjectForm();
	var cusApp = {
		"id" : ids
	};
	var dataStr = JSON.stringify(cusApp);
	var url = "customAppEdit";
	$.ajax({
		url : url,
		type : "post",
		data : dataStr,
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			$("#appname").val(data.appname);
			$("#desc").val(data.appdescription);
			$("#ids").val(data.id);
			editCusApp_Module(data.id);
		}
	});
}
/**
 * 自定义应用包查询_模块_展示数据
 *
 * @param ids
 *            实体标识符
 */
function editCusApp_Module(ids) {
	var cusApp = {
		"id" : ids
	};
	var dataStr = JSON.stringify(cusApp);
	var url = "findModule";
	$.ajax({
		url : url,
		type : "post",
		data : dataStr,
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			var zTree = $.fn.zTree.getZTreeObj("cusTree");
			zTree.checkAllNodes(false);
			$("#listModule").html("");
			for ( var i = 0; i < data.length; i++) {
				zTree.checkNode(zTree.getNodeByParam("id", data[i].moduleid,
						null), true, true);
				$("#listModule").html(
						$("#listModule").html() + " <option value='"
								+ data[i].moduleid + "'>" + data[i].modulename
								+ "</option>");
			}
		}
	});
}
/**
 * 自定义应用包_取消
 */
function cancel() {
	$("#appname").val("");
	$("#desc").val("");
	$("#ids").val("");
	var zTree = $.fn.zTree.getZTreeObj("cusTree");
	zTree.checkAllNodes(false);
	$("#listModule").html("");
}
/**
 * 自定义应用包编辑或者保存
 */
function merge() {

	var zTree = $.fn.zTree.getZTreeObj("cusTree");
	var nodes = zTree.getCheckedNodes(true);
	var ids = ""; // 获取选中的模块id
	for ( var i = 0; i < nodes.length; i++) {
		ids += nodes[i].id;
		if (i != nodes.length - 1) {
			ids += ",";
		}
	}
	//console.info(ids);
	var cusApp = {
		"ids" : ids,
		"appname" : $("#appname").val(),
		"desc" : $("#desc").val(),
		"id" : $("#ids").val()
	};
	var dataStr = JSON.stringify(cusApp);
	var url = "merge";
	$.ajax({
		url : url,
		type : "post",
		data : dataStr,
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
		      if(data==true||data=="true"){
		    	  cancel();
			      location.reload();
		      }
		}
	});
}