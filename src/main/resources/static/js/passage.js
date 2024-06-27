
// This script handles the retrieval and editing of passage data based on the selected date.
// It triggers a GET request to fetch passage data for the specified date and populates the HTML table.
// The script also allows inline editing of fields, such as price, cost, and depreciation.
// When the "save" icon is clicked, the edited data is sent via a PUT request to update the passage record in the database.

$(document).ready(function() {

    // GET REQUEST BY DATE
    $("#getPassagesByDate").click(function() {
        var selectedDate = $("#dateBilan").val(); // Récupérer la date sélectionnée
        ajaxGetByDate(selectedDate);
    });


    // DO GET BY DATE
    function ajaxGetByDate(selectedDate) {
        $.ajax({
            type: "GET",
            url: "passagesFilter?date=" + selectedDate,
            success: function(result) {
                if (result.status === "success") {
                    $('#getResultDiv').empty();
                    var data = result.data;
                    // Structure des parties
                    var parts = [
                        { title: "I. RESULTAT NET COMPTABLE", id: "part1" },
                        { title: "II. REINTEGRATIONS FISCALES", id: "part2" },
                        { title: "III. DEDUCTIONS FISCALES", id: "part3" },
                        { title: "IV. RESULTAT BRUT FISCAL", id: "part4" },
                        { title: "V. REPORTS DEFICITAIRES IMPUTES (C) (1)", id: "part5" },
                        { title: "VI. RESULTAT NET FISCAL", id: "part6" },
                        { title: "VII. CUMUL DES AMORTISSEMENTS FISCALEMENT DIFFERES", id: "part7" },
                        { title: "VIII. CUMUL DES DEFICITS FISCAUX RESTANT A REPORTER", id: "part8" }
                    ];

                    // Boucle sur les parties
                    for (var i = 0; i < parts.length; i++) {
                        var part = parts[i];
                        var partData = data[i];
                        var partHtml = '<tr><th>' + part.title + '</th><th></th><th></th><th></th></tr>';
                        var total_amountplus = 0.0;
                        var total_amountminus = 0.0;

                        // Construction de HTML pour chaque passage dans la partie
                        partData.forEach(function(passage) {
                            total_amountplus += passage.amountPlus;
                            total_amountminus += passage.amountMinus;

                            if(part.id === "part1"){
                                partHtml += "<tr>"
                                    + "<td data-field-name='name'>" + passage.name + "</td>"
                                    + "<td  data-field-name='amountPlus' class='text-right'>" + parseFloat(passage.amountPlus).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                    + "<td  data-field-name='amountMinus' class='text-right'>" + parseFloat(passage.amountMinus).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                    + "<td></td>"
                                    + "</tr>";
                            }
                            else{
                                partHtml += "<tr>"
                                    + "<td data-field-name='name'>" + passage.name + "</td>"
                                    + "<td style='cursor:pointer;' data-field-name='amountPlus' class='text-right editable-field'>" + parseFloat(passage.amountPlus).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                    + "<td style='cursor:pointer;' data-field-name='amountMinus' class='text-right editable-field'>" + parseFloat(passage.amountMinus).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>"
                                    + "<td><i class='fa-regular fa-pen-to-square save-icon'></i></td>"
                                    + "</tr>";
                            }

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

    $(document).on('click', '.save-icon', function(event) {
        event.stopPropagation();
        var date = $("#dateBilan").val();
        var row = $(this).closest('tr');
        savePassageWithoutDateModal(date,row);

        // Fonction pour sauvegarder le passage sans ouvrir le modal de date
        function savePassageWithoutDateModal(date,row) {
            var name = row.find('[data-field-name="name"]').text().trim();
            var amountPlus = row.find('[data-field-name="amountPlus"]').text().trim();
            var amountMinus = row.find('[data-field-name="amountMinus"]').text().trim();

            var data = {
                name : name,
                amountPlus: amountPlus,
                amountMinus: amountMinus,
                date : date
            };

            $.ajax({
                type: 'PUT',
                url: 'updatePassage/' + name +'/' + date,
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(response) {
                    alert("passage updated successfully!");
                },
                error: function(xhr, status, error) {
                    alert("Please retry!");
                }
            });
        }
    });

    // TO DO (VALIDATION)

});
