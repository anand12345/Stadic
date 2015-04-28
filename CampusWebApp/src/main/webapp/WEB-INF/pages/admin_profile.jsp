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

    <title>Admin Profile</title>

    <!-- Bootstrap Core CSS -->
    <!-- <link href="css/bootstrap.min.css" rel="stylesheet"> -->
    
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    
    <%-- <link href="<c:url value = "WEB-INF/pages/css/admin_profile.css"/>" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/bree-serif-font.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet"> --%>
    
    <script src="js/admin.js"></script>
    <style>
     <%@ include file = "css/bootstrap.min.css" %>
     <%@ include file = "css/admin_profile.css" %>
     <%@ include file="css/font-awesome.css"%>
     <%@ include file="css/bree-serif-font.css"%>
     <%@ include file="css/font-awesome.css"%>
     <%@ include file="css/font-awesome.min.css"%>
</style>
</head>
    
    
<body>
    
    <div  class="container" id="profile-content">
        <div class="row">
              <div class="container-fluid heading1">
                <a href="">
                <span class="glyphicon glyphicon-th-list pull-left"></span>
                </a>
                <div class="pull-right">
                    <a href="">Prof. XYZ</a>
                    <a href="">
                    <span class="glyphicon glyphicon-off"></span>
                    </a>
                    <a href="">
                    <span class="glyphicon glyphicon-user"></span>
                    </a>
                </div>
            </div>
        <div class="container">
                
            <div class="container-fluid posts">
                <div class="row">
                    <div class="col-md-12">
                          <label for="comment">Notify Students:</label>
                          <textarea class="form-control" rows="5" id="notify-students"></textarea>
                    </div>
                        <div class="col-md-12">
                            <div class="col-md-3">
                              <select class="form-control" id="sel1">
                                <option>1st Year</option>
                                <option>2nd Year</option>
                                <option>3rd Year</option>
                                <option>Final Year</option>
                              </select>
                            </div>
                            <div class="col-md-3">
                              <select class="form-control" id="sel2">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                              </select>
                            </div>
                            <div class="col-md-3">
                                  <select class="form-control" id="sel3">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                  </select>
                                </div>
                            <div class="col-md-3">
                                 <button type="button" class="btn btn-default check1">
                                 <i class="fa fa-check-square-o"></i>
                                 </button>
                            </div>
                        </div>
                </div>
            </div>
            <div class="container-fluid posts">
                <div class="row">
                    <div class="col-md-12">
                        <label class="col-md-12 notes1"> Admin </label>
<!--                           <div class="New Status">-->
                                <span></span>
<!--                                <textarea class="form-control" rows="5" id="comment" disabled>New Status</textarea>-->
                            
                            <div class="col-md-12">                              
                                <div class="col-md-12 filediv">
                                        <label for="name" class="col-md-4">Student:</label>
                                        <input type="file" class="file col-md-4 col-xs-12 "/>
<!--                                    <input type="file" accept="application/vnd.ms-excel"
 class="file col-md-4 col-xs-12" />-->

                                        <a href="" class="col-md-4 "><input type="button" name="student" class="btn btn-default  button" value="Submit"/></a>
                                </div>
                                <div class="col-md-12 filediv">                    
                                        <label for="name" class="col-md-4">Teacher:</label>
                                        <input type="file" class="file col-md-4 col-xs-12 "/>
                                        <a href="" class="col-md-4"><input type="button" name="teacher" class="btn btn-default  button" value="Submit"/></a>
                                </div>
                                <div class="col-md-12 filediv">
                                     <label for="name" class="col-md-4">Department:</label>
                                     <input type="file" name="pic" class="file col-md-4 col-xs-12 "/>
                                     <a href="" class="col-md-4"><input type="button" name="department" class="btn btn-default button " value="Submit"/></a>
                                </div>
<!--                              </div>-->
                           </div>
                        </div>
                    </div>
                </div>
                
                    
            <div class="container-fluid posts">
                <div class="row">
                    <div class="col-md-12">
                          <label class="col-md-12 notes1">Admin View:</label>
                    </div>
                        <div class="col-md-12">
                            <span></span>
                            <div class="col-md-4 notes3 select">
                                <a href="student.jsp"><input type="button" name="student" class="btn btn-default button" value="student"/></a>
                            </div>
                            <span></span>
                             <div class="col-md-4 notes3 select">
                                 <a href="teacher.jsp"><input type="button" name="teacher" class="btn btn-default button" value="teacher"/></a>
                            </div>
                            <span></span>
                             <div class="col-md-3 notes3 select">
                                 <a href="department.jsp"><input type="button" name="department" class="btn btn-default button" value="department"/></a>
                             </div>

                        </div>
                </div>
            </div>
          </div>
        </div>
    </div>
    
        <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 2000 //changes the speed
    })
    </script>

</body>

</html>