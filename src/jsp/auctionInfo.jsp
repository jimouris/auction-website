<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>
<%ResultSet resultset = null;%>

<%
if ((session.getAttribute("isUser") == null) || (session.getAttribute("isUser") == "")) {
    response.sendRedirect("./index.jsp");
  }
else{ // the user is not admin
  // page is called with parameteres
    if ( (request.getParameter("id") != null && request.getParameter("id") != "") ){
    try{
      
        // Open a connection
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();

        // assign post data to vars
        String auction_id = request.getParameter("id");

        // getting the aproprate user
        resultset = stmt.executeQuery("select * from auction where id = " + auction_id);
%>


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

        <!-- get auction data -->
        <% resultset.next();
            String title = resultset.getString("title");
            String city = resultset.getString("city");
            String country = resultset.getString("country");
            String description = resultset.getString("description");
            String startingprice = resultset.getString("startingPrice");
            String ends = resultset.getString("endDate");
            Boolean isActive = false;
            if (resultset.getBoolean("isActive")){
                isActive = true;
            }
        %> 

        <!-- print users data -->
        <div class="row">
            <table>
            
            <tbody>
                <tr>
                    <td>auction title:</td>
                    <td><%=title%></td>
                </tr>
                <tr>
                    <td>city:</td>
                    <td><%=city%></td>
                </tr>
                <tr>
                    <td>country:</td>
                    <td><%=country%></td>
                </tr>
                <tr>
                    <td>description:</td>
                    <td><%=description%></td>
                </tr>
                <tr>
                    <td>starting price:</td>
                    <td><%=startingprice%></td>
                </tr>
                <tr>
                    <td>auction ends on:</td>
                    <td><%=ends%></td>
                </tr>
                <tr>
                    <td>Category:</td>
                    <% 
                        out.println("<td> </td>"); 
                    %>
                </tr>
            </tbody>
            </table>

             <% if (!isActive){
                out.println("<h4 class=important>The auction is not active yet. Set an end date and active it.</h4>");
                out.println("<form class=row action=./editAuction method=GET> "
                                 + "<label>Set end date: <input name=date type=date id=js-endDate required/></label>"
                                 + "<input type=hidden name=auctionid value=" + auction_id + " />"
                                 + "<input type=hidden name=mode value=start />"
                                 + "<input class=button-primary type=submit value='Active it'>"
                                 + "</form>");
            }    else if(isActive && session.getAttribute("userId")!=resultset.getString("seller")) {
                    out.println("diffs");
                }
            %>

        </div>
    </div>
    <!-- end of container -->

    <%  
        //**clean up things up
        conn.close();
        stmt.close();
        resultset.close();
        
        //**Should I input the codes here?**
        } catch (Exception e) {
            out.println("wrong entry" + e);
        }
    } else {// get method has an id
        response.sendRedirect("./index.jsp");        
    }
} // the user is admin
%>

<script src="./js/jquery.js"></script>
<script src="./js/validate.js"></script>
<script>
    // set the arguments for GET call
    var auctionHref =  $("#js-activate").attr("href");
    $("#js-activate").attr("href", auctionHref + $("#js-endDate").val());
    $( "#js-endDate" ).change(function() {
        console.log($(this).val());
      $("#js-activate").attr("href", auctionHref + $(this).val());
    });
</script>
</body>
</html>
