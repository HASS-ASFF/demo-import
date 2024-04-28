package com.api.demoimport.entity.Bilan;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Esg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Double exercice;
    @Column
    private Double exerciceP;

    // TO DO EXERCICE RELATION
}
