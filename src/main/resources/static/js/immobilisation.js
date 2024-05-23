
// This script handles the retrieval and editing of immobilisation data based on the selected date.
// It triggers a GET request to fetch immobilisation data for the specified date and populates the HTML table.
// The script also allows inline editing of fields, such as price, cost, and depreciation.
// When the "save" icon is clicked, the edited data is sent via a PUT request to update the immobilisation record in the database.

$(document).ready(function() {

    // GET REQUEST BY DATE
    $("#getImmobilisationByDate").click(function() {
        var selectedDate = $("#dateBilan").val(); // Récupérer la date sélectionnée
        ajaxGetByDate(selectedDate);
    });

    // DO GET BY DATE
    function ajaxGetByDate(selectedDate) {
        $.ajax({
            type: "GET",
            url: "immobilisationFilter?date=" + selectedDate,
            success: function(result) {
                if (result.status === "success") {
                    $('#getResultDiv').empty();
                    var data = result.data;
                    var partHtml ='';
                    // Construction de HTML pour chaque passage dans la partie
                    data.forEach(function(immobilisation) {
                        // Formater la date
                        const dateAquisition = new Date(immobilisation.dateAquisition);
                        const formattedDateAquisition = dateAquisition.toLocaleDateString('en-US');

                            partHtml += "<tr data-id=\"" + immobilisation.id + "\">" +
                                "<td data-field-name='name'>" + immobilisation.name + "</td>" +
                                "<td data-field-name='dateAquisition'>" + formattedDateAquisition + "</td>" +
                                "<td class='text-right editable-field' data-field-name='prixAquisition'>" +  parseFloat(immobilisation.prixAquisition).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>" +
                                "<td class='text-right editable-field' data-field-name='coutDeRevient'>" + parseFloat(immobilisation.coutDeRevient).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>" +
                                "<td class='text-right editable-field' data-field-name='amortAnterieur'>" + parseFloat(immobilisation.amortAnterieur).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>" +
                                "<td class='text-right editable-field' data-field-name='taux_amort'>" + immobilisation.taux_amort + "</td>" +
                                "<td class='text-right editable-field' data-field-name='amortDeduitBenefice'>" + parseFloat(immobilisation.amortDeduitBenefice).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+ "</td>" +
                                "<td class='text-right editable-field' data-field-name='dea' >" + parseFloat(immobilisation.dea).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>" +
                                "<td class='text-right editable-field' data-field-name='deaGlobal'>" + parseFloat(immobilisation.deaGlobal).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ') + "</td>" +
                                "<td data-field-name='name'>" + immobilisation.name + "</td>" +
                                "<td><i class='fa-regular fa-pen-to-square save-icon'></i></td>"+
                                "</tr>";
                        });
                        // Ajout de HTML pour la partie spécifiée
                        $('#getResultDiv').append(partHtml);

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
        var id = $(this).closest('tr').data('id');
        var row = $(this).closest('tr');
        savePassageWithoutDateModal(id,row);

        // Fonction pour sauvegarder le passage sans ouvrir le modal de date
        function savePassageWithoutDateModal(id,row) {
            var dateAquisition = row.find('[data-field-name="dateAquisition"]').text().trim();
            var prixAquisition = row.find('[data-field-name="prixAquisition"]').text().trim();
            var coutDeRevient = row.find('[data-field-name="coutDeRevient"]').text().trim();
            var amortAnterieur = row.find('[data-field-name="amortAnterieur"]').text().trim();
            var taux_amort = row.find('[data-field-name="taux_amort"]').text().trim();
            var amortDeduitBenefice = row.find('[data-field-name="amortDeduitBenefice"]').text().trim();
            var dea = row.find('[data-field-name="dea"]').text().trim();
            var deaGlobal = row.find('[data-field-name="deaGlobal"]').text().trim();

            var data = {
                dateAquisition: dateAquisition,
                prixAquisition: prixAquisition,
                coutDeRevient: coutDeRevient,
                amortAnterieur: amortAnterieur,
                taux_amort: taux_amort,
                amortDeduitBenefice: amortDeduitBenefice,
                dea: dea,
                deaGlobal: deaGlobal

            };

            console.log(data);

            $.ajax({
                type: 'PUT',
                url: 'updateImmobilisation/' + id,
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(response) {
                    alert("Immobilisation updated successfully!");
                },
                error: function(xhr, status, error) {
                    alert("Please retry!");
                }
            });
        }
    });

    // TO DO (VALIDATION)

});
