package com.api.demoimport.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * An entity class representing an immobilisation.
 */
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
    private Date dateAquisition;
    @Column
    private Double prixAquisition;
    @Column
    private Double coutDeRevient;
    @Column
    private Double amortAnterieur;
    @Column
    private Double taux_amort;
    @Column
    private Double amortDeduitBenefice;
    @Column
    private Double dea;
    @Column
    private Double deaGlobal;
    @ManyToOne
    @JoinColumn(name = "balance_id")
    private Balance balance;

}
