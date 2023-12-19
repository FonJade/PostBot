package database;

import java.sql.*;

import static vk.ApiAppRequests.getOwnerId;

public class SQLiteDB {
    private static final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\..\\DataBase.db";


    public static void createNewTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS DataBase (
                	userId Long PRIMARY KEY,
                	TgId Long,
                	VkId String
                );""";

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
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTg(Long userId, Long tgId) {
        String sql = "UPDATE DataBase SET TgId = ? WHERE userId = ?";

        try (Connection conn = Connect.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, tgId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
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

    public static void insertVk(Long user_id) {
        String sql = "UPDATE DataBase SET VkId = ? WHERE userId = ?";
        String vk_id = getOwnerId();
        try (Connection conn = Connect.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, vk_id);
            preparedStatement.setLong(2, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
