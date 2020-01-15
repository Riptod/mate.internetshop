<%--
  Created by IntelliJ IDEA.
  User: super
  Date: 12.01.2020
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Registration</title>
    </head>
    <body>
        Create a new user!
        <form action="/mate_internetshop_war_exploded/registration" method="post">
            <div class="container">
                <h1>Register</h1>
                <p>Please fill in this form to create an account.</p>
                <hr>
                <label for="login"><b>Login</b></label>
                <input type="text" placeholder="Enter login" name="login" required>

                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw" required>

                <label for="psw-repeat"><b>Repeat Password</b></label>
                <input type="password" placeholder="Repeat Password" name="psw-repeat" required>

                <label for="user_name"><b>Name</b></label>
                <input type="text" placeholder="Name" name="user_name" required>

                <label for="user_surname"><b>Surname</b></label>
                <input type="text" placeholder="Surname" name="user_surname" required>
                <hr>

                <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
                <button type="submit" class="registerbtn">Register</button>
            </div>

            <div class="container signin">
                <p>Already have an account? <a href="/mate_internetshop_war_exploded/login">Sign in</a>.</p>
            </div>
        </form>
    </body>
</html>

