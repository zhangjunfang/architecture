/*
 * 
 *公共调用js验证包
 *
 */
function checkNullStr(obj, msg) {
	var el_name = document.getElementById(obj);	
	if (Trim(el_name.value).length==0){
		alert(msg);
		el_name.focus();
		return true;
	}
	return	false;
}

/**验证密码是否重复*/
function isSame(objOne,objTwo,msg){
	
	var one = document.getElementById(objOne);
	var two = document.getElementById(objTwo);
	
	if(one.value!=two.value){
		alert(msg);		
		one.focus();
		return true;
	}
	
	return false;
}

/**检验邮件是否合法  /^([a-z0-9][a-z0-9_\-\.]+)@([a-z0-9][a-z0-9\.\-]{0,20})\.([a-z]{2,4})$/  */
function checkEmail(obj,msg){
	var el_name = document.getElementById(obj);
	var temp = el_name.value;
	if(Trim(temp).length==0){
		return ;
	}
	var flag = /^([a-z0-9][a-z0-9_\-\.]+)@([a-z0-9][a-z0-9\.\-]{0,20})\.([a-z]{2,4})$/i.test(temp);
	if(!flag){
		alert(msg);
		el_name.value='';
		el_name.focus();
	}
}


/*
 * 右边不能为空
 */
function LTrim(str) {
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1) {
        var j=0, i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1) {
           j++;
        }
        s = s.substring(j, i);
    }
    return s;
}

/*
 * 右边不能为空
 */
function RTrim(str) {
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length-1)) != -1) {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) {
            i--;
        }
        s = s.substring(0, i+1);
    }
    return s;
}

function Trim(str) {
    return RTrim(LTrim(str));
}