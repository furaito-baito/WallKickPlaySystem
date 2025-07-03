package jp.furaito.baito.wallkickPlaySystem;

import com.sk89q.worldedit.bukkit.adapter.BukkitImplAdapter;
import jp.furaito.baito.wallkickPlaySystem.command.WallkickPlaySettingCommand;
import jp.furaito.baito.wallkickPlaySystem.event.PlayerJoinStageEvent;
import jp.furaito.baito.wallkickPlaySystem.gui.GUIManager;
import jp.furaito.baito.wallkickPlaySystem.listeners.*;
import jp.furaito.baito.wallkickPlaySystem.serialize.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public final class WallkickPlaySystem extends JavaPlugin {

    //スポーンポイントの座標取得
    private static Location spawnPointA, spawnPointB;
    private static Location lobbyAreaStart, lobbyAreaEnd;


    public static Plugin getPlugin() {
        return getProvidingPlugin(WallkickPlaySystem.class);
    }

    public static Location getSpawnPointA() {
        return spawnPointA;

    }

    public static void setSpawnPointA(Location spawnPoint) {
        spawnPointA = spawnPoint;

    }

    public static Location getSpawnPointB() {
        return spawnPointB;

    }

    public static void setSpawnPointB(Location spawnPoint) {
        spawnPointB = spawnPoint;

    }

    public static Location getLobbyAreaStart() {
        return lobbyAreaStart;

    }

    public static void setLobbyAreaStart(Location spawnPoint) {
        lobbyAreaStart = spawnPoint;

    }

    public static Location getLobbyAreaEnd() {
        return lobbyAreaEnd;

    }

    public static void setLobbyAreaEnd(Location spawnPoint) {
        lobbyAreaEnd = spawnPoint;

    }

    @Override
    public void onEnable() {
        //プラグイン開始処理
        Bukkit.getPluginManager().registerEvents(new DeathCheckListener(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyAreaListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpectatorListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArrowHitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PointGetListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinStageListener(), this);

        TabExecutor executor = new WallkickPlaySettingCommand();
        getCommand("wallkickplaysystem").setExecutor(executor);
        getCommand("wallkickplaysystem").setTabCompleter(executor);

        for (Player player:Bukkit.getOnlinePlayers()){
            Event event = new PlayerJoinStageEvent(player);
            Bukkit.getPluginManager().callEvent(event);
        }


        // GUIの初期化
        GUIManager.init(this);
    }

    @Override
    public void onDisable() {

        //プラグイン終了処理
        StatsManager.gamePlayers();


        // GUIページキャッシュ削除
        GUIManager.clearHistoryAll();


    }

}


//Todo プレイヤーのリスポーンポイント設定のひな形作成
//Todo 別マップや新規設定など、拡張性を持たせて実装する
//Todo プラグインの動作範囲指定、ワールド・ロケーション