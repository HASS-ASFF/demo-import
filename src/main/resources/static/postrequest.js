$(document).ready(function() {
    // Gestion du clic sur l'icône de sauvegarde
    $(document).on('click', '.save-icon', function(event) {
        event.stopPropagation();
        $('#dateModal').modal('show'); // Afficher la boîte de dialogue modale pour la date
        var row = $(this).closest('tr');
        var name = row.find('[data-field-name="name"]').text().trim();
        var amountPlus = row.find('[data-field-name="amountPlus"]').text().trim();
        var amountMinus = row.find('[data-field-name="amountMinus"]').text().trim();

        $('#confirmDate').off('click').on('click', function() {
            var selectedDate = $('#inputDate').val();
            $('#dateModal').modal('hide'); // Fermer la boîte de dialogue modale pour la date

            var data = {
                name: name,
                amountPlus: amountPlus,
                amountMinus: amountMinus,
                date: selectedDate
            };

            $.ajax({
                type: 'POST',
                url: 'savePassage',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(response) {
                    // Gérer la réponse de votre contrôleur ici
                },
                error: function(xhr, status, error) {
                    // Gérer les erreurs ici
                }
            });
        });
    });
});