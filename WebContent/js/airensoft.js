// 쿠키 생성
function setCookie(cName, cValue, cDay){
  var expire = new Date();
  expire.setDate(expire.getDate() + cDay);
  cookies = cName + '=' + escape(cValue) + '; path=/ '; // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
  if(typeof cDay != 'undefined') cookies += ';expires=' + expire.toGMTString() + ';';
  document.cookie = cookies;
}

// 쿠키 가져오기
function getCookie(cName) {
  cName = cName + '=';
  var cookieData = document.cookie;
  var start = cookieData.indexOf(cName);
  var cValue = '';
  if(start != -1){
	   start += cName.length;
	   var end = cookieData.indexOf(';', start);
	   if(end == -1)end = cookieData.length;
	   cValue = cookieData.substring(start, end);
  }
  return unescape(cValue);
}

// validator initalize
airenValidator = function( el, rules, callback ) {
	
	this.validator = el.validate({
		rules:rules,
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).removeClass('validation-success');
			$(element).addClass('validation-error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).removeClass('validation-error');
			$(element).addClass('validation-success');
		},
		submitHandler : function(form) {
			callback( form );
		}
	});	
};

// localization
$("#lang_kr").on("click", function(e) {
	setCookie("lang","kr");
	location.reload();
});
$("#lang_en").on("click", function(e) {
	setCookie("lang","en");
	location.reload();
});

$( function(){
var lang = getCookie("lang");
if( lang == "kr" ) {
	$("#lang_kr").addClass("active");
} else if (lang == "en" ) 
{
	$("#lang_en").addClass("active");
}
});