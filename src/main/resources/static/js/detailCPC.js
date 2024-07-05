
// This script handles the retrieval of Detail CPC data by date.
// It triggers a GET request when the "Get Detail CPC  by Date" button is clicked,
// retrieves the data, and dynamically populates tables different parts.


$(document).ready(function() {

    // GET REQUEST BY DATE
    $("#getDetailCPCByDate").click(function() {
        var selectedDate = $("#dateBilan").val(); // Récupérer la date sélectionnée

        // Verify date format ("YYYY-MM-DD")
        if (!selectedDate.match(/^\d{4}-\d{2}-\d{2}$/)) {
            swal("Erreur", "Veuillez entrer une date au format YYYY-MM-DD.", "error");
            return;
        }

        ajaxGetByDate(selectedDate);
    });

    // DO GET BY DATE
    function ajaxGetByDate(selectedDate) {
        $.ajax({
            type: "GET",
            url: "passagesCPCFilter?date=" + selectedDate,
            success: function(result) {
                if (result.status === "success") {
                    $('#getResultDiv').empty();

                    var dataSix = result.dataCPCSix;
                    var dataSeven = result.dataCPCSeven;

                    // Verify data six & seven
                    if (dataSix == null && dataSeven == null) {
                        swal("Aucune donnée", "Aucune donnée trouvée pour cette date.", "warning");
                        return;
                    }

                    // Structure des parties class six
                    var partsCPCSix = [
                        { title: "Achats revendus de marchandises", id: "part1",total:result.total1},
                        { title: "Achats consommés de matières et fournitures", id: "part2",total:result.total2 },
                        { title: "Autres charges externes", id: "part3",total:result.total3 },
                        { title: "Charges de personnel", id: "part4",total:result.total4 },
                        { title: "Autres charges d'exploitation", id: "part5",total:result.total5 },
                        { title: "Autres charges financières", id: "part6",total:result.total6 },
                        { title: "Autres charges non courantes", id: "part7",total:result.total7 }
                    ];

                    // Structure des parties class sept
                    var partsCPCSeven = [
                        { title: "Ventes de marchandises", id: "part8",total:result.total8},
                        { title: "Ventes de biens et services produits", id: "part9",total:result.total9 },
                        { title: "Variation des stocks de produits", id: "part10",total:result.total10 },
                        { title: "Autres produits d'exploitation", id: "part11",total:result.total11 },
                        { title: "Reprises d'exploitation transferts de charges", id: "part12",total:result.total12 },
                        { title: "Intérêts et produits assimilésl", id: "part13",total:result.total13 }
                    ];

                    // Boucle sur les parties Class Six
                    for (var i = 0; i < partsCPCSix.length; i++) {
                        var part = partsCPCSix[i];
                        var partData = dataSix[i];
                        var partHtml = '<tr><th>' + part.title + '</th><th></th><th class=\'text-right\'>'+parseFloat(part.total).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+'</th><th></th></tr>';

                        // Construction de HTML pour chaque passage dans la partie
                        partData.forEach(function(detailCPC) {
                            const formattedExercice = parseFloat(detailCPC.exercice).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            const formattedExerciceP = parseFloat(detailCPC.exerciceP).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            partHtml += "<tr>"
                                + "<td data-field-name='poste'>" + detailCPC.poste + "</td>"
                                + "<td data-field-name='name'>" + detailCPC.name + "</td>"
                                + "<td class='text-right' data-field-name='exercice'>" + formattedExercice + "</td>"
                                + "<td class='text-right' data-field-name='exerciceP'>" + formattedExerciceP + "</td>"
                                + "</tr>";
                        });

                        $('#getResultDiv').append(partHtml);
                    }

                    // Boucle sur les parties Class Seven
                    for (var i = 0; i < partsCPCSeven.length; i++) {
                        var part = partsCPCSeven[i];
                        var partData = dataSeven[i];
                        var partHtml = '<tr><th>' + part.title + '</th><th></th><th class=\'text-right\'>'+parseFloat(part.total).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+'</th><th></th></tr>';

                        // Construction de HTML pour chaque passage dans la partie
                        partData.forEach(function(detailCPC) {
                            const formattedExercice = parseFloat(detailCPC.exercice).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            const formattedExerciceP = parseFloat(detailCPC.exerciceP).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            partHtml += "<tr>"
                                + "<td data-field-name='poste'>" + detailCPC.poste + "</td>"
                                + "<td data-field-name='name'>" + detailCPC.name + "</td>"
                                + "<td class='text-right' data-field-name='exercice'>" + formattedExercice + "</td>"
                                + "<td class='text-right' data-field-name='exerciceP'>" + formattedExerciceP + "</td>"
                                + "</tr>";
                        });

                        $('#getResultDiv').append(partHtml);
                    }


                } else {
                    swal("Aucune donnée", "Aucune donnée trouvée pour cette date.", "warning");
                    console.log("EMPTY (NO DATA): ", result.status);
                }
            },
            error: function(e) {
                swal("Erreur", "Une erreur s'est produite lors de la récupération des données.", "error");
                console.log("ERROR: ", e);
            }
        });
    }
});