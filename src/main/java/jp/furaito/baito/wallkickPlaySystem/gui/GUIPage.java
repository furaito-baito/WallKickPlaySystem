package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class GUIPage implements InventoryHolder {

    /**
     * プレイヤーのUUID
     */
    protected final UUID playerId;

    /**
     * 表示しているインベントリ
     */
    protected Inventory inventory;

    /**
     * コンストラクタ
     *
     * @param player 表示するプレイヤー
     */
    public GUIPage(Player player) {
        this.playerId = player.getUniqueId();
    }

    /**
     * 表示に使用するインベントリを作成する
     *
     * @return インベントリ
     */
    public abstract Inventory createInventory();

    /**
     * 中身を表示する
     */
    public abstract void renderContents();

    /**
     * 開いたときにする処理
     */
    public void onOpen() {
    }

    /**
     * 閉じるときにする処理
     */
    public void onClose() {
    }

    /**
     * ページ内でクリックされたときの処理
     *
     * @param event イベント情報
     */
    public abstract void onClick(InventoryClickEvent event);

    /**
     * ページを開く
     */
    public void open() {
        this.inventory = createInventory();
        renderContents();
        getPlayer().openInventory(inventory);
        onOpen();
    }

    /**
     * 表示しているインベントリを返す
     *
     * @return 表示中のインベントリ 何も表示されていない場合nullが返る
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * 表示する or している プレイヤーを返す
     *
     * @return プレイヤー
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }
}
