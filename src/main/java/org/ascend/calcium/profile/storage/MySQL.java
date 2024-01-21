package org.ascend.calcium.profile.storage;

import org.ascend.calcium.Calcium;
import org.ascend.calcium.profile.StorageHook;
import dev.hely.lib.CC;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class MySQL implements StorageHook {

    private Connection connection;

    private boolean isConnected(){
        return connection != null;
    }

    public boolean userExists(UUID player) {
        try {
            PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM Calcium WHERE UUID =?");
            ps.setString(1, player.toString());

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createUser(UUID player) {
        try {
            PreparedStatement ps = this.connection.prepareStatement("INSERT INTO Calcium(`UUID`,`Tag`) VALUES (?, ?)");
            ps.setString(1, player.toString());
            ps.setString(2, "");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTag(UUID player) {
        if(!userExists(player)) createUser(player);
        try {
            PreparedStatement ps = this.connection.prepareStatement("SELECT Tag FROM Calcium WHERE UUID =?");
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
    public List<String> getFavourites(UUID player) {
        return null;
    }

    @Override
    public void setTag(UUID player, String tag) {
        if(!userExists(player)) createUser(player);
        try {
            PreparedStatement ps = this.connection.prepareStatement("UPDATE Calcium SET Tag =? WHERE UUID=?");
            ps.setString(1, tag);
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFavourites(UUID player, List<String> favourites) {

    }

    @Override
    public void onEnable() {
        String host = Calcium.getInstance().getConfig().getString("settings.storage.mysql.host");
        int port = Calcium.getInstance().getConfig().getInt("settings.storage.mysql.port");
        String user = Calcium.getInstance().getConfig().getString("settings.storage.mysql.user");
        String database = Calcium.getInstance().getConfig().getString("settings.storage.mysql.database");
        String password = Calcium.getInstance().getConfig().getString("settings.storage.mysql.password");

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&autoReconnectForPools=true&interactiveClient=true&characterEncoding=UTF-8", user, password);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&6[Calcium] &aYou have successfully connected to the &6MySQL Database&a!"));
        } catch (SQLException e) {
            Bukkit.getLogger().info("&cMySQL connection failed");
            e.printStackTrace();
        }

        try {
            PreparedStatement ps = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS Calcium "+
                    "(UUID VARCHAR(100), Tag VARCHAR(100))");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if(!this.isConnected()){
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
