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

//uk
function authUK(){
		$('#customername').val('新开普电子公司');
		$('#customerunitcode').val('08600000001');
}

function validateForm(){
	if($("#customername").val()==""){
		alert("请认证客户名!");
		return true;
	}
	if($("#empcode").val()==""){
		alert("请输入用户名！");
		return true;
	} 
	if($("#emppwd").val()==""){
		alert("请输入密码！");
		return true;
	} 
}
function userLogin() {
	if(!validateForm()){
		var str="";
		var data = $("#dataDatail").serializeObjectForm();
		var url = "login";
		jQuery.ajax( {
			url : url,
			type : "post",
			data : data,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					alert(data.msg);
					//loadDiv("pageInfo","userListGrid");
					window.location.href="/controller/user/userList";
				} else {
					alert(data.msg);
				}
			}
		});
	}
}



