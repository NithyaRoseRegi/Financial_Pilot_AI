package com.financialpilot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.financialpilot.dto.FinancialSummary;
public class SummaryDAO {

public static FinancialSummary getFinancialSummary(int userId) {

    FinancialSummary summary = new FinancialSummary();
    
    String bankSql = """
        SELECT SUM(balance)
        FROM bank_accounts
        WHERE user_id = ?
        """;

try (Connection conn = DBConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(bankSql)) {

    stmt.setInt(1, userId);

    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {

        summary.setTotalBankBalance(
                rs.getDouble(1));
    }

} catch (Exception e) {

    e.printStackTrace();
}
String expenseSql = """
        SELECT SUM(amount)
        FROM expenses
        WHERE user_id = ?
        """;

try (Connection conn = DBConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(expenseSql)) {

    stmt.setInt(1, userId);

    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {

        summary.setTotalExpenses(
                rs.getDouble(1));
    }

} catch (Exception e) {

    e.printStackTrace();
}

summary.setRemainingBalance(

        summary.getTotalBankBalance()

        - summary.getTotalExpenses()

);
String accountCountSql = """
        SELECT COUNT(*)
        FROM bank_accounts
        WHERE user_id = ?
        """;

try (Connection conn = DBConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(accountCountSql)) {

    stmt.setInt(1, userId);

    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {

        summary.setBankAccountCount(
                rs.getInt(1));
    }

} catch (Exception e) {

    e.printStackTrace();
}
String expenseCountSql = """
        SELECT COUNT(*)
        FROM expenses
        WHERE user_id = ?
        """;

try (Connection conn = DBConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(expenseCountSql)) {

    stmt.setInt(1, userId);

    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {

        summary.setExpenseCount(
                rs.getInt(1));
    }

} catch (Exception e) {

    e.printStackTrace();
}
String highestExpenseSql = """
        SELECT amount, category
        FROM expenses
        WHERE user_id = ?
        ORDER BY amount DESC
        LIMIT 1
        """;

try (Connection conn = DBConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(highestExpenseSql)) {

    stmt.setInt(1, userId);

    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {

        summary.setHighestExpense(
                rs.getDouble("amount"));

        summary.setHighestExpenseCategory(
                rs.getString("category"));
    }

} catch (Exception e) {

    e.printStackTrace();
}

    return summary;
}
}