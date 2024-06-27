package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.AccountCategoryClass2;
import com.api.demoimport.enums.PassageCategory;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An entity class representing a Passage of immobilisation.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassageImmo {
    Long id;
    String m_name;
    String name;
    Double brut;
    Double acquisition;
    Double ppe;
    Double virement;
    Double cession;
    Double retraitMinus;
    Double virementMinus;

    public static List<PassageImmo> initializeData() {
        List<PassageImmo> passages = new ArrayList<>();

        for (AccountCategoryClass2 passageImmo : AccountCategoryClass2.values()) {
            for (String val : passageImmo.getSous_compte()){
                if(Objects.equals(passageImmo.getNumber(), "21") || Objects.equals(passageImmo.getNumber(), "22")
                || Objects.equals(passageImmo.getNumber(), "23")){
                    PassageImmo passage = new PassageImmo();
                    passage.setM_name(passageImmo.getLabel());
                    passage.setName(val);
                    passages.add(passage);
                }

                else break;
            }
        }

        return passages;
    }

}
