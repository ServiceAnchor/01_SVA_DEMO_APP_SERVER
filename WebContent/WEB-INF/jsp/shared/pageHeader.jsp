<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- BEGIN HEADER -->   
<div class="header navbar navbar-inverse navbar-fixed-top">
   <!-- BEGIN TOP NAVIGATION BAR -->
  <div class="header-inner">
     <!-- BEGIN LOGO -->  
     <div class="logoBox">
		<a class="brand" href="index.html">
	     	<img src="<c:url value='/images/header/logo.png'/>"  alt="logo" />
		</a>
     </div>
     <span class="nav navbar-nav pull-left" style="margin-left: 30px;">
     <a href="/sva/home/changeLocal?local=zh">中文</a> | 
     <a href="/sva/home/changeLocal?local=en">English</a>
     </span>
     <!-- <form class="search-form search-form-header" role="form" action="index.html" >
        <div class="input-icon right">
           <i class="icon-search"></i>
           <input type="text" class="form-control input-medium input-sm" name="query" placeholder="Search...">
        </div>
     </form> -->
     <!-- END LOGO -->
     <!-- BEGIN RESPONSIVE MENU TOGGLER --> 
     <a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
     <img src="<c:url value='/images/menu-toggler.png'/>" alt=""/>
     </a> 
     <!-- END RESPONSIVE MENU TOGGLER -->
     <!-- BEGIN TOP NAVIGATION MENU -->
     <ul class="nav navbar-nav pull-right">
        <a href="<spring:message code="helpPage" />" target="_blank" class="nav navbar-nav pull-middle" ><spring:message code="help_html" /></a>
       <!--  <li class="devider">&nbsp;</li> -->
        <!-- BEGIN USER LOGIN DROPDOWN -->
        <li class="dropdown user" style="min-width: 160px;">
           <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
           <img alt="" class="avatar" src="<c:url value='/images/avatar3_small.jpg'/>"/>&nbsp;
           <span class="username">${sessionScope.username}</span>
           <i class="icon-new icon-A2B1"></i>
           </a>
           <ul class="dropdown-menu">
              <!-- <li><a href="extra_profile.html"><i class="icon-user"></i><spring:message code="menu_logout" /></a>
              </li> -->
              <li class="divider"></li>
              <li><a href="<c:url value='/account/logout' />"><i class="icon-off"></i><spring:message code="menu_logout" /></a>
              </li>
           </ul>
        </li>
        <!-- END USER LOGIN DROPDOWN -->
     </ul>
     <!-- END TOP NAVIGATION MENU -->
     
  </div>
  <!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->