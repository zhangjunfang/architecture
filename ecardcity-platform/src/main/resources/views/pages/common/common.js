
String.prototype.Trim = function() {

    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 把长度大于len的字符串 截断成 span
 *
 * @param len
 * @returns {String}
 */
String.prototype.formatStrLength = function(len) {
    var result = this;
    if (result.length > len) {
        result = "<span title='" + this + "'>";
        result += this.substring(0, len) + "...";
        result += "</span>";
    }
    return result;
};

/**
 * 是不是数字
 */
function isNumber(text) {
    var newPar = /^\d+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}

function isChineseWord(text) {
    var newPar = /^[\x00-\xff]+$/;
    if (newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}
function isPunctuation(text) {
    var newPar = /^[0-9a-zA-Z\u4E00-\u9FA5]+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}
function isEnglishWord(text) {
    var newPar = /^[a-zA-Z]+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}

/**
 * 校验是否为邮箱
 * @param text
 * @returns {Boolean}
 */
function isEmail(text){
    var newPar = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    if (!newPar.test(text)) {
        return false;
    } else {
        return true;
    }
}

function keyDownAction(method, ascCode) {
    if (ascCode == 13) {
        eval(method);
        return false;
    }
    return true;
}
