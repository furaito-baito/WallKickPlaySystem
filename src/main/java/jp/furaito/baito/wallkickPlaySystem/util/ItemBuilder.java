package jp.furaito.baito.wallkickPlaySystem.util;


import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

/**
 * {@link ItemStack} を作成・編集するためのビルダー
 * アイテムのメタデータをチェーンメソッド形式で設定可能
 */
public class ItemBuilder {
    /** 作成対象の {@link ItemStack}。 */
    private final ItemStack item;
    /** アイテムの {@link ItemMeta}。 */
    private ItemMeta meta;

    /**
     * 指定された {@link Material} を元にビルダーを初期化する
     *
     * @param material アイテムの素材
     * @throws IllegalArgumentException 指定された素材が ItemMeta をサポートしていない場合
     */
    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
        if (this.meta == null) {
            throw new IllegalArgumentException("このアイテムは詳細なデータ設定（ItemMeta）に対応していません: " + item.getType());
        }
    }

    /**
     * 既存の ItemStack を使ってビルダーを初期化する
     *
     * @param item 編集対象のアイテム
     * @throws IllegalArgumentException ItemMeta を取得できない場合
     */
    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
        this.meta = this.item.getItemMeta();
        if (this.meta == null) {
            throw new IllegalArgumentException("このアイテムは詳細なデータ設定 (ItemMeta) に対応していません: " + item.getType());
        }
    }

    /**
     * 指定した {@link Material} に変更する
     * これを実行するとアイテムデータがリセットされます
     * @param material アイテムの素材
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setMaterial(Material material) {
        this.item.setType(material);
        this.meta = this.item.getItemMeta();
        if (this.meta == null) {
            throw new IllegalArgumentException("このアイテムは詳細なデータ設定（ItemMeta）に対応していません: " + item.getType());
        }
        return this;
    }

    /**
     * アイテムのスタック数を変更する
     * @param amount アイテムの数
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }


    /**
     * アイテムの表示名を設定する
     *
     * @param name 表示名
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * アイテムの説明文を設定する（可変長引数）
     *
     * @param lines 説明文の行
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setLore(String... lines) {
        meta.setLore(Arrays.asList(lines));
        return this;
    }

    /**
     * アイテムの説明文を設定する（リスト形式）
     *
     * @param lines 説明文の行リスト
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setLore(List<String> lines) {
        meta.setLore(lines);
        return this;
    }

    /**
     * アイテムにカスタムデータタグを追加する
     *
     * @param key   データキー（{@link NamespacedKey}）
     * @param value 保存する文字列データ
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder addTag(NamespacedKey key, String value) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, value);
        return this;
    }

    /**
     * アイテムを光らせる設定をする
     * 実際のエンチャントは表示されない
     *
     * @param glow true で光る、false で通常の見た目
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setGlowing(boolean glow) {
        if (glow) {
            meta.addEnchant(Enchantment.INFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    /**
     * アイテムを壊れないように設定する
     * ツールチップに表示されない
     *
     * @param unbreakable true で非破壊にする
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    /**
     * 指定した {@link ItemFlag} を追加する
     *
     * @param flags 追加するフラグ
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * 利用可能なすべての {@link ItemFlag} を追加する
     *
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder addAllItemFlags() {
        meta.addItemFlags(ItemFlag.values());
        return this;
    }

    /**
     * アイテムのツールチップを非表示にする
     *
     * @param hideTooltip true でツールチップを非表示にする
     * @return このビルダー（メソッドチェーン用）
     */
    public ItemBuilder setHideTooltip(boolean hideTooltip) {
        meta.setHideTooltip(hideTooltip);
        return this;
    }

    /**
     * 現在の設定をもとに {@link ItemStack} を構築する
     *
     * @return 完成した {@link ItemStack} のクローン
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}


