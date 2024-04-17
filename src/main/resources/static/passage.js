$(document).ready(function() {

    // GET REQUEST BY DATE
    $("#getPassagesByDate").click(function() {
        var selectedDate = $("#dateBilan").val(); // Récupérer la date sélectionnée
        ajaxGetByDate(selectedDate);
    });

    // Gestionnaire d'événements pour le clic sur le bouton "Confirmer"
    $('#confirmDate').click(function() {
        // Récupérer la date sélectionnée dans le modal
        var selectedDate = $('#inputDate').val();
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

                        // Construction de HTML pour chaque passage dans la partie
                        partData.forEach(function(passage) {
                            if(passage.amountPlus == null){passage.amountPlus = '';}
                            if(passage.amountMinus == null){ passage.amountMinus = '';}
                            partHtml += "<tr>"
                                + "<td data-field-name='name'>" + passage.name + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='amountPlus' class='editable-field'>" + passage.amountPlus + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='amountMinus' class='editable-field'>" + passage.amountMinus + "</td>"
                                + "<td><i class='fa-regular fa-pen-to-square save-icon'></i></td>"
                                + "</tr>";
                        });
                        // Ajout de HTML pour la partie spécifiée
                        $('#getResultDiv').append(partHtml);
                    }


                } else {
                    $("#getResultDiv").html("<strong>No data found for this date</strong>");
                    console.log("Fail: ", result.status);
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

        // Vérifier si le champ de date est vide
        if ($('#dateBilan').val().trim() === '') {
            $('#dateModal').modal('show'); // Afficher le modal seulement si le champ de date est vide
        } else {
            // Le champ de date est déjà rempli, procéder à l'envoi des données sans afficher le modal
            var selectedDate = $('#dateBilan').val();
            savePassageWithoutDateModal(selectedDate);
        }

        // Fonction pour sauvegarder le passage sans ouvrir le modal de date
        function savePassageWithoutDateModal(selectedDate) {
            var row = $(this).closest('tr');
            var name = row.find('[data-field-name="name"]').text().trim();
            var amountPlus = row.find('[data-field-name="amountPlus"]').text().trim();
            var amountMinus = row.find('[data-field-name="amountMinus"]').text().trim();

            var data = {
                name: name,
                amountPlus: amountPlus,
                amountMinus: amountMinus,
                date: selectedDate
            };

            $.ajax({
                type: 'POST',
                url: 'savePassage?date=' + selectedDate,
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(response) {
                    alert("passage added successfully!");
                },
                error: function(xhr, status, error) {
                    alert("Please retry!");
                }
            });
        }

        // Gestionnaire d'événements pour le bouton "Confirmer" du modal
        $('#confirmDate').off('click').on('click', function() {
            var selectedDate = $('#inputDate').val();
            $('#dateModal').modal('hide'); // Fermer la boîte de dialogue modale pour la date

            savePassageWithoutDateModal(selectedDate);
        });
    });



});
