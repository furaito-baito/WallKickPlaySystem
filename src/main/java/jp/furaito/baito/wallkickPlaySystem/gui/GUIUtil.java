package jp.furaito.baito.wallkickPlaySystem.gui;

import jp.furaito.baito.wallkickPlaySystem.util.ItemBuilder;
import jp.furaito.baito.wallkickPlaySystem.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIUtil {

    /**
     * 境界用のツールチップのないアイテムを作る
     *
     * @param material マテリアル
     * @return 生成されたアイテムスタック
     */
    public static ItemStack createBorder(Material material) {
        return new ItemBuilder(material)
                .setDisplayName("")
                .addTag(GUIPage.GUI_ID, "border")
                .setHideTooltip(true)
                .build();
    }

    /**
     * インベントリに指定のアイテムで横線を作る
     *
     * @param inventory 編集するインベントリ
     * @param material  使用するマテリアル
     * @param line      行インデックス
     * @param overwrite アイテムを上書きするか
     */
    public static void drawHorizontalLine(Inventory inventory, Material material, int line, boolean overwrite) {
        ItemStack border = createBorder(material);
        int height = inventory.getSize() / 9;

        if (line > (height - 1)) {
            throw new IllegalArgumentException("out of inventory");
        }

        for (int i = 0; i < 9; i++) {
            int setIndex = 9 * line + i;
            if (overwrite || ItemUtils.isEmpty(inventory.getItem(setIndex))) {
                inventory.setItem(setIndex, border.clone());
            }
        }
    }

    /**
     * インベントリに指定のアイテムで縦線を作る
     *
     * @param inventory 編集するインベントリ
     * @param material  使用するマテリアル
     * @param line      列インデックス
     * @param overwrite アイテムを上書きするか
     */
    public static void drawVerticalLine(Inventory inventory, Material material, int line, boolean overwrite) {
        ItemStack border = createBorder(material);
        if (line < 0 || 8 < line) {
            throw new IllegalArgumentException("out of line");
        }
        int height = inventory.getSize() / 9;

        for (int i = 0; i < height; i++) {
            int setIndex = 9 * i + line;
            if (overwrite || ItemUtils.isEmpty(inventory.getItem(setIndex))) {
                inventory.setItem(setIndex, border.clone());
            }
        }
    }

    /**
     * インベントリの背景を作る
     * 既にアイテムのある場所は上書きされない
     *
     * @param inventory インベントリ
     * @param material  背景のアイテム
     */
    public static void drawBackGround(Inventory inventory, Material material) {
        drawBackGround(inventory, material, false);
    }

    /**
     * インベントリの背景を作る
     *
     * @param inventory インベントリ
     * @param material  背景のアイテム
     * @param overwrite アイテムを上書きするか
     */
    public static void drawBackGround(Inventory inventory, Material material, boolean overwrite) {
        int height = inventory.getSize() / 9;
        for (int i = 0; i < height; i++) {
            drawHorizontalLine(inventory, material, i, overwrite);
        }
    }

    /**
     * 次へボタンを作成する
     *
     * @return アイテムスタック
     */
    public static ItemStack createForward() {
        String skinURL = "http://textures.minecraft.net/texture/8e403cc7bbac73670bd543f6b0955bae7b8e9123d83bd760f6204c5afd8be7e1";

        ItemStack forward = ItemUtils.createCustomHead(skinURL);
        return new ItemBuilder(forward)
                .setDisplayName(ChatColor.WHITE + "次へ")
                .addTag(GUIPage.GUI_ID, "forward")
                .addAllItemFlags()
                .build();
    }

    /**
     * 戻るボタンを作成する
     *
     * @return アイテムスタック
     */
    public static ItemStack createBackward() {
        String skinURL = "http://textures.minecraft.net/texture/533ad5c22db16435daad61590aba51d9379142dd556d6c422a7110ca3abea50";

        ItemStack customHead = ItemUtils.createCustomHead(skinURL);
        return new ItemBuilder(customHead)
                .setDisplayName(ChatColor.WHITE + "戻る")
                .addTag(GUIPage.GUI_ID, "backward")
                .addAllItemFlags()
                .build();
    }

}
