package com.api.demoimport.service;

import com.api.demoimport.entity.Balance;
import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.repository.BalanceRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelHelperServiceImpl implements ExcelHelperService{

    @Autowired
    private ExcelPlanComptableServiceImpl excelPlanComptableServiceImpl;
    @Autowired
    private BalanceRepository balanceRepository;

    // Définition des types MIME pour les fichiers Excel
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String TYPE2 = "application/vnd.ms-excel";

    // Vérifie si le fichier est au format Excel
    public static boolean hasExcelFormat(MultipartFile file) {
        String fileType = file.getContentType();
        return fileType != null
                && (fileType.equals(TYPE) ||
                fileType.equals(TYPE2));
    }

    // Conversion d'un fichier Excel en une liste d'objets PlanComptable
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

    // Conversion d'un fichier Excel en une liste d'objets BalanceDetail
    public  List<BalanceDetail> excelToBalanceDetail(InputStream is) throws ParseException{
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet("balance_detail");
            Iterator<Row> rows = sheet.iterator();

            List<BalanceDetail> balanceDetails = new ArrayList<>();

            // Création d'une instance de Balance avec la date au 30/12/2023
            Balance balance = new Balance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            balance.setDate(sdf.parse("2023-12-30"));



            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();


                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                BalanceDetail balanceDetail = new BalanceDetail();



                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    balanceDetail.setBalance(balance);

                    switch (cellIdx) {
// Récupération des données de chaque cellule et assignation aux attributs de l'objet BalanceDetail

                        /*case 0:

// Récupère la valeur de la classe à partir de la cellule actuelle.
// Si le contenu de la cellule est une chaîne de caractères ou une formule, récupère la valeur sous forme de chaîne de caractères.
// Sinon, convertit la valeur numérique en chaîne de caractères

                            String the_class = currentCell.getCellType() == CellType.STRING || currentCell
                                    .getCellType() == CellType.FORMULA
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());
// Convertir la valeur récupéré en Double
                            Double classValue1 = Double.valueOf(the_class);
                            balanceDetail.setThe_class(classValue1.longValue());
                            break;*/

                        case 0:

                            String compte = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue()).replace(".", "").replace("E7",
                                    repeat(11 - String.valueOf(currentCell.getNumericCellValue()).length(), "0"));



                            Double classValue2 = Double.valueOf(compte);
// Vérifier si la valeur récupéré existe dans la table plan comptable (numéro de compte)
                            Optional<PlanComptable> planComptable = excelPlanComptableServiceImpl
                                    .search(classValue2.longValue());

                            if (planComptable.isPresent()) {
                                balanceDetail.setCompte(planComptable.get());
                                balanceDetail.setN_Compte(planComptable.get().getNo_compte());
                                balanceDetail.setThe_class(planComptable.get().getThe_class());
                            } else {
                                //System.out.println("here is ; " + compte);
                            }
                            break;

                        case 1:
                            String label = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            balanceDetail.setLabel(label);

                            break;

                        case 2:
                            String debitDex = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            //Double classValue3 = convertDhStringToDouble(debitDex);
                            balanceDetail.setDebitDex(Double.parseDouble(debitDex));

                            break;

                        case 3:
                            String creditDex = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue4 = convertDhStringToDouble(creditDex);

                            balanceDetail.setCreditDex(classValue4);

                            break;

                        case 4:
                            String debitEx = currentCell.getCellType() == CellType.STRING ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue5 = convertDhStringToDouble(debitEx);

                            balanceDetail.setDebitEx(classValue5);

                            break;

                        case 5:
                            String creditEx = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue6 = convertDhStringToDouble(creditEx);

                            balanceDetail.setCreditEx(classValue6);

                            break;

                        case 6:
                            String debitFex = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue7 = convertDhStringToDouble(debitFex);

                            balanceDetail.setDebitFex(classValue7);

                            break;

                        case 7:
                            String creditFex = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue8 = convertDhStringToDouble(creditFex);


                            balanceDetail.setCreditFex(classValue8);

                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                balanceDetails.add(balanceDetail);
                balanceRepository.save(balance);
            }

            workbook.close();

            return balanceDetails;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }


    private Double convertDhStringToDouble(String dhNumber) {
        // enlever les caractères du résultat string ( à savoir , et DH )
        String cleanedInput = dhNumber.replaceAll(",", "");
        cleanedInput = cleanedInput.replaceAll(" DH", "");
        try {
            return Double.parseDouble(cleanedInput);
        } catch (NumberFormatException e) {
        // handle in case where data is double format
            System.err.println("Error converting string to Double: " + e.getMessage());
            return null; // or throw an exception, depending on your requirements
        }
    }

    // methode pour compléter 8 chiffres pour le numéro de compte
    private static String repeat(int repetitions, String character) {
        return Collections.nCopies(repetitions, character).stream()
                .collect(Collectors.joining());
    }
}
