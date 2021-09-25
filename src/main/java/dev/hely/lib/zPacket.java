package dev.hely.lib;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import static dev.hely.lib.Assert.assertNotNull;
import static dev.hely.lib.util.VersionUtil.VERSION;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Sunday, July 18, 2021
 */

public class zPacket {

    private Object packet = null;
    private Class<?> packetClass = null;

    public zPacket(String name) {
        try {
            packetClass = Class.forName("net.minecraft.server." + VERSION + "." + name);
            packet = packetClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <H> Accessor<H> getFieldAccessor(String name, Class<H> type) {
        assertNotNull(name, type);

        for (Field field : packetClass.getDeclaredFields()) {
            if (field.getName().equals(name) && field.getType().isAssignableFrom(type)) {
                field.setAccessible(true);

                return new Accessor<H>() {

                    public H get() {
                        try {
                            return (H) field.get(packet);
                        } catch (IllegalAccessException exception) {
                            throw new RuntimeException("Cannot access Reflection.");
                        }
                    }

                    public void set(H value) {
                        assertNotNull(value);

                        try {
                            field.set(packet, value);
                        } catch (IllegalAccessException exception) {
                            throw new RuntimeException("Cannot access Reflection.");
                        }
                    }

                    @Override
                    public void add(Collection<H> collection) {
                        assertNotNull(collection);

                        if (type.isAssignableFrom(Collection.class)) {
                            try {
                                ((List<H>) field.get(packet)).addAll(collection);
                            } catch (IllegalAccessException exception) {
                                throw new RuntimeException("Cannot access Reflection.");
                            }
                        } else {
                            throw new IllegalArgumentException("Cannot take that field as Collection.");
                        }
                    }
                };
            }
        }

        throw new IllegalArgumentException("Cannot find a field with that name and type.");
    }

    public void sendPacket(Player player) {
        try {
            Class<?> packet = Class.forName("net.minecraft.server." + VERSION + ".Packet");
            Class<?> aClass = Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftPlayer");

            if (!packet.isAssignableFrom(packetClass)) {
                throw new IllegalArgumentException("Object cannot cast as Packet!");
            }

            Object craftPlayer = aClass.cast(player);
            Object getHandle = aClass.getMethod("getHandle").invoke(craftPlayer);
            Object playerConnection = getHandle.getClass().getField("playerConnection").get(getHandle);

            playerConnection.getClass().getMethod("sendPacket", packet).invoke(playerConnection, packet);
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }
}
