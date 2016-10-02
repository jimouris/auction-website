<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <!DOCTYPE html> -->
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
<c:if test="${not empty successMsg}">
    <p class="container status--success">${successMsg}</p>
</c:if>
<c:if test="${isAdmin}">
    <div class="container">
        <div class="row">
            <div class="five columns">
                <h3><span class="icon-user"></span>View users</h3>
                <p class="small-p">
                    Here you can view information about all registered users. Also from here you are able to approve them.<br />
                    <a href="/user.do?action=getAllUsers" class="button button-primary" style="margin-top: 5px;">lists users</a>
                </p>
            </div>
            <div class="six offset-by-one columns">
                <h3><span class="icon-export"></span>View auctions</h3>
                <p class="small-p">
                    From here you get a small information preview for each auction. You can also select them to export them as XML.<br />
                    <a href="/search.do?action=auctionsForExport" class="button button-primary" style="margin-top: 5px;">view auctions</a>
                </p>
            </div>
        </div>
    </div>
    <br/>
    <div class="container">
        <div class="row">
            <div class="eleven columns">
                <h3><span class="icon-upload"></span>Import auctions from XML</h3>
                <div style="margin-left: 55px">
                    From here you can import auctions from XML file.<br />
                    <form action="/auction.do" method="get">
                        <input type="hidden" name="action" value="setFromXML">
                        <div class="row">
                            <div class="two columns">
                                <label>Select first file:</label>
                                <input type="number" name="firstFile" value="0" min="0" max="39">
                            </div>
                            <div class="two columns">
                                <label>Select last file:</label>
                                <input type="number" name="lastFile" value="1" min="1" max="40">
                            </div>
                            <div class="three columns">
                                <label>&nbsp;</label>
                                <button type="submit" class="button button-primary" style="margin-top: 5px;" onclick="return confirmImport()">import auctions</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
<script>
    function confirmImport() {
        return confirm("Are you sure? This may take some time.");
    }
</script>
</body>
</html>
