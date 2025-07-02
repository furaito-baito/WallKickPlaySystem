package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.entity.Player;

/**
 * 複数ページを持つGUI
 */
public abstract class MultiPageGUI extends GUIPage {

    /**
     * ページ番号
     */
    private final int pageNumber;

    /**
     * コンストラクタ
     * @param player プレイヤー
     * @param pageNumber ページ番号
     */
    public MultiPageGUI(Player player, int pageNumber) {
        super(player);
        this.pageNumber = pageNumber;
    }

    /**
     * ページ番号を返す
     * @return ページ番号
     */
    public int getPageNumber() {
        return pageNumber;
    }
}
