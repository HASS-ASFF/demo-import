package com.api.demoimport.entity.BilanActif;

import com.api.demoimport.entity.BilanActif.BilanActif;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubAccount {
    private String sub_account;
    private List<BilanActif> values;
}
