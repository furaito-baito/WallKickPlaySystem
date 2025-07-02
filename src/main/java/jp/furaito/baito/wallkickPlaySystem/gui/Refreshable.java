package jp.furaito.baito.wallkickPlaySystem.gui;

/**
 * 更新可能なページ
 */
public interface Refreshable {
    /**
     * ページを更新する
     */
    void refresh();

    /**
     * ページを自動で更新する周期
     *
     * @return tick
     */
    long refreshIntervalTicks();
}
