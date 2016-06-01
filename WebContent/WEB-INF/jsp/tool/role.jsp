<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if IE 11]> <html lang="en" class="ie11 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>SVA APP demo</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	<%@ include file="../shared/importCss.jsp"%>
	<link rel="shortcut icon" href="favicon.ico" />
	<link href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>"
	rel="stylesheet" type="text/css" />
	
	<link href="<c:url value='/plugins/jquery-chosen/chosen.css'/>"
	rel="stylesheet" type="text/css" />
</head>
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
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="icon-home" style="background-image:none"></i>
								<spring:message code="menu_info_manage" />
							<i class="icon-angle-right"></i>
						</li>
						<li><spring:message code="role_manage" /></li>
					</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="tableBox">
				<div id="alertBox" class="hide">
					<div class="alert">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<div id="info"></div>
					</div>
				</div>
				<form class="demoform" onkeydown= "if(event.keyCode==13)return false;">
					<div id="editBox" class="portlet light-grey hide ">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-edit icon-large"  style="background: none"></i><spring:message code="role_info" />
							</div>
						</div>
						<div class="row-fluid form-horizontal">
							<div class="span6" style="outline:0px solid #E6E4E4;padding:20px 0;">
								<div class="control-group">
									<label class="control-label" for="roleName"><spring:message code="account_name" /></label>
									<div class="controls">
										<input type="text" nullmsg='<spring:message code="account_input" />'  maxlength="20" datatype="password" errormsg="input error"  style="width: 59%;height: 15%;" name="roleName" id="roleName"  >
									</div>
								</div>
						<!--  	<div class="control-group">
									<label class="control-label" for="menuAccount"><spring:message code="menu_name" /></label>
									<div class="controls">
										<select class="chzn-select" datatype="*"  data-placeholder="<spring:message code="menu_input" />" multiple nullmsg='<spring:message code="menu_input" />' errormsg="input error" style="width: 59%;height: 15%;" name="menuAccount" id="menuAccount" >
											<option value=""></option>
										</select>
									</div>
								</div>-->
								<div class="control-group">
									<label class="control-label" for="menuAccount"><spring:message code="menu_name" /></label>
									<div class="controls"> 
										<table id="menuAccount" style="width:967px" class="table table-bordered">
										</table>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="storeAccount"><spring:message code="store_add_name" /></label>
									<div class="controls"> 
										<table id="storeAccount"  style="width:967px" class="table table-bordered">
										</table>
									</div>
								</div>
								
								<div class="control-group">
									<div class="controls">
										<input type="hidden" name = "id" id="idid" style="width: 56%; height: 15%;">
									</div>
								</div>
							</div>
						</div>
						<div class="form-actions" style="padding-left: 0px;">
							<div class="row-fluid">
								<div class="span12" style="text-align:center;">
									<button id="confirm" style="height: 30px" type="button" class="btn btn-success"><spring:message code="common_confirm" /></button>
									<button id="cancel" style="height: 30px" type="button" class="btn"><spring:message code="common_cancel" /></button>
									<span class="sameInfo" ></span>
									<input type="hidden" id="id">
								</div>
							</div>
						</div>
					</div>
				</form> 
				<div>
					<button id="add" style="height: 30px;" type="button" class="btn btn-primary" ><spring:message code="common_add" /></button>
				</div>
				<table id="table" class="table table-bordered">
					<thead>
						<tr>
							<th></th>
							<th><spring:message code="role_name" /></th>
							<th><spring:message code="menu_account" /></th>
							<th><spring:message code="store_account" /></th>
							<th><spring:message code="bluemix_updateTime" /></th>
							<th><spring:message code="code_table_title_operation" /></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="../shared/pageFooter.jsp"%>
	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script> 
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/role.js'/>" type="text/javascript"></script>  
	<script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='../plugins/underscore/underscore-min.js'/>"type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->  
	<script type="text/javascript">
		var oTable;
		var i18n_edit = '<spring:message code="common_edit" />',
			i18n_delete = '<spring:message code="common_delete" />',
			i18n_deleteInfo = '<spring:message code="map_delete" />',
			i18n_inputInfo = '<spring:message code="message_util_inputinfo" />';
			i18n_roleSame =  '<spring:message code="role_sameName" />';
			i18n_storeinput = '<spring:message code="role_roleInput" />';
			i18n_selmenu = '<spring:message code="menu_input" />';
			i18n_selstore = '<spring:message code="map_store_name" />';
			var pa = '<spring:message code="common_pass" />';
	$(document).ready(function() { 	
		$(".chzn-select").chosen();
		App.init();
		role.initTable();
		role.bindClickEvent();
		var demo = $(".demoform").Validform({tiptype:3,
			datatype:{
				"password":function(gets,obj,curform,regxp){
				if(gets=="")
				{
					 return false;
				}
			}}});
	});
   </script>
   <!-- END JAVASCRIPTS -->
</body>
</html>