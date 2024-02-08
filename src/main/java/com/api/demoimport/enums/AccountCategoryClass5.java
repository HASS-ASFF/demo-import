package com.api.demoimport.enums;

public enum AccountCategoryClass5 {
    CLASSE_5("Classe 5", ""),
    TRESORERIE_ACTIF("51", "Trésorerie - Actif"),
    CHEQUES_VALEURS_ENCAISSER("511", "Chèques et valeurs à encaisser"),
    CHEQUES_ENCAISSER("5111", "Chèques à encaisser ou à l'encaissement"),
    CHEQUES_PORTFOLIO("51111", "Chèques en portefeuille"),
    CHEQUES_ENCAISSEMENT("51112", "Chèques à l'encaissement"),
    EFFETS_ENCAISSER("5113", "Effets à encaisser ou à l'encaissement"),
    EFFETS_ECHUS_ENCAISSER("51131", "Effets échus à encaisser"),
    EFFETS_ENCAISSEMENT2("51132", "Effets à l'encaissement"),
    VIREMENT_FONDS("5115", "Virement de fonds"),
    AUTRES_VALEURS_ENCAISSER("5118", "Autres valeurs à encaisser"),
    BANQUES_TRESORERIE_CHEQUES_DEBITEURS("514", "Banques, Trésorerie Générale et chèques postaux débiteurs"),
    BANQUES_DEBITEURS("5141", "Banques (solde débiteur)"),
    TRESORERIE_GENERALE("5143", "Trésorerie Générale"),
    CHEQUES_POSTAUX("5146", "Chèques postaux"),
    AUTRES_ETABLISSEMENTS_FINANCIERS_DEBITEURS("5148", "Autres établissements financiers et assimilés (soldes débiteurs)"),
    CAISSES_REGIES_AVANCES_ACCREDITIFS("516", "Caisses, régies d'avances et accréditifs"),
    CAISSES("5161", "Caisses"),
    CAISSE_CENTRALE("51611", "Caisse Centrale"),
    CAISSE_SUCCURSALE_A("51613", "Caisse (succursale ou agence A)"),
    CAISSE_SUCCURSALE_B("51614", "Caisse (succursale ou agence B)"),
    REGIES_AVANCES_ACCREDITIFS("5165", "Régies d'avances et accréditifs");

    private final String number;
    private final String label;

    AccountCategoryClass5(String number, String label) {
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

