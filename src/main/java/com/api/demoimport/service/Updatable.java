package com.api.demoimport.service;

public interface Updatable {
    String getMainAccount();
    String getN_compte();
    Double getCurrentExercice();
    void setPreviousExercice(Double exerciceP);

}
