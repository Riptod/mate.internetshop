<%--
  Created by IntelliJ IDEA.
  User: super
  Date: 15.01.2020
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
Hello login page

<form action="/mate_internetshop_war_exploded/login" method="post" id="form">
    <div class="container">
        <h1>Login</h1>
        <p>Please fill in this form to login an account.</p>
        <hr>
        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter login" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>
        <hr>

        <button type="submit" class="registerbtn">Login</button>
        <br>
        <div>${errorMsg}</div>
    </div>

    <div class="container signin">
        <p>Don`t have an account? <a href="/mate_internetshop_war_exploded/registration">Sign up</a>.</p>
    </div>
</form>

</body>
</html>
