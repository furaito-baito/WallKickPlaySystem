package jp.furaito.baito.wallkickPlaySystem.gui;

public class MultiPageGUI extends InventoryGUI {

    private final int page;

    public MultiPageGUI(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
