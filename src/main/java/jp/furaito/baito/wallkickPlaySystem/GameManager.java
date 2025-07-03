package jp.furaito.baito.wallkickPlaySystem;

import jp.furaito.baito.wallkickPlaySystem.serialize.StageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameManager {
    private static final List<UUID> gamePlayers = new ArrayList<>();
    private static Map<String, StageManager> stageMap;

    private GameManager() {

    }

    public static List<UUID> getGamePlayers() {
        //gamePlayersリストのコピーをreturn
        return new ArrayList<>(gamePlayers);
    }

    public static void addGamePlayer(UUID player) {
        gamePlayers.add(player);
    }

}

//Todo プレイヤー処理は軽めの方向で考える