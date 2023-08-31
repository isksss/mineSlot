package net.isksss.mc.mineslot.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GuiMenu {
    public Inventory createSlotMenu(){
        Inventory inv = Bukkit.getServer().createInventory(null, 27, "Menu");
        inv.clear();
        // ここでGUIメニューを設定する
        return inv;
    }
}
