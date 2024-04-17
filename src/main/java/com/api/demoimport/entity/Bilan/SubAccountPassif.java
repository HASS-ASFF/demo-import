package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.AccountCategoryClass1;
import com.api.demoimport.enums.AccountCategoryClass4;
import com.api.demoimport.enums.AccountCategoryClass5;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubAccountPassif {
    private String mainAccount;
    private String n_compte;
    private String libelle;
    private Double brut;
    private Double brutP;

    SubAccountPassif(String mainAccount,String n_compte,String libelle){
        this.mainAccount = mainAccount;
        this.n_compte = n_compte;
        this.libelle = libelle;
    }
    public static List<SubAccountPassif> initializeData(String n_class){

        List<SubAccountPassif> subAccountPassifs = new ArrayList<>();

        switch(n_class){
            case "1" :
                for (AccountCategoryClass1 category : AccountCategoryClass1.values()) {
                    for(String val : category.getSous_compte()){
                        SubAccountPassif subAccountPassif = new SubAccountPassif();
                        subAccountPassif.setMainAccount(category.getLabel());
                        getPassif(val,subAccountPassif);
                        subAccountPassifs.add(subAccountPassif);
                    }
                }
                subAccountPassifs.add(1, new SubAccountPassif(AccountCategoryClass1.CAPITAUX_PROPRES.getLabel(),
                        "11190000","Moins : actionnaires, capital souscrit non appelé"));
                subAccountPassifs.add(2, new SubAccountPassif(AccountCategoryClass1.CAPITAUX_PROPRES.getLabel(),
                        "11191000","Moins : Capital appelé"));
                subAccountPassifs.add(3,new SubAccountPassif(AccountCategoryClass1.CAPITAUX_PROPRES.getLabel(),
                        "11192000","Moins : Dont versé"));
                break;
            case "4":
                for (AccountCategoryClass4 category : AccountCategoryClass4.values()) {
                    for(String val : category.getSous_compte()){
                        SubAccountPassif subAccountPassif = new SubAccountPassif();
                        subAccountPassif.setMainAccount(category.getLabel());
                        getPassif(val,subAccountPassif);
                        subAccountPassifs.add(subAccountPassif);
                    }
                }
                break;
            case "5":
                for(String val : AccountCategoryClass5.TRESORERIE_PASSIF.getSous_compte()){
                        SubAccountPassif subAccountPassif = new SubAccountPassif();
                        subAccountPassif.setMainAccount(AccountCategoryClass5.TRESORERIE_PASSIF.getLabel());
                        getPassif(val,subAccountPassif);
                        subAccountPassifs.add(subAccountPassif);

                }
                break;
        }

        return subAccountPassifs;
    }

    private static void getPassif(String val, SubAccountPassif subAccountPassif) {
        subAccountPassif.setN_compte(val.substring(0,3)+"00000");
        subAccountPassif.setLibelle(val);
    }
}
