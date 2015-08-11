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
function reloadRoleList(offset){
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
	loadDiv('pageInfo','roleListGrid',params,null);
}
// 添加角色UI
function addRoleUI() {
	$("#editpageInfo").hide();
	$("#addpageInfo").show();
	loadDiv('addpageInfo','addRoleUI',null,treetext);
	//授权菜单
//	treetext();
}

//添加角色
function addRole() {
	var data = $("#adddataDatail").serializeObjectForm();
	if(data["roleName"]=="" || data["roleName"] == undefined ){
		alert("角色名称不能为空！");
		return;
	};
	if($("#rolestate").attr("checked")){
		data["rolestate"] = 1;
	}else{
		data["rolestate"] = 0;
	}
	var url = "addRole";
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv('pageInfo',"roleListGrid");
				$('#addpageInfo').hide();
			} else {
				alert(data.msg);
			}
		}
	});
}

// 修改角色UI
function editRoleUI(uid) {
	var params = {uuid:uid};
	$('#addpageInfo').hide();
	$('#editpageInfo').show();
	loadDiv('editpageInfo','editRoleUI',params,treetext2);
	//授权菜单
}

//修改角色
function editRole() {
	var data = $("#editdataDatail").serializeObjectForm();
	if(data["roleName"]=="" || data["roleName"] == undefined ){
		alert("角色名称不能为空！");
		return;
	};
	if($("#rolestate1").attr("checked")){
		data["rolestate"] = 1;
	}else{
		data["rolestate"] = 0;
	}
	var url = "editRole";
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv('pageInfo',"roleListGrid");
				$('#editpageInfo').hide();
			} else {
				alert(data.msg);
			}
		}
	});
}

//删去角色
function deleteById(uid) {
	if (!confirm('您确认删除角色吗？')) {
		return false;
	}
	$('#addpageInfo').hide();
	$('#editpageInfo').hide();
	var url = "deleteRole";
	$.ajax({
		url : url,
		type : "post",
		data : {uuid:uid},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv('pageInfo',"roleListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}

var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		check: {
			enable: true
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				console.log(arguments)
			}
		}
	}

	var modules = [{ Id: '9dea3de18553497284a0e9777c37291b',
		  PId: '0',
		  ModuleName: '城市一卡通应用包',
		  ModuleUrl: '',
		  Sort: 1 },{ Id: 'c5f1f1c14a9c42d88629498054bf5ba2',
		  PId: '9dea3de18553497284a0e9777c37291b',
		  ModuleName: '功能1',
		  Sort: 1 },{ Id: 'a4c691697b8a4d5baf63ee2b54f37a31',
		  PId: '9dea3de18553497284a0e9777c37291b',
		  ModuleName: '功能2',
		  Sort: 1 },{ Id: '7888a2a5de5c47d68cb80fe8415b2b0b',
		  PId: '9dea3de18553497284a0e9777c37291b',
		  ModuleName: '功能3',
		  Sort: 1 },{ Id: 'f7f4b33ef6ef4af289a307b12095b0c3',
		  PId: '0',
		  ModuleName: '管理功能2',
		  ModuleUrl: '',
		  Sort: 2 },{ Id: '6b23c8a4559646d5a23796222b654dc6',
		  PId: 'f7f4b33ef6ef4af289a307b12095b0c3',
		  ModuleName: '功能1',
		  Sort: 1 },{ Id: 'd3245e0014854a0e8321e85b3bbd2429',
		  PId: 'f7f4b33ef6ef4af289a307b12095b0c3',
		  ModuleName: '功能2',
		  Sort: 1 },{ Id: '0d4425e82b434601bbbb9f80f886f96a',
		  PId: '0',
		  ModuleName: '管理功能3',
		  ModuleUrl: '',
		  Sort: 3 }];

	for(var i=0; i<modules.length; i++){
		var module = modules[i];
		module.id = module.Id;
		module.pId = module.PId;
		module.name = module.ModuleName;
		module.file = module.ModuleUrl;
		module.open = true;
	}

function treetext(){
	$.fn.zTree.init($("#menuTree"), setting, modules);
}
function treetext2(){
	$.fn.zTree.init($("#menuTree2"), setting, modules);
}

