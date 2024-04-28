package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.entity.Bilan.Esg;

import java.util.List;
import java.util.Optional;

public interface DetailCPCService {

    DetailCPC createDetailCPC(DetailCPC detailCPC);
    List<DetailCPC> FindDetailCPC(String name, String date);
    Optional<DetailCPC> FindByID(Long id);
}
