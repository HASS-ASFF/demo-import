
    // Fonction pour générer les options du datalist avec les dates du 31 décembre
    function generateDatesList() {
    var datalist = document.getElementById("datesList");
    for (var year = 2000; year <= 2025; year++) {
    var option = document.createElement("option");
    // Formatage de la date au format DD/MM/AAAA
    var formattedDate = year + "-12-31";
    option.value = formattedDate;
    datalist.appendChild(option);
}
}

    // Appel de la fonction au chargement de la page
    window.onload = generateDatesList;
