<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.models.Order>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
Orders :
<table border="1">
    <!-- here should go some titles... -->
    <tr>
        <th>OrderID</th>
        <th>Items</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}" />
            </td>
            <td>
                <c:out value="${order.items}" />
            </td>
            <td>
                <a href="/mate_internetshop_war_exploded/deleteOrder?order_id=${order.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/mate_internetshop_war_exploded/bucket">Bucket</a>
<a href="/mate_internetshop_war_exploded/home">Back to home</a>
</body>
</html>
