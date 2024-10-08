package com.api.demoimport.entity.Bilan;

import com.api.demoimport.service.Updatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * An entity class representing a ESG.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Esg implements Updatable {
    private Long id;
    private String name;
    private Double exercice;
    private Double exerciceP;

    @Override
    public String getMainAccountAccess() {
        return this.name;
    }

    @Override
    public String getN_compteAccess() {
        return null;
    }

    @Override
    public Double getCurrentExercice() {
        return this.exercice;
    }

    @Override
    public void setPreviousExercice(Double exerciceP) {
        this.exerciceP = exerciceP;
    }

}
