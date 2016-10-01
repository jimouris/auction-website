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
    <link href="/css/organism.css" rel="stylesheet">

    <jsp:useBean id="categoryLst" class="java.util.ArrayList" scope="request" />
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="/"/>
</c:if>
<!-- HEADER STUFF -->
<%@ include file="../header.jsp" %>

<div class="container">
    <!-- NEW AUCTION ROW -->
    <div class="row">
        <div class="column">
            <section class="register u-full-width">
                <h1>Create a new auction</h1>
                <!-- Restriction for validation are inserted on the end of input -->
                <form action="/auction.do" method="POST" id="new_auction" enctype="multipart/form-data">

                    <label for="name">Name of auctioned item:</label>
                    <input class="u-full-width" type="text" id="name" name="name" minlength="2" required autofocus>

                    <label>Add some images:</label>
                    <input type="file" name="fileName">


                    <label for="categories">Select at least one category:</label>
                    <select class="a-select--multiple" id="categories" name="categories" multiple size=${categoryLst.size()}>
                        <c:forEach var="category" items="${categoryLst}">
                            <option value=${category.categoryId}>${category.categoryName}</option>
                        </c:forEach>
                    </select>

                    <b>Can someone instantly buy your product?</b>
                    <label class="u-full-width">
                        <input type="radio" name="instantBuy" value="false" onclick="document.getElementById('buyPrice').setAttribute('style','display: none')" checked>
                        <span class="label-body">No. Everyone will bid for my product.</span>
                    </label>
                    <label class="u-full-width">
                        <input type="radio" name="instantBuy" value="true" onclick="document.getElementById('buyPrice').setAttribute('style','display: block')">
                        <span class="label-body">Yea! Someone can instantly buy my product.</span>
                    </label>
                    <div class="u-softhide u-full-width" id="buyPrice">
                        <label>Ok. Set the buy price:</label>
                        <input class="u-full-width" type="number" min=0 max=1000000 name="buyPrice">
                    </div>

                    <label for="lowestBid">Set the lowest bid</label>
                    <input class="u-full-width" type="number" id="lowestBid" name="lowestBid" minlength="1" required>

                    <div class="row">
                        <div class="six columns">
                            <label for="location">Location</label>
                            <input class="u-full-width" type="text" id="location" name="location" minlength="2" required autofocus>

                            <label for="country">Country</label>
                            <input class="u-full-width" type="text" id="country" name="country" minlength="2" required autofocus>

                            <input type="hidden" name="longitude" id="longitude" value="23.728569442749">
                            <input type="hidden" name="latitude" id="latitude" value="37.926358404059">
                        </div>
                        <div class="six columns">
                            <div id="map" style="height: 250px;"></div>
                        </div>
                    </div>

                    <label for="description">Description</label>
                    <textarea form="new_auction" class="u-full-width" placeholder="a high quality product..." name="description" id="description"></textarea>

                    <b>Activate the auction now?</b>
                    <label>
                        <input type="radio" name="startToday" value="false" onclick="document.getElementById('activeDays').setAttribute('style','display: none')" checked>No
                    </label>
                    <label>
                        <input type="radio" name="startToday" value="true" onclick="document.getElementById('activeDays').setAttribute('style','display: block')">Yes
                    </label>
                    <div class="u-softhide" id="activeDays">
                        <label for="endDate">So How many days this auction will be active?</label>
                        <input type="number" name="activeDays" value="1" id="endDate">
                    </div>

                    <input class="button-primary u-pull-right" type="submit" name="action" value="addNew">
                </form>
            </section>
        </div>

    </div>
    <!-- END OF NEW AUCTION -->

</div>

<script src="../js/jquery.min.js"></script>
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
                document.getElementById('location'));

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


            // Get each component of the location from the place details
            // and fill the corresponding field on the form.
            var location = '';
            var country = '';
            place.address_components.forEach(function(a){
                var a_symbol = location.length > 0 ? ', ' : '';
                switch (a.types[0]) {
                    case 'neighborhood':
                        location = location + a_symbol + a.long_name;
                        break;
                    case 'country':
                        country = a.long_name;
                        break;
                    case 'route':
                        location = location + a_symbol +  a.long_name;
                        break;
                    case 'street_number':
                        location = location + a_symbol + a.short_name;
                        break;
                    case 'locality':
                        location = location + a_symbol + a.long_name;
                        break;
                    default:
                        break;
                };
            });

            $('#location').val(location);
            $('#country').val(country);

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

    $('form').delegate('input[type="file"]', 'change', function(){
        $(this).after('<input type="file" name="fileName" >');
    });
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA7om9lzVVpATrE6I8ceaK9vMyE6Bi2KSw&callback=initMap&libraries=places" async defer></script>
<script src="../js/scripts.js"></script>
</body>
</html>
