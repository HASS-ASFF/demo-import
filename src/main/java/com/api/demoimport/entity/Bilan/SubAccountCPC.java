package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.AccountCategoryClass6;
import com.api.demoimport.enums.AccountCategoryClass7;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubAccountCPC {
    private String mainAccount;
    private String n_compte;
    private String libelle;
    private Double brut;

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
}
