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
<div class="container">
    <!-- HEADER STUFF -->
    <a href="/public/">Homepage</a>
    <!-- end of header row -->
    <c:if test="${not empty successMsg}">
        <p class="status--success">${successMsg}</p>
    </c:if>


    <!-- REGISTER && SIGNUP ROW -->
    <div class="row">
        <%-- register --%>
        <div class="two-thirds column">
            <h2><span class="look">> </span>Register at our eshop</h2>
            <a class="button button-primary" href="/public/register.jsp">register</a>
        </div>
        <!-- end of register column -->
        <%-- login --%>
        <div class="one-third column">
            <section class="login u-full-width">
                <h2><span class="look">> </span>Login</h2>
                <form action="/login.do" method="POST" id="loginForm">
                    <label> username </label>
                    <input class="u-full-width" type="text" id="username" name="username"  autofocus>
                    <label for="password">password</label>
                    <input class="u-full-width" type="password" id="password" name="password" required>
                    <input type="hidden" name="action" value="user">
                    <c:if test="${not empty errorMsg}">
                        <p class="status--error">${errorMsg}</p>
                    </c:if>
                    <input class="button-primary u-pull-right" type="submit" value="Login">
                </form>
            </section>
        </div>
        <!-- end of login column -->
    </div>

    <!-- SEARCH ROW -->
    <div class="row">
        <section class="search seven columns">
            <h4><span class="look">> </span>Search for a product</h4>
            <form action="/search.do" method="POST">
                <input class="u-full-width" type="text" name="name">
                <button class="button-primary" type="submit" name="action" value="searchAuctions">Search</button>
            </form>
            <h5><span class="look">> </span>Or perform an advanced search</h5>
            <a class="button" href="/search.do?action=advancedSearch">Advanced Search</a>
        </section>
    </div>
    <!-- end of search row -->

</div>
</body>
</html>
