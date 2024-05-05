package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.FormatUtils;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import com.api.demoimport.enums.*;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.service.AccountDataManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class AccountDataManagerServiceImpl implements AccountDataManagerService {

    @Autowired
    BalanceDetailRepository balanceDetailRepository;

    /**
     * Processing data accounts (MainAccount with their subAccount values),
     * initializing with empty values and then filtering with raw data values from the balance,
     * regrouping the same subAccounts (referring to the account number),
     * and returning a list of subAccounts (Actif or Passif) with all values (both empty and from the balance).
     * Similar logic applies to CPC accounts, where for class 6, debit or credit values may exist.
     *   ( A for Actif and P for Passif )
     */

    @Override
    public List<SubAccountActif> processAccountDataA(List<SubAccountActif> rawData, String n_class) {
        List<SubAccountActif> mainAccountList;
        try{
            //initialize data with empty values
            mainAccountList = SubAccountActif.initializeData(n_class);

            // loop after raw data values of balance
            for (SubAccountActif result : rawData) {
                // Get the number account
                String accountNumber = result.getN_compte();
                String prefix = extractPrefix(accountNumber);

                for (SubAccountActif subAccount : mainAccountList) {
                    // check for the same subAccount
                    if (subAccount.getLibelle().startsWith(prefix)) {
                        // Adding the values
                        subAccount.setN_compte(result.getN_compte());
                        subAccount.setTotal_amo(result.getTotal_amo());
                        subAccount.setBrut(result.getBrut());
                        subAccount.setNet(result.getNet());
                        subAccount.setNetN(result.getNetN());
                        break;
                    }

                }
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to process data account actif, error message : "+ e.getMessage());
        }
        return mainAccountList;
    }

    @Override
    public List<SubAccountPassif> processAccountDataP(List<SubAccountPassif> rawData,String  n_class) {
        List<SubAccountPassif> mainAccountList;
        try{
            //initialize data with empty values
            mainAccountList = SubAccountPassif.initializeData(n_class);

            // loop after raw data values of balance
            for (SubAccountPassif result : rawData) {
                // Get the number account
                String accountNumber = result.getN_compte();
                String prefix = extractPrefix(accountNumber);

                for (SubAccountPassif subAccount : mainAccountList) {
                    // check for the same subAccount
                    if (subAccount.getLibelle().startsWith(prefix)) {
                        // Adding the values
                        subAccount.setN_compte(result.getN_compte());
                        subAccount.setBrut(result.getBrut());
                        subAccount.setBrutP(result.getBrutP());
                        break;
                    }


                }
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to process data account passif, error message : "+ e.getMessage());
        }
        return mainAccountList;
    }

    @Override
    public List<SubAccountCPC> processAccountDataCPC(List<SubAccountCPC> rawData, String n_class) {
        List<SubAccountCPC> mainAccountList;
        try{
            //initialize data with empty values
            mainAccountList = SubAccountCPC.initializeData(n_class);

            // loop after raw data values of balance
            for (SubAccountCPC result : rawData) {
                // Get the number account
                String accountNumber = result.getN_compte();
                String prefix = accountNumber.substring(0,3);
                // Initialize total for each sub-account
                Double total = 0.0;

                for (SubAccountCPC subAccount : mainAccountList) {
                    // check for the same subAccount
                    if (subAccount.getLibelle().startsWith(prefix) || subAccount.getLibelle().contains(prefix)) {
                        // Adding the values
                        if(subAccount.getBrut() != null){
                            total = subAccount.getBrut() + result.getBrut();
                        }else{
                            total = result.getBrut();
                        }
                        subAccount.setN_compte(result.getN_compte());
                        subAccount.setBrut(total);
                        break;
                    }

                }
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to process data account cpc , error message : "+ e.getMessage());
        }
        return mainAccountList;
    }


    public String extractPrefix(String accountNumber) {
        return accountNumber.substring(0, 3);
    }


    public Double GetTotalBrutAccountActif(List<SubAccountActif> accountDataMap) {
        Double total = 0.0;

        try{
            for (SubAccountActif subAccountActif : accountDataMap) {

                total += FormatUtils.
                        formatDecimal((subAccountActif.getBrut() != null ? (double) subAccountActif.getBrut() : 0.0));
            }
            total = FormatUtils.formatDecimal(total);
        }catch (Exception e){
            throw new RuntimeException("Failed to get total accounts, error message : "+ e.getMessage());
        }

        return total;
    }

    public Double GetTotalAmortAccountActif(List<SubAccountActif> accountDataMap) {
        Double total = 0.0;

        try{
            for (SubAccountActif subAccountActif : accountDataMap) {

                total += FormatUtils.
                        formatDecimal((subAccountActif.getTotal_amo() != null ? subAccountActif.getTotal_amo() : 0.0));
            }
            total = FormatUtils.formatDecimal(total);
        }catch (Exception e){
            throw new RuntimeException("Failed to get total accounts, error message : "+ e.getMessage());
        }
        return total;
    }

    public Double GetTotalBrutAccountPassif(List<SubAccountPassif> accountData) {
        Double total = 0.0;
        try {
            for (SubAccountPassif subAccountPassif : accountData) {

                total += FormatUtils.
                        formatDecimal((subAccountPassif.getBrut() != null ? subAccountPassif.getBrut() : 0.0));

            }
        }catch (Exception e){
            throw new RuntimeException("Failed to get total accounts, error message: "+e.getMessage());
        }

        return total;
    }

    public Double GetTotalNetAccountPassif(List<SubAccountPassif> accountData) {
        Double total = 0.0;
        try {
            for (SubAccountPassif subAccountPassif : accountData) {

                total += FormatUtils.
                        formatDecimal((subAccountPassif.getBrutP() != null ? subAccountPassif.getBrutP() : 0.0));

            }
        }catch (Exception e){
            throw new RuntimeException("Failed to get total accounts, error message: "+e.getMessage());
        }

        return total;
    }

    public Double GetTotalBrutCPC(List<SubAccountCPC> accountData) {
        Double total = 0.0;
        try {
            for (SubAccountCPC subAccountCPC : accountData) {

                total += FormatUtils.
                        formatDecimal((subAccountCPC.getBrut() != null ? subAccountCPC.getBrut() : 0.0));

            }
        }catch (Exception e){
            throw new RuntimeException("Failed to get total accounts, error message: "+e.getMessage());
        }

        return total;
    }

    @Override
    public List<SubAccountActif> FilterAccountDataA(List<SubAccountActif> subAccountActifs,String mainAccount) {
        List<SubAccountActif> filteredList = new ArrayList<>();
        for(SubAccountActif val : subAccountActifs){
            if(Objects.equals(val.getMainAccount(), mainAccount)){
                filteredList.add(val);
            }
        }
        return filteredList;
    }

    @Override
    public List<SubAccountPassif> FilterAccountDataP(List<SubAccountPassif> subAccountPassifs,String mainAccount) {
        List<SubAccountPassif> filteredList = new ArrayList<>();
        for(SubAccountPassif val : subAccountPassifs){
            if(Objects.equals(val.getMainAccount(), mainAccount)){
                filteredList.add(val);
            }
        }
        return filteredList;
    }

    @Override
    public List<SubAccountCPC> FilterAccountDataCPC(List<SubAccountCPC> subAccountCPCS, String mainAccount) {
        List<SubAccountCPC> filteredList = new ArrayList<>();
        for(SubAccountCPC val : subAccountCPCS){
            if(Objects.equals(val.getMainAccount(), mainAccount)){
                filteredList.add(val);
            }
        }
        return filteredList;
    }

    public String  getMainAccountOne(String mainAccount) {
        switch (mainAccount) {
            case "11":
                return AccountCategoryClass1.CAPITAUX_PROPRES.
                        getLabel();
            case "13":
                return AccountCategoryClass1.CAPITAUX_PROPRES_ASSIMILES
                        .getLabel();
            case "14":
                return AccountCategoryClass1.DETTES_DE_FINANCEMENT.
                        getLabel();
            case "15":
                return AccountCategoryClass1.PROVISIONS_DURABLES_POUR_RISQUES_ET_CHARGES.
                        getLabel();
            case "17":
                return AccountCategoryClass1.ECARTS_DE_CONVERSION_PASSIF.
                        getLabel();
            default:
                return null;
        }
    }
    public String getMainAccountTwo(String mainAccount) {
        switch (mainAccount) {
            case "21":
                return AccountCategoryClass2.IMMOBILISATION_NON_VALEURS
                        .getLabel();
            case "22":
                return AccountCategoryClass2.IMMOBILISATION_INCORPORELLES
                        .getLabel();
            case "23":
                return AccountCategoryClass2.IMMOBILISATION_CORPORELLES
                        .getLabel();
            case "24":
                return AccountCategoryClass2.IMMOBILISATION_FINANCIERES
                        .getLabel();
            case "27":
                return AccountCategoryClass2.ECART_CONVERSION_ACTIF
                        .getLabel();
            default:
                return null;
        }
    }

    public String getMainAccountThree(String mainAccount) {
        switch (mainAccount) {
            case "31":
                return AccountCategoryClass3.STOCKS
                        .getLabel();
            case "34":
                return AccountCategoryClass3.CREANCES_ACTIF_CIRCULANT
                        .getLabel();
            case "35":
                return AccountCategoryClass3.TITRES_VALEURS_PLACEMENT
                        .getLabel();
            case "37":
                return AccountCategoryClass3.ECART_CONVERSION_ACTIF
                        .getLabel();
            default:
                return null;
        }
    }

    public String getMainAccountFour(String mainAccount) {
        switch (mainAccount) {
            case "44":
                return AccountCategoryClass4.DETTES_DU_PASSIF_CIRCULANT
                        .getLabel();
            case "45":
                return AccountCategoryClass4.AUTRES_PROVISIONS_POUR_RISQUES_ET_CHARGES
                        .getLabel();
            case "47":
                return AccountCategoryClass4.ECARTS_DE_CONVERSION_PASSIF
                        .getLabel();
            default:
                return null;
        }
    }

    public String getMainAccountFive(String mainAccount) {
        switch (mainAccount) {
            case "51":
                return AccountCategoryClass5.TRESORERIE_ACTIF
                        .getLabel();
            case "55":
                return AccountCategoryClass5.TRESORERIE_PASSIF
                        .getLabel();
            default:
                return null;
        }
    }

    public String getMainAccountSix(String mainAccount) {
        switch (mainAccount) {
            case "61":
                return AccountCategoryClass6.CHARGES_DEXPLOITATION
                        .getLabel();
            case "63":
                return AccountCategoryClass6.CHARGES_FINANCIERES
                        .getLabel();
            case "65":
                return AccountCategoryClass6.CHARGES_NON_COURANTES
                        .getLabel();
            default:
                return null;
        }
    }

    public String getMainAccountSeven(String mainAccount) {
        switch (mainAccount) {
            case "71":
                return AccountCategoryClass7.PRODUITS_DEXPLOITATION
                        .getLabel();
            case "73":
                return AccountCategoryClass7.PRODUITS_FINANCIERS
                        .getLabel();
            case "75":
                return AccountCategoryClass7.PRODUITS_NON_COURANTS
                        .getLabel();
            default:
                return null;
        }
    }
}









//  ************ ************ OLD CODE (FOR HTML) ************ ************

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

//    public List<MainAccount> initializeMainAccountData(int n_class,boolean C5test) {
//        List<MainAccount> mainAccountList = new ArrayList<>();
//
//        try{
//            switch(n_class) {
//                case 1:
//                    // boucle sur les catégories de compte principaux pour initialiser la liste
//                    for(AccountCategoryClass1 category : AccountCategoryClass1.values()){
//                        MainAccount mainAccount = new MainAccount();
//                        mainAccount.setMain_account(category.getLabel());
//                        mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());
//
//                        mainAccountList.add(mainAccount);
//                    }
//                    break;
//
//                case 2:
//                    // boucle sur les catégories de compte principaux pour initialiser la liste
//                    for (AccountCategoryClass2 category : AccountCategoryClass2.values()) {
//                        MainAccount mainAccount = new MainAccount();
//                        mainAccount.setMain_account(category.getLabel());
//                        mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());
//
//                        mainAccountList.add(mainAccount);
//                    }
//
//                    break;
//                case 3:
//                    // boucle sur les catégories de compte principaux pour initialiser la liste
//                    for (AccountCategoryClass3 category : AccountCategoryClass3.values()) {
//                        MainAccount mainAccount = new MainAccount();
//                        mainAccount.setMain_account(category.getLabel());
//                        mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());
//
//                        mainAccountList.add(mainAccount);
//                    }
//
//                    break;
//
//                case 4:
//                    // boucle sur les catégories de compte principaux pour initialiser la liste
//                    for (AccountCategoryClass4 category : AccountCategoryClass4.values()) {
//                        MainAccount mainAccount = new MainAccount();
//                        mainAccount.setMain_account(category.getLabel());
//                        mainAccount.setSubAccounts(category.getSubAccountsDataWithEmptyValues());
//
//                        mainAccountList.add(mainAccount);
//                    }
//
//                    break;
//
//                case 5:
//                    MainAccount mainAccount = new MainAccount();
//
//                    if(C5test) {
//                        mainAccount.setMain_account(AccountCategoryClass5.TRESORERIE_PASSIF.getLabel());
//                        mainAccount.setSubAccounts(getSubAccountsDataWithEmptyValues(true));
//                    }
//                    else {
//                        mainAccount.setMain_account(AccountCategoryClass5.TRESORERIE_ACTIF.getLabel());
//                        mainAccount.setSubAccounts(getSubAccountsDataWithEmptyValues(false));
//                    }
//
//                    mainAccountList.add(mainAccount);
//                    break;
//            }
//        }catch (Exception e){
//            throw new RuntimeException("Failed to initialize data, message error:  " + e.getMessage());
//        }
//
//
//
//        return mainAccountList;
//    }


// methode pour lier les sous-compte avec leur valeurs
    /*public List<MainAccount> processAccountData(List<Bilan> rawData, int n_class, boolean checkA5) {
        List<MainAccount> mainAccountList;
        try{

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
        }catch (Exception e){
            throw new RuntimeException("Failed to process data account, error message : "+ e.getMessage());
        }

        return mainAccountList;
    }*/
