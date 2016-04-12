<%-- 
    Document   : messages
    Created on : May 10, 2015, 2:09:49 PM
--%>

<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>
<%@page import="java.lang.*"%>

<%
String receiver_id="";
String user_id="";
ResultSet rs = null;
// the user has not logged in yet
if ((session.getAttribute("isUser") == null) || (session.getAttribute("isUser") == "")) {
    response.sendRedirect("./index.jsp");
  }
else{
 
  try{
      receiver_id = (String) request.getParameter("receiver");
      user_id = session.getAttribute("userId").toString();
      // setting connection & statement vars

      // Open a connection
      System.out.println("Connecting to a tedproject database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected to tedporject successfully...");
      stmt = conn.createStatement();

      System.out.println("getting the messages of a conversation");
      rs = stmt.executeQuery("select * from message, account inner join conversations on senderid=account.id where ((senderid=" + user_id + " and receiverid=" + receiver_id + ") or (senderid = " + receiver_id + " and receiverid= " + user_id + ")) and (messageid=message.id) order by time");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Conversation</title>

  <link href="./css/skeleton.css" rel="stylesheet">
  <link href="./css/organism.css" rel="stylesheet">
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


    <div class="">
    <a href="./messages_inc.jsp">go to income message</a>
    </div>
    <!-- MESSAGS -->
    <div class="row">
      <main class="u-full-width ">
      <% while (rs.next()){ %>
        <div class="u-full-width message u-cf">
          <h3 class="message__title ten columns"><%= rs.getString("firstname") %> <%= rs.getString("lastname") %> said:</h3>
          <div class="message__info two columns u-pull-right">
            <h4 class="message__time u-text-right"><%= rs.getDate("time") %></h4>
          </div>
        </div>
        <p class="message__text"><strong><%= rs.getString("title") %></strong><br /><%= rs.getString("messagetext") %></p>
        <hr />

        <%  } // end of while fetching message
              
            } catch (Exception e){
               out.println("An exception occurred: " + e.getMessage());
            } finally{
              rs.close();
              conn.close();
              stmt.close();
            }
        } // end of if else user

        %>

        <div class="row u-full-width">
          <form action="executeAnswer" method="POST" id="answerForm">
          <input type="hidden" name="sender_id" value=<%=session.getAttribute("isUser")%> /> 
          <label><span class="arrow">></span>Reply:
            <input type="hidden" name="receiver_id" value=<%=receiver_id%> /> 
          </label>
          <label>Message Title
            <input type="text" name="title" value="">
          </label>
          <input type="text" name="message_text" class="u-full-width">
          <input type="submit" class="button-primary" value="send">
        </form>
        </div>
      </main>
    </div>
    <!-- end of messages row -->
  </div>
</body>
</html>
