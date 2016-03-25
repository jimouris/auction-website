<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>

<% // check if user is admin or regular and forward accordingly to his page
if ((session.getAttribute("isAdmin") != null) && (session.getAttribute("isAdmin") != "")) {
    String nextJSP = "/admin.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
    dispatcher.forward(request,response);
} else if ((session.getAttribute("isUser") != null) && (session.getAttribute("isUser") != "")) {
    String nextJSP = "/welcome.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
    dispatcher.forward(request,response);
}
ResultSet resultset = null;
%>

<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>DI Ted Project</title>
  <%@ include file="./linked_files.jsp" %>
  <style>
 h4.u-center-text.button {
     position: relative;
     top: -10px; 
     background: white;
     box-shadow: 0 -4px 47px -5px #eee;
     border: none;
     border-top: 1px solid #333;   
     border-radius: 0;
     margin-top:0;
 }


 @-webkit-keyframes moveDown{
     from{ top: -10px;}
     to{ top: -2px;}
 }


 h4.u-center-text.button:hover{
     -webkit-animation: moveDown 500ms;
     top: -2px;
 }

  </style>
</head>
<body>  
<div class="container"> 
 
  <!-- SEARCH ROW -->
  <div class="row">
    <section class="search">
      <h1>Search something buddy</h1>
      <form action="#" method="POST">
        <div class="nine columns">
          <input class="u-full-width" type="text" name="searchTetxt">
        </div>
        <div class="three columns">
          <input class="u-full-width button-primary" type="submit" value="Search">
        </div>
      </form>
    </section>
  </div>
  <!-- end of search row -->
    
  <!-- login's data::loginEmail and loginPassword -->
  <!-- REGISTER && SIGNUP ROW -->
  <div class="row">
    <div class="two-thirds column">
      
        <h1>register at our eshop</h1>
        <button class="button-primary" onclick="location.href='register.jsp'" >register</button>
      
    </div>
    <!-- end of register column -->
    <div class="one-third column">
      <section class="login u-full-width">
        <h1>Login</h1>
        <form action="executeLogin" method="POST" id="loginForm">
          <label for="loginEmail">email</label>
          <input class="u-full-width" type="email" id="loginUsername" name="loginUsername" required autofocus>
          <label for="loginPassword">password</label>
          <input class="u-full-width" type="password" id="loginPassword" name="loginPassword" required>
          <input class="button-primary u-pull-right" type="submit" value="Login">
        </form>
      </section>
    </div>
    <!-- end of login column -->
  </div>

   <!-- CATEGORIES ROW -->
    <div class="row u-full-width u-cf">
      <h1>Navigate based on the categories</h1>
      <section class="categories">
        <ul>
        <%
          try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            resultset = stmt.executeQuery("select * from category order by name");
            while (resultset.next()) {
              out.println("<li class=no-style><a href=./search.jsp?searchCategory=" + resultset.getString(1) + ">" + resultset.getString(2) + "</a></li>" );
             } 
          } catch(Exception e){
            conn.close();
            stmt.close();
            resultset.close();
          } finally{
            conn.close();
            stmt.close();
            resultset.close();
          }
        %>
      </section>
    </div>  
  
  </div>


  <script type="text/javascript">
      $("#loginForm").validate();
  </script>
</body>
</html>
