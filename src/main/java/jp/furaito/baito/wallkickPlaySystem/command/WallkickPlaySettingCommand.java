package jp.furaito.baito.wallkickPlaySystem.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WallkickPlaySettingCommand implements TabExecutor {

    private final Map<String, CommandExecutor> subcommandMap = new HashMap<>();

    public WallkickPlaySettingCommand() {
        subcommandMap.put(WallKickStageCommand.SUB_COMMAND, new WallKickStageCommand());
        subcommandMap.put(HelpCommand.SUB_COMMAND, new HelpCommand());
        subcommandMap.put(SpawnPointCommand.SUB_COMMAND, new SpawnPointCommand());
    }

    //リスポーンポイント設定の雛型
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                CommandUtil.systemMessage(sender, "サブコマンド(引数)を入力してください");
                return true;
            }

            if (subcommandMap.containsKey(args[0].toLowerCase())) {
                return subcommandMap.get(args[0].toLowerCase()).onCommand(sender, command, label, args);
            } else {
                CommandUtil.systemMessage(sender, ChatColor.DARK_RED + "不明なサブコマンドです。/wkps help を確認してください。");
                return true;
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return subcommandMap.keySet().stream()
                    .filter(st -> st.startsWith(strings[0].toLowerCase()))
                    .toList();
        }
        return Collections.emptyList();
    }
}

//Todo インベントリUIでスポーンポイント管理
//Todo スポーンポイントは個別、ランダム、チーム