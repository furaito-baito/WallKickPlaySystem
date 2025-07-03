package jp.furaito.baito.wallkickPlaySystem.gui;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import jp.furaito.baito.wallkickPlaySystem.util.ItemBuilder;
import jp.furaito.baito.wallkickPlaySystem.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * ステージ管理画面
 */
public class StageManagerGUI extends MultiPageGUI {

    public static final NamespacedKey STAGE_UUID = new NamespacedKey(WallkickPlaySystem.getPlugin(), "stage_uuid");

    /**
     * コンストラクタ
     *
     * @param player     表示するプレイヤー
     * @param pageNumber ページ番号
     */
    public StageManagerGUI(Player player, int pageNumber) {
        super(player, pageNumber);
    }

    /**
     * インベントリを作成する
     *
     * @return インベントリ
     */
    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(this, 9 * 6, "ステージ管理画面");
    }

    @Override
    public void renderContents() {
        //TODO ステージ取得 + ブロック化

        // 境界線を引く
        GUIUtil.drawHorizontalLine(inventory, Material.GRAY_STAINED_GLASS_PANE, 4, false);

        //TODO ステージの数が36を超えたらページボタンを表示する
        int stageCount = 1; // 仮

        if (stageCount < 36) {
            ItemStack backward = GUIUtil.createBackward();
        }

        // ステージ追加ボタン
        ItemStack addStageButton = new ItemBuilder(Material.LIME_WOOL)
                .setDisplayName("ステージを追加")
                .addTag(GUI_ID, "add_stage")
                .addAllItemFlags()
                .build();
        inventory.setItem(49, addStageButton);

        // ヘルプボタン
        ItemStack helpButton = new ItemBuilder(Material.BOOK)
                .setDisplayName("ヘルプ")
                .addTag(GUI_ID, "help")
                .addAllItemFlags()
                .build();
        inventory.setItem(53, helpButton);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (!ItemUtils.isValid(event.getCurrentItem())) return;
        ItemStack clickedItem = event.getCurrentItem();

        if (ItemUtils.hasTag(clickedItem, GUI_ID, "border")) return;
        if (ItemUtils.hasTag(clickedItem, GUI_ID, "add_stage")) {
            //TODO ステージ追加画面遷移
            return;
        }
        if (ItemUtils.hasTag(clickedItem, GUI_ID, "help")) {
            //TODO ヘルプ表示
            return;
        }
        if (ItemUtils.hasNamespacedKey(clickedItem, STAGE_UUID)) {
            //TODO ステージIdを利用して表示するデータを変更
            String stageUUID = ItemUtils.getData(clickedItem, STAGE_UUID);
            GUIManager.goTo(new StageDetailGUI(UUID.randomUUID(), getPlayer()));
        }
    }

}
