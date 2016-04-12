<%-- 
    Document   : admin
    Created on : May 10, 2015, 2:09:49 PM
    Author     : hostAdmin
--%>

<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>

<%ResultSet resultset = null;
ResultSet rs = null;%>

<%
conn = DriverManager.getConnection(DB_URL, USER, PASS);
stmt = conn.createStatement();

// assign post data to vars
String txt = request.getParameter("searchTetx");
String description = request.getParameter("searchDescription");
String country = request.getParameter("searchLocation");
String priceStr = request.getParameter("searchPrice");
Integer category = null;
if(request.getParameter("searchCategory") != null)
  category = Integer.parseInt(request.getParameter("searchCategory"));
Integer price;

// start creating the query
String query = "select * from auction inner join categories on id=auction where isActive=1";

// to build the query start checking each variable
// if it has been passed to the page
if (txt != null && !txt.equals(""))
  query =  query.concat(" and title like '%" + txt + "%'");
if (description != null && !description.equals(""))
  query = query.concat(" and description like '%" + description + "%'");
if (country != null && !country.equals(""))
  query = query.concat(" and country like '%" + country + "%'");
if (category != null && !category.equals(""))
  query = query.concat(" and categories.category=" + category);
query = query.concat(" group by id");

out.println(query);


if ((session.getAttribute("isAdmin") != null) && (session.getAttribute("isAdmin") != "")) { // the user is admin
     response.sendRedirect("./index.jsp");
} else if((session.getAttribute("isUser") != null) && (session.getAttribute("isUser") != "")) { // the user is a regular one
    resultset = stmt.executeQuery(query);
} else { // this is not a user
    resultset = stmt.executeQuery(query);
}

    try {
       // Open a connection
      
    %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
        <title>search results</title>

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

        <!-- SEARCH ROW -->
        <div class="row">
          <section class="search">
            <h1>Search for a product</h1>
            <form class="row" action="./search.jsp" method="POST" id="search">
              <div class="nine columns">
                <input class="u-full-width" type="text" name="searchTetx">
              </div>
              <div class="three columns">
                <input class="u-full-width button-primary" type="submit" value="Search">
              </div>
            </form>
            <h4>Use custom criteria?</h4>
            <label class="disableCustomCriteria one-half column">
              <input type="radio" name="criteria" value="disable" onclick="document.getElementById('customCriteria').setAttribute('style','visibility: hidden; opacity: 0; height: 0')" checked>
              <span class="label-body">No, I want just a simple search.</span>
            </label>
            <label class="enableCustomCriteria one-half column">
              <input type="radio" name="criteria" value="enable" onclick="document.getElementById('customCriteria').setAttribute('style','visibility: visible; opacity: 1; height: auto;')">
              <span class="label-body">Yea! I love custom advanced search.</span>
            </label>
            <div class="row hidden u-full-width" id="customCriteria">
              <div class="one-third column">
                <label for="searchCategory">Category</label>
                <select class="u-full-width" form="search" list="datalistCategories" name="searchCategory" id="searchCategory">
                <%  
                  stmt = conn.createStatement();
                  rs = stmt.executeQuery("select * from category order by name");
                  while (rs.next()) {
                    out.println("<option value=" + rs.getString(1) + ">" + rs.getString(2) + "</option>" );
                 } %>
                </select>
              </div>
              <div class="one-third column">
                <label for="searchPrice">max price</label>
                <input class="u-full-width" form="search" type="number" min=0 max=10000 name="searchPriceInput" id="searchPriceInput">
              </div>
              <div class="one-third column">
                <label for="searchLocation">Location</label>
                <select class="u-full-width" form="search" type="search" name="searchLocation" id="searchLocation">
                  <%  
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("select * from country");
                    while (rs.next()) {
                      out.println("<option>" + rs.getString(3) + "</option>" );
                    } 
                  %>
                <select>
              </div>
              <div class="u-cf"></div>
              <div class="u-full-width">
                <label for="searchDescription">Description</label>
                <textarea form="search" class="u-full-width" placeholder="a high quality sa..." name="searchDescription" id="searchDescription"></textarea>
              </div>
            </div>
            <!-- end of custom criteria row -->
          </section>
        </div>
        <!-- end of search row -->

        
        <div class="row">
          <table class="u-full-width">
          <thead>
            <th class="nine tabl">Product Name</th>
            <th class="one tabl">Bid at</th>
            <th class="one tabl">Bidded</th>
            <th class="two tabl">More info:</th>
          </thead>
          <% while (resultset.next()) {%>
              <tr>
                <td class="nine tabl"><%=resultset.getString("title")%></td>
                <td class="one tabl"><span></span></td>
                <td class="one tabl"><span class="new">No</span></td>
                <td class="two tabl"><a class="button-primary button" href=./auction.jsp?id=<%=resultset.getString("id")%> >view</a></td>
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
    <script>
      $('select').prop('selectedIndex', -1)
    </script>
    </body>
</html>
