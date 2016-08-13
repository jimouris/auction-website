<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Auction Info</title>
    <jsp:useBean id="auction" class="javauction.model.AuctionEntity" scope="request" />
    <jsp:useBean id="categoryLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="usedCategories" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="bidLst" class="java.util.ArrayList" scope="request" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
</head>
<body>
<!-- HEADER STUFF -->
<c:if test="${not empty user.userId}">
    <a href="/user/homepage.jsp">Homepage</a>
    <a href="/auction.do?action=getAllAuctions">View All Auctions</a>
</c:if>
<c:if test="${empty user.userId}">
    <a href="/public/">Guest, Homepage</a>
</c:if>
<!-- end of header row -->

<c:if test ="${not empty auction}">

    <div class="custom-container">
        <form action="/auction.do" method="post">
            <input type="hidden" value=${auction.auctionId} name="aid">
            <c:if test="${auction.isStarted == 0}" >
                <c:if test="${isEnded}" >
                    <h2>The auction has ended</h2>
                </c:if>
                <c:if test="${not isEnded}" >
                    <h2>The auction is inactive</h2>
                    <input type=submit value=activateAuction name="action">
                </c:if>
            </c:if>
        </form>
        <c:if test="${auction.isStarted == 1}">
            <h2>The auction is started-active</h2>
        </c:if>

        <c:if test="${isSeller and not isEnded and empty bidLst}">
            <a class="js-make-writable button">edit some fields</a>
        </c:if>

        <form action="/auction.do" method="POST" id="view_updateAuction">
            <div class="row">
                <div class="one-half column">
                    <label>Name</label>
                    <input class="u-full-width" type="text" name="name" minlength="2" required disabled value="${auction.name}">

                    <c:if test="${isSeller}">
                        <label for="categories">All Categories (select to update):</label>
                        <select class="a-select--multiple" id="categories" name="categories" multiple disabled size=${categoryLst.size()}>
                            <c:forEach var="category" items="${categoryLst}">
                                <option value=${category.categoryId}>${category.categoryName}</option>
                            </c:forEach>
                        </select>
                        <br>
                    </c:if>

                    <label>Description</label>
                    <input class="u-full-width" type="text" name="description" minlength="2" disabled required autofocus value="${auction.description}">

                    <label>Lowest bid</label>
                    <input class="u-full-width" type="number" name="lowestBid" minlength="1" disabled required value="${auction.lowestBid}">

                    <c:if test="${isSeller}">
                        <label>Final price (after bidding)</label>
                        <input class="u-full-width" type="number" name="finalPrice" minlength="1" disabled readonly value="${auction.finalPrice}">
                    </c:if>

                    <label>Buy price (Instant buy)</label>
                    <input class="u-full-width" type="number" name="buyPrice" minlength="1" disabled required value="${auction.buyPrice}">
                </div>

                <div class="one-half column">
                    <label for="categories">Selected Categories:</label>
                    <select class="a-select--multiple" id="categories" name="categories" multiple disabled readonly size=${categoryLst.size()}>
                        <c:forEach var="category" items="${usedCategories}">
                            <option value=${category.categoryId}>${category.categoryName}</option>
                        </c:forEach>
                    </select>
                    <br>

                    <label>City</label>
                    <input class="u-full-width" type="text" name="city" minlength="2" required disabled value="${auction.city}">

                    <label>The country where is the auction item.</label>
                    <input class="u-full-width" type="text" name="country" minlength="2" required disabled value="${auction.country}">

                    <c:if test="${isSeller}">
                        <label>Starting date</label>
                        <input class="u-full-width" type="date" name="startingDate" required disabled  value="${auction.startingDate}">
                    </c:if>

                    <label>Ending date</label>
                    <input class="u-full-width" type="date" name="endingDate" required disabled value="${auction.endingDate}">

                    <input type="hidden" name="aid" value="${auction.auctionId}">
                </div>
            </div>
            <c:if test="${isSeller}">
                <button class="button-primary" type="submit" name="action" value="updateAuction" disabled>Edit/Update</button>
                <button class="button" type="submit" name="action" value="deleteAuction" disabled>Delete Auction</button>
            </c:if>
        </form>
    </div>

    <jsp:useBean id="seller" scope="request" class="javauction.model.UserEntity"/>
    <jsp:useBean id="avg_rating" scope="request" type="java.lang.Double"/>
    <c:if test="${not empty seller}">
        <div class="custom-container">
            <h5>Seller Info</h5>
            <p>${seller.username} ${seller.firstname} ${seller.lastname} ${avg_rating}</p>
        </div>
    </c:if>

    <div class="custom-container">
        <c:if test="${isEnded}">
            <h5>The auction has ended. (Ending date ${auction.endingDate})</h5>
            <c:if test="${not empty bidLst}">
                <%-- if the final bidder is the current user show him a "message the seller" button --%>
                <c:if test="${user.userId == biddersLst[0].userId}">
                    <h5>You won the auction, final bid (<span>${bidLst[0].amount} &euro;</span>) was placed by you.</h5>
                    <a class="button" href="/message.do?action=getConversation&rid=${auction.sellerId}&aid=${auction.auctionId}">Contact the seller</a>
                    <a class="button" href="/rate.do?action=getRating&to_id=${auction.sellerId}&aid=${auction.auctionId}">Rate the seller</a>
                </c:if>
                <c:if test="${user.userId != biddersLst[0].userId}">
                    <h5>Final bid: <span>${bidLst[0].amount} &euro;</span></h5>
                </c:if>

                <c:if test="${isSeller}">
                    <h5>All submitted bids:</h5>
                    <c:forEach var="bid" items="${bidLst}" varStatus="status">
                        <h6>${biddersLst[status.index].firstname} ${biddersLst[status.index].lastname} ${bid.amount}&euro; ${bid.bidTime}</h6>
                    </c:forEach>
                    <a class="button" href="/message.do?action=getConversation&rid=${auction.buyerId}&aid=${auction.auctionId}">Contact the buyer</a>
                    <a class="button" href="/rate.do?action=getRating&to_id=${auction.buyerId}&aid=${auction.auctionId}">Rate the buyer</a>
                </c:if>
            </c:if>
            <c:if test="${empty bidLst}">
                <h5>No bids placed for this auction.</h5>
            </c:if>
        </c:if>
        <c:if test="${not isEnded}">
            <c:if test="${not empty bidLst}">
                <c:if test="${user.userId == biddersLst[0].userId}">
                    <h5>Current bid (<span>${bidLst[0].amount} &euro;</span>) was placed by you.</h5>
                </c:if>
                <c:if test="${user.userId != biddersLst[0].userId}">
                    <h5>Current bid: <span>${bidLst[0].amount} &euro;</span></h5>
                </c:if>

                <c:if test="${not isSeller and not empty user.userId}">
                    <form action="/auction.do" method="post" id="bidAuction">
                        <input type="number" min="${bidLst[0].amount +1}" value="${bidLst[0].amount +1}" name="bid">
                        <input type="hidden" name="aid" value="${auction.auctionId}">
                        <button class="button-primary" type="submit" name="action" id="bidAuction" value="bidAuction">Bid for this item</button>
                    </form>
                </c:if>
                <c:if test="${isSeller}">
                    <h5>All submitted bids:</h5>
                    <c:forEach var="bid" items="${bidLst}" varStatus="status">
                        <h6>${biddersLst[status.index].firstname} ${biddersLst[status.index].lastname} ${bid.amount}&euro; ${bid.bidTime}</h6>
                    </c:forEach>
                </c:if>
            </c:if>
            <c:if test="${empty bidLst}">
                <c:if test="${not isSeller and not empty user.userId}">
                    <h5>No bids placed yet.</h5>
                    <form action="/auction.do" method="post" id="firstBid">
                        <input type="number" min="${auction.lowestBid}" value="${auction.lowestBid}" name="bid">
                        <input type="hidden" name="aid" value="${auction.auctionId}">
                        <button class="button-primary" type="submit" name="action" value="bidAuction">Make the first bid</button>
                    </form>
                </c:if>
                <c:if test="${isSeller}">
                    <h5>Your auction has no bids yet.</h5>
                </c:if>
            </c:if>
        </c:if>
    </div>
</c:if>

<c:if test ="${empty auction}">
    <h3>The auction with id ${param.aid} does not exist!</h3>
</c:if>

<script src="https://code.jquery.com/jquery-2.2.4.min.js" type="text/javascript"></script>
<script>
    $(document).ready(function() {
        window.inputActive = false;

        $('#firstBid, #bidAuction').submit(function(event) {
            var bidIt = confirm('Are you sure you want to bid?');
            if (bidIt){
                return true;
            } else{
                return false;
            }
        });

        $('.js-make-writable').on('click', function () {
            if (window.inputActive) {
                $('input:not([readonly]), button').prop('disabled', true);
                window.inputActive = false;
            }
            else {
                $('[disabled]').prop('disabled', false);
                window.inputActive = true;
            }
        });
    });
</script>

</body>
</html>
