<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Colleges</title>
</head>
<body>
Colleges : ${college.name}
<form:form method="POST" action="main" commandName="college">
	<table>
    <tr>
        <td><form:label path="name">College Name :</form:label></td>
        <td><form:input path="name" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Add"/>
        </td>
    </tr>

</form:form>
</body>
</html>