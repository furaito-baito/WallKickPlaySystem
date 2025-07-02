package jp.furaito.baito.wallkickPlaySystem.command;

import jp.furaito.baito.wallkickPlaySystem.gui.GUIManager;
import jp.furaito.baito.wallkickPlaySystem.gui.StageManagerGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements CommandExecutor {

    public static final String SUB_COMMAND = "help";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase(SUB_COMMAND)) {
            // ヘルプメッセージ
            List<String> helpMessages = new ArrayList<>(List.of(
                    ChatColor.GRAY + "/wallkickplaysystem or /wkps メインコマンド",
                    ChatColor.GRAY + "/wkps help ヘルプの表示",
                    ChatColor.GRAY + "/wkps stats <minecraftID>プレイヤーの戦績を表示",
                    ChatColor.GRAY + "/wkps skin 装備品の見た目を変更"
            ));
            if (sender.isOp()) helpMessages.add(ChatColor.GRAY + "/wkps stage ステージ管理GUIの表示");

            // 送信
            CommandUtil.systemMessage(sender, helpMessages.toArray(String[]::new));
        }
        return true;
    }
}
