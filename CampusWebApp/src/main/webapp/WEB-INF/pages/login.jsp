<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<!-- To ensure proper rendering and touch zooming, add the following <meta> tag inside the <head> element:   -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Link to the CSS files       -->

<!-- <link rel="stylesheet" href="css/systemLogin.css" type="text/css"/> -->
<style>
     <%@ include file="css/systemLogin.css"%>
</style>
</head>
<body class="container">
	<!-- The name of the System        -->
	<h1>SYSTEM</h1>
	
	<!-- The LOGIN block       -->
	<div class="card">
		<h3>Admin Login</h3>

		<form:form action="login" method="POST" commandName="college">
			<form:select path="name" items="${colleges}" class = "userInput" placeholder="SELECT A COLLEGE" ></form:select>
			<form:input path="username" type="text" placeholder="USER NAME" class="userInput"></form:input>
			<form:input path="password" type="password" placeholder="PASSWORD" class="userInput"></form:input>
			${loginError} 
			<input name="login" type="submit" value="LOGIN" class="button">
		</form:form>
		<!-- This will link to other page for registration           -->
		<p>
			<a href="#">Create an account</a>
		</p>

	</div>

	<!-- Latest compiled JavaScript -->
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>