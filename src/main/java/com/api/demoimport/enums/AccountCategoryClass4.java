package com.api.demoimport.enums;

import com.api.demoimport.entity.Bilan.SubAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum AccountCategoryClass4 {
    CLASSE_4("Classe 4", "",new ArrayList<>()),
    DETTES_DU_PASSIF_CIRCULANT("44", "DETTES DU PASSIF CIRCULANT (F)"
            , Arrays.asList(
            "441 - Fournisseurs et comptes rattachés",
            "442 - Clients créditeurs, avances et acomptes",
            "443 - Personnel - créditeur",
            "444 - Organismes sociaux",
            "445 - Etat - créditeur",
            "446 - Comptes d'associés - créditeurs",
            "448 - Autres créanciers",
            "449 - Comptes de régularisation - passif"
    )),
    AUTRES_PROVISIONS_POUR_RISQUES_ET_CHARGES("45","AUTRES PROVISIONS POUR RISQUES ET CHARGES (G)"
        ,  Arrays.asList(
            ""
    )),
    ECARTS_DE_CONVERSION_PASSIF("47","ECARTS DE CONVERSION - PASSIF (Eléments circulants) (H)"
           , Arrays.asList(
            ""
    ));

    @Setter
    private final String number;
    @Setter
    private final String label;
    @Setter
    private final List<String> sous_compte;


    AccountCategoryClass4(String number, String label, List<String> sous_compte) {
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
