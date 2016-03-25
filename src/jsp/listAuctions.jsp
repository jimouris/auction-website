<%-- 
    Document   : admin
    Created on : May 10, 2015, 2:09:49 PM
    Author     : hostAdmin
--%>

<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>

<%ResultSet resultset = null;%>

<%
// the user has not logged in yet
if ((session.getAttribute("isUser") == null) || (session.getAttribute("isUser") == "")) {
    response.sendRedirect("./index.jsp");
  }
  %>
<%
    try {
       
        // Open a connection
        System.out.println("Connecting to a tedproject database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected to tedporject successfully...");
        stmt = conn.createStatement();

        // list auctions of the current user
        resultset = stmt.executeQuery("select * from item where seller=" + session.getAttribute("isUser") + " order by started ASC"); %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
        <title>View your auctions page</title>

        <link href="./css/skeleton.css" rel="stylesheet">
        <script src="./js/jquery.js"></script>
        <!-- <script src="./js/validate.js"></script> -->
    </head>
    <body>
    <div class="container">
        <!-- HEADER STUFF -->
        <div class="row">
          <div class="two columns">
            <a href="./welcome.jsp">
              <img class="three columns" src="./images/logo.png"><span class="nine columns">Home</span>
            </a>
          </div>
          <div class="offset-by-nine one column">
            <ul class="nav u-full-width row">
              <li class="u-full-width column">
              <a href="./logout.jsp?type=regular">
                <span class="delete">Logout</span>
              </a>
              </li>
            </ul>
          </div>
        </div>
        <!-- end of header row -->

        <div class="row">
          <table class="u-full-width">
            <thead>
              <tr>
                <th class="table four">auction name</th>
                <th class="table two">bidded</th>
                <th class="table two">status</th>
                <th class="table two">edit</th>
                <th class="table two">delete</th>
              </tr>
            </thead>
            <% while (resultset.next()) { %>
              <tr>
                <!-- the name of auction -->
                <td><%=resultset.getString("name")%></td>
                 <!-- check if the auction is bidded -->
                <td> <% 
                  if (resultset.getBoolean("bidded"))
                    out.println("<span class='important-text'>Bidded</span>");
                  else
                    out.println("Not bidded yet");
                %> </td>
                 <!-- check the status of auction -->
                <td> <%
                  if(resultset.getTimestamp("started") == null)
                    out.println("<a href=./editAuction?auctionid=" + resultset.getString("id") + "&mode=start class=\"button button-primary\">Start it</a>");
                  else if (resultset.getTimestamp("started") != null && resultset.getTimestamp("ends") == null)
                    out.println("<span class=important-text>Running</span>");
                else
                  out.println("Ended");
                %> </td>
                 <!-- edit the auction -->
                <td> <%
                  if(resultset.getTimestamp("started") == null || !(resultset.getBoolean("bidded")))
                    out.println("<a href=./editAuction.jsp?auctionid=" + resultset.getString("id") + " class=\"button\">Edit</a>");
                  else
                    out.println("<p class='unvailable'>cann't edit</p>");  
                %> </td>
                 <!-- delete the auction -->
                <td><a href=./editAuction?auctionid=<%=resultset.getString("id")%>&mode=delete class="delete u-full-width">delete</a></td>
              </tr>
              <% } %>

              <%  //**clean up things
                      conn.close();
                      stmt.close();
                      resultset.close();

                      //**Should I input the codes here?**
                  } catch (Exception e) {
                      out.println("wrong entry" + e);
              } // end of while for resultset             
              %>

          </table>
        </div>
    </div>
    <!-- end of container -->
    </body>
</html>
