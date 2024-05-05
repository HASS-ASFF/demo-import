package com.api.demoimport.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * An entity class representing an exercice.
 */
@Entity
@Getter
@Setter
public class Exercice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    @Column
    private Date dateExercice;

    @Column
    private String company_name;

    @ManyToOne
    @JoinColumn(name = "balance_id")
    private Balance balance;
}
