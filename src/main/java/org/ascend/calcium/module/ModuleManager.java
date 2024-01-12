package org.ascend.calcium.module;

import lombok.Getter;
import lombok.Setter;
import org.ascend.calcium.conversion.ConversionManager;
import org.ascend.calcium.module.category.CategoryManager;
import org.ascend.calcium.module.tag.TagsManager;
import org.ascend.calcium.util.command.CommandManager;
import org.ascend.calcium.util.command.SubCommandManager;

@Setter
@Getter
public class ModuleManager {

    private TagsManager tags;
    private CategoryManager category;
    private ConversionManager conversion;
    private CommandManager command;
    private SubCommandManager subCommand;

    public ModuleManager(){
        this.category = new CategoryManager();
        this.tags = new TagsManager();
        this.conversion = new ConversionManager();
        this.command = new CommandManager();
        this.subCommand = new SubCommandManager();

        command.onEnable();
        subCommand.onEnable();
    }

    public void onDisable(){
        this.category.onDisable();
        this.tags.onDisable();
        this.subCommand.onDisable();
    }
}
