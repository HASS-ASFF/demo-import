package com.api.demoimport.service;

import com.api.demoimport.enums.AccountCategoryClass2;
import com.api.demoimport.enums.AccountCategoryClass3;
import com.api.demoimport.enums.AccountCategoryClass5;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class AccountDataManager {
    private Map<String, List<Object[]>> accountMapClass2 = new LinkedHashMap<>(); // classe 2 - IMMOBILISATIONS

   // private Map<AccountCategoryClass3, Map<String, List<String>>> accountMapClass3 = new HashMap<>();
    //private Map<AccountCategoryClass5, Map<String, List<String>>> accountMapClass5 = new HashMap<>();

    public Map<String, List<Object[]>> processAccountData(List<Object[]> rawData) {

        // Ajout des libellés avec des valeurs null
        for (AccountCategoryClass2 enumValue : AccountCategoryClass2.values()) {
            String label = enumValue.getLabel();
            accountMapClass2.put(label, null); // Ajouter le libellé avec une liste null
        }

        for (Object[] result : rawData) {
            BigInteger accountNumberBigInt = (BigInteger) result[0];
            String accountNumber = accountNumberBigInt.toString(); // Récupère le numéro du compte
            String mainAccount = accountNumber.substring(0, 2); // Les deux premiers chiffres du numéro de compte

            // Associez le libellé du compte au compte principal correspondant dans l'enum
            AccountCategoryClass2 mainAccountEnum = getMainAccountEnum(mainAccount);
            if (mainAccountEnum != null) {
                String mainAccountLabel = mainAccountEnum.getLabel();
                List<Object[]> accountDataList = accountMapClass2.computeIfAbsent(mainAccountLabel, k -> new ArrayList<>());
                // Ajouter le libellé à la carte avec une liste vide
                accountDataList.add(result);
            }
        }

        return accountMapClass2;
    }

    private AccountCategoryClass2 getMainAccountEnum(String mainAccount) {
        switch (mainAccount) {
            case "21":
                return AccountCategoryClass2.IMMOBILISATION_NON_VALEURS;
            case "22":
                return AccountCategoryClass2.IMMOBILISATION_INCORPORELLES;
            case "23":
                return AccountCategoryClass2.IMMOBILISATION_CORPORELLES;
            case "24":
                return AccountCategoryClass2.IMMOBILISATION_FINANCIERES;
            case "27":
                return AccountCategoryClass2.ECART_CONVERSION_ACTIF;
            default:
                return null;
        }
    }

}

