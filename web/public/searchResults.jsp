<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Results</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">

    <jsp:useBean id="auctionsLst" class="java.util.ArrayList" scope="request" />
</head>
<body>
<%@ include file="../header.jsp" %>

<div class="container">
    <h3>Auctions</h3>
    <c:forEach var="auction" items="${auctionsLst}">
        <div class="row c-result">
            <div class="two columns">
                <c:if test="${not empty auction.images}">
                    <img class="img-resp" src="image_auction/${auction.images.iterator().next().imageFileName}">
                </c:if>
                <c:if test="${empty auction.images}">
                    <span class="icon-picture c-result__no-img">
                        no image set
                    </span>
                </c:if>
            </div>
            <div class="six columns">
                <h6>${fn:replace(auction.name, fn:substring(auction.name, 80, fn:length(auction.name)), '...')}</h6>
                <c:if test="${empty auction.description}">
                    <p class="c-result--empty">No description provided</p>
                </c:if>
                <c:if test="${not empty auction.description}">
                    <p>${fn:replace(auction.description, fn:substring(auction.description, 120, fn:length(auction.description)), '...')}</p>
                </c:if>
            </div>
            <div class="two columns">
                <c:if test="${not empty auction.categories}">
                    <c:forEach var="category" items="${auction.categories}">
                        <a href="https://localhost:8443/search.do?action=searchAuctions&categories=${category.categoryId}&reallyActive=true"
                           class="c-result__categories">${fn:replace(category.categoryName, fn:substring(category.categoryName, 15, fn:length(category.categoryName)), '...')}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${empty auction.categories}">
                    <p class="c-result--empty">No categories provided</p>
                </c:if>
            </div>
            <div class="two columns"><a class="button button-primary" href=/auction.do?action=getAnAuction&aid=${auction.auctionId}>View auction</a></div>
        </div>

    </c:forEach>

    <c:if test="${auctionsLst.size() == 0}">
        <p>
            No auctions found.
        </p>
    </c:if>
    <c:if test="${auctionsLst.size() != 0}">
        <div class="row">
            <%@ include file="../paginationLinks.jsp" %>
        </div>
    </c:if>
    <c:if test="${auctionsLst.size() == 0 and not empty previousPage}">
        <div class="row">
            Last page. Please go to <a href="${previousPage}">previous page</a>
        </div>
    </c:if>
</div>

<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
