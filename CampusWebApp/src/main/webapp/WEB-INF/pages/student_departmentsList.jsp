<%@page import="com.resoneuronance.campus.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Notifications From Department</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="<c:url value="/resources/css/bootstrap.css"/>"" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="<c:url value="/resources/css/font-awesome.css"/>"" rel="stylesheet" />
     <!-- MORRIS CHART STYLES-->
    <link href="<c:url value="/resources/js/morris/morris-0.4.3.min.css"/>"" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="<c:url value="/resources/css/custom.css"/>"" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">${student.college.name}</a> 
            </div>
		<div style="color: white; padding: 15px 50px 5px 50px;float: right;font-size: 16px;">
			<a href="<%=Constants.LOGOUT_URL%>" class="btn btn-danger square-btn-adjust">Logout</a> 
		</div>
        </nav>   
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
					<li class="text-center"><img src="<c:url value="/resources/img/find_user.png"/>" class="user-image img-responsive"/></li>
                    <li>
                        <a class="active-menu"  href="<%=Constants.STUDENT_PROFILE_URL%>"><i class="fa fa-user fa-3x"></i>Profile</a>
                    </li>
				 </ul> 				                           
            </div>
        </nav>    
          
        <!-- /. NAV SIDE  -->
		<div id="page-wrapper" >
			<div id="page-inner">
				<div class="row">
					<div class="col-md-2">
						<img src="<c:url value="/resources/img/find_user.png"/>" alt="Profile Photo" class="img-circle">
					</div>
					<div class="col-md-2">   
							<h3>Welcome</h3><h2>${student.name}</h2>
					</div>
					<div class="col-md-3">
					<p>
						<address>
							  <strong>Email ID:&nbsp</strong>${student.email}
						</address>
						<address>
							  <strong>Phone:&nbsp</strong>(+91) 8420101234
						</address>	
					</p>
					</div>		
					<div class="col-md-1">
						<p><a href="<%=Constants.TEACHER_SELECT_URL %>" style="text-decoration:none;"><button class="btn btn-success">Add Teacher</i></button></a></p>				
					</div>
					<div class="col-md-1">
						<p><a href="<%=Constants.DEPARTMENT_SELECT_URL %>" style="text-decoration:none;"><button class="btn btn-success">Add Department</i></button></a></p>				
					</div>
				</div>              
				<br>			
			<hr />
	
					<!--<div class="col-md-2 col-sm-4 col-xs-4">           
						<div class="noti-box">
							<span class="icon-box bg-color-red set-icon">
								<i class="fa fa-user"></i>
							</span>
							<div class="text-box" >
								<p class="main-text"><a href="teachNotify.html" style="text-decoration:none"><small>Mrs. AR. Joshi</small></a></p>
								<p class="text-muted">Notifications&nbsp <span class="badge">10</span></p>
							</div>
						</div>
					</div>
					<!--Div End-->	
					<c:forEach var="department" items="${student.departments}">
					<div class="row" >
						<div class="col-md-2 text-center">
							<div class="form-group">
								<label><i class="fa fa-user fa-2x"></i></label>
							</div>												
						</div>
						<div class="col-md-8 text-left">
							<div class="form-group">
								<label class="main-text">${department.name}</label>
							</div>
						</div>
						<div class="col-md-2 text-center">
						<form action="showDepartmentNotifications" method="post">
								<div class="form-group">
									<input type="hidden" value="${department.id}" name="id"/>
									<input type="hidden" value="${department.name}" name="name"/>
									<label><button type="submit" style="border:none;background:transparent"><i class="fa fa-bullhorn fa-2x"><span class="badge">${department.latestCount}</span></i></button></label>
								</div>
						</form>
						</div>
					</div>
					<hr />
					</c:forEach>
					<!--Div End-->
					<!-- <div class="row" >
						<div class="col-md-2 text-center">
							<div class="form-group">
								<label><i class="fa fa-user fa-2x"></i></label>
							</div>												
						</div>
						<div class="col-md-8 text-left">
							<div class="form-group">
								<label class="main-text">Teacher Name</label>
							</div>
						</div>
						<div class="col-md-2 text-center">
							<div class="form-group">
								<label><i class="fa fa-bullhorn fa-2x"><span class="badge">10</span></i></label>
							</div>
						</div>
					</div>
					Div End
				
							
			Row	
			<hr />  -->
			</div>
            <!-- /. PAGE INNER  -->
        </div>
         <!-- /. PAGE WRAPPER  -->
    </div>
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<c:url value="/resources/js/jquery.metisMenu.js"/>"></script>
    <!-- MORRIS CHART SCRIPTS 
    <script src="<c:url value="/resources/js/morris/raphael-2.1.0.min.js"/>"></script>
    <script src="<c:url value="/resources/js/morris/morris.js"/>"></script>-->
	
	<!-- CUSTOM SCRIPTS -->
    <script src="<c:url value="/resources/js/custom.js"/>"></script>
    
   
</body>
</html>
    