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
    <jsp:useBean id="sendersLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="auctionsLst" class="java.util.ArrayList" scope="request" />
</head>
<body>

<div class="container">
    <a href="/user/homepage.jsp">home</a>
    <h2>Your received ratings</h2>

    <div>
        <c:if test="${not empty ratingsLst}">
            <c:forEach var="rating" items="${ratingsLst}" varStatus="status">
                <a class="message message--inbox">
                    <span class="message__text">From</span>
                    <span class="message__composer">${sendersLst[status.index].firstname} ${sendersLst[status.index].lastname}</span>
                    <span class="message__text">for</span>
                    <span class="message__composer">${auctionsLst[status.index].name}:</span>
                    <span class="message__text">${rating.rating}</span>
                </a>
            </c:forEach>
        </c:if>
    </div>
</div>

</body>
</html>
