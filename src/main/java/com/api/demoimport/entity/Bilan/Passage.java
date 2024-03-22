package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.PassageCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
public class Passage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String m_name;
    @Column
    private String name;
    @Column
    private Double amountPlus;
    @Column
    private Double amountMinus;
    @Column
    private Date date;

    public static List<Passage> initializeData() {
        List<Passage> passages = new ArrayList<>();

        for (PassageCategory passageCategory : PassageCategory.values()) {
            for (String val : passageCategory.getSub_name()) {
                Passage passage = new Passage();
                passage.setM_name(passageCategory.getMain_name());
                passage.setName(val);
                passages.add(passage);
            }
        }

        return passages;
    }
}
