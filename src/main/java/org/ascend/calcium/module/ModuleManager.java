package org.ascend.calcium.module;

import org.ascend.calcium.module.category.CategoryManager;
import org.ascend.calcium.module.tag.TagsManager;
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
