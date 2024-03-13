package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Passage;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public interface PassageService {
    List<Passage> getAllPassages();
    Passage createPassage(Passage passage);
    Passage updatePassage(Object field);
    List<Passage> findPassages(String date);
    void deletePassage(Long id);
}
