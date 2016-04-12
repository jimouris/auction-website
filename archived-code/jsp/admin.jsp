<%-- 
    Document   : admin
    Created on : May 10, 2015, 2:09:49 PM
    Author     : hostAdmin
--%>

<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>

<%ResultSet resultset = null;%>

<%
if ((session.getAttribute("isAdmin") == null) || (session.getAttribute("isAdmin") == "")) {
    response.sendRedirect("./index.jsp");
  }
  else{ // the user is admin
    try {
       // Open a connection
        System.out.println("Connecting to a selected database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected database successfully...");   
        stmt = conn.createStatement();

        resultset = stmt.executeQuery("select firstname, lastname, id, approved,email from account order by approved ASC"); %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
        <title>Administrator login page</title>

        <link href="./css/skeleton.css" rel="stylesheet">
        <script src="./js/jquery.js"></script>
        <script src="./js/validate.js"></script>
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
              <a href="./logout.jsp?type=admin">
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
            <% while (resultset.next()) {%>
              <tr>
                <td><%=resultset.getString("firstname")%></td>
                 <td><%=resultset.getString("lastname")%> </td>
                 <td><%=resultset.getString("email")%> </td>
                <td>
                <% if (resultset.getString("approved").equals("0")){
                  out.println("<span class='important-text'>needs approvement</span>");
                } else{
                  out.println("approved");
                }
                %></td>
                <td><a href=./userInfo.jsp?id=<%=resultset.getString("id")%> class="button">View</a></td>
              </tr>
              <% } %>

              <%  //**clean up things
                      conn.close();
                      stmt.close();
                      resultset.close();

                      //**Should I input the codes here?**
                  } catch (Exception e) {
                      out.println("wrong entry" + e);
                  }
                } // the user is admin
             
              %>

          </table>
        </div>
    </div>
    <!-- end of container -->
    </body>
</html>
