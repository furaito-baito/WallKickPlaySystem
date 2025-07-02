package jp.furaito.baito.wallkickPlaySystem;

import jp.furaito.baito.wallkickPlaySystem.command.WallkickPlaySettingCommand;
import jp.furaito.baito.wallkickPlaySystem.gui.GUIManager;
import jp.furaito.baito.wallkickPlaySystem.listeners.*;
import jp.furaito.baito.wallkickPlaySystem.serialize.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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

    //
    private PlayerStats playerStats = null;

    @Override
    public void onEnable() {
        //プラグイン開始処理
        Bukkit.getPluginManager().registerEvents(new DeathCheckListener(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyAreaListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpectatorListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArrowHitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PointGetListener(), this);
        TabExecutor executor = new WallkickPlaySettingCommand();
        getCommand("wallkickplaysystem").setExecutor(executor);
        getCommand("wallkickplaysystem").setTabCompleter(executor);

        //プレイヤーの戦績系
        // PlayerStatsオブジェクトをシリアライズ・デシリアライズするのに使うクラスをBukkitに登録する
        ConfigurationSerialization.registerClass(PlayerStats.class);

        // 設定されている値を取得してデシリアライズする
        playerStats=(PlayerStats) getConfig().get("subjugation");
        if(playerStats == null){
            // データを読み取れなかった場合はオブジェクトを新規作成
            playerStats=new PlayerStats();
        }

        // GUIの初期化
        GUIManager.init(this);
    }

    @Override
    public void onDisable() {
        //プラグイン終了処理
        // GUIページキャッシュ削除
        GUIManager.clearHistoryAll();
    }

}


//Todo プレイヤーのリスポーンポイント設定のひな形作成
//Todo 別マップや新規設定など、拡張性を持たせて実装する
//Todo プラグインの動作範囲指定、ワールド・ロケーション