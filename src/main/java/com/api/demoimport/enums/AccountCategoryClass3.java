package com.api.demoimport.enums;

public enum AccountCategoryClass3 {
    CLASSE_3("Classe 3", ""),
    STOCKS("31", "Stocks (F)"),
    CREANCES_ACTIF_CIRCULANT("34", "Créances de l'actif circulant (G)"),
    TITRES_VALEURS_PLACEMENT("35", "Titres et valeurs de placement (H)"),
    ECART_CONVERSION_ACTIF("37", "Ecart de conversion - ACTIF (element circulant)");

    private final String number;
    private final String label;

    AccountCategoryClass3(String number, String label) {
        this.number = number;
        this.label = label;
    }

    public String getNumber() {
        return number;
    }

    public String getLabel() {
        return label;
    }
}
