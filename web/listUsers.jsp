<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>
</head>
<body>
<div class="container">
        <h1>List users</h1>

    <!-- id = request.setAttribute("id", whateva);
    class = well the class
    scope = where should I look? -->

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
                <td><a href=getAUser.do?uid=${user.userId}>more info</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
