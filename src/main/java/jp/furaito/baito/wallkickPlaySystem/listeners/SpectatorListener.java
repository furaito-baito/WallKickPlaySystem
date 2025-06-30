package jp.furaito.baito.wallkickPlaySystem.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SpectatorListener implements Listener {
    @EventHandler
    public void listenLogout(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) player.setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void listenTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) {
            if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) event.setCancelled(true);
        }
    }

}

//Todo ステージ範囲内から観戦者が出られないようにする文章をここに書く
