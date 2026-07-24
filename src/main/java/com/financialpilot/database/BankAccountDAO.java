package com.financialpilot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.financialpilot.model.BankAccount;

public class BankAccountDAO {

    // Add Bank Account
    public static boolean addBankAccount(BankAccount account) {

        String sql = """
                INSERT INTO bank_accounts
                (user_id, bank_name, account_number, account_type, balance)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getBankName());
            stmt.setString(3, account.getAccountNumber());
            stmt.setString(4, account.getAccountType());
            stmt.setDouble(5, account.getBalance());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get Account By ID
    public static BankAccount getBankAccountById(String accountId) {

        String sql = "SELECT * FROM bank_accounts WHERE account_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                BankAccount account = new BankAccount();

                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setBankName(rs.getString("bank_name"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getDouble("balance"));

                return account;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get All Accounts of a User
    public static ArrayList<BankAccount> getAllAccountsByUser(int userId) {
        ArrayList<BankAccount> accounts = new ArrayList<>();

        String sql = "SELECT * FROM bank_accounts WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                BankAccount account = new BankAccount();

                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setBankName(rs.getString("bank_name"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setAccountType(rs.getString("account_type"));
                account.setBalance(rs.getDouble("balance"));

                accounts.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    // Update Account
    public static boolean updateBankAccount(BankAccount account) {

        String sql = """
                UPDATE bank_accounts
                SET bank_name=?,
                account_number=?,
                account_type=?,
                balance=?
               WHERE account_id=?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            
            stmt.setString(1, account.getBankName());
            stmt.setString(2,account.getAccountNumber());
            stmt.setString(3, account.getAccountType());
            stmt.setDouble(4, account.getBalance());
            stmt.setInt(5, account.getAccountId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete Account
    public static boolean deleteBankAccount(String accountId) {

        String sql = "DELETE FROM bank_accounts WHERE account_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
