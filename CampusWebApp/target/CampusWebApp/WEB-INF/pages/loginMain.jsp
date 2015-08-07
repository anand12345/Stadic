<%@page import="com.resoneuronance.campus.web.controller.MainController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   <style type="text/css">
    	.autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
		.autocomplete-suggestion { padding: 5px 5px; white-space: nowrap; overflow: hidden; font-size:22px}
		.autocomplete-selected { background: #F0F0F0; }
    </style>
</head>
<body>
    <div class="container">
        <div class="row text-center ">
            <div class="col-md-12">
                <br /> 
                <h2>Sign In</h2>              
                <br />
            </div>
        </div>
        <div class="row ">              
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                <div class="panel panel-default">
                    <div class="panel-heading">
						<strong>Login Home</strong>  
                    </div>
					<div class="panel panel-default">
						<div class="panel-body">
	  						${resultMsg}
							<div role="tabpanel">

							  <!-- Nav tabs -->
							  <ul class="nav nav-tabs nav-justified" role="tablist">
								<li role="presentation" class="active"><a href="#loginadmin" aria-controls="#loginadmin" role="tab" data-toggle="tab">Admin</a></li>
								<li role="presentation"><a href="#loginstudent" aria-controls="loginstudent" role="tab" data-toggle="tab">Student</a></li>
								<li role="presentation"><a href="#loginteacher" aria-controls="loginteacher" role="tab" data-toggle="tab">Teacher</a></li>
							  </ul>

								<!-- Tab panes -->
							    <div class="tab-content">
								<!-- Admin Login -->
									<div role="tabpanel" class="tab-pane active" id="loginadmin">
										<form role="form" action="login" method="post">
											<br />
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
												<input type="text" name="name" id="college" class="form-control" placeholder="Your College " value="" />
											</div>
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
												<input type="text" class="form-control" name="username" placeholder="Admin Username " />
											</div>
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-lock" ></i></span>
												<input type="password" class="form-control" name="password" placeholder="Admin Password" />
											</div>
											<div class="form-group">
												<label class="checkbox-inline">
													<input type="checkbox" /> Remember me
												</label>
												<span class="pull-right">
													   <a href="#" >Forget password ? </a> 
												</span>
											</div>
											<button type="submit" name="submitadmin" class="btn btn-primary">Submit</button>
										</form>
									</div>
									<!-- Student Login -->
									<div role="tabpanel" class="tab-pane" id="loginstudent">
										<form role="form" action="studentLogin" method="POST">
											<br />
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
												<input type="text" name="collegeName" id="collegeStudent" class="form-control" placeholder="Your College " value="" />
											</div>
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
												<input type="text" class="form-control" name="email" placeholder="Student Username " />
											</div>
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
												<input type="password" class="form-control" name="password" placeholder="Student Password" />
											</div>
											<div class="form-group">
												<label class="checkbox-inline">
													<input type="checkbox" /> Remember me
												</label>
												<span class="pull-right">
													   <a href="#" >Forget password ? </a> 
												</span>
											</div>
											<button type="submit" name="submitstud" class="btn btn-primary">Submit</button>
										</form>
										<hr />
                                    		Not registered ? <a href="<%=MainController.REGISTRATION_URL %>?type=student" >click here </a>
										
									</div>
									
									<!-- Teacher Login -->
									<div role="tabpanel" class="tab-pane" id="loginteacher">
										<form role="form" action="teacherLogin" method="post">
											<br />
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
												<input type="text" name="collegeName" id="collegeTeacher" class="form-control" placeholder="Your College " value="" />
											</div>
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-tag"  ></i></span>
												<input type="text" class="form-control" name="email" placeholder="Teacher's Email " />
											</div>
											<div class="form-group input-group">
												<span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
												<input type="password" class="form-control" name="password" placeholder="Teacher's Password" />
											</div>
											<div class="form-group">
												<label class="checkbox-inline">
													<input type="checkbox" /> Remember me
												</label>
												<span class="pull-right">
													   <a href="#" >Forget password ? </a> 
												</span>
											</div>
											<button type="submit" name="submitteach" class="btn btn-primary">Submit</button>
											<hr />
                                    		Not register ? <a href="<%=MainController.REGISTRATION_URL %>?type=teacher" >click here </a>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>                         
        </div>
    </div>                              
    


     <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<c:url value="/resources/js/jquery.metisMenu.js"/>"></script>
      <!-- CUSTOM SCRIPTS -->
    <%-- <script src="<c:url value="/resources/js/custom.js"/>"></script> --%>
    <script src="<c:url value="/resources/js/autocomplete.js"/>"></script>
   <script>
  $(document).ready(function() {
	  $('#college').autocomplete({
			serviceUrl: '${pageContext.request.contextPath}/getColleges',
			paramName: "tagName",
			delimiter: ",",
		   transformResult: function(response) {
				return {      	
			  	suggestions: $.map($.parseJSON(response), function(item) {
			  	return { value: item.tagName, data: item.id };
			   		})
	 			};
	 
	      	}
	 
		});
	  $('#collegeTeacher').autocomplete({
			serviceUrl: '${pageContext.request.contextPath}/getColleges',
			paramName: "tagName",
			delimiter: ",",
		   transformResult: function(response) {
				return {      	
			  	suggestions: $.map($.parseJSON(response), function(item) {
			  	return { value: item.tagName, data: item.id };
			   		})
	 			};
	 
	      	}
	 
		});
	  $('#collegeStudent').autocomplete({
			serviceUrl: '${pageContext.request.contextPath}/getColleges',
			paramName: "tagName",
			delimiter: ",",
		   transformResult: function(response) {
				return {      	
			  	suggestions: $.map($.parseJSON(response), function(item) {
			  	return { value: item.tagName, data: item.id };
			   		})
	 			};
	 
	      	}
	 
		});
  });
  
  </script>
   
</body>
</html>
    