package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.entity.Bilan.Esg;
import com.api.demoimport.entity.Bilan.SubAccountCPC;

import javax.xml.soap.Detail;
import java.util.List;
import java.util.Optional;

public interface DetailCPCService {
    // DETAIL CPC LOGIC
    List<DetailCPC> convertCPCtoDetailCPC(List<SubAccountCPC> subAccountCPCS);

    List<DetailCPC> processDataSix(List<SubAccountCPC> subAccountCPCS);

    List<DetailCPC> processDataSeven(List<SubAccountCPC> subAccountCPCS);

    List<DetailCPC> FilterAccountDataDetailCPC(List<DetailCPC> detailCPCList, String mainAccount);

    Double GetTotalDataDetailCPCC(List<DetailCPC> data);


}
