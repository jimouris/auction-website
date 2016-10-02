<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>List auctions for export</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">

    <jsp:useBean id="auctionsLst" class="java.util.ArrayList" scope="request" />
</head>
<body>
<c:if test="${not isAdmin}">
    <c:redirect url="/"/>
</c:if>
<%@ include file="../header.jsp" %>

<c:if test="${isAdmin}">
    <div class="container">
        <form action="/auction.do" method="POST">
            <input type="hidden" name="action" value="getAuctionAsXML">
            Be patient and export every auction: <button class="button">export all</button>
        </form>
        <hr />
        <form action="/auction.do" method="POST" id="export">
            <input type="hidden" name="action" value="getAuctionsAsXML">
            <input type="hidden" name="exportSelected" value="">
            Select auctions and export: <button type="submit" class="button button-primary" disabled>export selected</button><br />
            <b><span data-bind="selectedAuctions"></span> selected auctions</b>

            <div class="row">
                <p class="three columns offset-by-one">Auction name</p>
                <p class="eight columns">Description</p>
            </div>
            <c:forEach var="auction" items="${auctionsLst}">
                <label class="row light zebra-hover" for="${auction.auctionId}">
                    <div class="one column u-text-center"><input type="checkbox" name="auctionIds" value="${auction.auctionId}" id="${auction.auctionId}"></div>
                    <div class="three columns">${auction.name}</div>
                    <div class="eight columns">${fn:replace(auction.description, fn:substring(auction.description, 40, fn:length(auction.description)), '...')}</div>
                </label>
            </c:forEach>
            <c:if test="${auctionsLst.size() == 0}">
                <span>No auctions found.</span>
            </c:if>
        </form>
        <c:if test="${auctionsLst.size() != 0}">

            <div class="row">
                <c:if test="${empty previousPage}">
                    <span class="u-unvailable">previous page</span> |
                </c:if>
                <c:if test="${not empty previousPage}">
                    <a href="${previousPage}">previous page</a> |
                </c:if>
                <a href="${nextPage}">next page</a>
            </div>
        </c:if>
        <c:if test="${auctionsLst.size() == 0 and not empty previousPage}">
            <div class="row">
                Last page. Please go to <a href="${previousPage}">previous page</a>
            </div>
        </c:if>
        </section>

    </div>
</c:if>
<script src="../js/jquery.min.js"></script>
<script src="../js/cookie.js"></script>
<script src="../js/scripts.js"></script>
<script>

    // this hold the name of the cookie that we store the array of ids
    var auctions = "auction_ids";

    setSelectedFields(auctions);
    checkForSelected(auctions);

    $('[data-bind="selectedAuctions"]').text(getArray(auctions).length);

    $('form').delegate('input', 'change', function(){
        var self_input = $(this);
        auctionId = $(self_input).val();

        if ($(self_input)[0].checked)
            addToArrayCookie(auctions, auctionId, checkForSelected);
        else
            removeFromArrayCookie(auctions, auctionId, checkForSelected);
        $('[data-bind="selectedAuctions"]').text(getArray(auctions).length);
    });

    $('#export').submit(function() {
        var arr = getArray(auctions);
        arr.forEach(function(aid){
            var ele = '[value="' + aid + '"]';
            if ($(ele).length == 0){
                $('form').append('<input type="hidden" name="auctionIds" value =' + aid +'>');
            }
        });
        deleteAll(auctions);
    });

    $('.js-clearAll').on('click', function () {
        deleteAll(auctions);
    });


</script>
</body>
</html>
