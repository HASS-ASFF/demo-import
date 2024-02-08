package com.api.demoimport.service;

import com.api.demoimport.entity.BalanceDetail;
import com.api.demoimport.entity.PlanComptable;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

public interface ExcelHelperService {
    List<PlanComptable> excelToPlanComptable(InputStream is) throws ParseException;
    List<BalanceDetail> excelToBalanceDetail(InputStream is) throws ParseException;

}
