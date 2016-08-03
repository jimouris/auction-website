<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <!DOCTYPE html> -->
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Administrator page</title>
    <link href="./css/skeleton.css" rel="stylesheet">
</head>
<body>

<c:if test="${isAdmin}">
    <div class="row">
        <div class="one-half column">
            <h1>This is the main page of admin.</h1>
        </div>

        <div class="one-half column">
            <h5><a href="logout.do" class="button-primary">Logout</a></h5>
        </div>
    </div>

    <p>Γεια σου ρε χοντρέ!</p>
    <a href="user.do?action=getAllUsers">View all users my nigga</a>
</c:if>
<c:if test="${not isAdmin}">
    <h1>You are not admin. Go back to <a href="/" class="button-primary">homepage</a>.</h1>
</c:if>

</body>
</html>
