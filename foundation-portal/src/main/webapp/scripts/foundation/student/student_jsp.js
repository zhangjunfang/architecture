jQuery.ajaxSetup({
			cache : false
		});

function loadDiv(url, params, callback) {
	$("#pageInfo").load(url + "?timestamp=" + new Date().getTime(), params,
			callback);
}

// 角色列表（包含查询）
function reloadStuList(offset) {
	// 总分页数目
	var totalpage = $("#totalpage").val();
	// 角色名称
	var stuname = $("#stuname").val();

	if (offset == 0) { // 点击go触发
		offset = $("#offset").val();
	}
	if (offset > parseInt(totalpage) && parseInt(totalpage) != 0) {
		offset = totalpage;
	}
	// 拼json参数
	var params = {
		offset : offset,
		stuname : stuname
	};
	// return;
	loadDiv("stuListGrid", params, null);
}

function addStuUI() {
	$.post('addStuUI', null, function(data) {
				$.dialog({
							id : 'addstuid',
							title : '添加学生',
							content : data,
							lock : true
						});
			});
}

function saveStu() {
	var name = $("#name").val();
	var classname = $("#classname").val();
	var no = $("#no").val();
	if (name == "") {
		alert("姓名不能为空！");
		return;
	}
	if (classname == "") {
		alert("班级不能为空！");
		return;
	}
	if (no == "") {
		alert("学号不能为空！");
		return;
	}
	var photo = $("#photo").val();
	if (!/\.(png|gif|bmp|jpg|jpeg)$/i.test(photo) && photo != "") {
		alert("请上传png、gif、bmp、jpg、jpeg格式的图片！");
		return;
	}

	$("#stuform").ajaxSubmit({
				url : "add",
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						loadDiv("/controller/student/stuListGrid", function() {
									art.dialog({
												id : 'addstuid'
											}).close();
								});
					} else {
						alert(data.msg);
					}
				}

			});
}

function editStuUI() {
	var str = "";
	var i = 0;
	$("input[name='menuids']:checked").each(function() {
				if ($(this).val()) {
					str += "" + $(this).val() + ",";
				}
				i = (++i);
			});
	if (i != 1) {
		alert("请选中一条数据!");
		return false;
	}
	str = str.substr(0, str.length - 1);

	$.ajax({
		url : "selectById",
		type : "post",
		data : {
			id : str
		},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				var __data = data;
				$.post('editStuUI', null, function(data) {
							$.dialog({
										id : 'editstuid',
										title : '修改学生',
										content : data,
										lock : true,
										init : function() {
											$("#id").val(__data.data.id);
											$("#name").val(__data.data.name);
											$("#className")
													.val(__data.data.className);
											$("#no").val(__data.data.no);
											$(":radio[name='sex'][value='"
													+ __data.data.sex + "']")
													.attr("checked", "checked");
											$("#photoimg")
													.attr(
															"src",
															"/upload/"
																	+ __data.data.photo);
											$("#birthday")
													.val(__data.data.birthday);
										}
									});
						});
			} else {
				alert(data.msg);
			}
		}
	});
}

function updateStu() {
	var name = $("#name").val();
	var classname = $("#classname").val();
	var no = $("#no").val();
	if (name == "") {
		alert("姓名不能为空！");
		return;
	}
	if (classname == "") {
		alert("班级不能为空！");
		return;
	}
	if (no == "") {
		alert("学号不能为空！");
		return;
	}
	var photo = $("#photo").val();
	if (!/\.(png|gif|bmp|jpg|jpeg)$/i.test(photo) && photo != ""
			&& photo != undefined) {
		alert("请上传png、gif、bmp、jpg、jpeg格式的图片！");
		return;
	}
	$("#stuform").ajaxSubmit({
				url : "edit",
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						loadDiv("stuListGrid", function() {
									art.dialog({
												id : 'editstuid'
											}).close();
								});
					} else {
						alert(data.msg);
					}
				}

			});
}

function deleteById() {
	var str = "";
	$(".check_box").each(function() {
				if ($(this).attr("checked")) {
					if ($(this).val()) {
						str += $(this).val() + ",";
					}
				}
			});

	if (confirm('您确认删除吗？')) {
		str = str.substr(0, str.length - 1);

		$.ajax({
					url : "delete",
					type : "post",
					data : {
						ids : str
					},
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							loadDiv("stuListGrid");
						} else {
							alert(data.msg);
						}
					}
				});
	}
}