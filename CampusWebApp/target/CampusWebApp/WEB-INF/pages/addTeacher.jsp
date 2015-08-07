<%@page import="com.resoneuronance.campus.web.util.Constants"%>
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
    <title>Teacher Info</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   <script type="text/javascript">
    function onEdit(studentId) {
        $.ajax({
        	type : "POST",
            url : 'initTeacherEdit',
            dataType: 'json',
            data: "id=" + studentId,
            success : function(data) {
                //$('#result').html(data);
                $('#editName').val(data.name);
                $('#editEmail').val(data.email);
                $('#editId').val(data.id);
            },
            error: function(e){
            	alert('Error: ' + e);
        	}
        });
    }
</script>
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%=Constants.ADMIN_DASHBOARD_URL%>">College Name</a> 
            </div>
		<div style="color: white;padding: 15px 50px 5px 50px;float: right;font-size: 16px;">
			<a href="logout" class="btn btn-danger square-btn-adjust">Logout</a> 
		</div>
        </nav>   
           <!-- /. NAV TOP  -->
                <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
					<li class="text-center">
						<img src="<c:url value="/resources/img/find_user.png"/>" class="user-image img-responsive"/>
					</li>
					<li>
                        <a class="active-menu"  href="<%=Constants.ADMIN_DASHBOARD_URL%>"><i class="fa fa-book fa-3x"></i>Dashboard</a>
                    </li>
                </ul>               
            </div>
            
        </nav>  
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>Teacher</h2>   
                        <h4>Welcome <span>Admin</span></h4>                      
                    </div>
                </div>
			    <!-- /. ROW  -->	
			<!--Add Modal Starts Here-->
				<div>
				<p class="text-center">
					<button class="btn btn-default" data-toggle="modal" data-target="#addModal">Add Teacher</button>
				</p>
					<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title">Teacher Details</h4>
								</div>

								<div class="modal-body">
									<!-- The form is placed inside the body of modal -->
									<form id="addForm" action="addTeacher" method="post" class="form-horizontal">
										<div class="form-group">
											<label class="col-xs-3 control-label">Name</label>
											<div class="col-xs-5">
												<input type="text" class="form-control" name="name" />
											</div>
										</div>

										<div class="form-group">
											<label class="col-xs-3 control-label">Email</label>
											<div class="col-xs-5">
												<input type="email" class="form-control" name="email" />
											</div>
										</div>

										<div class="form-group">
											<div class="col-xs-5 col-xs-offset-3">
												<button type="submit" class="btn btn-primary">Save Data</button>
												<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			<!-- End Modal -->
			<!--Edit Modal Starts Here-->
			<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Edit Teacher Details</h4>
						</div>

						<div class="modal-body">
							<!-- The form is placed inside the body of modal -->
							<form id="editForm" action="editTeacher" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-xs-3 control-label">Edit Name</label>
									<div class="col-xs-5">
										<input type="text" id="editName" class="form-control" name="name" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-xs-3 control-label">Edit Email</label>
									<div class="col-xs-5">
										<input type="email" id="editEmail" class="form-control" name="email" />
									</div>
								</div>
								<input type="hidden" id="editId" class="form-control" name="id" />
								<div class="form-group">
									<div class="col-xs-5 col-xs-offset-3">
										<button type="submit" class="btn btn-primary">Save Data</button>
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>	
			<!-- End Modal -->
            <hr />
            <c:forEach items="${teachers}" var="teacher"> 
				<div class="row">
					<div class="col-md-2">
						<div class="form-group">
							<label>PIC</label>
						</div>												
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label>${teacher.name}</label>                           
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label>${teacher.email}</label>
						</div>
					</div>
					<div class="col-md-1">
						<div class="form-group">
							<button class="btn btn-default" data-toggle="modal" data-target="#editModal" onclick="onEdit(${teacher.id})">Edit Info</button>
						</div>
					</div>
					<div class="col-md-1">
						<div class="form-group">
						<form action="${teacher.id}.teacher" method="post">
							<button type="submit" class="btn btn-danger">Delete</button>
						</form>
						</div>
					</div>
				</div>	
                </c:forEach>             
			</div>
             <!-- /. PAGE INNER  -->
            </div>
         <!-- /. PAGE WRAPPER  -->
        </div>
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<c:url value="/resources/js/jquery.metisMenu.js"/>"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="<c:url value="/resources/js/custom.js"/>"></script>
   
</body>
</html>
    