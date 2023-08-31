package net.isksss.mc.mineslot.db;

import net.isksss.mc.mineslot.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private String url;

    public DatabaseManager() {
        this.url = Config.DB_URL;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    private void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * データベースの初期化を行う。
     * テーブルの存在を確認し、なかった場合は作成する。
     * @return result
     */
    public void InitDatabase() {
        boolean result = false;
        try {
            this.connect();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS chests (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "x_coordinate INTEGER NOT NULL," +
                    "y_coordinate INTEGER NOT NULL," +
                    "z_coordinate INTEGER NOT NULL," +
                    "required_level INTEGER NOT NULL" +
                    ");";
            executeUpdate(createTableSQL);
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return;
    }

    private void executeUpdate(String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }
}
