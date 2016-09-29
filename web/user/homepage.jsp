<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


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
    <%@ include file="../header.jsp" %>
    <!-- SEARCH ROW -->
    <div class="row ">
        <section class="five columns">
            <h1><span class="look">></span> Auctions</h1>
            <a class="button button-primary" href="/auction.do?action=newAuction">Create an auction</a>

            <a class="button button" href="/search.do?action=searchAuctions&boughtBy=you">View all auctions you have bought</a>

            <a class="button button" href="/search.do?action=searchAuctions&seller=you">View all your auctions</a>
            <a class="button button" href="/search.do?action=searchAuctions&seller=you&isActive=0">View all your ended auctions</a>

            <a class="button button" href="/search.do?action=searchAuctions&isActive=1">Browse to all active auctions</a>
            <a class="button button" href="/search.do?action=searchAuctions&isActive=0">Browse to all ended auctions</a>
        </section>

        <section class="search seven columns">
            <h1><span class="look">></span> Search for a product</h1>
            <form action="/search.do" method="POST">
                <input class="u-full-width" type="text" name="name">
                <button class="button-primary" type="submit" name="action" value="searchAuctions">Search</button>
            </form>
            <a class="button" href="/search.do?action=advancedSearch">Advanced Search</a>
        </section>
</div>

<br/>
<br/>
<br/>
<div>
    <div class="row ">
        <section>
            <h4><span class="look">></span> Recommendations</h4>
            <c:if test="${not empty recommendationLst}">
                <c:forEach var="recomendation" items="${recommendationLst}" varStatus="status">

                    <tr>
                        <td>${recomendation.name}</td>
                        <td>${fn:replace(recomendation.description, fn:substring(recomendation.description, 20, fn:length(recomendation.description)), '...')}</td>
                        <td>${recomendation.lowestBid}</td>
                        <td><a class="button" href=/auction.do?action=getAnAuction&aid=${recomendation.auctionId}>View auction</a></td>
                        <br/>
                    </tr>

                </c:forEach>
            </c:if>
            <c:if test="${empty recommendationLst}">
                We have no recommendations for you. Try placing some bids and we will take care of it.
            </c:if>
        </section>
    </div>
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