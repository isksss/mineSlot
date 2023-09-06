package net.isksss.mc.mineslot.gui;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.model.Chest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiMenu {
    private final ItemStack frame;
    private final Chest chest;

    public GuiMenu(Chest chest){
        this.chest = chest;
        this.frame = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    }
    public Inventory createSlotMenu(){
        int row = 6;
        String chestTitle = "SLOT: "+this.chest.getRequiredLevel();
        Inventory inv = Bukkit.createInventory(null, 9 * row, chestTitle);
        // ここでGUIメニューを設定する
//        inv.addItem(new ItemStack(Material.CHEST));
        //  0  1  2  3  4  5  6  7  8
        //  9 10 11 12 13 14 15 16 17
        // 18 19 20 21 22 23 24 25 26
        // 27 28 29 30 31 32 33 34 35
        // 36 37 38 39 40 41 42 43 44
        // 45 46 47 48 49 50 51 52 53

        // スロットのフレーム(枠)を作成。
        this.createFrame(inv);

        // startボタンの設置
        this.setStartButton(inv);

        // stop biuttohn
//        this.setStopButton(inv);
        // 掛け金の設定
        createBetBottle(inv,chest);

        //リール配置
        Reel reel = new Reel(inv);
        reel.SetReel();

        return inv;
    }

    private Inventory createFrame(Inventory inv){
        for (int x=2;x<7;x++){
            for(int y=0;y<5;y++){
                int index = x + (9*y);
                // slot部分を空白にする
                if (    index == 12 || index == 13 || index == 14 ||
                        index == 21 || index == 22 || index == 23 ||
                        index == 30 || index == 31 || index == 32) {
                    continue;
                }
                inv.setItem(index,this.frame);
            }
        }
        return inv;
    }

    private void setStartButton(Inventory inv){
        //スロット開始ボタンの設置
        ItemStack button = new ItemStack(Material.ENDER_EYE);
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName("START!");
        button.setItemMeta(meta);

        inv.setItem(Config.SLOT_START_BUTTON,button);
    }

    private void createBetBottle(Inventory inv, Chest chest){
        //掛け金スロット
        int index = 0;
        ItemStack bottole = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = bottole.getItemMeta();
        meta.setDisplayName(String.valueOf(chest.getRequiredLevel()));
        bottole.setItemMeta(meta);

        inv.setItem(index,bottole);
    }

}
