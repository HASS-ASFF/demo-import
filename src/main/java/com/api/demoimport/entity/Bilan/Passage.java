package com.api.demoimport.entity.Bilan;

import com.api.demoimport.enums.PassageCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class Passage {
    private Long id;
    private String name;
    private Double amountPlus;
    private Double amountMinus;
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
