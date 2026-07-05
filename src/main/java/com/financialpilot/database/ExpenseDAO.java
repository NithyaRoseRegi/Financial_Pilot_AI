package com.financialpilot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.financialpilot.model.Expense;

public class ExpenseDAO {

    // Add Expense
    public static boolean addExpense(Expense expense) {

        String sql = """
                INSERT INTO expenses
                (user_id, account_id, title, category, amount, expense_date, note)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expense.getUserId());
            stmt.setInt(2, expense.getAccountId());
            stmt.setString(3, expense.getTitle());
            stmt.setString(4, expense.getCategory());
            stmt.setDouble(5, expense.getAmount());
            stmt.setDate(6, java.sql.Date.valueOf(expense.getDate()));
            stmt.setString(7, expense.getNote());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get Expense By ID
    public static Expense getExpenseById(int expenseId) {

        String sql = "SELECT * FROM expenses WHERE expense_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expenseId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Expense expense = new Expense();

                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setAccountId(rs.getInt("account_id"));
                expense.setTitle(rs.getString("title"));
                expense.setCategory(rs.getString("category"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDate(rs.getDate("expense_date").toLocalDate());
                expense.setNote(rs.getString("note"));

                return expense;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get All Expenses of a User
    public static ArrayList<Expense> getAllExpensesByUser(int userId) {

        ArrayList<Expense> expenses = new ArrayList<>();

        String sql = "SELECT * FROM expenses WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Expense expense = new Expense();

                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setAccountId(rs.getInt("account_id"));
                expense.setTitle(rs.getString("title"));
                expense.setCategory(rs.getString("category"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDate(rs.getDate("expense_date").toLocalDate());
                expense.setNote(rs.getString("note"));

                expenses.add(expense);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return expenses;
    }

    // Update Expense
    public static boolean updateExpense(Expense expense) {

        String sql = """
                UPDATE expenses
                SET account_id = ?, title = ?, category = ?, amount = ?, expense_date = ?, note = ?
                WHERE expense_id = ? AND user_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expense.getAccountId());
            stmt.setString(2, expense.getTitle());
            stmt.setString(3, expense.getCategory());
            stmt.setDouble(4, expense.getAmount());
            stmt.setDate(5, java.sql.Date.valueOf(expense.getDate()));
            stmt.setString(6, expense.getNote());
            stmt.setInt(7, expense.getExpenseId());
            stmt.setInt(8, expense.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete Expense
    public static boolean deleteExpense(int expenseId, int userId) {

        String sql = "DELETE FROM expenses WHERE expense_id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expenseId);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}