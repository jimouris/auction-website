<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" rel="stylesheet">   
</head>
<body>
<div class="container">
    <a href="/">home</a>

    <h1>List users</h1>
    <jsp:useBean id="userLst" class="java.util.ArrayList" scope="request" />

    <%--<%--%>
        <%--List userLst = (List) request.getAttribute("userLst");--%>
        <%--out.print(userLst.get(0));--%>
    <%--%>--%>
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
</body>
</html>
