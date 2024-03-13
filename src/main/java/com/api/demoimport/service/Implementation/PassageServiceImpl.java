package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.repository.PassageRepository;
import com.api.demoimport.service.PassageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

@Service
public class PassageServiceImpl implements PassageService {

    @Autowired
    private PassageRepository passageRepository;

    @Override
    public List<Passage> getAllPassages() {
        return passageRepository.findAll();
    }

    @Override
    public Passage createPassage(Passage passage) {
        return passageRepository.save(passage);
    }

    @Override
    public Passage updatePassage(Object field) {
        return null;
    }

    @Override
    public List<Passage> findPassages(String date) {
        return passageRepository.findPassagesByBilanDate(date);
    }

    @Override
    public void deletePassage(Long id) {
        passageRepository.deleteById(id);
    }
}
