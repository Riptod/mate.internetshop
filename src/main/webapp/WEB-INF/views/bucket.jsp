<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.models.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>

<div class="container">
    <h2>Items in bucket</h2>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Delete</th>
        </tr>
        <c:if test="${not empty items}">
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>
                        <c:out value="${item.name}"/>
                    </td>
                    <td>
                        <c:out value="${item.price}"/>
                    </td>
                    <td>
                        <a class="add-link" href="/mate_internetshop_war_exploded/servlet/deleteItemFromBucket?item_id=${item.id}">DELETE</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>

    <div class="button-wrap">
        <a href="/mate_internetshop_war_exploded/getAllItems">
            <button type="submit" class="registerbtn">Back to items</button>
        </a>
        <a href="/mate_internetshop_war_exploded/servlet/completeOrder">
            <button type="submit" class="registerbtn">Complete Order</button>
        </a>
        <a href="/mate_internetshop_war_exploded/servlet/home">
            <button type="submit" class="registerbtn">Back to home</button>
        </a>
    </div>
</div>
</body>
</html>
