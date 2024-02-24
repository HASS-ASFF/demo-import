package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Bilan.FormatUtils;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.service.BilanService.AccountDataManagerServiceImpl;
import com.api.demoimport.service.ExcelService.ExcelHelperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class BalanceDetailServiceImpl implements BalanceDetailService {

    // Dependency injection
    @Autowired
    private BalanceDetailRepository repository;

    @Autowired
    private ExcelHelperServiceImpl excelHelperServiceImpl;

    @Autowired
    private AccountDataManagerServiceImpl accountDataManagerService;

    // Save file excel balance and retrieve data & save it to db
    public void save(MultipartFile file,String date) {
        try {
            List<BalanceDetail> balanceDetails = excelHelperServiceImpl.excelToBalanceDetail(file.getInputStream(),date);
            repository.saveAll(balanceDetails);
    // In case we have  parsing or I/O error
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to store excel data: ");
        }
    }


    // Get all details of a balance
    public List<BalanceDetail> getBalanceDetails() {
        return repository.findAll();
    }

    // fetching data for the class 1
    @Override
    public List<SubAccountPassif> getClassOne(String dateBilan){

        List<Object[]> resultsrequest =  repository.getBilanC1(dateBilan);


        //regroupClassesP(bilanPassifs);

        return ConvertToBilanPassif(resultsrequest);

    }

    // fetching data for the class 2
    @Override
    public List<SubAccountActif> getClassTwo(String dateBilan){

        List<Object[]> resultsrequest =  repository.getBilanC2(dateBilan);

        List<SubAccountActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClassesA(bilanActifs);


        return bilanActifs;

    }

    // fetching data for the class 3
    @Override
    public List<SubAccountActif> getClassThree(String dateBilan){

        List<Object[]> resultsrequest =  repository.getBilanC3(dateBilan);

        List<SubAccountActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClassesA(bilanActifs);

        return bilanActifs;
    }

    // fetching data for the class 4
    @Override
    public List<SubAccountPassif> getClassFour(String dateBilan) {
        List<Object[]> resultsrequest =  repository.getBilanC4(dateBilan);

        List<SubAccountPassif> bilanPassifs = ConvertToBilanPassif(resultsrequest);

        regroupClassesP(bilanPassifs);

        return bilanPassifs;
    }

    // fetching data for the class 5 Actif
    @Override
    public List<SubAccountActif> getClassFiveActif(String dateBilan){

        List<Object[]> resultsrequest =  repository.getBilanC5A(dateBilan);

        List<SubAccountActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClassesA(bilanActifs);

        return bilanActifs;
    }

    // fetching data for the class 5 Passif
    @Override
    public List<SubAccountPassif> getClassFivePassif(String dateBilan) {
        List<Object[]> resultsrequest =  repository.getBilanC5P(dateBilan);

        List<SubAccountPassif> bilanPassifs = ConvertToBilanPassif(resultsrequest);

        regroupClassesP(bilanPassifs);

        return bilanPassifs;
    }

    // Convert object to SubAccountActif
    @Override
    public List<SubAccountActif> ConvertToBilanActif(List<Object[]> resultsrequest){

        List<SubAccountActif> bilansActifs = new ArrayList<>();

    try{
        // Parcourir les résultats et convertir chaque élément en Bilan
        for (Object[] resultat : resultsrequest) {

            String mainA = getMainAccount(resultat[0].toString());
            String n_compte =  resultat[0].toString();
            SubAccountActif subAccountActif = getSubAccountActif(resultat, n_compte, mainA);
            bilansActifs.add(subAccountActif);
        }

        return bilansActifs;

    }catch (RuntimeException e){
        throw new RuntimeException("Failed to convert object to SubAccountActif (convert method) "+e.getMessage());
    }

    }

    // Sub method in case if we are dealing with class 2 or other actif classes (3/5)
    private static SubAccountActif getSubAccountActif(Object[] resultat, String n_compte, String mainA) {
        try{
            String libelle = (String) resultat[1];
            Double brut = (Double) resultat[2];
            if(brut == 0) brut =null;
            Double net=brut;
            if(n_compte.startsWith("2")){
                Double totalAmort = (Double) resultat[3];
                if (totalAmort == 0) {totalAmort =null;}
                else {FormatUtils.formatDecimal(totalAmort);
                net=FormatUtils.formatDecimal(brut-totalAmort);}
                return new SubAccountActif(mainA, n_compte, libelle,
                       brut, totalAmort, net);
            }
            else{
                return new SubAccountActif(mainA, n_compte, libelle,
                        brut, net);
            }

        }catch(RuntimeException e){
            throw  new RuntimeException("Failed to get data of actif bilan  "+ e.getMessage());
        }
    }

    // Convert object to SubAccountPassif
    @Override
    public List<SubAccountPassif> ConvertToBilanPassif(List<Object[]> resultsrequest) {

        List<SubAccountPassif> bilansPassifs = new ArrayList<>();

        // Parcourir les résultats et convertir chaque élément en Bilan
        for (Object[] resultat : resultsrequest) {

            String mainA = getMainAccount(resultat[0].toString());
            String n_compte =  resultat[0].toString();
            String libelle = (String) resultat[1];
            Double brut = (Double) resultat[2];

            SubAccountPassif bilanPassif = new SubAccountPassif();
            bilanPassif.setMainAccount(mainA);
            bilanPassif.setN_compte(n_compte);
            bilanPassif.setLibelle(libelle);
            bilanPassif.setBrut(brut == 0 ? null : FormatUtils.formatDecimal(brut));
            bilansPassifs.add(bilanPassif);
        }

        return bilansPassifs;
    }

    // Regroup actif classes in case we have the same account number at beginning
   @Override
   public void regroupClassesA(List<SubAccountActif> bilanActifdata) {
       Map<String, SubAccountActif> mapBilanActif = new HashMap<>();

       try{
           // Regrouper les éléments par les trois premiers chiffres du compte
           for (SubAccountActif val : bilanActifdata) {
               String n_sous_compte = val.getN_compte().substring(0, 3);
               String n_compte = n_sous_compte + "00000"; // Ajout de 5 zéros pour avoir 8 chiffres
               String libelle = val.getLibelle();

               SubAccountActif bilanActif = mapBilanActif.get(n_compte);
               if (bilanActif == null) {
                   bilanActif = new SubAccountActif();
                   bilanActif.setMainAccount(getMainAccount(n_compte));
                   bilanActif.setN_compte(n_compte);
                   bilanActif.setLibelle(libelle);
                   bilanActif.setBrut(val.getBrut());
                   bilanActif.setTotal_amo(val.getTotal_amo());
                   bilanActif.setNet(val.getNet());
                   bilanActif.setNetN(val.getNetN());
                   mapBilanActif.put(n_sous_compte, bilanActif);
               }else {

                   if (bilanActif.getTotal_amo() != null && val.getTotal_amo() != null) {
                       bilanActif.setTotal_amo(FormatUtils.
                               formatDecimal(bilanActif.getTotal_amo() + val.getTotal_amo()));
                   }
                   if (bilanActif.getBrut() != null && val.getBrut() != null) {
                       bilanActif.setBrut(bilanActif.getBrut() + val.getBrut());

                       bilanActif.setBrut(FormatUtils.formatDecimal(bilanActif.getBrut()));
                       bilanActif.setNet(FormatUtils.formatDecimal(bilanActif.getBrut()));
                   }
               }
           }
       }catch(RuntimeException e){
           throw new RuntimeException("Failed to regroup data actif "+ e.getMessage());
       }

   }

    // Regroup passif classes in case we have the same account number at beginning
    @Override
    public void regroupClassesP(List<SubAccountPassif> bilanPassifdata) {
        Map<String, SubAccountPassif> mapBilanPassif = new HashMap<>();

        // Regrouper les éléments par les trois premiers chiffres du compte
        for (SubAccountPassif val : bilanPassifdata) {
            String n_sous_compte = val.getN_compte().substring(0, 3);
            String n_compte = n_sous_compte + "00000"; // Ajout de 5 zéros pour avoir 8 chiffres
            String libelle = val.getLibelle();
            SubAccountPassif bilanPassif = mapBilanPassif.get(n_sous_compte);

            if (bilanPassif == null) {
                bilanPassif = new SubAccountPassif(getMainAccount(val.getN_compte()),
                        n_compte, libelle, val.getBrut(), val.getBrutP());
                mapBilanPassif.put(n_sous_compte, bilanPassif);
            }else{
                if (bilanPassif.getBrut() != null && val.getBrut() != null) {
                bilanPassif.setBrut(bilanPassif.getBrut() + val.getBrut());
                bilanPassif.setBrut(FormatUtils.formatDecimal(bilanPassif.getBrut()));
                }
            }



        }

    }

    // Get the Main account of a subaccount
    @Override
    public String getMainAccount(String n_compte) {

        char prefix = n_compte.charAt(0);

        switch(prefix){
            case '1':
                return accountDataManagerService.getMainAccountOne(n_compte.substring(0,2));

            case '2':
                return accountDataManagerService.getMainAccountTwo(n_compte.substring(0,2));

            case '3':
                return accountDataManagerService.getMainAccountThree(n_compte.substring(0,2));

            case '4':
                return accountDataManagerService.getMainAccountFour(n_compte.substring(0,2));

            case '5':
                return accountDataManagerService.getMainAccountFive(n_compte.substring(0,2));

            default:
                return null;
        }
    }
}
