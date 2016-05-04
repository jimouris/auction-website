<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Ted Project at DI</title>

        <link href="./css/skeleton.css" rel="stylesheet">
    </head>
    <body> 

        <div class="container">
            <%
                Boolean status = (Boolean)request.getAttribute("status");
                if(status != null){
                    out.print(status);
                }

            %>
            <!-- REGISTER ROW -->
            <div class="row">
                <div class="one-third column">
                    <section class="register u-full-width">
                        <h1>register at our eshop</h1>
                        <!-- Restriction for validation are inserted on the end of input -->
                        <form action="register.do" method="POST" id="registerForm">
                            <label for="email">Email:</label>
                            <input class="u-full-width" type="email" id="email" name="email">

                            <label for="name">Name:</label>
                            <input class="u-full-width" type="text" id="name" name="name" minlength="2" required autofocus>

                            <label for="lastname">lastname:</label>
                            <input class="u-full-width" type="text" id="lastname" name="lastname" minlength="2" required>


                            <label for="password">Password:</label>
                            <input class="u-full-width" type="password" id="password" name="password" minlength="3" required>
                            <label for="repeat_password">Verify Password:</label>
                            <input class="u-full-width" type="password" id="repeat_password" name="repeat_password" equalTo="#regPassword" required>

                            <label for="vat">VAT</label>
                            <input class="u-full-width" type="number" id="vat" name="vat" minlength="2" required>

                            <label for="phone">phone number:</label>
                            <input class="u-full-width" type="text" id="phone" name="phone" minlength="2" required>

                            <label for="address">Address</label>
                            <input class="u-full-width" type="text" id="address" name="address" minlength="2" required>

                            <label for="city">City</label>
                            <input class="u-full-width" type="text" id="city" name="city" minlength="2" required>

                            <label for="country">Country</label>
                            <input class="u-full-width" type="text" id="country" name="country" minlength="2" required>

                            <label for="postcode">Post Code</label>
                            <input class="u-full-width" type="text" id="postcode" name="postcode" length="5" required>

                            <label for="latitude">Add your latitude</label>
                            <input type="text" name="latitude" id="latitude" value="23.728569442749">
                            <label for="longitude">Add your longitude</label>
                            <input type="text" name="longitude" id="longitude" value="37.926358404059">


                            <input class="button-primary" type="submit" value="Register">
                        </form>
                    </section>
                </div>

            </div>

        </div>
    </body>
    
</html>
