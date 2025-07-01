package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIManager {

    private static final GUIManager INSTANCE = new GUIManager();
    private final List<InventoryGUI> guiList = new ArrayList<>();
    private final List<Inventory> observeInventories = new ArrayList<>();
    private Plugin plugin;

    //TODO インベントリがどのGUIの派生か判定する

    public static GUIManager getInstance() {
        return INSTANCE;
    }

    private GUIManager(){}

    public boolean hasGUI(InventoryGUI gui) {
        return guiList.contains(gui);
    }

    public void addObserveInventory(Inventory inventory) {
        observeInventories.add(inventory);
    }

    public void removeObserveInventory(Inventory inventory) {
        observeInventories.remove(inventory);
    }

    public void showGUI(Player player, InventoryGUI gui) {
        if (!guiList.contains(gui)) {
            guiList.add(gui);
            Bukkit.getPluginManager().registerEvents(gui.getGUIListener(), plugin);
        }
        gui.showGUI(player);
    }

    /**
     * GUIのインベントリのイベントをキャンセルするリスナーを登録する
     * @param plugin 登録するプラグイン
     */
    public void registerListener(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new Listener() {
            /**
             * アイテムの持ち出しを禁止してクリック情報を流す
             * @param event イベント情報
             */
            @EventHandler
            public void listenClick(InventoryClickEvent event) {
                Inventory inventory = event.getInventory();
                if(observeInventories.contains(inventory)) {
                    event.setCancelled(true);
                    //TODO GUIを特定して渡す null
                    Bukkit.getPluginManager().callEvent(new GUIEvent(event, null));
                }
            }

            /**
             * GUIを閉じたらイベントの監視から除外
             * @param event イベント情報
             */
            @EventHandler
            public void listenClose(InventoryCloseEvent event) {
                removeObserveInventory(event.getInventory());
            }

        }, plugin);
    }
}
