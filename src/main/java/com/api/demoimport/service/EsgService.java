package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Esg;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;

import java.util.List;
import java.util.Optional;

public interface EsgService {

    /**
     * Interface defining service operations for managing financial data including :
     *  - ESG (L'état des soldes de gestion)
     *  - TFR (Tableau de Formation des Résultats)
     *  - CAF (Capacité d'Autofinancement - Autofinancement)

     * Methods include converting CPC (Compte de Produit et Charge) data to ESG data,
     * processing TFR and CAF data,
     * calculating total ESG data, and getting the result from CPC data.
     */

    List<Esg> convertCPCtoESG(List<SubAccountCPC> subAccountCPCS);

    List<Esg> processDataTFR(List<SubAccountCPC> dataSix,List<SubAccountCPC> dataSeven, int part);

    List<Esg> processDataCAF(List<SubAccountCPC> dataSix,List<SubAccountCPC> dataSeven, int part);

    Double GetTotalDataEsg(List<Esg> data,int part);

    Double GetResultat(List<SubAccountCPC> dataSix,List<SubAccountCPC> dataSeven,String typeResultat);

}
