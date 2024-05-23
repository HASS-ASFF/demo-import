
// This script handles the retrieval of ESG data by date.
// It triggers a GET request when the "Get ESG by Date" button is clicked,
// retrieves the data, and dynamically populates tables for TFR and CAF parts.

$(document).ready(function() {

    // GET REQUEST BY DATE
    $("#getEsgByDate").click(function() {
        var selectedDate = $("#dateBilan").val(); // Récupérer la date sélectionnée
        ajaxGetByDate(selectedDate);
    });

    // DO GET BY DATE
    function ajaxGetByDate(selectedDate) {
        $.ajax({
            type: "GET",
            url: "passagesESGFilter?date=" + selectedDate,
            success: function(result) {
                if (result.status === "success") {

                    $('#getResultTFR').empty();
                    $('#getResultCAF').empty();

                    var data = result.dataTFR;

                    // Structure des parties
                    var partsTFR = [
                        { title: "MARGES BRUTES VENTES EN ETAT", id: "part1",total:result.total1},
                        { title: "PRODUCTION DE L'EXERCICE (3+4+5)", id: "part2",total:result.total2 },
                        { title: "CONSOMMATIONS DE L'EXERCICE (6+7)", id: "part3",total:result.total3 },
                        { title: "VALEUR AJOUTEE (I+II+III)", id: "part4",total:result.total4 },
                        { title: "EXCEDENT BRUT D'EXPLOITATION (EBE)", id: "part5",total:result.total5 },
                    ];

                    // Boucle sur les parties TFR
                    for (var i = 0; i < partsTFR.length; i++) {
                        var part = partsTFR[i];
                        var partData = data[i];
                        var partHtmlTFR = '<tr><th>' + part.title + '</th><th></th><th class=\'text-right\'>'+parseFloat(part.total).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+'</th><th></th></tr>';

                        // Construction de HTML pour chaque passage dans la partie
                        partData.forEach(function(esg) {
                            const formattedExercice = parseFloat(esg.exercice).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            const formattedExerciceP = parseFloat(esg.exerciceP).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            partHtmlTFR += "<tr>"
                                + "<td></td>"
                                + "<td data-field-name='name'>" + esg.name + "</td>"
                                + "<td class='text-right' data-field-name='exercice'>" + formattedExercice + "</td>"
                                + "<td class='text-right' data-field-name='exerciceP'>" + formattedExerciceP + "</td>"
                                + "</tr>";
                        });

                        $('#getResultTFR').append(partHtmlTFR);
                    }

                    partHtmlTFR = "<tr><td>IV</td>" +
                        +"<td></td>"
                        +"<td>RESULTAT FINANCIER</td>"
                        +"<td class='text-right' >"+parseFloat(result.RF).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+"</td>"
                        +"<td></td>"

                        +"</tr><tr>"
                        +"<td>V</td>"
                        +"<td>RESULTAT COURANT</td>"
                        +"<td class='text-right'>"+parseFloat(result.RC).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+"</td>"
                        +"<td></td>"

                        +"</tr><tr>"
                        +"<td>VI</td>"
                        +"<td>RESULTAT NON COURANT</td>"
                        +"<td class='text-right'>"+parseFloat(result.RNC).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+"</td>"
                        +"<td></td>"

                        +"</tr><tr>"
                        +"<td>VII</td>"
                        +"<td>Impôt sur les résultats</td>"
                        +"<td class='text-right'>"+parseFloat(result.IR).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+"</td>"
                        +"<td></td>"

                        +"</tr><tr>"
                        +"<td>VIII</td>"
                        +"<td>RESULTAT NET DE L'EXERCICE</td>"
                        +"<td class='text-right'>"+parseFloat(result.RN).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+"</td>"
                        +"<td></td>"
                        +"</tr>"

                    $('#getResultTFR').append(partHtmlTFR);

                    var dataCAF = result.dataCAF;

                    var partsCAF = [
                        {title:"1. Résultat net de l'exercice",id:"part1"},
                        {title:"",id:"part2"}
                    ];

                    // Boucle sur les parties CAF
                    for (var i = 0; i < partsCAF.length; i++) {
                        var part = partsCAF[i];
                        var partData = dataCAF[i];
                        var partHtmlCAF = '<tr><th>' + part.title + '</th><th></th><th></th><th></th></tr>';

                        // Construction de HTML pour chaque passage dans la partie
                        partData.forEach(function(esg) {
                            const formattedExercice = parseFloat(esg.exercice).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            const formattedExerciceP = parseFloat(esg.exerciceP).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ');
                            partHtmlCAF += "<tr>"
                                + "<td></td>"
                                + "<td data-field-name='name'>" + esg.name + "</td>"
                                + "<td class='text-right' data-field-name='exercice'>" + formattedExercice + "</td>"
                                + "<td class='text-right' data-field-name='exerciceP'>" + formattedExerciceP + "</td>"
                                + "</tr>";
                        });
                        $('#getResultCAF').append(partHtmlCAF)
                    }
                    partHtmlCAF = "<tr><td>I</td>" +
                        +"<td></td>"
                        +"<td>CAPACITE D'AUTOFINANCEMENT (C.A.F.)</td>"
                        +"<td class='text-right'>"+parseFloat(result.totalCAF).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+"</td>"
                        +"<td></td>"

                        +"</tr><tr>"
                        +"<td></td>"
                        +"<td>Distributions de bénéfices</td>"
                        +"<td></td>"
                        +"<td></td>"

                        +"</tr><tr>"
                        +"<td>II</td>"
                        +"<td>AUTOFINANCEMENT</td>"
                        +"<td class='text-right'>"+parseFloat(result.totalCAF).toLocaleString('en-US', { style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2 }).replaceAll(',', ' ')+"</td>"
                        +"<td></td>"
                        +"</tr>";

                    $('#getResultCAF').append(partHtmlCAF)


                } else {
                    $("#getResultTFR").html("<strong>No data found for this date</strong>");

                    console.log("EMPTY (NO DATA): ", result.status);
                }
            },
            error: function(e) {
                $("#getResultTFR").html("<strong>Error</strong>");

                console.log("ERROR: ", e);
            }
        });
    }
});
