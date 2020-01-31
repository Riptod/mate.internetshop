<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.models.Order>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<div class="container">
    <h2>Orders</h2>
    <table border="1">
        <tr>
            <th>OrderID</th>
            <th>Items</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>
                    <c:out value="${order.id}"/>
                </td>
                <td>
                    <c:out value="${order.items}"/>
                </td>
                <td>
                    <a class="add-link" href="/mate_internetshop_war_exploded/servlet/deleteOrder?order_id=${order.id}">DELETE</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="button-wrap">
        <a href="/mate_internetshop_war_exploded/servlet/bucket">
            <button type="submit" class="registerbtn">Bucket</button>
        </a>
        <a href="/mate_internetshop_war_exploded/servlet/home">
            <button type="submit" class="registerbtn">Back to home</button>
        </a>
    </div>
</div>
</body>
</html>
