<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Rating</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.css">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>
<c:if test="${empty user or user.userId == to_user.userId}">
    <c:redirect url="/"/>
</c:if>
<c:if test="${not empty param.aid}">
<!-- HEADER STUFF -->
<%@ include file="../header.jsp" %>

<div class="container">
    <!-- Rating -->
    <div class="row">
        <main class="u-full-width ">
            <div class="row u-full-width">
                <c:if test="${not empty rating}">
                    <form action="/rate.do" method="POST">
                        <h6>You have already rated ${to_user.firstname} ${to_user.lastname} with <span data-rating="${rating}" class="c-rate-write"></span></h6>
                        <input type="hidden" name="to_id" value=${to_id} />
                        <input type="hidden" name="aid" value=${aid} />
                        <input class="get-from-rateyo" type="hidden" min="0" max="5" value="${rating}" name="rating" required>
                        <button class="button-primary" type="submit" name="action" value="updateRating">Update rating</button>
                    </form>
                </c:if>
                <c:if test="${empty rating}">
                    <h6>You have not rated ${to_user.firstname} ${to_user.lastname} yet.</h6>
                    <form action="/rate.do" method="POST">
                        <input type="hidden" name="to_id" value=${to_id} />
                        <input type="hidden" name="aid" value=${aid} />
                        <span data-rating="0" class="c-rate-write"></span>
                        <input class="get-from-rateyo" type="hidden" min="0" max="5" value="0" name="rating" required>
                        <button class="button-primary" type="submit" name="action" value="addRating">Rate</button>
                    </form>
                </c:if>
            </div>
        </main>
    </div>
    <!-- end of rating row -->
    </c:if>
</div>
<script src="../js/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
