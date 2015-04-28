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
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    
    <link href="css/teacher_profile.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/bree-serif-font.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    
    
    

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
                        <label class="col-md-2">Your Status:</label>
                        <div class="col-md-10">
                            <div class="list-group">
                              <div class="list-group-item">
                                Physics Teacher
                              </div>
                              <div class="list-group-item">10 year Experience</div>
                            </div>
                        </div>
                            <div class="New Status">
                                 <span></span>
                                <textarea class="form-control" rows="5" id="comment" disabled>New Status</textarea>
                            </div>
                        </div>
                    </div>
                    </div>
                
                    
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
                               <label> Notes</label>
                            </div>
                            <div class="col-md-12 notes2">
                            <span class="badge">10</span>
                            </div>
                            <div class="col-md-12 notes3">
                            <button type="button" class="btn btn-default button">Manage Notes</button>
                            </div>
                        </div>
                        <div class="col-md-5 post1">
                            <div class="col-md-12 notes1">
                             <label>Assignments</label>
                            </div>
                            <div class="col-md-12 notes2">
                            <span class="badge">14</span>
                            </div>
                            <div class="col-md-12 notes3">
                            <button type="button" class="btn btn-default button">Manage Assignments</button>
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