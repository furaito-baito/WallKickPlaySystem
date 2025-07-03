package jp.furaito.baito.wallkickPlaySystem.serialize;

import jp.furaito.baito.wallkickPlaySystem.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static jp.furaito.baito.wallkickPlaySystem.WallkickPlaySystem.getPlugin;

//プレイヤーごとの.yml作成
public class StatsManager {
    public static void gamePlayers() {

        List<UUID> gamePlayers = GameManager.getGamePlayers();


        for (int i = 0; i < gamePlayers.size(); i++) {
            String uuid = gamePlayers.get(i).toString();
            try {
                // yml読み込み
                File file = new File(getPlugin().getDataFolder(), uuid + ".yml");
                if (!file.exists()) {
                    file.createNewFile();
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    int killCount = 0;
                    config.set("kill", killCount);

                    int deathCount = 0;
                    config.set("death", deathCount);

                    int winCount = 0;
                    config.set("win", winCount);

                    int loseCount = 0;
                    config.set("lose", loseCount);

                    int playStandardCount = 0;
                    config.set("playStandard", playStandardCount);

                    int playUniqueCount = 0;
                    config.set("playUnique", playUniqueCount);
                    config.save(file);
                }
                //保存
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
