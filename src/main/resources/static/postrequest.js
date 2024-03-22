$(document).ready(function() {
    // Function to handle click on editable fields
    $('.editable-field').click(function() {
        var fieldValue = $(this).text().trim();
        var inputType = $(this).hasClass('editable-textarea') ? 'textarea' : 'input';
        var inputHtml = '<td><' + inputType + ' class="edit-input form-control" value="' + fieldValue + '"></td>';
        $(this).html(inputHtml);
    });
});

