package jp.furaito.baito.wallkickPlaySystem.command;

import jp.furaito.baito.wallkickPlaySystem.gui.GUIManager;
import jp.furaito.baito.wallkickPlaySystem.gui.StageManagerGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WallKickStageCommand implements CommandExecutor {

    /**
     * サブコマンドのラベル
     */
    public static final String SUB_COMMAND = "stage";

    /**
     * ステージ管理のGUIを表示する
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return 正常に実行したか
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase(SUB_COMMAND)) {
            if (sender instanceof Player player) {
                //                    if (args.length >= 2) {
//                        WallkickStageSave.stageSave(args[1], player);
//                    } else {
//                        systemMessage(sender, "ステージ名を入力してください。");
//                    }

                GUIManager.goTo(new StageManagerGUI(player, 0));
            }
        }
        return true;
    }
}
