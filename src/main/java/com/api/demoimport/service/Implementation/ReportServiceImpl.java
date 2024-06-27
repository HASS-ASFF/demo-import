package com.api.demoimport.service.Implementation;

import com.api.demoimport.dto.Tvadto;
import com.api.demoimport.entity.Bilan.*;
import com.api.demoimport.enums.*;
import com.api.demoimport.repository.SocieteRepository;
import com.api.demoimport.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    AccountDataManagerServiceImpl accountDataManagerServiceImpl;
    @Autowired
    BalanceDetailServiceImpl balanceDetailServiceImpl;
    @Autowired
    EsgServiceImpl esgService;
    @Autowired
    DetailCPCServiceImpl detailCPCService;
    @Autowired
    SocieteRepository societeRepository;
    @Autowired
    TvaServiceImpl tvaService;

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

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

            // ANNEE N-1 (exercice precedent)

            // CLASS ONE
            List<SubAccountPassif> ClassOneN = balanceDetailServiceImpl.getClassOne(getLastYear(date),company_name);
            List<SubAccountPassif> FullClassOneN = accountDataManagerServiceImpl.
                    processAccountDataP(ClassOneN,"1");

            List<SubAccountPassif> dataset1N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOneN, AccountCategoryClass1.CAPITAUX_PROPRES.getLabel());
            // CLASS SIX
            List<SubAccountCPC> ClassSixN = balanceDetailServiceImpl.getClassSix(getLastYear(date),company_name);
            List<SubAccountCPC> FullClassSixN = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSixN,"6");
            // CLASS SEVEN
            List<SubAccountCPC> ClassSevenN = balanceDetailServiceImpl.getClassSeven(getLastYear(date),company_name);
            List<SubAccountCPC> FullClassSevenN = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSevenN,"7");
            Double resultat_netN = esgService.GetResultat(FullClassSixN,FullClassSevenN,"RESULTAT NET DE L'EXERCICE");
            dataset1N.get(10).setBrut(resultat_netN);
            List<SubAccountPassif> dataset2N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOneN,AccountCategoryClass1.CAPITAUX_PROPRES_ASSIMILES.getLabel());
            List<SubAccountPassif> dataset3N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOneN,AccountCategoryClass1.DETTES_DE_FINANCEMENT.getLabel());
            List<SubAccountPassif> dataset4N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOneN,AccountCategoryClass1.PROVISIONS_DURABLES_POUR_RISQUES_ET_CHARGES.getLabel());
            List<SubAccountPassif> dataset5N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOneN,AccountCategoryClass1.ECARTS_DE_CONVERSION_PASSIF.getLabel());


            // CLASS FOUR
            List<SubAccountPassif> ClassFourN = balanceDetailServiceImpl.getClassFour(getLastYear(date),company_name);
            List<SubAccountPassif> FullClassFourN = accountDataManagerServiceImpl.
                    processAccountDataP(ClassFourN,"4");
            List<SubAccountPassif> dataset6N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFourN, AccountCategoryClass4.DETTES_DU_PASSIF_CIRCULANT.getLabel());
            List<SubAccountPassif> dataset7N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFourN,AccountCategoryClass4.AUTRES_PROVISIONS_POUR_RISQUES_ET_CHARGES.getLabel());
            List<SubAccountPassif> dataset8N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFourN,AccountCategoryClass4.ECARTS_DE_CONVERSION_PASSIF.getLabel());

            //CLASS FIVE PASSIF
            List<SubAccountPassif> ClassFivePN = balanceDetailServiceImpl.getClassFivePassif(getLastYear(date),company_name);
            List<SubAccountPassif> FullClassFivePN =  accountDataManagerServiceImpl.
                    processAccountDataP(ClassFivePN,"5");
            List<SubAccountPassif> dataset9N = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassFivePN,AccountCategoryClass5.TRESORERIE_PASSIF.getLabel());

            // ANNEE N (exercice brut)

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


            // ADD THE DATA OF EXERCICE PRECEDENT IN THE ACTUAL DATASETS (ANNEE N)
            accountDataManagerServiceImpl.updateExerciceP(dataset1N,dataset1);
            accountDataManagerServiceImpl.updateExerciceP(dataset2N,dataset2);
            accountDataManagerServiceImpl.updateExerciceP(dataset3N,dataset3);
            accountDataManagerServiceImpl.updateExerciceP(dataset4N,dataset4);
            accountDataManagerServiceImpl.updateExerciceP(dataset5N,dataset5);
            accountDataManagerServiceImpl.updateExerciceP(dataset6N,dataset6);
            accountDataManagerServiceImpl.updateExerciceP(dataset7N,dataset7);
            accountDataManagerServiceImpl.updateExerciceP(dataset8N,dataset8);
            accountDataManagerServiceImpl.updateExerciceP(dataset9N,dataset9);


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

        long startTime = System.currentTimeMillis(); // Début du chronométrage

        // Generer automatiquement le bilan actif
        try{
            // ANNEE N-1 (exercice precedent)

            // CLASS TWO
            List<SubAccountActif> ClassTwoN = balanceDetailServiceImpl.getClassTwo(getLastYear(date),company_name);
            List<SubAccountActif> FullClassTwoN = accountDataManagerServiceImpl.
                    processAccountDataA(ClassTwoN,"2");

            List<SubAccountActif> dataset1N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwoN, AccountCategoryClass2.IMMOBILISATION_NON_VALEURS.getLabel());
            List<SubAccountActif> dataset2N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwoN,AccountCategoryClass2.IMMOBILISATION_INCORPORELLES.getLabel());
            List<SubAccountActif> dataset3N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwoN,AccountCategoryClass2.IMMOBILISATION_CORPORELLES.getLabel());
            List<SubAccountActif> dataset4N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwoN,AccountCategoryClass2.IMMOBILISATION_FINANCIERES.getLabel());
            List<SubAccountActif> dataset5N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassTwoN,AccountCategoryClass2.ECART_CONVERSION_ACTIF.getLabel());

            // CLASS THREE
            List<SubAccountActif> ClassThreeN = balanceDetailServiceImpl.getClassThree(getLastYear(date),company_name);
            List<SubAccountActif> FullClassThreeN = accountDataManagerServiceImpl.
                    processAccountDataA(ClassThreeN,"3");

            List<SubAccountActif> dataset6N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassThreeN, AccountCategoryClass3.STOCKS.getLabel());
            List<SubAccountActif> dataset7N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassThreeN,AccountCategoryClass3.CREANCES_ACTIF_CIRCULANT.getLabel());


            // CLASS FIVE
            List<SubAccountActif> ClassFiveAN = balanceDetailServiceImpl.getClassFiveActif(getLastYear(date),company_name);
            List<SubAccountActif> FullClassFiveAN = accountDataManagerServiceImpl.processAccountDataA
                    (ClassFiveAN,"5");
            List<SubAccountActif> dataset8N = accountDataManagerServiceImpl.
                    FilterAccountDataA(FullClassFiveAN, AccountCategoryClass5.TRESORERIE_ACTIF.getLabel());



            // ANNEE N (exercice brut)

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


            dataset1.forEach(d -> System.out.println(d.getMainAccount() + " "+ d.getBrut()));
            dataset2.forEach(d -> System.out.println(d.getMainAccount() + " "+ d.getBrut()));
            dataset3.forEach(d -> System.out.println(d.getMainAccount() + " "+ d.getBrut()));
            dataset4.forEach(d -> System.out.println(d.getMainAccount() + " "+ d.getBrut()));
            dataset5.forEach(d -> System.out.println(d.getMainAccount() + " "+ d.getBrut()));
            dataset6.forEach(d -> System.out.println(d.getMainAccount() + " "+ d.getBrut()));

            /*// ADD THE DATA OF EXERCICE PRECEDENT IN THE ACTUAL DATASETS (ANNEE N)
            accountDataManagerServiceImpl.updateExerciceP(dataset1N,dataset1);
            accountDataManagerServiceImpl.updateExerciceP(dataset2N,dataset2);
            accountDataManagerServiceImpl.updateExerciceP(dataset3N,dataset3);
            accountDataManagerServiceImpl.updateExerciceP(dataset4N,dataset4);
            accountDataManagerServiceImpl.updateExerciceP(dataset5N,dataset5);
            accountDataManagerServiceImpl.updateExerciceP(dataset6N,dataset6);
            accountDataManagerServiceImpl.updateExerciceP(dataset7N,dataset7);
            accountDataManagerServiceImpl.updateExerciceP(dataset8N,dataset8);*/


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

            long endTime = System.currentTimeMillis(); // Fin du chronométrage
            long executionTime = endTime - startTime;
            logger.info("Temps d'exécution : {} ms", executionTime);

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
            Map<String, Object> parameters = new HashMap<>();

            // ANNEE N-1 (exercice precedent)

            // CLASS SIX
            List<SubAccountCPC> ClassSixN = balanceDetailServiceImpl.getClassSix(getLastYear(date),company_name);

            List<SubAccountCPC> FullClassSixN = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSixN,"6");

            // CLASS SEVEN
            List<SubAccountCPC> ClassSevenN = balanceDetailServiceImpl.getClassSeven(getLastYear(date),company_name);
            List<SubAccountCPC> FullClassSevenN = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSevenN,"7");

            // CREATE INSTANCES OF JRBEANCOLLECTIONDATASOURCE FOR OUR JAVA BEAN OBJECTS

            List<SubAccountCPC> dataset1N = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSevenN,AccountCategoryClass7.PRODUITS_DEXPLOITATION.getLabel());
            List<SubAccountCPC> dataset2N = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSixN,AccountCategoryClass6.CHARGES_DEXPLOITATION.getLabel());
            List<SubAccountCPC> dataset3N = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSevenN,AccountCategoryClass7.PRODUITS_FINANCIERS.getLabel());
            List<SubAccountCPC> dataset4N = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSixN,AccountCategoryClass6.CHARGES_FINANCIERES.getLabel());
            List<SubAccountCPC> dataset5N = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSevenN,AccountCategoryClass7.PRODUITS_NON_COURANTS.getLabel());
            List<SubAccountCPC> dataset6N = accountDataManagerServiceImpl.
                    FilterAccountDataCPC(FullClassSixN,AccountCategoryClass6.CHARGES_NON_COURANTES.getLabel());

            if(!ClassSevenN.isEmpty() && !ClassSixN.isEmpty()){

                List<SubAccountCPC> totalListIN = new ArrayList<>();
                totalListIN.addAll(dataset1N);
                parameters.put("total1P",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIN));
                List<SubAccountCPC> totalListIIN = new ArrayList<>();
                totalListIIN.addAll(dataset2N);
                parameters.put("total2P",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIIN));
                List<SubAccountCPC> totalListIIIN = new ArrayList<>();
                totalListIIIN.addAll(dataset3N);
                parameters.put("total3P",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIIIN));
                List<SubAccountCPC> totalListIVN = new ArrayList<>();
                totalListIVN.addAll(dataset4N);
                parameters.put("total4P",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIVN));
                List<SubAccountCPC> totalListVN = new ArrayList<>();
                totalListVN.addAll(dataset5N);
                parameters.put("total5P",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVN));
                List<SubAccountCPC> totalListVIN = new ArrayList<>();
                totalListVIN.addAll(dataset6N);
                parameters.put("total6P",accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVIN));

                Double totaltempN = accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIN) -
                        accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIIN);

                Double total7N = (totaltempN - accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIIIN)) + (accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVN)
                        - accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVIN));
                parameters.put("total7P",total7N);


                Double total9N = (accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIN)
                        + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIIIN) + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVN)) - (accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIIN)
                        + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListIVN) + accountDataManagerServiceImpl.GetTotalBrutCPC(totalListVIN));
                parameters.put("total9P",total9N);
            }



            // ANNEE N (exercice brut)
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

            accountDataManagerServiceImpl.updateTotalBrutCPC(dataset1);
            accountDataManagerServiceImpl.updateTotalBrutCPC(dataset2);
            accountDataManagerServiceImpl.updateTotalBrutCPC(dataset3);
            accountDataManagerServiceImpl.updateTotalBrutCPC(dataset4);
            accountDataManagerServiceImpl.updateTotalBrutCPC(dataset5);
            accountDataManagerServiceImpl.updateTotalBrutCPC(dataset6);

           /* // ADD THE DATA OF EXERCICE PRECEDENT IN THE ACTUAL DATASETS (ANNEE N)
            accountDataManagerServiceImpl.updateExerciceP(dataset1N,dataset1);
            accountDataManagerServiceImpl.updateExerciceP(dataset2N,dataset2);
            accountDataManagerServiceImpl.updateExerciceP(dataset3N,dataset3);
            accountDataManagerServiceImpl.updateExerciceP(dataset4N,dataset4);
            accountDataManagerServiceImpl.updateExerciceP(dataset5N,dataset5);
            accountDataManagerServiceImpl.updateExerciceP(dataset6N,dataset6);*/




            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(dataset1);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataset2);
            JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(dataset3);
            JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(dataset4);
            JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(dataset5);
            JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(dataset6);



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
            e.printStackTrace();
            String message = "Failed to report CPC " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }

    }

    @Override
    public ByteArrayOutputStream exportEsg(String date, String company_name) throws JRException {

        // TO CHECK
        String pathEsg = path+"esg.jrxml";

        try {
            // ANNEE N-1 (exercice precedent)

            // CLASS SIX
            List<SubAccountCPC> ClassSixN = balanceDetailServiceImpl.getClassSix(getLastYear(date),company_name);
            List<SubAccountCPC> FullClassSixN = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSixN,"6");
            // CLASS SEVEN
            List<SubAccountCPC> ClassSevenN = balanceDetailServiceImpl.getClassSeven(getLastYear(date),company_name);
            List<SubAccountCPC> FullClassSevenN = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSevenN,"7");

            // TFR PART
            List<Esg> dataset1N = esgService.processDataTFR(FullClassSixN,FullClassSevenN,1);
            List<Esg> dataset2N = esgService.processDataTFR(FullClassSixN,FullClassSevenN,2);
            List<Esg> dataset3N = esgService.processDataTFR(FullClassSixN,FullClassSevenN,3);
            List<Esg> dataset4N = esgService.processDataTFR(FullClassSixN,FullClassSevenN,4);
            List<Esg> dataset5N = esgService.processDataTFR(FullClassSixN,FullClassSevenN,5);
            // CAF PART
            List<Esg> dataset6N = esgService.processDataCAF(FullClassSixN,FullClassSevenN,1);
            List<Esg> dataset7N = esgService.processDataCAF(FullClassSixN,FullClassSevenN,2);

            Double total1P = esgService.GetTotalDataEsg(dataset1N,2);
            Double total2P = esgService.GetTotalDataEsg(dataset2N,1);
            Double total3P = esgService.GetTotalDataEsg(dataset3N,1);
            Double total4P = esgService.GetTotalDataEsg(dataset4N,1);
            Double total5P = esgService.GetTotalDataEsg(dataset5N,1);

            Map<String, Object> parameters = new HashMap<>();

            List<Esg> totalCAFListP = new ArrayList<>();
            totalCAFListP.addAll(dataset6N);
            totalCAFListP.addAll(dataset7N);

            parameters.put("totalCAFP",esgService.GetTotalDataEsg(totalCAFListP,1));


            if(!FullClassSevenN.isEmpty() && !FullClassSixN.isEmpty()){
                Double REPrevious = esgService.GetResultat(FullClassSixN,FullClassSevenN,"RESULTAT D'EXPLOITATION");
                Double RFPrevious = esgService.GetResultat(FullClassSixN,FullClassSevenN,"RESULTAT FINANCIER");
                Double RCPrevious = esgService.GetResultat(FullClassSixN,FullClassSevenN,"RESULTAT COURANT");
                Double RNCPrevious = esgService.GetResultat(FullClassSixN,FullClassSevenN,"RESULTAT NON COURANT");
                Double IRPrevious = esgService.GetResultat(FullClassSixN,FullClassSevenN,"Impôt sur les résultats");
                Double RNPrevious = esgService.GetResultat(FullClassSixN,FullClassSevenN,"RESULTAT NET DE L'EXERCICE");

                parameters.put("REPrevious",REPrevious);
                parameters.put("RFPrevious",RFPrevious);
                parameters.put("RCPrevious",RCPrevious);
                parameters.put("RNCPrevious",RNCPrevious);
                parameters.put("IRPrevious",IRPrevious);
                parameters.put("RNPrevious",RNPrevious);

            }

            // ANNEE N (exercice brut)

            // CLASS SIX
            List<SubAccountCPC> ClassSix = balanceDetailServiceImpl.getClassSix(date,company_name);
            List<SubAccountCPC> FullClassSix = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSix,"6");
            // CLASS SEVEN
            List<SubAccountCPC> ClassSeven = balanceDetailServiceImpl.getClassSeven(date,company_name);
            List<SubAccountCPC> FullClassSeven = accountDataManagerServiceImpl.
                    processAccountDataCPC(ClassSeven,"7");

            // CREATE INSTANCES OF JRBEANCOLLECTIONDATASOURCE FOR OUR JAVA BEAN OBJECTS

            // TFR PART
            List<Esg> dataset1 = esgService.processDataTFR(FullClassSix,FullClassSeven,1);
            List<Esg> dataset2 = esgService.processDataTFR(FullClassSix,FullClassSeven,2);
            List<Esg> dataset3 = esgService.processDataTFR(FullClassSix,FullClassSeven,3);
            List<Esg> dataset4 = esgService.processDataTFR(FullClassSix,FullClassSeven,4);
            List<Esg> dataset5 = esgService.processDataTFR(FullClassSix,FullClassSeven,5);
            // CAF PART
            List<Esg> dataset6 = esgService.processDataCAF(FullClassSix,FullClassSeven,1);
            List<Esg> dataset7 = esgService.processDataCAF(FullClassSix,FullClassSeven,2);

            // ADD THE DATA OF EXERCICE PRECEDENT IN THE ACTUAL DATASETS (ANNEE N)
            accountDataManagerServiceImpl.updateExerciceP(dataset1N,dataset1);
            accountDataManagerServiceImpl.updateExerciceP(dataset2N,dataset2);
            accountDataManagerServiceImpl.updateExerciceP(dataset3N,dataset3);
            accountDataManagerServiceImpl.updateExerciceP(dataset4N,dataset4);
            accountDataManagerServiceImpl.updateExerciceP(dataset5N,dataset5);
            accountDataManagerServiceImpl.updateExerciceP(dataset6N,dataset6);
            accountDataManagerServiceImpl.updateExerciceP(dataset7N,dataset7);


            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(dataset1);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataset2);
            JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(dataset3);
            JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(dataset4);
            JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(dataset5);

            JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(dataset6);
            JRBeanCollectionDataSource dataSource7 = new JRBeanCollectionDataSource(dataset7);



            parameters.put("param1",dataSource1);
            parameters.put("param2",dataSource2);
            parameters.put("param3",dataSource3);
            parameters.put("param4",dataSource4);
            parameters.put("param5",dataSource5);

            parameters.put("param6",dataSource6);
            parameters.put("param7",dataSource7);

            parameters.put("total1",esgService.GetTotalDataEsg(dataset1,2));
            parameters.put("total2",esgService.GetTotalDataEsg(dataset2,1));
            parameters.put("total3",esgService.GetTotalDataEsg(dataset3,1));
            parameters.put("total4",esgService.GetTotalDataEsg(dataset4,1));
            parameters.put("total5",esgService.GetTotalDataEsg(dataset5,1));

            parameters.put("total1P",total1P);
            parameters.put("total2P",total2P);
            parameters.put("total3P",total3P);
            parameters.put("total4P",total4P);
            parameters.put("total5P",total5P);

            parameters.put("RE",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT D'EXPLOITATION"));
            parameters.put("RF",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT FINANCIER"));
            parameters.put("RC",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT COURANT"));
            parameters.put("RNC",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT NON COURANT"));
            parameters.put("IR",esgService.GetResultat(FullClassSix,FullClassSeven,"Impôt sur les résultats"));
            parameters.put("RN",esgService.GetResultat(FullClassSix,FullClassSeven,"RESULTAT NET DE L'EXERCICE"));

            List<Esg> totalCAFList = new ArrayList<>();
            totalCAFList.addAll(dataset6);
            totalCAFList.addAll(dataset7);

            parameters.put("totalCAF",esgService.GetTotalDataEsg(totalCAFList,1));

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);

            return jasperConfiguration(pathEsg,parameters);
        }catch (RuntimeException e) {
            String message = "Failed to report ESG " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }

    @Override
    public ByteArrayOutputStream exportDetailCPC(String date, String company_name) throws JRException {

        // TO CHECK
        String pathDetailCPC1 = path+"DetailCPC1.jrxml";
        String pathDetailCPC2 = path+"DetailCPC2.jrxml";

        try {
            // ANNEE N-1 (exercice precedent)

            // CLASS SIX
            List<SubAccountCPC> ClassSixN = balanceDetailServiceImpl.getClassSix(getLastYear(date),company_name);
            List<DetailCPC> FullClassSixN = detailCPCService.processDataSix(ClassSixN);

            List<DetailCPC> dataset1N = detailCPCService.FilterAccountDataDetailCPC(FullClassSixN,
                    DetailCPCCategoryClass6.ACHAT_REVENDUS.getLabel());
            List<DetailCPC> dataset2N = detailCPCService.FilterAccountDataDetailCPC(FullClassSixN,
                    DetailCPCCategoryClass6.ACHAT_CONSOMES.getLabel());
            List<DetailCPC> dataset3N = detailCPCService.FilterAccountDataDetailCPC(FullClassSixN,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_EXTERNES.getLabel());
            List<DetailCPC> dataset4N = detailCPCService.FilterAccountDataDetailCPC(FullClassSixN,
                    DetailCPCCategoryClass6.CHARGES_PERSONEL.getLabel());
            List<DetailCPC> dataset5N = detailCPCService.FilterAccountDataDetailCPC(FullClassSixN,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_EXPLOITATION.getLabel());
            List<DetailCPC> dataset6N = detailCPCService.FilterAccountDataDetailCPC(FullClassSixN,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_FINANCIERES.getLabel());
            List<DetailCPC> dataset7N = detailCPCService.FilterAccountDataDetailCPC(FullClassSixN,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_NON_COURANTES.getLabel());

            // CLASS SEVEN
            List<SubAccountCPC> ClassSevenN = balanceDetailServiceImpl.getClassSeven(getLastYear(date),company_name);
            List<DetailCPC> FullClassSevenN = detailCPCService.processDataSeven(ClassSevenN);

            List<DetailCPC> dataset8N = detailCPCService.FilterAccountDataDetailCPC(FullClassSevenN,
                    DetailCPCCategoryClass7.VENTES_MARCHANDISES.getLabel());
            List<DetailCPC> dataset9N = detailCPCService.FilterAccountDataDetailCPC(FullClassSevenN,
                    DetailCPCCategoryClass7.VENTES_BIENS_SERVICES.getLabel());
            List<DetailCPC> dataset10N = detailCPCService.FilterAccountDataDetailCPC(FullClassSevenN,
                    DetailCPCCategoryClass7.VAR_STOCK_PRODUITS.getLabel());
            List<DetailCPC> dataset11N = detailCPCService.FilterAccountDataDetailCPC(FullClassSevenN,
                    DetailCPCCategoryClass7.AUTRES_PRODUITS_EXP.getLabel());
            List<DetailCPC> dataset12N = detailCPCService.FilterAccountDataDetailCPC(FullClassSevenN,
                    DetailCPCCategoryClass7.REPRISE_EXPLOITATION.getLabel());
            List<DetailCPC> dataset13N = detailCPCService.FilterAccountDataDetailCPC(FullClassSevenN,
                    DetailCPCCategoryClass7.INTERETS_ASSIMILES.getLabel());


            // ANNEE N (exercice brut)

            // CLASS SIX
            List<SubAccountCPC> ClassSix = balanceDetailServiceImpl.getClassSix(date,company_name);
            List<DetailCPC> FullClassSix = detailCPCService.processDataSix(ClassSix);

            List<DetailCPC> dataset1 = detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                    DetailCPCCategoryClass6.ACHAT_REVENDUS.getLabel());
            List<DetailCPC> dataset2 = detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                    DetailCPCCategoryClass6.ACHAT_CONSOMES.getLabel());
            List<DetailCPC> dataset3 = detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_EXTERNES.getLabel());
            List<DetailCPC> dataset4 = detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                    DetailCPCCategoryClass6.CHARGES_PERSONEL.getLabel());
            List<DetailCPC> dataset5 = detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_EXPLOITATION.getLabel());
            List<DetailCPC> dataset6 = detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_FINANCIERES.getLabel());
            List<DetailCPC> dataset7 = detailCPCService.FilterAccountDataDetailCPC(FullClassSix,
                    DetailCPCCategoryClass6.AUTRES_CHARGES_NON_COURANTES.getLabel());

            // CLASS SEVEN
            List<SubAccountCPC> ClassSeven = balanceDetailServiceImpl.getClassSeven(date,company_name);
            List<DetailCPC> FullClassSeven = detailCPCService.processDataSeven(ClassSeven);

            List<DetailCPC> dataset8 = detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                    DetailCPCCategoryClass7.VENTES_MARCHANDISES.getLabel());
            List<DetailCPC> dataset9 = detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                    DetailCPCCategoryClass7.VENTES_BIENS_SERVICES.getLabel());
            List<DetailCPC> dataset10 = detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                    DetailCPCCategoryClass7.VAR_STOCK_PRODUITS.getLabel());
            List<DetailCPC> dataset11 = detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                    DetailCPCCategoryClass7.AUTRES_PRODUITS_EXP.getLabel());
            List<DetailCPC> dataset12 = detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                    DetailCPCCategoryClass7.REPRISE_EXPLOITATION.getLabel());
            List<DetailCPC> dataset13 = detailCPCService.FilterAccountDataDetailCPC(FullClassSeven,
                    DetailCPCCategoryClass7.INTERETS_ASSIMILES.getLabel());



            Map<String, Object> parameters1 = new HashMap<>();
            Map<String, Object> parameters2 = new HashMap<>();

            // PARAMETER PAGE 1 ( DATA CLASS SIX )

            // ADD THE DATA OF EXERCICE PRECEDENT IN THE ACTUAL DATASETS (ANNEE N)
            accountDataManagerServiceImpl.updateExerciceP(dataset1N,dataset1);
            accountDataManagerServiceImpl.updateExerciceP(dataset2N,dataset2);
            accountDataManagerServiceImpl.updateExerciceP(dataset3N,dataset3);
            accountDataManagerServiceImpl.updateExerciceP(dataset4N,dataset4);
            accountDataManagerServiceImpl.updateExerciceP(dataset5N,dataset5);
            accountDataManagerServiceImpl.updateExerciceP(dataset6N,dataset6);
            accountDataManagerServiceImpl.updateExerciceP(dataset7N,dataset7);

            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(dataset1);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataset2);
            JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(dataset3);
            JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(dataset4);
            JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(dataset5);
            JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(dataset6);
            JRBeanCollectionDataSource dataSource7 = new JRBeanCollectionDataSource(dataset7);

            parameters1.put("param1",dataSource1);
            parameters1.put("param2",dataSource2);
            parameters1.put("param3",dataSource3);
            parameters1.put("param4",dataSource4);
            parameters1.put("param5",dataSource5);
            parameters1.put("param6",dataSource6);
            parameters1.put("param7",dataSource7);

            parameters1.put("total1",detailCPCService.GetTotalDataDetailCPCC(dataset1));
            parameters1.put("total2",detailCPCService.GetTotalDataDetailCPCC(dataset2));
            parameters1.put("total3",detailCPCService.GetTotalDataDetailCPCC(dataset3));
            parameters1.put("total4",detailCPCService.GetTotalDataDetailCPCC(dataset4));
            parameters1.put("total5",detailCPCService.GetTotalDataDetailCPCC(dataset5));
            parameters1.put("total6",detailCPCService.GetTotalDataDetailCPCC(dataset6));
            parameters1.put("total7",detailCPCService.GetTotalDataDetailCPCC(dataset7));

            parameters1.put("total1P",detailCPCService.GetTotalDataDetailCPCC(dataset1N));
            parameters1.put("total2P",detailCPCService.GetTotalDataDetailCPCC(dataset2N));
            parameters1.put("total3P",detailCPCService.GetTotalDataDetailCPCC(dataset3N));
            parameters1.put("total4P",detailCPCService.GetTotalDataDetailCPCC(dataset4N));
            parameters1.put("total5P",detailCPCService.GetTotalDataDetailCPCC(dataset5N));
            parameters1.put("total6P",detailCPCService.GetTotalDataDetailCPCC(dataset6N));
            parameters1.put("total7P",detailCPCService.GetTotalDataDetailCPCC(dataset7N));

            parameters1.put("DateN",date.substring(0,4));
            parameters1.put("DateN1",getLastYear(date).substring(0,4));

            parameters1.put("name_company",company_name);

            // PARAMETER PAGE 2 (DATA CLASS 7)
            // ADD THE DATA OF EXERCICE PRECEDENT IN THE ACTUAL DATASETS (ANNEE N)
            accountDataManagerServiceImpl.updateExerciceP(dataset8N,dataset8);
            accountDataManagerServiceImpl.updateExerciceP(dataset9N,dataset9);
            accountDataManagerServiceImpl.updateExerciceP(dataset10N,dataset10);
            accountDataManagerServiceImpl.updateExerciceP(dataset11N,dataset11);
            accountDataManagerServiceImpl.updateExerciceP(dataset12N,dataset12);
            accountDataManagerServiceImpl.updateExerciceP(dataset13N,dataset13);

            JRBeanCollectionDataSource dataSource8 = new JRBeanCollectionDataSource(dataset8);
            JRBeanCollectionDataSource dataSource9 = new JRBeanCollectionDataSource(dataset9);
            JRBeanCollectionDataSource dataSource10 = new JRBeanCollectionDataSource(dataset10);
            JRBeanCollectionDataSource dataSource11 = new JRBeanCollectionDataSource(dataset11);
            JRBeanCollectionDataSource dataSource12 = new JRBeanCollectionDataSource(dataset12);
            JRBeanCollectionDataSource dataSource13 = new JRBeanCollectionDataSource(dataset13);

            parameters2.put("param8",dataSource8);
            parameters2.put("param9",dataSource9);
            parameters2.put("param10",dataSource10);
            parameters2.put("param11",dataSource11);
            parameters2.put("param12",dataSource12);
            parameters2.put("param13",dataSource13);

            parameters1.put("total8",detailCPCService.GetTotalDataDetailCPCC(dataset8));
            parameters1.put("total9",detailCPCService.GetTotalDataDetailCPCC(dataset9));
            parameters1.put("total10",detailCPCService.GetTotalDataDetailCPCC(dataset10));
            parameters1.put("total11",detailCPCService.GetTotalDataDetailCPCC(dataset11));
            parameters1.put("total12",detailCPCService.GetTotalDataDetailCPCC(dataset12));
            parameters1.put("total13",detailCPCService.GetTotalDataDetailCPCC(dataset13));

            parameters1.put("total8P",detailCPCService.GetTotalDataDetailCPCC(dataset8N));
            parameters1.put("total9P",detailCPCService.GetTotalDataDetailCPCC(dataset9N));
            parameters1.put("total10P",detailCPCService.GetTotalDataDetailCPCC(dataset10N));
            parameters1.put("total11P",detailCPCService.GetTotalDataDetailCPCC(dataset11N));
            parameters1.put("total12P",detailCPCService.GetTotalDataDetailCPCC(dataset12N));
            parameters1.put("total13P",detailCPCService.GetTotalDataDetailCPCC(dataset13N));

            parameters2.put("DateN",date.substring(0,4));
            parameters2.put("DateN1",getLastYear(date).substring(0,4));

            parameters2.put("name_company",company_name);


            return jasperConfigurationCPC(pathDetailCPC1,pathDetailCPC2,parameters1,parameters2);
        }catch (RuntimeException e){
            String message = "Failed to report DetailCPC " + e.getLocalizedMessage() + "!";
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
            // ANNEE N-1 (exercice precedent)
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
        if(start_date == null){
            return true;
        }
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

    public ByteArrayOutputStream jasperConfigurationCPC(String path1,String path2,Map<String,Object> parameters1,Map<String,Object> parameters2) throws JRException {

        JasperReport report1 = JasperCompileManager.compileReport(path1);
        JasperPrint jasperPrint1 = JasperFillManager.fillReport(report1, parameters1, new JREmptyDataSource());

        JasperReport report2 = JasperCompileManager.compileReport(path2);
        JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters2, new JREmptyDataSource());

        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(jasperPrint1);
        jasperPrintList.add(jasperPrint2);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();

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
