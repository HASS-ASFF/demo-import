package com.api.demoimport.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "plan_comptable")
@Data
public class PlanComptable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle", nullable = true)
    private String libelle;
    @Column(name = "niv_de_reg", nullable = true)
    private Long niv_de_reg;

    @Column(name = "no_compte", nullable = true)
    private Long no_compte;
    @Column(name = "the_class", nullable = true)
    private Long the_class;

    @Column(name = "amort", nullable = true)
    private Long amort;

}
