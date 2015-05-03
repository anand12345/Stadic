<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Student Profile</title>

    <!-- Bootstrap Core CSS -->    
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <style>
     <%@ include file="css/student_profile.css"%>
     <%@ include file="css/bootstrap.css"%>
     <%@ include file ="css/bootstrap.min.css" %>
     <%@ include file="css/bootstrap-theme.min.css"%>
     <%@ include file="css/font-awesome.css"%>
     <%@ include file="css/font-awesome.min.css"%>
     <%@ include file="css/bree-serif-font.css"%>
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
                    <a href="">Student ABC</a>
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
                        </div>
                        <div class="col-md-12">
                        <div class="jumbotron">
                          <h1>ABC College Of Engineering</h1>
                          <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
                        </div>
                        </div>
                        
                        <div class="col-md-12">
                        <div class="panel panel-default">
                          <div class="panel-heading">Departments</div>
                          <div class="panel-body">
                            Panel content
                          </div>
                        </div>
                        </div>
                        
                        <div class="col-md-12">
                        <div class="panel panel-default">
                              <div class="panel-heading">Teachers</div>
                              <div class="panel-body">
                                Panel content
                              </div>
                         </div>
                        </div>
                </div>
            </div>  
          <!-- /.container -->
    <!-- jQuery -->
<!--    <script src="js/jquery.js"></script>-->

    <!-- Bootstrap Core JavaScript  -->
<!--    <script src="js/bootstrap.min.js"></script>-->
    <!-- Script to Activate the Carousel -->
    
            <%@include file="js/jquery.js" %>
            <%@include file="js/bootstrap.min.js" %>
            <%@include file="js/admin.js" %>

            
            
    <script>
    $('.carousel').carousel({
        interval: 2000 //changes the speed
    })
    </script>

</body>

</html>