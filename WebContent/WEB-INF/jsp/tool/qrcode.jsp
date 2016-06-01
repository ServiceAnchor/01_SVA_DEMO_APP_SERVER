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
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="icon-home" style="background-image:none"></i>
								<spring:message code="menu_tools" />
							<i class="icon-angle-right"></i>
						</li>
						<li><spring:message code="menu_customer_analyse_APK" /></li>
					</ul>
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<div class="clearfix"></div>
			<div style="width:80%;margin:auto;text-align:center; margin-top:10%">
				<div class="row-fluid">
					<div class="span12">
						<div>
							<img id="pictureid" style="">
						</div>
						<a id="url" ><font color="blue">APK download</font></a>
					</div>
				<!--  	<div class="span6">
						<div>
							<img id="iosPictureid" style="">
						</div>	
						<a id="iosurl"><font color="blue">IOS download</font></a>
					</div>	-->
				</div>
			</div>
		</div>	
	</div>	
	<!-- END PAGE -->

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
        
        <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
       
        <!-- END PAGE LEVEL SCRIPTS -->  

        <script type="text/javascript">
   
      $(document).ready(function() { 
    	$.post("/sva/down/api/generrateQrcode",function(data){
  			$("#pictureid").attr("src","/sva/upload/QRcode.jpg");
  			$("#url").attr("href","/sva/version/APK/"+data.data);
  		});
  		$.post("/sva/down/api/iosQrcode",function(data){
  			$("#iosPictureid").attr("src","/sva/upload/IOSQRcode.jpg");
  			$("#iosurl").attr("href","/sva/version/IOS/"+data.data);
  		});
         App.init();
      });
      
        </script>
   <!-- END JAVASCRIPTS -->
    </body>
</html>