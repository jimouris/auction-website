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
                <li class="u-full-width column"><a href="./logout.jsp?type=admin"><span class="delete">Logout</span></a></li>
            </ul>
        </div>
    </div>
    <!-- end of header row -->


    <!-- print users data -->
    <div class="row">
        <table class="columns six">

            <tbody>
            <tr>
                <td>auction title:</td>
            </tr>
            <tr>
                <td>city:</td>
            </tr>
            <tr>
                <td>country:</td>
            </tr>
            <tr>
                <td>description:</td>
            </tr>
            <tr>
                <td>starting price:</td>
            </tr>
            <tr>
                <td>auction ends on:</td>
            </tr>
            </tbody>
        </table>

        <h4 class=important>The auction is not active yet. Set an end date and active it.</h4>
        <form class=row action=./editAuction method=GET>
            <label>Set end date: <input name=date type=date id=js-endDate required/></label>
            <input type=hidden name=auctionid value=" + auction_id + " />
            <input type=hidden name=mode value=start />
            <input class=button-primary type=submit value='Active it'>
        </form>
    </div>
</div>
<!-- end of container -->
</body>
</html>
