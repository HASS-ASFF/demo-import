package com.api.demoimport.entity.BilanAndCPC;


import com.api.demoimport.enums.AccountCategoryClass2;
import com.api.demoimport.enums.AccountCategoryClass3;
import com.api.demoimport.enums.AccountCategoryClass5;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubAccountActif {

    private String mainAccount;
    private String n_compte;
    private String libelle;
    private Double brut;
    private Double total_amo;
    private Double net;
    private Double netN;

    public SubAccountActif(String mainA, String nCompte, String libelle, Double brut, Double net, Double netn) {
        this.mainAccount=mainA;
        this.n_compte=nCompte;
        this.libelle=libelle;
        this.brut=brut;
        this.net=net;
        this.netN=netn;
    }


    public static List<SubAccountActif> initializeData(String n_class){

        List<SubAccountActif> subAccountActifs = new ArrayList<>();

        switch(n_class){
            case "2":
                for (AccountCategoryClass2 category : AccountCategoryClass2.values()) {
                    for(String val : category.getSous_compte()){
                        SubAccountActif subAccountActif = new SubAccountActif();
                        subAccountActif.setMainAccount(category.getLabel());
                        getActif(val,subAccountActif);
                        subAccountActifs.add(subAccountActif);
                    }
                }
                break;
            case "3":
                for (AccountCategoryClass3 category : AccountCategoryClass3.values()) {
                    for(String val : category.getSous_compte()){
                        SubAccountActif subAccountActif = new SubAccountActif();
                        subAccountActif.setMainAccount(category.getLabel());
                        getActif(val,subAccountActif);
                        subAccountActifs.add(subAccountActif);
                    }
                }
                break;
            case "5":
                for(String val : AccountCategoryClass5.TRESORERIE_ACTIF.getSous_compte()){
                        SubAccountActif subAccountActif = new SubAccountActif();
                        subAccountActif.setMainAccount(AccountCategoryClass5.TRESORERIE_ACTIF.getLabel());
                         getActif(val,subAccountActif);
                        subAccountActifs.add(subAccountActif);
                }
                break;
        }

        return subAccountActifs;
    }

    private static void getActif(String val, SubAccountActif subAccountActif) {
        subAccountActif.setN_compte(val.substring(0,3)+"00000");
        subAccountActif.setLibelle(val);
    }

}
