<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>
<%ResultSet resultset = null;%>

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

            <!-- REGISTER ROW -->
            <div class="row">
                <div class="one-third column">
                    <section class="register u-full-width">
                        <h1>register at our eshop</h1>
                        <!-- Restriction for validation are inserted on the end of input -->
                        <form action="executeRegister" method="POST" id="registerForm">
                            <label for="regName">Name:</label>
                            <input class="u-full-width" type="text" id="regName" name="regName" minlength="2" required autofocus>

                            <label for="regSurname">Surname:</label>
                            <input class="u-full-width" type="text" id="regSurname" name="regSurname" minlength="2" required>

                           
                           
                             <% if ( (request.getParameter("duplicate_email") == null || request.getParameter("duplicate_email") == "") ){
                                    out.println(" <label for=\"regEmail\">Email (will be used as your username):</label>\n"
                                                + "<input class=\"u-full-width\" type=\"email\" id=\"regEmai\" name=\"regEmail\" required>");
                                } else if( request.getParameter("duplicate_email").equals("1") ){
                                    out.println(" <label for=\"regEmail\">Email (will be used as your username):</label>\n"
                                                + "<input class=\"u-full-width error\" type=\"email\" id=\"regEmai\" name=\"regEmail\" required>\n"
                                                + "<label id=\"regEmai-error\" class=\"error\" for=\"regEmai\">Email was already registed.</label>");
                                }
                                
                            %>

                            <label for="regVAT">VAT</label>
                            <input class="u-full-width" type="number" id="regVAT" name="regVAT" minlength="2" required>

                            <label for="regAddress">Address</label>
                            <input class="u-full-width" type="text" id="regAddress" name="regAddress" minlength="2" required>

                            <label for="regCity">City</label>
                            <input class="u-full-width" type="text" id="regCity" name="regCity" minlength="2" required>

                            <label for="regPostCode">Post Code</label>
                            <input class="u-full-width" type="text" id="regPostCode" name="regPostCode" length="5" required>

                            <label for="regPostCode">Country</label>
                            <%
                                try {
                                  

                                    // Open a connection
                                    System.out.println("Connecting to a selected database...");
                                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                                    System.out.println("Connected database successfully...");
                                    stmt = conn.createStatement();


                                    resultset = stmt.executeQuery("select * from country");
                                    

                            %>



                            <select name="regCountry" class="u-full-width">
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



                            <label for="regPassword">Password:</label>
                            <input class="u-full-width" type="password" id="regPassword" name="regPassword" minlength="3" required>

                            <label for="regVerifyPassword">Verify Password:</label>
                            <input class="u-full-width" type="password" id="regVerifyPassword" name="regVerifyPassword" equalTo="#regPassword" required>

                            <input class="button-primary" type="submit" value="Register">
                        </form>
                    </section>
                </div>

            </div>

        </div>
        <script>
            $("#registerForm").validate();
        </script>
    </body>
    
</html>
