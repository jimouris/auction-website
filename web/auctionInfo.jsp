<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>

    <link href="./css/skeleton.css" rel="stylesheet">
    <link href="./css/custom.css" rel="stylesheet">
</head>
<body>
    <!-- HEADER STUFF -->
    <a href="./homepage.jsp">Homepage</a>
    <a href="auction.do?action=getAllAuctions">View All Auctions</a>
    <!-- end of header row -->

    <c:if test ="${not empty auction}">
    <jsp:useBean id="auction" class="javauction.model.AuctionEntity" scope="request" />

    <div class="custom-container">

        <form action="auction.do" method="post">
            <input type="hidden" value=${auction.auctionId} name="aid">
            <c:if test="${auction.isStarted == 0}">
                <input type=submit value=activateAuction name="action">
            </c:if>
        </form>
        <c:if test="${auction.isStarted == 1}">
            <h2>The auction is started-active</h2>
        </c:if>

        <c:if test="${isSeller}">
            <a class="js-make-writable button">edit some fields</a>
        </c:if>

        <form action="auction.do" method="POST" id="view_updateAuction">
            <div class="row">
                <div class="one-half column">
                    <label>Name</label>
                    <input class="u-full-width" type="text" name="name" minlength="2" required disabled value="${auction.name}">

                    <%--<label for="categories">Categories selected:</label>--%>
                    <%--<jsp:useBean id="categoriesLst" class="java.util.ArrayList" scope="request" />--%>
                    <%--<select class="a-select--multiple" id="categories" name="categories" multiple size=${categoriesLst.size()}>--%>
                    <%--<c:forEach var="category" items="${categoriesLst}">--%>
                    <%--<option value=${category.categoryId}>${category.categoryName}</option>--%>
                    <%--</c:forEach>--%>
                    <%--</select>--%>
                    <%--<br>--%>

                    <label>Description</label>
                    <input class="u-full-width" type="text" name="description" minlength="2" disabled required autofocus value="${auction.description}">

                    <label>Lowest bid</label>
                    <input class="u-full-width" type="number" name="lowestBid" minlength="1" disabled required value="${auction.lowestBid}">

                    <label>Current bid</label>
                    <input class="u-full-width" type="number" name="currentBid" minlength="1" disabled readonly value="${auction.currentBid}">

                    <label>Final price (after bidding)</label>
                    <input class="u-full-width" type="number" name="finalPrice" minlength="1" disabled readonly value="${auction.finalPrice}">

                    <label>Buy price (Instant buy)</label>
                    <input class="u-full-width" type="number" name="buyPrice" minlength="1" disabled required value="${auction.buyPrice}">
                </div>

                <div class="one-half column">
                    <label>City</label>
                    <input class="u-full-width" type="text" name="city" minlength="2" required disabled value="${auction.city}">

                    <label>The country where is the auction item.</label>
                    <input class="u-full-width" type="text" name="country" minlength="2" required disabled value="${auction.country}">

                    <label>Starting date</label>
                    <input class="u-full-width" type="date" name="startingDate" required disabled  value="${auction.startingDate}">

                    <label>Ending date</label>
                    <input class="u-full-width" type="date" name="endingDate" required disabled value="${auction.endingDate}">

                    <input type="hidden" name="aid" value="${auction.auctionId}">
                </div>
            </div>
            <button class="button-primary" type="submit" name="action" value="updateAuction" disabled>Edit/Update</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-2.2.4.min.js" type="text/javascript"></script>
    <script>
        window.inputActive = false;

        $('.js-make-writable').on('click', function(){
            if (window.inputActive){
                $('input:not([readonly]), button').prop('disabled', true);
                window.inputActive = false;
            }
            else{
                $('[disabled]').prop('disabled', false);
                window.inputActive = true;
            }
        });
    </script>
</c:if>
<c:if test ="${empty auction}">
    <h3>The auction with id ${param.aid} does not exist!</h3>
</c:if>

</body>
</html>
