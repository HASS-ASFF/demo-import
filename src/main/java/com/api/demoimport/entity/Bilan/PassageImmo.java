package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.AccountCategoryClass2;
import com.api.demoimport.enums.PassageCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class PassageImmo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String m_name;
    @Column
    String name;
    @Column
    Double brut;
    @Column
    Double acquisition;
    @Column
    Double ppe;
    @Column
    Double virement;
    @Column
    Double cession;
    @Column
    Double retraitMinus;
    @Column
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
