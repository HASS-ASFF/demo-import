package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.AccountCategoryClass6;
import com.api.demoimport.enums.AccountCategoryClass7;
import com.api.demoimport.enums.DetailCPCCategoryClass6;
import com.api.demoimport.enums.DetailCPCCategoryClass7;
import com.api.demoimport.service.Updatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An entity class representing a DetailCPC.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailCPC implements Updatable {
    private Long id;
    private String main_account;
    private String n_compte;
    private String poste;
    private String name;
    private Double exercice;
    private Double exerciceP;

    public static List<DetailCPC> initializeData(String n_class){
        List<DetailCPC> detailCPCList = new ArrayList<>();

        switch (n_class){
            case "6":
                for(DetailCPCCategoryClass6 category : DetailCPCCategoryClass6.values()){
                    for(String val : category.getSous_compte()){
                        DetailCPC detailCPC = new DetailCPC();
                        detailCPC.setMain_account(category.getLabel());
                        getCPC(val,detailCPC);
                        detailCPCList.add(detailCPC);
                    }
                }
                break;
            case "7":
                for(DetailCPCCategoryClass7 category : DetailCPCCategoryClass7.values()){
                    for(String val : category.getSous_compte()){
                        DetailCPC detailCPC = new DetailCPC();
                        detailCPC.setMain_account(category.getLabel());
                        getCPC(val,detailCPC);
                        detailCPCList.add(detailCPC);
                    }
                }
                break;
        }
        return detailCPCList;
    }

    private static void getCPC(String val,DetailCPC detailCPC){
        detailCPC.setPoste(val.substring(0,4)+"0000");
        detailCPC.setName(val);
        detailCPC.setExercice(0.0);
        detailCPC.setExerciceP(0.0);
    }

    @Override
    public String getMainAccount() {
        return this.main_account;
    }

    @Override
    public Double getCurrentExercice() {
        return this.exercice;
    }

    @Override
    public void setPreviousExercice(Double exerciceP) {
        this.exerciceP = exerciceP;
    }
}
