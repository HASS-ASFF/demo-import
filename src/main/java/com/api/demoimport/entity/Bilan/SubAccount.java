package com.api.demoimport.entity.Bilan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubAccount {
    private String sub_account;
    private List<Bilan> values;
}
