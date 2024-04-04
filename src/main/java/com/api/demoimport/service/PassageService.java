package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountPassif;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PassageService {
    List<Passage> getAllPassages();
    Passage createPassage(Passage passage);
    Passage updatePassage(Passage updatedPassage);
    List<Passage> findPassages(String date);
    Passage getById(Long id);
    void deletePassage(Long id);
    List<Passage> processAccountData(List<Passage> rawData);
    void updateMainAccountMap(List<Passage> mainAccountMap, List<Passage> rawData);
    List<Passage> FilterPassages(List<Passage> passages,String m_name);

    Passage PassageById(Long id);

    Passage findByNameAndDate(String name, Date date);

    List<SubAccountActif> findPassageImmo(String date,String company_name);
}
