<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Dept Profile</title>

    <!-- Bootstrap Core CSS -->    
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    
 	<style>
     <%@ include file="css/teacher_profile.css"%>
     <%@ include file="css/dept_profile.css"%>
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
                    <a href="">Department Of Studies</a>
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
                
                         
                    <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-5 post1">
                            <div class="col-md-12 notes1">
                               <label>Staff</label>
                            </div>
                             <div class="col-md-12 notes2">
                            <span class="badge">10</span>
                            </div>
                            <div class="col-md-12 notes3">
                            <button type="button" class="btn btn-default button">Manage Staff</button>
                            </div>
                        </div>
                        <div class="col-md-5 post1">
                            <div class="col-md-12 notes1">
                             <label>Files</label>
                            </div>
                             <div class="col-md-12 notes2">
                            <span class="badge">10</span>
                            </div>
                            <div class="col-md-12 notes3">
                            <button type="button" class="btn btn-default button">Manage Files</button>
                            </div>
                        </div>
                    </div>
                    </div>
                
            </div>
        </div>
    </div>
    
        <!-- /.container -->

    <!-- jQuery -->
<!--    <script src="js/jquery.js"></script>-->

    <!-- Bootstrap Core JavaScript -->
<!--    <script src="js/bootstrap.min.js"></script>-->

        <%@include file="js/jquery.js" %>
        <%@include file="js/bootstrap.min.js" %>
        <%@include file="js/admin.js" %>

    
    
    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 2000 //changes the speed
    })
    </script>

</body>

</html>