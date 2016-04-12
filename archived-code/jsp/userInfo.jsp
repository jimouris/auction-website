<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>
<%ResultSet resultset = null;%>

<%
if ((session.getAttribute("isAdmin") == null) || (session.getAttribute("isAdmin") == "")) {
    response.sendRedirect("./index.jsp");
  }
  else{ // the user is admin
  // page is called with parameteres
    if ( (request.getParameter("id") != null && request.getParameter("id") != "") ){
    try{
      

        // Open a connection
        System.out.println("Connecting to a selected database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected database successfully...");
        stmt = conn.createStatement();

        // assign post data to vars
        String id = request.getParameter("id");

        // getting the aproprate user
        resultset = stmt.executeQuery("select * from account where id = " + id);
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
          </div>
          <div class="offset-by-nine one column">
            <ul class="nav u-full-width row">
              <li class="u-full-width column"><a href="./logout.jsp?type=admin"><span class="delete">Logout</span></a></li>
            </ul>
          </div>
        </div>
        <!-- end of header row -->

        <!-- get user's data -->
        <% resultset.next();
            String first = resultset.getString("firstname");
            String last = resultset.getString("lastname");
            String city = resultset.getString("city");
            String vat = resultset.getString("vat");
            String country = resultset.getString("country");
            String email = resultset.getString("email");
            Boolean toApprove = false;
            if (resultset.getString("approved").equals("0")){
                toApprove = true;
            }
        %> 

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
                    <td><%=first%></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><%=last%></td>
                </tr>
                <tr>
                    <td>City:</td>
                    <td><%=city%></td>
                </tr>
                <tr>
                    <td>Country:</td>
                    <td><%=country%></td>
                </tr>
                <tr>
                    <td>VAT:</td>
                    <td><%=vat%></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><%=email%></td>
                </tr>
            </tbody>
            </table>
            <% if (toApprove){
                out.println("<form action=\"executeApprove\" method=\"post\">\n "
                + "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"> \n"
                + "<input type=\"submit\" class=\"button-primary\" value=\"Approve\">\n "
                + "</form>");
            }%>

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

</body>
</html>
