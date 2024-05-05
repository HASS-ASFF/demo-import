package com.api.demoimport.entity.Bilan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * An entity class representing a DetailCPC.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailCPC {
    private Long id;
    private String poste;
    private String name;
    private Double exercice;
    private Double exerciceP;

}
