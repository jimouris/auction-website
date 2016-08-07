<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Ted Project at DI</title>
    <jsp:useBean id="categoryLst" class="java.util.ArrayList" scope="request" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
</head>
<body>
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
                <li class="offset-by-one-third one-third column newMessage tooltip">
                    Messages
                    <a href="/user/listInbox.jsp">Inbox</a><br />
                    <a href="/user/listSent.jspt">Sent</a></div>
                </li>
                <li class="one-third column">
                    <a href="/logout.do"><span class="delete">Logout</span></a>
                </li>
            </ul>
        </div>
    </div>
    <!-- end of header row -->

    <!-- NEW AUCTION ROW -->
    <div class="row">
        <div class="column">
            <section class="register u-full-width">
                <h1>Create a new auction</h1>
                <!-- Restriction for validation are inserted on the end of input -->
                <form action="/auction.do" method="POST" id="new_auction">

                    <label for="name">Name of auctioned item:</label>
                    <input class="u-full-width" type="text" id="name" name="name" minlength="2" required autofocus>

                    <label for="categories">Select at least one category:</label>
                    <select class="a-select--multiple" id="categories" name="categories" multiple size=${categoryLst.size()}>
                        <c:forEach var="category" items="${categoryLst}">
                            <option value=${category.categoryId}>${category.categoryName}</option>
                        </c:forEach>
                    </select>

                    <b>Can someone instantly buy your product?</b>
                    <label class="u-full-width">
                        <input type="radio" name="instantBuy" value="false" onclick="document.getElementById('buyPrice').setAttribute('style','display: none')" checked>
                        <span class="label-body">No. Everyone will bid for my product.</span>
                    </label>
                    <label class="u-full-width">
                        <input type="radio" name="instantBuy" value="true" onclick="document.getElementById('buyPrice').setAttribute('style','display: block')">
                        <span class="label-body">Yea! Someone can instantly buy my product.</span>
                    </label>
                    <div class="u-softhide u-full-width" id="buyPrice">
                        <label>Ok. Set the buy price:</label>
                        <input class="u-full-width" type="number" min=0 max=10000 name="buyPrice">
                    </div>

                    <label for="lowestBid">Set the lowest bid</label>
                    <input class="u-full-width" type="number" id="lowestBid" name="lowestBid" minlength="1" required>

                    <label for="city">The city where is the auctioned item.</label>
                    <input class="u-full-width" type="text" id="city" name="city" minlength="2" required>

                    <%-- todo: add a selectbox for countries --%>
                    <label for="country">The country where is the auction item.</label>
                    <input class="u-full-width" type="text" id="country" name="country" minlength="2" required>

                    <label for="description">Description</label>
                    <textarea form="new_auction" class="u-full-width" placeholder="a high quality product..." name="description" id="description"></textarea>

                    <b>Activate the auction now?</b>
                    <label>
                        <input type="radio" name="startToday" value="false" onclick="document.getElementById('activeDays').setAttribute('style','display: none')" checked>No
                    </label>
                    <label>
                        <input type="radio" name="startToday" value="true" onclick="document.getElementById('activeDays').setAttribute('style','display: block')">Yes
                    </label>
                    <div class="u-softhide" id="activeDays">
                        <label for="endDate">So How many days this auction will be active?</label>
                        <input type="number" name="activeDays" value="1" id="endDate">
                    </div>

                    <input class="button-primary u-pull-right" type="submit" name="action" value="addNew">
                </form>
            </section>
        </div>

    </div>
    <!-- END OF NEW AUCTION -->

</div>

</body>
</html>
