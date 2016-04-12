
<%@page import="java.lang.Integer"%>
<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>
<%ResultSet rs = null;%>

<%
// check if the user has an active session
if ((session.getAttribute("isUser") == null) || (session.getAttribute("isUser") == "")) {
    response.sendRedirect("./index.jsp");
  }
  else{ // is is a user
    
    try {
     
        // Open a connection
        System.out.println("Connecting to a tedproject database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected to tedporject successfully...");
        stmt = conn.createStatement();

      
        // select all the messages that have been sent by the logged in user.
        // return the name and id of the receiver
        rs = stmt.executeQuery("select firstname, lastname, receiverid from conversations,account where senderid=" + session.getAttribute("isUser") + " and receiverid=account.id group by receiverid" ); %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
        <title>Messages</title>

        <link href="./css/skeleton.css" rel="stylesheet">
        <script src="./js/jquery.js"></script>
        <!-- <script src="./js/validate.js"></script> -->
    </head>
    <body>
    <div class="container">
       <!-- HEADER STUFF -->
       <div class="row"> 
         <div class="one column">
           <a href="#">
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

        <div class="u-text-right">
          <a href="./conversation.jsp">Start a conversation</a>
        </div>
        <div class="row">
        <h4>People that you have sent messages:</h4>
          <div class="conversations row">
              <% while (rs.next()){ %>
                <div class="u-full-width message row">
                 <% 
                   String receiver_fname;
                   Integer receiver_id;
                   receiver_fname = (String) rs.getString("firstname");
                   receiver_id = (Integer) rs.getInt("receiverid");
                   out.println("<span class='message__title four columns'>" + receiver_fname + "</span>");
                 %>
                 <a class="eight columns" href=./conversation.jsp?receiver=<%=receiver_id%>>view conversation</a>

                </div>
                <hr />

                
              <%} } catch (Exception e) {
                      out.println("wrong entry" + e);
                  } finally{
                      rs.close();
                      conn.close();
                      stmt.close();
                  }
                } // end of user login if
              %>
            </div>
          </div>
        </div>
    </div>
    <!-- end of container -->
    </body>
</html>
