package dev.hely.lib.maker;

import com.google.common.collect.Lists;
import dev.hely.lib.CC;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static dev.hely.lib.Assert.assertNotNull;
import static dev.hely.lib.Assert.assertPositive;
import static dev.hely.lib.CC.translate;

public class ItemMaker {


    public static ItemMaker of(Material material) {
        return new ItemMaker(material, 1, (short) 0);
    }

    public static ItemMaker of(Material material, int amount) {
        return new ItemMaker(material, amount, (short) 0);
    }

    public static ItemMaker of(Material material, int amount, int data) {
        return new ItemMaker(material, amount, (short) data);
    }

    public static ItemMaker copyOf(ItemStack itemStack) {
        return new ItemMaker(itemStack);
    }

    private final ItemStack itemStack;

    public ItemMaker(ItemStack itemStack) {
        assertNotNull(itemStack);

        this.itemStack = itemStack;
    }

    private ItemMaker(Material material, int amount, short data) {
        assertNotNull(material);
        assertPositive(amount, data);

        this.itemStack = new ItemStack(material, amount, data);
    }

    public ItemMaker amount(int amount) {
        assertPositive(amount);

        itemStack.setAmount(amount);

        return this;
    }

    public ItemMaker data(short data) {
        assertPositive(data);

        itemStack.setDurability(data);

        return this;
    }

    public ItemMaker displayName(String name) {
        assertNotNull(name);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(translate(name));

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemMaker lore(String... lore) {
        assertNotNull(lore);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.stream(lore).map(CC::translate).collect(Collectors.toList()));

        itemStack.setItemMeta(itemMeta);

        return this;
    }
    public ItemMaker addLore(String lores) {
        assertNotNull(lores);
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = Lists.newArrayList();
        }
        lore.add(CC.translate(lores));

        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return this;
    }


    public ItemMaker lore(List<String> lore) {
        assertNotNull(lore);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore.stream().map(CC::translate).collect(Collectors.toList()));

        itemStack.setItemMeta(itemMeta);

        return this;
    }





    public ItemMaker owner(String owner) {
        assertNotNull(owner);

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setOwner(owner);

            itemStack.setItemMeta(itemMeta);
        }

        return this;
    }

    public ItemMaker color(Color color) {
        assertNotNull(color);

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
            leatherArmorMeta.setColor(color);

            itemStack.setItemMeta(itemMeta);
        }

        return this;
    }

    public ItemMaker addFakeGlow() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(CC.FAKE_GLOW, 2, true);
        
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMaker enchant(Enchantment enchantment, int level) {
        assertNotNull(enchantment);
        assertPositive(level);

        itemStack.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    public ItemMaker removeEnchantment(Enchantment enchantment) {
        assertNotNull(enchantment);

        itemStack.removeEnchantment(enchantment);

        return this;
    }

    public ItemStack build() {
        return itemStack.clone();
    }

}
