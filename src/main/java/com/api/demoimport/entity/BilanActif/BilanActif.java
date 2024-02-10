package com.api.demoimport.entity.BilanActif;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilanActif {


    private String n_compte;
    private String libelle;
    private Double brut;
    private Double total_amo;
    private Double net;


    @Override
    public String toString(){

        return libelle +" " + brut+" " + total_amo+" " +net;
    }

    public List<String> toList() {
        return Arrays.asList(n_compte, libelle, String.valueOf(brut), String.valueOf(total_amo), String.valueOf(net));
    }


    /*public static boolean isEmpty(BilanActif bilanActif) {
        return ObjectUtils.isEmpty(bilanActif);
    }*/
}
