<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View user's info</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
</head>
<body>
<c:if test="${not isAdmin}">
    <c:redirect url="/"/>
</c:if>
<c:if test ="${not empty user}">
    <a href="/user.do?action=getAllUsers">view all users</a>
    <jsp:useBean id="user" class="javauction.model.UserEntity" scope="request" />
    <form action="/user.do" method="post">
        <input type="hidden" value=${user.userId} name="uid">

        <c:if test="${user.isApproved == 0}">
            <input type=submit value=approveUser name="action">
        </c:if>
    </form>
    <c:if test="${user.isApproved == 1}">
        <h2>The user is approved</h2>
    </c:if>

    <dl>Username</dl>
    <dd>${user.username}</dd>
    <dl>Firstname</dl>
    <dd>${user.firstname}</dd>
    <dl>Lastname</dl>
    <dd>${user.lastname}</dd>
    <dl>Email</dl>
    <dd>${user.email}</dd>
    <dl>PhoneNumber</dl>
    <dd>${user.phoneNumber}</dd>
    <dl>VAT</dl>
    <dd>${user.vat}</dd>
    <dl>Home Address</dl>
    <dd>${user.homeAddress}</dd>
    <dl>Latitude</dl>
    <dd>${user.latitude}</dd>
    <dl>Longtitude</dl>
    <dd>${user.longitude}</dd>
    <dl>City</dl>
    <dd>${user.city}</dd>
    <dl>Country</dl>
    <dd>${user.country}</dd>
    <dl>Sign up date</dl>
    <dd>${user.signUpDate}</dd>
</c:if>
<c:if test ="${empty user}">
    <h1>The user with id ${param.uid} does not exist!</h1>
</c:if>


</body>
</html>
