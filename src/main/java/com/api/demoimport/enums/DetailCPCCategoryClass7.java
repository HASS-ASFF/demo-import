package com.api.demoimport.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum DetailCPCCategoryClass7 {

    VENTES_MARCHANDISES("711","Ventes de marchandises",
            Arrays.asList(
                    "7111 - Ventes de marchandises au Maroc",
                    "7113 - Ventes de biens à l'étranger",
                    "7118/7119 - Reste du poste des ventes de marchandises"
            )),
    VENTES_BIENS_SERVICES("712","Ventes de biens et services produits"
            ,Arrays.asList(
            "7121 - Ventes de biens produits au Maroc",
            "7122 - Ventes de biens à l'étranger",
            "7124 - Ventes des services au Maroc",
            "7125 - Ventes des services à l'étranger",
            "7126 - Redevances pour brevets, marques, droits..",
            "7127/7128/7129 - Reste du poste des ventes et services produits"
    )),
    VAR_STOCK_PRODUITS("713","Variation des stocks de produits"
            ,Arrays.asList(
            "7132 - Variation des stocks des biens produits (+/-)",
            "7134 - Variation des stocks des services produits (+/-)",
            "7131 - Variation des stocks des produits en cours (+/-)"
    )),
    AUTRES_PRODUITS_EXP("718","Autres produits d'exploitation"
            ,Arrays.asList(
            "7181 - Jetons de présence reçus",
            "7182/7185/7186 - Reste du poste (produits divers)"
    )),
    REPRISE_EXPLOITATION("719","Reprises d'exploitation transferts de charges"
            ,Arrays.asList(
            "7191/7192/7193/7194/7195/7196 - Reprises",
            "7197 - Transferts de charges"
    )),
    INTERETS_ASSIMILES("738","Intérêts et produits assimilés"
            ,Arrays.asList(
            "7381 - Intérêts et produits assimilés",
            "7383 - Revenus des créances rattachées à des participations",
            "7384/7385 - Produits nets sur cessions de titres et valeurs de placement",
            "7386/7388 - Reste du poste intérêts et autres produits financiers"
    ));

    @Setter
    private  String number;

    @Setter
    private  String label;

    @Setter
    private List<String> sous_compte;

    DetailCPCCategoryClass7(String number, String label, List<String> sous_compte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sous_compte;
    }
}
