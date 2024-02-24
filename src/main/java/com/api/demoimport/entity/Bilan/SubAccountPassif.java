package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.AccountCategoryClass1;
import com.api.demoimport.enums.AccountCategoryClass4;
import com.api.demoimport.enums.AccountCategoryClass5;
import lombok.*;

import javax.persistence.*;
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


    public SubAccountPassif(String mainAccount, String nCompte, String libelle) {
        this.mainAccount=mainAccount;
        this.n_compte=nCompte;
        this.libelle=libelle;
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
