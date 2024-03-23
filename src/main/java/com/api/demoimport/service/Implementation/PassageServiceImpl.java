package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import com.api.demoimport.enums.AccountCategoryClass1;
import com.api.demoimport.enums.PassageCategory;
import com.api.demoimport.repository.PassageRepository;
import com.api.demoimport.service.PassageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;

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
    public Passage updatePassage(Passage updatedPassage) {
        // Vérifier si le passage existe dans la base de données
        Optional<Passage> optionalPassage = passageRepository.findById(updatedPassage.getId());

        // Mettre à jour les valeurs du passage existant avec les nouvelles valeurs
        Passage existingPassage = optionalPassage.get();
        existingPassage.setName(updatedPassage.getName());
        existingPassage.setAmountPlus(updatedPassage.getAmountPlus());
        existingPassage.setAmountMinus(updatedPassage.getAmountMinus());
        // Vous pouvez également mettre à jour d'autres champs si nécessaire

        // Enregistrer les modifications dans la base de données
        return passageRepository.save(existingPassage);
    }

    @Override
    public List<Passage> findPassages(String date) {
        return passageRepository.findPassagesByBilanDate(date);
    }

    @Override
    public Passage getById(Long id) {
        return passageRepository.getById(id);
    }

    @Override
    public void deletePassage(Long id) {
        passageRepository.deleteById(id);
    }

    @Override
    public List<Passage> processAccountData(List<Passage> rawData) {
        List<Passage> mainAccountMap = Passage.initializeData();

        if (rawData != null) {
            updateMainAccountMap(mainAccountMap, rawData);
        }

        return mainAccountMap;
    }

    @Override
    public void updateMainAccountMap(List<Passage> mainAccountMap, List<Passage> rawData) {
        for (Passage rawPassage : rawData) {
            String name_p = rawPassage.getName();
            for (Passage val: mainAccountMap) {
                    if (val.getName().startsWith(name_p)) {
                        val.setAmountPlus(rawPassage.getAmountPlus());
                        val.setAmountMinus(rawPassage.getAmountMinus());
                    }
                    break;
            }
        }
    }

    @Override
    public List<Passage> FilterPassages(List<Passage> passages, String m_name) {
        List<Passage> filteredList = new ArrayList<>();
        for(Passage val : passages){
            if(Objects.equals(val.getM_name(), m_name)){
                filteredList.add(val);
            }
        }
        return filteredList;
    }

    @Override
    public Passage PassageById(Long id) {
        if(id != null){
            return passageRepository.getById(id);
        }
        return null;
    }

    @Override
    public Passage findByNameAndDate(String name, Date date) {
        return passageRepository.findPassageByNameAndDate(name,date);
    }
}
