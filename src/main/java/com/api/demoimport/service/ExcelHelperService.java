package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.repository.PlanComptableRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelHelperService {

    @Autowired
    private ExcelPlanComptableService excelPlanComptableServiceImpl;
        public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        public static String TYPE2 = "application/vnd.ms-excel";

    public static boolean hasExcelFormat(MultipartFile file) {
        String fileType = file.getContentType();
        return fileType != null
                && (fileType.equals(TYPE) ||
                fileType.equals(TYPE2));
    }


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
                                planComptable.setNo_compte((long) currentCell.getNumericCellValue());
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

    public  List<BalanceDetail> excelToBalanceDetail(InputStream is) throws ParseException{
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet("balance_detail");
            Iterator<Row> rows = sheet.iterator();

            List<BalanceDetail> balanceDetails = new ArrayList<>();

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

                    switch (cellIdx) {
                        case 0:
                            String the_class = currentCell.getCellType() == CellType.STRING || currentCell
                                    .getCellType() == CellType.FORMULA
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());
                            Double classValue1 = Double.valueOf(the_class);

                            balanceDetail.setThe_class(classValue1.longValue());
                            break;

                        case 1:

                            String compte = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue()).replace(".", "").replace("E7",
                                    repeat(11 - String.valueOf(currentCell.getNumericCellValue()).length(), "0"));


                            Double classValue2 = Double.valueOf(compte);

                            Optional<PlanComptable> planComptable = excelPlanComptableServiceImpl
                                    .search(classValue2.longValue());

                            if (planComptable.isPresent()) {
                                balanceDetail.setCompte(planComptable.get());
                            } else {
                                // do nothing
                            }
                            break;

                        case 2:
                            String label = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            balanceDetail.setLabel(label);

                            break;

                        case 3:
                            String debitDex = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue3 = convertDhStringToDouble(debitDex);

                            balanceDetail.setDebitDex(classValue3);

                            break;

                        case 4:
                            String creditDex = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue4 = convertDhStringToDouble(creditDex);

                            balanceDetail.setCreditDex(classValue4);

                            break;

                        case 5:
                            String debitEx = currentCell.getCellType() == CellType.STRING ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue5 = convertDhStringToDouble(debitEx);

                            balanceDetail.setDebitEx(classValue5);

                            break;

                        case 6:
                            String creditEx = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue6 = convertDhStringToDouble(creditEx);

                            balanceDetail.setCreditEx(classValue6);

                            break;

                        case 7:
                            String debitFex = currentCell.getCellType() == CellType.STRING
                                    ? currentCell.getStringCellValue()
                                    : String.valueOf(currentCell.getNumericCellValue());

                            Double classValue7 = convertDhStringToDouble(debitFex);

                            balanceDetail.setDebitFex(classValue7);

                            break;

                        case 8:
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
            }

            workbook.close();

            return balanceDetails;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    // convert number from "97,545.00" to double number
    private Double convertDhStringToDouble(String dhNumber) {
        // Remove commas from the input string
        String cleanedInput = dhNumber.replaceAll(",", "");
        cleanedInput = cleanedInput.replaceAll(" DH", "");
        try {
            return Double.parseDouble(cleanedInput);
        } catch (NumberFormatException e) {
            // Handle the case where the string cannot be parsed as a Double
            System.err.println("Error converting string to Double: " + e.getMessage());
            return null; // or throw an exception, depending on your requirements
        }
    }

    private static String repeat(int repetitions, String character) {
        return Collections.nCopies(repetitions, character).stream()
                .collect(Collectors.joining());
    }
}
