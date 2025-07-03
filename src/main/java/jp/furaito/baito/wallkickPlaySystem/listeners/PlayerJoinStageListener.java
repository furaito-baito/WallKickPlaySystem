package jp.furaito.baito.wallkickPlaySystem.listeners;

import jp.furaito.baito.wallkickPlaySystem.GameManager;
import jp.furaito.baito.wallkickPlaySystem.event.PlayerJoinStageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerJoinStageListener implements Listener {
    @EventHandler
    public void playerJoinStage(PlayerJoinStageEvent event) {
        UUID uuid = event.getJoinPlayer().getUniqueId();
        GameManager.addGamePlayer(uuid);
    }
}
