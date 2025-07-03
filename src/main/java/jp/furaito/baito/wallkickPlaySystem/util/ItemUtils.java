package jp.furaito.baito.wallkickPlaySystem.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

/**
 * アイテムに関する汎用処理
 */
public class ItemUtils {

    /**
     * アイテムが null または Material.AIR の場合 true を返す
     */
    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    /**
     * アイテムが null でも AIR でもない場合 true を返す
     */
    public static boolean isValid(ItemStack item) {
        return !isEmpty(item);
    }

    /**
     * ItemMeta が null でない場合 true を返す
     */
    public static boolean isValid(ItemMeta meta) {
        return meta != null;
    }

    /**
     * 有効な ItemMeta を持っているか
     */
    public static boolean hasMeta(ItemStack item) {
        return isValid(item) && item.hasItemMeta();
    }

    /**
     * DisplayName を持っているか
     */
    public static boolean hasDisplayName(ItemStack item) {
        return hasMeta(item) && Objects.requireNonNull(item.getItemMeta()).hasDisplayName();
    }

    /**
     * Lore（説明文）を持っているか
     */
    public static boolean hasLore(ItemStack item) {
        return hasMeta(item) && Objects.requireNonNull(item.getItemMeta()).hasLore();
    }

    /**
     * 指定された表示名を持っているか（色コード無視するか選べる）
     */
    public static boolean hasDisplayNameEquals(ItemStack item, String name, boolean ignoreColor) {
        if (!isValid(item.getItemMeta())) return false;
        if (!hasDisplayName(item)) return false;
        String displayName = item.getItemMeta().getDisplayName();
        if (ignoreColor) {
            displayName = ChatColor.stripColor(displayName);
            name = ChatColor.stripColor(name);
        }
        return displayName.equals(name);
    }

    /**
     * タグを持っているか
     */
    public static boolean hasTag(ItemStack item, NamespacedKey key, String id) {
        if (!isValid(item) || !isValid(item.getItemMeta())) return false;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return id.equals(container.get(key, PersistentDataType.STRING));
    }

    /**
     * キーを持っているか
     */
    public static boolean hasNamespacedKey(ItemStack item, NamespacedKey key) {
        if (!isValid(item) || !isValid(item.getItemMeta())) return false;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.has(key, PersistentDataType.STRING);
    }

    /**
     * アイテムに埋め込まれているデータを消す
     */
    public static void deleteNamespaceKey(ItemStack item, NamespacedKey key) {
        if (!isValid(item) || !isValid(item.getItemMeta())) return;
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        container.remove(key);
    }

    /**
     * キーで値を取得する
     */
    public static String getData(ItemStack item, NamespacedKey key) {
        if (!isValid(item) || !isValid(item.getItemMeta())) throw new IllegalArgumentException();
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(key, PersistentDataType.STRING);
    }

    /**
     * カスタムヘッドを作成する
     *
     * @param skinURL スキンのURL
     * @return カスタムヘッド
     */
    public static ItemStack createCustomHead(String skinURL) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        if (!isValid(item) || !isValid(meta)) {
            throw new IllegalStateException();
        }

        // プロファイルを生成
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        URL url;
        try {
            url = URI.create(skinURL).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        textures.setSkin(url);
        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
        item.setItemMeta(meta);

        return item;
    }
}
