package com.api.demoimport.service;

import com.api.demoimport.entity.Passage;
import com.api.demoimport.entity.Bilan.SubAccountActif;

import java.util.List;
import java.util.Optional;

public interface PassageService {

    /**
     * Interface defining service operations for managing passages.
     * Methods include creating a passage, processing raw passage data,
     * updating a main account map, filtering passages, finding a passage by name and date,
     * and finding passages by date.
     */

    Passage createPassage(Passage passage);
    List<Passage> processAccountData(List<Passage> rawData);
    void updateMainAccountMap(List<Passage> mainAccountMap, List<Passage> rawData);
    List<Passage> FilterPassages(List<Passage> passages,String m_name);
    Optional<Passage> findByNameAndDate(String name, String date);
    List<Passage> findByDate(String date);

    List<SubAccountActif> findPassageImmo(String date,String company_name);
}
