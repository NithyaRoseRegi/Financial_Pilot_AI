package com.financialpilot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financialpilot.model.Budget;

public class BudgetDAO {

    // Add Budget
    public static boolean addBudget(Budget budget) {

        String sql = "INSERT INTO budgets(user_id, month, year, amount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, budget.getUserId());
            stmt.setInt(2, budget.getMonth());
            stmt.setInt(3, budget.getYear());
            stmt.setDouble(4, budget.getAmount());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // View Budget for a Month
    public static Budget getBudget(int userId, int month, int year) {

        String sql = "SELECT * FROM budgets WHERE user_id = ? AND month = ? AND year = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Budget budget = new Budget();

                budget.setBudgetId(rs.getInt("budget_id"));
                budget.setUserId(rs.getInt("user_id"));
                budget.setMonth(rs.getInt("month"));
                budget.setYear(rs.getInt("year"));
                budget.setAmount(rs.getDouble("amount"));

                return budget;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Budget> getAllBudgets(int userId) {

    List<Budget> budgets = new ArrayList<>();

    String sql =
            "SELECT * FROM budgets " +
            "WHERE user_id = ? " +
            "ORDER BY year DESC, month DESC";

    try (
        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(sql)
    ) {

        statement.setInt(1, userId);

        ResultSet resultSet =
                statement.executeQuery();

        while (resultSet.next()) {

            Budget budget = new Budget();

            budget.setBudgetId(
                    resultSet.getInt("budget_id"));

            budget.setUserId(
                    resultSet.getInt("user_id"));

            budget.setMonth(
                    resultSet.getInt("month"));

            budget.setYear(
                    resultSet.getInt("year"));

            budget.setAmount(
                    resultSet.getDouble("amount"));

            budgets.add(budget);
        }

    } catch (SQLException e) {

        throw new RuntimeException(e);
    }

    return budgets;
}

    // Update Budget
    public static boolean updateBudget(Budget budget) {

        String sql = "UPDATE budgets SET amount = ? WHERE budget_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, budget.getAmount());
            stmt.setInt(2, budget.getBudgetId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete Budget
    public static boolean deleteBudget(int budgetId) {

        String sql = "DELETE FROM budgets WHERE budget_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, budgetId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Calculate Total Expenses for a Month
    public static double getTotalExpenses(int userId, int month, int year) {

        String sql = """
                SELECT SUM(amount)
                FROM expenses
                WHERE user_id = ?
                AND MONTH(expense_date) = ?
                AND YEAR(expense_date) = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Remaining Budget
    public static double getRemainingBudget(int userId, int month, int year) {

        Budget budget = getBudget(userId, month, year);

        if (budget == null) {
            return 0;
        }

        double totalExpenses = getTotalExpenses(userId, month, year);

        return budget.getAmount() - totalExpenses;
    }

    // Budget Exists
    public static boolean budgetExists(int userId, int month, int year) {

        String sql = "SELECT COUNT(*) FROM budgets WHERE user_id = ? AND month = ? AND year = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}