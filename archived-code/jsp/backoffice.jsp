<%-- 
    Document   : backoffice
    Created on : May 10, 2015, 2:09:49 PM
    Author     : hostAdmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrator login page</title>
        <%@ include file="./linked_files.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="row four columns u-center">
                <h1>Administrator's Login</h1>
                <form action="executeLogin" method="POST" id="loginForm">
                  <label for="loginEmail">Admin's username</label>
                  <input class="u-full-width" type="text" id="loginUsername" name="loginUsername" required autofocus>
                  <label for="loginPassword">password</label>
                  <input class="u-full-width" type="password" id="loginPassword" name="loginPassword" required>
                  <input class="button-primary u-pull-right" type="submit" value="Admin Login">
                </form>
                
            </div>
            <a href="./index.jsp" class="u-center-text">Back to startpage</a>
        </div>
    <script type="text/javascript">
        $("#loginForm").validate();
    </script>
    </body>
</html>
