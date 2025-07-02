package jp.furaito.baito.wallkickPlaySystem.gui;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GUIUtil {

    /**
     * アイテムに指定のキーで文字を埋め込む
     * 主に識別用のidとして使う
     * @param itemStack 埋め込むアイテム
     * @param key キー
     * @param id 埋め込む文字
     */
    public static void embedData(ItemStack itemStack, String key, String id) {
        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(WallkickPlaySystem.getPlugin(), key);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        container.set(namespacedKey, PersistentDataType.STRING, id);
        itemStack.setItemMeta(meta);
    }

    /**
     * アイテムに指定のキーで文字が埋め込まれているか
     * @param itemStack 調べるアイテム
     * @param key キー
     * @param id 文字
     */
    public static boolean containsData(ItemStack itemStack, String key, String id) {
        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(WallkickPlaySystem.getPlugin(), key);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if(container.has(namespacedKey, PersistentDataType.STRING)) {
            return Objects.requireNonNull(container.get(namespacedKey, PersistentDataType.STRING)).equalsIgnoreCase(id);
        }
        return false;
    }

    /**
     * アイテムに指定のキーで埋め込まれている文字が特定の文字で始まるか
     * @param itemStack 調べるアイテム
     * @param key キー
     * @param id 文字
     */
    public static boolean startsWith(ItemStack itemStack, String key, String id) {
        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(WallkickPlaySystem.getPlugin(), key);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if(container.has(namespacedKey, PersistentDataType.STRING)) {
            return Objects.requireNonNull(container.get(namespacedKey, PersistentDataType.STRING)).startsWith(id);
        }
        return false;
    }

    /**
     * アイテムに指定のキーで埋め込まれている文字を返す
     * @param itemStack 調べるアイテム
     * @param key キー
     */
    public static String getData(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(WallkickPlaySystem.getPlugin(), key);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if(container.has(namespacedKey, PersistentDataType.STRING)) {
            return Objects.requireNonNull(container.get(namespacedKey, PersistentDataType.STRING));
        }
        throw new IllegalArgumentException("key not found");
    }

    public static boolean hasKey(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(WallkickPlaySystem.getPlugin(), key);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        return container.has(namespacedKey, PersistentDataType.STRING);
    }

    /**
     * アイテムに埋め込まれているデータを消す
     * @param itemStack 埋め込むアイテム
     * @param key キー
     */
    public static void deleteKey(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(WallkickPlaySystem.getPlugin(), key);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        container.remove(namespacedKey);
    }

    /**
     * 境界用のツールチップのないアイテムを作る
     * @param material マテリアル
     * @return 生成されたアイテムスタック
     */
    public static ItemStack createBorder(Material material) {
        // 境界線のアイテム作成
        ItemStack border = new ItemStack(material);
        embedData(border, "gui", "border");

        ItemMeta borderMeta = Objects.requireNonNull(border.getItemMeta());
        borderMeta.setDisplayName("");
        borderMeta.setLore(List.of(""));
        borderMeta.setHideTooltip(true);
        border.setItemMeta(borderMeta);
        return  border;
    }


    public static ItemStack createPlainInfo(Material material, String display, List<String> description) {
        ItemStack border = new ItemStack(material);
        ItemMeta borderMeta = Objects.requireNonNull(border.getItemMeta());
        borderMeta.setDisplayName(display);
        borderMeta.setLore(description);
        hideFlag(borderMeta);
        border.setItemMeta(borderMeta);
        return  border;
    }

    /**
     * インベントリに指定のアイテムで横線を作る
     * @param inventory 編集するインベントリ
     * @param material 使用するマテリアル
     * @param line 行インデックス
     * @param overwrite アイテムを上書きするか
     */
    public static void drawHorizontalLine(Inventory inventory, Material material ,int line, boolean overwrite) {
        ItemStack border = createBorder(material);
        int height = inventory.getSize() / 9;

        if (line > (height - 1)) {
            throw new IllegalArgumentException("out of inventory");
        }

        for (int i = 0; i < 9; i++) {
            if (overwrite) {
                inventory.setItem(9 * line + i, border.clone());
            } else {
                ItemStack checkItem = inventory.getItem(9 * line + i);
                if (checkItem == null) {
                    inventory.setItem(9 * line + i, border.clone());
                } else if (checkItem.getType().isAir()) {
                    inventory.setItem(9 * line + i, border.clone());
                }
            }
        }
    }

    /**
     * インベントリに指定のアイテムで縦線を作る
     * @param inventory 編集するインベントリ
     * @param material 使用するマテリアル
     * @param line 列インデックス
     * @param overwrite アイテムを上書きするか
     */
    public static void drawVerticalLine(Inventory inventory, Material material ,int line, boolean overwrite) {
        ItemStack border = createBorder(material);
        if (line < 0 || 8 < line) {
            throw new IllegalArgumentException("out of line");
        }
        int height = inventory.getSize() / 9;

        for (int i = 0; i < height; i++) {
            if (overwrite) {
                inventory.setItem(9 * i + line, border.clone());
            } else {
                ItemStack checkItem = inventory.getItem(9 * line + i);
                if (checkItem == null) {
                    inventory.setItem(9 * i + line, border.clone());
                } else if (checkItem.getType().isAir()) {
                    inventory.setItem(9 * i + line, border.clone());
                }
            }
        }
    }

    /**
     * インベントリの背景を作る
     * 既にアイテムのある場所は上書きされない
     * @param inventory インベントリ
     * @param material 背景のアイテム
     */
    public static void drawBackGround(Inventory inventory, Material material) {
        drawBackGround(inventory,material, false);
    }

    /**
     * インベントリの背景を作る
     * @param inventory インベントリ
     * @param material 背景のアイテム
     * @param overwrite アイテムを上書きするか
     */
    public static void drawBackGround(Inventory inventory, Material material, boolean overwrite) {
        int height = inventory.getSize() / 9;
        for (int i = 0;i < height;i++) {
            drawHorizontalLine(inventory, material, i, overwrite);
        }
    }

    /**
     * 次へボタンを作成する
     * @return アイテムスタック
     */
    public static ItemStack createForward() {
        // ページ移動のアイテム作成
        ItemStack forward = new ItemStack(Material.PLAYER_HEAD);
        embedData(forward, "gui", "forward");

        SkullMeta meta = (SkullMeta) forward.getItemMeta();

        // プロファイルを生成
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        String skinURL = "http://textures.minecraft.net/texture/8e403cc7bbac73670bd543f6b0955bae7b8e9123d83bd760f6204c5afd8be7e1";
        URL url;
        try {
            url = URI.create(skinURL).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        textures.setSkin(url);
        profile.setTextures(textures);
        Objects.requireNonNull(meta).setOwnerProfile(profile);
        meta.setDisplayName("次へ");
        hideFlag(meta);
        forward.setItemMeta(meta);

        return forward;
    }

    /**
     * 戻るボタンを作成する
     * @return アイテムスタック
     */
    public static ItemStack createBackward() {
        // ページ移動のアイテム作成
        ItemStack backward = new ItemStack(Material.PLAYER_HEAD);
        embedData(backward, "gui", "backward");

        SkullMeta meta = (SkullMeta) backward.getItemMeta();

        // プロファイルを生成
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        String skinURL = "http://textures.minecraft.net/texture/533ad5c22db16435daad61590aba51d9379142dd556d6c422a7110ca3abea50";
        URL url;
        try {
            url = URI.create(skinURL).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        textures.setSkin(url);
        profile.setTextures(textures);
        Objects.requireNonNull(meta).setOwnerProfile(profile);
        meta.setDisplayName(ChatColor.WHITE + "戻る");
        hideFlag(meta);
        backward.setItemMeta(meta);

        return backward;
    }

    /**
     * アイテムの情報を隠す + 耐久値を消す
     * @param meta 編集するメタデータ
     */
    public static void hideFlag(ItemMeta meta) {
        Arrays.stream(ItemFlag.values()).forEach(meta::addItemFlags);
        meta.setUnbreakable(true);
    }

}
