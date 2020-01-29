<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.models.User>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
Users:
<table border="1">
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items ="${users}">
        <tr>
            <td>
                <c:out value="${user.id}" />
            </td>
            <td>
                <c:out value="${user.login}" />
            </td>
            <td>
                <c:out value="${user.name}" />
            </td>
            <td>
                <c:out value="${user.surname}" />
            </td>
            <td>
                <a href="/mate_internetshop_war_exploded/servlet/deleteUser?user_id=${item.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/mate_internetshop_war_exploded/servlet/createItem">CreateItem</a>
<a href="/mate_internetshop_war_exploded/servlet/getAllItems">AllItems</a>
<a href="/mate_internetshop_war_exploded/servlet/home">Back to home</a>
</body>
</html>
