<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	        <c:when test="${tools}">  
	            <li class="start active open">
	            <a href="javascript:;"> <i class="icon-new icon-A1B2""></i><span><spring:message code="menu_tools" /></span><span class="arrow open"></span></a>
	        </c:when>  
	        <c:otherwise>  
	            <li class="start">
	            <a href="javascript:;"> <i class="icon-new icon-A1B2""></i><span><spring:message code="menu_tools" /></span><span class="arrow "></span></a>
	        </c:otherwise>  
	    </c:choose>
		
			<ul class="sub-menu">
				<c:choose>  
			        <c:when test="${bluemix}">  
			            <li class="active">
			        </c:when>  
			        <c:otherwise>  
			            <li class="">
			        </c:otherwise>  
			    </c:choose>
					<a href="<c:url value='/home/showBluemix' />"><i class="round" style="background-color: #E64C66;"></i><span><spring:message code="menu_tools_bluemix" /></span></a>
				</li>
			</ul>
		</li>
		<li class="last"><a href="<c:url value='/account/logout' />"> <i class="icon-new icon-A1B5"></i>
				<span class="title"><spring:message code="menu_logout" /></span>
		</a></li>
	</ul>
	<!-- END SIDEBAR MENU -->
</div>
<!-- END SIDEBAR -->