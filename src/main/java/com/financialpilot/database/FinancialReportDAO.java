package com.financialpilot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.financialpilot.dto.FinancialReport;

public class FinancialReportDAO {

    public static FinancialReport getMonthlyReport(
            int userId,
            int month,
            int year) {

        FinancialReport report =
                new FinancialReport();


        try {

            Connection connection =
                    DBConnection.getConnection();


            // =====================================
            // 1. TOTAL BANK BALANCE
            // =====================================

            String balanceSQL =
                    "SELECT COALESCE(SUM(balance), 0) " +
                    "FROM bank_accounts " +
                    "WHERE user_id = ?";


            PreparedStatement balanceStatement =
                    connection.prepareStatement(balanceSQL);

            balanceStatement.setInt(1, userId);


            ResultSet balanceResult =
                    balanceStatement.executeQuery();


            if (balanceResult.next()) {

                report.setTotalBankBalance(
                        balanceResult.getDouble(1));

            }


            // =====================================
            // 2. MONTHLY EXPENSES
            // =====================================

            String expenseSQL =
                    "SELECT " +
                    "COALESCE(SUM(amount), 0), " +
                    "COUNT(*) " +
                    "FROM expenses " +
                    "WHERE user_id = ? " +
                    "AND MONTH(expense_date) = ? " +
                    "AND YEAR(expense_date) = ?";


            PreparedStatement expenseStatement =
                    connection.prepareStatement(expenseSQL);


            expenseStatement.setInt(1, userId);
            expenseStatement.setInt(2, month);
            expenseStatement.setInt(3, year);


            ResultSet expenseResult =
                    expenseStatement.executeQuery();


            if (expenseResult.next()) {

                report.setTotalExpenses(
                        expenseResult.getDouble(1));

                report.setExpenseCount(
                        expenseResult.getInt(2));

            }


            // =====================================
            // 3. BUDGET
            // =====================================

            String budgetSQL =
                    "SELECT COALESCE(amount, 0) " +
                    "FROM budgets " +
                    "WHERE user_id = ? " +
                    "AND month = ? " +
                    "AND year = ?";


            PreparedStatement budgetStatement =
                    connection.prepareStatement(budgetSQL);


            budgetStatement.setInt(1, userId);
            budgetStatement.setInt(2, month);
            budgetStatement.setInt(3, year);


            ResultSet budgetResult =
                    budgetStatement.executeQuery();


            if (budgetResult.next()) {

                report.setBudgetAmount(
                        budgetResult.getDouble(1));

            }


            // =====================================
            // 4. REMAINING BUDGET
            // =====================================

            report.setRemainingBudget(
                    report.getBudgetAmount()
                    - report.getTotalExpenses()
            );


            // =====================================
            // 5. HIGHEST EXPENSE
            // =====================================

            String highestExpenseSQL =
                    "SELECT amount, category " +
                    "FROM expenses " +
                    "WHERE user_id = ? " +
                    "AND MONTH(expense_date) = ? " +
                    "AND YEAR(expense_date) = ? " +
                    "ORDER BY amount DESC " +
                    "LIMIT 1";


            PreparedStatement highestStatement =
                    connection.prepareStatement(
                            highestExpenseSQL);


            highestStatement.setInt(1, userId);
            highestStatement.setInt(2, month);
            highestStatement.setInt(3, year);


            ResultSet highestResult =
                    highestStatement.executeQuery();


            if (highestResult.next()) {

                report.setHighestExpense(
                        highestResult.getDouble(
                                "amount"));

                report.setHighestExpenseCategory(
                        highestResult.getString(
                                "category"));

            }


            connection.close();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to generate financial report."
            );
        }


        return report;
    }
}