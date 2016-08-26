<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">
</head>
<body>
<c:if test="${isAdmin}">
    <div class="container">
        <a href="/admin/homepage.jsp">home</a>

        <h1>List users</h1>
        <jsp:useBean id="userLst" class="java.util.ArrayList" scope="request" />

        <table>
            <c:forEach var="user" items="${userLst}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.firstname}</td>
                    <td>${user.isApproved}</td>
                    <td><a href=user.do?action=getAUser&uid=${user.userId}>more info</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>
<c:if test="${not isAdmin}">
    <c:redirect url="/"/>
</c:if>

</body>
</html>
