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
    .demo-wrapper {
        position: relative;
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
                  <li><spring:message code="tools_pRRU" /></li>
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
            <form class="form-horizontal demoform"  onkeydown= "if(event.keyCode==13)return false;" action="/sva/pRRU/api/saveData" enctype="multipart/form-data" method="post" >
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
                             <select style="width: 59%;height: 15%;"datatype="*"  nullmsg='<spring:message code="map_store_name" />' name="placeId" id="placeSel" >
                                <option value=" "></option>
                             </select>
                          </div>
                   </div>
                   <div class="control-group">
                        <label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
                          <div class="controls">
                             <select style="width: 59%;height: 15%;" datatype="*"  nullmsg='<spring:message code="map_place_name" />'  name="floor" id="zSel" >
                                <option value=" "></option>
                             </select>
                          </div>
                   </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="hidden" name ="floorNo" id="idid" style="width: 56%; height: 15%;">
                       </div>
                     </div>                       
                  </div>
                 </div>
                </div>
                <div class="span6" style="padding:10px;">
                   <div class="row-fluid">
                      <div></div>
                   </div>
                  <div class="row-fluid">
                      <div class="span12" style="outline:0px solid #E6E4E4;padding:50px 0;">
                   <div class="control-group">
                        <label class="control-label" for="pictrueId"><spring:message code="tools_pRRU_wenjian" /></label>
                          <div class="controls">
                            <input type='text' errormsg='<spring:message code="tools_pRRU_tishi" />' nullmsg='<spring:message code="tools_pRRU_tishi" />' datatype="xml"  name='textfield' id='textfield' disabled="disabled"   style="width:20%;margin: auto;" /> 
                            <input type='button' onclick="clickFile()" class='btn' value='<spring:message code="tools_pRRU_wenjian" />' style="width: 36%;margin-left: -1%"/>
                            <input type="file" name="file" class="file hide" id="fileField"  onchange="fileValue(this)"/>                          
                          </div>
                   </div>

                  </div>
                </div>
               </div>
               </div>
                    <div class="form-actions" style="padding-left: 0px;">
                        <div class="row-fluid">
                             <div class="span12" style="text-align:center;">
                               <input type="button" id="confirm" style="height: 30px" value="<spring:message code="common_confirm" />" class="btn btn-success btn_submit">
                               <input type="button" id="cancel" style="height: 30px" onclick="hideAdd()" type="button" value="<spring:message code="common_cancel" />" class="btn">
                               <span class="sameInfo" ></span>
                              </div>
                        </div>
                     </div>
                    </div>
                <!--  
                    <thead>
                        <tr>
                            <th class="width2"><spring:message code="message_table_title_location" /></th>
                            <th class="width1"><spring:message code="message_table_title_floor" /></th>
                            <th class="width1"><spring:message code="message_table_title_x" /></th>
                            <th class="width1"><spring:message code="message_table_title_y" /></th>
                            <th class="width1"><spring:message code="message_table_title_range" /></th>
                            <th class="width1"><spring:message code="message_table_title_shopName" /></th>
                            <th><spring:message code="message_table_title_message" /></th>
                            <th class= "width1"><spring:message code="message_table_title_enable" /></th>
                            <th class="width2"><spring:message code="message_table_title_operation" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><select id="placeSel"  style="width:90%;height:30px;margin: auto;" name="place"><option value="-1"></option></select></td>
                            <td><select id="zSel" style="width:90%;height:30px;margin: auto;" name="floor"><option value="-1"></option></select></td>
                            <td><input type="text" maxlength="10" style="width:90%;margin: auto;" name="xSpot" onchange="estimateOnkeyup(this)"// onkeyup="estimateOnkeyup(this)"///></td>
                            <td><input type="text" maxlength="10" style="width:90%;margin: auto;" name="ySpot" onchange="estimateOnkeyup(this)"//  onkeyup="estimateOnkeyup(this)"///></td>
                            <td><input type="text" maxlength="10" style="width:90%;margin: auto;" maxlength="4" name="rangeSpot" onchange="estimateOnkeyup(this)"// onkeyup="estimateOnkeyup(this)"///></td>
                            <td><input type="text" maxlength="20" style="width:90%;margin: auto;" name="shopName" /></td>
                            <td><input type="text" style="width:90%;margin: auto;" name="message" /></td>
                            <td>
                                <select id="enableSel" style="width:90%;height:30px;margin: auto;" name="isEnable">
                                    <option value="-1"></option>
                                    <option value="开启"><spring:message code="message_swith_open" /></option>
                                    <option value="关闭"><spring:message code="message_swith_close" /></option>
                                </select>
                            </td>
                            <td><input type="submit" value="<spring:message code="common_submit" />" class= "btn btn-primary" /></td>
                        </tr>
                    </tbody>
                </table>
                    -->
            </form>
            <div><button id="add" style="height: 30px;" type="button" class="btn btn-primary" onclick="addMap()"><spring:message code="common_add" /></button></div>                    
            <table id="table" class="table table-bordered">
                  <thead>
                        <tr>
                            <th><spring:message code="store_add_name" /></th>
                            <th><spring:message code="message_table_title_floor" /></th>
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
        <div class="modal hide fade" id="myModal1">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
                <div style="padding-top: 20px;">
                <div style="float: left;padding-top: 20px" id="contans"><label  style="float: left ;"><spring:message  code="tools_pRRU_count"></spring:message>
                   </label> <label id="count"
                            style="float: left ;"></label>
                   </div> 
                </div>
            </div>
            <div class="modal-body">
                <div id="divCon" style="text-align: center; height: 500px;">
                    <div id="preview" class="demo-wrapper" 
                        style="outline: 0px solid #B3B1B1;"></div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="hide">
                    <input id="pointX" type="text">
                    <input id="pointY" type="text">
                </div>
                <button onclick="closeModel()" class="btn"><spring:message code="common_close" /></button>
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
        <script src="<c:url value='/js/pRRU.js'/>" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->  

        <script type="text/javascript">
   var oTable,validForm,timer;
   var rect = {};
   var i18n_format = '<spring:message code="tools_pRRU_tishi" />';
   var i18n_movie = '<spring:message code="message_movie_format" />';
   var i18n_delete = '<spring:message code="common_delete" />',
        i18n_edit = '<spring:message code="common_edit" />',
        i18n_info = '<spring:message code="map_info" />',
        i18n_deleteInfo = '<spring:message code="map_delete" />',
        i18n_open = '<spring:message code="message_swith_open" />',
        i18n_close = '<spring:message code="message_swith_close" />',
        i18n_choose_title = '<spring:message code="map_choose_posistion_alert" />',
        i18n_sameplace = '<spring:message code="tools_pRRU_same" />',
        i18n_Preview = '<spring:message code="test_table_title_detail" />';
      $(document).ready(function() { 
    	  
    	  validForm = $(".demoform").Validform({
        	  tiptype:3,
              datatype:{
                        "xml":function(gets,obj,curform,regxp){
                        	  if(gets=="")
                        		{
                        		 return false;
                        		}else
                        		{
                                 var fi =gets.split(".")[gets.split(".").length-1];
                                  if (fi!=""&&fi!="xml"&&fi!="XML")
                                   {
                                  return false;
                                   }
                        		}
                            }

              }

          });

         App.init();
         MsgMng.initSelect();
         MsgMng.initMsgTable();
         MsgMng.bindClickEvent();
      });
        </script>
   <!-- END JAVASCRIPTS -->
    </body>
</html>