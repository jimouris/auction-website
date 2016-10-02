<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <!DOCTYPE html> -->
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Auction Submitted</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
</head>
<body>

<%@ include file="../header.jsp" %>

<div class="container">
    <h1>Your auction submitted successfully.</h1>
    <p>Thanks for your auction!</p>

    <h4>Please, navigate to one of the following pages.</h4>
    <p>
        <a class="button button-primary" href=/auction.do?action=getAnAuction&aid=${aid}>View/Edit Submitted Auction</a>
        <a class="button" href="/user/homepage.jsp">Homepage</a>
    </p>
</div>

<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
