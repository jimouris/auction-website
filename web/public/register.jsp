<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Ted Project at DI</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
</head>
<body>
<!-- HEADER STUFF -->
<a href="/public/">Homepage</a>

<!-- end of header row -->
<div class="container">
    <!-- REGISTER ROW -->
    <div class="row">
        <div class="">
            <section class="register u-full-width">
                <h1>Register at our eshop</h1>
                <c:if test="${not empty errorMsg}">
                    <p class="status--error">${errorMsg}</p>
                </c:if>
                <!-- Restriction for validation are inserted on the end of input -->
                <form action="/user.do" method="POST" id="registerForm">
                    <input type="hidden" name="action" value="register">
                    <label for="username">Username</label>
                    <input class="u-full-width" type="text" id="username" name="username" required autofocus></input>
                    <span class="js-input-error"></span>

                    <label for="email">Email:</label>
                    <input class="u-full-width" type="email" id="email" name="email" required autofocus></input>
                    <span class="js-input-error"></span>

                    <label for="name">Name:</label>
                    <input class="u-full-width" type="text" id="name" name="name" minlength="2" required autofocus>

                    <label for="lastname">Lastname:</label>
                    <input class="u-full-width" type="text" id="lastname" name="lastname" minlength="2" required autofocus>


                    <label for="password">Password:</label>
                    <input class="u-full-width" type="password" id="password" name="password" minlength="3" required autofocus>
                    <label for="repeat_password">Verify Password:</label>
                    <input class="u-full-width" type="password" id="repeat_password" name="repeat_password" equalTo="#regPassword" required autofocus>

                    <label for="vat">VAT</label>
                    <input class="u-full-width" type="number" id="vat" name="vat" minlength="2" required autofocus>

                    <label for="phone">Phone number:</label>
                    <input class="u-full-width" type="text" id="phone" name="phone" minlength="2" required autofocus>

                    <div class="row">
                        <div class="six columns">
                            <label for="address">Address</label>
                            <input class="u-full-width" type="text" id="address" name="address" minlength="2" required autofocus>

                            <label for="city">City</label>
                            <input class="u-full-width" type="text" id="city" name="city" minlength="2" required autofocus>

                            <label for="country">Country</label>
                            <input class="u-full-width" type="text" id="country" name="country" minlength="2" required autofocus>

                            <input type="hidden" name="longitude" id="longitude" value="23.728569442749">
                            <input type="hidden" name="latitude" id="latitude" value="37.926358404059">
                        </div>
                        <div class="six columns">
                            <div id="map" style="height: 250px;"></div>
                        </div>
                    </div>


                    <input class="button-primary" type="submit" value="Register">
                </form>
            </section>
        </div>

    </div>

</div>
<script src="/js/jquery.min.js"></script>
<script>
    lat = 0;
    lng = 0;
    function initMap() {
        mapOptions = {
            center: {lat: -33.8688, lng: 151.2195},
            zoom: 13,
            scrollwheel: true
        };
        map = new google.maps.Map(document.getElementById('map'),
                mapOptions);

        input = /** @type {HTMLInputElement} */(
                document.getElementById('address'));

        // Create the autocomplete helper, and associate it with
        // an HTML text input box.
        autocomplete = new google.maps.places.Autocomplete(input);

        infowindow = new google.maps.InfoWindow();
        marker = new google.maps.Marker({
            draggable: true,
            map: map,
        });


        google.maps.event.addListener(autocomplete, 'place_changed', function() {
            var place = autocomplete.getPlace();
            console.log(place.geometry.location.lng + ', lat' + place.geometry.location.lat);

            // check if the selected place has a geometry representation
            if (!place.geometry) {
                return;
            }

            // set the data that we will send to server
            lat = place.geometry.location.lat();
            lng = place.geometry.location.lng();
            $('#latitude').val(lat);
            $('#longitude').val(lng);


            // Get each component of the address from the place details
            // and fill the corresponding field on the form.
            var address = '';
            var country = '';
            var city = '';
            place.address_components.forEach(function(a){
                var a_symbol = address.length > 0 ? ', ' : '';
                var c_symbol = city.length > 0 ? ', ' : '';
                switch (a.types[0]) {
                    case 'neighborhood':
                        address = address + a_symbol + a.long_name;
                        break;
                    case 'country':
                        country = a.long_name;
                        break;
                    case 'route':
                        address = address + a_symbol +  a.long_name;
                        break;
                    case 'street_number':
                        address = address + a_symbol + a.short_name;
                        break;
                    case 'locality':
                        address = address + a_symbol + a.long_name;
                        break;
                    case 'administrative_area_level_5':
                        city = city + c_symbol + a.long_name;
                        break;
                    default:
                        break;
                };
            });

            $('#address').val(address);
            $('#country').val(country);
            $('#city').val(city);

            if (place.geometry.viewport) {
                map.fitBounds(place.geometry.viewport);
            } else {
                map.setCenter(place.geometry.location);
                map.setZoom(15);
            }

            // Set the position of the marker using the place ID and location.
            marker.setPlace(/** @type {!google.maps.Place} */ ({
                location: place.geometry.location,
                placeId: place.id,
            }));
            marker.setVisible(true);
        });
    }

    $(document).ready(function(){
        // when the user clicks the button
        $('#username').focusout(function(){
            // get the data from input
            var uname = $('#username').val();
            // write the appropriate url
            // note: an usernameExist should be implemented on user.do controller as a get action
            var url = '/user.do?action=unameExists&uname=' + uname;

            // the $.ajax(....) will send a get request to url
            $.ajax({
                url: url,
                success: function(data){
                    // this is the data returned by the controller
                    if (data.indexOf('exists') >= 0 && uname.length > 0)
                        $('#username + .js-input-error').removeClass('status--success').addClass('status--error').text('username already exists');
                    else
                        $('#username + .js-input-error').addClass('status--success').removeClass('status--error').text('username available');
                } // end sucess function
            });
            if (uname.length > 0)
                $('#username + .js-input-error').show();
            else
                $('#username + .js-input-error').hide();
        });

        // when the user clicks the button
        $('#email').focusout(function(){
            // get the data from input
            var email = $('#email').val();
            // write the appropriate url
            // note: an emailExist should be implemented on user.do controller as a get action
            var url = '/user.do?action=emailExists&email=' + email;

            // the $.ajax(....) will send a get request to url
            $.ajax({
                url: url,
                success: function(data){
                    // this is the data returned by the controller
                    if (data.indexOf('exists') >= 0)
                        $('#email + .js-input-error').removeClass('status--success').addClass('status--error').text('email already exists');
                    else
                        $('#email + .js-input-error').addClass('status--success').removeClass('status--error').text('email available');
                } // end sucess function
            });
            if (email.length > 0)
                $('#email + .js-input-error').show();
            else
                $('#email + .js-input-error').hide();
        });
    });

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA7om9lzVVpATrE6I8ceaK9vMyE6Bi2KSw&callback=initMap&libraries=places" async defer></script>

</body>
</html>
