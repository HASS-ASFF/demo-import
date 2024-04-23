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
                            partHtml += "<tr data-id=\"" + immobilisation.id + "\">" +
                                "<td data-field-name='name'>" + immobilisation.name + "</td>" +
                                "<td data-field-name='dateAquisition' class='editable-field'>" + immobilisation.dateAquisition + "</td>" +
                                "<td data-field-name='prixAquisition' class='editable-field'>" + immobilisation.prixAquisition + "</td>" +
                                "<td data-field-name='coutDeRevient' class='editable-field'>" + immobilisation.coutDeRevient + "</td>" +
                                "<td data-field-name='amortAnterieur' class='editable-field'>" + immobilisation.amortAnterieur + "</td>" +
                                "<td data-field-name='taux_amort' class='editable-field'>" + immobilisation.taux_amort + "</td>" +
                                "<td data-field-name='amortDeduitBenefice' class='editable-field'>" + immobilisation.amortDeduitBenefice + "</td>" +
                                "<td data-field-name='dea' class='editable-field'>" + immobilisation.dea + "</td>" +
                                "<td data-field-name='deaGlobal' class='editable-field'>" + immobilisation.deaGlobal + "</td>" +
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
