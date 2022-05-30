package dev.hely.tag.profile.storage;

import dev.hely.tag.Neon;
import dev.hely.tag.profile.StorageHook;

import java.sql.*;
import java.util.UUID;

public class MySQL implements StorageHook {

    public boolean userExists(UUID player) {
        try {
            PreparedStatement ps = Neon.getPlugin().getProfileManager().getConnection().prepareStatement("SELECT * FROM Neon WHERE UUID =?");
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
            PreparedStatement ps = Neon.getPlugin().getProfileManager().getConnection().prepareStatement("INSERT INTO Neon(`UUID`,`Tag`) VALUES (?, ?)");
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
            PreparedStatement ps = Neon.getPlugin().getProfileManager().getConnection().prepareStatement("SELECT Tag FROM Neon WHERE UUID =?");
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
        if(!userExists(player)) createUser(player);
        try {
            PreparedStatement ps = Neon.getPlugin().getProfileManager().getConnection().prepareStatement("UPDATE Neon SET Tag =? WHERE UUID=?");
            ps.setString(1, tag);
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
