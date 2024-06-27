package com.api.demoimport.service.Implementation;

import com.api.demoimport.dto.Tvadto;
import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.service.TvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TvaServiceImpl implements TvaService {

    @Autowired
    BalanceDetailRepository repository;

    @Override
    public Tvadto convertToTva(List<Object []> data) {
        Tvadto tvadto = new Tvadto(0.0,0.0,0.0,0.0);
        if(!data.isEmpty()) {
            for (Object[] val : data) {
                Double val1 = val[0] != null ? (Double) val[0] : 0.0;
                Double val2 = val[1] != null ? (Double) val[1] : 0.0;
                Double val3 = val[2] != null ? (Double) val[2] : 0.0;
                tvadto.setCdex(val1);
                tvadto.setCmex(val2);
                tvadto.setDmex(val3);
            }
        }
        return tvadto;
    }

    @Override
    public Tvadto getTvaF(String date,String company_name) {
        List<Object []> objectsList = repository.getTvaValues(date,company_name);

        return convertToTva(objectsList);
    }

    @Override
    public Tvadto getTvaRSc(String date,String company_name) {
        List<Object []> objectsList = repository.getTvaSCValues(date,company_name);

        return convertToTva(objectsList);
    }

    @Override
    public Tvadto getTvaRSi(String date,String company_name) {
        List<Object []> objectsList = repository.getTvaSIValues(date,company_name);

        return convertToTva(objectsList);
    }

    @Override
    public void getTotalTva(Tvadto tvadtos) {
        tvadtos.setTotal((tvadtos.getCdex()+tvadtos.getCmex()) - tvadtos.getDmex());
    }

    @Override
    public List<Double> getTotalTvaRecup(Tvadto dataSc, Tvadto dataSi) {
        List<Double> resultList = new ArrayList<>();

        resultList.add(dataSc.getCdex()+dataSi.getCdex());
        resultList.add(dataSc.getCmex()+dataSi.getCmex());
        resultList.add(dataSc.getDmex()+dataSi.getDmex());
        resultList.add(dataSc.getTotal()+dataSi.getTotal());

        return resultList;
    }

    @Override
    public List<Double> getTvaAMinusB(Tvadto data1, List<Double> data2) {
        List<Double> resultList = new ArrayList<>();

        resultList.add(data1.getCdex()-data2.get(0));
        resultList.add(data1.getCmex()-data2.get(1));
        resultList.add(data1.getDmex()-data2.get(2));
        resultList.add(data1.getTotal()-data2.get(3));

        return resultList;
    }


}
