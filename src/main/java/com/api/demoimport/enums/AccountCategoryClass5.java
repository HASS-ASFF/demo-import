package com.api.demoimport.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumeration representing account categories for class 5.
 */
@Getter
public enum AccountCategoryClass5 {
    TRESORERIE_ACTIF("51", "Trésorerie - Actif",
            Arrays.asList(
                    "511 - Chèques et valeurs à encaisser",
                    "514 - Banques, T.G et C.C.P",
                    "516 - Caisses, régies d'avances et accréditifs"
            )),
    TRESORERIE_PASSIF("55","Trésorerie - Passif",
            Arrays.asList(
                    "552 - Crédits d'escompte",
                    "553 - Crédits de trésorerie",
                    "554 - Banques (solde créditeur)"
            ));

    @Setter
    private  String number;

    @Setter
    private  String label;

    @Setter
    private  List<String> sous_compte;

    AccountCategoryClass5(String number, String label, List<String> sousCompte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sousCompte;
    }


}

