<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>

    <link href="./css/skeleton.css" rel="stylesheet">
</head>
<body>
    <!-- HEADER STUFF -->
    <a href="./homepage.jsp">Homepage</a>
    <a href="auction.do?action=getAllAuctions">View All Auctions</a>
    <!-- end of header row -->

    <jsp:useBean id="auction" class="javauction.model.AuctionEntity" scope="request" />
    <form action="auction.do" method="post">
        <input type="hidden" value=${auction.auctionId} name="aid">
        <c:if test="${auction.isStarted == 0}">
            <input type=submit value=activateAuction name="action">
        </c:if>
    </form>
    <c:if test="${auction.isStarted == 1}">
        <h2>The auction is started-active</h2>
    </c:if>

    <dl>Name</dl>
    <dd>${auction.name}</dd>
    <dl>Description</dl>
    <dd>${auction.description}</dd>
    <dl>Lowest Bid</dl>
    <dd>${auction.lowestBid}</dd>
    <dl>Current Bid</dl>
    <dd>${auction.currentBid}</dd>
    <dl>Final Price</dl>
    <dd>${auction.finalPrice}</dd>
    <dl>Starting Date</dl>
    <dd>${auction.startingDate}</dd>
    <dl>Ending Date</dl>
    <dd>${auction.endingDate}</dd>
    <dl>Country</dl>
    <dd>${auction.country}</dd>
    <dl>City</dl>
    <dd>${auction.city}</dd>
    <dl>Buy Price</dl>
    <dd>${auction.buyPrice}</dd>

    <%--<h4 class=important>The auction is not active yet. Set an end date and active it.</h4>--%>
    <%--<form class=row action=./editAuction method=GET>--%>
        <%--<label>Set end date: <input name=date type=date id=js-endDate required/></label>--%>
        <%--<input type=hidden name=auctionid value=" + auction_id + " />--%>
        <%--<input type=hidden name=mode value=start />--%>
        <%--<input class=button-primary type=submit value='Active it'>--%>
    <%--</form>--%>
<!-- end of container -->

</body>
</html>
