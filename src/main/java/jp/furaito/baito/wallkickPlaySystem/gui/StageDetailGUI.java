package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class StageDetailGUI extends GUIPage {

    private final UUID stageUUID;

    public StageDetailGUI(UUID stageUUID, Player player) {
        super(player);
        this.stageUUID = stageUUID;
    }

    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(this, 9 * 3, "テスト");
    }

    @Override
    public void renderContents() {
        // 背景の設定
        GUIUtil.drawBackGround(inventory, Material.GRAY_STAINED_GLASS_PANE);

        //TODO ステージのUUIDから情報を引っ張ってきて表示
        //TODO ステージの情報(仮)
        ItemStack stageInfo = GUIUtil.createPlainInfo(Material.GRASS_BLOCK, ChatColor.AQUA + "テストステージ", List.of(ChatColor.WHITE + "プレイ人数: 2(仮)"));
        inventory.setItem(9, stageInfo);


        // 戻るボタン
        ItemStack backward = GUIUtil.createBackward();
        inventory.setItem(0, backward);
    }

    /**
     * クリック時の処理
     *
     * @param event イベント情報
     */
    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;

        ItemStack clicked = event.getCurrentItem();
        if (GUIUtil.containsData(clicked, "gui", "border")) return;
        if (GUIUtil.containsData(clicked, "gui", "backward")) {
            // 前のページに戻る
            GUIManager.goBack(getPlayer());
        }
    }
}
