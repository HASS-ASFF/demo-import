package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.Esg;
import com.api.demoimport.entity.Bilan.FormatUtils;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.enums.AccountCategoryClass6;
import com.api.demoimport.enums.AccountCategoryClass7;
import com.api.demoimport.service.EsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsgServiceImpl  implements EsgService {

    @Autowired
    AccountDataManagerServiceImpl accountDataManagerService;

    /**
     * Service implementation for ESG (Etat des Soldes de Gestion) calculations.
     * Includes methods for converting CPC (Compte de Produit et Charge) data to ESG,
     * processing data for the Tableau de Formation des Résultats (TFR), and processing data for the Capacité d'Autofinancement (CAF).
     * Also provides methods to calculate total data for ESG and different types of results.
     */

    @Override
    public List<Esg> convertCPCtoESG(List<SubAccountCPC> subAccountCPCS) {
        List<Esg> esgList = new ArrayList<>();
        for (SubAccountCPC val:subAccountCPCS){
            Esg esg = new Esg();
            esg.setName(val.getLibelle());
            esg.setExercice(val.getBrut() != null ? val.getBrut() : 0.0);
            esg.setExerciceP(0.0);
            esgList.add(esg);
        }
        return esgList;
    }

    @Override
    public List<Esg> processDataTFR(List<SubAccountCPC> dataSix,List<SubAccountCPC> dataSeven, int part) {
        List<Esg> esgList;
        List<SubAccountCPC> customList = new ArrayList<>();
        // FILTRAGE
        List<SubAccountCPC> ProdExpList = accountDataManagerService.
                FilterAccountDataCPC(dataSeven, AccountCategoryClass7.PRODUITS_DEXPLOITATION.getLabel());
        List<SubAccountCPC> ChExpList = accountDataManagerService.
                FilterAccountDataCPC(dataSix, AccountCategoryClass6.CHARGES_DEXPLOITATION.getLabel());
        // TRI
        switch (part){
            case 1:
                // Marges Brutes Etat
                customList.add(ProdExpList.get(0));
                customList.add(ChExpList.get(0));
                break;
            case 2:
                // Production de l'exercice
                customList.add(ProdExpList.get(1));
                customList.add(ProdExpList.get(2));
                customList.add(ProdExpList.get(3));
                break;
            case 3:
                // Consommation
                customList.add(ChExpList.get(1));
                customList.add(ChExpList.get(2));
                break;
            case 4:
                // Valeur ajoutee
                customList.add(ProdExpList.get(4));
                customList.add(ChExpList.get(3));
                customList.add(ChExpList.get(4));
                break;
            case 5:
                // EBE
                customList.add(ProdExpList.get(5));
                customList.add(ChExpList.get(5));
                customList.add(ProdExpList.get(6));
                customList.add(ChExpList.get(6));
                break;
            default:
                break;
        }

        esgList = convertCPCtoESG(customList);

       return esgList;
    }

    @Override
    public List<Esg> processDataCAF(List<SubAccountCPC> dataSix,List<SubAccountCPC> dataSeven, int part) {
        List<Esg> esgList ;
        List<SubAccountCPC> customList = new ArrayList<>();

        // FILTRAGE
        List<SubAccountCPC> ProdExpList = accountDataManagerService.
                FilterAccountDataCPC(dataSeven, AccountCategoryClass7.PRODUITS_DEXPLOITATION.getLabel());
        List<SubAccountCPC> ProdfinanList = accountDataManagerService.
                FilterAccountDataCPC(dataSeven, AccountCategoryClass7.PRODUITS_FINANCIERS.getLabel());

        List<SubAccountCPC> ChExpList = accountDataManagerService.
                FilterAccountDataCPC(dataSix, AccountCategoryClass6.CHARGES_DEXPLOITATION.getLabel());
        List<SubAccountCPC> ChfinanList = accountDataManagerService.
                FilterAccountDataCPC(dataSix, AccountCategoryClass6.CHARGES_FINANCIERES.getLabel());

        List<SubAccountCPC> ProdNCList = accountDataManagerService.
                FilterAccountDataCPC(dataSeven, AccountCategoryClass7.PRODUITS_NON_COURANTS.getLabel());
        List<SubAccountCPC> ChNCList = accountDataManagerService.
                FilterAccountDataCPC(dataSix, AccountCategoryClass6.CHARGES_NON_COURANTES.getLabel());

        Double total1 = accountDataManagerService.GetTotalBrutCPC(ProdExpList);
        Double total2 = accountDataManagerService.GetTotalBrutCPC(ChExpList);
        Double total3 = accountDataManagerService.GetTotalBrutCPC(ProdfinanList);
        Double total4 = accountDataManagerService.GetTotalBrutCPC(ChfinanList);
        Double total5 = accountDataManagerService.GetTotalBrutCPC(ProdNCList);
        Double total6 = accountDataManagerService.GetTotalBrutCPC(ChNCList);

        // TRI
        switch (part){
            case 1:
                Double ResultatNet = FormatUtils.formatDecimal((total1+total3+total5)-(total2+total4+total6));
                if(ResultatNet > 0 ){
                    customList.add(new SubAccountCPC("Bénéfice+",ResultatNet));
                    customList.add(new SubAccountCPC("Perte-",0.0));
                }
                else {
                    customList.add(new SubAccountCPC("Bénéfice+",0.0));
                    customList.add(new SubAccountCPC("Perte-",Math.abs(ResultatNet)));
                }
                break;
            case 2:
                // Dotations parts
                customList.add(ChExpList.get(6));
                customList.add(ChfinanList.get(3));
                customList.add(ChNCList.get(3));
                customList.add(ProdExpList.get(6));
                customList.add(ProdfinanList.get(2));
                customList.add(ProdNCList.get(3));
                customList.add(ProdNCList.get(0));
                customList.add(ChNCList.get(0));
                break;
            default:
                break;

        }

        esgList = convertCPCtoESG(customList);
        return esgList;
    }

    @Override
    public Double GetTotalDataEsg(List<Esg> data, int part) {
        Double total = 0.0;
        switch (part){
            case 1: // ADDITION (Consommation / Valeur ajoutee/ EBE ...)
                for(Esg val: data){
                    total += val.getExercice();
                }
                break;
            case 2: // SOUSTRACTION (Marges Brutes)
                total = data.get(0).getExercice() - data.get(1).getExercice();
        }
        return total;
    }

    @Override
    public Double GetResultat(List<SubAccountCPC> dataSix, List<SubAccountCPC> dataSeven, String typeResultat) {
        Double total = 0.0;

        // FILTRAGE
        List<SubAccountCPC> ProdExpList = accountDataManagerService.
                FilterAccountDataCPC(dataSeven, AccountCategoryClass7.PRODUITS_DEXPLOITATION.getLabel());
        List<SubAccountCPC> ProdfinanList = accountDataManagerService.
                FilterAccountDataCPC(dataSeven, AccountCategoryClass7.PRODUITS_FINANCIERS.getLabel());

        List<SubAccountCPC> ChExpList = accountDataManagerService.
                FilterAccountDataCPC(dataSix, AccountCategoryClass6.CHARGES_DEXPLOITATION.getLabel());
        List<SubAccountCPC> ChfinanList = accountDataManagerService.
                FilterAccountDataCPC(dataSix, AccountCategoryClass6.CHARGES_FINANCIERES.getLabel());

        List<SubAccountCPC> ProdNCList = accountDataManagerService.
                FilterAccountDataCPC(dataSeven, AccountCategoryClass7.PRODUITS_NON_COURANTS.getLabel());
        List<SubAccountCPC> ChNCList = accountDataManagerService.
                FilterAccountDataCPC(dataSix, AccountCategoryClass6.CHARGES_NON_COURANTES.getLabel());

        Double total1 = accountDataManagerService.GetTotalBrutCPC(ProdExpList);
        Double total2 = accountDataManagerService.GetTotalBrutCPC(ChExpList);
        Double total3 = accountDataManagerService.GetTotalBrutCPC(ProdfinanList);
        Double total4 = accountDataManagerService.GetTotalBrutCPC(ChfinanList);
        Double total5 = accountDataManagerService.GetTotalBrutCPC(ProdNCList);
        Double total6 = accountDataManagerService.GetTotalBrutCPC(ChNCList);

        switch (typeResultat){
            case "RESULTAT D'EXPLOITATION":
                total = total1 - total2;
                break;
            case "RESULTAT FINANCIER":
                total = total4 - total5;
                break;
            case "RESULTAT COURANT":
                total = total3 + total4;
                break;
            case "RESULTAT NON COURANT":
                total = total5 - total6;
                break;
            case "Impôt sur les résultats":
                total = 0.0;
                break;
            case "RESULTAT NET DE L'EXERCICE":
                total = (total1+total3+total5)-(total2+total4+total6);
                break;
            default:
                break;
        }
        return total;
    }
}
