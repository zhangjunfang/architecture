function divTag(name, className1, className2, value, flag) {
	var nameContent = name + "Content";
	var obj = document.getElementsByName(name);
	var div = document.getElementsByName(nameContent);
	var view = function() {
		for (var i = 1; i <= obj.length; i++) {
			if (i == value) {
				if (flag)
					obj[i - 1].className = className1 + "_" + i;
				else
					obj[i - 1].className = className1;
				div[i - 1].style.display = "";
			} else {
				if (flag)
					obj[i - 1].className = className2 + "_" + i;
				else
					obj[i - 1].className = className2;
				div[i - 1].style.display = "none";
			}
		}
	}

	var timeout = setTimeout(view, 300);

	obj[value - 1].onmouseout = function() {
		clearTimeout(timeout);
	}
}
