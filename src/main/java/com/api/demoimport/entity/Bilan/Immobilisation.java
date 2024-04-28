package com.api.demoimport.entity.Bilan;

import com.api.demoimport.entity.Exercice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @JoinColumn(name = "exercice_id")
    private Exercice exercice;

}
