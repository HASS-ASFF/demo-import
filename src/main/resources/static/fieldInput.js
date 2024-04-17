$(document).ready(function() {
    // Function to handle click on editable fields
    $('body').on('click', '.editable-field', function(event) {
        console.log("clicked ! ");
        event.stopPropagation();
        var fieldValue = $(this).text().trim();
        var inputType = 'input';
        var inputHtml = '<' + inputType + ' class="edit-input form-control" value="' + fieldValue + '">';
        $(this).empty().append(inputHtml);
        $('.edit-input').focus();
    });

    // Function to handle input field blur (when clicked outside)
    $('body').on('blur', '.edit-input', function(event) {
        var newValue = $(this).val().trim();
        var tdElement = $(this).closest('.editable-field');
        tdElement.text(newValue);
    });

    // Function to prevent removing input when clicking inside
    $('body').on('click', '.edit-input', function(event) {
        event.stopPropagation();
    });

    // Function to handle click outside the editable field
    $(document).click(function() {
        $('.edit-input').each(function() {
            var newValue = $(this).val().trim();
            var tdElement = $(this).closest('.editable-field');
            tdElement.text(newValue);
        });
    });
});
