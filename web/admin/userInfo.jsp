<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View user's info</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>
<c:if test="${not isAdmin}">
    <c:redirect url="/"/>
</c:if>
<%@ include file="../header.jsp" %>

<div class="container users">
    <c:if test ="${not empty user}">
        <jsp:useBean id="user" class="javauction.model.UserEntity" scope="request" />
        <form action="/user.do" method="post">
            <input type="hidden" value=${user.userId} name="uid">

            <c:if test="${user.isApproved == 0}">
                <input type=submit class="button-primary" value=approveUser name="action">
            </c:if>
        </form>
        <c:if test="${user.isApproved == 1}">
            <h3>The user is approved</h3>
        </c:if>

        <dl>Username: </dl>
        <dd>${user.username}</dd><br />
        <dl>Firstname: </dl>
        <dd>${user.firstname}</dd><br />
        <dl>Lastname: </dl>
        <dd>${user.lastname}</dd><br />
        <dl>Email: </dl>
        <dd>${user.email}</dd><br />
        <dl>PhoneNumber: </dl>
        <dd>${user.phoneNumber}</dd><br />
        <dl>VAT: </dl>
        <dd>${user.vat}</dd><br />
        <dl>Home Address: </dl>
        <dd>${user.homeAddress}</dd><br />
        <dl>Latitude: </dl>
        <dd>${user.latitude}</dd><br />
        <dl>Longtitude: </dl>
        <dd>${user.longitude}</dd><br />
        <dl>City: </dl>
        <dd>${user.city}</dd><br />
        <dl>Country: </dl>
        <dd>${user.country}</dd><br />
        <dl>Sign up date: </dl>
        <dd>${user.signUpDate}</dd><br />

    </c:if>
    <c:if test ="${empty user}">
        <h1>The user with id ${param.uid} does not exist!</h1>
    </c:if>
</div>

<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
1