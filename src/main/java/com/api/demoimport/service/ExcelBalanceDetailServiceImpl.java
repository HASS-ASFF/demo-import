package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Bilan.Bilan;
import com.api.demoimport.entity.Bilan.FormatUtils;
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

    // recuperer les donnees brute class 1
    @Override
    public List<Bilan> getClassOne(){

        List<Object[]> resultsrequest =  repository.getBilanC1();

        return ConvertToBilanActif(resultsrequest);

    }

    // recuperer les donnees brute class 2
    @Override
    public List<Bilan> getClassTwo(){

        List<Object[]> resultsrequest =  repository.getBilanC2();

        return ConvertToBilanActif(resultsrequest);

    }

    // recuperer les donnees brute class 3
    @Override
    public List<Bilan> getClassThree(){

        List<Object[]> resultsrequest =  repository.getBilanC3();

        List<Bilan> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClasses(bilanActifs);

        return bilanActifs;
    }

    @Override
    public List<Bilan> getClassFour() {
        List<Object[]> resultsrequest =  repository.getBilanC4();

        List<Bilan> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClasses(bilanActifs);

        return bilanActifs;
    }

    // recuperer les donnees brute class 5
    @Override
    public List<Bilan> getClassFiveActif(){

        List<Object[]> resultsrequest =  repository.getBilanC5A();

        List<Bilan> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClasses(bilanActifs);

        return bilanActifs;
    }

    @Override
    public List<Bilan> getClassFivePassif() {
        List<Object[]> resultsrequest =  repository.getBilanC5P();

        List<Bilan> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClasses(bilanActifs);

        return bilanActifs;
    }

    // Convert donnees brute excel au type Bilan
    public List<Bilan> ConvertToBilanActif(List<Object[]> resultsrequest){

        List<Bilan> bilansActifs = new ArrayList<>();

    // Parcourir les résultats et convertir chaque élément en Bilan
        for (Object[] resultat : resultsrequest) {

            String n_compte =  resultat[0].toString();
            String libelle = (String) resultat[1];
            Bilan bilanActif = getActif(resultat, n_compte, libelle);
            bilansActifs.add(bilanActif);
        }

        return bilansActifs;
    }

    // Filter data classes
    private static Bilan getActif(Object[] resultat, String n_compte, String libelle) {
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

        Bilan bilanActif = new Bilan(n_compte, libelle, FormatUtils.formatDecimal(brut), totalAmort
                , FormatUtils.formatDecimal(net));
        return bilanActif;
    }

    // Pour regrouper les classes 3 et 5
    public void regroupClasses(List<Bilan> bilanActifs){
        Map<String, Bilan> mapBilanActif = new HashMap<>();

        // Regrouper les éléments par les trois premiers chiffres du compte
        for (Bilan val : bilanActifs) {
            String n_sous_compte = val.getN_compte().substring(0, 3);
            String n_compte = n_sous_compte + "00000"; // Ajout de 5 zéros pour avoir 8 chiffres

            Bilan bilanActif = mapBilanActif.get(n_sous_compte);
            if (bilanActif == null) {
                bilanActif = new Bilan();
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

        }

        // Mettre à jour la liste originale
        bilanActifs.clear();
        bilanActifs.addAll(mapBilanActif.values());
    }
}
