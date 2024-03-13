package com.api.demoimport.service;

import com.api.demoimport.entity.Bilan.Passage;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface PassageService {
    List<Passage> getAllPassages();
    Passage createPassage();
    ByteArrayOutputStream generatePassagePDF();
}
