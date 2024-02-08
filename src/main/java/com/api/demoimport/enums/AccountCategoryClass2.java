package com.api.demoimport.enums;

public enum AccountCategoryClass2 {
    CLASSE_2("Classe 2", ""),
    IMMOBILISATION_NON_VALEURS("21", "IMMOBILISATION en non-valeurs (A)"),
    IMMOBILISATION_INCORPORELLES("22", "IMMOBILISATION incorporelles (B)"),
    IMMOBILISATION_CORPORELLES("23", "IMMOBILISATION corporelles (C)"),
    IMMOBILISATION_FINANCIERES("24", "IMMOBILISATION financières (D)"),
    ECART_CONVERSION_ACTIF("27", "ECART de conversion actif (E)");

    private final String number;
    private final String label;

    AccountCategoryClass2(String number, String label) {
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
