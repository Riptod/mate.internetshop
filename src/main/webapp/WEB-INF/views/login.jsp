<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<form action="/mate_internetshop_war_exploded/login" method="post" id="form">
    <div class="container">
        <h2>Login</h2>
        <p class="text">Please fill in this form to login an account.</p>
        <div class="form-wrap">
            <label for="login"><b>Login</b></label>
            <input type="text" placeholder="Enter login" id="login" required>

            <label for="psw"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" id="psw" required>
            <button type="submit" class="registerbtn">Login</button>
        </div>
        <div>${errorMsg}</div>
        <p class="text">Don`t have an account? <a href="/mate_internetshop_war_exploded/registration">Sign up</a>.</p>
    </div>
</form>
</body>
</html>
