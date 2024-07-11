package com.api.demoimport.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String ice;

    @Column(name = "social_reason")
    private String socialReason;

    @Column(name = "legal_form")
    private String legalForm;

    @Column(name = "fix_tel")
    private String fixTel;

    @Column(name = "tel")
    private String tel;

    // siege social
    @Column(length = 200)
    @NotEmpty(message = "Ce champ ne doit pas Etre vide")
    private String headOffice;

    @Column(name = "descreption")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "gmt")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date lastEdit;

    @Column
    private String patente;

    @Column(name = "tax_identification")
    private String taxIdentification;

    @Column
    private String rc;

    @Column(length = 200)
    private String firstName;

    @Column(length = 200)
    private String lastName;

    @Column
    @Email(message = "non valide mail")
    private String email;

    @Column
    @Email(message = "non valide mail")
    private String email_2;

    @Column(length = 200)
    private String zone;

    @Column(length = 50)
    private String sector;

    @Column(length = 50)
    private String subSector;

    @Column(length = 50)
    private String branche;

    @Column(length = 200)
    private String activityPrincipales;

    @Column(length = 200)
    private String delaisDePaiement;

    @Column(length = 200)
    private String plafondCredits;

    @Column(length = 200)
    private String regimeTVA;

    @Column(length = 200)
    private String etat;

    @Column(length = 50)
    private String appType;

    /*@OneToMany(mappedBy = "provider", cascade = CascadeType.PERSIST)
    private List<Purchase> purchases = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;*/

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    // static function check if provider exist in list
    public static Integer search(List<String[]> searchList, String key) {
        int position = 0;
        for (String[] strings : searchList) {
            if (strings[0].equals(key)) {
                return position;
            }
            position += 1;

        }
        return null;
    }
}