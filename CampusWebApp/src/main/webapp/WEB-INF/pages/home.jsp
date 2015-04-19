<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Colleges</title>
</head>
<body>
	<h1>College Name : ${college.name}</h1>
	${resultMsg}
	<h2>Departments :</h2><br>
	<c:forEach var="department" items="${departments}">
		<form:form action="${department.id}" method="POST">
			${department.name}
			<input type="submit" value="Delete" size="50" /><br>
		</form:form>
	</c:forEach>
	<form method="post" action="uploadFile" enctype="multipart/form-data">
		<table border="0">
			<tr>
				<td>Upload Excel:</td>
				<td><input type="file" name="fileUpload" size="50" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="Upload" /></td>
			</tr>
		</table>
	</form>
</body>
</html>