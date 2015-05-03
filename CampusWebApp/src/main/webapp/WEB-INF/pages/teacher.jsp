<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Teacher Profile</title>

    <!-- Bootstrap Core CSS -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link href="<c:url value = "/resources/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value = "/resources/css/teacher_profile.css"/>" rel="stylesheet">
    <link href="<c:url value = "/resources/css/font-awesome.css"/>" rel="stylesheet">
    <link href="<c:url value = "/resources/css/bree-serif-font.css"/>" rel="stylesheet">
    <link href="<c:url value = "/resources/css/font-awesome.css"/>" rel="stylesheet">
    <link href="<c:url value = "/resources/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value = "/resources/css/bootstrap-theme.min.css"/>" rel="stylesheet">
   
    <%-- <style>
     <%@ include file="css/teacher_profile.css"%>
     <%@ include file="css/bootstrap.css"%>
     <%@ include file ="css/bootstrap.min.css" %>
     <%@ include file="css/bootstrap-theme.min.css"%>
     <%@ include file="css/font-awesome.css"%>
     <%@ include file="css/font-awesome.min.css"%>
     <%@ include file="css/bree-serif-font.css"%>
    </style>    --%>
    
    <!-- jQuery -->
<!--    <script src="js/jquery.js"></script>-->

    <!-- Bootstrap Core JavaScript -->
<!--    <script src="js/bootstrap.min.js"></script>-->

           <%--  <%@include file="js/jquery.js" %>
            <%@include file="js/bootstrap.min.js" %>
            <%@include file="js/admin.js" %> --%>
    
    
</head>
<body>
    <div  class="container" id="profile-content">
        <div class="row">
              <div class="container-fluid heading1">
                <a href="">
                <span class="glyphicon glyphicon-th-list pull-left"></span>
                </a>
                <div class="pull-right">
                    <a href="">Admin-Teacher</a>
                    <a href="">
                    <span class="glyphicon glyphicon-off"></span>
                    </a>
                    <a href="">
                    <span class="glyphicon glyphicon-user"></span>
                    </a>
                </div>
            </div>
            
            <div class="container-fluid posts">
               <div class="row">
                            <div class="col-sm-8 col-md-8 pull-right">
                                <form class="navbar-form" role="search">
                                    <div class="input-group row">
                                        <input type="text" class="form-control" placeholder="Search" name="q">
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                                        </div>
                                    </div>
                                </form>
                            </div>>
                     <div class="col-md-12">
                             <a class="btn btn-primary btn-lg" href="teacheradd" role="button">Add more</a>
							${resultMsg}
						<div class="jumbotron">
							<c:forEach var="teacher" items="${teachers}">
							<form:form action="${teacher.id}.teacher" method="POST">
								${teacher.name}
							<input type="submit" value="Delete" size="50" /><br>
							</form:form>
						</c:forEach>
                        </div>
                     </div>
                </div>
            </div>
                    <!-- /.container -->

          </div>
     </div> 
    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 2000 //changes the speed
    })
    </script>

</body>

</html>