package com.api.demoimport.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
@Getter
public enum DetailCPCCategoryClass6 {
    ACHAT_REVENDUS("611","Achats revendus de marchandises"
            , Arrays.asList(
            "6111/6112 - Achats de marchandises",
            "6112/6114/6118/6119 - Variation des stocks de marchandises (±)"
    )),
    ACHAT_CONSOMES("612","Achats consommés de matières et fournitures"
            , Arrays.asList(
            "6121 - Achat de matières premières",
            "61241 - Variation des stocks de matières premières (+)",
            "6122/6123 - Achats de matériel et fournitures consommables et d'emballages",
            "61242/61243 - Variation des stocks de matières, fournitures et emballages (±)",
            "6125 - Achats non stockés de matières et de fournitures",
            "6126 - Achats de travaux, études et prestations de services"
    )),
    AUTRES_CHARGES_EXTERNES("613/614","Autres charges externes"
            , Arrays.asList(
            "6131 - Locations et charges locatives",
            "6132 - Redevances de crédit-bail",
            "6133 - Entretien et réparations",
            "6134 - Primes d'assurances",
            "6135 - Rémunérations du personnel extérieur à l'entreprise",
            "6136 - Rémunérations d'intermédiaires et honoraires",
            "6137 - Redevances pour brevets, marques, droits.......",
            "6142 - Transports",
            "6143 - Déplacements, missions et réceptions",
            "6144/6146/6147/6148/6149 - Reste du poste des autres charges externes"

    )),
    CHARGES_PERSONEL("617","Charges de personnel"
            , Arrays.asList(
            "6171 - Rémunération du personnel",
            "6174 - Charges sociales",
            "6176 - Reste du poste des charges de personnel"
    )),
    AUTRES_CHARGES_EXPLOITATION("618","Autres charges d'exploitation"
            , Arrays.asList(
            "6181 - Jetons de présence",
            "6182 - Pertes sur créances irrécouvrables",
            "6185/6186 - Reste du poste des autres charges d'exploitation"
    )),
    AUTRES_CHARGES_FINANCIERES("638","Autres charges financières"
            , Arrays.asList(
            "6385 - Charges nettes sur cessions de titres et valeurs de placement",
            "6382/6386 - Reste du poste des autres charges financières"
    )),
    AUTRES_CHARGES_NON_COURANTES("658","Autres charges non courantes"
            , Arrays.asList(
            "6581 - Pénalités sur marchés et dédits",
            "6582 - Rappels d'impôts (autres qu'impôts sur les résultats)",
            "6583 - Pénalités et amendes fiscales",
            "6585 - Créances devenues irrécouvrables",
            "6586 - Reste du poste des autres charges non courantes"
    ));



    @Setter
    private  String number;

    @Setter
    private  String label;

    @Setter
    private List<String> sous_compte;

    DetailCPCCategoryClass6(String number, String label, List<String> sous_compte) {
        this.number = number;
        this.label = label;
        this.sous_compte = sous_compte;
    }
}
