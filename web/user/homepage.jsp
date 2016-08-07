<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Welcome Page</title>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>

<c:if test ="${not empty uid}">
<div class="container">
    <!-- HEADER STUFF -->
    <div class="row">
        <div class="one column">
            <a href="/user/homepage.jsp">
                <img class="u-max-full-width" src="/images/logo.png">
            </a>
        </div>
        <div class="offset-by-seven four columns">
            <ul class="nav u-full-width row">
                <li class="offset-by-one-third one-third column newMessage tooltip"><span class="tooltipFire">Messages</span>
                    <div class="tooltipText"><div class="tooltipMargin"></div>
                        <a class="button" href="/message.do?action=listInbox">Inbox</a>
                        <a class="button" href="/message.do?action=listSent">Sent</a>
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
        <section class="four columns">
            <h1><span class="look">></span> Auctions</h1>
            <a class="button button-primary" href="/auction.do?action=newAuction">Create an auction</a>
            <a class="button button u-top-10" href="/auction.do?action=getAllAuctions">View your auctions</a>
            <a class="button button u-top-10" href="/auction.do?action=getAllActiveAuctions">View all active auctions</a>
        </section>


        <section class="search eight columns">
            <h1><span class="look">></span> Search for a product</h1>
            <form action="/search.do" method="POST">
                <input class="u-full-width" type="text" name="name">
                <button class="button-primary" type="submit" name="action" value="doSimpleSearch">Search</button>
            </form>
            <a class="button" href="/search.do?action=advancedSearch">Advanced Search</a>
        </section>
    </div>
    <!-- end of search row -->
    </c:if>
    <c:if test ="${empty uid}">
    <h3>You are logged out</h3>
    <p>Please go to the <a href="/">start page</a> and login again.</p>
    </c:if>

</body>
</html>