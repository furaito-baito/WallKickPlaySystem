package jp.furaito.baito.wallkickPlaySystem.serialize;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem;
import jp.furaito.baito.wallkickPlaySystem.command.CommandUtil;
import jp.furaito.baito.wallkickPlaySystem.command.WallkickPlaySettingCommand;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WallkickStageSave {
    public static void stageSave(String stageName, Player player) {
        BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(wePlayer);

        try {
            Region region = session.getSelection(wePlayer.getWorld());


            BlockVector3 min = region.getMinimumPoint();
            BlockVector3 max = region.getMaximumPoint();
            CuboidRegion cuboidRegion = new CuboidRegion(min, max);
            BlockArrayClipboard clipboard = new BlockArrayClipboard(cuboidRegion);

            File pluginFolder = WallkickPlaySystem.getPlugin().getDataFolder();
            if (!pluginFolder.exists()) {
                pluginFolder.mkdirs();
            }

            File file = new File(WallkickPlaySystem.getPlugin().getDataFolder(), stageName);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_V3_SCHEMATIC.getWriter(new FileOutputStream(file))) {
                writer.write(clipboard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CommandUtil.systemMessage(player, "オブジェクトを保存しました。");
        } catch (IncompleteRegionException e) {
            CommandUtil.systemMessage(player,"始点と終点の2箇所を選択してください。");
        }
    }

}
