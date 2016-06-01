<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SVA APP demo</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">

<%@ include file="../shared/importCss.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<link href="<c:url value='/css/pages/heatmap.css'/>" rel="stylesheet"
	type="text/css" />
<link href="<c:url value='/css/pages/statistic.css'/>" rel="stylesheet"
    type="text/css" />	
<link href="<c:url value='/plugins/jquery-chosen/chosen.css'/>"
	rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGIN STYLES -->
<style type="text/css">
.countInfo {
	width: 120px;
	height: 30px;
	background: rgba(255,255,255,0.6); 
	z-index: 10;
	filter: alpha(opacity = 30);
	opacity: 0.5;
	padding: 0 5px;
	padding-top: 5px;
	border-radius: 10px;
	color:#15A4FA;
	border:1px solid #D6D2D2;
	margin-bottom:10px;
}
.legend-area {
	background: #DCFAFF; padding: 10px; outline: black solid 2px; right: 0px; bottom: 0px; line-height: 1em; position: absolute;
}
#min {
	float: left;
}
#max {
	float: right;
}
.tip {
	background: rgba(0, 0, 0, 0.8); padding: 5px; left: 0px; top: 0px; color: white; line-height: 18px; font-size: 14px; display: none; position: absolute;
}
.demo-wrapper {
	position: relative;
}
</style>

<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">

	<%@ include file="../shared/pageHeader.jsp"%>

	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">

		<%@ include file="../shared/sidebarMenu.jsp"%>

		<!-- BEGIN PAGE -->
		<div class="page-content">

			<!-- BEGIN PAGE HEADER-->
			<div>
				<div>
					<!-- BEGIN PAGE TITLE & BREADCRUMB
               <h3 class="page-title">
					<spring:message code="heatmap_title" />
               </h3>-->
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="icon-home" style="background-image: none"></i>
							Home <i
							class="icon-angle-right"></i></li>
						<li><spring:message code="global_overview" /></li>
						<div style="float: right;">             
						    <select id="marketSel" data-placeholder="<spring:message code="heatmap_place" />">
                            </select>
                        </div>

					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<div class="clearfix"></div>
			<div class="row-fluid">
			<form class="demoform">
				<div class="pull-right">
				</div>
				</form>
			</div>
			<div class="chartArea hide">

                <div class="infoArea">

                    <div class="infoBox fleft">

                        <div class="infoTop"><spring:message code="global_overview_number" /></div>

                        <div style="font-size: 25px"  class="infoNumber" id="total"></div>

                    </div>

                    <div class="infoBox">

                        <div class="infoTop"><spring:message code="global_overview_allnumber" /></div>

                        <div style="font-size: 25px"  class="infoNumber" id="maxDay"></div>

                    </div>

                    <div class="infoBox fright">

                        <div class="infoTop"><spring:message code="global_overview_bijiao" /></div>

                        <div  style="font-size: 25px"  class="infoNumber" id="maxTime"></div>

                    </div>

                </div>
            <div class="chartBox" style="margin-bottom: 10px;width: 98%;float: left;">
                <div class="titleStyle" style="background-color: red"><spring:message code="global_overview_pictrue" /></div>
                    <div>
                        <div id="chart" style="height:600px"></div>
                    </div>
            </div>
            <div class="chartBox" style="margin-bottom: 10px;width: 98%;float: left;">
                <div class="titleStyle" style="background-color: red"><spring:message code="overview_title" /></div>
                    <div>
                        <div id="chart1" style="height:600px"></div>
                    </div>
            </div>            
            </div>
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">   <%@ include file="../shared/pageFooter.jsp"%></div>
		<div class="footer-tools">
			<span class="go-top"> <i class="icon-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->

	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/plugins/heatmap.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/overView.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var i18n_info = '<spring:message code="map_info" />';
		var i18n_info_title = '<spring:message code="global_overview_pictrue" />',
        i18n_dataview = '<spring:message code="common_echart_dataview" />',
        i18n_close = '<spring:message code="common_close" />',
        i18n_refresh = '<spring:message code="common_refresh" />',
        i18n_saveimg = '<spring:message code="common_echart_saveimg" />';
		jQuery(document).ready(function() {
			var validForm = $(".demoform").Validform({tiptype:3});
			App.init();
			Heatmap.initDropdown();
			Heatmap.bindClickEvent();
			//Heatmap.initHeatmap();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>