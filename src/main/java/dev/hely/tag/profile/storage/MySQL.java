package dev.hely.tag.profile.storage;

import dev.hely.tag.Neon;
import dev.hely.tag.profile.StorageHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MySQL implements StorageHook {
    public static Connection connection;

    public static void connect(){
        String host = Neon.getPlugin().getConfig().getString("settings.storage.mysql.host");
        int port = Neon.getPlugin().getConfig().getInt("settings.storage.mysql.port");
        String user = Neon.getPlugin().getConfig().getString("settings.storage.mysql.user");
        String database = Neon.getPlugin().getConfig().getString("settings.storage.mysql.database");
        String password = Neon.getPlugin().getConfig().getString("settings.storage.mysql.password");
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&autoReconnectForPools=true&interactiveClient=true&characterEncoding=UTF-8", user, password);
            Bukkit.getLogger().info("MySQL connection success");
        }
        catch (SQLException e)
        {
            Bukkit.getLogger().info("&cMySQL connection failed");
            e.printStackTrace();
        }
    }

    private static boolean isConnected(){
        return connection != null;
    }

    public static void disconnect(){
        if(!isConnected()){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDatabase() {
        try {
            if (connection.isClosed()) {
                connect();
            }
            PreparedStatement ps = MySQL.connection.prepareStatement("CREATE TABLE IF NOT EXISTS Neon "+
                    "(UUID VARCHAR(100), Tag VARCHAR(100))");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(Player player) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Neon WHERE UUID =?");
            ps.setString(1, player.getUniqueId().toString());

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createUser(Player player) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Neon(`UUID`,`Tag`) VALUES (?, ?)");
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, "");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTag(UUID player) {
        try {
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT Tag FROM Neon WHERE UUID =?");
            ps.setString(1, player.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Tag");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setTag(UUID player, String tag) {
        try {
            PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE Neon SET Tag =? WHERE UUID=?");
            ps.setString(1, tag);
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
