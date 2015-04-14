jQuery.ajaxSetup ({cache:false});

function loadDiv(url,params, callback){
	$("#pageInfo").load(url+"?timestamp="+new Date().getTime(), params,callback);
}

//角色列表（包含查询）
function reloadJobList(offset){
	//总分页数目
	var totalpage = $("#totalpage").val();
	//角色名称
	var jobName = $("#jobName").val();

	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	//拼json参数
	var params = {offset:offset,jobName:jobName};
	//return;
	loadDiv("jobListGrid",params,null);
}

function viewJob(){
	var $menus=$("input[name='menuids']:checked");
	if($menus.size()!=1){
		alert("请选中一条数据!");
		return false ;
	}
	var name=$($menus[0]).val();
	var group=$($menus[0]).data("group");
	$.ajax({
		url:"view",
		type:"post",
		data: {name:name,group:group},
		dataType:'html',
		success:function(data){
			$.dialog( {
				id : 'viewjobid',
				title : '任务信息',
				content : data,
				lock : true,
				init: function(){}
			});
		}
	});
}

function addJobUI(){
	$.post('addJobUI', null,
			function(data) {
				$.dialog( {
					id : 'addjobid',
					title : '添加任务',
					content : data,
					lock : true
				});
			});
}

function saveJob(){
	var name=$("#name").val();
	var group=$("#group").val();
	var jobClass=$("#jobClass").val();
	var description=$("#description").val();
	var recovery=$("#recovery").attr("checked")=="checked";
	var durable=$("#durable").attr("checked")=="checked";
	if(name==null||name==""){
		alert("任务名不能为空！");
		return;
	}
	if(group==null||group==""){
		alert("分组不能为空！");
		return;
	}
	if(jobClass==null||jobClass==""){
		alert("任务(类名)不能为空！");
		return;
	}
	var parameters={};
	$("#parameters .parameter_name").each(function(){
		var key=$.trim($(this).val());
		var val=$.trim($(this).parent().next().children(".parameter_val:first").val());
		if(key!=null&&key!=""&&val!=null&&val!=""){
			parameters[key]=val;
		}
	});
	$.ajax({
		url:"add",
		type:"post",
		data: {
			name:name,
			group:group,
			jobClass:jobClass,
			description:description,
			recovery:recovery,
			durable:durable,
			parameters:JSON.stringify(parameters)
		},
		dataType:'json',
		success:function(data){
			if(data.success){
				loadDiv("jobListGrid",function(){
					art.dialog({id:'addjobid'}).close();
				});
			}else{
				alert(data.msg);
			}
		}
	});
}
function editJobUI(){
	var $menus=$("input[name='menuids']:checked");
	if($menus.size()!=1){
		alert("请选中一条数据!");
		return false ;
	}
	var name=$($menus[0]).val();
	var group=$($menus[0]).data("group");
	$.ajax({
		url:"editJobUI",
		type:"post",
		data: {name:name,group:group},
		dataType:'html',
		success:function(data){
			$.dialog( {
				id : 'editjobid',
				title : '任务信息',
				content : data,
				lock : true,
				init: function(){}
			});
		}
	});
}
function updateJob(){
	var name=$("#name").val();
	var group=$("#group").val();
	var jobClass=$("#jobClass").val();
	var description=$("#description").val();
	var recovery=$("#recovery").attr("checked")=="checked";
	var durable=$("#durable").attr("checked")=="checked";
	if(name==null||name==""){
		alert("任务名不能为空！");
		return;
	}
	if(group==null||group==""){
		alert("分组不能为空！");
		return;
	}
	if(jobClass==null||jobClass==""){
		alert("任务(类名)不能为空！");
		return;
	}
	var parameters={};
	$("#parameters .parameter_name").each(function(){
		var key=$.trim($(this).val());
		var val=$.trim($(this).parent().next().children(".parameter_val:first").val());
		if(key!=null&&key!=""&&val!=null&&val!=""){
			parameters[key]=val;
		}
	});
	$.ajax({
		url:"add",
		type:"post",
		data: {
			name:name,
			group:group,
			jobClass:jobClass,
			description:description,
			recovery:recovery,
			durable:durable,
			parameters:JSON.stringify(parameters)
		},
		dataType:'json',
		success:function(data){
			if(data.success){
				loadDiv("jobListGrid",function(){
					art.dialog({id:'editjobid'}).close();
				});
			}else{
				alert(data.msg);
			}
		}
	});
}
function deleteById(){
	var str = "";
	$(".check_box").each(function() {
		if ($(this).attr("checked")) {
			if ($(this).val()) {
				str += $(this).val() + ",";
			}
		}
	});
	
	var $menus=$("input[name='menuids']:checked");
	if($menus.size()<1){
		alert("请选择要删除的记录!");
		return;
	}
	var data=new Array();
	$menus.each(function(){
		var name=$(this).val();
		var group=$(this).data("group");
		var job={'name':name,'group':group};
		data.push(job);
	});
	
	if (confirm('您确认删除吗？')) {
		str = str.substr(0, str.length - 1);

		$.ajax( {
			url : "delete",
			type : "post",
			data : {ids:JSON.stringify(data)},
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					loadDiv("jobListGrid");
				} else {
					alert(data.msg);
				}
			}
		});
	}
}
function executeOnce(){
	var $menus=$("input[name='menuids']:checked");
	if($menus.size()!=1){
		alert("请选中一条数据!");
		return false ;
	}
	var name=$($menus[0]).val();
	var group=$($menus[0]).data("group");
	$.ajax({
		url : "executeOnce",
		type : "post",
		data : {name:name,group:group},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				alert("执行成功！");
			} else {
				alert(data.msg);
			}
		}
	});
}
function executeSimpleUI(){
	var $menus=$("input[name='menuids']:checked");
	if($menus.size()!=1){
		alert("请选中一条数据!");
		return false ;
	}
	var name=$($menus[0]).val();
	var group=$($menus[0]).data("group");
	$.ajax({
		url:"executeSimpleUI",
		type:"post",
		data: {name:name,group:group},
		dataType:'html',
		success:function(data){
			$.dialog( {
				id : 'executeSimpleUI',
				title : '简单调度',
				content : data,
				lock : true,
				init: function(){}
			});
		}
	});
}
function executeSimple(){
	var name=$("#name").val();
	var group=$("#group").val();
	var triggerGroup=$("#triggerGroup").val();
	var triggerName=$("#triggerName").val();
	var description=$("#description").val();
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var repeatCount=$("#repeatCount").val();
	var repeatInterval=$("#repeatInterval").val();
	
	if(triggerGroup==""){
		alert("触发器分组不能为空！");
		return;
	}
	if(triggerName==""){
		alert("触发器名不能为空！");
		return;
	}
	
	if(!(/^\d+$/.test(repeatCount)&&parseInt(repeatCount)>0)){
		alert("重复次数只能为大于0的整数！");
		return;
	}
	if(!(/^\d+$/.test(repeatInterval)||parseInt(repeatInterval)>0)){
		alert("间隔时间只能为大于0的整数！");
		return;
	}
	
	$("#executeSimpleForm").ajaxSubmit({
		url:"executeSimple",
		dataType:'json',
		success:function(data){
			if(data.success){
				art.dialog({id:'executeSimpleUI'}).close();
			}else{
				alert(data.msg);
			}
		}
	});
}
function executeCronUI(){
	var $menus=$("input[name='menuids']:checked");
	if($menus.size()!=1){
		alert("请选中一条数据!");
		return false ;
	}
	var name=$($menus[0]).val();
	var group=$($menus[0]).data("group");
	$.ajax({
		url:"executeCronUI",
		type:"post",
		data: {name:name,group:group},
		dataType:'html',
		success:function(data){
			$.dialog( {
				id : 'executeCronUI',
				title : '高级调度',
				content : data,
				lock : true,
				init: function(){}
			});
		}
	});
}
function executeCron(){
	var name=$("#name").val();
	var group=$("#group").val();
	var triggerGroup=$("#triggerGroup").val();
	var triggerName=$("#triggerName").val();
	var description=$("#description").val();
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();

	var cron=$("#cron").val();
	
	if(triggerGroup==""){
		alert("触发器分组不能为空！");
		return;
	}
	if(triggerName==""){
		alert("触发器名不能为空！");
		return;
	}
	
	if(cron==""){
		alert("cron表达式不能为空！");
		return;
	}
	
	
	$("#executeCronForm").ajaxSubmit({
		url:"executeCron",
		dataType:'json',
		success:function(data){
			if(data.success){
				art.dialog({id:'executeCronUI'}).close();
			}else{
				alert(data.msg);
			}
		}
	});
}
function createCronUI(){
	$.dialog.open("createCronUI",{
		id : 'createCronUI',
		title : '高级调度',
		lock : true,
		width: '616px',
	    height: '433px',
	});
}

function removeTrigger(but, triggerName, triggerGroup){
	jQuery.ajax({
		type:"post",
		url:"removeJobTrigger",
		data:{
			triggerName:triggerName,
			triggerGroup:triggerGroup
		},
		dataType:"json",
		success:function(data){
			if(data.success){
				$(but).parent().parent().remove();
			}else{
				alert("取消触发器失败。");
			}
		}
	});
}
function removeListener(but, listenerName){
	jQuery.ajax({
		type:"post",
		url:"removeJobListener",
		data:{
			name:"$!jobBean.name",
			listenerName:listenerName
		},
		dataType:"json",
		success:function(data){
			if(data.success){
				$(but).parent().parent().remove();
			}else{
				alert("取消触发器失败。");
			}
		}
	});
}