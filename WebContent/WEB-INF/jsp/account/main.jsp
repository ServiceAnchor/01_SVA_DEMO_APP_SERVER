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
			body{font-size:12px; font-family:"Microsoft YaHei";}
			.tab{width:100%; height:100%;}
			.tab ul{width:100%; height:44px; background:none; margin:0; padding:0;}
			.tab ul li{list-style:none; width:82px; height:42px; line-height:42px; border-top:2px solid #fff; border-right:4px solid #fff; border-bottom:0; float:left; text-align:center; font-size:14px; font-weight:bold; color:#fff;}
			.tab .tabContent{clear:left;}
			.tab .tabContent div{width:100%; height:354px; border:0; display:none; padding:25px 30px; box-sizing:border-box; -moz-box-sizing:border-box;}
			.tab .tabContent div.active{display:block;}
		</style>
	</head>


	<body>
		<div class="container">
    		<div class="row-fluid top">
				<a><img src="<c:url value='/images/login/huawei.png'/>"></a>
			    <img class="topline" src="<c:url value='/images/login/line.png'/>">
			    <img src="<c:url value='/images/login/SVA APP demo.png'/>">
				<a href="/sva/home/changeLocal?local=zh" style="float:right; font-size:14px;">中文</a> | 
				<a href="/sva/home/changeLocal?local=en" style="float:right; font-size:14px; margin-right: 13px;">English</a>
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
	  		</div>
	  		<div class="row-fluid midbox">
	  			<div class="tab">
					<ul>
						<li>URL</li>
						<li>Web</li>
						<li>Android</li>
						<li>Ios</li>
					</ul>
					<div class="tabContent">
						<div class="active">
							<table  class="table table-bordered"">
								<thead>
									<tr>
										<th><spring:message code="port" /></th>
										<th><spring:message code="url" /></th>
										<th><spring:message code="version_no" /></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>8084</td>
										<td>
										<a href="http://223.202.102.66:8084/sva/home/showDown">http://223.202.102.66:8084/sva/home/showDown
										</a>
										</td>
										<td>versionA</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div>
							<table  class="table table-bordered"">
								<thead>
									<tr>
										<th><spring:message code="version_name" /></th>
										<th><spring:message code="test_report" /></th>
										<th><spring:message code="explain_for_use" /></th>
										<th><spring:message code="download" /></th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
						<div>
							<table  class="table table-bordered"">
								<thead>
									<tr>
										<th><spring:message code="version_name" /></th>
										<th><spring:message code="test_report" /></th>
										<th><spring:message code="explain_for_use" /></th>
										<th><spring:message code="two_dimension_code" /></th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
						<div>
							<table  class="table table-bordered"">
								<thead>
									<tr>
										<th><spring:message code="version_name" /></th>
										<th><spring:message code="test_report" /></th>
										<th><spring:message code="explain_for_use" /></th>
										<th><spring:message code="two_dimension_code" /></th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
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
				//启动轮播
				$('.carousel').carousel({
					interval: 3000
				});
				
				$(".tab ul li:eq(0), .tab .tabContent div:eq(0)").css("background","#F68B6D");
				$(".tab ul li:eq(1), .tab .tabContent div:eq(1)").css("background","#FFB863");
				$(".tab ul li:eq(2), .tab .tabContent div:eq(2)").css("background","#96C2E2");
				$(".tab ul li:eq(3), .tab .tabContent div:eq(3)").css("background","#68CB4E");
				$(".tab ul li").click(function(){
					var _index = $(this).index();
					$(".tab .tabContent div").eq(_index).show().siblings().hide();
				});
			});
		</script>    
	</body>
</html>
