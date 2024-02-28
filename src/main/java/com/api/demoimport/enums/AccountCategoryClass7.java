package com.api.demoimport.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum AccountCategoryClass7 {

    PRODUITS_DEXPLOITATION("71","PRODUITS D’EXPLOITATION",
            Arrays.asList(
                "711 - Ventes de marchandises",
                "712 - Ventes de biens et services produits",
                "713 - Variation des stocks de produits",
                "714 - Immobilisations produites par l’entreprise pour elle même",
                "716 - Subventions d’exploitation",
                "718 - Autres produits d’exploitation",
                "719 - Reprise d’exploitation, transferts de charges"
            )),
    PRODUITS_FINANCIERS("73","PRODUITS FINANCIERS"
            ,Arrays.asList(
                "732 - Produits des titres de participation et des autres titres immobilisés",
                "733 - Gains de change",
                "738 - Intérêts et autres produits financiers",
                "739 - Reprises financières, transferts de charges"
    )),
    PRODUITS_NON_COURANTS("75","PRODUITS NON COURANTS"
            ,Arrays.asList(
                "751 - Produits des cessions d’immobilisations",
                "756 - Subventions d’équilibre",
                "757 - Reprise sur subventions d’investissements",
                "758 - Autres produits non courants"
    ));

    @Setter
    private  String number;

    @Setter
    private  String label;

    @Setter
    private List<String> sous_compte;

    AccountCategoryClass7(String number, String label, List<String> sous_compte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sous_compte;
    }
}
