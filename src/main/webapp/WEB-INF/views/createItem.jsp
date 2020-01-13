<%--
  Created by IntelliJ IDEA.
  User: super
  Date: 12.01.2020
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create item</title>
</head>
<body>
Create new item!

<form action="/mate_internetshop_war_exploded/createItem" method="post">
    <div class="container">
        <h1>CreateItem</h1>
        <hr>
        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter name" name="name" required>

        <label for="price"><b>Price</b></label>
        <input type="number" placeholder="Enter price" name="price" required>

        <button type="submit" class="registerbtn">Create</button>
    </div>
</form>
<a href="/mate_internetshop_war_exploded/getAllItems">AllItems</a>
<a href="/mate_internetshop_war_exploded/home">Back to home</a>
</body>
</html>
