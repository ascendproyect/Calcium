package dev.hely.tag.module;

import dev.hely.tag.module.category.CategoryManager;
import dev.hely.tag.module.tag.TagsManager;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModuleManager {

    private TagsManager tags;
    private CategoryManager category;

    public ModuleManager(){
        this.category = new CategoryManager();
        this.tags = new TagsManager();
    }

    public void onDisable(){
        this.category.onDisable();
        this.tags.onDisable();
    }
}
