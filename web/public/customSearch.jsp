<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Advanced Search</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
</head>
<body>
<!-- HEADER STUFF -->
<%@ include file="../header.jsp" %>

<!-- end of header row -->
<div class="container">
    <h1>Advanced Search</h1>
    <form action="/search.do" method="POST" id="advanced_search">
        <div class="row">
            <div class="four columns">
                <label for="categories">Multi select categories (use ctrl + click)</label>
                <jsp:useBean id="categoriesLst" class="java.util.ArrayList" scope="request" />
                <select class="a-select--multiple" id="categories" name="categories" multiple size=${categoriesLst.size()}>
                    <c:forEach var="category" items="${categoriesLst}">
                        <option value=${category.categoryId}>${category.categoryName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="eight columns">
                <label>Description</label>
                <textarea class="u-full-width" placeholder="e.g. a high quality sa..." name="description"></textarea>
            </div>
        </div>

        <div class="row">
            <div class="eight columns">
                <label>Location</label>
                <input class="u-full-width" type="search" name="location" placeholder="e.g. Athens">
            </div>

            <div class="two columns">
                <label>Minimum Price</label>
                <input class="u-full-width" type="number" min=0 name="minPrice" placeholder="0">
            </div>

            <div class="two columns">
                <label>Maximum Price</label>
                <input class="u-full-width" type="number" min=1 name="maxPrice" placeholder="100000">
            </div>

        </div>

        <button class="button-primary" type="submit" name="action" value="searchAuctions">Search</button>
    </form>
</div>


<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
