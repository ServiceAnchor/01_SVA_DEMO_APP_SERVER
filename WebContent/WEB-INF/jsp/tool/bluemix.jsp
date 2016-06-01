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
<link
	href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>"
	rel="stylesheet" type="text/css" />
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
					<spring:message code="message_title" />
               </h3>-->
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="icon-home" style="background-image: none"></i>
							<spring:message code="menu_tools" /> <i
							class="icon-angle-right"></i></li>
						<li><spring:message code="menu_tools_bluemix" /></li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			<!-- END PAGE HEADER-->

			<div class="clearfix"></div>
			<div class="tableBox">
				<div id="alertBox" class="hide">
					<div class="alert">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<div id="info1"></div>
					</div>
				</div>

				<div id="editBox" class="portlet light-grey hide">
					<div class="portlet-title">
						<div class="caption">
							<i class="icon-edit icon-large" style="background: none"></i><spring:message code="bluemix_config" />
						</div>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<div class="form-body">
							<div class="row-fluid">
								<div class="span6" style="padding:10px;">
									<div class="row-fluid">
										<div>SVA</div>
									</div>
									<div class="row-fluid">
										<div class="span12" style="outline:1px solid #E6E4E4;padding:20px 0;height: 325px;">
											<form class="form-horizontal demoform"  onkeydown= "if(event.keyCode==13)return false;">
												<div class="control-group">
													<label class="control-label" for="inputIP">IP</label>
													<div class="controls">
														<input type="text" maxlength="20" style="width: 68%" datatype="ipip" errormsg='<spring:message code="sva_ip" />'  nullmsg='<spring:message code="sva_ip_input" />' class="input-xlarge" id="inputIP" placeholder="IP">
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" for="inputSvaUser"><spring:message code="login_name" /></label>
													<div class="controls">
														<input type="text" maxlength="50" style="width: 68%" datatype="password"  nullmsg='<spring:message code="sva_user" />' class="input-xlarge" id="inputSvaUser" placeholder="<spring:message code="login_name" />">
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" for="inputSvaPassword"><spring:message code="login_password" /></label>
													<div class="controls">
														<input type="password"  maxlength="20" style="width: 68%" datatype="password"  nullmsg='<spring:message code="sva_password" />' class="input-xlarge" id="inputSvaPassword" placeholder="<spring:message code="login_password" />">
													</div>
												</div>
												<div class="control-group">
                                                    <label class="control-label" for="tokenId"><spring:message code="sva_token" /></label>
                                                    <div class="controls">
                                                        <input type="text" datatype="number" value="9001" style="width: 68%" errormsg='<spring:message code="sva_port_tishi" />'   nullmsg='<spring:message code="sva_token" />' style="width: 56%;height: 15%" class="input-xlarge" name="tokenProt" id="tokenId" maxlength="20" reg="^.{0,20}$" placeholder="<spring:message code="sva_token" />">
                                                    </div>
                                                 </div>
                                                 <div class="control-group">
                                                     <label class="control-label" for="brokerId"><spring:message code="sva_broker" /></label>
                                                     <div class="controls">
                                                         <input type="text" datatype="number" value="4703" style="width: 68%"  errormsg='<spring:message code="sva_port_tishi" />'   nullmsg='<spring:message code="sva_broker" />' style="width: 56%;height: 15%" class="input-xlarge" name="brokerProt" id="brokerId" maxlength="20" reg="^.{0,20}$" placeholder="<spring:message code="sva_broker" />">
                                                     </div>
                                                 </div>
												<div class="hide">
													<input id="inputId" type="text">
												</div>
											</form>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="span6" style="padding:10px;">
									<div class="row-fluid">
										<div>IBM Bluemix</div>
									</div>
									<div class="row-fluid">
										<div class="span12" style="outline:1px solid #E6E4E4;padding:20px 0;height: 325px;">
											<form class="form-horizontal demoform"  onkeydown= "if(event.keyCode==13)return false;">
												<div class="control-group">
													<label class="control-label" for="inputUrl"><spring:message code="bluemix_url" /></label>
													<div class="controls">
														<input type="text" maxlength="300" style="width: 68%" datatype="password"  nullmsg='<spring:message code="blue_dizhi" />' class="input-xlarge" id="inputUrl" placeholder="<spring:message code="bluemix_url" />">
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" for="inputSite"><spring:message code="bluemix_site" /></label>
													<div class="controls">
														<input type="text" maxlength="50" style="width: 68%" datatype="tszf"  nullmsg='<spring:message code="blue_zhandian" />' class="input-xlarge" id="inputSite" placeholder="<spring:message code="bluemix_site" />">
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" for="inputIbmUser"><spring:message code="login_name" /></label>
													<div class="controls">
														<input type="text" maxlength="50" style="width: 68%" datatype="password"  nullmsg='<spring:message code="sva_user" />' class="input-xlarge" id="inputIbmUser" placeholder="<spring:message code="login_name" />">
													</div>
												</div>
												<div class="control-group">
													<label class="control-label" for="inputIbmPassword"><spring:message code="login_password" /></label>
													<div class="controls">
														<input type="password" maxlength="20" style="width: 68%" datatype="password"  nullmsg='<spring:message code="sva_password" />' class="input-xlarge" id="inputIbmPassword" placeholder="<spring:message code="login_password" />">
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
						</div>
						<div class="form-actions">
							<div class="row-fluid">
								<div class="span12" style="text-align:center;">
									<button id="confirm" type="button" class="btn btn-success"><spring:message code="common_confirm" /></button>
									<button id="cancel" type="button" class="btn"><spring:message code="common_cancel" /></button>
								    <span class="sameInfo" ></span>
								</div>
							</div>
						</div>
						<!-- END FORM-->
					</div>
				</div>
				<div><button id="add" type="button" class="btn btn-primary"><spring:message code="common_add" /></button></div>
				<table id="table" class="table table-bordered">
					<thead>
						<tr>
							<th >ID</th>
							<th >SVA IP</th>
							<th >SVA <spring:message code="login_name" /></th>
							<th >SVA <spring:message code="login_password" /></th>
							<th ><spring:message code="sva_token" /></th>
                            <th ><spring:message code="sva_broker" /></th>
							<th >Bluemix <spring:message code="bluemix_url" /></th>
							<th >Bluemix <spring:message code="bluemix_site" /></th>
							<th >Bluemix <spring:message code="login_name" /></th>
							<th >Bluemix <spring:message code="login_password" /></th>
							<th ><spring:message code="sva_title_status" /></th>
							<th > <spring:message code="bluemix_updateTime" /></th>
							<th ><spring:message code="bluemix_createTime" /></th>
							<th style="min-width:155px"><spring:message code="map_table_title_operation" /></th>
						</tr>
					</thead>
				</table>

			</div>

		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!-- END FOOTER -->

	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script
		src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/util.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/bluemix.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var i18n_edit = '<spring:message code="common_edit" />', 
			i18n_delete = '<spring:message code="common_delete" />',
			i18n_disable = '<spring:message code="sva_status_disable" />',
			i18n_enable = '<spring:message code="sva_statsu_enable" />',
			i18n_open = '<spring:message code="message_swith_open" />', 
			i18n_close = '<spring:message code="message_swith_close" />',
			i18n_InputInfo = '<spring:message code="message_util_inputinfo" />',
			i18n_utilIp = '<spring:message code="message_util_utilip" />'
			i18n_deleteInfo = '<spring:message code="map_delete" />';
		var validForm;	
				
		var oTable;
		$(document).ready(function() {
			App.init();
			Bluemix.initTable();
			Bluemix.bindClickEvent();
		    validForm = $(".demoform").Validform({tiptype:3,
	              datatype:{
	                  "ipip":function(gets,obj,curform,regxp){
	                      var regIp =/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
	                      var ip = gets.split(".");
	                      if(!gets.match(regIp)){
	                          return false;
	                      }
	                      for(var i = 0 ;i<ip.length;i++)
	                          {
	                          if (ip[i]>255) 
	                          {
	                            return false;
	                           }
	                        }
	                      },
	                      "password":function(gets,obj,curform,regxp){
	                          if(gets=="")
	                              {
	                              return false;
	                              }
	                          
	                      },
	                      "number":function(gets,obj,curform,regxp){
	                          if(gets=="")
	                              {
	                              return false;
	                              }
	                          if(isNaN(gets))
	                          {
	                          return false;
	                          }
	                          var nu = gets.split(".");
	                          if(nu.length>1)
	                              {
	                              return false;
	                              }
	                          if(gets>65535||gets<0)
	                          {
	                          return false;
	                          }
	                          
	                      }
	              }});
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>