<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administrator login page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>

<c:if test="${isAdmin}">
    <div class="container">
        <h3>List users</h3>
        <jsp:useBean id="userLst" class="java.util.ArrayList" scope="request" />
        <table>
            <c:forEach var="user" items="${userLst}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.firstname}</td>
                    <td>${user.isApproved}</td>
                    <td><a href=/user.do?action=getAUser&uid=${user.userId}>more info</a></td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${userLst.size() != 0}">
            <div class="row">
                <%@ include file="../paginationLinks.jsp" %>
            </div>
        </c:if>
        <c:if test="${userLst.size() == 0 and not empty previousPage}">
            <div class="row">
                Last page. Please go to <a href="${previousPage}">previous page</a>
            </div>
        </c:if>
    </div>
</c:if>
<c:if test="${not isAdmin}">
    <c:redirect url="/"/>
</c:if>

<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
