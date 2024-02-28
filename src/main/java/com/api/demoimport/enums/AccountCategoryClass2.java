package com.api.demoimport.enums;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum AccountCategoryClass2 {
    IMMOBILISATION_NON_VALEURS("21", "IMMOBILISATION en non-valeurs (A)"
    ,Arrays.asList(
            "211 - Frais préliminaires",
            "212 - Charges à répartir sur plusieurs exercices",
            "213 - Primes de remboursement des obligations"
    )),


    IMMOBILISATION_INCORPORELLES("22", "IMMOBILISATION incorporelles (B)",
   Arrays.asList(
                    "221 - Immobilisation en recherche et développement",
                    "222 - Brevets, marques, droits et valeurs similaires",
                    "223 - Fonds commercial",
                    "228 - Autres immobilisations incorporelles"
            )),
    IMMOBILISATION_CORPORELLES("23", "IMMOBILISATION corporelles (C)",
    Arrays.asList(
                    "231 - Terrains",
                    "232 - Constructions",
                    "233 - Installations techniques, matériel et outillage",
                    "234 - Matériel de transport",
                    "235 - Mobilier, matériel de bureau et aménagements divers",
                    "238 - Autres immobilisations corporelles",
                    "239 - Immobilisations corporelles en cours"
            )),
    IMMOBILISATION_FINANCIERES("24", "IMMOBILISATION financières (D)",
    Arrays.asList(
                    "241 - Prêts immobilisés",
                    "248 - Autres créances financières",
                    "251 - Titres de participation",
                    "258 - Autres titres immobilisés"
    )),
    ECART_CONVERSION_ACTIF("27", "ECART de conversion actif (E)",
    Arrays.asList(
                    "271 - Diminution des créances immobilisées",
                    "272 - Augmentation des dettes de financement"
            ));

    @Setter
    private  String number;
    @Setter
    private  String label;
    @Setter
    private  List<String> sous_compte;



    AccountCategoryClass2(String number, String label, List<String> sous_compte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sous_compte;
    }



}
