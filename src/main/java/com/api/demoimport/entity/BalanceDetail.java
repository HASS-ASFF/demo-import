package com.api.demoimport.entity;


import com.api.demoimport.repository.PlanComptableRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * An entity class representing a balance details.
 */
@Entity
@Getter
@Setter
public class BalanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Long the_class;

    @Column
    private String label;

    @Column
    private Double debitDex;

    @Column
    private Double creditDex;

    @Column
    private Double debitEx;

    @Column
    private Double creditEx;

    @Column
    private Double debitFex;

    @Column
    private Double creditFex;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private PlanComptable compte;

    @Column(nullable = true)
    private Long n_Compte;

    @ManyToOne
    @JoinColumn(name = "balance_id")
    private Balance balance;

}
