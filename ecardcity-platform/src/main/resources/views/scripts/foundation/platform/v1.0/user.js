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
 * **/
jQuery.ajaxSetup ({cache:false}); 


function loadDiv(divid,url,params, callback){
	$("#"+divid).load(url, params,callback);
}

//角色列表（包含查询）
function reloadUserList(offset){
	//总分页数目
	var totalpage = $("#totalpage").val();
	//用户名称
	var name = $("#username").val();
	//角色类别
	//var roletype = 0;
	//roletype = $("#roletype").find("option:selected").val(); 
	//角色
	var roleid = -1;
	roleid = $("#roleid").find("option:selected").val(); 
	//状态
	var empstate = 0;
	if($("#empstate").attr("checked")=="checked"){
	    empstate = 1;
	}
	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	//拼json参数
	var params = {offset:offset,name:name,roleid:roleid,empstate:empstate};
	loadDiv("pageInfo","userListGrid",params,null);
}

//添加用户UI
function addUserUI(){
	jQuery.post(
			'addUserUI', null,
			function(data) {
				jQuery.dialog( {
					id : 'adduserid',
					title : '添加人员',
					content : data,
					lock : false
				});
			});
}
//修改用户UI
function updateUserUI(uuid){
	jQuery.post(
			'editUserUI', {'uuid':uuid},
			function(data) {
				jQuery.dialog( {
					id : 'updateuserid',
					title : '修改人员',
					content : data,
					lock : false
				});
			});
}

function validateForm(){
	if($("#empCode").val()==""){
		alert("请输入用户名!");
		return true;
	}
	if($("#empPwd").val()==""){
		alert("请输入密码！");
		return true;
	} 
	if($("#empPwd2").val()==""){
		alert("请输入重复密码！");
		return true;
	} 
	if($("#empPwd").val()!=$("#empPwd2").val()){
		alert("两次输入密码不同！");
		return true;
	}
	if($("#name").val() == ""){
		alert("请输入姓名!");
		return true;
	}
	if($("#idcardno").val() == ""){
		alert("请输入证件号码!");
		return true;
	} 
//	if($("#roletype").find("option:selected").val() == "-1"){
//		alert("请选择角色或角色组!");
//		return true;
//	} 
	if($("#roleid").find("option:selected").val() == "-1"){
		alert("请选择角色组!");
		return true;
	} 
}
//新增用户
function addUser() {
	if(!validateForm()){
		var str="";
		var data = $("#dataDatail").serializeObjectForm();
		if($("#empstate").attr("checked")){
			data["empstate"] = 1;
		}else{
			data["empstate"] = 0;
		}
		var url = "addUser";
		jQuery.ajax( {
			url : url,
			type : "post",
			data : data,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					alert(data.msg);
					art.dialog( {
						id : 'adduserid'
					}).close();
					loadDiv("pageInfo","userListGrid");
				} else {
					alert(data.msg);
				}
			}
		});
	}
}
//修改用户
function updateUser() {
	var data = $("#dataDatail").serializeObjectForm();
	var url = "updateUser";
	if($("#empstate").attr("checked")){
		data["empstate"] = 1;
	}else{
		data["empstate"] = 0;
	}
	$.ajax( {
		url : url,
		type : "post",
		data : data,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				art.dialog( {
					id : 'updateuserid'
				}).close();
				loadDiv("pageInfo","userListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}
//删去
function deleteUser(str) {
	if(confirm("确认删除吗？")){ 
		var url= "deleteUser";
		$.ajax( {
			url : url,
			type : "post",
			data : {uuid:str},
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					loadDiv("pageInfo","userListGrid");
				} else {
					alert(data.msg);
				}
			}
		});
	}
}

/**
 * 加载角色 or 角色组 用于查询
 * aa 为当前 this
 * divid
 */
function loadRoleorGroup(aa,divid){
	var url = "getRole";
	//角色
	if(aa.value==0){
		 url = "getRole";
			$.ajax( {
				url : url,
				type : "post",
				data : null,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						var htm = "";
						for(var a=0 ;a<data.data.items.length;a++){
							//放入DIV
							htm += "<option value='"+data.data.items[a].ID+"'>"+data.data.items[a].ROLENAME+"</option>"   
						}
						$("#"+divid).html(htm);
					} else {
						alert(data.msg);
					}
				}
			});
	//角色组
	}else if (aa.value==1){
		 url = "getRoleGroup";
			$.ajax( {
				url : url,
				type : "post",
				data : null,
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						var htm ="";
						for(var a=0 ;a<data.data.items.length;a++){
							//放入DIV
							htm += "<option value='"+data.data.items[a].ID+"'>"+data.data.items[a].GROUPNAME+"</option>"   
						}
						$("#"+divid).html(htm);
					} else {
						alert(data.msg);
					}
				}
			});
	}else {
		return;
	}
}

