jQuery.ajaxSetup ({cache:false});

function loadDiv(url,params, callback){
	$("#pageInfo").load(url+"?timestamp="+new Date().getTime(), params,callback);
}

//角色列表（包含查询）
function reloadTriggerList(offset){
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
	//return;
	loadDiv("triggerListGrid",params,null);
}

function viewTrigger(){
	var $menus=$("input[name='menuids']:checked");
	if($menus.size()!=1){
		alert("请选中一条数据!");
		return false ;
	}
	var name=$($menus[0]).val();
	var group=$($menus[0]).data("group");
	$.ajax({
		url:"../job/view",
		type:"post",
		data: {name:name,group:group},
		dataType:'html',
		success:function(data){
			$.dialog( {
				id : 'viewtriggerid',
				title : '任务信息',
				content : data,
				lock : true,
				init: function(){}
			});
		}
	});
}

function removeTrigger(but, triggerName, triggerGroup){
	jQuery.ajax({
		type:"post",
		url:"../job/removeJobTrigger",
		data:{
			triggerName:triggerName,
			triggerGroup:triggerGroup
		},
		dataType:"json",
		success:function(data){
			if(data.success){
				loadDiv("triggerListGrid",function(){
					$(but).parent().parent().remove();
				});
			}else{
				alert("取消触发器失败。");
			}
		}
	});
}
function removeListener(but, listenerName){
	jQuery.ajax({
		type:"post",
		url:"../job/removeJobListener",
		data:{
			name:"$!jobBean.name",
			listenerName:listenerName
		},
		dataType:"json",
		success:function(data){
			if(data.success){
				loadDiv("triggerListGrid",function(){
					$(but).parent().parent().remove();
				});
			}else{
				alert("取消触发器失败。");
			}
		}
	});
}