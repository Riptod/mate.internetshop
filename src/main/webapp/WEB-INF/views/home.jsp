<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home page</title>
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
    <p class="menu_text">Select option:</p>
    <section class="menu">
        <a href="/mate_internetshop_war_exploded/getAllItems">
            <button class="home_button">AllItems</button>
        </a>
        <a href="/mate_internetshop_war_exploded/servlet/getAllOrders">
            <button class="home_button">AllOrders</button>
        </a>
        <br>
        <a href="/mate_internetshop_war_exploded/servlet/bucket">
            <button class="home_button">My bucket</button>
        </a>
        <a href="/mate_internetshop_war_exploded/logout">
            <button class="home_button">Logout</button>
        </a>
    </section>


</body>
</html>
