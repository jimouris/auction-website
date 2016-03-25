<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>

<%
// the user has not logged in yet
if ((session.getAttribute("isUser") == null) || (session.getAttribute("isUser") == "")) {
    response.sendRedirect("./index.jsp");
  }
%>

<%ResultSet resultset = null;
try {

    // Open a connection
    System.out.println("Connecting to a selected database...");
    conn = DriverManager.getConnection(DB_URL, USER, PASS);
    System.out.println("Connected database successfully...");
    stmt = conn.createStatement();
%>

<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Ted Project at DI</title>

        <link href="./css/skeleton.css" rel="stylesheet">
        <script src="./js/jquery.js"></script>
        <script src="./js/validate.js"></script>
    </head>
    <body> 
    <div class="container">

    <!-- HEADER STUFF -->
    <div class="row">
      <div class="one column">
        <a href="./welcome.jsp">
          <img class="u-max-full-width" src="./images/logo.png">
        </a>
      </div>
      <div class="offset-by-seven four columns">
        <ul class="nav u-full-width row">
          <li class="offset-by-one-third one-third column newMessage tooltip"><span class="tooltipFire">Messages</span><div class="tooltipText"><div class="tooltipMargin"></div><a href="./messages_inc.jsp">Income</a><br /><a href="./messages_out.jsp">SENT</a></div></li>
          <li class="one-third column"><a href="./logout.jsp?type=regular"><span class="delete">Logout</span></a></li>
        </ul>
      </div>
    </div>
    <!-- end of header row -->
       


    <!-- NEW AUCTION ROW -->
    <div class="row">
        <div class="one-half column">
            <section class="register u-full-width">
                <h1>Create a new auction</h1>
                <!-- Restriction for validation are inserted on the end of input -->
                <form action="executeNewAuction" method="POST" id="new_auction">
                    <label for="aucName">Name of auctioned item:</label>
                    <input class="u-full-width" type="text" id="aucName" name="aucName" minlength="2" required autofocus>

                    <%
                        resultset = stmt.executeQuery("select id, name from category order by name");
                    %>

                    <label for="aucCategory1">Select appropriate categories for you item</label>
                    
                    <%  while (resultset.next()) {
                        // aucCategory sends a string with all of the selected categories
                        out.println("<label>");
                        out.println("<input type=\"checkbox\" name=\"aucCategory\" id=\"aucCategory\" value=" + resultset.getString(1) + " required />");
                        out.println(resultset.getString(2) + "</label>");
                     } %>
                    <!-- </datalist> -->

                    
                    <h4>Use a buy price?</h4>
                    <label class="disableBuyPrice u-full-width">
                      <input type="radio" name="aucInstantBuy" value="0" onclick="document.getElementById('buyPrice').setAttribute('style','visibility: hidden; opacity: 0; height: 0')" checked> <!-- value 0 of aucInstantBuy means that no buy price is available -->
                      <span class="label-body">No. Everyone will bid for my product.</span>
                    </label>
                    <label class="enableBuyPrice u-full-width">
                      <input type="radio" name="aucInstantBuy" value="1" onclick="document.getElementById('buyPrice').setAttribute('style','visibility: visible; opacity: 1; height: auto;')"> <!-- value 1 aucInstantBuy means that a fixed buy price is available -->
                      <span class="label-body">Yea! Someone can instantly buy my product.</span>
                    </label>
                    <div class="row hidden u-full-width" id="buyPrice">
                      <label for="">Set the buy price:</label>
                      <input class="u-full-width" form="new_auction" type="number" min=0 max=10000 name="aucBuyPrice" value="10">
                    </div>  

                    <label for="aucFirstBid">Set the lowest bid</label>
                    <input class="u-full-width" type="number" id="aucFirstBid" name="aucFirstBid" minlength="1" required>
                
                    <label for="aucCity">The city where is the auctioned item.</label>
                    <input class="u-full-width" type="text" id="aucCity" name="aucCity" minlength="2" required>

                    <label for="aucCountry">The country where is the auction item.</label>
                    <%
                        resultset = stmt.executeQuery("select * from country");
                    %>


                    <select name="aucCountry" id="aucCountry" class="u-full-width">
                        <%  while (resultset.next()) {%>
                        <option><%= resultset.getString(3)%></option>
                        <% } %>
                    </select>


                    <%  //**clean up things
                        conn.close();
                        stmt.close();
                        resultset.close();
                        
                        //**Should I input the codes here?**
                        } catch (Exception e) {
                            out.println("wrong entry" + e);
                        }
                    %>


                    
                    <label for="auchDescription">Description</label>
                    <textarea form="new_auction" class="u-full-width" placeholder="a high quality product..." name="aucDescription" id="auchDescription"></textarea>
                    

                    <input class="button-primary u-pull-right" type="submit" value="create auction">
                </form>
            </section>
        </div>

    </div>
    <!-- END OF NEW AUCTION -->

    </div>
        <script>
            $("#new_auction").validate();
        </script>
    </body>
    
</html>
