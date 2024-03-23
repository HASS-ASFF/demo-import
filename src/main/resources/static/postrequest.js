$(document).ready(function() {

    // Gestion du clic sur l'icône de sauvegarde
    $(document).on('click', '.save-icon', function(event) {
        event.stopPropagation();
        var row = $(this).closest('tr');
        var name = row.find('[data-field-name="name"]').text().trim();
        var amountPlus = row.find('[data-field-name="amountPlus"]').text().trim();
        var amountMinus = row.find('[data-field-name="amountMinus"]').text().trim();

        var data = {
            name: name,
            amountPlus: amountPlus,
            amountMinus: amountMinus
        };

        $.ajax({
            type: 'POST',
            url: '/savePassage', // URL de votre endpoint Spring
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

