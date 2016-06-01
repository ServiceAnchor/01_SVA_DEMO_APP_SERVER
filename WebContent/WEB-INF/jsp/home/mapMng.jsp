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
input {
	padding: 4px 4px !important
}

.file-box {
	position: relative;
	width: 340px
}
.icon-question-sign
{
background-position: 16px 16px;
}

.txt {
	height: 22px;
	border: 1px solid #cdcdcd;
	width: 180px;
}

.file {
	position: absolute;
	top: 0;
	right: 10px;
	height: 24px;
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 150px
}

i.icon-*{
	background-image:none;
}

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
                    <spring:message code="map_title" />
               </h3>-->
					<ul class="page-breadcrumb breadcrumb">
						<li><i class="icon-home" style="background-image: none"></i>
							<spring:message code="menu_info_manage" /> <i
							class="icon-angle-right"></i></li>
						<li><spring:message code="menu_info_manage_map" /></li>
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
				<form class="form-horizontal demoform" onkeydown= "if(event.keyCode==13)return false;" action="/sva/map/api/upload"
					method="post" enctype="multipart/form-data"
					>
					<div id="editBox" class="portlet light-grey hide">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-edit icon-large" style="background: none"></i>
								<spring:message code="bluemix_config" />
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6" style="padding: 10px;">
								<div class="row-fluid">
									<div></div>
								</div>
								<div class="row-fluid">
									<div class="span12"
										style="outline: 0px solid #E6E4E4; padding: 20px 0;">								
										<div class="control-group">
											<label class="control-label" for="placeId"><spring:message code="store_add_name" /></label>
	                          				<div class="controls">
	                           					<select style="width: 58%;height: 15%;" datatype="*"  nullmsg='<spring:message code="map_store_name" />' name="placeId" id="placeIdId" >
	                                    			<option value=" "></option>
	                             				</select>
	                          				</div>											
                                            <input type="hidden" name = "id" id="idid">
										</div>
										<div class="control-group">
											<label class="control-label" for="floorNoId"><spring:message
													code="map_table_title_floor" /></label>
											<div class="controls">
												<input  type="text" maxlength="20"  datatype="abc"  nullmsg='<spring:message code="map_place_name" />' style="width: 56%; height: 15%;"
													class="input-xlarge" name="floor" id="floorNoId"
													placeholder="<spring:message code="map_table_title_floor" />"
													/>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="floorNameId"><spring:message
													code="map_table_title_floorid" /><i
												class="icon-question-sign tip" data-toggle="tooltip"
												title="<spring:message code="tooltip_floorNo" />"></i></label>
											<div class="controls">
												<input type="text" style="width: 56%; height: 15%;"
													class="input-xlarge" maxlength="6" name="floorid" id="floorNameId"
													placeholder="<spring:message code="map_table_title_floorid" />"
													datatype="sn" errormsg='<spring:message code="map_floorNo_tishi" />'
													 nullmsg="<spring:message code="map_foorNo" />"/>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="coorSel"><spring:message
													code="map_table_title_coordinate" /><i
												class="icon-question-sign tip" data-toggle="tooltip"
												title="<spring:message code="tooltip_base" />"></i></label>
											<div class="controls">
												<select style="width: 58%; height: 15%;" datatype="*"  nullmsg='<spring:message code="map_controls" />' name="coordinate"
													id="coorSel">
													<option value=""></option>
													<option value="ul">
														<spring:message code="map_coordinate_leftup" />
													</option>
													<option value="ll">
														<spring:message code="map_coordinate_leftdown" />
													</option>
													<option value="ur">
														<spring:message code="map_coordinate_rightup" />
													</option>
													<option value="lr">
														<spring:message code="map_coordinate_rightdown" />
													</option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="angleId"><spring:message
													code="map_table_title_angle" /></label>
											<div class="controls">
												<input type="text" style="width: 56%; height: 15%;"
													class="input-xlarge" maxlength="10" datatype="jd" errormsg='<spring:message code="map_jiaodu_val" />' onkeyup="estimateOnkeyup(this)" nullmsg='<spring:message code="map_jiaodu" />'   name="angle" id="angleId"
										 />
											</div>
										</div>
                                        <div class="control-group">
                                            <label class="control-label" for="MAPid">MapId</label>
                                            <div class="controls">
                                                <input type="text" style="width: 56%; height: 15%;"
                                                    class="input-xlarge" maxlength="10" datatype="mapId" onkeyup="estimateOnkeyup(this)" nullmsg='<spring:message code="map_jiaodu" />'   name="mapId" id="mapId"
                                         />
                                            </div>
                                        </div>										
                                        <div class="control-group">
                                            <label class="control-label" for="routeId"><spring:message
                                                    code="bz_route_file" /></label>
                                            <div class="controls">
                                              <input type='text' class="input-text" errormsg='<spring:message code="tools_pRRU_tishi" />' name='routefield' nullmsg='<spring:message code="tools_pRRU_tishi" />' datatype = "xml" id='routefield'
                                                    disabled="disabled" style="width: 32%; margin: auto;"   readonly />
                                               <span class="btn-upload form-group">
                                                    <input type='button'  class="btn"
                                                    value='<spring:message code="tools_pRRU_wenjian" />'
                                                    style="width: 101%; margin-left: -1%;margin-top: 2px;" /><input id="fileRouteField"  type="file" style="height: 30px" multiple name="routefile" class="input-file-1">
                                                </span>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="pathfile2"><spring:message
                                                    code="map_file_path" /></label>
                                            <div class="controls">
                                              <input type='text' class="input-text" errormsg='<spring:message code="tools_pRRU_tishi" />' name='pathFile1' nullmsg='<spring:message code="tools_pRRU_tishi" />' datatype = "xml" id='pathFile1'
                                                    disabled="disabled" style="width: 32%; margin: auto;"   readonly />
                                               <span class="btn-upload form-group">
                                                    <input type='button'  class="btn"
                                                    value='<spring:message code="tools_pRRU_wenjian" />'
                                                    style="width: 101%; margin-left: -1%;margin-top: 2px;" /><input id="pathFileId"  type="file" style="height: 30px" multiple name="pathroutefile" class="input-file-1">
                                                </span>
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
									<div class="span12"
										style="outline: 0px solid #E6E4E4; padding: 20px 0;">
										<div class="control-group">
											<label class="control-label" for="mapid"><spring:message
													code="map_table_title_choose" /><i
												class="icon-question-sign tip" data-toggle="tooltip"
												title="<spring:message code="map_map_info" />"></i></label>
											<div class="controls">
											  <input type='text' class="input-text"  name='textfield' id='textfield'
                                                    disabled="disabled" style="width: 32%; margin: auto;" errormsg='<spring:message code="map_format" />' nullmsg='<spring:message code="map_info_choose" />' datatype="jp" readonly />
											   <span class="btn-upload form-group">
													<input type='button'  class="btn"
													value='<spring:message code="map_table_title_choose" />'
													style="width: 101%; margin-left: -1%;margin-top: 2px;" /><input id="fileField"  type="file" style="height: 30px" multiple name="file" class="input-file-1">
												</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="svgid"><spring:message
													code="map_table_title_choosesvg" /><i
												class="icon-question-sign tip" data-toggle="tooltip"
												title="<spring:message code="map_svg_info" />"></i></label>
											<div class="controls">
											  <input type='text' class="input-text"  name='svgfield' id='svgfield'
                                                    disabled="disabled" style="width: 32%; margin: auto;" errormsg='<spring:message code="map_svg_info" />' nullmsg='<spring:message code="map_table_title_choosesvg" />' datatype="svg" readonly />
											   <span class="btn-upload form-group">
													<input type='button'  class="btn"
													value='<spring:message code="map_table_title_choosesvg" />'
													style="width: 101%; margin-left: -1%;margin-top: 2px;" /><input id="fileSvgField"  type="file" style="height: 30px" multiple name="svgfile" class="input-file-1">
												</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="scaleId"><spring:message
													code="map_table_title_scale" /><i
												class="icon-question-sign tip" data-toggle="tooltip"
												title="<spring:message code="tooltip_scale" />"></i></label>
											<div class="controls">
												<input type="text" maxlength="15" style="width: 33%" class="input-xlarge"
													name="scale" id="scaleId" datatype="blc"  errormsg='<spring:message code="map_scale" />'  nullmsg='<spring:message code="map_choose_scale" />'
													placeholder="<spring:message code="map_table_title_scale" />" onkeyup="estimateOnkeyup(this)"
										   /><a data-toggle="modal"
													href="#myModal" role="button" class="btn" data-type="scale" 
													disabled="disabled" style="width: 12%"><spring:message code="map_choose_dian" /></a>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="xId"><spring:message code="map_zsel_dian" /></label>
											<a data-toggle="modal" data-type="point" 
													href="#myModal" role="button" class="btn"
													disabled="disabled" style="width: 10%;margin: 0 0 5px 20px;"><spring:message code="map_choose_dian" /></a>
											<div class="controls" style="border: 1px solid #DDD;border-radius: 3px;width: 45%;padding: 10px 10px 0 0;">
												<div class="control-group">
													<label class="control-label" for="xId" style="width:120px;"><spring:message
															code="map_table_title_x0" /></label>
													<div class="controls" style="margin-left:140px;">
														<input type="text" style="width: 60%; height: 15%" datatype="n3" errormsg='<spring:message code="map_zuobiaox" />'  nullmsg='<spring:message code="map_x" />'
															maxlength="10" class="input-xlarge" name="x" id="xId"
															 onkeyup="estimateOnkeyup(this)" />
													</div>
												</div>
												<div class="control-group" style="margin-bottom: 10px;">
													<label class="control-label" for="yId" style="width:120px;"><spring:message
															code="map_table_title_y0" /></label>
													<div class="controls" style="margin-left:140px;">
														<input type="text" style="width: 60%; height: 15%"
															maxlength="10" class="input-xlarge" name="y" id="yId" datatype="n3" errormsg='<spring:message code="map_zuobiaoy" />'  nullmsg='<spring:message code="map_y" />'
															 onkeyup="estimateOnkeyup(this)" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="form-actions" style="padding-left: 0px;">
							<div class="row-fluid">
								<div class="span12" style="text-align: center;">
									<input id="confirm" style="width:54px" type="button"
										class="btn btn-success" value="<spring:message code="common_confirm" />">
									<button id="cancel" style="" onclick="hideAdd()" type="button"
										class="btn">
										<spring:message code="common_cancel" />
									</button>
									<span class="sameInfo" ></span>
								</div>
							</div>
						</div>
					</div>
				</form>
				<div>
					<button id="add" style="" type="button" class="btn btn-primary"
						onclick="addMap()">
						<spring:message code="common_add" />
					</button>
				</div>
				<table id="tableMap" class="table table-bordered">
					<thead>
						<tr>
							<th style="min-width: 70px"><spring:message
									code="store_add_name" /></th>
							<th><spring:message code="map_table_title_floor" /></th>
							<th><spring:message code="map_table_title_floorid" /><i
								class="icon-question-sign tip" data-toggle="tooltip"
								title="<spring:message code="tooltip_floorNo" />"></i></th>
							<th style="max-width: 70px"><spring:message
									code="map_table_title_x0" /></th>
							<th style="max-width: 70px"><spring:message
									code="map_table_title_y0" /></th>
							<th><spring:message code="map_table_title_scale" /><i
								class="icon-question-sign tip" data-toggle="tooltip"
								title="<spring:message code="tooltip_scale" />"></i></th>
							<th><spring:message code="map_table_title_coordinate" /><i
								class="icon-question-sign tip" data-toggle="tooltip"
								title="<spring:message code="tooltip_base" />"></i></th>
							<th><spring:message code="map_table_title_angle" /></th>
							<th><spring:message code="map_table_title_choose" /><i
								class="icon-question-sign tip" data-toggle="tooltip"
								title="<spring:message code="map_map_info" />"></i></th>
							<th style="min-width: 56px; text-align: center;"><spring:message
									code="map_svg" /></th>
							<th style="min-width: 56px; text-align: center;"><spring:message
									code="bz_route_file" /></th>
							<th style="min-width: 36px; text-align: center;"><spring:message
									code="map_file_path" /></th>
							<th style="min-width: 165px; text-align: center;"><spring:message
									code="map_table_title_operation" /></th>
							<th></th>
						</tr>
					</thead>
				</table>

			</div>
			<!-- END PAGE -->
			<img class="preview_size_fake" style="position: fixed;" />
		</div>
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<%@ include file="../shared/pageFooter.jsp"%></div>
		
	<div>
		<a href="#myModal" data-toggle="modal"></a>
	</div>
	<div class="modal hide fade" id="myModal">
		<div id="modalHeader1" class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<div style="padding-top: 20px;">
				<label style="float: left;"><spring:message code="map_pictrue_juli" /></label> 
				<input id="realDis" maxlength="10" type="text" onchange="estimateOnkeyup(this)" onkeyup="estimateOnkeyupOne(this)">
				<button class="btn btn-small clearPaper"
					style="float: right; margin-right: 40px;"><spring:message code="map_pictrue_clear" /></button>
			</div>
		</div>
		<div id="modalHeader2" class="modal-header hide">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<div style="padding-top: 20px;">
				<spring:message code="map_zsel_yuandian" />
				<button class="btn btn-small clearPaper"
					style="float: right; margin-right: 40px;"><spring:message code="map_pictrue_clear" /></button>
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
			<div class="hide">
				<input id="typeModal" type="text">
				<input id="pixDis" type="text">
				<input id="pointX" type="text">
				<input id="pointY" type="text">
			</div>
			<button id="scaleOk" class="btn"><spring:message code="common_confirm" /></button>
		</div>
	</div>
	<!-- END FOOTER -->

	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script
		src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/plugins/raphael-min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/mapEdit.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/mapMng.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var i18n_edit = '<spring:message code="common_edit" />',
		   i18n_delete = '<spring:message code="common_delete" />',
		   i18n_fuzhi  = '<spring:message code="map_fuzhi" />';
		var oTableMap,validForm;
		var place;
		var i18n_delete = '<spring:message code="common_delete" />';
		var i18n_info = '<spring:message code="map_info" />';
		var i18n_deleteInfo = '<spring:message code="map_delete" />';
		var i18n_sameplace = '<spring:message code="map_samePlace" />';
		var i18n_floorNo = '<spring:message code="floorNo" />';
		var i18n_format = '<spring:message code="map_format" />';
		var i18n_ul = '<spring:message code="map_coor_ul" />';
		var i18n_ll = '<spring:message code="map_coor_ll" />';
		var i18n_ur = '<spring:message code="map_coor_ur" />';
		var i18n_lr = '<spring:message code="map_coor_lr" />';
		var i18n_max = '<spring:message code="map_pictrue_max" />';
		var i18n_position = '<spring:message code="map_home_position" />';
		var i18n_distancelength = '<spring:message code="map_distance_length" />';
		var i18n_isscale = '<spring:message code="map_scale" />';
		var i18n_msg_distance = '<spring:message code="msg_distance" />';
		$(document).ready(function() {
	            App.init();
	            MapMng.initSelect();
	            MapMng.initMapTable();
	            validForm = $(".demoform").Validform({tiptype:3,
					datatype:{
						"sn":/^(-|\+)?\d+$/,
						"jd":function(gets,obj,curform,regxp){
						if(isNaN(gets))
							{
							return false;
							}
		                  if(gets>360||gets<0)
                          {
                          return false;
                          }
						},
	                      "jp":function(gets,obj,curform,regxp){
	                    	  if(gets=="")
	                    		  {
	                    		  return false;
	                    		  }
	                    	  else
	                    		  {
	                    	  var fi =gets.split(".")[gets.split(".").length-1];
	                    	    if (fi!=""&&fi!="jpg"&&fi!="png"&&fi!="PNG"&&fi!="JPG"&&fi!="SVG"&&fi!="svg")
	                    	    {
	                    	        return false;
	                    	    }

	                          }
	                      },
	                       "svg":function(gets,obj,curform,regxp){
	                    	   if(gets!="")
	                                  {
	                              var fi =gets.split(".")[gets.split(".").length-1];
	                                if (fi!=""&&fi!="SVG"&&fi!="svg")
	                                {
	                                    return false;
	                                }

	                              }
	                          },
	                       "blc":function(gets,obj,curform,regxp){
	                              if(gets=="")
	                                  {
	                                  return false;
	                                  }
	                              if(isNaN(gets))
	                              {
	                              return false;
	                              }
	                               if(gets<0)
                                   {
                                   return false;
                                   }
                                   if(gets==0)
                                   {
                                   return false;
                                   }
	                    

	                          },
	                           "xml":function(gets,obj,curform,regxp){
	                                  if(!gets=="")
	                                    {
	                                     var fi =gets.split(".")[gets.split(".").length-1];
	                                      if (fi!=""&&fi!="xml"&&fi!="XML")
	                                       {
	                                      return false;
	                                       }
	                                    }
	                                },
	                            "mapId":function(gets,obj,curform,regxp){
	                                 if(isNaN(gets))
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
			$('.tip').tooltip();

			MapMng.bindClickEvent();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>