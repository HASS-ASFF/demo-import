package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.Esg;
import com.api.demoimport.repository.EsgRepository;
import com.api.demoimport.service.EsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EsgServiceImpl  implements EsgService {
    @Autowired
    EsgRepository esgRepository;


    @Override
    public Esg createEsg(Esg esg) {
        return esgRepository.save(esg);
    }

    @Override
    public List<Esg> FindEsg(String name, String date) {
        return esgRepository.findEsgByND(name,date);
    }

    @Override
    public Optional<Esg> FindByID(Long id) {
        return esgRepository.findById(id);
    }
}
