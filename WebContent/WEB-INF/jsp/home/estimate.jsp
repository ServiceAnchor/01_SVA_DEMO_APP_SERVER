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
      <style type="text/css">
      input{
      margin: auto;
      }
      .icon-question-sign
{
background-position: 16px 16px;
}
      </style>
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
                     <spring:message code="ceshi_tool" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="menu_location_estimate" /></li>
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
         	<form class="form-horizontal demoform" onkeydown= "if(event.keyCode==13)return false;" action="/sva/estimate/api/saveData" method="post" >
                <div id="editBox" class="portlet light-grey hide">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="icon-edit icon-large" style="background: none"></i><spring:message code="bluemix_config" />
                        </div>
                    </div>            
               <div class="row-fluid">
                  <div class="span6" style="padding:10px;">
                      <div class="row-fluid">
                              <div></div>
                       </div>            
             <div class="row-fluid">
              <div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">
                   <div class="control-group">
                        <label class="control-label" for="placeSel"><spring:message code="store_add_name" /></label>
                          <div class="controls">
                             <select style="width: 58%;height: 15%;"  datatype="*"  nullmsg='<spring:message code="map_store_name" />'  name="placeId" id="placeSel"  >
                                <option value=""></option>
                             </select>
                          </div>
                   </div>
                   <div class="control-group">
                        <label class="control-label" for="zSel"><spring:message code="map_table_title_floor" /></label>
                          <div class="controls">
                             <select style="width: 58%;height: 15%;" datatype="*"  nullmsg='<spring:message code="map_place_name" />' name="floorNo" id="zSel"  >
                                <option value=""></option>
                             </select>
                          </div>
                   </div>
                  <div class="control-group">
                    <div class="controls">
                       <input type="hidden" name = "id" id="idid" style="width: 56%; height: 15%;">
                     </div>
                  </div>                   
                   <div class="control-group">
                        <label class="control-label" for="enableSel"><spring:message code="test_estimate_title_type" /></label>
                          <div class="controls">
                             <select style="width: 58%;height: 15%;" datatype="*"  nullmsg='<spring:message code="esitame_changjing" />' name="type" id="enableSel"  >
                                    <option value=""></option>
                                    <option value="0.25"><spring:message code="test_estimate_type_1" /></option>
                                    <option value="0.33"><spring:message code="test_estimate_type_2" /></option>
                                    <option value="0.375"><spring:message code="test_estimate_type_3" /></option>
                                    <option value="0.5"><spring:message code="test_estimate_type_4" /></option>
                             </select>
                          </div>
                   </div>
                  </div>
                 </div>
                </div>
                <div class="span6" style="">
                   <div class="row-fluid">
                      <div>&nbsp
                      </div>
                   </div>
                  <div class="row-fluid">
                      <div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">
                   <div class="control-group">
                        <label class="control-label" for="aId"><spring:message code="test_estimate_title_a" /></label>
                          <div class="controls">
                               <input type="text" datatype="n2" errormsg='<spring:message code="esitamet_changdu_zhengque" />'  nullmsg='<spring:message code="esitamet_changdu" />'  style="width: 56%;height: 15%" maxlength="10" class="input-xlarge" name="a" id="aId" placeholder="<spring:message code="test_estimate_title_a" />"
                               onchange="estimateOnkeyup(this)"// onkeyup="estimateOnkeyup(this)"///>
                          </div>
                   </div>
                   <div class="control-group">
                        <label class="control-label" for="bId"><spring:message code="test_estimate_title_b" /></label>
                          <div class="controls">
                               <input type="text" datatype="n2" errormsg='<spring:message code="esitamet_changdu_zhengque" />'  nullmsg='<spring:message code="esitamet_kuandu" />'  style="width: 56%;height: 15%"  maxlength="10" class="input-xlarge" name="b" id="bId" placeholder="<spring:message code="test_estimate_title_b" />"
                               onchange="estimateOnkeyup(this)"// onkeyup="estimateOnkeyup(this)"///>
                          </div>
                   </div>
                   <div class="control-group">
                        <label class="control-label" for="nId"><spring:message code="test_estimate_title_n" /><i class="icon-question-sign tip" data-toggle="tooltip" title="<spring:message code="test_table_title_number" />"></i></label>
                          <div class="controls">
                               <input type="text" datatype="prru" errormsg='<spring:message code="test_table_title_number" />'  nullmsg='<spring:message code="esitamet_prru" />' style="width: 56%;height: 15%"  maxlength="10" class="input-xlarge" name="n" id="nId" placeholder="<spring:message code="test_estimate_title_n" />"
                               onchange="estimateOnkeyup(this)"// onkeyup="estimateOnkeyup(this)"///>                          
                          </div>
                   </div>                   
                  </div>
                </div>
               </div>
               </div>
                    <div class="form-actions" style="padding-left: 0px;">
                        <div class="row-fluid">
                             <div class="span12" style="text-align:center;">
                               <input id="confirm" style="" type="button" value="<spring:message code="common_confirm" />" class="btn btn-success">
                               <button id="cancel" style="" onclick="hideAdd()" type="button" class="btn"><spring:message code="common_cancel" /></button>
                               <span class="sameInfo" ></span>
                              </div>
                        </div>
                     </div>
                    </div>         		
         		<!-- <table class="table table-bordered">
         			<thead>
         				<tr>
         					<th class="width2"><spring:message code="map_table_title_location" /></th>
         					<th class="width1"><spring:message code="map_table_title_floor" /></th>
	         				<th class="width1"><spring:message code="test_estimate_title_a" /></th>
	         				<th class="width1"><spring:message code="test_estimate_title_b" /></th>
	         				<th class="width1"><spring:message code="test_estimate_title_n" /><i class="icon-question-sign tip" data-toggle="tooltip" title="<spring:message code="test_table_title_number" />"></i></th>
	         				<th class="width2"><spring:message code="test_estimate_title_type" /></th>
                            <th class="width1"><spring:message code="test_table_title_variance" /></th>	         				
	         				<th class="width2"><spring:message code="message_table_title_operation" /></th>
         				</tr>
         			</thead>
         			<tbody>
         				<tr>
         					<td><select id="placeSel" style="width:90%;height:30px;margin: auto;" name="place"><option value="-1"></option></select></td>
         					<td><select id="zSel" style="width:90%;height:30px;margin: auto;" name="floorNo"><option value="-1"></option></select></td>
         					<td><input maxlength="6" type="text" style="width:90%;margin: auto;" name="a" onchange="estimateOnkeyup(this)"//  onkeyup="estimateOnkeyup(this)"// /></td>
         					<td><input maxlength="6" type="text" style="width:90%;margin: auto;" name="b" onchange="estimateOnkeyup(this)"//  onkeyup="estimateOnkeyup(this)"///></td>
         					<td><input maxlength="6" type="text" style="width:90%;margin: auto;" name="n" onchange="estimateOnkeyup1(this)"// onkeyup="estimateOnkeyup1(this)"///></td>
         					<td>
         						<select id="enableSel" style="width:90%;height:30px;margin: auto;" name="type">
         							<option value="-1"></option>
         							<option value="0.25"><spring:message code="test_estimate_type_1" /></option>
         							<option value="0.33"><spring:message code="test_estimate_type_2" /></option>
         							<option value="0.375"><spring:message code="test_estimate_type_3" /></option>
         							<option value="0.5"><spring:message code="test_estimate_type_4" /></option>
         						</select>
         					</td>
                            <td><input  type="hidden" style="width:90%;margin: auto;" name="deviation" /></td>         					
         					<td>
         						<input type="submit" value="<spring:message code="common_submit" />" class= "btn btn-primary" />
         						<div class="hide"><input type="text" value="0" name="id"></div>
         					</td>
         				</tr>
         			</tbody>
         		</table> -->
			</form>
			
   <div><button id="add" style="" type="button" class="btn btn-primary" onclick="addMap()"><spring:message code="common_add" /></button></div>                            
                <table id="table" class="table table-bordered">
                    <thead>
                        <tr>
                            <th style="width:100px"><spring:message code="map_table_title_location" /></th>
                            <th><spring:message code="map_table_title_floor" /></th>
                            <th style="width:180px"><spring:message code="test_estimate_title_type" /></th>
                            <th><spring:message code="test_estimate_title_a" /></th>
                            <th><spring:message code="test_estimate_title_b" /></th>
                            <th style="max-width: 50px"><spring:message code="test_estimate_title_n" /><i class="icon-question-sign tip" data-toggle="tooltip" title="<spring:message code="test_table_title_number" />"></i></th>
                            <th><spring:message code="test_table_title_variance" /></th>                         
                            <th style="min-width:90px;text-align: center;"><spring:message code="message_table_title_operation" /></th>
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
   <script src="<c:url value='/js/estimate.js'/>" type="text/javascript"></script>  
   <!-- END PAGE LEVEL SCRIPTS -->  

   <script type="text/javascript">
   var oTable,validForm;
   var i18n_delete = '<spring:message code="common_delete" />',
   		i18n_edit = '<spring:message code="common_edit" />',
   		i18n_info = '<spring:message code="map_info" />',
   	    i18n_deleteInfo = '<spring:message code="map_delete" />',
   	    i18n_estimate1 =  '<spring:message code="test_estimate_type_1" />',
   	    i18n_estimate2 =  '<spring:message code="test_estimate_type_2" />',
   	    i18n_estimate3 =  '<spring:message code="test_estimate_type_3" />',
   	    i18n_pRRU =  '<spring:message code="test_table_title_number" />',
   	    i18n_same =  '<spring:message code="map_samePlace" />',
   	    i18n_estimate4 =  '<spring:message code="test_estimate_type_4" />';
      $(document).ready(function() {
    	  validForm =  $(".demoform").Validform({tiptype:3,
    		  datatype:{
                  "prru":function(gets,obj,curform,regxp){
                      if(gets=="")
                      {
                      return false;
                      }
                    if(isNaN(gets))
                     {
                       return false;
                      }
                    var len = gets.split(".").length;
                    if(len>1)
                    {
                      return false;
                     }
                    if(gets<2)
                    {
                      return false;
                     }
                      
                  }
    		  }});
          var info = '${info}';
          if(info=="same")
        	  {
        	  alert(i18n_same);
        	  }
    	 App.init();
    	 Estimate.init();
    	 Estimate.bindClickEvent();
      });
   </script>
   <!-- END JAVASCRIPTS -->
</body>
</html>