package me.psikuvit.betterbalance.data;

import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class MySQL {

    private Connection connection;

    public MySQL() {
        connectToDatabase("betterbalance", "root", "");
        createPlayersTable();
    }

    private void connectToDatabase(String database, String username, String password) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            Bukkit.getLogger().info("Connected to the database.");
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to connect to the database: " + e.getMessage());
        }
    }

    public void disconnectFromDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Bukkit.getLogger().info("Disconnected from the database.");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to disconnect from the database: " + e.getMessage());
        }
    }

    public boolean ifExists(UUID playerId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM players WHERE uuid = ?");
            statement.setString(1, playerId.toString());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to check if player exists: " + e.getMessage());
        }

        return false;
    }

    private void createPlayersTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players (uuid VARCHAR(36) NOT NULL, value INT NOT NULL, PRIMARY KEY (uuid))");
            statement.executeUpdate();
            Bukkit.getLogger().info("Players table created or already exists.");
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to create players table: " + e.getMessage());
        }
    }

    public void saveIntegerValue(UUID playerId, int value) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO players (uuid, value) VALUES (?, ?)");
            statement.setString(1, playerId.toString());
            statement.setInt(2, value);

            statement.executeUpdate();
            Bukkit.getLogger().info("Saved integer value for player " + playerId);
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to save integer value for player " + playerId + ": " + e.getMessage());
        }
    }
    public int getPlayerBalance(UUID playerId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT value FROM players WHERE uuid = ?");
            statement.setString(1, playerId.toString());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("value");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to get integer value for player " + playerId + ": " + e.getMessage());
        }

        return 0;
    }

    public void updatePlayerBalance(UUID playerId, int value) {
        int currentValue = getPlayerBalance(playerId);
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE players SET value = ? WHERE uuid = ?");
            statement.setInt(1, currentValue + value);
            statement.setString(2, playerId.toString());

            statement.executeUpdate();
            Bukkit.getLogger().info("Updated integer value for player " + playerId);
        } catch (SQLException e) {
            Bukkit.getLogger().info("Failed to update integer value for player " + playerId + ": " + e.getMessage());
        }
    }

    public void replace(UUID playerId, int newValue) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE players SET value = ? WHERE uuid = ?");
            statement.setInt(1, newValue);
            statement.setString(2, playerId.toString());

            statement.executeUpdate();
            Bukkit.getLogger().info("Updated integer value for player " + playerId);
        } catch (SQLException e) {
            Bukkit.getLogger().info("Failed to update integer value for player " + playerId + ": " + e.getMessage());
        }
    }
}
