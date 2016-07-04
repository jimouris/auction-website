<%@ page import="com.sun.org.apache.xpath.internal.operations.Bool" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>DI Ted Project</title>
  <link href="css/skeleton.css" rel="stylesheet">
  <link href="css/organism.css" rel="stylesheet">
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
        <form action="login.do" method="POST" id="loginForm">
          <label>email</label>
          <input class="u-full-width" type="text" id="username" name="username"  autofocus>
          <label for="password">password</label>
          <input class="u-full-width" type="password" id="password" name="password" required>
          <input type="hidden" name="action" value="user">
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
    </section>
  </div>

</div>
</body>
</html>
