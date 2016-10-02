<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Your submitted ratings</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.css">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">

    <jsp:useBean id="ratingsLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="sendersLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="auctionsLst" class="java.util.ArrayList" scope="request" />
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="/"/>
</c:if>
<%@ include file="../header.jsp" %>

<div class="container">
    <c:if test="${not empty ratingsLst}">
        <h2>Your received ratings</h2>
        <h5>Average stars<span data-rating="${avg_rating}" class="c-rate"></span></h5>
        <h5>Total reputation ${total_reputation}</h5>
        <c:forEach var="rating" items="${ratingsLst}" varStatus="status">
            <a class="message message--inbox">
                <span class="message__text">From</span>
                <span class="message__composer">${sendersLst[status.index].firstname} ${sendersLst[status.index].lastname}</span>
                <span class="message__text">for</span>
                <span class="message__composer">${auctionsLst[status.index].name}:</span>
                <span class="message__text c-rate" data-rating="${rating.rating}"></span>
            </a>
        </c:forEach>
    </c:if>
    <c:if test="${empty ratingsLst}">
        <h5>You have not received any ratings yet.</h5>
    </c:if>
</div>
<script src="../js/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
