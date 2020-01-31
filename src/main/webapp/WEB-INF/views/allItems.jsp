<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.models.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All items</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<div class="container main-table">
    <h2>Items</h2>
    <table class="table-item">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Add</th>
        </tr>
        <c:forEach var="item" items="${items}">
            <tr>
                <td>
                    <c:out value="${item.name}"/>
                </td>
                <td>
                    <c:out value="${item.price}"/>
                </td>
                <td>
                    <a class="add-link" href="/mate_internetshop_war_exploded/servlet/addItemToBucket?item_id=${item.id}">ADD</a>
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
