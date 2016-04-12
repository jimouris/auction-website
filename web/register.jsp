<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Ted Project at DI</title>

        <link href="./css/skeleton.css" rel="stylesheet">
    </head>
    <body> 

        <div class="container"> 

            <!-- REGISTER ROW -->
            <div class="row">
                <div class="one-third column">
                    <section class="register u-full-width">
                        <h1>register at our eshop</h1>
                        <!-- Restriction for validation are inserted on the end of input -->
                        <form action="" method="POST" id="registerForm">
                            <label for="regName">Name:</label>
                            <input class="u-full-width" type="text" id="regName" name="regName" minlength="2" required autofocus>

                            <label for="regSurname">Surname:</label>
                            <input class="u-full-width" type="text" id="regSurname" name="regSurname" minlength="2" required>


                            <label for="regVAT">VAT</label>
                            <input class="u-full-width" type="number" id="regVAT" name="regVAT" minlength="2" required>

                            <label for="regAddress">Address</label>
                            <input class="u-full-width" type="text" id="regAddress" name="regAddress" minlength="2" required>

                            <label for="regCity">City</label>
                            <input class="u-full-width" type="text" id="regCity" name="regCity" minlength="2" required>

                            <label for="regPostCode">Post Code</label>
                            <input class="u-full-width" type="text" id="regPostCode" name="regPostCode" length="5" required>

                            <label for="regPostCode">Country</label>



                            <select name="regCountry" class="u-full-width">
                                <option>Athens</option>
                            </select>



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
    </body>
    
</html>
