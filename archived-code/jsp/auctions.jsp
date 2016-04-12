<%-- 
    Document   : admin
    Created on : May 10, 2015, 2:09:49 PM
    Author     : hostAdmin
--%>

<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>

<%ResultSet resultset = null;%>

<%
conn = DriverManager.getConnection(DB_URL, USER, PASS);
stmt = conn.createStatement();

if ((session.getAttribute("isAdmin") != null) && (session.getAttribute("isAdmin") != "")) { // the user is admin
     resultset = stmt.executeQuery("SELECT * FROM auction");
} else if((session.getAttribute("isUser") != null) && (session.getAttribute("isUser") != "")) { // the user is a regular one
    Integer s = (Integer)session.getAttribute("userId");

    String str= "";
    String p = new String(""); 
    if(request.getParameter("p") == null)
    {
        str = "SELECT * FROM auction WHERE seller=" + s;
    }
    else // the page is called with p parameter. If it's value is all, then perfect!
    { 
        p = request.getParameter("p");
        if(p.equals("all"))
          str = "SELECT * FROM auction where isActive=1";
    else
    {
        str = "SELECT * FROM auction WHERE isActive = 1";
    }
   
    out.println(str);
    resultset = stmt.executeQuery(str); 
} else { // this is not a user
  response.sendRedirect("./index.jsp");
}

    try {
       // Open a connection
      
    %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
        <title>Your auctions</title>

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
          </div>
          <div class="offset-by-nine one column">
            <ul class="nav u-full-width row">
              <li class="u-full-width column">
                  <%
                     if ((session.getAttribute("isUser") != null) && (session.getAttribute("isUser") != "")) {
                     out.println("<a href='./logout.jsp?type=regular'>");
                     }
                     else if ((session.getAttribute("isAdmin") != null) && (session.getAttribute("isAdmin") != "")) 
                     {
                          out.println("<a href='./logout.jsp?type=admin'>");      
                     }
                  %>      
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
                <th>Title</th>
                <th>details</th>
                <th>profile</th>
              </tr>
            </thead>
            <% while (resultset.next()) {%>
              <tr>
                <td><%=resultset.getString("title")%></td>
                 <td><a href=./auction.jsp?id=<%=resultset.getString("id")%> class="button">View</a></td>
                 <td><a href=./editAuction.jsp?id=<%=resultset.getString("id")%> class="button">Edit</a></td>

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
               
             
              %>

          </table>
        </div>
    </div>
    <!-- end of container -->
    </body>
</html>
