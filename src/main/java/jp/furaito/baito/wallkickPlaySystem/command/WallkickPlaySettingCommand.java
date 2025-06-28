package jp.furaito.baito.wallkickPlaySystem.command;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class WallkickPlaySettingCommand implements TabExecutor {

    public static void systemMessage(CommandSender sender,String message){
        sender.sendMessage("[" + ChatColor.GREEN + "WallKick" +ChatColor.RESET + "]:" + message);

    }


    //リスポーンポイント設定の雛型
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length==0){
                systemMessage(sender,"サブコマンド(引数)を入力してください");
                return true;
            }
            //サブコマンド
            switch (args[0].toLowerCase()) {
                //ヘルプの表示
                case "help" -> {
                    systemMessage(sender,"ヘルプを表示しました。");
                    //stage,rename,confirm
                }

                //スポーンポイント設定
                case "spawnpoint" -> {
                    //プレイヤースポーンポイント設定用アイテムのメタデータ
                    ItemStack spawnPointerA = new ItemStack(Material.ARMOR_STAND, 1);
                    ItemStack spawnPointerB = new ItemStack(Material.ARMOR_STAND, 1);

                    ItemMeta metaA = spawnPointerA.getItemMeta();
                    ItemMeta metaB = spawnPointerB.getItemMeta();

                    metaA.setDisplayName("プレイヤー" + ChatColor.YELLOW + "<A>" + ChatColor.RESET + "のスポーンポイントを設定");
                    metaB.setDisplayName("プレイヤー" + ChatColor.AQUA + "<B>" + ChatColor.RESET + "のスポーンポイントを設定");

                    metaA.setLore(List.of(ChatColor.RED + "設定したい場所を右クリック"));
                    metaB.setLore(List.of(ChatColor.RED + "設定したい場所を右クリック"));

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
                    systemMessage(sender,"設定したいスポーンポイントのブロックをクリックしてください。");
                }
                case "lobby" -> {

                    systemMessage(sender,"左クリックで始点、右クリックで終点を設定します。範囲は立体です。");

                }
                default -> systemMessage(sender,ChatColor.DARK_RED  +"不明なサブコマンドです。/wkps help を確認してください。");
            }
            return true;
        }

        return true;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            List<String> subcommands = List.of("help", "spawnpoint", "lobby");
            return subcommands.stream()
                    .filter(st -> st.startsWith(strings[0].toLowerCase()))
                    .toList();
        }
        return Collections.emptyList();
    }
}

//Todo インベントリUIでスポーンポイント管理
//Todo スポーンポイントは個別、ランダム、チーム