package dev.hely.lib.command;

import dev.hely.hcf.events.dtc.DTCExecutor;
import dev.hely.hcf.events.schedule.ScheduleExecutor;
import dev.hely.lib.command.sub.SubCommandHandler;
import dev.hely.lib.manager.Manager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created By LeandroSSJ
 * Created on 28/08/2021
 */
public enum SubCommandManager implements Manager {

    INSTANCE;

    private ScheduleExecutor scheduleExecutor;
    private DTCExecutor dtcExecutor;


    @Override
    public void onEnable(JavaPlugin plugin) {


        try {
            this.setupSubCommand();
        } catch(Exception e) {
        }
    }



    @Override
    public void onDisable(JavaPlugin plugin) {
        this.scheduleExecutor.disable();
        this.dtcExecutor.disable();
    }

    private void setupSubCommand() throws Exception {
        for(Field field : this.getClass().getDeclaredFields()) {
            if(field.getType().getSuperclass() != SubCommandHandler.class) continue;

            field.setAccessible(true);

            Constructor<?> constructor = field.getType().getDeclaredConstructor();
            field.set(this, constructor.newInstance());
        }

    }


}
