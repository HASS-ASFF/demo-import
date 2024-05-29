package com.api.demoimport.service;

import com.api.demoimport.dto.Tvadto;
import com.api.demoimport.entity.Bilan.Esg;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.TVA.Tva;

import java.util.List;

public interface TvaService {
    Tvadto convertToTva(List<Object []> data);
    Tvadto getTvaF(String date,String company_name);
    Tvadto getTvaRSc(String date,String company_name);
    Tvadto getTvaRSi(String date,String company_name);

    void getTotalTva(Tvadto tvadtos);
    List<Double> getTotalTvaRecup(Tvadto dataSc,Tvadto dataSi);
    List<Double> getTvaAMinusB(Tvadto data1,List<Double> data2);

}
