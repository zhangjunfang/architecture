var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	check: {
		enable: false
	},
	callback: {
		beforeClick: function(treeId, treeNode) {
			$('#txt_name').val(treeNode.name)
		}
	}
};

$(function(){
});

function saveNodeName(){
	console.log($('#txt_name').val())
}

function loadTree(id){
	$('#txt_name').val('')
	$.ajax({
		url: '/controller/menuManage/AppMenuTree',
		type: "POST",
		dataType: "json",
		data: {
			appid: id
		}
	}).done(function(responseText) {
		for(var i=0; i<responseText.length; i++){
			var module = responseText[i];
			module.pId = module.pid;
			module.name = module.nodename;
			module.open = true;
		}
		$.fn.zTree.init($("#menuTree"), setting, responseText);
	}).complete(function(){
	});
}

function loadTree2(id){
	$('#txt_name').val('')
	$.ajax({
		url: '/controller/menuManage/CustMenuTree',
		type: "POST",
		dataType: "json",
		data: {
			custid: id
		}
	}).done(function(responseText) {
		for(var i=0; i<responseText.length; i++){
			var module = responseText[i];
			module.pId = module.pid;
			module.name = module.nodename;
			module.open = true;
		}
		$.fn.zTree.init($("#menuTree"), setting, responseText);
	}).complete(function(){
	});
}