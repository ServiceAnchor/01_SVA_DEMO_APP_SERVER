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
<link href="<c:url value='/css/pages/statistic.css'/>" rel="stylesheet"
	type="text/css" />

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
						<spring:message code="menu_customer_analyse_linemap" />
					</h3>-->
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="icon-home" style="background-image: none"></i>
							<spring:message code="menu_customer_analyse" /> <i
							class="icon-angle-right"></i></li>
						<li><spring:message code="history_area_title" /></li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<div class="r-top row-fluid">
			<form class="demoform">
				<div class="span3">
					<select id="marketSel" name = "marketSelName"  datatype="*"  nullmsg='<spring:message code="map_store_name" />'
						data-placeholder="<spring:message code="heatmap_place" />"
						style="width: 100%;height:26px;padding:2px 0 2px 0;">
						<option value=""></option>
					</select>
				</div>
				<div class="span6">
					<div class="input-append">
						<input id="select_time_begin_tab1" datatype="*"  nullmsg='<spring:message code="all_choose_starttime" />' style="width: 180px" readonly/> 
						<span class="add-on" onclick="Linemap.showDate('select_time_begin_tab1');">
							<i class="icon-calendar"></i>
						</span>
					</div>
					-
					<div class="input-append">
						<input id="select_time_end_tab1" datatype="*"  nullmsg='<spring:message code="all_choose_endtime" />' style="width: 180px" readonly/> 
						<span class="add-on" onclick="Linemap.showDate('select_time_end_tab1');"> 
							<i class="icon-calendar"></i>
						</span>
					</div>
				</div>
				<div class="span1">
					<input type="button" value="<spring:message code="common_confirm" />" id="confirm" style="vertical-align: top;">
				</div>
				<span  id="msgdemo2"></span>
			</form>	
			</div>
			<div class="chartArea hide">

				<div class="infoArea">

					<div class="infoBox fleft">

						<div class="infoTop"><spring:message code="linemap_info_total" /></div>

						<div style="font-size: 25px"  class="infoNumber" id="total"></div>

					</div>

					<div class="infoBox">

						<div class="infoTop"><spring:message code="linemap_info_maxDay" /></div>

						<div style="font-size: 25px"  class="infoNumber" id="maxDay"></div>

					</div>

					<div class="infoBox fright">

						<div class="infoTop"><spring:message code="linemap_info_maxTime" /></div>

						<div  style="font-size: 25px"  class="infoNumber" id="maxTime"></div>

					</div>

				</div>
			<div class="chartBox" style="margin-bottom: 10px">
				<div class="titleStyle"><spring:message code="linemap_chart_title" /></div>
					<div class="btn-group"  data-toggle="buttons-radio">
						<button id="hour" class="btn active" value="1"><spring:message code="linemap_info_hour" /></button>
						<button id="day" class="btn" value="2"><spring:message code="linemap_info_day" /></button>
					</div>
					<div>
						<div id="chart" style="height:400px"></div>
					</div>
			</div>
            <div class="chartBox" style="margin-bottom: 10px">
                 <div class="titleStyle"><spring:message code="menu_customer_analyse_barmap" /></div>
                    <div>
                        <div id="chart1" style="height:400px;text-align: center;"></div>
                    </div>
            </div>
            <div class="chartBox" style="margin-bottom: 10px">
                <div class="titleStyle"><spring:message code="menu_customer_analyse_rangemap" /></div>
                    <div class="btn-group"  data-toggle="buttons-radio">
                        <button id="hour1" class="btn active" value="1"><spring:message code="linemap_info_hour" /></button>
                        <button id="day1" class="btn" value="2"><spring:message code="linemap_info_day" /></button>
                    </div>
                    <div>
                        <div id="chart2" style="height:400px"></div>
                    </div>
            </div>
            <div class="chartBox" style="margin-bottom: 10px">
                <div class="titleStyle"><spring:message code="linemap_visitTime_title" /></div>
                    <div>
                        <div id="chart3" style="height:400px"></div>
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
	<script
		src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/plugins/wDatePicker/WdatePicker.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/linemap.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var i18n_delete = '<spring:message code="common_delete" />',
        i18n_info = '<spring:message code="map_info" />',
        i18n_time = '<spring:message code="linemap_time" />',
        i18n_title = '<spring:message code="linemap_chart_title" />', 
        i18n_title1 = '<spring:message code="barmap_chart_title" />',
        i18n_tag = '<spring:message code="linemap_chart_tag" />',
        i18n_person = '<spring:message code="linemap_chart_person" />', 
        i18n_max = '<spring:message code="linemap_chart_max" />', 
        i18n_min = '<spring:message code="linemap_chart_min" />', 
        i18n_avg = '<spring:message code="linemap_chart_avg" />',
        i18n_dataview = '<spring:message code="common_echart_dataview" />',
        i18n_close = '<spring:message code="common_close" />',
        i18n_refresh = '<spring:message code="common_refresh" />',
        i18n_saveimg = '<spring:message code="common_echart_saveimg" />',
        i18n_visitTime = '<spring:message code="linemap_visitTime" />',
        i18n_visitTitle = '<spring:message code="linemap_visitTime_title" />',
        i18n_language = '<spring:message code="time_language" />';  
		$(document).ready(function() {
		       $(".demoform").Validform({       
				tiptype:function(msg,o,cssctl){
	            var objtip=$("#msgdemo2");
	            cssctl(objtip,o.type);
	            objtip.text(msg);
	        },tipSweep:true});
			App.init();
			Linemap.initDropdown();
			Linemap.bindClickEvent();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>