package jp.furaito.baito.wallkickPlaySystem.listeners;

import jp.furaito.baito.wallkickPlaySystem.GameManager;
import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem.getPlugin;

public class DeathCheckListener implements Listener {
    @EventHandler
    //プレイヤーの死亡判定を回避しつつ体力が0になった際に処理を入れる
    public static void listenDeath(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player player)) return;

        //プレイヤーの体力と最終被ダメージの取得
        double nowHP = player.getHealth();
        double damage = event.getFinalDamage();

        if (nowHP - damage <= 0) {
            event.setCancelled(true);

            //キルカウント
            List<UUID> gamePlayers = GameManager.getGamePlayers();
            String uuid = gamePlayers.toString();
            File file = new File(getPlugin().getDataFolder().getAbsoluteFile(), uuid + ".yml");
            int killCnt = YamlConfiguration.loadConfiguration(file).getInt("kill");
            killCnt++;
            YamlConfiguration.loadConfiguration(file).set("kill", killCnt);


            //プレイヤーの体力をリセット・スペクテイター化
            player.setHealth(player.getMaxHealth());
            player.setGameMode(GameMode.SPECTATOR);

            //Todo プレイヤーの武器をクリア


            //パーティクル生成用ロケーション指定
            Location location = player.getLocation();
            World world = location.getWorld();

            //DUSTパーティクル生成
            Particle.DustOptions colorOption = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1.0f);
            world.spawnParticle(Particle.DUST, location, 60, 0.3, 1.0, 0.3, 0.0, colorOption);


            //20tick(1秒)*3秒後に接触判定と透明化を戻す
            Plugin plugin = WallkickPlaySystem.getPlugin();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.setGameMode(GameMode.SURVIVAL);
                int value = YamlConfiguration.loadConfiguration(file).getInt("kill");
                player.sendMessage(value + "回倒した！");
            }, 20 * 3);
        }

    }

}
