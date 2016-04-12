<%--
    Document   : admin
    Created on : May 10, 2015, 2:09:49 PM
    Author     : hostAdmin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Administrator login page</title>

    <link href="./css/skeleton.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <!-- HEADER STUFF -->
    <div class="row">
        <div class="two columns">
            <a href="./admin.jsp">
                <img class="three columns" src="./images/logo.png"><span class="nine columns">Home</span>
            </a>
            <a href="./auctions.jsp">
                <span class="nine columns">Home</span>
            </a>
            <a href="./auctionsxml.jsp">
                <span class="nine columns">XML</span>
            </a>
        </div>
        <div class="offset-by-nine one column">
            <ul class="nav u-full-width row">
                <li class="u-full-width column">
                    <a href="">
                        <span class="delete">Logout</span></li>
                </a>
            </ul>
        </div>
    </div>
    <!-- end of header row -->


    <div class="row">
        <table class="u-full-width">
            <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>status</th>
                <th>profile</th>
            </tr>
            </thead>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>
                </td>
                <td><a href=> class="button">View</a></td>
            </tr>


        </table>
    </div>
</div>
<!-- end of container -->
</body>
</html>
