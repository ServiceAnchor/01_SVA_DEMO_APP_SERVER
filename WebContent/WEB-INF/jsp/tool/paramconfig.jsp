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
    <link href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .demo-wrapper {
            position: relative;
        }
        .icon-question-sign{
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
        .preview_size_fake { /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
            filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
            height: 1px;
            visibility: hidden;
            overflow: hidden;
            display: none;
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
                        <li><spring:message code="paramconfig" /></li>
                    </ul>
                    <!-- END PAGE TITLE & BREADCRUMB-->
                </div>
            </div>
            <!-- END PAGE HEADER-->
            <div class="clearfix"></div>
            <div class="tableBox">
                <div id="alertBox" class="hide">
                    <div class="alert">
                        <button type="button" class="close" data-dismiss="alert" >&times;</button>
                        <div id="info"></div>
                    </div>
                </div>
                <form class="form-horizontal demoform"  onkeydown= "if(event.keyCode==13)return false;" enctype="multipart/form-data" method="post" >
                    <div id="editBox" class="portlet light-grey hide">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-edit icon-large" style="background: none"></i><spring:message code="bluemix_config" />
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span6" style="padding:10px;">
                                <div class="row-fluid">
                                    <div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">
                                        <div class="control-group">
                                            <label class="control-label" for="banThreshold"><spring:message code="param_banThreshold" /></label>
                                            <div class="controls">
                                                <input type="text" datatype="paramYz" nullmsg="<spring:message code="param_inputbanThreshold" />" errormsg="<spring:message code="param_inputInfo" />" maxlength="10"  style="width: 59%;height: 15%;" name="banThreshold" id="banThreshold"  >
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="weight"><spring:message code="param_filterlength" /></label>
                                            <div class="controls">
                                                <input type="text" datatype="paramYz" nullmsg="<spring:message code="param_filterlength_input" />" errormsg="<spring:message code="param_inputInfo" />"maxlength="10"  style="width: 59%;height: 15%;" name="filterLength" id="filterLength"  >
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="maxDeviation"><spring:message code="param_horizontal" /></label>
                                            <div class="controls">
                                                <input type="text" datatype="paramYz" nullmsg="<spring:message code="param_horizontal_input" />" maxlength="10"  errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="horizontalWeight" id="horizontalWeight"  >
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="biasSpeed"><spring:message code="param_ongitudinal" /></label>
                                            <div class="controls">
                                                <input type="text" datatype="paramYz" nullmsg="<spring:message code="param_ongitudinal_input" />" maxlength="10" errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="ongitudinalWeight" id="ongitudinalWeight"  >
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="SPR"><spring:message code="param_maxDeviation_title" /></label>
                                            <div class="controls">
                                                <input type="text" value="30" datatype="paramYz" nullmsg="<spring:message code="param_inputmaxDeviation" />" maxlength="10" errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="maxDeviation" id="maxDeviation"  >
                                            </div>
                                        </div>                                      
                                        <div class="control-group">
                                            <label class="control-label" for="SPR"><spring:message code="param_exceedMaxDeviation_title" /></label>
                                            <div class="controls">
                                                <input type="text" value="2" datatype="paramYz" nullmsg="<spring:message code="param_inputexceedMaxDeviation" />" maxlength="10" errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="exceedMaxDeviation" id ="exceedMaxDeviation"  >
                                            </div>
                                        </div>                                      
                                        <div class="control-group">
                                            <div class="controls">
                                                <input type="hidden" name = "id" id="idid" style="width: 56%; height: 15%;">
                                            </div>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        <div class="span6" style="">
                                <div class="row-fluid">
                                    <div>&nbsp</div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span12" style="outline: 0px solid #E6E4E4; padding: 5px 0;">
                                        <div class="control-group">
                                            <label class="control-label" for="SPR"><spring:message code="param_correMap" /></label>
                                            <div class="controls">
                                                <input type="text"  datatype="paramYz" nullmsg="<spring:message code="param_correMap_input" />" maxlength="10" errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="correctMapDirection" id="correctMapDirection">
                                            </div>
                                        </div>                                    
                                        <div class="control-group">
                                            <label class="control-label" for="exceedMaxDeviation"><spring:message code="param_resTime" /></label>
                                            <div class="controls">
                                                <input type="text" datatype="paramYz" nullmsg="<spring:message code="param_resTime_input" />" maxlength="10" errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="restTimes" id="restTimes"  >
                                            </div>
                                        </div>                                        
                                        <div class="control-group">
                                            <label class="control-label" for="SPR"><spring:message code="param_filterPeak" /></label>
                                            <div class="controls">
                                                <input type="text"  datatype="paramYz" nullmsg="<spring:message code="param_filterPeak_input" />" maxlength="10" errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="filterPeakError" id="filterPeakError" >
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="SPR"><spring:message code="param_peakWidth" /></label>
                                            <div class="controls">
                                                <input type="text" datatype="paramYz" nullmsg="<spring:message code="param_peakWidth_input" />" maxlength="10" errormsg="<spring:message code="param_inputInfo" />" style="width: 59%;height: 15%;" name="peakWidth" id="peakWidth"  >
                                            </div>
                                        </div> 
                                        <div class="control-group">
                                            <label class="control-label" for="step"><spring:message code="param_step" /></label>
                                              <div class="controls">
                                                 <input style="width: 59%;height: 15%;" datatype="paramYz"  nullmsg='<spring:message code="param_step_input" />' errormsg="<spring:message code="param_inputInfo" />" name=step id="step" >
                                              </div>
                                         </div>                                                                                                                                                                              
                                    </div>
                                </div>
                            </div>                          
                        </div>
                        <div class="form-actions" style="padding-left: 0px;">
                            <div class="row-fluid">
                                <div class="span12" style="text-align:center;">
                                    <input id="confirm" style="height: 30px" type="button" value="<spring:message code="common_confirm" />" class="btn btn-success"> 
                                    <button id="cancel" style="height: 30px" type="button" class="btn"><spring:message code="common_cancel" /></button>
                                <span class="sameInfo" ></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div>
                    <button id="add" style="height: 30px;" type="button" class="btn btn-primary" ><spring:message code="common_add" /></button>
                    </div>  
                    <table id="table" class="table table-bordered" style="text-align:center">
                        <thead>
                            <tr>
                                <th style="width:100px"><spring:message code="param_banThreshold_title" /></th>
                                <th style="width:50px"><spring:message code="param_filterlength" /></th>
                                <th style="width:50px"><spring:message code="param_horizontal" /></th>
                                <th style="width:50px"><spring:message code="param_ongitudinal" /></th>
                                <th style="width:50px"><spring:message code="param_maxDeviation" /></th>
                                <th style="width:50px"><spring:message code="param_exceedMaxDeviation" /></th>
                                <th style="width:50px"><spring:message code="param_correMap" /></th>
                                <th style="width:50px"><spring:message code="param_resTime" /></th>
                                <th style="width:50px"><spring:message code="param_filterPeak" /></th>
                                <th style="width:50px"><spring:message code="param_peakWidth" /></th>
                                <th style="width:50px"><spring:message code="param_step" /></th>
                                <th style="width:50px"><spring:message code="bluemix_updateTime" /></th>                                
                                <th style="width:188px"><spring:message code="message_table_title_operation" /></th>
                            </tr>
                        </thead>            
                    </table>    
                </div>
            </div>
            <!-- END PAGE -->
        </div>
    <%@ include file="../shared/pageFooter.jsp"%>
    <%@ include file="../shared/importJs.jsp"%>
    <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script> 
        <script src="<c:url value='/plugins/raphael-min.js'/>"
        type="text/javascript"></script>
        <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/js/paramconfig.js'/>" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->  

        <script type="text/javascript">
           var oTable;
           var validForm;
           var i18n_edit = '<spring:message code="common_edit" />',
            i18n_open = '<spring:message code="message_swith_open" />',
            i18n_close = '<spring:message code="message_swith_close" />';
            $(document).ready(function() {
                 App.init();
                 paramconfig.initTable();
                 paramconfig.bindClickEvent(); 
                  validForm =  $(".demoform").Validform({tiptype:3,
                      showAllError:true,
                      datatype:{
                          "password":function(gets,obj,curform,regxp){
                          if(gets=="")
                          {
                          return false;
                          }
                      
                  },
                  "jd":function(gets,obj,curform,regxp){
                      if(isNaN(gets))
                      {
                      return false;
                      }
                    if(gets>360||gets<0)
                    {
                    return false;
                      }}}});

                  if (info == "Max") {
                      alert(i18n_max);
                  }
                
                 $('.tip').tooltip();
              });
        </script>
   <!-- END JAVASCRIPTS -->
    </body>
</html>