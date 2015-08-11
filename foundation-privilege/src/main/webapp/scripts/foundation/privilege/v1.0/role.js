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

// 添加角色UI
function addRoleUI() {
	$.post('/restful/privilegeProxyService/role/addRoleUI', null,
			function(data) {
				$.dialog( {
					id : 'addroleid',
					title : '添加角色',
					content : data,
					lock : true
				});
			});
}

//添加角色
function addRole(uiId) {
	if($("#roleName").val()==""){
		alert("角色名称不能为空！");
		return;
	}
	var data = $("#dataDatail").serializeObjectForm();
	var data1 = {
		"data" : data
	};
	var dataStr = JSON.stringify(data1);
	var url = "/restful/privilegeProxyService/role/add";
	$.ajax( {
		url : url,
		type : "post",
		data : dataStr,
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			if (data.success) {
				art.dialog( {
					id : uiId
				}).close();
				loadDiv("/restful/privilegeProxyService/role/roleListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}

// 修改角色UI
function editRoleUI() {
	var str = "";
	var i=0;
	$("input[name='roleids']:checked").each(function() {
			if ($(this).val()) {
				str += "" + $(this).val() + ",";
			}
			i=(++i);
	});
	if(i!=1){
		alert("请选中一条数据!");
		return false ;
	}
	str = str.substr(0, str.length - 1);
	
	$.post('/restful/privilegeProxyService/role/findRole', {
		"roleid" : str
	}, function(data) {
		$.dialog( {
			id : 'updateroleid',
			title : '修改角色',
			content : data,
			lock : false
		});
	});
}

//删去角色
function deleteById() {
	var i=0;
	$("input[name='roleids']:checked").each(function() {
			if ($(this).val()) {
				str += "" + $(this).val() + ",";
			}
			i=(++i);
	});
	if(i==0){
		alert("请选择要删除的角色!");
		return false ;
	} 
	if (!confirm('您确认删除角色吗？')) {
		return false;
	}
	var data = {};
	data["data"] = {};
	var url = "/restful/privilegeProxyService/role/delete";
	var str = "";
	$(".check_box").each(function() {
		if ($(this).attr("checked")) {
			if ($(this).val()) {
				str += "'" + $(this).val() + "',";
			}
		}
	});
	str = str.substr(0, str.length - 1);
	data["data"]["roleids"] = str;
	$.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv('/restful/privilegeProxyService/role/roleListGrid');
			} else {
				alert(data.msg);
			}
		}
	});
}


//角色列表（包含查询）
function reloadRoleList(offset){
	//总分页数目
	var totalpage = $("#totalpage").val();
	//角色名称
	var rolename = $("#rolename").val()
	
	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	//拼json参数
	var params = {offset:offset,name:rolename};
	loadDiv('/restful/privilegeProxyService/role/roleListGrid',params,null);
}


// 赋值资源UI
function openDistributionUI(roleid) {
	$.post('/restful/privilegeProxyService/role/openDistributionUI', {roleid:roleid},function(data) {
		$.dialog( {
			id : 'distributionResourceid',
			title : '分配资源',
			content : data,
			lock : false
		});
	});
}


function submitCheckboxDistributioReosurce(){
	var data = {};
	data["data"] = {};
	var url = "/restful/privilegeProxyService/role/distributionResource";
	var str = "";
	if($(".checkValue").length<=0){
		alert("请选择资源！");
		return false;
	}
		
	
	$(".checkValue").each(function() {
		if ($(this).attr("checked")) {
			if ($(this).val()) {
				str += "" + $(this).val() + ",";
			}
		}
	});
	str = str.substr(0, str.length - 1);
	data["data"]["roleid"]=$("#roleid").val();
	data["data"]["resourceids"] = str;
	$.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				art.dialog({
					id : 'distributionResourceid'
				}).close();
				loadDiv("/restful/privilegeProxyService/role/roleListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}


//部门授权UI
function openDepartmentAuthorizeUI(roleid) {
	$.post('/restful/privilegeProxyService/role/openDepartmentAuthorizeUI', {roleid:roleid},function(data) {
		$.dialog( {
			id : 'departmentAuthorizeid',
			title : '部门授权',
			content : data,
			lock : false
		});
	});
}

//部门授权
function submitCheckboxDepartmentAuthorize(){
	var data = {};
	data["data"] = {};
	var url = "/restful/privilegeProxyService/role/openDepartmentAuthorize";
	var str = "";
	if($(".checkValue").length <=0){
		alert("请选择资源！");
		return false;
	}
		
	
	$(".checkValue").each(function() {
		if ($(this).attr("checked")) {
			if ($(this).val()) {
				str += "" + $(this).val() + ",";
			}
		}
	});
	str = str.substr(0, str.length - 1);
	data["data"]["roleid"]=$("#roleid").val();
	data["data"]["departmentids"] = str;
	$.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				art.dialog({
					id : 'departmentAuthorizeid'
				}).close();
				loadDiv("/restful/privilegeProxyService/role/roleListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}
