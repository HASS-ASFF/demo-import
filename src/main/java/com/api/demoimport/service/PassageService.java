package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Passage;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountPassif;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PassageService {
    Passage createPassage(Passage passage);
    Optional<Passage> FindByID(Long id);
    void deletePassage(Long id);
    List<Passage> processAccountData(List<Passage> rawData);
    void updateMainAccountMap(List<Passage> mainAccountMap, List<Passage> rawData);
    List<Passage> FilterPassages(List<Passage> passages,String m_name);
    Optional<Passage> findByNameAndDate(String name, String date);
    List<Passage> findByDate(String date);

    List<SubAccountActif> findPassageImmo(String date,String company_name);
}
