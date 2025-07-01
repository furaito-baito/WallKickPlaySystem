package jp.furaito.baito.wallkickPlaySystem.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StageManagerGUI extends MultiPageGUI {

    public StageManagerGUI(int page) {
        super(page);
    }

    @Override
    public Inventory createGUI() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "ステージ管理画面");

        //TODO ステージ取得 + ブロック化

        // 境界線のアイテム作成
        ItemStack borderline = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = Objects.requireNonNull(borderline.getItemMeta());
        borderMeta.setDisplayName("");
        borderMeta.setLore(List.of(""));
        borderMeta.setHideTooltip(true);
        borderline.setItemMeta(borderMeta);

        // 境界線を引く
        for (int i = 0; i < 9; i++) {
            inventory.setItem(9 * 4 + i, borderline.clone());
        }

        //TODO ステージの数が36を超えたらページボタンを表示する
        int stageCount = 6; // 仮

        if (stageCount < 36) {
            // ページ移動のアイテム作成
            ItemStack backward = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) backward.getItemMeta();

            // プロファイルを生成
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();
            String skinURL = "http://textures.minecraft.net/texture/8e403cc7bbac73670bd543f6b0955bae7b8e9123d83bd760f6204c5afd8be7e1";
            URL url;
            try {
                url = URI.create(skinURL).toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            textures.setSkin(url);
            profile.setTextures(textures);
            Objects.requireNonNull(meta).setOwnerProfile(profile);
            backward.setItemMeta(meta);
        }

        // ステージ追加ボタン
        ItemStack addStage = new ItemStack(Material.LIME_WOOL);
        ItemMeta addStageMeta = Objects.requireNonNull(addStage.getItemMeta());
        addStageMeta.setDisplayName("ステージを追加する");
        addStageMeta.setLore(List.of(""));
        addStageMeta.setHideTooltip(true);
        addStage.setItemMeta(addStageMeta);

        inventory.setItem(49, addStage);

        // ヘルプボタン
        ItemStack help = new ItemStack(Material.BOOK);
        ItemMeta helpMeta = Objects.requireNonNull(help.getItemMeta());
        helpMeta.setDisplayName("ヘルプを表示");
        helpMeta.setLore(List.of(""));
        helpMeta.setHideTooltip(true);
        help.setItemMeta(helpMeta);

        inventory.setItem(53, help);


        return inventory;
    }

    @Override
    public Listener getGUIListener() {
        return new Listener() {
            @EventHandler
            public void listenGUI(GUIEvent event) {

            }
        };
    }
}
