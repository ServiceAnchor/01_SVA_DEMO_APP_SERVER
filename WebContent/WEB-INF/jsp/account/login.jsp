<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="../shared/taglib.jsp"%>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
	<head>
		<%@ include file="../shared/importCss.jsp"%>
		<meta charset="utf-8">
		<title>SVA APP demo</title>
		<style>
			body{ background-color:#e9e9e9 !important;}
			.top{ overflow:hidden; margin-top:15px}
			.top a img{ float:left}
			.top p{ float:right; line-height:0px}
			.toptext{ font-size:48px; color:#274f70; margin:1em 0;}
			.topline { margin-left:25px; margin-right:25px;}
			
			.midbox{margin:0 auto; height:358px; background-color:#FFF;overflow:hidden; position:relative; margin-top:40px}
			.banner{margin-top:10px;}
			.banner img { height:338px;}
			
			.loginbox{ width:290px; height:298px; background-image:url(../images/login/login_background.png); r; position:absolute; top:30px; right:20px}
			.login{ margin-top:25px; margin-left:36px}
			.login *{font-size:14px; color:#3e3e3e;}
			.login p{  line-height:0px; margin-top:20px}
			.name,.password{ width:180px; height:31px; border:#699dc3 solid 1px; padding-left:30px !important}
			.loginimg{ position:absolute; margin: 6px;}
			.checkbox{margin-top:4px;}
			.button{width: 218px;height: 40px;border-radius: 6px;cursor:pointer; margin-top:20px}
			
			.bottom{ position:absolute; bottom:10px; width: 940px;}
			.bottom p{font-size:14px; color:#3e3e3e;}
			
			@media( max-width:1024px){				
			
			.loginbox{ 
				width:80%; 
				height:298px; 
				background-color:#83c4f4; 
				position:absolute; 
				top:30px; 
				left:6%; 
				background-image:none
			}
			.login{ margin-top:25px; margin-left:36px}
			.login *{font-size:14px; color:#3e3e3e;}
			.login p{  line-height:0px; margin-top:20px}
			.name,.password{ width:75%; height:31px; border:#699dc3 solid 1px; padding-left:30px}
			.loginimg{ position:absolute; margin: 8px;}
			.button{width:75%;height: 40px;border-radius: 6px;cursor:pointer; margin-top:20px; margin-left:15px}
			#myCarousel{ display:none}
			.midbox{ background-color:#e9e9e9}
			.toptext{ display:none}
			.top{ margin-left:60px}
			
				}
		</style>
	</head>


	<body>
		<div class="container">
    		<div class="row-fluid top">
				<a><img src="<c:url value='/images/login/huawei.png'/>"></a>
			    <img class="topline" src="<c:url value='/images/login/line.png'/>">
			    <img src="<c:url value='/images/login/SVA APP demo.png'/>">
			    <p class="toptext"><spring:message code="login_title" /></p>
		    </div>

  
			<div class="row-fluid midbox">
	 	 		<div class="bannerbox">
					<div id="myCarousel" class="carousel slide banner">
						<ol class="carousel-indicators">
							<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
	    					<li data-target="#myCarousel" data-slide-to="1"></li>
							<li data-target="#myCarousel" data-slide-to="2"></li>
							<li data-target="#myCarousel" data-slide-to="3"></li>
						</ol>
						<!-- Carousel items -->
						<div class="carousel-inner">
							<div class="active item">
								<img src="<spring:message code="sva_login_1" />">
							</div>
							<div class="item">
								<img src="<spring:message code="sva_login_2" />">
							</div>
							<div class="item">
								<img src="<spring:message code="sva_login_3" />">
							</div>
							<div class="item">
								<img src="<spring:message code="sva_login_4" />">
							</div>
						</div>
					</div>
	     		</div>
				<div class="loginbox">
			     	<div class="login">
			     		<form method="post" id="loginForm">
			     			<div class="row-fluid">
			     				<div class="span12">
									<p><spring:message code="login_name" />：</p>
			     				</div>
			     			</div>
			     			<div class="row-fluid">
			     				<div class="span12">
									<img class="loginimg" src="<c:url value='/images/login/name.png'/>">
									<input class="name" type="text" id="username" name="username" placeholder="<spring:message code="login_name" />">
			     				</div>
			     			</div>
			     			<div class="row-fluid">
			     				<div class="span12">
									<p><spring:message code="login_password" />：</p>
			     				</div>
			     			</div>
			     			<div class="row-fluid">
			     				<div class="span12">
									<img class="loginimg" src="<c:url value='/images/login/password.png'/>">
									<input class="password" type="password" name="password" placeholder="<spring:message code="login_password" />">
			     				</div>
			     			</div>
			     			<div class="row-fluid">
			     				<div class="span6">
			     					<label class="checkbox">
										<input type="checkbox" id="rememberMe" name="remember" value="1"/><spring:message code="login_remember" />
									</label>
			     				</div>
			     				<div class="span6">
									<a href="/sva/home/changeLocal?local=zh">中文</a> | 
									<a href="/sva/home/changeLocal?local=en" style="margin-right: 13px;">English</a>
								</div>
			     			</div>
			     			<div class="row-fluid">
			     				<div class="span12">
			     					<button id="loginid" type="submit" class="button"><spring:message code="login_go" /></button>
			     				</div>
			     				<span id="infomation" style="color: red;margin-left: 10%;"></span>
			     			</div>
			     		</form>
			     	</div>
			     </div>  
	  		</div>
			<div class="row-fluid">
				<div class="span12">
					<div class="bottom">
						<%@ include file="../shared/loginFooter.jsp"%>
					</div>
				</div>
			</div>
		</div>
  
		<%@ include file="../shared/importJs.jsp"%>
		<script type="text/javascript">
			$(function() {
				var info = "${info}";
				var login_info = '<spring:message code="login_infomation" />';
				var login_null = '<spring:message code="login_tishi_null" />';
				//var login_admin = '<spring:message code="login_admin" />';
				// var login_word = '<spring:message code="login_word" />';
				//App.init();
				$("#infomation").text("");
				if(info=="error"){
					$("#infomation").text(login_info);
				}
				if(info=="null"){
					$("#infomation").text(login_null);
				}
				//AccountValidate.handleLogin({username:login_admin, password:login_word});
				if($.cookie('sva_userName')!=undefined){  
				$("#rememberMe").attr("checked", true);  
				}else{  
					$("#rememberMe").attr("checked", false);  
				}
				//读取cookie  
				if($('#rememberMe:checked').length>0){  
					$('#username').val($.cookie('sva_userName'));   
				}
				//启动轮播
				$('.carousel').carousel({
					interval: 3000
				});
				//监听【记住我】事件  
				$("#rememberMe").click(function(){  
					if($('#rememberMe:checked').length>0){//设置cookie  
						$.cookie('sva_userName', $('#username').val());  
					}else{//清除cookie  
						$.removeCookie('sva_userName');
					}
				}); 
				$("#loginid").click(function(){  
	                if($('#rememberMe:checked').length>0&&$("input[name='username']").val()!=""&&$("input[name='password']").val()!=""){//设置cookie  
	                    $.cookie('sva_userName', $('#username').val());  
	                }	                
	            });
			});
		</script>    
	</body>
</html>
