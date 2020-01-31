<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.models.User>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>

<div class="container">
    <h2>Users</h2>
    <table class="table-item">
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>
                    <c:out value="${user.id}"/>
                </td>
                <td>
                    <c:out value="${user.login}"/>
                </td>
                <td>
                    <c:out value="${user.name}"/>
                </td>
                <td>
                    <c:out value="${user.surname}"/>
                </td>
                <td>
                    <a class="add-link" href="/mate_internetshop_war_exploded/servlet/deleteUser?user_id=${item.id}">DELETE</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="button-wrap">
        <a href="/mate_internetshop_war_exploded/servlet/createItem">CreateItem
            <button type="submit" class="registerbtn">Create new item</button>
        </a>
        <a href="/mate_internetshop_war_exploded/servlet/getAllItems">AllItems
            <button type="submit" class="registerbtn">Get all items</button>
        </a>
        <a href="/mate_internetshop_war_exploded/servlet/home">Back to home
            <button type="submit" class="registerbtn">Back to home</button>
        </a>
    </div>
</div>
</body>
</html>
