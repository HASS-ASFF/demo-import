package com.api.demoimport.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
public class Immobilisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String dateAquisition;
    @Column(precision = 3, scale = 2)
    private Double prixAquisition;
    @Column(precision = 3, scale = 2)
    private Double coutDeRevient;
    @Column(precision = 3, scale = 2)
    private Double amortAnterieur;
    @Column(precision = 3, scale = 2)
    private Double taux_amort;
    @Column(precision = 3, scale = 2)
    private Double amortDeduitBenefice;
    @Column(precision = 3, scale = 2)
    private Double dea;
    @Column(precision = 3, scale = 2)
    private Double deaGlobal;

}
