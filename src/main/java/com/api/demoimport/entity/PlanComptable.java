package com.api.demoimport.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PlanComptable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String libelle;
    @Column
    private Long niv_de_reg;
    @Column
    private Long no_compte;
    @Column
    private Long the_class;
    @Column
    private Long amort;

}
