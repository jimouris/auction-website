<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Results</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <a href="/homepage.jsp">homepage</a>

    <h1>Search Results</h1>
    <jsp:useBean id="auctionsLst" class="java.util.ArrayList" scope="request" />

    <table>
        <c:forEach var="auction" items="${auctionsLst}">
            <tr>
                <td>${auction.name}</td>
                <td>${auction.description}</td>
                <td>${auction.lowestBid}</td>
                <td><a class="button button-primary" href=auction.do?action=getAnAuction&aid=${auction.auctionId}>View Auction</a></td>
            </tr>
        </c:forEach>

        <c:if test="${auctionsLst.size() == 0}">
            <tr>
                <td>No auctions found.</td>
            </tr>
        </c:if>
    </table>
</div>
</body>
</html>
