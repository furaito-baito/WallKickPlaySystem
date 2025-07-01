package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class InventoryGUI {


    public void showGUI(Player player) {
        if (GUIManager.getInstance().hasGUI(this)) {

        }

        Inventory gui = createGUI();
        player.openInventory(gui);
        GUIManager.getInstance().addObserveInventory(gui);
    }

    public Inventory createGUI() {
        return Bukkit.createInventory(null, 9 * 6);
    }

    public Listener getGUIListener() {
        return new Listener() {
            @EventHandler
            public void listenGUI(GUIEvent event) {
            }
        };
    }
}
