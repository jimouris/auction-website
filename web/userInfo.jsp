<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        </div>
        <div class="offset-by-nine one column">
            <ul class="nav u-full-width row">
                <li class="u-full-width column"><a href=""><span class="delete">Logout</span></a></li>
            </ul>
        </div>
    </div>
    <!-- end of header row -->



    <!-- print user's data -->
    <div class="row">
        <table>
            <thead>
            <tr>
                <th>Χαρακτηριστικό</th>
                <th>Τιμή</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>First Name</td>
            </tr>
            <tr>
                <td>Last Name:</td>
            </tr>
            <tr>
                <td>City:</td>
            </tr>
            <tr>
                <td>Country:</td>
            </tr>
            <tr>
                <td>VAT:</td>
            </tr>
            <tr>
                <td>Email:</td>
            </tr>
            </tbody>
        </table>
        <form action="executeApprove" method="post">
            <input type=hidden name=id value=>
            <input type=submit class=button-primary value=Approve>
        </form>
    </div>
</div>
<!-- end of container -->
</body>
</html>
