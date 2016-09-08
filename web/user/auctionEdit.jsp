<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Auction Info</title>
    <jsp:useBean id="auction" class="javauction.model.AuctionEntity" scope="request" />
    <jsp:useBean id="usedCategories" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="categoryLst" class="java.util.ArrayList" scope="request" />
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
</head>
<body>
<!-- HEADER STUFF -->
<c:if test="${empty user}">
    <c:redirect url="/"/>
</c:if>
<c:if test="${not empty user.userId}">
    <%@ include file="./header.jsp" %>
</c:if>

<c:if test ="${not empty auction}">
    <div class="custom-container">
        <div class="row">
            <a class="u-pull-left button" href="/auction.do?action=getAnAuction&aid=${auction.auctionId}">cancel edit</a>
            <form class="u-pull-right" action="/auction.do" method="POST">
                <input type="hidden" name="aid" value="${auction.auctionId}">
                <button class="button" type="submit" name="action" value="deleteAuction">Delete Auction</button>
            </form>
        </div>
        <form action="/auction.do" method="POST" id="view_updateAuction">
            <div class="row">
                <label>Name</label>
                <input class="u-full-width" type="text" name="name" minlength="2" required value="${auction.name}">

                <c:if test="${not empty usedCategories}">
                    <label>current selected categories</label>
                    <c:forEach var="category" items="${usedCategories}">
                        <span value=${category.categoryId}>${category.categoryName}</span>
                    </c:forEach>
                </c:if>

                <label for="categories">Select and override the previous categories</label>
                <select class="a-select--multiple" id="categories" name="categories" multiple size=${categoryLst.size()}>
                    <c:forEach var="category" items="${categoryLst}">
                        <option value=${category.categoryId}>${category.categoryName}</option>
                    </c:forEach>
                </select>
                <br>

                <label>Description</label>
                <input class="u-full-width" type="text" name="description" minlength="2" autofocus value="${auction.description}">

                <label>Lowest bid</label>
                <input class="u-full-width" type="number" name="lowestBid" minlength="1" required value="${auction.lowestBid}">

                <c:if test="${auction.buyPrice >= 0}">
                    <label>Buy price (Instant buy)</label>
                    <input class="u-full-width" type="number" name="buyPrice" minlength="1" value="${auction.buyPrice}">
                </c:if>
                <c:if test="${auction.buyPrice < 0}">
                    <label>Buy price (Instant buy)</label>
                    <input class="u-full-width" type="number" name="buyPrice" minlength="1">
                </c:if>

                <div class="row">
                    <div class="six columns">
                        <label for="location">Location</label>
                        <input class="u-full-width" type="text" id="location" name="location" minlength="2" value="${auction.location}" required autofocus>

                        <label for="country">Country</label>
                        <input class="u-full-width" type="text" id="country" name="country" minlength="2" value="${auction.country}" required autofocus>

                        <input type="hidden" name="longitude" id="longitude" value="${auction.longitude}">
                        <input type="hidden" name="latitude" id="latitude" value="${auction.latitude}">
                    </div>
                    <div class="six columns">
                        <div id="map" style="height: 250px;"></div>
                    </div>
                </div>

                <input type="hidden" name="aid" value="${auction.auctionId}">
                <button class="button button-primary" type="submit" name="action" value="updateAuction">update auction</button>
            </div>
        </form>
    </div>
</c:if>

<c:if test ="${empty auction}">
    <h3>The auction with id ${param.aid} does not exist!</h3>
</c:if>

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
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA7om9lzVVpATrE6I8ceaK9vMyE6Bi2KSw&callback=initMap&libraries=places" async defer></script>
<script src="../js/scripts.js"></script>

</body>
</html>
