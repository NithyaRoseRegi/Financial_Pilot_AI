package com.financialpilot.service;

import com.financialpilot.database.SummaryDAO;
import com.financialpilot.dto.FinancialSummary;

public class SummaryService {

    public FinancialSummary getFinancialSummary(int userId) {

        return SummaryDAO.getFinancialSummary(userId);
    }
}