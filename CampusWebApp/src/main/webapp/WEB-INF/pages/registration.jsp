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
    <title>Register..Its Free</title>
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
        <div class="row text-center  ">
            <div class="col-md-12">
                <br /><br />
                <h2>Register</h2>
                <br />
            </div>
        </div>
         <div class="row">               
                <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                        <div class="panel panel-default">
                            <div class="panel-heading">
							<strong> New User ? Register Yourself </strong>  
                            </div>
                            <div class="panel-body">
                            	${resultMsg}
                                <form role="form" action="${action}" method="post">
									<br/>
										<div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-circle-o-notch"  ></i></span>
                                            <input type="text" id="colleges" name="collegeName"  class="form-control" placeholder="Your College" />
                                        </div>
                                        <div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-circle-o-notch"  ></i></span>
                                            <input type="text" class="form-control" name="name" placeholder="Your Name" />
                                        </div>
                         
                                        <div class="form-group input-group">
                                            <span class="input-group-addon">@</span>
                                            <input type="text" class="form-control" name="email" placeholder="Your Email" />
                                        </div>
										<div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                                            <input type="password" class="form-control" name="password"  placeholder="Enter Password" />
                                        </div>
										<div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                                            <input type="password" class="form-control" name="passwordRetype" placeholder="Retype Password" />
                                        </div>
                                        <c:if test="${register == 'registerStudent' }">
                                        <div class="form-group input-group">
                                        	<span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                                            <input type="text" class="form-control" name="departmentName" placeholder="Your Department" />
                                        </div>
                                        </c:if>
                                     
                                    <!-- <a href="index.html" class="btn btn-success ">Register Me</a> -->
                                    <button type="submit" name="submitteach" class="btn btn-primary">Register</button>
                                    <hr />
                                    Already Registered ?  <a href="login.html" >Login here</a>
                                </form>
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
	  $('#colleges').autocomplete({
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
    