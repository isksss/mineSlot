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
            Reel reel = new Reel(inv);
            if(clickedSlot == Config.SLOT_START_BUTTON){
                if(!UserTags.contains(Config.SLOT_PLAYING)) {
                    reel.StartReel();
                    p.addScoreboardTag(Config.SLOT_PLAYING);
                }
            }else if(clickedSlot ==48 || clickedSlot == 49 || clickedSlot == 50){
                //スロット停止処理
                if(UserTags.contains(Config.SLOT_PLAYING)){
                    // slot number 追加
                    // 0はサンプル
                    // range: 0 - 2
                    int line = clickedSlot - 48;
                    reel.StopTask(p.getName(), line);

                    if(reel.getSlotTaskByPlayerName(p.getName()).getDone()){
                        p.sendMessage("end slot");
                        reel.removeTask(p.getName());
                        UserTags.remove(Config.SLOT_PLAYING);
                    }

                }
            }
        }
    }
}
