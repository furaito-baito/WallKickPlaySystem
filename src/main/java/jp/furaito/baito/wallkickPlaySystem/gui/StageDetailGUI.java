package jp.furaito.baito.wallkickPlaySystem.gui;

import jp.furaito.baito.wallkickPlaySystem.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        if (!ItemUtils.isValid(event.getCurrentItem())) return;

        ItemStack clicked = event.getCurrentItem();

        if (ItemUtils.hasTag(clicked, GUI_ID, "border")) return;
        if (ItemUtils.hasTag(clicked, GUI_ID, "backward")) {
            // 前のページに戻る
            GUIManager.goBack(getPlayer());
        }
    }
}
