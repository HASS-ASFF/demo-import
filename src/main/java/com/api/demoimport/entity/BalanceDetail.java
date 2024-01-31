package com.api.demoimport.entity;


import com.api.demoimport.repository.PlanComptableRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "compte",
            joinColumns = @JoinColumn(name = "compte_id"),
            inverseJoinColumns = @JoinColumn(name = "balance_detail_id"))
    private PlanComptable compte;

 /*   @ManyToOne(fetch = FetchType.LAZY)
    private Balance balance; */

}
