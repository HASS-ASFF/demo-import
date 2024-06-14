package com.api.demoimport.service;

public interface Updatable {
    String getMainAccountAccess();
    String getN_compteAccess();
    Double getCurrentExercice();
    void setPreviousExercice(Double exerciceP);

}
