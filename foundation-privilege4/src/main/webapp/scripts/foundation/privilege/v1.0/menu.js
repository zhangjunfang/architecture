jQuery.ajaxSetup ({cache:false});

function loadDiv(url,params, callback){
	$("#pageInfo").load(url+"?timestamp="+new Date().getTime(), params,callback);
}


//角色列表（包含查询）
function reloadMenuList(offset){
	//总分页数目
	var totalpage = $("#totalpage").val();
	//角色名称
	var menuname = $("#menuname").val();
	var status = $("#status").val();
	var typeid = $("#typeid").val();

	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	//拼json参数
	var params = {offset:offset,name:menuname,status:status,typeid:typeid};
	//return;
	loadDiv("/restful/privilegeProxyService/menu/menuListGrid",params,null);
}

function reloadMenuList_clickTree(parent_id){
	//拼json参数
	var params = {parent_id:"'"+parent_id+"'"};
	//return;
	loadDiv("/restful/privilegeProxyService/menu/menuListGrid",params,null);
}

//全选
function selectAll(obj){
	var str = "";
	if($(obj).attr("checked")){
		$("input[type='checkbox']").attr("checked", true);
		$(".selectFocus").each(function(){
			if($(this).val()){
				str += "'"+ $(this).val() +"',";
			}
		});
		str = str.substr(0,str.length-1);
		$("#ids").val(str);
	}else{
		$("input[type='checkbox']").attr("checked", false);
	}
}


//选择其中一行
function selectFocus(obj){
	var b = true;
	$(".selectFocus").each(function(){
		if(!$(this).attr("checked")){
			b = false;
			return false;
		}
	});
	if(b){
		$("input[type='checkbox']").attr("checked", true);
	}else{
		$("input[type='checkbox']:first").attr("checked", false);
		$("input[type='checkbox']:last").attr("checked", false);
	}
}

var deleteById = function(){
	//var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	//var nodes = treeObj.getSelectedNodes();
	//if(nodes.length != 1){
		//alert("请选中一个树节点");
		//return;
	//}
	//var node = nodes[0];
	//if(node.isParent){ }
	var str = "";
	var isParent = [];
	$(".check_box").each(function() {
		if ($(this).attr("checked")) {
			if ($(this).val()) {
				str += "'" + $(this).val() + "',";
				isParent.push($(this).attr("isparent"));
			}
		}
	});

	if(str == ""){
		alert("请选择菜单！");
		return;
	}

	if(isParent.indexOf("true") != -1){
		alert("选中菜单中有子级，请先删除子级！");
		return;
	}

	if (confirm('您确认删除菜单吗？')) {
		var data = {};
		data["data"] = {};
		str = str.substr(0, str.length - 1);
		data["data"]["menuids"] = str;

		$.ajax( {
			url : "/restful/privilegeProxyService/menu/delete",
			type : "post",
			data : JSON.stringify(data),
			contentType : "application/json",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					loadDiv("/restful/privilegeProxyService/menu/menuListGrid");
				} else {
					alert(data.msg);
				}
			}
		});
	}
}

//添加部门UI
function addMenuUI() {
	if($("#tree_id").val() == -1){
		alert("请选择菜单树节点！");
		return;
	}
	$.post('/restful/privilegeProxyService/menu/addMenuUI', null,
			function(data) {
				$.dialog( {
					id : 'addmenuid',
					title : '添加菜单',
					content : data,
					lock : true
				});
			});
}

var saveMenu = function(){

	/*var data = $("#dataDatail").serialize();
	var data1 = {"data":data};

	var dataStr = JSON.stringify(data1);

	console.log(data);
	console.log(data1);
	console.log(dataStr);*/

	//console.log($("#dataDatail").serializeJson())

	var name = $("#name").val().trim();
	if(name == ""){
		alert("菜单名称不能为空！");
		return;
	}

	var data = $("#dataDatail").serializeObjectForm();
	data.parentid = $("#tree_id").val();
	var __data = JSON.stringify(data);
	//console.log(__data);
	//return;

	var url = "/restful/privilegeProxyService/menu/add" ;
	$.ajax({
		url:url,
		type:"post",
		data: __data,
		dataType:'json',
		success:function(data){
			if(data.success){
				loadDiv("/restful/privilegeProxyService/menu/menuListGrid",function(){
					art.dialog({id:'addmenuid'}).close();
				});
			}else{
				alert(data.msg);
			}
		}
	});
}

//修改角色UI
function editMenuUI() {
	var str = "";
	var i=0;
	$("input[name='menuids']:checked").each(function() {
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

	$.ajax({
		url:"/restful/privilegeProxyService/menu/selectById",
		type:"post",
		data: "{\'id\':"+ str +"}",
		dataType:'json',
		success:function(data){
			if(data.success){
				var __data = data;
				$.post('/restful/privilegeProxyService/menu/editMenuUI', {
					"menuid" : str
				}, function(data) {
					$.dialog( {
						id : 'updatemenuid',
						title : '修改菜单',
						content : data,
						lock : true,
						init: function(){
							$("#edit_name").val(__data.data.name);
							$("#edit_sortby").val(__data.data.sortby);
							$("#edit_url").val(__data.data.url);
							$("#edit_icon").val(__data.data.icon);
							$("#edit_isParent").val(__data.data.isParent);
						}
					});
				});
			}else{
				alert(data.msg);
			}
		}
	});


}

var editMenu = function(){

	/*var data = $("#dataDatail").serialize();
	var data1 = {"data":data};

	var dataStr = JSON.stringify(data1);

	console.log(data);
	console.log(data1);
	console.log(dataStr);*/

	//console.log($("#dataDatail").serializeJson())

	var name = $("#edit_name").val().trim();
	if(name == ""){
		alert("菜单名称不能为空！");
		return;
	}


	var str = "";
	$("input[name='menuids']:checked").each(function() {
			if ($(this).val()) {
				str = $(this).val();
			}
	});
	var data = $("#dataDatail").serializeObjectForm();
	data.id = str;
	var __data = JSON.stringify(data);
	//console.log(__data);
	//return;

	var url = "/restful/privilegeProxyService/menu/edit" ;
	$.ajax({
		url:url,
		type:"post",
		data: __data,
		dataType:'json',
		success:function(data){
			if(data.success){
				loadDiv("/restful/privilegeProxyService/menu/menuListGrid",function(){
					art.dialog({id:'updatemenuid'}).close();
				});
			}else{
				alert(data.msg);
			}
		}
	});
}

