package com.api.demoimport.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum PassageCategory {
    RESULTAT_NET_COMPTABLE("RESULTAT NET COMPTABLE",
            Arrays.asList(
            "Bénéfice net",
            "Perte nette"
    )),
    REINTEGRATIONS_FISCALES("REINTEGRATIONS FISCALES",
            Arrays.asList(
                    "COURANTES",
                    "NON COURANTES"
            )),
    DEDUCTIONS_FISCALES("DEDUCTIONS FISCALES",
            Arrays.asList(
                    "COURANTES",
                    "NON COURANTES"
            )),
    RESULTAT_BRUT_FISCAL("RESULTAT BRUT FISCAL",
            Arrays.asList(
                    "Bénéfice brut si T1> T2 (A) ",
                    "Déficit brut fiscal si T2> T1 (B)"
            )),
    REPORTS_DEFICITAIRES_IMPUTES("REPORTS DEFICITAIRES IMPUTES (C) (1)",
            Arrays.asList(
                    "Exercice n-4",
                    "Exercice n-3",
                    "Exercice n-2",
                    "Exercice n-1"
            )),
    RESULTAT_NET_FISCAL("RESULTAT NET FISCAL",
            Arrays.asList(
                    "Bénéfice net fiscal ( A - C) (OU)",
                    "Déficit net fiscal (B)"
            )),
    CUMUL_DES_AMORTISSEMENTS_FISCALEMENT_DIFFERES("CUMUL DES AMORTISSEMENTS FISCALEMENT DIFFERES",
            Arrays.asList("")),

    CUMUL_DES_DEFICITS_FISCAUX_RESTANT_A_REPORTER("CUMUL DES DEFICITS FISCAUX RESTANT A REPORTER",
            Arrays.asList(
                    "Exercice n-4",
                    "Exercice n-3",
                    "Exercice n-2",
                    "Exercice n-1"
            ));

    @Setter
    private String main_name;
    @Setter
    private List<String> sub_name;

    PassageCategory(String m_name, List<String> s_name) {
        this.main_name = m_name;
        this.sub_name = s_name;
    }
}
