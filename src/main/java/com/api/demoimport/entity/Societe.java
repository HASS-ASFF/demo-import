package com.api.demoimport.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Societe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // socialReason
    @Column(name = "social_reason",unique = true)
    private String socialReason;

    @Column
    private String image;

    @Column
    private String signature;

    @Column(unique = true, length = 16)
    @NumberFormat
    @NotBlank(message = "ce champs ne doit pas être vide")
    private String ice;

    @Column(length = 30)
    @NumberFormat
    private String fixTel;

    @Column(name = "tel")
    // @NotBlank(message = "ce champs ne doit pas être vide")
    private String tel;

    @Column(name = "tel2")
    private String tel2;

    @Column(length = 200)
    // @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String swift;

    @Column(name = "legal_form")
    @NotBlank(message = "ce champs ne doit pas être vide")
    private String legalForm;

    @Column(name = "tax_identification")
    // @NotBlank(message = "ce champs ne doit pas être vide")
    private String taxIdentification;

    @Column(length = 200)
    // @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String rc;

    @Column(length = 200)
    // @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String patente;

    @Column(length = 200)
    private String agrement;

    @Column(length = 20)
    // @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String cnss;// numero d'affiliation

    @Column
    private String rib;

    @Column(length = 200)
    @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String iban;

    // siege social
    @Column(length = 200)
    @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String headOffice;

    @Column
    private String bank;

    @Column
    private String agency;

    @Column
    @NotBlank(message = "ce champs ne doit pas être vide")
    @Email
    private String email;

    @Column
    private String email_2;

    @Column(length = 200)
    // @NotEmpty(message = "Ce champ ne doit pas être vide")
    private String gerant;

    @Column(length = 50)
    private String gFunction;

    @Column(length = 50)
    private String gEmail;

    @Column(length = 50)
    private String gPhone;

    // jour de repos
    @Column(length = 50)
    private String restDay = "Non spécifié";

    @Column(length = 50)
    private String effectif;

    @Column(length = 50)
    private String sector;

    @Column(length = 50)
    private String subSector;

    @Column(length = 200)
    private String activity;

    @Column(length = 50)
    private String position;

    @Column(length = 200)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(length = 50)
    private Date start_activity_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(length = 50)
    private Date createdAt = new Date();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(length = 50)
    private Date updatedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}