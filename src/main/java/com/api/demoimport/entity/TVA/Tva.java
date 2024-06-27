package com.api.demoimport.entity.TVA;

import com.api.demoimport.entity.Societe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tva {
    private Double scDdex;
    private Double scDmex;
    private Double scCmex;

    private Double siDdex;
    private Double siDmex;
    private Double siCmex;

    private Date date;
    private Societe societe;
}
