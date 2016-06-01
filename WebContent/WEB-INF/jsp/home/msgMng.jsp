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
   <link
    href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>"
    rel="stylesheet" type="text/css" />
    <style type="text/css">
    .btn-upload{position: relative; display:inline-block;height:36px; *display:inline;overflow:hidden;vertical-align:middle;cursor:pointer}
    .upload-url{cursor: pointer}
    .input-file-1{position:absolute; right:0; top:0; cursor: pointer; z-index:1; font-size:30em; *font-size:30px;opacity:0;filter: alpha(opacity=0)}
    .btn-upload .input-text{ width:auto;}
    .form-group .upload-btn{ margin-left:-1px}
    .demo-wrapper {
		position: relative;
	}
	
	.icon-question-sign
	{
		background-position: 16px 16px;
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
	
	.preview_size_fake { /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
		filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image
			);
		height: 1px;
		visibility: hidden;
		overflow: hidden;
		display: none;
	}
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
                     <spring:message code="menu_info_manage" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="menu_info_manage_message" /></li>
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
         	<form class="form-horizontal demoform" onkeydown= "if(event.keyCode==13)return false;" action="/sva/message/api/saveData" enctype="multipart/form-data" method="post" >
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
                        <label class="control-label" for=placeSel><spring:message code="store_add_name" /></label>
                          <div class="controls">
                             <select style="width: 65%;height: 15%;" datatype="*"  nullmsg='<spring:message code="map_store_name" />' name="placeId" id="placeSel" >
                                <option value=""></option>
                             </select>
                          </div>
                   </div>
                   <div class="control-group">
                        <label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
                          <div class="controls">
                             <select style="width: 65%;height: 15%;" datatype="*"  nullmsg='<spring:message code="all_floor_choose" />' name="floorNo" id="zSel" >
                                <option value=""></option>
                             </select>
                          </div>
                   </div>
                   <div class="control-group">
                    <label class="control-label" for=""shopId""><spring:message code="shop" /></label>
                     <div class="controls">
                      <select datatype="*"  nullmsg='<spring:message code="area_RegionalCategory" />'  style="width: 65%;height: 15%;" name="shopId" id="shopId" >
                          <option value=" "></option>
                      </select>
                     </div>
                    </div> 
                   <div class="control-group">
                        <label class="control-label" for="shopNameId"><spring:message code="message_table_title_shopName" /></label>
                          <div class="controls">
                               <input type="text"  datatype="password"  nullmsg='<spring:message code="msg_dianpu" />' style="width: 62%;height: 15%;" class="input-xlarge" maxlength="100" name="shopName" id="shopNameId" placeholder="<spring:message code="message_table_title_shopName" />"
                               >
                          </div>
                   </div>                                     
                    <div class="control-group">
                        <div class="controls">
                            <input type="hidden" name = "id" id="idid" style="width: 56%; height: 15%;">
                       </div>
                     </div>                       
			<!-- 		<div class="control-group">
						<label class="control-label" for="xId"><spring:message code="map_choose_posistion" /></label>
						<a data-toggle="modal" data-type="point" 
								href="#myModal1" role="button" class="btn"
								disabled="disabled" style="width: 10%;margin: 0 0 5px 20px;"><spring:message code="map_choose_dian" /></a>
						<div class="controls" style="border: 1px solid #DDD;border-radius: 3px;width: 46%;padding: 10px 10px 0 0;">
		                   	<div class="control-group">
		                        <label class="control-label" style="width:120px;" for="xSpotId"><spring:message code="message_table_title_x" /></label>
	                          	<div class="controls" style="margin-left:140px;">
	                               	<input type="text" style="width: 66%;height: 15%;"  maxlength="10" datatype="n3" errormsg='<spring:message code="message_dingweidian_x" />'  nullmsg='<spring:message code="map_x" />'  class="input-xlarge" name="xSpot" id="xSpotId"
	                               onchange="estimateOnkeyup(this)" onkeyup="estimateOnkeyup(this)"/>
	                          	</div>
		                   	</div>
		                   	<div class="control-group">
								<label class="control-label" style="width:120px;" for="ySpotId"><spring:message code="message_table_title_y" /></label>
		                        <div class="controls" style="margin-left:140px;">
		                            <input type="text" style="width: 66%;height: 15%;" datatype="n3" errormsg='<spring:message code="message_dingweidian_y" />'  nullmsg='<spring:message code="map_y" />'  maxlength="10" class="input-xlarge" name="ySpot" id="ySpotId"
		                               onchange="estimateOnkeyup(this)" onkeyup="estimateOnkeyup(this)"/>
		                        </div>
		                   	</div>
		                   	<div class="control-group">
	                        	<label class="control-label" style="width:120px;" for="rangeSpotId"><spring:message code="message_table_title_range" /></label>
	                          	<div class="controls" style="margin-left:140px;">
	                               	<input type="text" style="width: 66%;height: 15%;" datatype="n3" errormsg='<spring:message code="message_dingweidian_fanwei" />'  nullmsg='<spring:message code="msg_fanwei" />'  maxlength="10" class="input-xlarge" name="rangeSpot" id="rangeSpotId"
		                               onchange="estimateOnkeyup(this)" onkeyup="estimateOnkeyup(this)"/>
	                          	</div>
		                   	</div> 
						</div>
					</div>
						  -->
                    
  						  
                  </div>
                 </div>
                </div>
                <div class="span6" style="padding:10px;">
                   <div class="row-fluid">
                      <div></div>
                   </div>
                  <div class="row-fluid">
                      <div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">
                   <div class="control-group">
                        <label class="control-label" for="messageId"><spring:message code="message_table_title_message" /></label>
                          <div class="controls">
                               <input type="text" maxlength="300" datatype="password"  nullmsg='<spring:message code="msg_xiaoxi" />' style="width: 58%;height: 15%;" class="input-xlarge" name="message" id="messageId" placeholder="<spring:message code="message_table_title_message" />"
                               >
                          </div>
                   </div>                      
                   <div class="control-group">
                        <label class="control-label" for="timeInterval"><spring:message code="msg_timeInterval" /></label>
                          <div class="controls">
                               <input type="text" maxlength="30" datatype="zs" nullmsg='<spring:message code="msg_inputtimeInterval" />' errormsg='<spring:message code="heatmap_integer" />'style="width: 58%;height: 15%;" class="input-xlarge" name="timeInterval" id="timeInterval" placeholder="<spring:message code="msg_timeInterval" />"
                               >
                          </div>
                   </div>                    
                   <div class="control-group">
                        <label class="control-label" for="pictrueId"><spring:message code="seller_choose_pictrue" /><i class="icon-question-sign tip" data-toggle="tooltip" title="<spring:message code="map_map_info" />"></i></label>
                          <div class="controls">
                            <input type='text' name='textfield' errormsg='<spring:message code="map_format" />' nullmsg='<spring:message code="map_table_title_choose" />' datatype="jp"  id='textfield' disabled="disabled"   style="width:24%;margin: auto;" /> 
                            <span class="btn-upload form-group">
                             <input type='button'  class="btn" value='<spring:message code="seller_choose_pictrue" />'  style="width: 101%; margin-left: -1%;margin-top: 2px;" />
                             <input id="fileField"  type="file" style="height: 30px;" multiple name="file" class="input-file-1">
                            </span>                          
                          </div>
                   </div>
                   <div class="control-group">
                        <label class="control-label" for="movieId"><spring:message code="seller_choose_movie" /><i class="icon-question-sign tip" data-toggle="tooltip" title="<spring:message code="message_movie_mp4" />"></i></label>
                          <div class="controls">
                            <input type='text' errormsg='<spring:message code="message_movie_mp4" />' nullmsg='<spring:message code="map_table_title_choose" />' datatype="mv" name='textfield' id='textfield1' disabled="disabled"   style="width:24%;margin: auto;" /> 
                            <span class="btn-upload form-group">
                            <input type='button'  class="btn" value='<spring:message code="seller_choose_movie" />'  style="width: 101%; margin-left: -1%;margin-top: 2px;" />
                            <input id="fileField1"  type="file" style="height: 30px;" multiple name="file1" class="input-file-1">
                            </span>                           
                          </div>
                   </div>                  
                   <div class="control-group">
                        <label class="control-label" for="isEnableId"><spring:message code="message_table_title_enable" /></label>
                          <div class="controls">
                             <select style="width: 59%;height: 15%;" datatype="*"  nullmsg='<spring:message code="msg_kaiqi" />' name=isEnable id="isEnableId" >
                                    <option value=""></option>
                                    <option value="开启"><spring:message code="message_swith_open" /></option>
                                    <option value="关闭"><spring:message code="message_swith_close" /></option>
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
                               <button id="confirm" style="height: 30px" type="submit" class="btn btn-success"><spring:message code="common_confirm" /></button>
                               <button id="cancel" style="height: 30px" onclick="hideAdd()" type="button" class="btn"><spring:message code="common_cancel" /></button>
                             <span class="sameInfo" ></span>
                              </div>
                        </div>
                     </div>
                    </div>
                    </form>
            <div><button id="add" style="height: 30px;" type="button" class="btn btn-primary" onclick="addMap()"><spring:message code="common_add" /></button></div>          			
			<table id="table" class="table table-bordered">
			      <thead>
                        <tr>
                            <th style=""><spring:message code="message_table_title_location" /></th>
                            <th style=""><spring:message code="message_table_title_floor" /></th>
                            <!--  <th style="width:50px"><spring:message code="message_table_title_x" /></th>
                            <th style="width:70px"><spring:message code="message_table_title_y" /></th>
                            <th style="width:70px"><spring:message code="message_table_title_range" /></th>-->
                            <th ><spring:message code="shop" /></th>
                            <th style=""><spring:message code="message_table_title_shopName" /></th>
                            <th><spring:message code="message_table_title_message" /></th>
                            <th><spring:message code="msg_timeInterval" /></th>
                            <th style=""><spring:message code="message_table_title_enable" /></th>
                            <th style="width:278px"><spring:message code="message_table_title_operation" /></th>
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
		<div class="modal hide fade" id="myModal">
	       	<div class="modal-body">
	       		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	       		<div class="bs-docs-example">
	       			<ul id = "myTab" class="nav nav-tabs">
	       				<li class="active">
	       					<a href="#pictrue" data-toggle="tab"><spring:message code="seller_pictrue_Preview" /></a>
	       				</li>
	       				<li class>
	       					<a href="#movie" data-toggle="tab"><spring:message code="seller_movie_Preview" /></a>
	       				</li>
	       			</ul>
	       			<div class="tab-content" id = "myTabContent">
	       				<div class="tab-pane fade action in" id = "pictrue"  style="text-align: center;">
	       					<img id="pictureid" style="max-width: 44%">
	       				</div>
       					<div class="tab-pane fade" style="text-align: center;" id = "movie">
							<div id="movieCover" style="width:80%;height:426px;position:fixed;margin-left:auto;margin-right:auto;background-color:#eee;z-index:10001;">
								<i id="play" class="icon-play-circle icon-5x" style="background-image: none;margin-top: 200px;cursor:pointer;"></i>
							</div>
							<video id="video" width=798px height=426px controls="controls">
	       				</div>
	       			</div>
	       		</div>
	       	</div>
	       	<div class="modal-footer">
	       		<input type="button" id= "closeId" onclick="closeModel()" value=" <spring:message code="common_close" />">
			</div>

       	</div>
       	<div class="modal hide fade" id="myModal1">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<div style="padding-top: 20px;">
					<spring:message code="map_choose_posistion" />
					<button type="button" class="btn btn-small clearPaper"
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
						style="outline: 1px solid #B3B1B1;"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" id="Ok" class="btn"><spring:message code="common_confirm" /></button>
			</div>
		</div>
   		<!-- END FOOTER -->
   
		<%@ include file="../shared/importJs.jsp"%>
		<!-- BEGIN PAGE LEVEL SCRIPTS -->
		<script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script> 
		<script src="<c:url value='/plugins/raphael-min.js'/>"
		type="text/javascript"></script>
		<script src="<c:url value='/js/mapEdit.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/msgMng.js'/>" type="text/javascript"></script>
		<!-- END PAGE LEVEL SCRIPTS -->  

		<script type="text/javascript">
   var oTable;
   var rect = {};
   var i18n_format = '<spring:message code="map_format" />';
   var i18n_movie = '<spring:message code="message_movie_format" />';
   var i18n_delete = '<spring:message code="common_delete" />',
   		i18n_edit = '<spring:message code="common_edit" />',
   	    i18n_info = '<spring:message code="map_info" />',
   	    i18n_deleteInfo = '<spring:message code="map_delete" />',
   	   	i18n_open = '<spring:message code="message_swith_open" />',
   	   	i18n_close = '<spring:message code="message_swith_close" />',
   	 	i18n_choose_title = '<spring:message code="map_choose_posistion_alert" />',
        i18n_max = '<spring:message code="map_pictrue_max" />',
        i18n_fuzhi  = '<spring:message code="map_fuzhi" />',
       	i18n_Preview = '<spring:message code="seller_Preview" />';
      $(document).ready(function() { 
    	  $(".demoform").Validform({tiptype:3,
    		  showAllError:true,
              datatype:{
                    "jp":function(gets,obj,curform,regxp){
                        var fi =gets.split(".")[gets.split(".").length-1];
                          if (fi!=""&&fi!="jpg"&&fi!="png"&&fi!="PNG"&&fi!="JPG")
                          {
                              return false;
                          }

                    	},
                        "mv":function(gets,obj,curform,regxp){
                            var fi =gets.split(".")[gets.split(".").length-1];
                              if (fi!=""&&fi!="mp4"&&fi!="MP4")
                              {
                                  return false;
                              }

                            },
                        "password":function(gets,obj,curform,regxp){
                                if(gets=="")
                                    {
                                    return false;
                                    }
                                
                            }
              }});
          var info1 = '${info}';
          if (info1 == "Max") {
                 $("#editBox").show();
                 $(".sameInfo").addClass("Validform_wrong");
                 $(".sameInfo").text(i18n_max);
             }
    	 App.init();
    	 MsgMng.initSelect();
    	 MsgMng.initMsgTable();
    	 MsgMng.bindClickEvent();
    	 $('.tip').tooltip();
      });
		</script>
   <!-- END JAVASCRIPTS -->
	</body>
</html>