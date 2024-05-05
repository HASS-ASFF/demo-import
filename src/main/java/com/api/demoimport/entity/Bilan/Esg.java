package com.api.demoimport.entity.Bilan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Esg {
    private Long id;
    private String name;
    private Double exercice;
    private Double exerciceP;

}
