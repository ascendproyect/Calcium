package dev.hely.lib.maker;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LocationMaker {

    public static Location destringifyLocation(String string) {
        String[] split = string.substring(1, string.length() - 2).split(",");
        World world = Bukkit.getWorld(split[0]);
        if (world == null) {
            return null;
        }
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        Location loc = new Location(world, x, y, z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        return loc;
    }
    public static String stringifyLocation(Location location) {
        return "[" + location.getWorld().getName() + "," + location.getX() + "," + location.getY() + ","
                + location.getZ() + "," + location.getYaw() + "," + location.getPitch() + "]";
    }

    public static String getBlockName(Block block) {
        return block.getType().name().replace("_" , " ").toLowerCase();
    }

    public static String getLocationNameWithWorld(Location location) {
        return getLocationName(location) + " (" + getWorldName(location) +")";
    }

    public static String getLocationName(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

    public static String getLocationNameWithWorldWithoutY(Location location) {
        return getLocationNameWithoutY(location) + " (" + getWorldName(location) +")";
    }

    public static String getLocationNameWithoutY(Location location) {
        return location.getBlockX() + ", " + location.getBlockZ();
    }

    public static String getWorldName(Location location) {
        return getWorldName(location.getWorld());
    }

    public static String getWorldName(World world) {
        switch(world.getEnvironment()) {
            case NORMAL: return "World";
            case NETHER: return "Nether";
            case THE_END: return "End";
            default: return world.getName();
        }
    }

}
