package net.isksss.mc.mineslot.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GuiMenu {
    public Inventory createSlotMenu(){
        int row = 3;
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST);
        // ここでGUIメニューを設定する
        return inv;
    }
}
