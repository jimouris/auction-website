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
