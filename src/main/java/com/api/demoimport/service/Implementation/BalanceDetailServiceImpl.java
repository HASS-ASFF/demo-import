package com.api.demoimport.service.Implementation;

import com.api.demoimport.dto.Tvadto;
import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Bilan.FormatUtils;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import com.api.demoimport.repository.BalanceDetailRepository;
import com.api.demoimport.service.BalanceDetailService;
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

    /**
     * Processing balance detail data involves several steps:
     * - Parsing Excel data to extract balance details and storing them in the database.
     * - Retrieving balance details for different account classes (e.g., class 1, class 2, etc.).
     * - Regrouping balance detail data based on account numbers and calculating total values.
     * - Converting retrieved data into appropriate subaccount objects (Actif or Passif).
     * - Handling CPC (Compte de Produit et Charge) accounts, where both debit and credit values may exist.
     *   (A for Actif and P for Passif)
     */

    // Save file excel balance and retrieve data & save it to db
    @Override
    public void save(MultipartFile file,String date,String company_name) {
        try {
            List<BalanceDetail> balanceDetails = excelHelperServiceImpl.excelToBalanceDetail(file.getInputStream(),date,company_name);
            repository.saveAll(balanceDetails);
    // In case we have  parsing or I/O error
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to store excel data: ");
        }
    }


    // Get all details of a balance
    @Override
    public List<BalanceDetail> getBalanceDetails() {
        return repository.findAll();
    }

    // fetching data for the class 1
    @Override
    public List<SubAccountPassif> getClassOne(String dateBilan,String company_name){

        List<Object[]> resultsrequest =  repository.getBilanC1(dateBilan,company_name);

        return ConvertToBilanPassif(resultsrequest);

    }

    // fetching data for the class 2
    @Override
    public List<SubAccountActif> getClassTwo(String dateBilan,String company_name){

        List<Object[]> resultsrequest =  repository.getBilanC2(dateBilan,company_name);

        List<SubAccountActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        bilanActifs.forEach(x->System.out.println(
                x.getMainAccount()+" "+x.getLibelle()+" "+x.getBrut()+" "+x.getN_compte()
        ));

        regroupClassesA(bilanActifs);


        return bilanActifs;

    }

    // fetching data for the class 3
    @Override
    public List<SubAccountActif> getClassThree(String dateBilan,String company_name){

        List<Object[]> resultsrequest =  repository.getBilanC3(dateBilan,company_name);

        List<SubAccountActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClassesA(bilanActifs);

        return bilanActifs;
    }

    // fetching data for the class 4
    @Override
    public List<SubAccountPassif> getClassFour(String dateBilan,String company_name) {
        List<Object[]> resultsrequest =  repository.getBilanC4(dateBilan,company_name);

        List<SubAccountPassif> bilanPassifs = ConvertToBilanPassif(resultsrequest);

        regroupClassesP(bilanPassifs);

        return bilanPassifs;
    }

    // fetching data for the class 5 Actif
    @Override
    public List<SubAccountActif> getClassFiveActif(String dateBilan,String company_name){

        List<Object[]> resultsrequest =  repository.getBilanC5A(dateBilan,company_name);

        List<SubAccountActif> bilanActifs = ConvertToBilanActif(resultsrequest);

        regroupClassesA(bilanActifs);

        return bilanActifs;
    }

    // fetching data for the class 5 Passif
    @Override
    public List<SubAccountPassif> getClassFivePassif(String dateBilan,String company_name) {
        List<Object[]> resultsrequest =  repository.getBilanC5P(dateBilan,company_name);

        List<SubAccountPassif> bilanPassifs = ConvertToBilanPassif(resultsrequest);

        regroupClassesP(bilanPassifs);

        return bilanPassifs;
    }

    // fetching data for the class 6
    @Override
    public List<SubAccountCPC> getClassSix(String date, String company_name) {
        List<Object []> resultsrequest = repository.getCPCC6(date,company_name);

        List<SubAccountCPC> subAccountCPCS = ConvertToCPC(resultsrequest);

        subAccountCPCS.forEach(x->System.out.println(x.getMainAccount()
                +" "+ x.getLibelle() + " " + x.getBrut()));

        //regroupClassesCPC(subAccountCPCS);

        return subAccountCPCS;
    }

    // fetching data for the class 7
    @Override
    public List<SubAccountCPC> getClassSeven(String date, String company_name) {
        List<Object []> resultsrequest = repository.getCPCC7(date,company_name);

        List<SubAccountCPC> subAccountCPCS = ConvertToCPC(resultsrequest);



        //regroupClassesCPC(subAccountCPCS);

        return subAccountCPCS;
    }

    @Override
    public List<Tvadto> getTvaData(String date, String company_name) {
        List<Object []> resultrequest;
        return null;
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
                Double netn = (Double) resultat[4];
                if (totalAmort == 0) {totalAmort =null;}
                else {FormatUtils.formatDecimal(totalAmort);
                net=FormatUtils.formatDecimal(brut-totalAmort);}
                return new SubAccountActif(mainA, n_compte, libelle,
                       brut, totalAmort, net, netn);
            }
            else{
                Double netn = (Double) resultat[3];
                return new SubAccountActif(mainA, n_compte, libelle,
                        brut, net, netn);
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
            //Double brutp = (Double) resultat[3];

            SubAccountPassif bilanPassif = new SubAccountPassif();
            bilanPassif.setMainAccount(mainA);
            bilanPassif.setN_compte(n_compte);
            bilanPassif.setLibelle(libelle);
            bilanPassif.setBrut(brut == 0 ? null : FormatUtils.formatDecimal(brut));
            //bilanPassif.setBrutP(brutp == 0 ? null : FormatUtils.formatDecimal(brutp));
            bilansPassifs.add(bilanPassif);
        }

        return bilansPassifs;
    }

    @Override
    public List<SubAccountCPC> ConvertToCPC(List<Object[]> resultsrequest) {
        List<SubAccountCPC> accountCPCS = new ArrayList<>();

        // Parcourir les résultats et convertir chaque élément en Bilan
        for (Object[] resultat : resultsrequest) {

            String mainA = getMainAccount(resultat[0].toString());
            String n_compte =  resultat[0].toString();
            String libelle = (String) resultat[1];
            Double brut = (Double) resultat[2];

            SubAccountCPC subAccountCPC = new SubAccountCPC();
            subAccountCPC.setMainAccount(mainA);
            subAccountCPC.setN_compte(n_compte);
            subAccountCPC.setLibelle(libelle);
            subAccountCPC.setBrut(brut == 0 ? null : FormatUtils.formatDecimal(brut));
            accountCPCS.add(subAccountCPC);
        }

        return accountCPCS;
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

       // Effacer la liste initiale
       bilanActifdata.clear();

       // Ajouter les éléments regroupés à la liste initiale
       for (Map.Entry<String, SubAccountActif> entry : mapBilanActif.entrySet()) {
           bilanActifdata.add(entry.getValue());
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

        // Effacer la liste initiale
        bilanPassifdata.clear();

        // Ajouter les éléments regroupés à la liste initiale
        for (Map.Entry<String, SubAccountPassif> entry : mapBilanPassif.entrySet()) {
            bilanPassifdata.add(entry.getValue());
        }

    }

    // Regroup CPC classes in case we have the same account number at beginning
    @Override
    public void regroupClassesCPC(List<SubAccountCPC> cpcAccount) {
        Map<String, SubAccountCPC> subAccountCPCMap = new HashMap<>();

        // Regrouper les éléments par les trois premiers chiffres du compte
        for (SubAccountCPC val : cpcAccount) {
            String n_sous_compte = val.getN_compte().substring(0, 3);
            String n_compte = n_sous_compte + "00000"; // Ajout de 5 zéros pour avoir 8 chiffres
            String libelle = val.getLibelle();
            SubAccountCPC subAccountCPC = subAccountCPCMap.get(n_sous_compte);

            if (subAccountCPC == null) {
                subAccountCPC = new SubAccountCPC(getMainAccount(n_compte),
                        n_compte, libelle, val.getBrut());
                subAccountCPCMap.put(n_sous_compte, subAccountCPC);
            }else{
                if (val.getBrut() == null){
                    subAccountCPC.setBrut(subAccountCPC.getBrut());
                }
                else {
                    subAccountCPC.setBrut(subAccountCPC.getBrut() + val.getBrut());
                }

                subAccountCPC.setBrut(FormatUtils.formatDecimal(subAccountCPC.getBrut()));
            }
        }

        // Effacer la liste initiale
        cpcAccount.clear();

        // Ajouter les éléments regroupés à la liste initiale
        for (Map.Entry<String, SubAccountCPC> entry : subAccountCPCMap.entrySet()) {
            cpcAccount.add(entry.getValue());
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

            case '6':
                return accountDataManagerService.getMainAccountSix(n_compte.substring(0,2));

            case '7':
                return accountDataManagerService.getMainAccountSeven(n_compte.substring(0,2));

            default:
                return null;
        }
    }
}
