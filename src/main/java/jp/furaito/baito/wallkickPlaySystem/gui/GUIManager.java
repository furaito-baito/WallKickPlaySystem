package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class GUIManager {

    private static final Map<UUID, Deque<GUIPage>> historyMap = new HashMap<>();
    private static Plugin plugin;

    /**
     * 初期化処理
     * @param plugin 動作させるプラグイン
     */
    public static void init(Plugin plugin) {
        GUIManager.plugin = plugin;
        registerListener();
    }

    /**
     * ページを移動する
     * @param next 移動するページ
     */
    public static void goTo(GUIPage next) {
        Player player = next.getPlayer();
        UUID uuid = player.getUniqueId();
        historyMap.computeIfAbsent(uuid, k -> new ArrayDeque<>()).push(next);
        if (next instanceof Refreshable refreshable) {
            AutoRefreshManager.register(uuid, refreshable);
        }

        Bukkit.getScheduler().runTask(plugin, next::open);
    }

    /**
     * 一つ前のページに戻る
     * @param player 戻すプレイヤー
     */
    public static void goBack(Player player) {
        UUID uuid = player.getUniqueId();
        Deque<GUIPage> stack = historyMap.get(uuid);
        if (stack == null || stack.size() < 2) {
            player.closeInventory();
            return;
        }
        stack.pop();
        GUIPage prev = stack.peek();

        if (prev != null) {
            Bukkit.getScheduler().runTask(plugin, prev::open);
        }
    }

    /**
     * ページの移動履歴を強制的に消す
     * @param player 削除するプレイヤー
     */
    public static void clearHistory(Player player) {
        historyMap.remove(player.getUniqueId());
    }

    /**
     * ページの移動履歴を全て強制的に消す
     */
    public static void clearHistoryAll() {
        historyMap.clear();
    }


    /**
     * ページの履歴を消す
     * @param player プレイヤー
     * @param page ページ
     */
    public static void clearIfTop(Player player, GUIPage page) {
        UUID uuid = player.getUniqueId();
        Deque<GUIPage> stack = historyMap.get(uuid);
        if (stack != null && !stack.isEmpty() && stack.peek() == page) {
            stack.pop();
            if (stack.isEmpty()) historyMap.remove(uuid); // 完全解放
        }
    }

    /**
     * インスタンスの生成を制限
     */
    private GUIManager(){}

    /**
     * GUIのインベントリのイベントをキャンセルするリスナーを登録する
     */
    private static void registerListener() {
        Bukkit.getPluginManager().registerEvents(new Listener() {

            /**
             * クリック情報をページに委譲
             * @param event イベント情報
             */
            @EventHandler
            public void onClick(InventoryClickEvent event) {
                if (!(event.getInventory().getHolder() instanceof GUIPage page)) return;
                page.onClick(event);
            }

            /**
             * ページ解放処理
             * @param event イベント情報
             */
            @EventHandler
            public void onClose(InventoryCloseEvent event) {
                if (!(event.getInventory().getHolder() instanceof GUIPage page)) return;

                page.onClose();
                AutoRefreshManager.unregister(page.playerId);
                clearIfTop(page.getPlayer(), page);
            }
        }, plugin);
    }
}
