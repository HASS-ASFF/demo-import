package com.api.demoimport.helper;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import com.api.demoimport.repository.PlanComptableRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

        public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        public static boolean hasExcelFormat(MultipartFile file) {

            if (!TYPE.equals(file.getContentType())) {
                System.out.println(file.getContentType());
                System.out.println(TYPE);
                return false;
            }

            return true;
        }

        public static List<PlanComptable> excelToPlanComptable(InputStream is) {
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

    public static List<BalanceDetail> excelToBalanceDetail(InputStream is, PlanComptableRepository planComptableRepository) {
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
                            balanceDetail.setThe_class((long) currentCell.getNumericCellValue());
                            break;

                        case 1:

                            setPlanComptableByNoCompte(balanceDetail,(long) currentCell.getNumericCellValue(),planComptableRepository);
                            break;

                        case 2:
                            balanceDetail.setLabel(currentCell.getStringCellValue());
                            break;

                        case 3:
                            balanceDetail.setDebitDex(currentCell.getNumericCellValue());
                            break;

                        case 4:
                            balanceDetail.setCreditDex(currentCell.getNumericCellValue());
                            break;

                        case 5:
                            balanceDetail.setDebitEx(currentCell.getNumericCellValue());
                            break;

                        case 6:
                            balanceDetail.setCreditEx(currentCell.getNumericCellValue());
                            break;

                        case 7:
                            balanceDetail.setDebitFex(currentCell.getNumericCellValue());
                            break;

                        case 8:
                            balanceDetail.setCreditFex(currentCell.getNumericCellValue());
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

    private static void setPlanComptableByNoCompte(BalanceDetail balanceDetail, Long noCompte, PlanComptableRepository planComptableRepository) {
        PlanComptable planComptable = planComptableRepository.findById(noCompte)
                .orElseThrow(() -> new IllegalArgumentException("Aucun plan comptable trouvé pour le numéro de compte : " + noCompte));
        balanceDetail.setCompte(planComptable);
    }
}
