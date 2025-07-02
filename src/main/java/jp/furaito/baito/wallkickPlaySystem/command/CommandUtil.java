package jp.furaito.baito.wallkickPlaySystem.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandUtil {
    /**
     * システムメッセージを送信する
     *
     * @param target  送信相手
     * @param message メッセージの内容
     */
    public static void systemMessage(CommandSender target, String message) {
        target.sendMessage("[" + ChatColor.GREEN + "WallKick" + ChatColor.RESET + "]:" + message);
    }

    /**
     * システムメッセージを送信する
     *
     * @param target   送信相手
     * @param messages メッセージの内容
     */
    public static void systemMessage(CommandSender target, String... messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = "[" + ChatColor.GREEN + "WallKick" + ChatColor.RESET + "]:" + messages[i];
        }
        target.sendMessage(messages);
    }
}
