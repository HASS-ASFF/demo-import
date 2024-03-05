package com.api.demoimport.service;

import net.sf.jasperreports.engine.JRException;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public interface ReportService {
    ByteArrayOutputStream exportReportPassif(String  date,String company_name) throws JRException;
    ByteArrayOutputStream exportReportActif(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportCPC(String date, String company_name) throws JRException;

    ByteArrayOutputStream jasperConfiguration(String path, Map<String,Object> parameters) throws JRException;

    String getLastYear(String dateString);
}
