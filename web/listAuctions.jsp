<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View User's Auctions</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <a href="/homepage.jsp">homepage</a>

    <h1>List Auctions</h1>
    <jsp:useBean id="auctionLst" class="java.util.ArrayList" scope="request" />

    <table>
        <c:forEach var="auction" items="${auctionLst}">
            <tr>
                <td>${auction.name}</td>
                <td>${auction.description}</td>
                <td>${auction.lowestBid}</td>
            </tr>
        </c:forEach>

        <c:if test="${auctionLst.size() == 0}">
            <tr>
                <td>No auctions yet.</td>
            </tr>
        </c:if>
    </table>
</div>
</body>
</html>
