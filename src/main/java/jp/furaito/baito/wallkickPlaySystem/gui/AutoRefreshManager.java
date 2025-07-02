package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class AutoRefreshManager {

    private static Plugin plugin;

    private static final Map<UUID, Refreshable> allRefreshable = new HashMap<>();
    private static final Map<UUID, BukkitTask> tasks = new HashMap<>();

    /**
     * 初期化
     * @param plugin プラグイン
     */
    public static void init(Plugin plugin) {
        AutoRefreshManager.plugin = plugin;
    }

    /**
     * 自動更新の対象に加える
     * @param uuid プレイヤーのuuid
     * @param page 更新するページ
     */
    public static void register(UUID uuid, Refreshable page) {
        unregister(uuid);

        long interval = page.refreshIntervalTicks();
        if (interval <= 0) return;

        allRefreshable.put(uuid, page);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Player player = Bukkit.getPlayer(uuid);
                Refreshable refreshable = allRefreshable.get(uuid);
                if (player == null || !player.isOnline() || refreshable == null) {
                    unregister(uuid); // 自動解除
                    return;
                }

                refreshable.refresh();
            }
        }.runTaskTimer(plugin, interval, interval);

        tasks.put(uuid, task);
    }

    /**
     * 自動更新の対象から外す
     * @param uuid プレイヤーのuuid
     */
    public static void unregister(UUID uuid) {
        allRefreshable.remove(uuid);

        BukkitTask task = tasks.remove(uuid);
        if (task != null) task.cancel();
    }
}

