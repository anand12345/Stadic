<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<body>
<h2>My Campus</h2>
<form:form method="POST" action="main" commandName="user">
   <table>
    <tr>
        <td><form:label path="username">Username :</form:label></td>
        <td><form:input path="username" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table>  
</form:form>
</body>
</html>
