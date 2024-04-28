package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import com.api.demoimport.enums.AccountCategoryClass1;
import com.api.demoimport.enums.PassageCategory;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.repository.PassageRepository;
import com.api.demoimport.service.PassageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PassageServiceImpl implements PassageService {

    @Autowired
    private PassageRepository passageRepository;

    @Autowired
    private BalanceDetailServiceImpl balanceDetailService;

    @Autowired
    private BalanceDetailRepository repository;

    @Override
    public Passage createPassage(Passage passage) {
        return passageRepository.save(passage);
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
                    if (name_p != null && val.getName().startsWith(name_p)) {
                        val.setAmountPlus(rawPassage.getAmountPlus());
                        val.setAmountMinus(rawPassage.getAmountMinus());
                    }

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
    public Optional<Passage> findByNameAndDate(String name, String date) {
        return passageRepository.findPassageByND(name,date);
    }

    @Override
    public List<Passage> findByDate(String date) {
        return passageRepository.findPassagesByDate(date);
    }

    @Override
    public List<SubAccountActif> findPassageImmo(String date,String company_name) {

        List<Object[]> resultrequest = repository.getPassageImmo(date,company_name);
        List<SubAccountActif> bilanActifs = ConvertToBilanActif(resultrequest);
        balanceDetailService.regroupClassesA(bilanActifs);
        bilanActifs.forEach(x -> System.out.println(x.getBrut()));
        return bilanActifs;
    }


    private List<SubAccountActif> ConvertToBilanActif(List<Object[]> resultsrequest){

        List<SubAccountActif> bilansActifs = new ArrayList<>();

        try{
            for (Object[] resultat : resultsrequest) {

                String mainA = balanceDetailService.getMainAccount(resultat[0].toString());
                String n_compte =  resultat[0].toString();
                String libelle = (String) resultat[1];
                Double brut = (Double) resultat[2];
                SubAccountActif subAccountActif = new SubAccountActif(mainA,n_compte,libelle,brut);
                bilansActifs.add(subAccountActif);
            }

            return bilansActifs;

        }catch (RuntimeException e){
            throw new RuntimeException("Failed to convert object to SubAccountActif (convert method) "+e.getMessage());
        }

    }
}
