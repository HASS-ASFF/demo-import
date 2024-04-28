package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.repository.DetailCPCRepository;
import com.api.demoimport.service.DetailCPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailCPCServiceImpl implements DetailCPCService {
    @Autowired
    DetailCPCRepository detailCPCRepository;


    @Override
    public DetailCPC createDetailCPC(DetailCPC detailCPC) {
        return detailCPCRepository.save(detailCPC);
    }

    @Override
    public List<DetailCPC> FindDetailCPC(String name, String date) {
        return detailCPCRepository.findDetailCPCByND(name,date);
    }

    @Override
    public Optional<DetailCPC> FindByID(Long id) {
        return detailCPCRepository.findById(id);
    }
}
