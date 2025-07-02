package jp.furaito.baito.wallkickPlaySystem.listeners;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import jp.furaito.baito.wallkickPlaySystem.command.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class PointGetListener implements Listener {

    @EventHandler
    public void listenClick(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof ArmorStand)) return;

        Player player = event.getPlayer();
        ItemStack inMainHand = player.getInventory().getItemInMainHand();

        //手に持っているものがなければreturn
        if (inMainHand.getType().isAir()) return;

        //キーとなるアイテム
        Plugin plugin = WallkickPlaySystem.getPlugin();
        NamespacedKey keyA = new NamespacedKey(plugin, "pointer-a");
        NamespacedKey keyB = new NamespacedKey(plugin, "pointer-b");

        PersistentDataContainer dataContainer = inMainHand.getItemMeta().getPersistentDataContainer();
        if (!(dataContainer.has(keyA) || dataContainer.has(keyB))) return;

        //アイテムを持った状態でのクリック後の処理

        if (dataContainer.has(keyA)) {
            CommandUtil.systemMessage(player, "スポーンポイント" + ChatColor.RED + "<A>" + ChatColor.RESET + "を設定しました。");
        } else if (dataContainer.has(keyB)) {
            CommandUtil.systemMessage(player, "スポーンポイント" + ChatColor.GREEN + "<B>" + ChatColor.RESET + "を設定しました。");
        }

        //確定後処理
        player.playSound(player, Sound.ENTITY_ALLAY_ITEM_THROWN, 100, 2.0f);
        player.getInventory().removeItem(inMainHand);

    }
}

//設定済みのスポーンポイントはインタラクト対象を削除するため再クリック回避の文章は非記述

//Todo アマスタの座標と向きの保存(シアライズ化)