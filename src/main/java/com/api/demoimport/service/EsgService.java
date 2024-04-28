package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Esg;
import java.util.List;
import java.util.Optional;

public interface EsgService {

    Esg createEsg(Esg esg);
    List<Esg> FindEsg(String name,String date);
    Optional<Esg> FindByID(Long id);



}
