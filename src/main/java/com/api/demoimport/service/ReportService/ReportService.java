package com.api.demoimport.service.ReportService;

import net.sf.jasperreports.engine.JRException;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public interface ReportService {
    ByteArrayOutputStream exportReportPassif(String  date) throws JRException;
    ByteArrayOutputStream exportReportActif(String date) throws JRException;

    ByteArrayOutputStream jasperConfiguration(String path, Map<String,Object> parameters) throws JRException;

    String getLastYear(String dateString);
}
