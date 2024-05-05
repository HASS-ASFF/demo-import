package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.service.DetailCPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailCPCServiceImpl implements DetailCPCService {

    @Override
    public List<DetailCPC> FindDetailCPC(String name, String date) {
        return null;
    }
}
