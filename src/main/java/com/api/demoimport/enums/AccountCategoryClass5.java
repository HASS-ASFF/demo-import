package com.api.demoimport.enums;

import com.api.demoimport.entity.Bilan.SubAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum AccountCategoryClass5 {
    CLASSE_5("Classe 5", "", new ArrayList<String>()),
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
    private final String number;

    @Setter
    private final String label;

    @Setter
    private final List<String> sous_compte;

    AccountCategoryClass5(String number, String label, List<String> sousCompte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sousCompte;
    }

    // method for creating new subaccounts with empty values in order
    public static List<SubAccount> getSubAccountsDataWithEmptyValues(boolean forActif) {
        List<SubAccount> subAccountsData = new ArrayList<>();
        List<String> sous_compte;

        if (!forActif) {
            sous_compte = AccountCategoryClass5.TRESORERIE_ACTIF.getSous_compte();
        } else {
            sous_compte = AccountCategoryClass5.TRESORERIE_PASSIF.getSous_compte();
        }

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

