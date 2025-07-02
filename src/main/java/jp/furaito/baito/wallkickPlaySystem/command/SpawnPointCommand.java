package jp.furaito.baito.wallkickPlaySystem.command;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpawnPointCommand implements CommandExecutor {

    public static final String SUB_COMMAND = "spawnpoint";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase(SUB_COMMAND)) {
            //スポーンポイント設定
            if (sender instanceof Player player) {
                //プレイヤースポーンポイント設定用アイテムのメタデータ
                ItemStack spawnPointerA = new ItemStack(Material.RED_WOOL, 1);
                ItemStack spawnPointerB = new ItemStack(Material.LIME_WOOL, 1);

                ItemMeta metaA = spawnPointerA.getItemMeta();
                ItemMeta metaB = spawnPointerB.getItemMeta();

                metaA.setDisplayName("スポーンポイント" + ChatColor.RED + "<A>" + ChatColor.RESET + "を設定");
                metaB.setDisplayName("スポーンポイント" + ChatColor.GREEN + "<B>" + ChatColor.RESET + "を設定");

                metaA.setLore(List.of(ChatColor.RED + "設定した防具立てを右クリック"));
                metaB.setLore(List.of(ChatColor.RED + "設定した防具立てを右クリック"));

                //上記アイテムの識別子作り
                Plugin plugin = WallkickPlaySystem.getPlugin();
                NamespacedKey keyA = new NamespacedKey(plugin, "pointer-a");
                NamespacedKey keyB = new NamespacedKey(plugin, "pointer-b");

                metaA.getPersistentDataContainer().set(keyA, PersistentDataType.STRING, "nandemoii-a");
                metaB.getPersistentDataContainer().set(keyB, PersistentDataType.STRING, "nandemoii-b");

                //メタデータをアイテムへ反映
                spawnPointerA.setItemMeta(metaA);
                spawnPointerB.setItemMeta(metaB);

                //プレイヤーへ付与
                player.getInventory().addItem(spawnPointerA, spawnPointerB);
                CommandUtil.systemMessage(sender, "設定したいポイントに防具立てを設置後、専用アイテムで確定してください。");
                CommandUtil.systemMessage(sender, "上限は2箇所です。防具立ての向きと座標を記録します。");
            }
        }
        return true;
    }
}
