/*
  # 描述：时间格式化
  # @param date Date 时间
  # @param format String 格式
  # @return 格式化时间
  # eg:
  # "yyyy-MM-DD HH:mm:ss.S"
  # "yyyy-MM-DD hh:mm:ss.S"
  # "yyyy-MM-DD hh:mm:ss"
  */
var dateFormat = function(date, fmt) {
    var k, o, v;

    o = {
      "M+": date.getMonth() + 1,
      "d+": date.getDate(),
      "h+": date.getHours() % 12 === 0 ? 12 : date.getHours() % 12,
      "H+": date.getHours(),
      "m+": date.getMinutes(),
      "s+": date.getSeconds(),
      "q+": Math.floor((date.getMonth() + 3) / 3),
      "S": date.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (k in o) {
      v = o[k];
      if (new RegExp("(" + k + ")").test(fmt)) {
        fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
      }
    }
    return fmt;
  };
  
  function HtmlDecode2(str) {
		var str1 = str.replace(/&lt;/g,"<");
		var str2 = str1.replace(/&gt;/g,">");
		var str3 = str2.replace(/&amp;/g,"&");
		var str4 = str3.replace(/&quot;/g,"\"");
		var str5 = str4.replace(/&apos;/g,"\'");
	    return str5;
	}
	function HtmlDecode3(str) {
		var str1 = str.replace(/</g,"&lt;");
		var str2 = str1.replace(/>/g,"&gt;");
		return str2;
	}