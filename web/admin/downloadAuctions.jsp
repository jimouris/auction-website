<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Administrator page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">

</head>
<body>
<c:if test="${not isAdmin}">
    <c:redirect url="/"/>
</c:if>
<%@ include file="../header.jsp"%>
<c:if test="${isAdmin}">
    <div class="container">
        <div class="row">
            <div class="five columns">
                <h3><span class="icon-user"></span>Export completed</h3>
                <p class="small-p">
                    In order to view the exported auctions click the button bellow to download them as XML.<br />
                    <a href="${xmlLink}" download="auctions.xml" class="button button-primary" style="margin-top: 5px;">download the xml</a>
                    <a href="${xmlLink}" target="_blank" class="button" style="margin-top: 5px;">view the xml</a>
                </p>
            </div>
        </div>
    </div>
</c:if>


<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
