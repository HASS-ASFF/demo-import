package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ExcelHelperService {

    // Convert EXCEL DATA TO DB (PlanComptable & BalanceDetail)
    List<PlanComptable> excelToPlanComptable(InputStream is) throws ParseException;
    List<BalanceDetail> excelToBalanceDetail(InputStream is, String date,String company_name) throws ParseException, IOException;

    Double convertDhStringToDouble(String dhNumber);
}
