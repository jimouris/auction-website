<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>
<div class="container">

    <h1 class="u-text-center">Administrator's Login</h1>
    <div class="four columns offset-by-four">
        <section class="login u-full-width row">
            <form action="/login.do" method="POST" id="loginForm">
                <label for="username">Admin's username</label>
                <input class="u-full-width" type="text" id="username" name="username" required autofocus>
                <label for="password">password</label>
                <input class="u-full-width" type="password" id="password" name="password" required>
                <!-- this hidden field must be used only on pages that the admin can login -->
                <input type="hidden" name="action" value="admin">
                <c:if test="${not empty errorMsg}">
                    <p class="status--error">${errorMsg}</p>
                </c:if>
                <input class="button-primary u-full-width u-pull-right" type="submit" value="Admin Login">
            </form>
        </section>
        <a href="/public/" class="u-text-center u-full-width">Back to startpage</a>
    </div>
</div>
</body>
</html>
