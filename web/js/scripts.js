/**
 * Created by gpelelis on 27/8/2016.
 */
$(document).ready(function () {
   $('.c-dropdown').delegate('.c-dropdown__trigger', 'click', function () {
     var dropd = $(this).closest('.c-dropdown');
       $(dropd).find('.c-dropdown__content').toggleClass('h-forceShow');
   });

    $('.c-delete').delegate('.c-delete__icon, .c-delete__cancel', 'click', function(){
        var daddy = $(this).closest('.c-delete');
        $(daddy).find('.c-delete__icon').toggleClass('h-forceHide');
        $(daddy).find('.c-delete__confirm').toggleClass('h-show');
        $(daddy).find('.c-delete__cancel').toggleClass('h-showInline');
    });

    $('.form-changeDate').submit(function () {
        var dt = new Date();
        var dateField = $('[name="endingDate"]');
        var endingDate;
        var h, m, s;
        h = dt.getHours();
        m = dt.getMinutes();
        s = dt.getSeconds();
        endingDate = $(dateField).val() + " " + h + ":" + m + ":" + s;
        $(dateField).attr('type', 'text').val(endingDate);
        console.log(dateField.val());
    });
});

// get an array of elements from ck cookie
// *1* calling split on an empty cookie will return [""],
// so use this check in order to create an empty array
var getArray = function(ck){
    var arr;
    var cookiz = $.cookie(ck) || "";
    arr = cookiz.length > 0 ? cookiz.split(",") : []; /* 1* */
    return arr;
};

// create a comma seperated string with values from array
var genStringArray = function(arr){
    return arr.join(',');
};

var print = function(msg){
    console.log(msg);
};


// create a method that adds an element to array
var addToArrayCookie = function(cookieName, ele, callback){
    // create the array from the cookie
    var arr = getArray(cookieName);

    // check if value exists in array
    if (!arr.includes(ele)){
        arr.push(ele);
        print("added " + ele);
    }

    // recreate the cookie and then save
    $.cookie(cookieName, genStringArray(arr));

    callback(cookieName);
    return;
};

// create a method that removes an to array
var removeFromArrayCookie = function(cookieName, ele, callback){
    // create the array from the cookie
    var arr = getArray(cookieName);

    if (arr.includes(ele)){
        // check if value exists in array
        var found = arr.indexOf(ele);

        while (found !== -1) {
            arr.splice(found, 1);
            found = arr.indexOf(ele);
        }
    }

    // recreate the cookie and then save
    $.cookie(cookieName, genStringArray(arr));

    callback(cookieName);
    return;
};

var arrayCookieIsEmpty = function(cookieName){
    var ckie;
    ckie = $.cookie(cookieName) || ""; // use || if $.cookie returns undefined
    return ckie.length == 0;
}

// checks if there are selected values even from previous pages and shows/hides the export button
var checkForSelected = function(cookieName){
    // check if selected values
    var isEmpty = arrayCookieIsEmpty(cookieName);

    if (isEmpty)
        $('.button-primary').prop('disabled', true);
    else
        $('.button-primary').prop('disabled', false);
};

// will set the checkboxes that are previous selected
var setSelectedFields = function (cookieName) {
    var arr = getArray(cookieName);
    $('input').each(function(){
        $(this)[0].checked = false;
    });
    arr.forEach(function(id){
        try {
            $('#' + id)[0].checked = true;
        } catch(e){
            // nothing
        }
    });
};


var deleteAll = function (cookieName) {
    $.removeCookie(cookieName);
};