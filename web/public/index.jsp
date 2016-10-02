<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>DI Ted Project</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
    <style>
        h4.u-center-text.button {
            position: relative;
            top: -10px;
            background: white;
            box-shadow: 0 -4px 47px -5px #eee;
            border: none;
            border-top: 1px solid #333;
            border-radius: 0;
            margin-top:0;
        }


        @-webkit-keyframes moveDown{
            from{ top: -10px;}
            to{ top: -2px;}
        }


        h4.u-center-text.button:hover{
            -webkit-animation: moveDown 500ms;
            top: -2px;
        }

    </style>
</head>
<body>

<%@ include file="../header.jsp" %>

<!-- end of header row -->
<c:if test="${not empty successMsg}">
    <p class="container status--success">${successMsg}</p>
</c:if>

<div class="container">
    <div class="row">
        <h3><span class="icon-search"></span> Search for a product</h3>
        <form class="row" action="/search.do" method="POST">
            <input class="seven columns" type="text" name="name">
            <button class="two columns button-primary" type="submit" name="action" value="searchAuctions">Search</button>
            <a class="two columns advanced" href="/search.do?action=advancedSearch">Advanced</a>
        </form>
    </div>
    <!-- REGISTER && SIGNUP ROW -->
    <div class="row">
        <%-- register --%>
        <div class="seven columns">
            <h3><span class="icon-user"></span>Register at our eshop</h3>
            <p class="small-p">
                In order to start buying or selling items on auction-website, you have to create an account.<br />
                <a class="button button-primary" href="/public/register.jsp" style="margin-top: 5px;">register</a>
            </p>
        </div>
        <!-- end of register column -->
        <%-- login --%>
        <div class="three columns">
            <h3><span class=""></span>Login</h3>
            <section class="login u-full-width">
                <form action="/login.do" method="POST" id="loginForm">
                    <label> username </label>
                    <input class="u-full-width" type="text" id="username" name="username"  autofocus>
                    <label for="password">password</label>
                    <input class="u-full-width" type="password" id="password" name="password" required>
                    <input type="hidden" name="action" value="user">
                    <c:if test="${not empty errorMsg}">
                        <p class="status--error">${errorMsg}</p>
                    </c:if>
                    <input class="button-primary u-pull-left" type="submit" value="Login">
                </form>
            </section>
        </div>
        <!-- end of login column -->
    </div>
</div>

<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
