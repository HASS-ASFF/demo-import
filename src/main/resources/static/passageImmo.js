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
                            if(passage.brut == null){passage.brut = '';}
                            if(passage.acquisition == null){ passage.acquisition = '';}
                            if(passage.ppe == null){ passage.ppe = '';}
                            if(passage.virement == null){ passage.virement = '';}
                            if(passage.cession == null){ passage.cession = '';}
                            if(passage.retraitMinus == null){ passage.retraitMinus = '';}
                            if(passage.virementMinus == null){ passage.virementMinus = '';}
                            partHtml += "<tr>"
                                + "<td data-field-name='name'>" + passage.libelle + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='brut' class='editable-field'>" + passage.brut + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='acquisition' class='editable-field'>" + passage.acquisition + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='ppe' class='editable-field'>" + passage.ppe + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='virement' class='editable-field'>" + passage.virement + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='cession' class='editable-field'>" + passage.cession + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='retrait' class='editable-field'>" + passage.retraitMinus + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='virementM' class='editable-field'>" + passage.virementMinus + "</td>"
                                + "<td style='cursor:pointer;' data-field-name='brutE'>" + passage.brut + "</td>"
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

    // Gestionnaire d'événements pour le clic sur le bouton "Confirmer"
    $('#confirmDate').click(function() {
        // Récupérer la date sélectionnée dans le modal
        var selectedDate = $('#inputDate').val();

        // Vérifier si une date est sélectionnée
        if (selectedDate) {
            // Effectuer ici l'appel AJAX pour récupérer les passages en fonction de la date sélectionnée

            $.ajax({
                url: 'passagesFilter?date=' + selectedDate, // URL de l'endpoint à appeler
                type: 'GET',
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
                error: function(xhr, status, error) {
                    // Gérer les erreurs ici
                    console.error(error);
                }
            });
        } else {
            console.log("Veuillez sélectionner une date.");
        }
    });

});
