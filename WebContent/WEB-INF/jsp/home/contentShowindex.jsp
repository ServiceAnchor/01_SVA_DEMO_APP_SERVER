<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if IE 11]> <html lang="en" class="ie11 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Small Cell City</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<link href="<c:url value='../plugins/html/css/minnew.css'/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/pages/heatmap.css'/>" rel="stylesheet"
	type="text/css" />
<script src="<c:url value='../plugins/html/js/jquery-2.1.3.min.js'/>"></script>
<script src="<c:url value='../plugins/html/js/swiper.min.js'/>"></script>
<script src="<c:url value='../plugins/html/js/json.js'/>"></script>
<script src="<c:url value='/js/appcontent.js'/>"></script>
<!-- <script src="<c:url value='../plugins/html/js/appcontent.js'/>"></script>-->
<script src="<c:url value='/js/contentshowindex.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='../plugins/heatmap.min.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='../plugins/underscore/underscore-min.js'/>"
	type="text/javascript"></script>
</head>
<body>
<!-- Swiper -->
<div class="swiper-container">
  <div class="swiper-wrapper">
    <div class="swiper-slide" id="huawei">
      <div class="slide-box">
       <!--<header>SERVICE ANCHOR:<span> DIGITAL FOOTPRINT ANALYSIS SOLUTION</span></header>-->
       <header>&nbsp;</header>
       <!-- 
       <div class="numbern-data-list">
        <ul>
         <li><span id="numbern-data-item1"></span><span class="numbern-data-item">Current Visitor Number</span></li>
         <li><span id="numbern-data-item2"></span><span class="numbern-data-item">Cumulative Visitor Number</span></li>
         <li><span id="numbern-data-item3"></span><span class="numbern-data-item">Average Visit Time(Min)</span></li>
        </ul>
       </div>
        -->
       <div class="clear"></div>

       <div class="heatmap-slide1">
        <h2><spring:message code="hangzhou_a_1f"/></h2>
        <div id="heatmap-slide1-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap" class="heatmap"></div>
        </div>
       </div>
       <div class="heatmap-slide2">
        <h2><spring:message code="hangzhou_a_2f"/></h2>
        <div id="heatmap-slide2-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap2" class="heatmap2"></div>
        </div>
       </div> 
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 615px" id="number1"></h3>
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;" id="number2"></h3>
               <h3 style=" margin: 0% 0% 0% 0%;width: 615px;text-align: center;float: left;"> <spring:message code="hangzhou_visiter"/></h3>
               <h3 style=" margin: 0% 0% 0% 50%;width: 615px;text-align: center;"> <spring:message code="hangzhou_visiter"/></h3>       
       <div class="heatmap-slide3">

        <h2><spring:message code="hangzhou_b_f"/></h2>
        <div id="heatmap-slide3-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap3" class="heatmap3"></div>
        </div>
       </div> 
       <div class="heatmap-slide4">
       <h2><spring:message code="hangzhou_b_1f"/></h2>
        <div id="heatmap-slide4-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap4" class="heatmap4"></div>
        </div>
       </div>
       <div class="heatmap-slide5">
        <h2><spring:message code="hangzhou_b_2f"/></h2>
        <div id="heatmap-slide5-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap5" class="heatmap5"></div>
        </div>
       </div> 
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px" id="number3"></h3>
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px" id="number4"></h3>
               <h3 style=" margin: 0% 0% 0% 66%;text-align: center;width: 410px" id="number5"></h3>
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px"> <spring:message code="hangzhou_visiter"/></h3>
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px"> <spring:message code="hangzhou_visiter"/></h3>                
               <h3 style=" margin: 0% 0% 0% 66%;text-align: center;width: 410px"> <spring:message code="hangzhou_visiter"/></h3>        
       <div class="heatmap-slide6">
        <h2><spring:message code="hangzhou_b_3f"/></h2>
        <div id="heatmap-slide6-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap6" class="heatmap6"></div>
        </div>
       </div>
       <div class="heatmap-slide7">
        <h2><spring:message code="hangzhou_b_4f"/></h2>
        <div id="heatmap-slide7-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap7" class="heatmap7"></div>
        </div>
       </div>  
       <div class="heatmap-slide8">
        <h2><spring:message code="hangzhou_b_5f"/></h2>
        <div id="heatmap-slide8-graphic">
          <!--  热力图大小： width:1006px; height:630px;  -->
          <div id="heatmap8" class="heatmap8"></div>
        </div>
       
       </div>  
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px" id="number6"></h3>
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px" id="number7"></h3>
               <h3 style=" margin: 0% 0% 0% 66%;text-align: center;width: 410px" id="number8"></h3>
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px"><spring:message code="hangzhou_visiter"/></h3>
               <h3 style=" margin: 0% 0% 0% 0%;text-align: center;float: left;width: 410px"> <spring:message code="hangzhou_visiter"/></h3>                
               <h3 style=" margin: 0% 0% 0% 66%;text-align: center;width: 410px;margin-bottom: 17px"><spring:message code="hangzhou_visiter"/></h3>                                                                       
       <div class="bar-slide1">
        <div class="bar-slide1-title">
         <ul>
          <li><h3 style="margin: auto;"><spring:message code="hangzhou_booth"/></h3></li>
          <li><h3 style="margin: auto;"><spring:message code="hangzhou_visiter1"/></h3></li>
          <li><h3 style="margin: auto;"><spring:message code="hangzhou_visiterall"/></h3></li>
          <li><h3 style="margin: auto;"><spring:message code="hangzhou_visitertime"/></h3></li>
         </ul>
        </div>
        <div class="bar-slide1-list" id="bar-slide1-list">
        </div>
    </div>
       </div>
    </div>
    <!--  
	<div class="swiper-slide" id="vdf">
		<div class="slide-box">
			<header>&nbsp;</header>
			<div class="numbern-data-list" id="numbern-data-list-slide2">
				<ul>
	 				<li><span id="numbern-data-item1-slide2"></span><span class="numbern-data-item">Current Visitor Number</span></li>
					<li><span id="numbern-data-item2-slide2"></span><span class="numbern-data-item">Cumulative Visitor Number</span></li>
					<li><span id="numbern-data-item3-slide2"></span><span class="numbern-data-item">Average Visit Time(Min)</span></li>
				</ul>
			</div>
			<div class="heatmap-slide2">
				<div id="heatmap-slide2-graphic">
					<div id="heatmap2"></div>
				</div>
			</div>
			<div class="slide-box-tip slide-box-tip-slide3"><span class="slide-box-tip-title-v">Hall 3</span><span class="slide-box-tip-title-h">Vodafone Exhibition Hall</span></div>
		</div>
	</div>
	<div class="swiper-slide" id="other">
		<div class="slide-box">
			<header>&nbsp;</header>
			<div class="numbern-data-list" id="numbern-data-list-slide3">
				<ul>
					<li><span id="numbern-data-item1-slide3"></span><span class="numbern-data-item">Current Visitor Number</span></li>
					<li><span id="numbern-data-item2-slide3"></span><span class="numbern-data-item">Cumulative Visitor Number</span></li>
					<li><span id="numbern-data-item3-slide3"></span><span class="numbern-data-item">Average Visit Time(Min)</span></li>
				</ul>
			</div>
			<div class="heatmap-slide3">
				<div id="heatmap-slide3-graphic">
					<div id="heatmap3"></div>
					</div>
				</div>
			<div class="slide-box-tip slide-box-tip-slide3"><span class="slide-box-tip-title-v">Hall 6</span><span class="slide-box-tip-title-h">Other Exhibition Hall</span></div>
		</div>
	</div>
-->
</div>
  <!-- Add Pagination -->
  <div class="swiper-pagination"></div>
</div>
<script type="text/javascript">
		var configObj = {
			container : document.getElementById("heatmap"),
			maxOpacity : .6,
			radius : 20,
			blur : .90,
			backgroundColor : 'rgba(0, 0, 58, 0.1)'
		};
		var configObj2 = {
			container : document.getElementById("heatmap2"),
			maxOpacity : .6,
			radius : 20,
			blur : .90,
			backgroundColor : 'rgba(0, 0, 58, 0.1)'
		};
		var configObj3 = {
			container : document.getElementById("heatmap3"),
			maxOpacity : .6,
			radius : 20,
			blur : .90,
			backgroundColor : 'rgba(0, 0, 58, 0.1)'
		};
        var configObj4 = {
                container : document.getElementById("heatmap4"),
                maxOpacity : .6,
                radius : 20,
                blur : .90,
                backgroundColor : 'rgba(0, 0, 58, 0.1)'
            };
        var configObj5 = {
                container : document.getElementById("heatmap5"),
                maxOpacity : .6,
                radius : 20,
                blur : .90,
                backgroundColor : 'rgba(0, 0, 58, 0.1)'
            };
        var configObj6 = {
                container : document.getElementById("heatmap6"),
                maxOpacity : .6,
                radius : 20,
                blur : .90,
                backgroundColor : 'rgba(0, 0, 58, 0.1)'
            };
        var configObj7 = {
                container : document.getElementById("heatmap7"),
                maxOpacity : .6,
                radius : 20,
                blur : .90,
                backgroundColor : 'rgba(0, 0, 58, 0.1)'
            }; 
        var configObj8 = {
                container : document.getElementById("heatmap8"),
                maxOpacity : .6,
                radius : 20,
                blur : .90,
                backgroundColor : 'rgba(0, 0, 58, 0.1)'
            };         
		var legendCanvas = document.createElement('canvas');
		legendCanvas.width = 100;
		legendCanvas.height = 10;
		var gradientImg = document.querySelector('#gradient');

		var legendCtx = legendCanvas.getContext('2d');
		var gradientCfg = {};

		var demoWrapper = document.querySelector('.demo-wrapper');
		var tooltip = document.querySelector('.tip');
		jQuery(document).ready(function() {
			// App.init();
			Heatmap.initDropdown();
		});
	</script>
</body>
</html>