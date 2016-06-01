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
	<style type="text/css">
    .demo-wrapper {
		position: relative;
	}
	
	div.point {
		position: absolute;
		background-color: #0CD4F7;
		border-radius: 3px;
		width: 6px;
		height: 6px;
	}
	
	div.svgCotainer {
		position: absolute;
	}
	
	#mapPloy {
		position: absolute;
	}
    </style>

	<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body id="body" class="page-header-fixed" >

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
						<li><spring:message code="menu_customer_analyse_area" /></li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<div class="r-top row-fluid">
			<form class="demoform">
				<div class="span2">
					<select id="areaName" name="areaName" datatype="*"  nullmsg='<spring:message code="area_store_name" />'
						placeholder="<spring:message code="heatmap_place" />"
						style="width: 100%;height:26px;padding:2px 0 2px 0;">
						<option value=""></option>
					</select>
				</div>
				<div class="span2">
					<div class="input-append">
						<input id="select_time_begin_tab1"datatype="*"  nullmsg='<spring:message code="all_choose_starttime" />'
						 style="width: 155px" readonly/> 
						<span class="add-on" onclick="Rangemap.showDate('select_time_begin_tab1');">
							<i class="icon-calendar"></i>
						</span>
					</div>
				</div>
				<div class="span3">
					<div class="input-append">
						<input id="select_time_end_tab1" datatype="*"  nullmsg='<spring:message code="all_choose_endtime" />'
						 style="width: 155px" readonly/> 
						<span class="add-on" onclick="Rangemap.showDate('select_time_end_tab1');">
							<i class="icon-calendar"></i>
						</span>
					</div>
				</div>
				
				<div class="span1">
					<input id="confirm" type="button" value="<spring:message code="common_confirm" />">
				</div>
				<span  id="msgdemo2"></span>  
				</form>
			</div>	
			<div class="chartArea hide">
				<div class="chartBox">
					<div>
						<div id="chart" style="height:500px"></div>
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
	<div class="modal hide fade" id="myModal1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<div style="padding-top: 20px;">
				<spring:message code="map_choose_posistion" />
				<button class="btn btn-small clearPaper"
					style="float: right; margin-right: 40px;"><spring:message code="common_clear" /></button>
			</div>
		</div>
		<div class="modal-body">
			<div id="alertBoxScale" class="hide">
				<div class="alert fade in">
					<div id="infoScale"></div>
				</div>
			</div>
			<div id="divCon" style="text-align: center; height: 500px;">
				<div id="preview" class="demo-wrapper"
					style="outline: 1px solid #B3B1B1;">
				</div>
			</div>
		</div>
		
	</div>
	<!-- END FOOTER -->

	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value='/plugins/raphael-min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/mapEdit.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/plugins/moment/moment.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/plugins/underscore/underscore.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/mapEdit.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/wDatePicker/WdatePicker.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/areamap.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var i18n_delete = '<spring:message code="common_delete" />';
		i18n_info = '<spring:message code="map_info" />',
		i18n_time = '<spring:message code="linemap_time" />',
		i18n_coordinate = '<spring:message code="Coordinate" />',
		i18n_title = '<spring:message code="rangemap_chart_title" />',
		i18n_tag = '<spring:message code="linemap_chart_tag" />', 
		i18n_person = '<spring:message code="linemap_chart_person" />',
		i18n_max = '<spring:message code="linemap_chart_max" />', 
		i18n_min = '<spring:message code="linemap_chart_min" />', 
		i18n_avg = '<spring:message code="linemap_chart_avg" />',
		i18n_dataview = '<spring:message code="common_echart_dataview" />',
		i18n_close = '<spring:message code="common_close" />',
		i18n_refresh = '<spring:message code="common_refresh" />',
		i18n_saveimg = '<spring:message code="common_echart_saveimg" />',
		i18n_language = '<spring:message code="time_language" />',
		i18n_error = '<spring:message code="rangemap_error_info" />';	
		i18n_choose_title = '<spring:message code="map_choose_posistion_alert" />',
		$(document).ready(function() {
	        $(".demoform").Validform({       
	                  tiptype:function(msg,o,cssctl){
	                  var objtip=$("#msgdemo2");
	                  cssctl(objtip,o.type);
	                  objtip.text(msg);
	              },tipSweep:true});
			App.init();
			Rangemap.initDropdown();
			Rangemap.bindClickEvent();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>