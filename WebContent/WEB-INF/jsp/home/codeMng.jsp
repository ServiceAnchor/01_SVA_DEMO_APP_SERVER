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
					<spring:message code="code_title" />
               </h3>-->
               <ul class="page-breadcrumb breadcrumb">
                  <li>
                     <i class="icon-home" style="background-image:none"></i>
                     <spring:message code="ceshi_tool" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="menu_location_code" /></li>
               </ul>
               <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
         </div>
         <!-- END PAGE HEADER-->
         
         <div class="clearfix"></div>
         <div class="tableBox">
         	<form class="demoform" onkeydown= "if(event.keyCode==13)return false;" action="/sva/code/api/saveData" method="post" enctype="multipart/form-data" onsubmit="return checkCode()">
         		<table class="table table-bordered">
         			<thead>
         				<tr>
         					<th style="width:33.333333%"><spring:message code="code_table_title_name" /></th>
	         				<th style="width:33.333333%"><spring:message code="code_table_title_password" /></th>
	         				<th style="width:33.333333%"><spring:message code="code_table_title_operation" /></th>
         				</tr>
         			</thead>
         			<tbody>
         				<tr>
         					<td><input type="text"  maxlength="20" datatype="s4-20" errormsg='<spring:message code="code_length_name" />'  nullmsg='<spring:message code="code_input_name" />'  style="width:95%;margin: auto;" id = "nameId" name="name" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9\u4e00-\u9fa5]+$/gi,'')"//></td>
         					<td><input type="password" maxlength="20" datatype="password" errormsg='<spring:message code="code_length_password" />'  nullmsg='<spring:message code="code_input_password" />'  style="width:95%;margin: auto;" id = "passwordId" name="password" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9\@\#\$\%\&\*\?]+$/gi,'')"//></td>
         					<td><input type="button" id="confirm" value="<spring:message code="common_confirm" />" class= "btn btn-primary" /><span id="sameInfo" style="margin-left: 3px"></span></td>
         				</tr>
         			</tbody>
         		</table>
			</form>
			
			<table id="table" class="table table-bordered">
			</table>
			
         </div>
    
      </div>
      <!-- END PAGE -->
   </div>
   <!-- END CONTAINER -->
   <!-- BEGIN FOOTER -->
   <div class="footer">
      <div class="footer-inner">
   <%@ include file="../shared/pageFooter.jsp"%>
      </div>
      <div class="footer-tools">
         <span class="go-top">
         <i class="icon-angle-up"></i>
         </span>
      </div>
   </div>
   <!-- END FOOTER -->
   
   <%@ include file="../shared/importJs.jsp"%>
   <!-- BEGIN PAGE LEVEL SCRIPTS -->
   <script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
   <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
   <script src="<c:url value='/js/codeMng.js'/>" type="text/javascript"></script>  
   <!-- END PAGE LEVEL SCRIPTS -->  

   <script type="text/javascript">
      var i18n_delete = '<spring:message code="common_delete" />';
      var i18n_same = '<spring:message code="code_same" />';
      var i18n_info = '<spring:message code="map_info" />';
      var i18n_codedelete = '<spring:message code="map_delete" />';
      var i18n_codelength = '<spring:message code="code_length" />';
      $(document).ready(function() { 
          validForm = $(".demoform").Validform(
                  {tiptype:function(msg,o,cssctl){
                      var objtip=$("#sameInfo");
                      cssctl(objtip,o.type);
                      objtip.text(msg);
                  },
                  showAllError:true,
                   datatype:{
                        "password":function(gets,obj,curform,regxp){
                            if(gets=="")
                                {
                                return false;
                                }
                            if(gets.length<4)
                            {
                            return false;
                            }
                            
                        }
                        
                   }
                  }
                  
                       );
    	  var info = '${info}';
    	 App.init();
    	 CodeMng.initTable();
         $("input[data-type='del']").live("click",function(e){
        	 var name = $(this).data("name"),
        	 	   password = $(this).data("password");
        	 CodeMng.deleteRow(name, password);
         });
      });
   </script>
   <!-- END JAVASCRIPTS -->
</body>
</html>