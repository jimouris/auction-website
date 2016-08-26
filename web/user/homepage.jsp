<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Welcome Page</title>
    <jsp:useBean id="notifLst" class="java.util.ArrayList" scope="request" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>

<c:if test="${not empty user.userId}">
<div class="container">
    <!-- HEADER STUFF -->
    <div class="row">
        <div class="one column">
            <a href="/user/homepage.jsp">
                <img class="u-max-full-width" src="/images/logo.png">
            </a>
            Hello ${sessionScope.user.getFirstname()},
        </div>
        <div class="offset-by-seven four columns">
            <ul class="nav u-full-width row">
                <li class="one-third column newMessage tooltip"><span class="tooltipFire">Messages</span>
                    <div class="tooltipText"><div class="tooltipMargin"></div>
                        <a class="button" href="/message.do?action=listInbox">Inbox</a>
                        <a class="button" href="/message.do?action=listSent">Sent</a>
                    </div>
                </li>
                <li class="one-third column newRating tooltip"><span class="tooltipFire">Ratings</span>
                    <div class="tooltipText"><div class="tooltipMargin"></div>
                        <a class="button" href="/rate.do?action=listFrom">From</a>
                        <a class="button" href="/rate.do?action=listTo">To</a>
                    </div>
                </li>
                <li class="one-third column">
                    <a href="/logout.do"><span class="delete">Logout</span></a>
                </li>
            </ul>
        </div>
    </div>
    <!-- end of header row -->

    <!-- SEARCH ROW -->
    <div class="row ">
        <section class="five columns">
            <h1><span class="look">></span> Auctions</h1>
            <a class="button button-primary" href="/auction.do?action=newAuction">Create an auction</a>
            <a class="button button" href="/auction.do?action=getAuctionsYouHaveBought">View all auctions you have bought</a>
            <a class="button button" href="/auction.do?action=getAllAuctions">View all your auctions</a>
            <a class="button button" href="/auction.do?action=getAllYourEndedAuctions">View all your ended auctions</a>
            <a class="button button" href="/auction.do?action=getAllActiveAuctions">Browse to all active auctions</a>
            <a class="button button" href="/auction.do?action=getAllEndedAuctions">Browse to all ended auctions</a>
        </section>


        <section class="search seven columns">
            <h1><span class="look">></span> Search for a product</h1>
            <form action="/search.do" method="POST">
                <input class="u-full-width" type="text" name="name">
                <button class="button-primary" type="submit" name="action" value="doSimpleSearch">Search</button>
            </form>
            <a class="button" href="/search.do?action=advancedSearch">Advanced Search</a>
        </section>
    </div>
    <!-- end of search row -->
    <hr />
    <div class="row">
        <h5>Latest notifications</h5>
        <c:if test="${not empty notifLst}">
            <c:forEach var="notification" items="${notifLst}">
                <c:if test="${notification.isSeen == 1}">
                    <p class="c-notification--seen">
                    You have a new message from user ${notification.actor.username}. <a href="/notification.do?action=viewNotification&nid=${notification.notificationId}">view</a>
                    </p>
                </c:if>
                <c:if test="${notification.isSeen == 0}">
                    <p class="c-notification--unseen">
                        You have a new message from user ${notification.actor.username}. <a href="/notification.do?action=viewNotification&nid=${notification.notificationId}">view</a>
                    </p>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
    </c:if>
    <c:if test ="${empty user.userId}">
    <h3>You are logged out</h3>
    <p>Please go to the <a href="/">start page</a> and login again.</p>
    </c:if>

</body>
</html>