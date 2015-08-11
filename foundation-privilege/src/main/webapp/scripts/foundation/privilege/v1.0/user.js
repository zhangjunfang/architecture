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

var setting = {
			check: {
				enable: true,
				chkboxType: {"Y":"", "N":""}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onCheck: onCheck
			}
		};
		

		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			v_ids = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				v_ids+= nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (v_ids.length > 0 ) v_ids = v_ids.substring(0, v_ids.length-1);
			var cityObj = $("#citySel");
			var deptids = $("#deptids");
			
			cityObj.attr("value", v);
			deptids.attr("value", v_ids);
		}

		function showMenu() {
			var cityObj = $("#citySel");
			var cityOffset = $("#citySel").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			checkNodeById(getSelectDepartment(null));
			$("body").bind("mousedown", onBodyDown);
		}
		
		function hideMenu() {
			art.dialog( {
				id : 'adduserid'
			}).close();
			
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		var init = {
				treeItems:null,
				initTreeArchitecture:function()
				{
					var url = "/restful/privilegeProxyService/department/departmentItemAll";
					$.ajax( {
						url : url,
						type : "post",
						data : JSON.stringify({"test":"test"}),
						dataType : 'json',
						contentType : "application/json",
						success : function(data) {
							if (data.success) {
								this.treeItems = data.data;
								$.fn.zTree.init($("#treeDemo"), setting, this.treeItems);
							} else {
								alert(data.msg);
							}
						}
					});
				}
		}
		$(document).ready(function(){
			init.initTreeArchitecture();
		});
function checkNodeById(nodes)
{
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	
	
	//转化成数组循环处理ID
	var node = zTree.getNodeByTId("1");
	zTree.checkNode(node,true,false,false);
}
function getSelectDepartment(userId)
{
	//Ajax调用获取人员对应的部门
	var depts = '1';
	return depts;
}
// 添加用户UI
function addUserUI() {
	$.post('/restful/privilegeProxyService/user/addUserUI', null,
			function(data) {
				$.dialog( {
					id : 'adduserid',
					title : '添加人员',
					content : data,
					lock : false
				});
			});
}
//修改用户UI
function updateUserUI() {
	var length = $(":input[name='userids'][checked]").length;
	if(length != 1)
	{
		alert("请选择一条数据进行操作");
		return;
	}
	var temp = $(":input[name='userids'][checked]")[0].value;
	$.post('/restful/privilegeProxyService/user/updateUserUI', {"userid":temp},
			function(data) {
				$.dialog( {
					id : 'updateuserid',
					title : '修改人员',
					content : data,
					lock : false
				});
			});
}
function validateForm(){
	if($("#userId").val()==""){
		alert("请输入工号!");
		return true;
	}
	if($("#password").val()==""){
		alert("请输入密码！");
		return true;
	} 
	
	if($("#userName").val() == ""){
		alert("请输入姓名!");
		return true;
	}
	if($("#certificateCode").val() == ""){
		alert("请输入证件号码!");
		return true;
	}
}
//添加用户
function addUser() {
	if(!validateForm()){
		var str="";
		var data = {};
		data["data"] = {};
		var data1 = $("#dataDatail").serializeObjectForm();
		data["data"] = data1;
		$(".checkValue").each(function() {
			if ($(this).attr("checked")) {
				if ($(this).val()) {
					str += "" + $(this).val() + ",";
				}
			}
		});
		
		str = str.substr(0, str.length - 1);
		data["departmentids"] = str;
		var dataStr = JSON.stringify(data);
		var url = "/restful/privilegeProxyService/user/addUser";
		$.ajax( {
			url : url,
			type : "post",
			data : dataStr,
			dataType : 'json',
			contentType : "application/json",
			success : function(data) {
				if (data.success) {
					art.dialog( {
						id : 'adduserid'
					}).close();
					loadDiv("/restful/privilegeProxyService/user/userListGrid");
				} else {
					alert(data.msg);
				}
			}
		});
	}
	
}

//删去角色
function deleteById() {
	var length = $(":input[name='userids'][checked]").length;
	if(length <= 0)
	{
		alert("请选择要删除的人员！");
		return;
	}
	if (!confirm('您确认删除人员吗？')) {
		return;
	}
	var data = {};
	data["data"] = {};
	var url = "/restful/privilegeProxyService/user/delete";
	var str = "";
	$(".check_box").each(function() {
		if ($(this).attr("checked")) {
			if ($(this).val()) {
				str += "'" + $(this).val() + "',";
			}
		}
	});
	str = str.substr(0, str.length - 1);
	data["data"]["userids"] = str;
	$.ajax( {
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				loadDiv("/restful/privilegeProxyService/user/userListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}
//人员列表（包含查询）
function reloadUserList(offset){
	//总分页数目
	var totalpage = $("#totalpage").val();
	//角色名称
	var rolename = $("#username").val();
	var userid = $("#userId").val();
	var status = $("#status").val();
	
	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	//拼json参数
	var params = {};
	if(rolename)
	{
		params.name=rolename;
	}
	if(userid){
		params.userid = userid;
	}
	if(status){
		params.status = status;
	}
	if(offset!=undefined)
	{
		params.offset=offset;
	}
	loadDiv("/restful/privilegeProxyService/user/userListGrid",params,null);
}

//修改用户
function updateUser() {
	var data = $("#dataDatail").serializeObjectForm();
	var data1 = {
		"data" : data
	};
	var dataStr = JSON.stringify(data1);
	var url = "/restful/privilegeProxyService/user/updateUser";
	$.ajax( {
		url : url,
		type : "post",
		data : dataStr,
		dataType : 'json',
		contentType : "application/json",
		success : function(data) {
			if (data.success) {
				art.dialog( {
					id : 'updateuserid'
				}).close();
				loadDiv("/restful/privilegeProxyService/user/userListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}
//全部选择
function selectAllRole(obj){
	if($(obj).attr("checked")){
		$(".allresources").attr("checked",true);
	}else{
		$(".allresources").attr("checked",false);
	}
}
//赋值角色UI
function openDistributionUI(userId) {
	$.post('/restful/privilegeProxyService/user/openDistributionUI', {userid:userId},function(data) {
		$.dialog( {
			id : 'distributionRoleid',
			title : '分配资源',
			content : data,
			lock : false
		});
	});
}

//赋值角色
function submitCheckbox(){
	var data = {};
	data["data"] = {};
	var url = "/restful/privilegeProxyService/user/distributionResource";
	var str = "";
	$(".allresources").each(function() {
		if ($(this).attr("checked")) {
			if ($(this).val()) {
				str += "" + $(this).val() + ",";
			}
		}
	});
	str = str.substr(0, str.length - 1);
	data["data"]["userid"]=$("#userid").val();
	data["data"]["roleids"] = str;
	$.ajax({
		url : url,
		type : "post",
		data : JSON.stringify(data),
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				art.dialog({
					id : 'distributionRoleid'
				}).close();
				loadDiv("/restful/privilegeProxyService/user/userListGrid");
			} else {
				alert(data.msg);
			}
		}
	});
}




 
