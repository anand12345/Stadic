<%@page import="com.resoneuronance.campus.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Administrator</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="<c:url value = "/resources/css/bootstrap.css"/>" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="<c:url value = "/resources/css/font-awesome.css"/>" rel="stylesheet" />
     <!-- MORRIS CHART STYLES-->
    <link href="<c:url value = "/resources/js/morris/morris-0.4.3.min.css"/>" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="<c:url value = "/resources/css/custom.css"/>" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<%=Constants.ADMIN_DASHBOARD_URL%>">${college.name}</a> 
            </div>
	<div style="color: white; padding: 15px 50px 5px 50px;float: right;font-size: 16px;"><a href="logout" class="btn btn-danger square-btn-adjust">Logout</a> </div>
        </nav>   
           <!-- /. NAV TOP  -->
                <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
					<li class="text-center">
						<img src="<c:url value = "/resources/img/find_user.png"/>" class="user-image img-responsive"/>
					</li>
                    <li>
                        <a class="active-menu"  href="<%=Constants.ADMIN_DASHBOARD_URL%>"><i class="fa fa-book fa-3x"></i> Dashboard</a>
                    </li>
                </ul>              
            </div>
            
        </nav>  
        <!-- /. NAV SIDE  -->
		<div id="page-wrapper" >
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2>DASHBOARD</h2>   
							<h4>Welcome <span>Admin</span></h4>
					</div>
				</div>              
                <!-- /. ROW  -->
				<hr />
				<div class="row">
					<div class="col-md-4 col-sm-6 col-xs-8">           
						<div class="panel panel-back noti-box">
							<span class="icon-box bg-color-red set-icon">
								<i class="fa fa-user"></i>
							</span>
							<div class="text-box" >
								<p class="main-text"><a href="<%=Constants.ADD_STUDENT_URL%>" style="text-decoration:none">Student</a></p>
								<p class="text-muted">${studentsCount}</p>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-8">           
						<div class="panel panel-back noti-box">
							<span class="icon-box bg-color-green set-icon">
								<i class="fa fa-user"></i>
							</span>
						<div class="text-box" >
							<p class="main-text"><a href="<%=Constants.ADD_TEACHER_URL %>" style="text-decoration:none">Teachers</a></p>
							<p class="text-muted">${teachersCount}</p>
						</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-8">           
						<div class="panel panel-back noti-box">
							<span class="icon-box bg-color-blue set-icon">
								<i class="fa fa-home"></i>
							</span>
						<div class="text-box" >
							<p class="main-text"><a href="<%=Constants.ADD_DEPARTMENT_URL %>" style="text-decoration:none">Department</a></p>
							<p class="text-muted">${departmentsCount}</p>
						</div>
						</div>
					</div>    			     
                </div> 
				<!-- /. ROW  -->
			<form>
				<div class="row">
					<div class="col-md-9">
						<h2>Notify Student</h2>  
							<div class="form-group">
								<label>Your Message:</label>
									<textarea class="form-control" rows="3"></textarea>
							</div>
					</div>
				</div>
				<!-- /. ROW  -->
				<div class="row"> 
					<div class="col-md-3">
						<div class="form-group">
								<label>Year</label>
								<select class="form-control">
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
								<select class="form-control">
									<option>Civil</option>
									<option>Computer</option>
									<option>Electronic</option>
									<option>Mechanical</option>
								</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
								<label>Division</label>
								<select class="form-control">
									<option>One</option>
									<option>Two</option>
									<option>Three</option>
									<option>Four</option>
								</select>
						</div>
					</div>					
				</div> 
				<!-- /. ROW  -->
				<div class="row">
					<div class="col-md-1">
						<button type="submit" class="btn btn-primary">Send</button>
					</div>
					<div class="col-md-1">
						<button type="reset" class="btn btn-Danger">Reset</button>
					</div>
				</div>
			</form>
			<hr />
				<div class="row">
					<div class="col-md-4">
					<form method="post" action="uploadStudents" enctype="multipart/form-data"> 
						<div class="form-group">
                            <label>Upload Student Data</label>
                            <input type="file" name="studentsFile" />
                        </div>
		
						<button type="submit" class="btn btn-primary">Upload</button>
						</form>
					</div>
					<div class="col-md-4">
					<form method="post" action="uploadTeachers" enctype="multipart/form-data"> 
						<div class="form-group">
                            <label>Upload Teacher Data</label>
                            <input type="file" name="teachersFile" />
                        </div>
						<button type="submit" class="btn btn-primary">Uplaoad</button>
						</form>
					</div>
					<div class="col-md-4">
					<form method="post" action="uploadDepartments" enctype="multipart/form-data"> 
						<div class="form-group">
                            <label>Upload Department Data</label>
                            <input type="file" name="departmentsFile"/>
                        </div>
						<button type="submit" class="btn btn-primary">Upload</button>
						</form>
					</div>
				</div>
			
				<!-- /. ROW  -->
			<hr />
            <!-- /. PAGE INNER  -->
        </div>
         <!-- /. PAGE WRAPPER  -->
    </div>
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<c:url value = "/resources/js/jquery-1.10.2.js"/>"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="<c:url value = "/resources/js/bootstrap.min.js"/>"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<c:url value = "/resources/js/jquery.metisMenu.js"/>"></script>
    <!-- MORRIS CHART SCRIPTS 
    <script src="<c:url value = "/resources/js/morris/raphael-2.1.0.min.js"/>"></script>
    <script src="<c:url value = "/resources/js/morris/morris.js"/>"></script>-->
	
	<!-- CUSTOM SCRIPTS -->
    <script src="<c:url value = "/resources/js/custom.js"/>"></script>
    
   </div>
</body>
</html>
