<%@page import="com.resoneuronance.campus.web.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Student</title>
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
<!--    <script type="text/javascript">
   $(document).ready(function() {
	   setInterval(function() {
		alert("Here");
		$('#load_data').load('showNotifications.htm').fadeIn("slow");
		}, 3000); // refresh every 3000 milliseconds
   })
</script> -->
<!--    <script type="text/javascript">
var auto_refresh = setInterval(
function ()
{
$('body').load('showNotifications.htm');
}, 3000); // refresh every 3000 milliseconds
</script> -->
<script type="text/javascript">
//Customise those settings

var seconds = 5;
var divid = "wrapper";
var url = "<%=Constants.STUDENT_TEACHER_NOTIFICATIONS_URL%>";

////////////////////////////////
//
// Refreshing the DIV
//
////////////////////////////////

function refreshdiv(){

// The XMLHttpRequest object

var xmlHttp;
try{
xmlHttp=new XMLHttpRequest(); // Firefox, Opera 8.0+, Safari
}
catch (e){
try{
xmlHttp=new ActiveXObject("Msxml2.XMLHTTP"); // Internet Explorer
}
catch (e){
try{
xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
}
catch (e){
alert("Your browser does not support AJAX.");
return false;
}
}
}

// Timestamp for preventing IE caching the GET request

fetch_unix_timestamp = function()
{
return parseInt(new Date().getTime().toString().substring(0, 10))
}

var timestamp = fetch_unix_timestamp();
var nocacheurl = url+"?t="+timestamp;

// The code...

xmlHttp.onreadystatechange=function(){
if(xmlHttp.readyState==4){
document.getElementById(divid).innerHTML=xmlHttp.responseText;
setTimeout('refreshdiv()',seconds*1000);
}
}
xmlHttp.open("GET",nocacheurl,true);
xmlHttp.send(null);
}

// Start the refreshing process

var seconds;
window.onload = function startrefresh(){
setTimeout('refreshdiv()',seconds*1000);
}

</script>
   
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">${student.college.name}</a> 
            </div>
		<div style="color: white; padding: 15px 50px 5px 50px;float: right;font-size: 16px;">
			<a href="login.html" class="btn btn-danger square-btn-adjust">Logout</a> 
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
					
			<!--Row-->	
			<c:forEach items="${studentTeacher.notifications}" var="notification" >
			<hr /> 
				<div class="row" >
					<div class="col-md-1 text-center">
						<div class="form-group">
							<label><fmt:formatDate value="${notification.date}" pattern="yyyy-MM-dd" /></label>
						</div>												
					</div>
					<div class="col-md-10 text-center">
						<div class="form-group">
							<label>${notification.notification}</label>
						</div>
					</div>
					<div class="col-md-1 text-center">
						<div class="form-group">
							<!-- <label><i class="fa fa-download fa-3x"></i></label> -->
							<c:if test="${notification.filePath!=null}">
								<form action="downloadDocument/${notification.id}" method="post">
									<button type="submit" style="border:none;background:transparent"><i class="fa fa-file fa-2x" style="font-size: 2.5em;"></i></button>
								</form>
							</c:if>
						</div>
					</div>
				</div>	
			<hr />
			</c:forEach>
			</div>
            <!-- /. PAGE INNER  -->
        </div>
         <!-- /. PAGE WRAPPER  -->
         <div id="load_data"></div>
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
    