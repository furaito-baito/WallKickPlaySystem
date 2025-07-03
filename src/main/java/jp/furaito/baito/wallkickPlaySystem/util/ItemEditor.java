package jp.furaito.baito.wallkickPlaySystem.util;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ItemEditor {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemEditor(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
        if (this.meta == null) {
            throw new IllegalArgumentException("Item does not support ItemMeta: " + item.getType());
        }
    }

    public ItemEditor setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemEditor setLore(String... lines) {
        meta.setLore(Arrays.asList(lines));
        return this;
    }

    public ItemEditor setLore(List<String> lines) {
        meta.setLore(lines);
        return this;
    }

    public ItemEditor addTag(NamespacedKey key, String value) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, value);
        return this;
    }

    public ItemEditor setGlowing(boolean glow) {
        if (glow) {
            meta.addEnchant(Enchantment.INFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemEditor setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemEditor addItemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemEditor addAllItemFlags() {
        meta.addItemFlags(ItemFlag.values());
        return this;
    }

    public ItemEditor setHideTooltip(boolean hideTooltip) {
        meta.setHideTooltip(hideTooltip);
        return this;
    }

    public ItemStack apply() {
        item.setItemMeta(meta);
        return item;
    }
}

