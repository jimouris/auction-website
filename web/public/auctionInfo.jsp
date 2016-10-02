<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Auction Info</title>
    <jsp:useBean id="auction" class="javauction.model.AuctionEntity" scope="request" />
    <jsp:useBean id="usedCategories" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="imageLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="bidLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="seller" class="javauction.model.UserEntity" scope="request"/>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.css">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>
<!-- HEADER STUFF -->
<%@ include file="../header.jsp" %>

<c:if test ="${not empty auction}">

    <c:if test="${not empty errorMsg}">
        <p class="container status--error">${errorMsg}</p>
    </c:if>
    <c:if test="${not empty successMsg}">
        <p class="container status--success">${successMsg}</p>
    </c:if>

    <div class="container">
        <%-- if the auction is not yet started, then ask for an ending date and then activate the auction --%>
        <c:if test="${auction.isActive == 0 and not isEnded and isSeller}" >
            <p class="status--error eight columns u-no-bottom">The auction is inactive. to start the auction click activate.</p>
            <span class="button button-primary js-toggle-date">activate</span>
            <div class="js-date row">
                <form action="/auction.do" method="post" class="four columns u-no-bottom form-changeDate" >
                    <input type="hidden" value=${auction.auctionId} name="aid">
                    <input type="hidden" value=activateAuction name="action">
                    <label>Please set an ending date:</label>
                    <input type="date" value="${auction.endingDate}" name="endingDate" required>
                    <button type="submit" class="button button-primary">set date and activate</button>
                </form>
            </div>
        </c:if>
        <c:if test="${isSeller and not isEnded and empty bidLst}">
            <a class="js-make-writable button" href="/auction.do?action=editAuction&aid=${auction.auctionId}">edit auction</a>
        </c:if>
        <c:if test="${auction.isActive == 1 and not isEnded}">
            <p>The auction is active</p>
        </c:if>
        <c:if test="${isEnded}">
            <p>The auction has ended</p>
        </c:if>


        <div class="row">
            <div class="">
                <h3>${auction.name}</h3>

                <c:if test="${not empty usedCategories}">
                    <h5>Categories:</h5>
                    <c:forEach var="category" items="${usedCategories}">
                        <option value=${category.categoryId}>${category.categoryName}</option>
                    </c:forEach>
                </c:if>

                <c:if test="${not empty auction.description}">
                    <h5>Description:</h5>
                    <p>${auction.description}</p>
                </c:if>

                <c:if test="${not empty imageLst}">
                    <h5>Images:</h5>
                    <c:forEach var="image" items="${imageLst}">
                        <img src="image_auction/${image.imageFileName}" class="auction__img">
                    </c:forEach>
                </c:if>

                <%-- a registered user should be able to see buy now button --%>
                <%-- also if the seller didn't provide a buyPrice, then its value is -1 --%>
                <c:if test="${not isSeller and not empty user.userId and auction.buyPrice > 0}">
                    <c:if test="${not empty bidLst}">
                        <c:if test="${bidLst[0].amount < auction.buyPrice}">
                        <h5>Buy now for ${auction.buyPrice}</h5>
                        <form action="/auction.do" method="post">
                            <input type="hidden" name="aid" value="${auction.auctionId}">
                            <input type=submit name="action" value="buyAuction">
                        </form>
                        </c:if>
                    </c:if>
                    <c:if test="${empty bidLst}">
                        <h5>Buy now for ${auction.buyPrice}</h5>
                        <form action="/auction.do" method="post">
                            <input type="hidden" name="aid" value="${auction.auctionId}">
                            <input type=submit name="action" value="buyAuction">
                        </form>
                    </c:if>
                </c:if>

                <%-- let selller see this info, because is something that is initialised by him --%>
                <c:if test="${isSeller}">
                    <h5>Bids start from:</h5>
                    <p>${auction.lowestBid}</p>
                </c:if>

                <h5>Where is the item:</h5>
                <p>${auction.location}</p>
                <p>${auction.country}</p>
                <c:if test="${not empty auction.longitude && not empty auction.latitude}">
                    <div id="map" style="height: 250px;"></div>
                </c:if>

                <c:if test="${not empty auction.endingDate}">
                    <h5>The auction will end at:</h5>
                    <p>${auction.endingDate}</p>
                </c:if>

            </div>
        </div>
    </div>

    <!-- show the rating of seller -->
    <c:if test="${not empty seller}">
        <div class="container">
            <h5>Seller Info</h5>
            <c:if test="${not empty avg_rating}">
                <p>${seller.username} ${seller.firstname} ${seller.lastname} <span data-rating="${avg_rating}" class="c-rate"></span> rep ${total_reputation}</p>
            </c:if>
            <c:if test="${empty avg_rating}">
                <p>The user ${seller.username} ${seller.firstname} ${seller.lastname} has no ratings yet.</p>
            </c:if>
        </div>
    </c:if>

    <%-- showing appropriate messages if the auction has ended --%>
    <c:if test="${isEnded}">
        <div class="container">
            <h5>The auction has ended. (Ending date ${auction.endingDate})</h5>
            <c:if test="${not empty bidLst}">
                <%-- if the final bidder is the current user show him a "message the seller" button --%>
                <c:if test="${user.userId == biddersLst[0].userId}">
                    <h5>You won the auction, final bid (<span>${bidLst[0].amount} &euro;</span>) was placed by you.</h5>
                    <a class="button" href="/message.do?action=getConversation&rid=${auction.sellerId}&aid=${auction.auctionId}">Contact the seller</a>
                    <a class="button" href="/rate.do?action=getRating&to_id=${auction.sellerId}&aid=${auction.auctionId}">Rate the seller</a>
                </c:if>
                <%-- the viewer is not the one who buy it, so just write a simple message --%>
                <c:if test="${user.userId != biddersLst[0].userId}">
                    <h5>Final bid: <span>${bidLst[0].amount} &euro;</span></h5>
                </c:if>
                <%-- the seller should be able to view all the bids and contact/rate the buyer --%>
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
        </div>
    </c:if>
    <%-- while the auction is running show data about bids --%>
    <c:if test="${not isEnded and auction.isActive == 1}">
        <div class="container">
            <c:if test="${not empty bidLst}">
                <%-- first show the latest bid --%>
                <c:if test="${user.userId == biddersLst[0].userId}">
                    <h5>Current bid (<span>${bidLst[0].amount} &euro;</span>) was placed by you.</h5>
                </c:if>
                <c:if test="${user.userId != biddersLst[0].userId}">
                    <h5>Current bid: <span>${bidLst[0].amount} &euro;</span></h5>
                </c:if>

                <%-- let registered users to bid --%>
                <c:if test="${not isSeller and not empty user.userId}">
                    <form action="/auction.do" method="post" class="js-confirm-bid">
                        <input type="number" step="any" min="${bidLst[0].amount +1}" value="${bidLst[0].amount +1}" name="bid">
                        <input type="hidden" name="aid" value="${auction.auctionId}">
                        <button class="button-primary" type="submit" name="action" id="bidAuction" value="bidAuction">Bid for this item</button>
                    </form>
                </c:if>
                <%-- the seller should only see the bidding history --%>
                <c:if test="${isSeller}">
                    <h5>All submitted bids:</h5>
                    <c:forEach var="bid" items="${bidLst}" varStatus="status">
                        <h6>${biddersLst[status.index].firstname} ${biddersLst[status.index].lastname} ${bid.amount}&euro; ${bid.bidTime}</h6>
                    </c:forEach>
                </c:if>
            </c:if>
            <c:if test="${empty bidLst}">

                <%-- registered users should be able to bid for the first time --%>
                <c:if test="${not isSeller and not empty user.userId}">
                    <h5>No bids placed yet.</h5>
                    <form action="/auction.do" method="post" class="js-confirm-bid">
                        <input type="number" step="any" min="${auction.lowestBid}" value="${auction.lowestBid}" name="bid">
                        <input type="hidden" name="aid" value="${auction.auctionId}">
                        <button class="button-primary" type="submit" name="action" value="bidAuction">Make the first bid</button>
                    </form>
                </c:if>
                <c:if test="${isSeller or empty user.userId}">
                    <h5>Auction has no bids yet.</h5>
                </c:if>
            </c:if>
        </div>
    </c:if>
</c:if>

<c:if test ="${empty auction}">
    <h3>The auction with id ${param.aid} does not exist!</h3>
</c:if>

<script src="../js/jquery.min.js" type="text/javascript"></script>
<script>
    $(document).ready(function() {
        window.inputActive = false;

        $('.js-toggle-date').on('click', function () {
            var c_date = $('.js-date');
            if ($(c_date).hasClass('js-opened')){
                $('.js-date').hide().removeClass('js-opened');
                $(this).text('activate  ');
            } else {
                $('.js-date').show().addClass('js-opened');
                $(this).text('cancel');
            }
        });

        $('.js-confirm-bid').submit(function(event) {
            var bidIt = confirm('Are you sure you want to bid?');
            if (bidIt){
                return true;
            } else{
                return false;
            }
        });

        $('.js-make-writable').on('click', function () {
            // stuff is editable
            if (window.inputActive) {
                $('input:not([readonly]), button').prop('disabled', true);
                window.inputActive = false;
            }
            else {
                $('[disabled]').prop('disabled', false);
                window.inputActive = true;
            }
            $('.js-categories').toggleClass('u-softhide');
        });
    });

    <c:if test="${not empty auction.latitude and not empty auction.longitude}">
    function initMap() {
        var myLatLng = {lat: ${auction.latitude}, lng: ${auction.longitude}};

        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 13,
            center: myLatLng
        });

        var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: 'Hello World!'
        });
    }
    </c:if>
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA7om9lzVVpATrE6I8ceaK9vMyE6Bi2KSw&callback=initMap&libraries=places" async defer></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.js"></script>
<script src="../js/scripts.js"></script>

</body>
</html>