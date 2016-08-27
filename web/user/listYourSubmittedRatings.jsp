<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Your submitted ratings</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
    <jsp:useBean id="ratingsLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="receiversLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="auctionsLst" class="java.util.ArrayList" scope="request" />
</head>
<body>

<div class="container">
    <c:if test="${empty user}">
        <c:redirect url="/"/>
    </c:if>
    <%@ include file="./header.jsp" %>
    <div>
        <c:if test="${not empty ratingsLst}">
            <h2>Your submitted ratings</h2>
            <h5>Average score ${avg_rating}</h5>
            <c:forEach var="rating" items="${ratingsLst}" varStatus="status">
                <a href="/rate.do?action=getRating&to_id=${rating.toId}&aid=${rating.auctionId}" class="message message--inbox">
                    <span class="message__text">To</span>
                    <span class="message__composer">${receiversLst[status.index].firstname} ${receiversLst[status.index].lastname}</span>
                    <span class="message__text">for</span>
                    <span class="message__composer">${auctionsLst[status.index].name}:</span>
                    <span class="message__text">${rating.rating}</span>
                    <input type="hidden" name="to_user" value=${receiversLst[status.index]} />
                </a>
            </c:forEach>
        </c:if>
        <c:if test="${empty ratingsLst}">
            <h5>You have not submitted any ratings yet.</h5>
        </c:if>
    </div>
</div>
<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
