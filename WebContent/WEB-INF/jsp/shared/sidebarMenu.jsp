<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar navbar-collapse collapse">
    <!-- BEGIN SIDEBAR MENU -->
    <ul class="page-sidebar-menu">
        <li>
            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
            <div class="sidebar-toggler">
            </div>
            <div class="clearfix"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
        </li>
        <c:choose>
            <c:when test="${auth eq 'root'}">
                    <c:choose>
                        <c:when test="${showOverView}">  
                            <li class="start active open">
                                <a href="<c:url value='/home/showOverView' />"> <i class="icon-new icon-A1B2"></i><span class="toggleMenuTxt"><spring:message
                                    code="global_overview" /></span></a>
                        </c:when>  
                        <c:otherwise>  
                            <li class="start">
                                <a href="<c:url value='/home/showOverView' />"> <i class="icon-new icon-A1B2""></i><span class="toggleMenuTxt"><spring:message
                                code="global_overview" /></span></a>
                        </c:otherwise>
                    </c:choose>
                <c:if test = "${fn:contains(menu,'key_storeManage') or menu eq 'all' or fn:contains(menu,'key_svaManage') or fn:contains(menu,'key_mapManage') or fn:contains(menu,'key_messagePush') or fn:contains(menu,'key_sellerInfo') or fn:contains(menu,'key_areaCategory') or fn:contains(menu,'key_areaInfo') or fn:contains(menu,'key_role') or fn:contains(menu,'key_account') }" >
                <c:choose>  
                    <c:when test="${infoMng}">  
                        <li class="start active open">
                            <a href="javascript:;"> 
                            <img src="<c:url value='/images/header/navicon1.png'/>">
                            <span class="toggleMenuTxt"><spring:message code="menu_info_manage" /></span>
                            <span class="arrow open"></span>
                        </a>
                    </c:when>
                    <c:otherwise>  
                        <li class="start">
                            <a href="javascript:;"> 
                                <img src="<c:url value='/images/header/navicon1.png'/>">
                                <span class="toggleMenuTxt"><spring:message code="menu_info_manage" /></span>
                                <span class="arrow "></span>
                            </a>
                    </c:otherwise>  
                </c:choose>
                </c:if>
                    <ul class="sub-menu">
                        <c:if test = "${fn:contains(menu,'key_storeManage') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${storeMng}">
                                    <li class="active">
                                </c:when>
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showStoreMng' />"><i class="round" style="background-color: #614261;"></i><span>
                                <spring:message code="menu_info_manage_store" /></span>
                            </a>
                            </li>
                        </c:if> 
                        <!--sva管理 -->
                        <c:if test = "${fn:contains(menu,'key_svaManage') or menu eq 'all' }" >
                            <c:choose>
                                <c:when test="${svaMng}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showSvaMng' />"><i class="round" style="background-color: #F78803;"></i>
                                <span><spring:message code="menu_info_manage_sva" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_mapManage') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${mapMng}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showMapMng' />"><i class="round" style="background-color: #EA1C24;"></i>
                                <span><spring:message code="menu_info_manage_map" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_messagePush') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${msgMng}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showMsgMng' />"><i class="round" style="background-color: #E4E406;"></i>
                                <span><spring:message code="menu_info_manage_message" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_sellerInfo') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${sellerMng}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showSellerMng' />">
                                <i class="round" style="background-color: #2EE415;"></i>
                                <span><spring:message code="menu_info_manage_seller" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_areaCategory') or menu eq 'all' }" >
                            <c:choose>
                                <c:when test="${categoryMng}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showCategoryMng' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="category_Regional" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_areaInfo') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${InputMng}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showInputMng' />">
                                <i class="round" style="background-color: #159FE4;"></i>
                                <span><spring:message code="shop_name" /></span>
                            </a>
                            </li>
                        </c:if>                         
                        <c:choose>  
                                <c:when test="${showElectronic}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showElectronic' />">
                                <i class="round" style="background-color: #159FE4;"></i>
                                <span><spring:message code="Electronic_info" /></span>
                            </a>
                            </li> 
                                              
                        <c:if test = "${fn:contains(menu,'key_role') or menu eq 'all' }" >
                            <c:choose>
                                <c:when test="${role}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise> 
                            </c:choose>
                            <a href="<c:url value='/home/role' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="role_manage"/></span>
                            </a>
                            </li>
                        </c:if> 
                        <c:if test = "${fn:contains(menu,'key_account') or menu eq 'all' }" >   
                            <c:choose>
                                <c:when test="${account}">  
                                    <li class="active">
                                </c:when>
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise> 
                            </c:choose>
                            <a href="<c:url value='/home/account' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="account_manage"/></span>
                            </a>
                            </li>
                        </c:if>
                     <!--   <c:choose>
                                <c:when test="${content}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise> 
                            </c:choose>
                                <a href="<c:url value='/home/content' />"><i class="round" style="background-color: #E64C66;"></i><span><spring:message code="bz_showparam" /></span></a>
                            </li>    -->                       
                    </ul>
                </li>
            </c:when>   
        </c:choose>
        <c:choose>
            <c:when test="${auth eq 'root' or tourists eq 'all' }"> 
                <c:choose>
                    <c:when test="${customerStat}">  
                        <li class="active open">
                            <a href="javascript:;"><img src="<c:url value='/images/header/navicon2.png'/>">
                                <span class="toggleMenuTxt"><spring:message code="menu_customer_analyse" /></span>
                                <span class="arrow open"></span>
                            </a>
                    </c:when>
                    <c:otherwise>  
                        <li class="">
                        <a href="javascript:;"> 
                            <img src="<c:url value='/images/header/navicon2.png'/>">
                            <span class="toggleMenuTxt"><spring:message code="menu_customer_analyse" /></span>
                            <span class="arrow "></span>
                        </a>
                    </c:otherwise>
                </c:choose>
                <ul class="sub-menu">
                    <c:if test = "${fn:contains(menu,'key_customerHeamap') or menu eq 'all'or tourists eq 'all' }" >
                        <c:choose>  
                            <c:when test="${heatmap}">  
                                <li class="active">
                            </c:when>  
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>  
                        </c:choose>
                        <a href="<c:url value='/home/showHeatmap' />">
                            <i class="round" style="background-color: #E64C66;"></i>
                            <span><spring:message code="menu_customer_analyse_heatmap" /></span>
                        </a>
                        </li>
                    </c:if> 
                    <c:if test = "${fn:contains(menu,'key_customerPeriodHeamap') or menu eq 'all' }" >
                        <c:choose>
                            <c:when test="${heatmap5}">  
                                <li class="active">
                            </c:when>  
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>  
                        </c:choose>
                        <a href="<c:url value='/home/showHeatmap5' />">
                            <i class="round" style="background-color: #E64C66;"></i>
                            <span><spring:message code="menu_customer_analyse_heatmap5" /></span>
                        </a>
                        </li>
                    </c:if>
                    <c:if test = "${fn:contains(menu,'key_customerScattermap') or menu eq 'all' }" >
                        <c:choose>  
                            <c:when test="${scattermap}">
                                <li class="active">
                            </c:when>
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>
                        </c:choose>
                        <a href="<c:url value='/home/showScattermap' />">
                            <i class="round" style="background-color: #FFAB00;"></i>
                            <span><spring:message code="menu_customer_analyse_scattermap" /></span>
                        </a>
                        </li>
                    </c:if>
                    <c:if test = "${fn:contains(menu,'key_historicalTrack') or menu eq 'all' }" >
                        <c:choose>  
                            <c:when test="${follow}">  
                                <li class="active">
                            </c:when>  
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>  
                        </c:choose>
                        <a href="<c:url value='/home/showFollow' />">
                            <i class="round" style="background-color: #FFAB00;"></i>
                            <span><spring:message code="follow_person_history" /></span>
                        </a>
                        </li>
                    </c:if>
                    <c:if test = "${fn:contains(menu,'key_CustomerFlowLinemap') or menu eq 'all' }" >
                        <c:choose>  
                            <c:when test="${linemap}">  
                                <li class="active">
                            </c:when>  
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>  
                        </c:choose>
                        <a href="<c:url value='/home/showLinemap' />">
                            <i class="round" style="background-color: #FFAB00;"></i>
                            <span><spring:message code="history_area_title" /></span>
                        </a>
                        </li>
                    </c:if>
                    <!--  
                    <c:if test = "${fn:contains(menu,'key_CustomerFlowRangeLinemap') or menu eq 'all' }" >
                        <c:choose>  
                            <c:when test="${barmap}">  
                                <li class="active">
                            </c:when>  
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>  
                        </c:choose>
                        <a href="<c:url value='/home/showBarmap' />">
                            <i class="round" style="background-color: #FFAB00;"></i>
                            <span><spring:message code="menu_customer_analyse_barmap" /></span>
                        </a>
                        </li>
                    </c:if>
                    <c:if test = "${fn:contains(menu,'key_CustomerFlowRangeLinemap') or menu eq 'all' }" >
                        <c:choose>  
                            <c:when test="${rangemap}">  
                                <li class="active">
                            </c:when>  
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>  
                        </c:choose>
                        <a href="<c:url value='/home/showRangemap' />">
                            <i class="round" style="background-color: #FFAB00;"></i>
                            <span><spring:message code="menu_customer_analyse_rangemap" /></span>
                        </a>
                        </li>
                    </c:if>
                    <c:if test = "${fn:contains(menu,'key_areaLocation') or menu eq 'all' }" >
                        <c:choose>  
                            <c:when test="${areamap}"> 
                                <li class="active">
                            </c:when>
                            <c:otherwise>  
                                <li class="">
                            </c:otherwise>  
                        </c:choose>
                        <a href="<c:url value='/home/showAreamap' />">
                            <i class="round" style="background-color: #FFAB00;"></i>
                            <span><spring:message code="menu_customer_analyse_area" /></span>
                        </a>
                        </li>
                    </c:if>
                    -->
                </ul>
            </c:when>
        </c:choose>
         
        <c:choose>
            <c:when test="${auth eq 'root'}">
            <c:if test = "${fn:contains(menu,'key_code') or menu eq 'all' or fn:contains(menu,'key_staticAccuyacy') or fn:contains(menu,'key_estimateDeviation') or fn:contains(menu,'key_dynamicAccuyacy') or fn:contains(menu,'key_positionlatency') or fn:contains(menu,'key_MessagePush') }" >
                <c:choose>  
                    <c:when test="${locTest}">  
                        <li class="active open">
                        <a href="javascript:;"> 
                            <img src="<c:url value='/images/header/navicon3.png'/>">
                            <span class="toggleMenuTxt"><spring:message code="ceshi_tool" /></span>
                            <span class="arrow open"></span>
                        </a>
                    </c:when>  
                    <c:otherwise>  
                        <li class="">
                            <a href="javascript:;">
                                <img src="<c:url value='/images/header/navicon3.png'/>">
                                <span class="toggleMenuTxt"><spring:message code="ceshi_tool" /></span>
                                <span class="arrow "></span>
                            </a>
                    </c:otherwise>
                </c:choose>
                </c:if>
                    <ul class="sub-menu">
                        <c:if test = "${fn:contains(menu,'key_code') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${codeMng}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showCodeMng' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="menu_location_code" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_estimateDeviation') or menu eq 'all' }">  
                            <c:choose>  
                                <c:when test="${estimate}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>
                                    <li class="">
                                </c:otherwise>  
                            </c:choose>
                            <a href="<c:url value='/home/showEstimate' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="menu_location_estimate" /></span>
                            </a>
                            </li>
                        </c:if>
                    <!--    <c:if test = "${fn:contains(menu,'key_summaryDisplay') or menu eq 'all' }">
                            <c:choose>  
                                <c:when test="${accuracyResult}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value='/home/showAccuracy' />">
                                <i class="round" style="background-color: #FFAB00;"></i>
                                <span><spring:message code="menu_location_result" /></span>
                            </a>
                            </li>
                        </c:if>
                     -->    
                        <c:if test = "${fn:contains(menu,'key_staticAccuyacy') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${staticAccuracy}">  
                                    <li class="active">
                                </c:when> 
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value='/home/staticAccuracy' />">
                                <i class="round" style="background-color: #FFAB00;"></i>
                                <span><spring:message code="static_menu" /></span>
                            </a>
                            </li>
                        </c:if> 
                        <c:if test = "${fn:contains(menu,'key_dynamicAccuyacy') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${dynamicAccuracy}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value='/home/dynamicAccuracy' />">
                                <i class="round" style="background-color: #FFAB00;"></i>
                                <span><spring:message code="dynamic_menu" /></span>
                            </a>
                            </li>
                        </c:if> 
                        <c:if test="${fn:contains(menu,'key_positionlatency') or menu eq 'all' }">
                            <c:choose>  
                                <c:when test="${locationdelay}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value='/home/showLocationDelay' />">
                                <i class="round" style="background-color: #FFAB00;"></i>
                                <span><spring:message code="position_latency1" /></span>
                            </a>
                            </c:if>
                            <c:if test="${fn:contains(menu,'key_MessagePush') or menu eq 'all'}">
                             <c:choose>  
                                <c:when test="${messagepush}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value='/home/showMessagePush' />">
                                <i class="round" style="background-color: #FFAB00;"></i>
                                <span><spring:message code="message_push" /></span>
                            </a> 
                            </c:if>
                            <c:choose>  
                                <c:when test="${phone}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value='/home/showPhone' />">
                                <i class="round" style="background-color: #FFAB00;"></i>
                                <span>Phone Number</span>
                            </a>                                                 
                    </ul>
                </li>
            </c:when>
        </c:choose>
    <c:if test="${fn:contains(menu,'key_bluemixConnection') or menu eq 'all' or fn:contains(menu,'key_ping') or fn:contains(menu,'key_pRRUConfig') or fn:contains(menu,'key_paramConfig') or fn:contains(menu,'key_versionDownload') or fn:contains(menu,'key_APKDownload') }">
        <c:choose>
            <c:when test="${tools}">  
                <li class="start active open">
                <a href="javascript:;"> <img src="<c:url value='/images/header/navicon4.png'/>"><span class="toggleMenuTxt"><spring:message code="menu_tools" /></span><span class="arrow open"></span></a>
            </c:when>  
            <c:otherwise>  
                <li class="start">
                <a href="javascript:;"> <img src="<c:url value='/images/header/navicon4.png'/>"><span class="toggleMenuTxt"><spring:message code="menu_tools" /></span><span class="arrow "></span></a>
            </c:otherwise>  
        </c:choose>
        </c:if>   
            <ul class="sub-menu">
                 <c:choose>
                    <c:when test="${auth eq 'root'}">
                        <c:if test = "${fn:contains(menu,'key_bluemixConnection') or menu eq 'all' }" >
                        <c:choose>
                            <c:when test="${bluemix}">
                                <li class="active">
                            </c:when>
                            <c:otherwise>
                                <li class="">
                            </c:otherwise>
                        </c:choose>
                        <a href="<c:url value='/home/showBluemix' />">
                            <i class="round" style="background-color: #E64C66;"></i>
                            <span><spring:message code="menu_tools_bluemix" /></span>
                        </a>
                        </li>
                    </c:if>
                    </c:when>
                </c:choose>
                
                <c:choose>
                    <c:when test="${auth eq 'root'}">
                        <c:if test = "${fn:contains(menu,'key_ping') or menu eq 'all' }" >
                            <c:choose>  
                                <c:when test="${ping}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value='/home/showPing' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span>Ping</span>
                            </a>
                            </li>
                        </c:if> 
                        <c:if test = "${fn:contains(menu,'key_pRRUConfig') or menu eq 'all' }" >    
                            <c:choose>
                                <c:when test="${pRRU}">  
                                    <li class="active">
                                </c:when>
                                <c:otherwise>
                                    <li class="">
                                </c:otherwise> 
                            </c:choose>
                            <a href="<c:url value='/home/showpRRU' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="tools_pRRU" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_paramConfig') or menu eq 'all' }" >
                            <c:choose>
                                <c:when test="${paramconfig}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise> 
                            </c:choose>
                            <a href="<c:url value='/home/showparam' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="paramconfig" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_versionDownload') or menu eq 'all' }" >
                            <c:choose>
                                <c:when test="${down}">
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>
                                    <li class="">
                                 </c:otherwise> 
                            </c:choose>
                            <a href="<c:url value='/home/showDown' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="down_version_downversion" /></span>
                            </a>
                            </li>
                        </c:if>
                        <c:if test = "${fn:contains(menu,'key_APKDownload') or menu eq 'all' }" >
                            <c:choose>
                                <c:when test="${qrcode}">  
                                    <li class="active">
                                </c:when>  
                                <c:otherwise>  
                                    <li class="">
                                </c:otherwise> 
                            </c:choose>
                            <a href="<c:url value='/home/uploadQRcode' />">
                                <i class="round" style="background-color: #E64C66;"></i>
                                <span><spring:message code="menu_customer_analyse_APK"/></span>
                            </a>
                            </li>
                        </c:if>
                        
                    </c:when>
                </c:choose> 
            </ul>
            </li>
      <!--    <li class="last"><a href="<c:url value='/down/contentShow' />"> <img src="<c:url value='/images/bz.png'/>">
                <span class="title toggleMenuTxt"><spring:message code="huangzhou_manhua" /></span>
        </a></li>  -->         
        <li class="last"><a href="<c:url value='/account/logout' />"> 
            <img src="<c:url value='/images/header/navicon7.png'/>">
            <span class="title toggleMenuTxt"><spring:message code="menu_logout" /></span>
            </a>
        </li>
    </ul>
    <!-- END SIDEBAR MENU -->
</div>
