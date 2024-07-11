package com.api.demoimport.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DATE
    private LocalDate date;

    // COMPTE (foreign key)
    @ManyToOne
    @JoinColumn(name = "account_id")
    private PlanComptable account;

    // JOURNAL AUX
    private String journalAux;

    // PARTENAIRE
    private String partner;

    // N° DOC
    private String documentNumber;

    // DETAILS.
    private String details;

    // REF PAIMENT
    private String paymentRef;

    // PERIODE TVA
    private String tvaPeriod;

    // Montant HT
    private Double amountExclTax;

    // TVA
    private Double tvaRate;

    // Montant TVA
    private Double tvaAmount;

    // Total TTC
    private Double totalInclTax;

    @ManyToOne
    @JoinColumn(name = "id_company")
    private Societe societe;

    @ManyToOne
    @JoinColumn(name = "provide_partner")
    private Provider provider;


}
