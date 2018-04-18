<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>
</head>
<body>
<table border="1">
    <thead> <p>${updatedsuccess}</p>
    <tr>
        <th>Surname</th>
        <th>Name</th>
        <th>E-mail</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.surname}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <%--<td><a href="/deleteUser?username=${user.username}">Delete</a></td>--%>
            <%--<td><a href="/error?username=${user.username}">Update</a></td>--%>
        </tr>

    </c:forEach>

    </tbody>
</table>
</body>
</html>