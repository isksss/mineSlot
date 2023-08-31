package net.isksss.mc.mineslot.db;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.model.Chest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChestDAO {

    public void addChest(Chest chest) {
        try (Connection connection = DriverManager.getConnection(Config.DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO chests (x_coordinate, y_coordinate, z_coordinate, required_level) VALUES (?, ?, ?, ?)")) {

            statement.setInt(1, chest.getXCoordinate());
            statement.setInt(2, chest.getYCoordinate());
            statement.setInt(3, chest.getZCoordinate());
            statement.setInt(4, chest.getRequiredLevel());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Chest> getAllChests() {
        List<Chest> chests = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Config.DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM chests")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int xCoordinate = resultSet.getInt("x_coordinate");
                int yCoordinate = resultSet.getInt("y_coordinate");
                int zCoordinate = resultSet.getInt("z_coordinate");
                int requiredLevel = resultSet.getInt("required_level");
                chests.add(new Chest(id, xCoordinate, yCoordinate, zCoordinate, requiredLevel));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chests;
    }

    public void deleteChest(int id) {
        try (Connection connection = DriverManager.getConnection(Config.DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM chests WHERE id = ?")) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getChestIdByCoordinates(int x, int y, int z) {
        int id = -1; // デフォルト値（見つからなかった場合の扱い）を設定

        try (Connection connection = DriverManager.getConnection(Config.DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id FROM chests WHERE x_coordinate = ? AND y_coordinate = ? AND z_coordinate = ?")) {

            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, z);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
