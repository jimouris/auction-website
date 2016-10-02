<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View User's Auctions</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>

<div class="custom-container">
    <h1>List Auctions</h1>
    <jsp:useBean id="auctionLst" class="java.util.ArrayList" scope="request" />

    <table>
        <thead>
            <th>Auction name</th>
            <th>Description</th>
            <th>Lowest bid</th>
            <th></th>
        </thead>
        <c:forEach var="auction" items="${auctionLst}">
            <tr>
                <td>${auction.name}</td>
                <td>${fn:replace(auction.description, fn:substring(auction.description, 40, fn:length(auction.description)), '...')}</td>
                <td>${auction.lowestBid}</td>
                <td><a class="button button-primary" href=/auction.do?action=getAnAuction&aid=${auction.auctionId}>View auction</a></td>
            </tr>
        </c:forEach>


        <c:if test="${auctionLst.size() == 0}">
            <tr>
                <td>No auctions yet.</td>
            </tr>
        </c:if>
    </table>
</div>

<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
