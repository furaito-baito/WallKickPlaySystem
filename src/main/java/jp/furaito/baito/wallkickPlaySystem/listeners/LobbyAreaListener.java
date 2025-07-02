package jp.furaito.baito.wallkickPlaySystem.listeners;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import jp.furaito.baito.wallkickPlaySystem.command.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class LobbyAreaListener implements Listener {
    @EventHandler
    public void listenerLobbyArea(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hothand = event.getItem();
        if (hothand == null) return;
        if (!hothand.getType().equals(Material.WOODEN_SHOVEL)) return;

        Action click = event.getAction();

        Plugin plugin = WallkickPlaySystem.getPlugin();
        NamespacedKey key = new NamespacedKey(plugin, "lobby");

        PersistentDataContainer dataContainer = hothand.getItemMeta().getPersistentDataContainer();
        if (!(dataContainer.has(key))) return;

        event.setCancelled(true);

        Block clickBlock = event.getClickedBlock();
        Location location = clickBlock.getLocation();

        //始点処理
        if (click.equals(Action.LEFT_CLICK_BLOCK)) {
            WallkickPlaySystem.setLobbyAreaStart(location);
            CommandUtil.systemMessage(player, ChatColor.RED + "始点" + ChatColor.RESET + "を設定しました");
        }

        //終点処理
        else if (click.equals(Action.RIGHT_CLICK_BLOCK)) {
            WallkickPlaySystem.setLobbyAreaEnd(location);
            CommandUtil.systemMessage(player, ChatColor.AQUA + "終点" + ChatColor.RESET + "を設定しました");
        }
    }

}

//Todo スタートエンドを結ぶ範囲の取得と保存
//Todo 座標指定のシステムメッセージ内に座標を代入
//Todo 上記が終わればやっとプレイヤーの識別設定ができる