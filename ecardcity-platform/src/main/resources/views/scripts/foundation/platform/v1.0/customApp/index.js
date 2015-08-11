var setting = {
	data : {
		simpleData : {
			enable : true
		}
	},
	check : {
		enable : true
	}
//	,
//	callback: {
//		beforeClick: function(treeId, treeNode) {
//			console.log(arguments)
//		}
//	}
};

function loadTree() {
	$.ajax({
		url : '/controller/app/customTree',
		dataType : "json",
		type : "POST"
	}).done(function(dataJson) {
		for(var i=0; i<dataJson.length; i++){
			var module = dataJson[i];
			module.open = true;
		}
		$.fn.zTree.init($("#cusTree"), setting, dataJson);
		disabledNode()
	}).complete(function() {
	});
}
function disabledNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("cusTree");
	var lv0 = zTree.getNodesByParam('level', 0);
	for ( var i = 0; i < lv0.length; i++) {
		lv0[i].nocheck = true;
		zTree.updateNode(lv0[i]);
	}
	var lv2 = zTree.getNodesByParam('level', 2);
	for ( var i = 0; i < lv2.length; i++) {
		lv2[i].nocheck = true;
		zTree.updateNode(lv2[i]);
	}
}
