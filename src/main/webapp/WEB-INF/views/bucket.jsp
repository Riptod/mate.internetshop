<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.models.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>
Items:
<table border="1">
    <!-- here should go some titles... -->
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="/mate_internetshop_war_exploded/deleteItemFromBucket?item_id=${item.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/mate_internetshop_war_exploded/getAllItems">AllItems</a>
<a href="/mate_internetshop_war_exploded/compliteOrder">CompliteOrder</a>
<a href="/mate_internetshop_war_exploded/home">Back to home</a>
</body>
</html>