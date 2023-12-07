package org.example;

import java.sql.*;

public class SQLiteDB {
    private static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\..\\DataBase.db";


    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS DataBase (\n"
                + "	userId Long PRIMARY KEY,\n"
                + "	TgId Long\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertUser(Long userId) {
        String sql = "INSERT INTO DataBase(userId) VALUES(?)";

        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTg(Long userId, Long tgId) {
        String sql = "UPDATE DataBase SET TgId = ? WHERE userId = ?";

        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, tgId);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Long getTgId(Long userId){
        String sql = "SELECT TgId "
                + "FROM DataBase WHERE userId = ?";
        Long tgId = null;
        try (Connection conn = Connect.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setDouble(1,userId);
            //
            ResultSet rs  = pstmt.executeQuery();

            tgId = rs.getLong("TgId");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tgId;
    }
}
