package jp.furaito.baito.wallkickPlaySystem.gui;

import jp.furaito.baito.wallkickPlaySystem.util.ItemBuilder;
import jp.furaito.baito.wallkickPlaySystem.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class StageDeleteGUI extends GUIPage {

    private final UUID stageUUID;
    private final ItemStack cancelButton;
    private final ItemStack deleteButton;

    /**
     * コンストラクタ
     *
     * @param player    表示するプレイヤー
     * @param stageUUID 削除予定のステージuuid
     */
    public StageDeleteGUI(Player player, UUID stageUUID) {
        super(player);
        this.stageUUID = stageUUID;

        // 削除ボタン
        this.deleteButton = new ItemBuilder(Material.RED_WOOL)
                .setDisplayName(ChatColor.RED + "削除")
                .addTag(GUI_ID, "delete_stage")
                .addAllItemFlags()
                .build();

        // キャンセルボタン
        this.cancelButton = new ItemBuilder(Material.GRAY_WOOL)
                .setDisplayName(ChatColor.GRAY + "キャンセル")
                .addTag(GUI_ID, "cancel")
                .addAllItemFlags()
                .build();
    }

    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(this, 27, "ステージを削除しますか？");
    }

    @Override
    public void renderContents() {
        // 背景
        GUIUtil.drawBackGround(inventory, Material.RED_STAINED_GLASS_PANE);

        // 削除ボタン
        inventory.setItem(13, deleteButton);

        // キャンセルボタン
        inventory.setItem(26, cancelButton);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (!ItemUtils.isValid(event.getCurrentItem())) return;
        ItemStack item = event.getCurrentItem();

        if (ItemUtils.hasTag(item, GUI_ID, "cancel")) {
            GUIManager.goBack((Player) event.getWhoClicked());
        }
        if (ItemUtils.hasTag(item, GUI_ID, "delete_stage")) {
            //TODO ステージ削除処理追加
            event.getWhoClicked().sendMessage("削除処理を実行");
        }
    }
}
