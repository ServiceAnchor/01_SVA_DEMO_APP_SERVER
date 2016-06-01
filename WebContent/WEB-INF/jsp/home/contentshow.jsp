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
							<spring:message code="menu_info_manage" />
							<i class="icon-angle-right"></i>
						</li>
						<li><spring:message code="bz_showparam" /></li>
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
				<form class="form-horizontal demoform">
					<div id="editBox" class="portlet light-grey hide">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-edit icon-large" style="background: none"></i><spring:message code="paramconfig" />
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6" style="padding:10px;">
								<div class="row-fluid">
									<div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">
										<div class="control-group">
											<label class="control-label" for=placeSel><spring:message code="mwc_zhanguan1" /></label>
											<div class="controls">
												<select datatype="*" id="placeSel" nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;"   >
													<option value=" "></option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
											<div class="controls">
												<select datatype="*" id="zSel" nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
													<option value=" "></option>
												</select>
											</div>
										</div>
										<!-- 密度 -->
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="heatmap_density" /></label>
												<div class="controls">
												<select id="densitySel" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
												class="">
												<option value="1"></option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="10">10</option>
												<option value="15">15</option>
												<option value="20">20</option>
												</select>
												</div>
										 </div>
										<!-- 扩散度 -->
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="heatmap_diffusance" /></label>
												<div class="controls">
												<select id="radiusSel" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
													class="">
													<option value="20"></option>
													<option value="20">20</option>
													<option value="30">30</option>
													<option value="40">40</option>
													<option value="50">50</option>
													<option value="100">100</option>
													<option value="150">150</option>
													<option value="200">200</option>
												</select>
												</div>
										</div>										
										
										<div class="control-group">
											<label class="control-label" for=placeSel><spring:message code="mwc_zhanguan2" /></label>
											<div class="controls">
												<select datatype="*"  id="placeSel2"  nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;" >
												    <option value=" "></option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
											<div class="controls">
												<select datatype="*" id="zSel2"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
													<option value=" "></option>
												</select>
											</div>
										</div>
                                        <!-- 密度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_density" /></label>
                                                <div class="controls">
                                                <select id="densitySel1" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
                                                class="">
                                                <option value="1"></option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                </select>
                                                </div>
                                         </div>
                                        <!-- 扩散度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                <div class="controls">
                                                <select id="radiusSel1" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                    class="">
                                                    <option value="20"></option>
                                                    <option value="20">20</option>
                                                    <option value="30">30</option>
                                                    <option value="40">40</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                    <option value="150">150</option>
                                                    <option value="200">200</option>
                                                </select>
                                                </div>
                                        </div>              										
										<div class="control-group">
											<label class="control-label" for=placeSel><spring:message code="mwc_zhanguan3" /></label>
											<div class="controls">
												<select datatype="*"  id="placeSel3"  nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;" >
													<option value=" "></option>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
											<div class="controls">
												<select datatype="*" id="zSel3"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
													<option value=" "></option>
												</select>
											</div>
										</div>
                                        <!-- 密度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_density" /></label>
                                                <div class="controls">
                                                <select id="densitySel2" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
                                                class="">
                                                <option value="1"></option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                </select>
                                                </div>
                                         </div>
                                        <!-- 扩散度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                <div class="controls">
                                                <select id="radiusSel2" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                    class="">
                                                    <option value="20"></option>
                                                    <option value="20">20</option>
                                                    <option value="30">30</option>
                                                    <option value="40">40</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                    <option value="150">150</option>
                                                    <option value="200">200</option>
                                                </select>
                                                </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for=placeSel><spring:message code="mwc_zhanguan4" /></label>
                                            <div class="controls">
                                                <select datatype="*"  id="placeSel4"  nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;" >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
                                            <div class="controls">
                                                <select datatype="*" id="zSel4"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- 密度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_density" /></label>
                                                <div class="controls">
                                                <select id="densitySel3" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
                                                class="">
                                                <option value="1"></option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                </select>
                                                </div>
                                         </div>
                                        <!-- 扩散度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                <div class="controls">
                                                <select id="radiusSel3" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                    class="">
                                                    <option value="20"></option>
                                                    <option value="20">20</option>
                                                    <option value="30">30</option>
                                                    <option value="40">40</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                    <option value="150">150</option>
                                                    <option value="200">200</option>
                                                </select>
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
							<div class="span6" style="padding:10px;">
                  				<div class="row-fluid">
                      				<div class="span12" style="outline:0px solid #E6E4E4;padding:20px 0;">	
                                        <div class="control-group">
                                            <label class="control-label" for=placeSel><spring:message code="mwc_zhanguan5" /></label>
                                            <div class="controls">
                                                <select datatype="*"  id="placeSel5"  nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;" >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
                                            <div class="controls">
                                                <select datatype="*" id="zSel5"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- 密度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_density" /></label>
                                                <div class="controls">
                                                <select id="densitySel4" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
                                                class="">
                                                <option value="1"></option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                </select>
                                                </div>
                                         </div>
                                        <!-- 扩散度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                <div class="controls">
                                                <select id="radiusSel4" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                    class="">
                                                    <option value="20"></option>
                                                    <option value="20">20</option>
                                                    <option value="30">30</option>
                                                    <option value="40">40</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                    <option value="150">150</option>
                                                    <option value="200">200</option>
                                                </select>
                                                </div>
                                        </div> 
                                        <div class="control-group">
                                            <label class="control-label" for=placeSel><spring:message code="mwc_zhanguan6" /></label>
                                            <div class="controls">
                                                <select datatype="*"  id="placeSel6"  nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;" >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
                                            <div class="controls">
                                                <select datatype="*" id="zSel6"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- 密度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel5"><spring:message code="heatmap_density" /></label>
                                                <div class="controls">
                                                <select id="densitySel5" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
                                                class="">
                                                <option value="1"></option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                </select>
                                                </div>
                                         </div>
                                        <!-- 扩散度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                <div class="controls">
                                                <select id="radiusSel5" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                    class="">
                                                    <option value="20"></option>
                                                    <option value="20">20</option>
                                                    <option value="30">30</option>
                                                    <option value="40">40</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                    <option value="150">150</option>
                                                    <option value="200">200</option>
                                                </select>
                                                </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for=placeSel><spring:message code="mwc_zhanguan7" /></label>
                                            <div class="controls">
                                                <select datatype="*"  id="placeSel7"  nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;" >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
                                            <div class="controls">
                                                <select datatype="*" id="zSel7"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- 密度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_density" /></label>
                                                <div class="controls">
                                                <select id="densitySel6" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
                                                class="">
                                                <option value="1"></option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                </select>
                                                </div>
                                         </div>
                                        <!-- 扩散度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                <div class="controls">
                                                <select id="radiusSel6" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                    class="">
                                                    <option value="20"></option>
                                                    <option value="20">20</option>
                                                    <option value="30">30</option>
                                                    <option value="40">40</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                    <option value="150">150</option>
                                                    <option value="200">200</option>
                                                </select>
                                                </div>
                                        </div> 
                                        <div class="control-group">
                                            <label class="control-label" for=placeSel><spring:message code="mwc_zhanguan8" /></label>
                                            <div class="controls">
                                                <select datatype="*"  id="placeSel8"  nullmsg='<spring:message code="map_store_name" />' style="width: 59%;height: 15%;" >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label" for="zSel"><spring:message code="message_table_title_floor" /></label>
                                            <div class="controls">
                                                <select datatype="*" id="zSel8"  nullmsg='<spring:message code="all_floor_choose" />'  style="width: 59%;height: 15%;"  >
                                                    <option value=" "></option>
                                                </select>
                                            </div>
                                        </div>
                                        <!-- 密度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_density" /></label>
                                                <div class="controls">
                                                <select id="densitySel7" style="height: 32px;width: 82px" datatype="xzsc" data-placeholder="<spring:message code="heatmap_density" />"
                                                class="">
                                                <option value="1"></option>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="10">10</option>
                                                <option value="15">15</option>
                                                <option value="20">20</option>
                                                </select>
                                                </div>
                                         </div>
                                        <!-- 扩散度 -->
                                        <div class="control-group">
                                            <label class="control-label" for="zSel1"><spring:message code="heatmap_diffusance" /></label>
                                                <div class="controls">
                                                <select id="radiusSel7" style="height: 32px;width: 82px"  datatype="xzsc" data-placeholder="<spring:message code="heatmap_diffusance" />"
                                                    class="">
                                                    <option value="20"></option>
                                                    <option value="20">20</option>
                                                    <option value="30">30</option>
                                                    <option value="40">40</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                    <option value="150">150</option>
                                                    <option value="200">200</option>
                                                </select>
                                                </div>
                                        </div>                                                                                  
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="bz_shijian" /></label>
											<div class="controls">
												<select id="periodSel" datatype="*"  data-placeholder="<spring:message code="heatmap_period" />" nullmsg='<spring:message code="bz_shijian_shuru" />'  style="width: 59%;height:15%;padding:2px 0 2px 0;"  class="chosen-select" >
         											<option value=" "></option>
         										</select>
											</div>
										</div>
										<!-- nullmsg='<spring:message code="bzparam_input_coefficient" />'  -->
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="bzparam_coefficient" /></label>
											<div class="controls">
												<input id="coefficient" datatype="paramYz"  value ="1" data-placeholder="<spring:message code="heatmap_period" />"  style="width: 59%;height:15%;padding:2px 0 2px 0;" />
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="zSel"><spring:message code="bz_kasihishijian" /></label>
											<div class="controls">
												<input id="select_time_begin_tab1" datatype="*" onclick = "contentshow.showDate('select_time_begin_tab1');" datatype="*"  nullmsg='<spring:message code="all_choose_starttime" />' style="width: 180px" readonly/> 
												<span class="add-on" onclick = "contentshow.showDate('select_time_begin_tab1');">
													<i class="icon-calendar"></i>
												</span>
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
									<button id="cancel" style="height: 30px" onclick="hideAdd()" type="button" class="btn"><spring:message code="common_cancel" /></button>
								<span class="sameInfo" ></span>
								</div>
							</div>
						</div>
					</div>
				</form>
				<div>
					<!--  <button id="add" style="height: 30px;" type="button" class="btn btn-primary" ><spring:message code="common_add" /></button>
					-->
					</div>  
					
				<!-- 	<table id="table" class="table table-bordered" style="text-align:center">
						<thead>
							<tr>
								<th style="width:100px"><spring:message code="message_table_title_location" /></th>
								<th style="width:50px"><spring:message code="message_table_title_floor" /></th>
								<th style="width:50px"><spring:message code="category_category" /></th>
								<th style="width:50px"><spring:message code="area_areaName" /></th>
								<th style="width:100px"><spring:message code="message_table_title_x" /></th>
								<th style="width:100px"><spring:message code="message_table_title_y" /></th>
								<th style="width:100px"><spring:message code="input_table_title_x1" /></th>
								<th style="width:100px"><spring:message code="input_table_title_y1" /></th>
								<th style="width:188px"><spring:message code="message_table_title_operation" /></th>
							</tr>
						</thead>			
					</table>
					 -->	
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
	       		
	       	</div>
	       	<div class="modal-footer">
	       		<input type="button" id= "closeId" onclick="closeModel()" value=" <spring:message code="common_close" />">
			</div>

       	</div>
       	<div class="modal hide fade" id="myModal1">
			<div class="modal-header">
				<button type="button"  class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<div style="padding-top: 20px;">
					<spring:message code="map_choose_posistion" />
					<button class="btn btn-small clearPaper"
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
						style="outline: 1px solid #B3B1B1;">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="hide">
					<input id="x0" type="text">
					<input id="y0" type="text">
					<input id="x1" type="text">
					<input id="y1" type="text">
				</div>
				<button id="Ok" disabled="disabled"  class="btn"><spring:message code="common_confirm" /></button>
			</div>
		</div>
       	<div class="modal hide fade" id="areaModel">
			<div class="modal-header">
				<button type="button" style="margin-top: -9px;" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>				
			</div>
			<div class="modal-body">
				<div id="alertBoxScale" class="hide">
					<div class="alert fade in">
						<div id="areainfoScale"></div>
					</div>
				</div>
				<div id="areadivCon" style="text-align: center; height: 500px;">
					<div id="areapreview" class="demo-wrapper"
						style="outline: 1px solid #B3B1B1;">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="hide">
					
				</div>
				
			</div>
		</div>
   		<!-- END FOOTER -->
   
		<%@ include file="../shared/importJs.jsp"%>
		<!-- BEGIN PAGE LEVEL SCRIPTS -->
		<script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script> 
		<script src="<c:url value='/plugins/raphael-min.js'/>"type="text/javascript"></script>
		<script src="<c:url value='/js/mapEdit.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/js/contentshow.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/plugins/layer/layer.js'/>"type="text/javascript"></script> 
		<script	src="<c:url value='/plugins/echarts-2.2.5/build/source/echarts-all.js'/>"type="text/javascript"></script>
		<script src="<c:url value='/plugins/wDatePicker/WdatePicker.js'/>"type="text/javascript"></script>
		<script src="<c:url value='/js/contentshow.js'/>" type="text/javascript"></script>
		<!-- END PAGE LEVEL SCRIPTS -->  

		<script type="text/javascript">
		var validForm;
			var i18n_delete = '<spring:message code="common_delete" />',
	        i18n_info = '<spring:message code="map_info" />',
	        i18n_time = '<spring:message code="linemap_time" />',
	        i18n_title = '<spring:message code="linemap_chart_title" />', 
	        i18n_title1 = '<spring:message code="barmap_chart_title" />',
	        i18n_tag = '<spring:message code="linemap_chart_tag" />',
	        i18n_person = '<spring:message code="linemap_chart_person" />', 
	        i18n_max = '<spring:message code="linemap_chart_max" />', 
	        i18n_min = '<spring:message code="linemap_chart_min" />', 
	        i18n_avg = '<spring:message code="linemap_chart_avg" />',
	        i18n_dataview = '<spring:message code="common_echart_dataview" />',
	        i18n_close = '<spring:message code="common_close" />',
	        i18n_refresh = '<spring:message code="common_refresh" />',
	        i18n_saveimg = '<spring:message code="common_echart_saveimg" />',
	        i18n_language = '<spring:message code="time_language" />';  
	       	$(document).ready(function() {
	       		validForm =    $(".demoform").Validform({       
					tiptype:3,
					datatype:{
						"xzsc":function(gets,obj,curform,regxp){
							}	
					}});
			       
				App.init();
				contentshow.initDropdown();
				contentshow.bindClickEvent();
			});
		</script>
   <!-- END JAVASCRIPTS -->
	</body>
</html>