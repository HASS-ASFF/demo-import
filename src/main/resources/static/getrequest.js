$(document).ready(function() {

    // GET REQUEST BY DATE
    $("#getPassagesByDate").click(function(event) {
        event.preventDefault();
        var selectedDate = $("#dateBilan").val(); // Récupérer la date sélectionnée
        console.log(selectedDate);
        ajaxGetByDate(selectedDate);
    });

    // DO GET BY DATE
    function ajaxGetByDate(selectedDate) {
        $.ajax({
            type : "GET",
            url : "getPassagesByDate?date=" + selectedDate, // Envoyer la date au serveur
            success : function(result) {
                if (result.status === "success") {
                    $('#getResultDiv').empty();

                    $.each(result.data, function(i, passage) {
                        var dateObj = new Date(passage.date);
                        var year = dateObj.getFullYear();
                        var month = dateObj.getMonth() + 1;
                        var day = dateObj.getDate();
                        var formattedDate = day + "/" + month + "/" + year;

                        console.log("Success: ", passage);
                        var tableRowHtml = "<tr>"
                            + "<td style='cursor:pointer;' data-field-name='name' class='editable-field'>"+ passage.name+"</td>"
                            + "<td style='cursor:pointer;' data-field-name='amountPlus' class='editable-field'>"+ (passage.amountPlus !== "null" ? passage.amountPlus : "")+"</td>"
                            + "<td style='cursor:pointer;' data-field-name='amountMinus' class='editable-field'>"+ (passage.amountMinus !== "null" ? passage.amountMinus : "")+"</td>"
                            + "<td><i class=\"fa-regular fa-pen-to-square save-icon\"></i></td>"
                            +"</tr>";
                        $('#getResultDiv').append(tableRowHtml);
                    });

                } else {
                    $("#getResultDiv").html("<strong>No data found for this date</strong>");
                    console.log("Fail: ", result.status);
                }
            },
            error : function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }
});
