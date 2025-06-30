package jp.furaito.baito.wallkickPlaySystem.serialize;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WallkickStage {
    private final UUID stageUUID;
    private final List<Location> spawnPoint;
    private String stageName;
    private int minPlayer;
    private int maxPlayer;
    private boolean isRunning;


    public WallkickStage(UUID stageUUID, String stageName, int minPlayer, int maxPlayer) {
        this.stageUUID = stageUUID;
        this.stageName = stageName;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.spawnPoint = new ArrayList<>();
        this.isRunning = false;
    }

    public WallkickStage(UUID stageUUID, String stageName) {
        this(stageUUID, stageName, 2, 2);
    }

    public UUID getStageUUID() {
        return stageUUID;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public boolean addSpawnPoint(Location location) {
        return this.spawnPoint.add(location);
    }

    public boolean removeSpawnPoint(Location location) {
        return this.spawnPoint.remove(location);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
