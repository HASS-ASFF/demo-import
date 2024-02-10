package com.api.demoimport.service;

import com.api.demoimport.entity.BilanActif.BilanActif;
import com.api.demoimport.entity.BilanActif.MainAccount;
import com.api.demoimport.entity.BilanActif.SubAccount;
import com.api.demoimport.enums.AccountCategoryClass2;
import com.api.demoimport.enums.AccountCategoryClass3;
import com.api.demoimport.enums.AccountCategoryClass5;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountDataManager {

    // methode pour initialiser la liste (class 2 /3 / 5)
    public List<MainAccount> initializeMainAccountData(int n_class) {
        List<MainAccount> mainAccountList = new ArrayList<>();

        switch(n_class) {
            case 2:
                // boucle sur les catégories de compte principaux pour initialiser la liste
                for (AccountCategoryClass2 category : AccountCategoryClass2.values()) {
                    MainAccount mainAccount = new MainAccount();
                    mainAccount.setMain_account(category.getLabel());
                    mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());

                    mainAccountList.add(mainAccount);
                }

                break;
            case 3:
                // boucle sur les catégories de compte principaux pour initialiser la liste
                for (AccountCategoryClass3 category : AccountCategoryClass3.values()) {
                    MainAccount mainAccount = new MainAccount();
                    mainAccount.setMain_account(category.getLabel());
                    mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());

                    mainAccountList.add(mainAccount);
                }

                break;

            case 5:
                // boucle sur les catégories de compte principaux pour initialiser la liste
                for (AccountCategoryClass5 category : AccountCategoryClass5.values()) {
                    MainAccount mainAccount = new MainAccount();
                    mainAccount.setMain_account(category.getLabel());
                    mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());

                    mainAccountList.add(mainAccount);
                }
                break;

            default:
                return null;

        }

        return mainAccountList;
    }


    // methode pour lier les sous-compte avec leur valeurs
    public List<MainAccount> processAccountData(List<BilanActif> rawData,int n_class) {

        List<MainAccount> mainAccountList = initializeMainAccountData(n_class); // Initialisation des comptes principaux avec sous-comptes vides

        // boucle sur les données brutes pour ajouter les valeurs aux sous-comptes correspondants
        for (BilanActif result : rawData) {
            String accountNumber = result.getN_compte(); // Récupère le numéro du compte
            String prefix = extractPrefix(accountNumber);// 3 premiers chiffres

            for (MainAccount mainAccount : mainAccountList) {
                // boucle sur les sous-comptes de chaque compte principal
                for (SubAccount subAccount : mainAccount.getSubAccounts()) {
                    // Vérifiez si le sous-compte correspond au préfixe extrait
                    if (subAccount.getSub_account().startsWith(prefix)) {
                        // Ajoutez les valeurs au sous-compte correspondant
                        subAccount.getValues().add(result);
                        break;
                    }
                }
            }
        }

        return mainAccountList;
    }

    private String extractPrefix(String accountNumber) {
        return accountNumber.substring(0, 3);
    }



    //  ************ OLD CODE ************

    // private Map<AccountCategoryClass3, Map<String, List<String>>> accountMapClass3 = new HashMap<>();
    //private Map<AccountCategoryClass5, Map<String, List<String>>> accountMapClass5 = new HashMap<>();

    /*public Map<String, List<BilanActif>> processAccountData(List<BilanActif> rawData) {
        Map<String, List<BilanActif>> accountMapClass2 = new LinkedHashMap<>();

        List<BilanActif> dataClass = new ArrayList<>();


        // Liste des noms des comptes principaux
        List<String> nomsComptesPrincipaux = Arrays.asList(
                "IMMOBILISATION en non-valeurs (A)",
                "IMMOBILISATION incorporelles (B)",
                "IMMOBILISATION corporelles (C)",
                "IMMOBILISATION financières (D)",
                "ECART de conversion actif (E)"
        );

        // Initialisation de la carte avec des listes vides pour chaque nom de compte principal
        for (String nomCompte : nomsComptesPrincipaux) {
            accountMapClass2.put(nomCompte, new ArrayList<>());
        }

        // Parcourir les résultats
        for (BilanActif result : rawData) {
            String accountNumber = result.getN_compte(); // Récupère le numéro du compte
            String mainAccount = accountNumber.substring(0, 2); // Les deux premiers chiffres du numéro de compte

            // Associez le libellé du compte au compte principal correspondant dans l'enum
            AccountCategoryClass2 mainAccountEnum = getMainAccountEnum(mainAccount);
            if (mainAccountEnum != null) {
                String mainAccountLabel = mainAccountEnum.getLabel();

                // Obtenez la liste correspondant à la clé et ajoutez-y les données du bilan actif
                List<BilanActif> accountDataList = accountMapClass2.get(mainAccountLabel);
                if (accountDataList != null) {
                    accountDataList.add(result);
                }
            }
        }

        return accountMapClass2;
    }*/
    /*public AccountCategoryClass2 getMainAccountEnum(String mainAccount) {
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
    */

}

