package ticpackage;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:players.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
public static void insertIfNotExists(String username) {
    String sql = "INSERT OR IGNORE INTO players (username, wins) VALUES (?, 0)";
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, username);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "username TEXT UNIQUE NOT NULL," +
                     "wins INTEGER DEFAULT 0);";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
public static void deletePlayer(String username) {
    String sql = "DELETE FROM players WHERE username = ?";
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, username);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static boolean loginOrCreatePlayer(String username) {
        String checkUser = "SELECT * FROM players WHERE username = ?";
        String insertUser = "INSERT INTO players(username) VALUES (?)";

        try (Connection conn = connect()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkUser);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                PreparedStatement insertStmt = conn.prepareStatement(insertUser);
                insertStmt.setString(1, username);
                insertStmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet getAllPlayers() {
        try {
            Connection conn = connect();
            return conn.createStatement().executeQuery("SELECT * FROM players ORDER BY wins DESC");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateUsername(String oldName, String newName) {
        String sql = "UPDATE players SET username = ? WHERE username = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, oldName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addWin(String username) {
        String sql = "UPDATE players SET wins = wins + 1 WHERE username = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
