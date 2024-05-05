package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.*;
import com.api.demoimport.entity.Immobilisation;
import com.api.demoimport.repository.BalanceRepository;
import com.api.demoimport.repository.ExerciceRepository;
import com.api.demoimport.service.ExcelHelperService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelHelperServiceImpl implements ExcelHelperService {

    @Autowired
    private PlanComptableServiceImpl excelPlanComptableServiceImpl;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private ExerciceRepository exerciceRepository;

    /**
     * Service implementation for handling Excel files including operations like converting Excel data to PlanComptable,
     * BalanceDetail, and Immobilisation objects.
     * Provides methods for verifying the format of Excel files, converting Excel data to object representations,
     * and saving them to respective repositories.
     */


    // Defining type of MIME
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String TYPE2 = "application/vnd.ms-excel";

    // Verify format of file EXCEL
    public static boolean hasExcelFormat(MultipartFile file) {
        String fileType = file.getContentType();
        return fileType != null
                && (fileType.equals(TYPE) ||
                fileType.equals(TYPE2));
    }

    // Convert data excel to object PlanComptable
    public  List<PlanComptable> excelToPlanComptable(InputStream is) throws ParseException {
            try {
                Workbook workbook = new XSSFWorkbook(is);

                Sheet sheet = workbook.getSheet("plan_comptable");
                Iterator<Row> rows = sheet.iterator();

                List<PlanComptable> planComptables = new ArrayList<>();

                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();


                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    PlanComptable planComptable = new PlanComptable();

                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIdx) {
            // Récupération des données de chaque cellule et assignation aux attributs de l'objet PlanComptable
                            case 0:
                                planComptable.setId((long) currentCell.getNumericCellValue());
                                break;

                            case 1:
                                planComptable.setLibelle(currentCell.getStringCellValue());
                                break;

                            case 2:
                                planComptable.setNiv_de_reg((long) currentCell.getNumericCellValue());
                                break;

                            case 3:
                                String no_compte = currentCell.getCellType() == CellType.STRING
                                        ? currentCell.getStringCellValue()
                                        : String.format("%-9s", currentCell.getNumericCellValue()).replace(".", "").replace(" ", "0");

                                //System.out.println(no_compte);

                                Double classValue2 = Double.valueOf(no_compte);

                                planComptable.setNo_compte(classValue2.longValue());

                                break;

                            case 4:
                                planComptable.setThe_class((long) currentCell.getNumericCellValue());
                                break;

                            case 5:
                                planComptable.setAmort((long) currentCell.getNumericCellValue());
                                break;

                            default:
                                break;
                        }

                        cellIdx++;
                    }

                    planComptables.add(planComptable);
                }

                workbook.close();

                return planComptables;
            } catch (IOException e) {
                throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
            }
        }

    @Override
    public List<BalanceDetail> excelToBalanceDetail(InputStream is, String date, String company_name) throws ParseException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheet("balance");
        List<BalanceDetail> balanceDetails = new ArrayList<>();

        // Création d'une instance de Balance avec la date
        Balance balance = new Balance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        balance.setDate(sdf.parse(date));
        balance.setCompany_name(company_name);

        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum() + 1; i++){

            if (i == 0) {
                continue;
            }

            Row row = sheet.getRow(i);
            BalanceDetail balanceDetail = new BalanceDetail();
            balanceDetail.setBalance(balance);
            Double n_compte = getCellValuesAsDouble(row.getCell(0));
            // Vérifier si la valeur récupéré existe dans la table plan comptable (numéro de compte)
            Optional<PlanComptable> planComptable = excelPlanComptableServiceImpl
                    .search(n_compte.longValue());

            if (planComptable.isPresent()) {
                balanceDetail.setCompte(planComptable.get());
                balanceDetail.setN_Compte(planComptable.get().getNo_compte());
                balanceDetail.setThe_class(planComptable.get().getThe_class());
            }

            String label = getCellValuesAsString(row.getCell(1));
            Double debitDex = getCellValuesAsDouble(row.getCell(2));
            Double CreditDex = getCellValuesAsDouble(row.getCell(3));
            Double DebitEx = getCellValuesAsDouble(row.getCell(4));
            Double CreditEx = getCellValuesAsDouble(row.getCell(5));
            Double DebitFex = getCellValuesAsDouble(row.getCell(6));
            Double CreditFex = getCellValuesAsDouble(row.getCell(7));

            balanceDetail.setLabel(label);
            balanceDetail.setDebitDex(debitDex);
            balanceDetail.setCreditDex(CreditDex);
            balanceDetail.setDebitEx(DebitEx);
            balanceDetail.setCreditEx(CreditEx);
            balanceDetail.setDebitFex(DebitFex);
            balanceDetail.setCreditFex(CreditFex);

            balanceDetails.add(balanceDetail);
        }

        balanceRepository.save(balance);
        workbook.close();

        return balanceDetails;
    }

    @Override
    public List<Immobilisation> excelToImmobilisation(InputStream is, String date, String company_name) throws ParseException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheet("immobilisation");
        List<Immobilisation> immobilisations = new ArrayList<>();


        // Création d'une instance d'exercice avec la date
        Exercice exercice = new Exercice();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        exercice.setDateExercice(sdf.parse(date));
        exercice.setCompany_name(company_name);

        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum() + 1; i++){

            if (i == 0) {
                continue;
            }

            Row row = sheet.getRow(i);
            Immobilisation immobilisation = new Immobilisation();
            immobilisation.setExercice(exercice);

            String name = getCellValuesAsString(row.getCell(0));
            //DataFormatter dataFormatter = new DataFormatter();
            Date date_immo = row.getCell(1).getDateCellValue();
            Double prixAcqui = getCellValuesAsDouble(row.getCell(2));
            Double cout = getCellValuesAsDouble(row.getCell(3));
            Double amortAnterieur = getCellValuesAsDouble(row.getCell(4));
            Double taux_amort = getCellValuesAsDouble(row.getCell(5));
            Double amortDeduit = getCellValuesAsDouble(row.getCell(6));
            Double dea = getCellValuesAsDouble(row.getCell(7));
            Double deaGlobal = getCellValuesAsDouble(row.getCell(8));

            immobilisation.setName(name);
            immobilisation.setDateAquisition(date_immo);
            immobilisation.setPrixAquisition(prixAcqui);
            immobilisation.setCoutDeRevient(cout);
            immobilisation.setAmortAnterieur(amortAnterieur);
            immobilisation.setTaux_amort(taux_amort);
            immobilisation.setAmortDeduitBenefice(amortDeduit);
            immobilisation.setDea(dea);
            immobilisation.setDeaGlobal(deaGlobal);

            immobilisations.add(immobilisation);
        }

        exerciceRepository.save(exercice);
        workbook.close();

        return immobilisations;
    }

    // Cleaning to have format Double
    private static Double convertDhStringToDouble(String dhNumber) {
        // enlever les caractères du résultat string ( à savoir , et DH )
        String cleanedInput = dhNumber.replaceAll(",", "");
        cleanedInput = cleanedInput.replaceAll(" DH", "");

        System.out.println(cleanedInput);
        try {
            return Double.parseDouble(cleanedInput);
        } catch (NumberFormatException e) {
        // handle in case where data is double format
            throw new RuntimeException("Error converting string to Double: " + e.getMessage());
        }
    }

    // In case we have an account number less than 8 from data excel
    private static String repeat(int repetitions) {
        return String.join("", Collections.nCopies(repetitions, "0"));
    }

    private static String getCellValuesAsString(Cell cell) {
        CellType cellType = cell.getCellType();
        String val = "";

        switch (cellType) {
            case STRING:
                val = cell.getStringCellValue();
                break;

            case NUMERIC:
                val = String.valueOf(cell.getNumericCellValue());
                break;

            case BLANK:
                break;
        }

        return val;

    }

    private static Double getCellValuesAsDouble(Cell cell) {
        if(cell == null){
            return 0.00;
        }
        else {
            Double val = 0.00;
            CellType cellType = cell.getCellType();
            switch (cellType) {
                case STRING:
                    val = Double.parseDouble(cell.getStringCellValue());
                    break;

                case NUMERIC:
                    val = cell.getNumericCellValue();
                    break;

                case BLANK:
                    break;
            }
            return val;
        }

    }

}
