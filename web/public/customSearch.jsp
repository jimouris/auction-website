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
<c:if test="${not empty user.userId}">
    <a href="/user/homepage.jsp">Homepage</a>
    <a href="/auction.do?action=getAllAuctions">View All Auctions</a>
</c:if>
<c:if test="${empty user.userId}">
    <a href="/public/">Guest, Homepage</a>
</c:if>
<!-- end of header row -->
<h1>Advanced Search</h1>
<p>
<form action="/search.do" method="POST" id="advanced_search">
    <label for="categories">Select at least one category:</label>
    <jsp:useBean id="categoriesLst" class="java.util.ArrayList" scope="request" />
    <select class="a-select--multiple" id="categories" name="categories" multiple size=${categoriesLst.size()}>
        <c:forEach var="category" items="${categoriesLst}">
            <option value=${category.categoryId}>${category.categoryName}</option>
        </c:forEach>
    </select>
    <br>

    <label>Description</label>
    <textarea class="u-full-width" placeholder="e.g. a high quality sa..." name="description"></textarea>
    <br>

    <label>Minimum Price</label>
    <input class="u-full-width" type="number" min=0 name="minPrice" placeholder="0">
    <br>

    <label>Maximum Price</label>
    <input class="u-full-width" type="number" min=1 name="maxPrice" placeholder="100000">
    <br>

    <label>Location</label>
    <input class="u-full-width" type="search" name="location" placeholder="e.g. Athens">
    <br>

    <button class="button-primary" type="submit" name="action" value="searchAuctions">Search</button>
</form>

</p>

</body>
</html>
