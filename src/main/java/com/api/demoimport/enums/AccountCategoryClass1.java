package com.api.demoimport.enums;


import com.api.demoimport.entity.Bilan.SubAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum AccountCategoryClass1 {
    CLASSE_1("Classe 1", "",new ArrayList<>()),
    CAPITAUX_PROPRES("11", "CAPITAUX PROPRES (A)"
            , Arrays.asList(
            "111 - Capital social ou personnel",
            "112 - Primes d'émission, de fusion et d'apport",
            "113 - Ecarts de réévaluation",
            "114 - Réserve légale",
            "115 - Autres réserves",
            "116 - Report à nouveau",
            "118 - Résultats nets en instance d'affectation",
            "119 - Résultat net de l'exercice"
    )),
    CAPITAUX_PROPRES_ASSIMILES("13","CAPITAUX PROPRES ASSIMILES (B)"
            , Arrays.asList(
            "131 - Subventions d'investissement",
            "135 - Provisions réglementées"
    )),
    DETTES_DE_FINANCEMENT("14","DETTES DE FINANCEMENT (C)"
        ,Arrays.asList(
            "141 - Emprunts obligataires",
            "148 - Autres dettes de financement"
    )),
    PROVISIONS_DURABLES_POUR_RISQUES_ET_CHARGES("15","PROVISIONS DURABLES POUR RISQUES ET CHARGES (D)",
            Arrays.asList(
            "151 - Provisions pour risques",
            "155 - Provisions pour charges"
    )),
    ECARTS_DE_CONVERSION_PASSIF("17","ECARTS DE CONVERSION PASSIF (E)",
            Arrays.asList(
            "171 - Augmentation des créances immobilisées",
            "172 - Diminution des dettes de financement"
    ));

    @Setter
    private final String number;
    @Setter
    private final String label;
    @Setter
    private final List<String> sous_compte;


    AccountCategoryClass1(String number, String label, List<String> sous_compte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sous_compte;
    }

    // method for creating new subaccounts with empty values in order
    public List<SubAccount> getSubAccountsDataWithEmptyValues() {
        List<SubAccount> subAccountsData = new ArrayList<>();

        // boucle sur sous-comptes et initialisation avec des valeurs vides
        for (String subAccountLabel : sous_compte) {
            SubAccount subAccountData = new SubAccount();
            subAccountData.setSub_account(subAccountLabel);
            subAccountData.setValues(new ArrayList<>()); // Liste vide pour les valeurs

            subAccountsData.add(subAccountData);
        }

        return subAccountsData;
    }
}
