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

function loadDiv(divid,url,params, callback){
	$("#"+divid).load(url, params,callback);
}
//角色列表（包含查询）
function reloadRoleGroupList(offset){
	//总分页数目
	var totalpage = $("#totalpage").val();
	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	//拼json参数
	var params = {offset:offset};
	loadDiv('pageInfo','roleGroupListGrid',params,null);
}
// 添加角色UI
function addRoleGroupUI() {
//	var params = {offset:offset};
	$("#editpageInfo").hide();
	$("#addpageInfo").show();
	loadDiv('addpageInfo','addRoleGroupUI',null,null);
}

//添加角色
function addRoleGroup() {
	var data = $("#adddataDatail").serializeObjectForm();
	if(data["groupname"]=="" || data["groupname"] == undefined ){
		alert("角色组名称不能为空！");
		return;
	};
	if($("#groupstate").attr("checked")){
		data["groupstate"] = 1;
	}else{
		data["groupstate"] = 0;
	}
	var str ='',i = 0;
	$("input[name='roles']:checked").each(function() {
		if ($(this).val()) {
			str += "" + $(this).val() + ",";
		}
		i=(++i);
	});
	if(i==0){
		alert("请选择要关联的角色!");
		return false ;
	} 
	str = str.substr(0, str.length - 1);
	data["roleids"] = str;
	var url = "addRoleGroup";
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv('pageInfo',"roleGroupListGrid");
				$('#addpageInfo').hide();
			} else {
				alert(data.msg);
			}
		}
	});
}

// 修改角色UI
function editRoleGroupUI(uid) {
	var params = {uuid:uid};
	$('#addpageInfo').hide();
	$('#editpageInfo').show();
	loadDiv('editpageInfo','editRoleGroupUI',params,null);
}

//修改角色
function editRoleGroup() {
	var data = $("#editdataDatail").serializeObjectForm();
	if(data["groupname"]=="" || data["groupname"] == undefined ){
		alert("角色组名称不能为空！");
		return;
	};
	if($("#groupstate1").attr("checked")){
		data["groupstate"] = 1;
	}else{
		data["groupstate"] = 0;
	}
	var str ='';
	var i =0;
	$("input[name='roles']:checked").each(function() {
		if ($(this).val()) {
			str += "" + $(this).val() + ",";
		}
		i=(++i);
	});
	if(i==0){
		alert("请选择要关联的角色!");
		return false ;
	} 
	str = str.substr(0, str.length - 1);
	data["roleids"] = str;
	var url = "editRoleGroup";
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv('pageInfo',"roleGroupListGrid");
				$('#editpageInfo').hide();
			} else {
				alert(data.msg);
			}
		}
	});
}

//删去角色
function deleteById(uid) {
	if (!confirm('您确认删除角色组吗？')) {
		return false;
	}
	$('#addpageInfo').hide();
	$('#editpageInfo').hide();
	var url = "deleteRoleGroup";
	$.ajax({
		url : url,
		type : "post",
		data : {uuid:uid},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv('pageInfo',"roleGroupListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}

