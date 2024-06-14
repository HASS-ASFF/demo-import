package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.AccountCategoryClass6;
import com.api.demoimport.enums.AccountCategoryClass7;
import com.api.demoimport.service.Updatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * An entity class representing a CPC.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubAccountCPC implements Updatable {

    private String mainAccount;
    private String n_compte;
    private String libelle;
    private Double brut;
    private Double totalbrut;
    private Double totalbrutP;
    private Double brutP;

    public SubAccountCPC(String mainAccount, String nCompte, String libelle, Double brut) {
        this.mainAccount=mainAccount;
        this.n_compte=nCompte;
        this.libelle=libelle;
        this.brut=brut;
    }

    public SubAccountCPC(String libelle, Double brut){
        this.libelle = libelle;
        this.brut = brut;
    }

    public static List<SubAccountCPC> initializeData(String n_class){
        List<SubAccountCPC> subAccountCPCS = new ArrayList<>();

        switch (n_class){
            case "6":
                for(AccountCategoryClass6 category : AccountCategoryClass6.values()){
                    for(String val : category.getSous_compte()){
                        SubAccountCPC subAccountCPC = new SubAccountCPC();
                        subAccountCPC.setMainAccount(category.getLabel());
                        getCPC(val,subAccountCPC);
                        subAccountCPCS.add(subAccountCPC);
                    }
                }
                break;
            case "7":
                for(AccountCategoryClass7 category : AccountCategoryClass7.values()){
                    for(String val : category.getSous_compte()){
                        SubAccountCPC subAccountCPC = new SubAccountCPC();
                        subAccountCPC.setMainAccount(category.getLabel());
                        getCPC(val,subAccountCPC);
                        subAccountCPCS.add(subAccountCPC);
                    }
                }
                break;
        }
        return subAccountCPCS;
    }

    private static void getCPC(String val,SubAccountCPC subAccountCPC){
        subAccountCPC.setN_compte(val.substring(0,3)+"00000");
        subAccountCPC.setLibelle(val);
    }

    @Override
    public String getMainAccountAccess() {
        return this.mainAccount;
    }

    @Override
    public String getN_compteAccess() {
        return this.n_compte;
    }

    @Override
    public Double getCurrentExercice() {
        return this.brut;
    }

    @Override
    public void setPreviousExercice(Double exerciceP) {
        this.brutP = exerciceP;
    }
}
