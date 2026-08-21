package com.financialpilot.service;

import com.financialpilot.database.FinancialReportDAO;
import com.financialpilot.dto.FinancialReport;

public class FinancialReportService {

    public FinancialReport getMonthlyReport(
            int userId,
            int month,
            int year) {

        return FinancialReportDAO.getMonthlyReport(
                userId,
                month,
                year);
    }
}