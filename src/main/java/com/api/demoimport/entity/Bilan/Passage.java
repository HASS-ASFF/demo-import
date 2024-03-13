package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.PassageCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
public class Passage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Double amountPlus;
    @Column
    private Double amountMinus;
    @Column
    private Date date;

    public static List<Passage> initializeData(){
        List<Passage> passagesList = new ArrayList<>();

        for(PassageCategory passageCategory : PassageCategory.values()){
            for(String val : passageCategory.getSub_name()){
                Passage passage = new Passage();
                passage.setName(val);
                passagesList.add(passage);
            }
        }

        return passagesList;
    }
}
