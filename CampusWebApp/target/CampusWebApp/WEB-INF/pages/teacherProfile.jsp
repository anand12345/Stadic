<%@page import="com.resoneuronance.campus.web.util.Constants"%>
<%@page import="com.resoneuronance.campus.web.controller.MainController"%>
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
    <title>Teacher</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" />
     <!-- MORRIS CHART STYLES-->
    <link href="<c:url value="/resources/js/morris/morris-0.4.3.min.css"/>" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%=Constants.TEACHER_PROFILE_URL%>">${teacher.college.name}</a> 
            </div>
		<div style="color: white; padding: 15px 50px 5px 50px;float: right;font-size: 16px;">
			<a href="logout" class="btn btn-danger square-btn-adjust">Logout</a> 
		</div>
        </nav>   
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
					<li class="text-center"><img src="<c:url value="/resources/img/find_user.png"/>" class="user-image img-responsive"/></li>
                    <li>
                        <a class="active-menu"  href="<%=Constants.TEACHER_PROFILE_URL%>"><i class="fa fa-user fa-3x"></i>Profile</a>
                    </li>
					<li>
                        <a class="active-menu" href="#" data-toggle="collapse" data-target="#collapseMenu" aria-expanded="false" aria-controls="collapseMenu">
						<i class="fa fa-home fa-3x"></i>Departments</a>
					</li>
				 </ul> 
				 <div class="collapse" id="collapseMenu">
					<div class="sidebar-collapse">
						<ul class="nav" id="sub-menu">
							<c:forEach var="department" items="${teacher.taggedDepartments}">
								<li><a class="active-menu"  href="department.${department.id}"><i class="fa fa-home fa-3x"></i>${department.name}</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>					                           
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
							<h3>Welcome</h3><h2>${teacher.name}</h2>
					</div>
					<div class="col-md-4">
					<p>
						<address>
							  <strong>Email ID:&nbsp</strong>${teacher.email}
						</address>
						<address>
							  <strong>Phone:&nbsp</strong>(+91) 8420101234
						</address>	
					</p>
					Departments :
					<c:choose> 
						<c:when test="${fn:length(teacher.taggedDepartments) == 0}">
						You are not tagged to any department
						</c:when>
						<c:otherwise>
							<c:forEach items="${teacher.taggedDepartments}" var="dept">
								${dept.name},
							</c:forEach>
						</c:otherwise>
						</c:choose>
					</div>		
					Get tagged :<form:form action="tagTeacherToDepartment" commandName="teacher" method="post">
									<form:select path="departmentToTag">
   									 	<form:options items="${teacher.untaggedDepartmentNames}" />
									</form:select>	
									<input type="submit" value="Tag"/>
								</form:form> 		
				</div>              
                <!-- /. ROW  -->
			<form:form action="notify" method="post" commandName="${teacher.currentNotification}" enctype="multipart/form-data">
				<div class="row">
					<div class="col-md-9">
						<h2>Notify Student</h2> 
						<div class="input-group">	
						<textarea class="form-control" name="notification" rows="2" placeholder="Your Message..."></textarea>													  
							<span class="input-group-btn">
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#uploadModal"><i class="fa fa-arrow-circle-o-up  fa-3x" style="font-size:57px;"></i></button>
							</span>
						</div>							
						<!-- /input-group -->
						<!-- Modal Start -->
						<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModal" aria-hidden="true">
							<div class="modal-dialog modal-sm">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
										<h4 class="modal-title" id="uploadModal">Upload Notes/Assignment</h4>
									</div>
									<div class="modal-body">
										<div class="form-group">											
											<input type="file" name="file" />
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
									</div>
								</div>
							</div>
						</div>	
					</div>
				</div>
				<br>
				<!-- /. ROW  -->
				<div class="row"> 
					<div class="col-md-3">
						<div class="form-group">
								<label>Year</label>
								<select name="year" class="form-control">
									<option>F.E</option>
									<option>S.E</option>
									<option>T.E</option>
									<option>B.E</option>
								</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
								<label>Department</label>
								<select name="departmentId" class="form-control">
									<c:forEach items="${teacher.taggedDepartments}" var="dept">
										<option value="${dept.id}">${dept.name}</option>
									</c:forEach>
									<c:forEach items="${teacher.untaggedDepartments}" var="dept">
										<option value="${dept.id}">${dept.name}</option>
									</c:forEach>
								</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
								<label>Type</label>
								<select name="format" class="form-control">
									<option>Curricular</option>
									<option>Co-Curricular</option>
								</select>
						</div>
					</div>	
					<input type="hidden" name="type" value="Teacher">				
				</div> 
				<!-- /. ROW  -->
				<div class="row">
					<div class="col-md-1">
						<button type="submit" class="btn btn-primary btn-lg">Send</button>
					</div>
					<div class="col-md-2">
						<button type="reset" class="btn btn-danger btn-lg">Reset</button>
					</div>
				</div>
			</form:form>
			<!--Row-->
			<hr />
			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-8">           
					<div class="panel panel-back noti-box">
						<span class="icon-box bg-color-red set-icon">
							<i class="fa fa-book"></i>
						</span>
						<div class="text-box" >
							<p class="main-text"><a href="<%=Constants.CURRICULAR_NOTIFICATIONS_URL %>" style="text-decoration:none">Curricular Activities</a></p>
							<p class="text-muted">Notifications&nbsp <span class="badge">${fn:length(teacher.curricularNotifications)}</span></p>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-8">           
					<div class="panel panel-back noti-box">
						<span class="icon-box bg-color-green set-icon">
							<i class="fa fa-trophy"></i>
						</span>
						<div class="text-box" >
							<p class="main-text"><a href="<%=Constants.CO_CURRICULAR_NOTIFICATIONS_URL %>" style="text-decoration:none">Co-Curricular Activities</a></p>
							<p class="text-muted">Notifications&nbsp<span class="badge">${fn:length(teacher.coCurricularNotifications)}</span></p>	
						</div>
					</div>
				</div>
            </div> 
			<!--Row-->	
			<hr />
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
    