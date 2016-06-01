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
                     <spring:message code="menu_tools" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li>Ping</li>
               </ul>
               <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
         </div>
         <!-- END PAGE HEADER-->
         
         <div class="clearfix"></div>
         <div>
         	<div style="margin-bottom:20px;font-size: 15px;">
         	<form class="demoform">
         		<spring:message code="sva_title_name"/>:<select datatype="*"  nullmsg='<spring:message code="map_store_name" />'  style="height: 30px;margin-bottom: 9px;" id="svaList" >
          			<option value=""></option>
				</select> 
				<input type="text" datatype="cishu"  nullmsg='<spring:message code="map_store_name" />' style="width:80px"class="input-xlarge" id="pingnumber" value="4"  placeholder=<spring:message code="heatmap_Connection_count"/>>
				<input type="text" datatype="daxiao"  nullmsg='<spring:message code="map_store_name" />' style="width:80px"class="input-xlarge" id="packtsize"   value="56"  placeholder=<spring:message code="heatmap_package_size"/>>
				<input type="text" datatype="time"  nullmsg='<spring:message code="map_store_name" />' style="width:80px"class="input-xlarge" id="timeout"     value="2000"  placeholder=<spring:message code="heatmap_timeout"/>>
				<button id="start" style="margin-bottom: 10px;"class="btn btn-primary"  data-loading-text=<spring:message code="ping_processing" /> ><spring:message code="ping_start" /></button>
				<button id="end" style="margin-bottom: 10px;" class="btn btn-primary"><spring:message code="ping_end" /></button>
				<span id="infomation"></span>
			</form>
			</div>
			<div id="infoBox" class="hide">
				<div class="portlet box light-grey">
					<div class="portlet-title">
						<div class="caption"><i class="icon-search"></i><spring:message code="ping_result" /></div>
					</div>
					<div id="info" class="portlet-body" style="font-size: 16px">
					</div>
				</div>
         	</div>			
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
   <!-- END PAGE LEVEL SCRIPTS -->  

   <script type="text/javascript">
   	var i18n_delete = '<spring:message code="common_delete" />';
   	var i18n_ping_failed = '<spring:message code="ping_failure_info" />';
   	var i18n_ping_success = '<spring:message code="ping_success_info" />';
   	var i18n_ping_Connection_times_1 = '<spring:message code="ping_Connection_times_1" />';
   	var i18n_ping_Connection_times_10 = '<spring:message code="ping_Connection_times_10" />';
   	var i18n_ping_Packet_size_0 = '<spring:message code="ping_Packet_size_0" />';
   	var ping_Packet_size_256 = '<spring:message code="ping_Packet_size_256" />';
   	var ping_Packet_timeout = '<spring:message code="ping_Packet_timeout" />';
   	var ping_Packet_timeout1 = '<spring:message code="ping_Packet_timeout1" />';
   	var ping_Packet_sva= '<spring:message code="sva_name_choose" />';
   	var ping_Packet_sva1= '<spring:message code="sva_cishu_input" />';
   	var ping_Packet_sva2= '<spring:message code="sva_daxiao_input" />';
   	var ping_Packet_sva3= '<spring:message code="sva_time_input" />';
   	function repalceInfo(str,ip)
   	{
	   	var str1 = str.replace(/ip/g,ip);
	   	return str1;
   	}
   
   	function initDropdown(){
	   	$.get("/sva/svalist/api/getTableData?t="+Math.random(),function(data){
			if(!data.error){
				updateSva("svaList",data.data);
			}
	   	});
   	}
   
   	function removeOption(renderId){
		$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
	};
	function updateSva(renderId,data,callback){
	    var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
    		options += '<option class="addoption" value="'+info.ip+'">' + HtmlDecode3(info.name) +'</option>';
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
   	$(document).ready(function() {  
    	 App.init();
    	 initDropdown();
    	 var validForm = $(".demoform").Validform({tiptype:3,tipSweep:true,showAllError:true,datatype:{
    		 "cishu":function(gets,obj,curform,regxp){
                 if(isNaN(gets))
                 {
                 return false;
                 }
               if(gets>10||gets<1)
               {
               return false;
               }
             },
             "daxiao":function(gets,obj,curform,regxp){
                 if(isNaN(gets))
                 {
                 return false;
                 }
               if(gets>256||gets<1)
               {
               return false;
               }
             },
             "time":function(gets,obj,curform,regxp){
                 if(isNaN(gets))
                 {
                 return false;
                 }
               if(gets>20000||gets<1)
               {
               return false;
               }
             }
    	 }});
    	 $("#end").on("click",function(e){
    		 $("#infomation").text("");
             $("#infomation").removeClass("Validform_wrong");
             //刷新当前页面
    		 location.reload() ;
    		 return false;
    	 });
         $("#start").on("click",function(e){
             var svaList = $("#svaList").val();
             if(svaList == ""){
                 $("#infomation").text(ping_Packet_sva);
                 $("#infomation").addClass("Validform_wrong");
                 return false;
             }
        	 var pingnumber = $("#pingnumber").val();
             if(pingnumber=="" )
             {
                 $("#infomation").text(ping_Packet_sva1);
                 $("#infomation").addClass("Validform_wrong");
                 return false;
             }
        	 if(1>pingnumber)
        	 {
        		 $("#infomation").text(i18n_ping_Connection_times_1);
                 $("#infomation").addClass("Validform_wrong");
        		 return false;
        	 }
        	 if(pingnumber>10 )
        	 {
                 $("#infomation").text(i18n_ping_Connection_times_10);
                 $("#infomation").addClass("Validform_wrong");
        		 return false;
        	 }
        	 var packtsize = $("#packtsize").val();
             if(packtsize=="" )
             {
                 $("#infomation").text(ping_Packet_sva2);
                 $("#infomation").addClass("Validform_wrong");
                 return false;
             }
        	 if(packtsize<0 )
        	 {
                 $("#infomation").text(i18n_ping_Packet_size_0);
                 $("#infomation").addClass("Validform_wrong");
        		 return false;
        	 }
        	 if( packtsize>256 )
        	 {
                 $("#infomation").text(ping_Packet_size_256);
                 $("#infomation").addClass("Validform_wrong");
        		 return false;
        	 }
        	 var timeout = $("#timeout").val();
        	 if(timeout==""){
                 $("#infomation").text(ping_Packet_sva3);
                 $("#infomation").addClass("Validform_wrong");
                 return false;
        	 }
        	 if(timeout > 20000){
                 $("#infomation").text(ping_Packet_timeout);
                 $("#infomation").addClass("Validform_wrong");
                 return false;
        	 }
        	 if(timeout < 1){
                 $("#infomation").text(ping_Packet_timeout1);
                 $("#infomation").addClass("Validform_wrong");
                 return false;
        	 }
        	   $("#infomation").text("");
               $("#infomation").removeClass("Validform_wrong");
             var check = validForm.check(true);
             if(!check)
                 {
                 return false;
                 }
             $("#info").empty();
        	 $("#start").button('loading');
        	 $.post("/sva/api/pingSVA",{ip:$("#svaList").val(),pingnumber:pingnumber,packtsize:packtsize,timeout:timeout},function(data){
        		 $("#infoBox").show();
        		 var show = data.data;
        		 if(data.error){
        			 $("#info").append("<h2><spring:message code="ping_failure"/></h2>");
        		 }else{
        		 	$("#info").append("<h2><spring:message code="ping_success"/></h2>");
        		 }

        		 if(typeof(show) != "string"){
         		 	 show = show.join("<br>");
        		 }
    			 $("#info").append(show);
    			 $("#start").button('reset');
        	 });
        	 return false;
         });
         
		$("#svaList").on("change",function(e){
			if($("#svaList").val() != ""){
				$('#start').attr("disabled",false);
			}else{
				$('#start').attr("disabled","disabled");
			}
        });
     });
   </script>
   <!-- END JAVASCRIPTS -->
</body>
</html>