package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.gui.Reel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class CanselClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        Set<String> UserTags = p.getScoreboardTags();

        if(UserTags.contains(Config.CHEST_OPEN_TAG)){
            event.setCancelled(true);
            // クリックされたスロットが指定した場所なら処理を行う。
            int clickedSlot = event.getSlot();
            Inventory inv;
            inv = event.getInventory();
            //スロット開始の場合
            if(clickedSlot == Config.SLOT_START_BUTTON){
                Reel reel = new Reel(inv);
                //スロット停止処理
                if(UserTags.contains(Config.SLOT_PLAYING)){
                    p.removeScoreboardTag(Config.SLOT_PLAYING);
                    reel.StopTask(p.getName());
                    return;
                }
                reel.StartReel();
                p.addScoreboardTag(Config.SLOT_PLAYING);
            }
        }
    }
}
