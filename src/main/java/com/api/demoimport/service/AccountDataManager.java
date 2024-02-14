package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Bilan;
import com.api.demoimport.entity.Bilan.FormatUtils;
import com.api.demoimport.entity.Bilan.MainAccount;
import com.api.demoimport.entity.Bilan.SubAccount;
import com.api.demoimport.enums.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.api.demoimport.enums.AccountCategoryClass5.getSubAccountsDataWithEmptyValues;

@Service
public class AccountDataManager {

    // methode pour initialiser la liste (class 1 / 2 / 3 / 4 / 5)
    public List<MainAccount> initializeMainAccountData(int n_class,boolean C5test) {
        List<MainAccount> mainAccountList = new ArrayList<>();

        switch(n_class) {
            case 1:
                // boucle sur les catégories de compte principaux pour initialiser la liste
                for(AccountCategoryClass1 category : AccountCategoryClass1.values()){
                    MainAccount mainAccount = new MainAccount();
                    mainAccount.setMain_account(category.getLabel());
                    mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());

                    mainAccountList.add(mainAccount);
                }
                break;

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

            case 4:
                // boucle sur les catégories de compte principaux pour initialiser la liste
                for (AccountCategoryClass4 category : AccountCategoryClass4.values()) {
                    MainAccount mainAccount = new MainAccount();
                    mainAccount.setMain_account(category.getLabel());
                    mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());

                    mainAccountList.add(mainAccount);
                }

                break;

            case 5:
                MainAccount mainAccount = new MainAccount();

                if(C5test) {
                    mainAccount.setMain_account(AccountCategoryClass5.TRESORERIE_PASSIF.getLabel());
                    mainAccount.setSubAccounts(getSubAccountsDataWithEmptyValues(true));
                }
                else {
                    mainAccount.setMain_account(AccountCategoryClass5.TRESORERIE_ACTIF.getLabel());
                    mainAccount.setSubAccounts(getSubAccountsDataWithEmptyValues(false));
                }

                mainAccountList.add(mainAccount);
                break;
        }

        return mainAccountList;
    }


    // methode pour lier les sous-compte avec leur valeurs
    public List<MainAccount> processAccountData(List<Bilan> rawData, int n_class, boolean checkA5) {
        List<MainAccount> mainAccountList;

        if(checkA5) {
             mainAccountList = initializeMainAccountData(n_class, true);
        }
        else {
             mainAccountList = initializeMainAccountData(n_class, false);
        }
        // boucle sur les données brutes pour ajouter les valeurs aux sous-comptes correspondants

        for (Bilan result : rawData) {
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

    public List<Double> GetTotalAccountActif(List<MainAccount> accountDataMap) {
        List<Double> totalList = new ArrayList<>();
        Double total_brut = 0.0;
        Double total_amort = 0.0;
        Double total_net = 0.0;
        Double total = 0.0;

        for(MainAccount mainAccount : accountDataMap){
            for(SubAccount subAccount : mainAccount.getSubAccounts()){
                for(Bilan bilanActif : subAccount.getValues()){
                    total_brut += FormatUtils.formatDecimal((bilanActif.getBrut() != null ? bilanActif.getBrut() : 0));
                    total_amort += FormatUtils.formatDecimal((bilanActif.getTotal_amo() != null ? bilanActif.getTotal_amo() : 0));
                    total_net += FormatUtils.formatDecimal((bilanActif.getNet() != null ? bilanActif.getNet() : 0));
                }
            }
        }
        total = FormatUtils.formatDecimal(total_brut + total_amort + total_net);

        totalList.add(total_brut);
        totalList.add(total_amort);
        totalList.add(total_net);
        totalList.add(total);

        return totalList;
    }

    public List<Double> GetTotalAccountPassif(List<MainAccount> accountDataMap) {
        List<Double> totalList = new ArrayList<>();
        Double total = 0.0;

        for(MainAccount mainAccount : accountDataMap){
            for(SubAccount subAccount : mainAccount.getSubAccounts()){
                for(Bilan bilan : subAccount.getValues()){
                    total += FormatUtils.formatDecimal((bilan.getBrut() != null ? bilan.getBrut() : 0.0));
                }
            }
        }

        total = FormatUtils.formatDecimal(total);
        totalList.add(total);

        return totalList;
    }


    //  ************ OLD CODE ************

    // private Map<AccountCategoryClass3, Map<String, List<String>>> accountMapClass3 = new HashMap<>();
    //private Map<AccountCategoryClass5, Map<String, List<String>>> accountMapClass5 = new HashMap<>();

    /*public Map<String, List<Bilan>> processAccountData(List<Bilan> rawData) {
        Map<String, List<Bilan>> accountMapClass2 = new LinkedHashMap<>();

        List<Bilan> dataClass = new ArrayList<>();


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
        for (Bilan result : rawData) {
            String accountNumber = result.getN_compte(); // Récupère le numéro du compte
            String mainAccount = accountNumber.substring(0, 2); // Les deux premiers chiffres du numéro de compte

            // Associez le libellé du compte au compte principal correspondant dans l'enum
            AccountCategoryClass2 mainAccountEnum = getMainAccountEnum(mainAccount);
            if (mainAccountEnum != null) {
                String mainAccountLabel = mainAccountEnum.getLabel();

                // Obtenez la liste correspondant à la clé et ajoutez les données du bilan actif
                List<Bilan> accountDataList = accountMapClass2.get(mainAccountLabel);
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

