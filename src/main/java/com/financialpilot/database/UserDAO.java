package com.financialpilot.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.financialpilot.exception.DatabaseException;
import com.financialpilot.model.User;
import com.financialpilot.util.PasswordUtil;

public class UserDAO {

    // Register User
    public static boolean registerUser(User user) {

        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

            stmt.setString(3, hashedPassword);
            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static User getUserByEmail(String email) {

    String sql = "SELECT * FROM users WHERE email = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            User user = new User();

            user.setUserId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));

            return user;
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return null;
}

public static boolean emailExistsForAnotherUser(String email,
                                                int userId)
        throws DatabaseException {

    String sql = """
            SELECT COUNT(*)
            FROM users
            WHERE email = ?
            AND user_id <> ?
            """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email);
        stmt.setInt(2, userId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            return rs.getInt(1) > 0;
        }

    } catch (SQLException e) {

        throw new DatabaseException(
                "Unable to connect to database.");
    }

    return false;
}

public static ArrayList<User> getAllUsers() {

    ArrayList<User> users = new ArrayList<>();

    String sql = "SELECT * FROM users";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            User user = new User();

            user.setUserId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));

            users.add(user);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return users;
}

public static boolean emailExists(String email) throws DatabaseException {

    String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    }catch(SQLException e){

    throw new DatabaseException(
            "Unable to connect to database.");
}

    return false;
}

    // Get User by ID
    public static User getUserById(int userId) {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update User
    public static boolean updateUser(User user) {

        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

            stmt.setString(3, hashedPassword);
            stmt.setInt(4, user.getUserId());

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete User
    public static boolean deleteUser(int userId) {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}