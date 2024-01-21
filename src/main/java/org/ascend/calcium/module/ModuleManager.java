package org.ascend.calcium.module;

import lombok.Getter;
import lombok.Setter;
import org.ascend.calcium.conversion.ConversionManager;
import org.ascend.calcium.module.category.CategoryManager;
import org.ascend.calcium.module.tag.TagsManager;
@Setter
@Getter
public class ModuleManager {

    private TagsManager tags;
    private CategoryManager category;
    private ConversionManager conversion;

    public ModuleManager(){
        this.category = new CategoryManager();
        this.tags = new TagsManager();
        this.conversion = new ConversionManager();
    }

    public void onDisable(){
        this.category.onDisable();
        this.tags.onDisable();
    }
}
