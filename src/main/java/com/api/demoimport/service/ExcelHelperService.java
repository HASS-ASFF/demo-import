package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.Immobilisation;
import com.api.demoimport.entity.PlanComptable;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

public interface ExcelHelperService {

    /**
     * Interface defining service operations for Excel data handling.
     * Methods include converting Excel data to database entities for PlanComptable,
     * BalanceDetail, and Immobilisation.
     */

    List<PlanComptable> excelToPlanComptable(InputStream is) throws ParseException;
    List<BalanceDetail> excelToBalanceDetail(InputStream is, String date,String company_name) throws ParseException, IOException;
    List<Immobilisation> excelToImmobilisation(InputStream is, String date,String company_name) throws ParseException, IOException;
}
