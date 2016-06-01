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
    
    </style>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body id="body" class="page-header-fixed">
   
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
                     <spring:message code="menu_tools" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="down_version_downversion" /></li>
               </ul>
               <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
         </div>
         <!-- END PAGE HEADER-->
         
         <div class="clearfix"></div>
         <div class="tableBox">
            <table id="table" class="table table-bordered">
                  <thead>
                        <tr>
                            <th><spring:message code="down_version_name" /></th>
                            <th><spring:message code="down_version_time" /></th>
                            <th style="width:250px"><spring:message code="message_table_title_operation" /></th>
                        </tr>
                 </thead>           
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
        <script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script> 
        <script src="<c:url value='/plugins/raphael-min.js'/>"
        type="text/javascript"></script>
        <script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/js/down.js'/>" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->  

        <script type="text/javascript">
   var oTable;
   var i18n_down= '<spring:message code="down_version_down" />';
      $(document).ready(function() { 
         App.init();
         MsgMng.initMsgTable();
      });
        </script>
   <!-- END JAVASCRIPTS -->
    </body>
</html>