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
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>

<c:if test="${empty user}">
    <c:redirect url="/"/>
</c:if>
<c:if test ="${not empty user.userId}">

<%@ include file="../header.jsp" %>

<div class="container">
    <!-- SEARCH ROW -->
    <div class="row">
        <h3><span class="icon-search"></span> Search for a product</h3>
        <form class="row" action="/search.do" method="POST">
            <input class="seven columns" type="text" name="name">
            <button class="three columns button-primary" type="submit" name="action" value="searchAuctions">Search</button>
            <a class="two columns advanced" href="/search.do?action=advancedSearch">Advanced</a>
        </form>
    </div>

    <div class="row ">
        <section class="four columns">
            <h3>Auctions</h3>
            <a class="button button-primary" href="/auction.do?action=newAuction">Create an auction</a>

            <a class="button button" href="/search.do?action=searchAuctions&bidBy=you">View all auctions you have bid</a>

            <a class="button button" href="/search.do?action=searchAuctions&seller=you">View all your auctions</a>
            <a class="button button" href="/search.do?action=searchAuctions&seller=you&isActive=0">View all your ended auctions</a>

            <a class="button button" href="/search.do?action=searchAuctions&isActive=1">Browse to all active auctions</a>
            <a class="button button" href="/search.do?action=searchAuctions&isActive=0">Browse to all ended auctions</a>
        </section>

        <section class="search eight columns">
            <h3>Recommendations</h3>
            <c:if test="${not empty recommendationLst}">
                <c:forEach var="auction" items="${recommendationLst}" varStatus="status">
                    <div class="row c-result">
                        <div class="two columns">
                            <c:if test="${not empty auction.images}">
                                <img class="img-resp" src="/image_auction/${auction.images.iterator().next().imageFileName}">
                            </c:if>
                            <c:if test="${empty auction.images}">
                    <span class="icon-picture c-result__no-img">
                        no image set
                    </span>
                            </c:if>
                        </div>
                        <div class="seven columns">
                            <h6>${fn:replace(auction.name, fn:substring(auction.name, 60, fn:length(auction.name)), '...')}</h6>
                            <c:if test="${empty auction.description}">
                                <p class="c-result--empty">No description provided</p>
                            </c:if>
                            <c:if test="${not empty auction.description}">
                                <p>${fn:replace(auction.description, fn:substring(auction.description, 80, fn:length(auction.description)), '...')}</p>
                            </c:if>
                        </div>
                        <div class="three columns"><a class="button button-primary" href=/auction.do?action=getAnAuction&aid=${auction.auctionId}>View auction</a></div>
                    </div>

                </c:forEach>
            </c:if>
            <c:if test="${empty recommendationLst}">
                We have no recommendations for you. Try placing some bids and we will take care of it.
            </c:if>
        </section>
</div>

<br/>
<br/>
<br/>
<div>
    <div class="row ">
        <section>

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