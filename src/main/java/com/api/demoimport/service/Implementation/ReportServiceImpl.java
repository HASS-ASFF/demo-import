package com.api.demoimport.service.Implementation;

import com.api.demoimport.dto.Tvadto;
import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import com.api.demoimport.entity.Societe;
import com.api.demoimport.enums.*;
import com.api.demoimport.repository.SocieteRepository;
import com.api.demoimport.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    AccountDataManagerServiceImpl accountDataManagerServiceImpl;
    @Autowired
    BalanceDetailServiceImpl balanceDetailServiceImpl;
    @Autowired
    EsgServiceImpl esgService;
    @Autowired
    SocieteRepository societeRepository;
    @Autowired
    TvaServiceImpl tvaService;

    /**
     * Service implementation for managing reports, providing methods for exporting reports to PDF format.
     * Using AccountDataManagerServiceImpl and BalanceDetailServiceImpl to retrieve data for report generation.
     */


    // TO CHANGE (PATH)
    String path = "C:\\Users\\onizu\\OneDrive\\Bureau\\demo-import\\src\\main\\resources\\templates\\jasperReport\\";

    // EXPORT DATA PASSIF TO PDF
    public ByteArrayOutputStream exportReportPassif(String  date,String company_name) throws JRException {

        // TO CHECK
        String pathP = path+"BilanPassif.jrxml";

        // Generer automatiquement le bilan passif
        try{

            // CLASS ONE
            List<SubAccountPassif> ClassOne = balanceDetailServiceImpl.getClassOne(date,company_name);
            List<SubAccountPassif> FullClassOne = accountDataManagerServiceImpl.
                    processAccountDataP(ClassOne,"1");

            List<SubAccountPassif> dataset1 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOne, AccountCategoryClass1.CAPITAUX_PROPRES.getLabel());
            // CLASS SIX
            List<SubAccountCPC> ClassSix = balanceDetailServiceImpl.getClassSix(date,company_name);
            List<SubAccountCPC> FullClassSix = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSix,"6");
            // CLASS SEVEN
            List<SubAccountCPC> ClassSeven = balanceDetailServiceImpl.getClassSeven(date,company_name);
            List<SubAccountCPC> FullClassSeven = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSeven,"7");
            Double resultat_net = esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT NET DE L'EXERCICE");
            dataset1.get(10).setBrut(resultat_net);
            List<SubAccountPassif> dataset2 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOne,AccountCategoryClass1.CAPITAUX_PROPRES_ASSIMILES.getLabel());
            List<SubAccountPassif> dataset3 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOne,AccountCategoryClass1.DETTES_DE_FINANCEMENT.getLabel());
            List<SubAccountPassif> dataset4 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOne,AccountCategoryClass1.PROVISIONS_DURABLES_POUR_RISQUES_ET_CHARGES.getLabel());
            List<SubAccountPassif> dataset5 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOne,AccountCategoryClass1.ECARTS_DE_CONVERSION_PASSIF.getLabel());


            // CLASS FOUR
            List<SubAccountPassif> ClassFour = balanceDetailServiceImpl.getClassFour(date,company_name);
            List<SubAccountPassif> FullClassFour = accountDataManagerServiceImpl.
                            processAccountDataP(ClassFour,"4");
            List<SubAccountPassif> dataset6 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFour, AccountCategoryClass4.DETTES_DU_PASSIF_CIRCULANT.getLabel());
            List<SubAccountPassif> dataset7 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFour,AccountCategoryClass4.AUTRES_PROVISIONS_POUR_RISQUES_ET_CHARGES.getLabel());
            List<SubAccountPassif> dataset8 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFour,AccountCategoryClass4.ECARTS_DE_CONVERSION_PASSIF.getLabel());

            //CLASS FIVE PASSIF
            List<SubAccountPassif> ClassFiveP = balanceDetailServiceImpl.getClassFivePassif(date,company_name);
                    List<SubAccountPassif> FullClassFiveP =  accountDataManagerServiceImpl.
                            processAccountDataP(ClassFiveP,"5");
            List<SubAccountPassif> dataset9 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFiveP,AccountCategoryClass5.TRESORERIE_PASSIF.getLabel());


            // CREATE INSTANCES OF JRBEANCOLLECTIONDATASOURCE FOR OUR JAVA BEAN OBJECTS
            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(dataset1);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataset2);
            JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(dataset3);
            JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(dataset4);
            JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(dataset5);
            JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(dataset6);
            JRBeanCollectionDataSource dataSource7 = new JRBeanCollectionDataSource(dataset7);
            JRBeanCollectionDataSource dataSource8 = new JRBeanCollectionDataSource(dataset8);
            JRBeanCollectionDataSource dataSource9 = new JRBeanCollectionDataSource(dataset9);
            Map<String, Object> parameters = new HashMap<>();

            // SETTING PARAMETERS
            parameters.put("CapitauxA", dataSource1);
            parameters.put("CapitauxB", dataSource2);
            parameters.put("Dettes", dataSource3);
            parameters.put("Provisions", dataSource4);
            parameters.put("Ecarts", dataSource5);
            List<SubAccountPassif> totalListI = new ArrayList<>();
            totalListI.addAll(dataset2);
            totalListI.addAll(dataset3);
            totalListI.addAll(dataset4);
            totalListI.addAll(dataset5);
            parameters.put("DettesCirculant", dataSource6);
            parameters.put("AutreProvision", dataSource7);
            parameters.put("ElementCirculant", dataSource8);
            List<SubAccountPassif> totalListII = new ArrayList<>();
            totalListII.addAll(dataset6);
            totalListII.addAll(dataset7);
            parameters.put("TresoreriePassif", dataSource9);


            parameters.put("TotalCapitaux",accountDataManagerServiceImpl.GetTotalBrutAccountPassif(dataset1));
            parameters.put("TotalCapitauxN",accountDataManagerServiceImpl.GetTotalNetAccountPassif(dataset1));
            parameters.put("TotalI",accountDataManagerServiceImpl.GetTotalBrutAccountPassif(totalListI));
            parameters.put("TotalIN",accountDataManagerServiceImpl.GetTotalNetAccountPassif(totalListI));
            parameters.put("TotalII",accountDataManagerServiceImpl.GetTotalBrutAccountPassif(totalListII));
            parameters.put("TotalIIN",accountDataManagerServiceImpl.GetTotalNetAccountPassif(totalListII));
            parameters.put("TotalIII",accountDataManagerServiceImpl.GetTotalBrutAccountPassif(dataset9));
            parameters.put("TotalIIIN",accountDataManagerServiceImpl.GetTotalNetAccountPassif(dataset9));

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);

            return jasperConfiguration(pathP,parameters);

        }catch(RuntimeException e) {
            e.printStackTrace();
            String message = "Failed to report bilan passif " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }


    // EXPORT DATA ACTIF TO PDF
    public ByteArrayOutputStream exportReportActif(String date,String company_name) throws JRException {

        // TO CHECK
        String pathA = path+"BilanActif.jrxml";

        // Generer automatiquement le bilan actif
        try{

            // CLASS TWO
            List<SubAccountActif> ClassTwo = balanceDetailServiceImpl.getClassTwo(date,company_name);
            List<SubAccountActif> FullClassTwo = accountDataManagerServiceImpl.
                    processAccountDataA(ClassTwo,"2");

            List<SubAccountActif> dataset1 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwo, AccountCategoryClass2.IMMOBILISATION_NON_VALEURS.getLabel());
            List<SubAccountActif> dataset2 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwo,AccountCategoryClass2.IMMOBILISATION_INCORPORELLES.getLabel());
            List<SubAccountActif> dataset3 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwo,AccountCategoryClass2.IMMOBILISATION_CORPORELLES.getLabel());
            List<SubAccountActif> dataset4 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwo,AccountCategoryClass2.IMMOBILISATION_FINANCIERES.getLabel());
            List<SubAccountActif> dataset5 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwo,AccountCategoryClass2.ECART_CONVERSION_ACTIF.getLabel());

            // CLASS THREE
            List<SubAccountActif> ClassThree = balanceDetailServiceImpl.getClassThree(date,company_name);
            List<SubAccountActif> FullClassThree = accountDataManagerServiceImpl.
                    processAccountDataA(ClassThree,"3");

            List<SubAccountActif> dataset6 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassThree, AccountCategoryClass3.STOCKS.getLabel());
            List<SubAccountActif> dataset7 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassThree,AccountCategoryClass3.CREANCES_ACTIF_CIRCULANT.getLabel());


            // CLASS FIVE
            List<SubAccountActif> ClassFiveA = balanceDetailServiceImpl.getClassFiveActif(date,company_name);
            List<SubAccountActif> FullClassFiveA = accountDataManagerServiceImpl.processAccountDataA
                    (ClassFiveA,"5");
            List<SubAccountActif> dataset8 = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassFiveA, AccountCategoryClass5.TRESORERIE_ACTIF.getLabel());


            // CREATE INSTANCES OF JRBEANCOLLECTIONDATASOURCE FOR OUR JAVA BEAN OBJECTS
            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(dataset1);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataset2);
            JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(dataset3);
            JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(dataset4);
            JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(dataset5);
            JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(dataset6);
            JRBeanCollectionDataSource dataSource7 = new JRBeanCollectionDataSource(dataset7);
            JRBeanCollectionDataSource dataSource8 = new JRBeanCollectionDataSource(dataset8);

            Map<String, Object> parameters = new HashMap<>();

            // SETTING PARAMETERS
            parameters.put("param1",dataSource1);
            parameters.put("param2",dataSource2);
            parameters.put("param3",dataSource3);
            parameters.put("param4",dataSource4);
            parameters.put("param5",dataSource5);
            List<SubAccountActif> totalListI = new ArrayList<>();
            totalListI.addAll(dataset1);
            totalListI.addAll(dataset2);
            totalListI.addAll(dataset3);
            totalListI.addAll(dataset4);
            totalListI.addAll(dataset5);
            parameters.put("totalI",accountDataManagerServiceImpl.GetTotalBrutAccountActif(totalListI));

            parameters.put("param6",dataSource6);
            parameters.put("param7",dataSource7);
            List<SubAccountActif> totalListII = new ArrayList<>();
            totalListII.addAll(dataset6);
            totalListII.addAll(dataset7);
            parameters.put("totalII",accountDataManagerServiceImpl.GetTotalBrutAccountActif(totalListII));

            parameters.put("param8",dataSource8);
            parameters.put("totalIII",accountDataManagerServiceImpl.GetTotalBrutAccountActif(dataset8));


            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);

            return jasperConfiguration(pathA,parameters);

        }catch(RuntimeException e) {
            String message = "Failed to report bilan actif " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }

    @Override
    public ByteArrayOutputStream exportCPC(String date, String company_name) throws JRException {

        // TO CHECK
        String pathCPC = path+"CPC.jrxml";

        // Generer automatiquement le CPC
        try{
            // CLASS SIX
            List<SubAccountCPC> ClassSix = balanceDetailServiceImpl.getClassSix(date,company_name);

            List<SubAccountCPC> FullClassSix = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSix,"6");

            // CLASS SEVEN
            List<SubAccountCPC> ClassSeven = balanceDetailServiceImpl.getClassSeven(date,company_name);
            List<SubAccountCPC> FullClassSeven = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSeven,"7");

            // CREATE INSTANCES OF JRBEANCOLLECTIONDATASOURCE FOR OUR JAVA BEAN OBJECTS

            List<SubAccountCPC> dataset1 = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSeven,AccountCategoryClass7.PRODUITS_DEXPLOITATION.getLabel());
            List<SubAccountCPC> dataset2 = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSix,AccountCategoryClass6.CHARGES_DEXPLOITATION.getLabel());
            List<SubAccountCPC> dataset3 = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSeven,AccountCategoryClass7.PRODUITS_FINANCIERS.getLabel());
            List<SubAccountCPC> dataset4 = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSix,AccountCategoryClass6.CHARGES_FINANCIERES.getLabel());
            List<SubAccountCPC> dataset5 = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSeven,AccountCategoryClass7.PRODUITS_NON_COURANTS.getLabel());
            List<SubAccountCPC> dataset6 = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSix,AccountCategoryClass6.CHARGES_NON_COURANTES.getLabel());

            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(dataset1);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataset2);
            JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(dataset3);
            JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(dataset4);
            JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(dataset5);
            JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(dataset6);

            Map<String, Object> parameters = new HashMap<>();

            // SETTING PARAMETERS

            parameters.put("param1",dataSource1);
            parameters.put("param2",dataSource2);
            parameters.put("param3",dataSource3);
            parameters.put("param4",dataSource4);
            parameters.put("param5",dataSource5);
            parameters.put("param6",dataSource6);

            List<SubAccountCPC> totalListI = new ArrayList<>();
            totalListI.addAll(dataset1);
            parameters.put("total1",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListI));
            List<SubAccountCPC> totalListII = new ArrayList<>();
            totalListII.addAll(dataset2);
            parameters.put("total2",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListII));
            List<SubAccountCPC> totalListIII = new ArrayList<>();
            totalListIII.addAll(dataset3);
            parameters.put("total3",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIII));
            List<SubAccountCPC> totalListIV = new ArrayList<>();
            totalListIV.addAll(dataset4);
            parameters.put("total4",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIV));
            List<SubAccountCPC> totalListV = new ArrayList<>();
            totalListV.addAll(dataset5);
            parameters.put("total5",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListV));
            List<SubAccountCPC> totalListVI = new ArrayList<>();
            totalListVI.addAll(dataset6);
            parameters.put("total6",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVI));

            Double totaltemp = accountDataManagerServiceImpl.GetTotalBrutCPC(totalListI) -
            accountDataManagerServiceImpl.GetTotalBrutCPC(totalListII);

            Double total7 = (totaltemp - accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIII)) + (accountDataManagerServiceImpl.GetTotalBrutCPC(totalListV)
                    - accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVI));
            parameters.put("total7",total7);


            Double total9 = (accountDataManagerServiceImpl.GetTotalBrutCPC(totalListI)
                    + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIII) + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListV)) - (accountDataManagerServiceImpl.GetTotalBrutCPC(totalListII)
                    + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIV) + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVI));
            parameters.put("total9",total9);

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);


            Double impots_benefices = 0.0;

            // CHECK FOR COMPANY IF THEY HAVE MORE THAN 3 LOSSES
            if(!companyLessThanThreeLosses(FullClassSix,FullClassSeven,date,company_name)){

                // COTISATION MINIMALE

                Double  cot_min = (accountDataManagerServiceImpl.GetTotalBrutCPC(totalListI)*0.0025);

                // BENEFICE NET FISCAL

                Double benefice_net_fiscal = esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT NET DE L'EXERCICE");

                benefice_net_fiscal = calculateBeneficeNetFiscal(benefice_net_fiscal);

                if(cot_min > benefice_net_fiscal){
                    impots_benefices = cot_min;
                }else if(benefice_net_fiscal < 3000){
                    impots_benefices = 3000.00;
                }else {
                    impots_benefices = benefice_net_fiscal;
                }
            }


            parameters.put("Impots",impots_benefices);


            return jasperConfiguration(pathCPC,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report CPC " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }

    }

    @Override
    public ByteArrayOutputStream exportDetailTva(String date, String company_name) throws JRException {
        // TO CHECK
        String pathTva = path+"DetailTva.jrxml";

        try {
            // DATASET 1
            List<Tvadto> tvaFacturee = new ArrayList<>();
            tvaFacturee.add(tvaService.getTvaF(date,company_name));
            tvaService.getTotalTva(tvaFacturee.get(0));

            // DATASET 2
            List<Tvadto> tvaSc = new ArrayList<>();
            tvaSc.add(tvaService.getTvaRSc(date,company_name));
            tvaService.getTotalTva(tvaSc.get(0));

            // DATASET 3
            List<Tvadto> tvaSi = new ArrayList<>();
            tvaSi.add(tvaService.getTvaRSi(date,company_name));
            tvaService.getTotalTva(tvaSi.get(0));


            //data sources for each dataset
            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(tvaFacturee);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(tvaSc);
            JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(tvaSi);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("param1", dataSource1);
            parameters.put("param2", dataSource2);
            parameters.put("param3", dataSource3);

            List<Double> tvaRecuperable = tvaService.getTotalTvaRecup(tvaSc.get(0),tvaSi.get(0));
            List<Double> tvaDu = tvaService.getTvaAMinusB(tvaFacturee.get(0),tvaRecuperable);

            parameters.put("total1", tvaRecuperable.get(0));
            parameters.put("total2", tvaRecuperable.get(1));
            parameters.put("total3", tvaRecuperable.get(2));
            parameters.put("total4", tvaRecuperable.get(3));

            parameters.put("total5", tvaDu.get(0));
            parameters.put("total6", tvaDu.get(1));
            parameters.put("total7", tvaDu.get(2));
            parameters.put("total8", tvaDu.get(3));

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);

            return jasperConfiguration(pathTva,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report Tva table " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }

    }

    @Override
    public ByteArrayOutputStream exportTable14(String date, String company_name) throws JRException {
        try {
            // TO CHECK 
            String pathTva = path+"table14.jrxml";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("a1", 0.0);
            parameters.put("a2", 0.0);
            parameters.put("a3", 0.0);
            parameters.put("a4", 0.0);
            parameters.put("a5", 0.0);
            parameters.put("a6", 0.0);

            parameters.put("b1", 0.0);
            parameters.put("b2", 0.0);
            parameters.put("b3", 0.0);
            parameters.put("b4", 0.0);
            parameters.put("b5", 0.0);
            parameters.put("b6", 0.0);
            parameters.put("b7", 0.0);

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);

            return jasperConfiguration(pathTva,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report Table 14 " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }

    @Override
    public ByteArrayOutputStream exportTable15(String date, String company_name) throws JRException {
        try {
            // TO CHECK
            String pathTva = path+"table15.jrxml";

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);
            return jasperConfiguration(pathTva,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report Table 15 " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }

    @Override
    public ByteArrayOutputStream exportTable17(String date, String company_name) throws JRException {
        try {
            // TO CHECK
            String pathTva = path+"table17.jrxml";

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);
            return jasperConfiguration(pathTva,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report Table 17 " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }

    @Override
    public ByteArrayOutputStream exportTable18(String date, String company_name) throws JRException {
        try {
            // TO CHECK
            String pathTva = path+"table18.jrxml";

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);
            return jasperConfiguration(pathTva,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report Table 18 " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }

    @Override
    public ByteArrayOutputStream exportTable20(String date, String company_name) throws JRException {
        try {
            // TO CHECK
            String pathTva = path+"table20.jrxml";

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);
            return jasperConfiguration(pathTva,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report Table 20 " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }

    private boolean companyLessThanThreeLosses(List<SubAccountCPC> ClassSix,List<SubAccountCPC> ClassSeven,String date,String company_name) {

        // Convertir la date en objet LocalDate
        LocalDate datetemp = LocalDate.parse(date);

        // Avoir la date debut de la société et le convertir en localDate
        String start_date = societeRepository.getStartDateActivity(company_name);
        LocalDate dateSociete = LocalDate.parse(start_date);

        // Obtenir les années précédentes
        LocalDate previousYear1 = datetemp.minusYears(1);
        LocalDate previousYear2 = datetemp.minusYears(2);

        if(!start_date.isEmpty() && isSocieteActiveForThreeYears(dateSociete,datetemp)){
            // CLASS SIX N-1 & N-2
            List<SubAccountCPC> ClassSixN1 = balanceDetailServiceImpl.getClassSix(previousYear1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    company_name);

            List<SubAccountCPC> ClassSevenN1 = balanceDetailServiceImpl.getClassSeven(previousYear1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    company_name);

            // CLASS SEVEN N-1 & N-2
            List<SubAccountCPC> ClassSixN2 = balanceDetailServiceImpl.getClassSix(previousYear2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    company_name);
            List<SubAccountCPC> ClassSevenN2 = balanceDetailServiceImpl.getClassSeven(previousYear1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    company_name);

            // CHECK IF THERE IS DATA AVAILABLE FIRST
            if(ClassSixN1.isEmpty() || ClassSevenN1.isEmpty() || ClassSixN2.isEmpty() || ClassSevenN2.isEmpty()){
                return true;
            }
            else{
                // GET RESULTAT NET FOR EACH YEAR (N & N-1 & N-2)
                Double resultat_netN = esgService.GetResultat(ClassSix,ClassSeven,"RESULTAT NET DE L'EXERCICE");
                Double resultat_netN1 = esgService.GetResultat(ClassSixN1,ClassSevenN1,"RESULTAT NET DE L'EXERCICE");
                Double resultat_netN2 = esgService.GetResultat(ClassSixN2,ClassSevenN2,"RESULTAT NET DE L'EXERCICE");
                return (resultat_netN < 0 && resultat_netN1 < 0 && resultat_netN2 < 0);
            }
        }

        return true;

    }

    private boolean isSocieteActiveForThreeYears(LocalDate startDate, LocalDate currentDate) {
        // Date trois ans avant la date actuelle
        LocalDate currentDateMinusThreeYears = currentDate.minusYears(3);

        // Comparaison pour vérifier si la société existe depuis plus de trois ans
        return startDate.isBefore(currentDateMinusThreeYears) || startDate.isEqual(currentDateMinusThreeYears);
    }


    // Configurate the Jasper report
    public ByteArrayOutputStream jasperConfiguration(String path,Map<String,Object> parameters) throws JRException {

        JasperReport report = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter jrPdfExporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        jrPdfExporter.setConfiguration(configuration);
        jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        jrPdfExporter.exportReport();
        return byteArrayOutputStream;
    }

    public String getLastYear(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        LocalDate getLastYear = date.minusYears(1);
        return getLastYear.toString();
    }

    private Double calculateBeneficeNetFiscal(Double benefice_net_fiscal){
        if(benefice_net_fiscal <= 300000){
            benefice_net_fiscal = benefice_net_fiscal * 0.15;
        }else if(benefice_net_fiscal > 300000 && benefice_net_fiscal < 1000000){
            benefice_net_fiscal = benefice_net_fiscal * 0.2;
        }else if(benefice_net_fiscal > 1000000 && benefice_net_fiscal < 100000000){
            benefice_net_fiscal = benefice_net_fiscal * 0.255;
        }else{
            benefice_net_fiscal = benefice_net_fiscal * 0.33;
        }

        return benefice_net_fiscal;
    }


}
