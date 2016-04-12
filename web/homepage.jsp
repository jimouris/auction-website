<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Welcome Page</title>

    <link href="./css/skeleton.css" rel="stylesheet">
    <style>
        .look{
            font-size: 2.3em;
            position: absolute;
            font-weight: 900;
            top: -22px;
            left: -34px;
        }
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

    <!-- SEARCH ROW -->
    <div class="row ">
        <section class="four columns">
            <h1><span class="look">></span> Auctions</h1>
            <a class="button button-primary" href="./newAuction.jsp">Create an auction</a>
            <a class="button button u-top-10" href="./auctions.jsp">View your auctions</a>
            <a class="button button u-top-10" href="./auctions.jsp?p=all">View all active auctions</a>
        </section>


        <section class="search eight columns">
            <h1>Search for a product</h1>
            <form class="row" action="./search.jsp" method="POST" id="search">
                <div class="nine columns">
                    <input class="u-full-width" type="text" name="searchTetx">
                </div>
                <div class="three columns">
                    <input class="u-full-width button-primary" type="submit" value="Search">
                </div>
            </form>

            <h4>Use custom criteria?</h4>
            <label class="disableCustomCriteria one-half column">
                <input type="radio" name="criteria" value="disable" onclick="document.getElementById('customCriteria').setAttribute('style','visibility: hidden; opacity: 0; height: 0')" checked>
                <span class="label-body">No, I want just a simple search.</span>
            </label>
            <label class="enableCustomCriteria one-half column">
                <input type="radio" name="criteria" value="enable" onclick="document.getElementById('customCriteria').setAttribute('style','visibility: visible; opacity: 1; height: auto;')">
                <span class="label-body">Yea! I love custom advanced search.</span>
            </label>
            <div class="row hidden u-full-width" id="customCriteria">
                <div class="one-third column">
                    <label for="searchCategory">Category</label>
                    <input class="u-full-width" form="search" list="datalistCategories" name="searchCategory" id="searchCategory">
                    <datalist id="datalistCategories">
                        <option value="computer">
                        <option value="smartphones">
                        <option value="vinyls">
                        <option value="furniture">
                    </datalist>
                </div>
                <div class="one-third column">
                    <label for="searchPrice">Price</label>
                    <input class="u-full-width" form="search" type="number" min=0 max=10000 name="searchPriceInput" id="searchPriceInput" value="5000" oninput="this.form.searchPriceRange.value=this.value">
                    <input class="u-full-width" form="search" type="range" min=0 max=10000 name="searchPriceRange" id="searchPriceRange" oninput="this.form.searchPriceInput.value=this.value">
                </div>
                <div class="one-third column">
                    <label for="searchLocation">Location</label>
                    <input class="u-full-width" form="search" type="search" name="searchLocation" id="searchLocation">
                </div>
                <div class="u-cf"></div>
                <div class="u-full-width">
                    <label for="searchDescription">Description</label>
                    <textarea form="search" class="u-full-width" placeholder="a high quality sa..." name="searchDescription" id="searchDescription"></textarea>
                </div>
            </div>
            <!-- end of custom criteria row -->
        </section>
    </div>
    <!-- end of search row -->

    <!-- DIMOPRASIES ROW -->

    <!-- enf of dimoprasies row -->

    <!-- CATEGORIES ROW -->
    <div class="row u-full-width u-cf">
        <h1>Navigate based on the categories</h1>
        <section class="categories">
            <ul></ul>
        </section>
    </div>
</body>
</html>