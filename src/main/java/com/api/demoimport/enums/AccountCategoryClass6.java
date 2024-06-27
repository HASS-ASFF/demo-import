package com.api.demoimport.enums;

import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;
import java.util.List;

/**
 * Enumeration representing account categories for class 6.
 */
@Getter
public enum AccountCategoryClass6 {

    CHARGES_DEXPLOITATION("61","CHARGES D'EXPLOITATION"
            , Arrays.asList(
                    "611 - Achats revendus de marchandises",
                    "612 - Achats consommés de matières et fournitures",
                    "613/614 - Autres charges externes",
                    "616 - Impôts et taxes",
                    "617 - Charges de personnel",
                    "618 - Autres charges d'exploitation",
                    "619 Dotations d’exploitation"
    )),
    CHARGES_FINANCIERES("63","CHARGES FINANCIERES"
            , Arrays.asList(
                    "631 - Charges d’intérêts",
                    "633 - Pertes de change",
                    "638 - Autres charges financières",
                    "639 - Dotations financières"
    )),
    CHARGES_NON_COURANTES("65","CHARGES NON COURANTES"
            , Arrays.asList(
                    "651 - Valeurs nettes d’amortissements des immobilisations cédées",
                    "656 - Subventions accordées",
                    "658 - Autres charges non courantes",
                    "659 - Dotations non courantes"
    ));



    @Setter
    private  String number;

    @Setter
    private  String label;

    @Setter
    private List<String> sous_compte;

    AccountCategoryClass6(String number, String label, List<String> sous_compte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sous_compte;
    }
}
