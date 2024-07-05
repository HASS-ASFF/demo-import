package com.api.demoimport.service;

import net.sf.jasperreports.engine.JRException;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public interface ReportService {

    /**
     * Interface defining service operations for generating reports, specifically for passive and active balance sheets,
     * as well as CPC (Compte de Produit et Charge) reports using JasperReports.
     * Methods include exporting passive and active balance sheet reports, CPC reports,
     * and configuring JasperReports with provided parameters.
     * Additionally, the interface provides functionality to retrieve the last year based on a given date string.
     */

    ByteArrayOutputStream exportReportPassif(String  date,String company_name) throws JRException;
    ByteArrayOutputStream exportReportActif(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportCPC(String date, String company_name) throws JRException;
    ByteArrayOutputStream exportPassage(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable4(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportEsg(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportDetailCPC(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable8(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable9(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportDetailTva(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable14(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable15(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable17(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable18(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportTable20(String date, String company_name) throws JRException;

    ByteArrayOutputStream exportAlltable(String date, String company_name) throws JRException;

    ByteArrayOutputStream jasperConfiguration(String path, Map<String,Object> parameters) throws JRException;

    String getLastYear(String dateString);
}
