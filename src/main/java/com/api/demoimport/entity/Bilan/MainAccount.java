package com.api.demoimport.entity.Bilan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainAccount {
    private String main_account;
    private List<SubAccount> subAccounts;
}
