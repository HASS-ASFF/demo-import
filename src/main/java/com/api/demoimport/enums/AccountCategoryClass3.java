package com.api.demoimport.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum AccountCategoryClass3 {
    STOCKS("31", "Stocks (F)",
            Arrays.asList(
                    "311 - Marchandises",
                    "312 - Matières et fournitures consommables",
                    "313 - Produits en cours",
                    "314 - Produits intermédiaires et produits résiduels",
                    "315 - Produits finis"
            )),
    CREANCES_ACTIF_CIRCULANT("34", "Créances de l'actif circulant (G)",
            Arrays.asList(
                    "341 - Fournisseurs débiteurs, avances et acomptes",
                    "342 - Clients et comptes rattachés",
                    "343 - Personnel",
                    "345 - Etat",
                    "346 - Comptes d'associés",
                    "348 - Autres débiteurs"
            )),
    TITRES_VALEURS_PLACEMENT("35", "Titres et valeurs de placement (H)",
            Arrays.asList("Titres et valeurs de placement (H)")),
    ECART_CONVERSION_ACTIF("37", "Ecart de conversion - ACTIF (element circulant)",
            Arrays.asList("Ecart de conversion - ACTIF (element circulant)"));

    @Setter
    private  String number;
    @Setter
    private  String label;

    @Setter
    private  List<String> sous_compte;

    AccountCategoryClass3(String number, String label, List<String> sousCompte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sousCompte;
    }

}
