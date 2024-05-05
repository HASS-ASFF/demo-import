
// This function generates a list of options for a datalist with dates set to December 31st for the years 2000 to 2025.
// It creates an <option> element for each year and appends it to the datalist element specified by the ID "datesList".
// The date format used is YYYY-MM-DD.
// This function is called when the window is loaded.


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
