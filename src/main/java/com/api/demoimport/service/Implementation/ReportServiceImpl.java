package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.SubAccountActif;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.entity.Bilan.SubAccountPassif;
import com.api.demoimport.enums.*;
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
import java.time.LocalDate;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    AccountDataManagerServiceImpl accountDataManagerServiceImpl;
    @Autowired
    BalanceDetailServiceImpl balanceDetailServiceImpl;

    // TO CHANGE
    String path = "C:\\Users\\onizu\\OneDrive\\Bureau\\demo-import\\src\\main\\resources\\templates\\";

    // EXPORT DATA PASSIF TO PDF
    public ByteArrayOutputStream exportReportPassif(String  date,String company_name) throws JRException {

        // TO DO
        String pathP = path+"BilanPassif.jrxml";

        // Generer automatiquement le bilan passif
        try{

            // CLASS ONE
            List<SubAccountPassif> ClassOne = balanceDetailServiceImpl.getClassOne(date,company_name);
            List<SubAccountPassif> FullClassOne = accountDataManagerServiceImpl.
                    processAccountDataP(ClassOne,"1");

            List<SubAccountPassif> dataset1 = accountDataManagerServiceImpl.
                    FilterAccountDataP(FullClassOne, AccountCategoryClass1.CAPITAUX_PROPRES.getLabel());
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
            String message = "Failed to report bilan passif " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }
    }


    // EXPORT DATA ACTIF TO PDF
    public ByteArrayOutputStream exportReportActif(String date,String company_name) throws JRException {

        // TO DO
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

        // TO DO
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

            parameters.put("DateN",date.substring(0,4));
            parameters.put("DateN1",getLastYear(date).substring(0,4));

            parameters.put("name_company",company_name);


            return jasperConfiguration(pathCPC,parameters);
        }catch (RuntimeException e){
            String message = "Failed to report CPC " + e.getLocalizedMessage() + "!";
            throw new RuntimeException(message);
        }

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


}
