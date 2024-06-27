package com.api.demoimport.entity;

import com.api.demoimport.enums.PassageCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * An entity class representing a Passage.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Passage(String perteNette, Double amount,Date dateBilan) {
        this.name=perteNette;
        this.date=dateBilan;
        if(amount <0){
            this.amountMinus=Math.abs(amount);
            this.amountPlus=0.0;
        }else{
            this.amountPlus=amount;
            this.amountMinus=0.0;
        }

    }

    public static List<Passage> initializeData() {
        List<Passage> passages = new ArrayList<>();
        for (PassageCategory passageCategory : PassageCategory.values()) {
            for (String val : passageCategory.getSub_name()) {
                Passage passage = new Passage();
                passage.setM_name(passageCategory.getMain_name());
                passage.setName(val);
                passage.setAmountPlus(0.00);
                passage.setAmountMinus(0.00);
                passages.add(passage);
            }
        }

        return passages;
    }
}
