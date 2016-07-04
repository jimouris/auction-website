<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
        <h1>Administrator's Login</h1>
         <%
                String msg = (String)request.getAttribute("regStatus");
                if(msg != null){
                    out.print(msg);
                }

            %>
        <form action="loginAdmin.do" method="POST" id="loginForm">
            <label for="username">Admin's username</label>
            <input type="text" id="username" name="username" required autofocus>
            <label for="password">password</label>
            <input type="password" id="password" name="password" required>
            <!-- this hidden field must be used only on pages that the admin can login -->
            <input type="hidden" name="referrer" value="backoffice">
            <input type="submit" value="Admin Login">
        </form>

    <a href="./index.jsp" class="u-center-text">Back to startpage</a>
</div>
</body>
</html>
