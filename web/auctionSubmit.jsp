<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- <!DOCTYPE html> -->
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Auction Submitted</title>
    <link href="./css/skeleton.css" rel="stylesheet">
</head>
<body>

<h1>Your auction submitted successfully.</h1>
<p>Thanks for your auction!</p>

<h4>Please, navigate to one of the following pages.</h4>
<p>
    <a class="button button-primary" href=auction.do?action=getAnAuction&aid=${aid}>View/Edit Submitted Auction</a>
    <a class="button" href="/homepage.jsp">homepage</a>
</p>

</body>
</html>
