$(document).ready(function() {
    // Gestion du clic sur le bouton d'importation Excel
    $('#importExcelBtn').click(function() {
        // Charger les options de date dans la liste déroulante
        generateDatesList();

        // Ouvrir le modal de sélection de date
        $('#datePickerModal').modal('show');
    });

    // Gestion de l'événement quand le modal de sélection de date est affiché
    $('#datePickerModal').on('shown.bs.modal', function () {
        // Charger les options de date dans la liste déroulante
        generateDatesList();
    });

    // Gestion du clic sur le bouton "Uploader et Sauvegarder" dans le modal de sélection de date
    $('#uploadAndSaveBtn').click(function() {
        var selectedDate = $('#selectedDateInput').val();
        var file = $('#excelFileInput')[0].files[0];

        // Vérifier si une date et un fichier ont été sélectionnés
        if (selectedDate && file) {
            var formData = new FormData();
            formData.append('file', file);
            formData.append('dateBalance', selectedDate);
            formData.append('companyName', 'AL MORAFIQ'); // Remplacez 'Nom de la compagnie' par la vraie valeur

            // Requête AJAX POST pour uploader le fichier et sauvegarder les données
            $.ajax({
                url: '/api/excel/upload-immobilisation',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    $('#successMessage').text(response.message);
                    $('#successModal').modal('show'); // Afficher la boîte de dialogue modale de succès
                },
                error: function(xhr, status, error) {
                    var errorMessage = xhr.responseJSON.message;
                    alert(errorMessage); // Afficher le message d'erreur
                }
            });

            // Fermer le modal après avoir effectué l'action
            $('#datePickerModal').modal('hide');
        } else {
            alert('Veuillez sélectionner une date et un fichier !');
        }
    });
});

// Fonction pour générer les options du datalist avec les dates du 31 décembre
function generateDatesList() {
    var datalist = document.getElementById("datesListModal");
    datalist.innerHTML = "";

    for (var year = 2000; year <= 2025; year++) {
        var option = document.createElement("option");
        // Formatage de la date au format DD/MM/AAAA
        var formattedDate = year + "-12-31";
        option.value = formattedDate;
        datalist.appendChild(option);
    }
}