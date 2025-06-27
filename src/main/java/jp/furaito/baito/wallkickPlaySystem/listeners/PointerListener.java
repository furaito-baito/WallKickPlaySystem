package jp.furaito.baito.wallkickPlaySystem.listeners;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class PointerListener implements Listener {
    @EventHandler
    public void listenPointer(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hothand = event.getItem();
        if (hothand == null) return;
        if (!hothand.getType().equals(Material.ARMOR_STAND)) return;

        Action click = event.getAction();
        if (!click.equals(Action.RIGHT_CLICK_BLOCK)) return;

        Plugin plugin = WallkickPlaySystem.getPlugin();
        NamespacedKey keyA = new NamespacedKey(plugin, "pointer-a");
        NamespacedKey keyB = new NamespacedKey(plugin, "pointer-b");

        PersistentDataContainer dataContainer = hothand.getItemMeta().getPersistentDataContainer();
        if (!(dataContainer.has(keyA) || dataContainer.has(keyB))) return;

        event.setCancelled(true);


        Block clickBlock = event.getClickedBlock();
        Location centerLocation = clickBlock.getLocation().add(0.5, clickBlock.getBoundingBox().getHeight(), 0.5);
        if (!clickBlock.getRelative(BlockFace.UP).isEmpty()) {
            player.sendMessage(ChatColor.RED + "スポーンポイントを設定できませんでした");
            return;
        }


        //パーティクル生成用ロケーション指定
        World world = centerLocation.getWorld();

        if (dataContainer.has(keyA)) {

            WallkickPlaySystem.setSpawnPointA(centerLocation);

            world.spawnParticle(Particle.HAPPY_VILLAGER, centerLocation, 60, 0.0, 0.0, 0.0, 0.0);
            player.sendMessage(ChatColor.GREEN + "プレイヤー" + ChatColor.YELLOW + "<A>" + ChatColor.GREEN + "のスポーンポイントが設定されました!");
        } else if (dataContainer.has(keyB)) {

            WallkickPlaySystem.setSpawnPointB(centerLocation);

            world.spawnParticle(Particle.FLAME, centerLocation, 60, 0.0, 0.0, 0.0, 0.0);
            player.sendMessage(ChatColor.GREEN + "プレイヤー" + ChatColor.AQUA + "<B>" + ChatColor.GREEN + "のスポーンポイントが設定されました!");
        }

        player.getInventory().removeItem(hothand);
    }
}

//Todo スポーンしたプレイヤーのアングル設定
//Todo テレポート設定,DeathCheckListenerからプレイヤーの状態を読み取って動作
//Todo 上記の記述に伴ってプレイヤーに識別子を付ける