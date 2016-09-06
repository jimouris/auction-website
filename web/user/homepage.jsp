<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Welcome Page</title>
    <jsp:useBean id="notifLst" class="java.util.ArrayList" scope="request" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>

<c:if test="${empty user}">
    <c:redirect url="/"/>
</c:if>
<c:if test ="${not empty user.userId}">
<div class="container">
    <%@ include file="./header.jsp" %>
    <!-- SEARCH ROW -->
    <div class="row ">
        <section class="five columns">
            <h1><span class="look">></span> Auctions</h1>
            <a class="button button-primary" href="/auction.do?action=newAuction">Create an auction</a>
            <a class="button button" href="/auction.do?action=getAuctionsYouHaveBought">View all auctions you have bought</a>
            <a class="button button" href="/auction.do?action=getAllAuctions">View all your auctions</a>
            <a class="button button" href="/auction.do?action=getAllYourEndedAuctions">View all your ended auctions</a>
            <a class="button button" href="/auction.do?action=getAllActiveAuctions">Browse to all active auctions</a>
            <a class="button button" href="/auction.do?action=getAllEndedAuctions">Browse to all ended auctions</a>
        </section>


        <section class="search seven columns">
            <h1><span class="look">></span> Search for a product</h1>
            <form action="/search.do" method="POST">
                <input class="u-full-width" type="text" name="name">
                <button class="button-primary" type="submit" name="action" value="doSimpleSearch">Search</button>
            </form>
            <a class="button" href="/search.do?action=advancedSearch">Advanced Search</a>
        </section>
    </div>
    <!-- end of search row -->
    </c:if>
    <c:if test ="${empty user.userId}">
    <h3>You are logged out</h3>
    <p>Please go to the <a href="/">start page</a> and login again.</p>
    </c:if>

    <script src="../js/jquery.min.js"></script>
    <script src="../js/scripts.js"></script>
</body>
</html>