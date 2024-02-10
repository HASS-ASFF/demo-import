package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.BilanActif.BilanActif;
import com.api.demoimport.entity.BilanActif.FormatUtils;
import com.api.demoimport.repository.BalanceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelBalanceDetailServiceImpl implements ExcelBalanceDetailService{

    // Injection des dépendances
    @Autowired
    private BalanceDetailRepository repository;

    @Autowired
    private ExcelHelperServiceImpl excelHelperServiceImpl;

    // Méthode pour sauvegarder les données d'un fichier Excel de type BalanceDetail
    public void save(MultipartFile file) {
        try {
            List<BalanceDetail> balanceDetails = excelHelperServiceImpl.excelToBalanceDetail(file.getInputStream());
            repository.saveAll(balanceDetails);
    // Gestion des exceptions en cas d'échec de la lecture du fichier ou conversion
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to store excel data: ");
        }
    }


    // Méthode pour récupérer tous les balance detail
    public List<BalanceDetail> getBalanceDetails() {
        return repository.findAll();
    }

    // recuperer les donnees brute class 2
    public List<BilanActif> getClassTwo(){

        List<Object[]> resultsrequest =  repository.getBilanC2();

        return ConvertToBilanActif(resultsrequest);

    }

    // recuperer les donnees brute class 3
    public List<BilanActif> getClassThree(){

        List<Object[]> resultsrequest =  repository.getBilanC3();

        List<BilanActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClasses(bilanActifs);

        return bilanActifs;
    }

    // recuperer les donnees brute class 5
    public List<BilanActif> getClassFive(){

        List<Object[]> resultsrequest =  repository.getBilanC5();

        List<BilanActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClasses(bilanActifs);

        return bilanActifs;
    }

    // Convert donnees brute excel au type BilanActif
    public List<BilanActif> ConvertToBilanActif(List<Object[]> resultsrequest){

        List<BilanActif> bilansActifs = new ArrayList<>();

    // Parcourir les résultats et convertir chaque élément en BilanActif
        for (Object[] resultat : resultsrequest) {

            String n_compte =  resultat[0].toString();
            String libelle = (String) resultat[1];
            Double brut = (Double) resultat[2];
            Double totalAmort = null;
            Double net = null;

            if(n_compte.startsWith("2")){
                 totalAmort = (Double) resultat[3];
                 net = (Double) resultat[4];
            }
            else{
                 totalAmort = null;
                net = FormatUtils.formatDecimal(brut);
            }

            BilanActif bilanActif = new BilanActif(n_compte,libelle, FormatUtils.formatDecimal(brut), totalAmort
                    , FormatUtils.formatDecimal(net));
            bilansActifs.add(bilanActif);
        }

        return bilansActifs;
    }

    // Pour regrouper les classes 3 et 5
    public void regroupClasses(List<BilanActif> bilanActifs){
        Map<String, BilanActif> mapBilanActif = new HashMap<>();

        // Regrouper les éléments par les trois premiers chiffres du compte
        for (BilanActif val : bilanActifs) {
            String n_sous_compte = val.getN_compte().substring(0, 3);
            String n_compte = n_sous_compte + "00000"; // Ajout de 5 zéros pour avoir 8 chiffres

            BilanActif bilanActif = mapBilanActif.get(n_sous_compte);
            if (bilanActif == null) {
                bilanActif = new BilanActif();
                mapBilanActif.put(n_sous_compte, bilanActif);
            }


            bilanActif.setBrut((bilanActif.getBrut()  != null ? bilanActif.getBrut() : 0)
                    + (val.getBrut() != null ? val.getBrut() : 0));

            bilanActif.setBrut(FormatUtils.formatDecimal(bilanActif.getBrut()));
            bilanActif.setNet(FormatUtils.formatDecimal(bilanActif.getBrut()));

            // Mettre à jour le champ libelle
            val.setLibelle("");
            // Mettre à jour le n_compte
            bilanActif.setN_compte(n_compte);

            System.out.println(bilanActif.getN_compte());

        }

        // Mettre à jour la liste originale
        bilanActifs.clear();
        System.out.println(mapBilanActif.values());
        bilanActifs.addAll(mapBilanActif.values());
    }
}
