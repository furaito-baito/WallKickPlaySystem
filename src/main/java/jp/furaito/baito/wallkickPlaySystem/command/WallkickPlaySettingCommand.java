package jp.furaito.baito.wallkickPlaySystem.command;

import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import jp.furaito.baito.wallkickPlaySystem.serialize.WallkickStageSave;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class WallkickPlaySettingCommand implements TabExecutor {

    public static void systemMessage(CommandSender sender, String message) {
        sender.sendMessage("[" + ChatColor.GREEN + "WallKick" + ChatColor.RESET + "]:" + message);

    }


    //リスポーンポイント設定の雛型
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                systemMessage(sender, "サブコマンド(引数)を入力してください");
                return true;
            }
            //サブコマンド
            switch (args[0].toLowerCase()) {
                //ヘルプの表示
                case "help" -> {
                    systemMessage(sender, ChatColor.GRAY + "/wallkickplaysystem or /wkps メインコマンド");
                    systemMessage(sender, ChatColor.GRAY + "/wkps help ヘルプの表示");
                    if (player.isOp()) systemMessage(sender, ChatColor.GRAY + "/wkps stage ステージ管理GUIの表示");
                    systemMessage(sender, ChatColor.GRAY + "/wkps stats <minecraftID>プレイヤーの戦績を表示");
                    systemMessage(sender, ChatColor.GRAY + "/wkps skin 装備品の見た目を変更");

                }

                //スポーンポイント設定
                case "spawnpoint" -> {
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
                    systemMessage(sender, "設定したいポイントに防具立てを設置後、専用アイテムで確定してください。");
                    systemMessage(sender, "上限は2箇所です。防具立ての向きと座標を記録します。");
                }
                case "stage" -> {
                    if (args.length >= 2) {
                        WallkickStageSave.stageSave(args[1], player);
                    } else {
                        systemMessage(sender, "ステージ名を入力してください。");
                    }

                }
                default ->
                        systemMessage(sender, ChatColor.DARK_RED + "不明なサブコマンドです。/wkps help を確認してください。");
            }
            return true;
        }

        return true;

    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            List<String> subcommands = List.of("help", "spawnpoint", "stage");
            return subcommands.stream()
                    .filter(st -> st.startsWith(strings[0].toLowerCase()))
                    .toList();
        }
        return Collections.emptyList();
    }
}

//Todo インベントリUIでスポーンポイント管理
//Todo スポーンポイントは個別、ランダム、チーム