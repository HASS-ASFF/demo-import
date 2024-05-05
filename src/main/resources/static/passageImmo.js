$(document).ready(function() {

    // GET REQUEST BY DATE
    $("#getPassagesByDate").click(function(event) {
        event.preventDefault();
        var selectedDate = $("#dateBilan").val(); // Récupérer la date sélectionnée
        ajaxGetByDate(selectedDate);
    });

    // DO GET BY DATE
    function ajaxGetByDate(selectedDate) {
        $.ajax({
            type: "GET",
            url: "passagesImmoFilter?date=" + selectedDate,
            success: function(result) {
                if (result.status === "success") {
                    $('#getResultDiv').empty();
                    var data = result.data;

                    // Structure des parties
                    var parts = [
                        { title: "I. IMMOBILISATION EN NON-VALEURS", id: "part1" },
                        { title: "II. IMMOBILISATIONS INCORPORELLES", id: "part2" },
                        { title: "III. IMMOBILISATIONS CORPORELLES", id: "part3" }
                    ];

                    // Boucle sur les parties
                    for (var i = 0; i < parts.length; i++) {
                        var part = parts[i];
                        var partData = data[i];
                        var partHtml = '<tr><th>' + part.title + '</th><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr>';

                        // Construction de HTML pour chaque passage dans la partie
                        partData.forEach(function(passage) {
                            partHtml += "<tr>"
                                + "<td  data-field-name='name'>" + passage.libelle + "</td>"
                                + "<td class='text-right' data-field-name='brut' >" + parseFloat(passage.brut).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                + "<td class='text-right' data-field-name='acquisition' >" + parseFloat(passage.acquisition).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                + "<td class='text-right' data-field-name='ppe' >" + parseFloat(passage.ppe).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                + "<td class='text-right' data-field-name='virement' >" + parseFloat(passage.virement).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+ "</td>"
                                + "<td class='text-right' data-field-name='cession' >" + parseFloat(passage.cession).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+ "</td>"
                                + "<td class='text-right' data-field-name='retrait'>" + parseFloat(passage.retraitMinus).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+ "</td>"
                                + "<td class='text-right' data-field-name='virementM' >" + parseFloat(passage.virementMinus).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                + "<td class='text-right' data-field-name='brutE'>" + parseFloat(passage.brut).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+ "</td>"
                                + "<td><i class='fa-regular fa-pen-to-square save-icon'></i></td>"
                                + "</tr>";
                        });
                        // Ajout de HTML pour la partie spécifiée
                        $('#getResultDiv').append(partHtml);
                    }


                } else {
                    $("#getResultDiv").html("<strong>No data found for this date</strong>");
                    console.log("EMPTY (NO DATA): ", result.status);
                }
            },
            error: function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }

});
