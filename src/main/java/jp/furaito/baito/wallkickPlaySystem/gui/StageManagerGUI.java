package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * ステージ管理画面
 */
public class StageManagerGUI extends MultiPageGUI {

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

        //TODO 仮のステージデータを表示 あとで消す
        ItemStack stageInfo = GUIUtil.createPlainInfo(Material.GRASS_BLOCK, "テストステージ", List.of());
        GUIUtil.embedData(stageInfo, "stageUUID", "stage_0");
        inventory.addItem(stageInfo);
        //TODO ↑ 後に消す


        // 境界線を引く
        GUIUtil.drawHorizontalLine(inventory, Material.GRAY_STAINED_GLASS_PANE, 4, false);

        //TODO ステージの数が36を超えたらページボタンを表示する
        int stageCount = 1; // 仮

        if (stageCount < 36) {
            ItemStack backward = GUIUtil.createBackward();
        }

        // ステージ追加ボタン
        ItemStack addStage = GUIUtil.createPlainInfo(Material.LIME_WOOL, "ステージを追加", Collections.emptyList());
        GUIUtil.embedData(addStage, "gui", "add_stage");
        inventory.setItem(49, addStage);

        // ヘルプボタン
        ItemStack help = GUIUtil.createPlainInfo(Material.BOOK, "ヘルプ", Collections.emptyList());
        GUIUtil.embedData(help, "gui", "help");
        inventory.setItem(53, help);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        ItemStack clickedItem = event.getCurrentItem();
        if (GUIUtil.containsData(clickedItem, "gui", "border")) return;
        if (GUIUtil.containsData(clickedItem, "gui", "add_stage")) {
            //TODO ステージ追加画面遷移
//            GUIManager.goTo(new StageDetailGUI());
            return;
        }
        if (GUIUtil.containsData(clickedItem, "gui", "help")) {
            //TODO ヘルプ表示
            return;
        }
        if (GUIUtil.hasKey(clickedItem, "stageUUID")) {
            //TODO ステージIdを利用して表示するデータを変更
            String stageUUID = GUIUtil.getData(clickedItem, "stageUUID");
            GUIManager.goTo(new StageDetailGUI(UUID.randomUUID(), getPlayer()));
        }
    }

}
