package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class StageDetailGUI implements Listener {




    public static Listener getGUIListener() {
        return new Listener() {
            @EventHandler
            public void listenInventory(InventoryPickupItemEvent event) {

            }
        };
    }

}
