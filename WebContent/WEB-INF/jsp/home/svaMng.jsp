<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
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
      <link
    href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>"
    rel="stylesheet" type="text/css" />
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
                  <li>
                     <i class="icon-home" style="background-image:none"></i>
                     <spring:message code="menu_info_manage" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="menu_info_manage_sva" /></li>
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
                   	<div id="info"></div>
                </div>
            </div>
         	<form class="form-horizontal demoform" onkeydown= "if(event.keyCode==13)return false;" action="/sva/svalist/api/saveData" method="post" >
                <div id="editBox" class="portlet light-grey hide ">
                   	<div class="portlet-title">
                        <div class="caption">
                            <i class="icon-edit icon-large"  style="background: none"></i><spring:message code="bluemix_config" />
                        </div>
                    </div>            
               		<div class="row-fluid">
                  		<div class="span6" style="padding:10px;">           
             				<div class="row-fluid">
              					<div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">
                   					<div class="control-group">
                        				<label class="control-label" for=IpId>IP</label>
                          				<div class="controls">
                           					<input type="text" datatype="ipip" errormsg='<spring:message code="sva_ip" />'  nullmsg='<spring:message code="sva_ip_input" />' style="width: 56%;height: 15%" class="input-xlarge" name="ip" id="IpId" placeholder="IP">
                          				</div>
                   					</div>
                   					<div class="control-group">
                       					<label class="control-label" for="SVAId"><spring:message code="sva_title_name" /></label>
                          				<div class="controls">
                           					<input type="text" datatype="password"  nullmsg='<spring:message code="sva_name" />' style="width: 56%;height: 15%" class="input-xlarge" name="name" id="SVAId" maxlength="50"  placeholder="<spring:message code="sva_title_name" />">
                          				</div>
                   					</div>
                   					<div class="control-group">
                                        <label class="control-label" for="tokenId"><spring:message code="sva_token" /></label>
                                        <div class="controls">
                                            <input type="text" datatype="number"  errormsg='<spring:message code="sva_port_tishi" />'   nullmsg='<spring:message code="sva_token" />' style="width: 56%;height: 15%" class="input-xlarge" name="tokenProt" id="tokenId" maxlength="20" reg="^.{0,20}$" placeholder="<spring:message code="sva_token" />">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="brokerId"><spring:message code="sva_broker" /></label>
                                        <div class="controls">
                                            <input type="text" datatype="number"  errormsg='<spring:message code="sva_port_tishi" />'   nullmsg='<spring:message code="sva_broker" />' style="width: 56%;height: 15%" class="input-xlarge" name="brokerProt" id="brokerId" maxlength="20" reg="^.{0,20}$" placeholder="<spring:message code="sva_broker" />">
                                        </div>
                                    </div>
                  				</div>
                 			</div>
                		</div>
                		<div class="span6" style="padding:10px;">
                  			<div class="row-fluid">
                      			<div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">
                      			     <div class="control-group">
                                        <label class="control-label" for="usernameId"><spring:message code="sva_title_username" /></label>
                                        <div class="controls">
                                            <input type="text" datatype="password"  nullmsg='<spring:message code="sva_user" />' style="width: 56%;height: 15%" class="input-xlarge" name="username" id="usernameId" maxlength="20" reg="^.{0,20}$" placeholder="<spring:message code="sva_title_showusername" />">
                                            <input type="hidden" style="width: 56%;height: 15%" class="input-xlarge" name="id" id="idid">
                                        </div>
                                    </div>                   
                                    <div class="control-group">
                                        <label class="control-label" for="passwordId"><spring:message code="sva_title_password" /></label>
                                        <div class="controls">
                                            <input type="password" datatype="password"  nullmsg='<spring:message code="sva_password" />' style="width: 56%;height: 15%"class="input-xlarge" name="password" id="passwordId" maxlength="20" reg="^.{0,20}$"  placeholder="<spring:message code="sva_title_showpassword" />"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label" for="SVAPosition"><spring:message code="store_add_name" /></label>
                                        <div class="controls">
                                            <select style="width: 58%;height: 15%;" datatype="*"  nullmsg='<spring:message code="map_store_name" />' name="position" id="SVAPosition" >
                                                <option value=""></option>
                                            </select> 
                                        </div>
                                    </div>                   					
                   					<div class="control-group">
                        				<label class="control-label" for="enableSel"><spring:message code="sva_title_status" /></label>
                          				<div class="controls">
                             				<select style="width: 58%;height: 15%;" datatype="*"  nullmsg='<spring:message code="sva_zhuangtai" />' name=status id="enableSel" >
                                    			<option value=""></option>
                                    			<option value="1"><spring:message code="message_swith_open" /></option>
                                    			<option value="0"><spring:message code="message_swith_close" /></option>
                             				</select>                
                          				</div>
                   					</div>
                                    <div class="control-group">
                                        <label class="control-label" for="subscriptionId"><spring:message code="sva_Subscription_type" /></label>
                                        <div class="controls">
                                            <select style="width: 58%;height: 15%;" datatype="*"  nullmsg='<spring:message code="map_store_name" />' name=type id="subscriptionId" >
                                                <option value="1"><spring:message code="sva_type_anonymization" /></option>
                                                <option value="0"><spring:message code="sva_type_noanonymization" /></option>
                                                <option value="2"><spring:message code="sva_type_Designation" /></option>
                                            </select>                
                                        </div>
                                    </div>
                  				</div>
                			</div>
               			</div>
               		</div>
                    <div class="form-actions" style="padding-left: 0px;">
                        <div class="row-fluid">
                    		<div class="span12" style="text-align:center;">
                               	<input id="confirm" style="height: 30px" type="button"  class="btn btn-success" value="<spring:message code="common_confirm" />">
                               	<button id="cancel" style="height: 30px" onclick="hideAdd()" type="button" class="btn"><spring:message code="common_cancel" /></button>
                                <span class="sameInfo" ></span>
                                <img class="hide" id="jiazai" style="width:50px;height: 30px" src="/sva/images/jiazai.gif">
                            </div>
                        </div>
                    </div>
                </div>      
         	<!-- 
         		<table class="table table-bordered">
                    <thead>
                        <tr>
                            <th class="width2">IP</th>
                            <th class="width1"><spring:message code="sva_title_name" /></th>
                            <th class="width1"><spring:message code="sva_title_username" /></th>
                            <th class="width1"><spring:message code="sva_title_password" /></th>
                            <th class="width1"><spring:message code="sva_title_status" /></th>
                            <th class="width2"><spring:message code="message_table_title_operation" /></th>
                        </tr>
                    </thead>
         			<tbody>
         				<tr>
         					<td><input type="text" style="width:90%;" name="ip" /></td>
         					<td><input type="text" style="width:90%;" name="name" /></td>
         					<td><input type="text" style="width:90%;" name="username" /></td>
         					<td><input type="text" style="width:90%;" name="password" /></td>
         					<td>
         						<select id="enableSel" style="width:90%;height:30px;" name="status">
         							<option value="-1"></option>
         							<option value="1">true</option>
         							<option value="0">false</option>
         						</select>
         					</td>
         					<td>
         						<input type="submit" value="<spring:message code="common_submit" />" class= "btn btn-primary" />
         						<div class="hide"><input type = "text" name="id" value="0"></div>
         					</td>
         				</tr>
         			</tbody>
         		</table>
         		 -->
			</form>
<div><button id="add" style="height: 30px;" type="button" class="btn btn-primary" onclick="addMap()"><spring:message code="common_add" /></button></div>                    			
<!-- 
            <table id="table" class="table table-bordered">
                  <thead>
                        <tr>
                            <th style="width:100px">IP</th>
                            <th style="width:50px"><spring:message code="sva_title_name" />123123</th>
                            <th style="width:100px"><spring:message code="sva_title_username" /></th>
                            <th style="width:100px"><spring:message code="sva_title_password" /></th>
                            <th style="width:100px"><spring:message code="sva_title_status" /></th>
                            <th style="width:100px"><spring:message code="message_table_title_operation" /></th>
                        </tr>
                 </thead>           
            </table>
 -->
                <table id="table" class="table table-bordered">
                    <thead>
                        <tr>
                            <th >IP</th>
                            <th><spring:message code="sva_title_name" /></th>
                            <th><spring:message code="map_table_title_location" /></th>
                            <th><spring:message code="sva_token" /></th>
                            <th><spring:message code="sva_broker" /></th>
                            <th><spring:message code="sva_title_username" /></th>
                            <th><spring:message code="sva_title_password" /></th> 
                            <th><spring:message code="sva_Subscription_type" /></th>
                            <th><spring:message code="sva_title_status" /></th>
                            <th><spring:message code="message_table_title_operation" /></th>
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
   <script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script> 
   <script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
   <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
   <script src="<c:url value='/js/svaMng.js'/>" type="text/javascript"></script>  
   <!-- END PAGE LEVEL SCRIPTS -->  

   <script type="text/javascript">
   var oTable,validForm;
   var i18n_disable = '<spring:message code="sva_status_disable" />',
		i18n_enable = '<spring:message code="sva_statsu_enable" />',
   		i18n_edit = '<spring:message code="common_edit" />',
   		i18n_delete = '<spring:message code="common_delete" />',
   		i18n_info = '<spring:message code="map_info" />',
   	    i18n_deleteInfo = '<spring:message code="map_delete" />',
        i18n_svaSame = '<spring:message code="map_sva_same" />';
        var i18n_open = '<spring:message code="message_swith_open" />'; 
        var i18n_close = '<spring:message code="message_swith_close" />';
        var i18n_type = '<spring:message code="sva_type_anonymization" />';
        var i18n_type1 = '<spring:message code="sva_type_noanonymization" />';
        var i18n_type2 = '<spring:message code="sva_type_Designation" />';
        var i18n_status = '<spring:message code="sva_update_status" />';
        var i18n_ip = '<spring:message code="sva_update_ip" />';
        var i18n_user = '<spring:message code="sva_name_password" />';
        var i18n_wuxiao = '<spring:message code="sva_ip_wuxiao" />';
        var info;
        var storeVal ;
      $(document).ready(function() {
          App.init();
          SvaMng.initPosition();
          SvaMng.initTable();
          validForm = $(".demoform").Validform(
             {tiptype:3,
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
                   
              }
             }
             
        		  );
          SvaMng.bindClickEvent();
      });
   </script>
   <!-- END JAVASCRIPTS -->
</body>
</html>