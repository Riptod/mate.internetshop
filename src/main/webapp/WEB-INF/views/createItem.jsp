<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create item</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
Create new item!
<form action="/mate_internetshop_war_exploded/servlet/createItem" method="post">
    <div class="container">
        <h2>CreateItem</h2>
        <hr>
        <div class="form-wrap">
            <label for="name"><b>Name</b></label>
            <input type="text" placeholder="Enter name" name="name" required>

            <label for="price"><b>Price</b></label>
            <input type="number" placeholder="Enter price" name="price" required>

            <button type="submit" class="registerbtn">Create</button>
        </div>
    </div>
</form>
<a href="/mate_internetshop_war_exploded/servlet/getAllItems">Get all items</a>
<a href="/mate_internetshop_war_exploded/servlet/home">Back to home</a>
</body>
</html>
