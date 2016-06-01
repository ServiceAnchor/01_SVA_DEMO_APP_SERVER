<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
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
<link
	href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.popuptext{
	margin-top:50px;
}
.editData{
	-webkit-user-select:text !important;
}
</style>
<!-- END PAGE LEVEL PLUGIN STYLES -->

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
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB
					<h3 class="page-title"><spring:message code="test_title" /></h3>-->
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="icon-home" style="background-image:none"></i>
							<spring:message code="ceshi_tool" /> <i
							class="icon-angle-right"></i></li>
						<li><spring:message code="position_latency1" /></li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
				<div class = "col-md-6" style="text-align: right;">
					<button type = "button" class="btn btn-primary" id="exportButton"><spring:message code="sva_daochu" /></button>
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<div class="clearfix"></div>
			<div class="tableBox">
				<table id="table" class="table table-bordered">
					<thead>
						<tr>
							<th>ID</th>
							<th><spring:message code="test_table_title_location" /></th>
							<th><spring:message code="test_table_title_floor" /></th>
							<th style="min-width:81px"><spring:message code="data_latency" /></th>
							<th style="min-width:81px"><spring:message code="position_latency" /></th>
							<th><spring:message code="shang_chuan" /></th>
						</tr>
					</thead>
				</table>
			</div>

		</div>
		<!-- END PAGE -->
	</div>
	<div class="modal hide fade" id="modal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3><spring:message code="test_popup_title" /></h3>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="span8">
					<div id="chart" class="editData" style="height:400px;"></div>
				</div>
				<div class="span4 offset1 popuptext">
					<table class="table table-bordered table-striped">
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_location" />
			                </td>
			                <td><label id="place"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_floor" />
			                </td>
			                <td><label id="floor"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_x" />
			                </td>
			                <td><label id="x"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_y" />
			                </td>
			                <td><label id="y"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_starttime" />
			                </td>
			                <td><label id="startTime"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_endtime" />
			                </td>
			                <td><label id="endTime"></label></td>
			            </tr>
			            <tr>
			                <td>
			                  	<spring:message code="test_table_title_trigger" />
			                </td>
			                <td><label id="trigger"></label></td>
			            </tr>
						<tr>
			                <td>
			                  	<spring:message code="test_table_title_offset" />
			                </td>
			                <td><label id="offset"></label></td>
			            </tr>
						<tr>
			                <td>
			                  	<spring:message code="test_table_title_variance" />
			                </td>
			                <td><label id="estimate"></label></td>
			            </tr>
						<tr>
			                <td>
			                  	<spring:message code="ping_result" />
			                </td>
			                <td><label id="result"></label></td>
			            </tr>
		            </table>
				</div>
			</div>
		</div>
		<div class="modal-footer" >
			<a href="#" class="btn"  data-dismiss="modal" aria-hidden="true"><spring:message code="common_close" /></a>
		</div>
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
	<script
		src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/locationdelay.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var oTable;
		var basePath = "<%=basePath%>";
		var i18n_detail ='<spring:message code="test_table_title_detail" />',
			i18n_chart_title = '<spring:message code="test_popup_chart_title" />',
			i18n_chart_tip = '<spring:message code="test_popup_chart_tip" />',
			i18n_minus = '<spring:message code="test_popup_chart_minus" />',
			i18n_plus = '<spring:message code="test_popup_chart_plus" />',
			i18n_offset = '<spring:message code="test_table_title_offset" />',
			i18n_var ='<spring:message code="test_table_title_variance" />',
			i18n_dataview ='<spring:message code="common_echart_dataview" />',
			i18n_saveimg ='<spring:message code="common_echart_saveimg" />',
			i18n_close ='<spring:message code="common_close" />',
			i18n_Invalid ='<spring:message code="test_table_title_failed" />',
			i18n_Valid ='<spring:message code="test_table_title_success" />',
			i18n_common_info ='<spring:message code="common_echart_info" />',
			i18n_accuracy_static ='<spring:message code="test_table_static" />',
	         i18n_daochu ='<spring:message code="sva_daochu" />',
			i18n_accuracy_dynamic ='<spring:message code="test_table_dynamic" />';
		$(document).ready(function() {
			App.init();
			Accuracy.initTable();
			$("a[data-type='detail']").live('click', function(e) {
				var data = oTable.fnGetData( this.parentNode.parentNode );
				Accuracy.initPopupText(data);
				setTimeout(function(){Accuracy.initPopupChart(data);},500);
				//Accuracy.initPopupChart(data);
			});
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>