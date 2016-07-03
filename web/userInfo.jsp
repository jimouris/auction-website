<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View user's info</title>

</head>
<body>

    <jsp:useBean id="user" class="javauction.model.UserEntity" scope="request" />
    <form action="approveUser.do" method="post">
        <input type="hidden" value=${user.userId} name="uid">
        <input type=submit value=Approve>
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
    </form>
</body>
</html>
