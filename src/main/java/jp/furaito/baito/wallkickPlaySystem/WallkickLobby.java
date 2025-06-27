package jp.furaito.baito.wallkickPlaySystem;

import org.bukkit.util.BoundingBox;

public class WallkickLobby {
    private BoundingBox area;

    public BoundingBox getArea() {
        return area;
    }

    public void setArea(BoundingBox area) {
        this.area = area;
    }

    public WallkickLobby(BoundingBox area) {
        this.area = area;
    }
}
