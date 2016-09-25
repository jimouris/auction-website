<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <!DOCTYPE html> -->
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Administrator page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
</head>
<body>
<c:if test="${not isAdmin}">
    <c:redirect url="/"/>
</c:if>
<c:if test="${isAdmin}">
    <div class="container">
        <div class="row">
            <div class="four columns offset-by-four">
                <h2>This is the main page of admin.</h2>
                <a href="/user.do?action=getAllUsers" class="button button-primary">View all users</a>
                <a href="/auction.do?action=getAuctionsAsXML"  target="_blank" class="button button-primary">10 auctions to XML</a>
                <a href="/logout.do" class="button">Logout</a>
            </div>
        </div>
    </div>
</c:if>
</body>
</html>
