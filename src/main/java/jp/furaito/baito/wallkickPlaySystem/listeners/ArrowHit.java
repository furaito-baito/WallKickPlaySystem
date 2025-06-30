package jp.furaito.baito.wallkickPlaySystem.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ArrowHit implements Listener {
    @EventHandler
    public void listenArrow(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Player damager)) return;
        TextComponent message1 = new TextComponent();
        TextComponent message2 = new TextComponent();
        message1.setText(ChatColor.GREEN + "1Hit!");
        message2.setText(ChatColor.RED + "HIT ME!!!");
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message1);
        damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, message2);
    }
}
