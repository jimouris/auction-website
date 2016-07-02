<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
         <!-- <%     
                Boolean retrieveData = (Boolean)request.getAttribute("retrieveData");
                String msg = (String)request.getAttribute("regStatus");
                if(!retrieveData && msg != null){
                    out.print(msg);
                }

            %> -->
            <!-- id = request.setAttribute("id", whateva);
            class = well the class
            scope = where should I look? -->
            <jsp:useBean id="userList" class="java.util.ArrayList" scope="request" />

        <table>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.name}</td>
                        <td>${user.lastname}</td>
                        <td><a href="viewInfo.jsp"">more</a></td>
                    </tr>
                </c:forEach>
        </table>
</div>
</body>
</html>
