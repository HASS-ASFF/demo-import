
$('#myForm').on('submit', function(e) {
    e.preventDefault();

    $.ajax({
        type: 'post',
        url: '/passages/save',
        data: $('form').serialize(),
        success: function() {
            $("#success-message").text("form was submitted !");
        }
    });

});