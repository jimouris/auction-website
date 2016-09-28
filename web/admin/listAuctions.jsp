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
<c:if test="${isAdmin}">
    <div class="container">
        <section>
            Be patient and export every auction: <a href="/auction.do?action=getAuctionsAsXML"  target="_blank" class="button">export all</a>
            <hr />
            <form action="/auction.do" method="GET">
                <input type="hidden" name="action" value="getAuctionsAsXML">
                <input type="hidden" name="exportSelected" value="">
                Select auctions and export: <button type="submit" class="button button-primary" disabled>export selected</button>

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
        </section>

    </div>
</c:if>
<script src="../js/jquery.min.js"></script>
<script src="../js/cookie.js"></script>
<script>
    var checkForSelected = function(){
        // check if selected values
        var hasSelected = false;
        $('input[name="auctionIds"]').each(
                function() {
                    if ($(this)[0].checked) {
                        hasSelected = true;
                        return false;
                    }
                });
        if (hasSelected)
            $('.button-primary').prop('disabled', false);
        else
            $('.button-primary').prop('disabled', true);
    };

    checkForSelected();

    $('form').delegate('label', 'click', function(){
      checkForSelected()
    });
</script>
</body>
</html>
