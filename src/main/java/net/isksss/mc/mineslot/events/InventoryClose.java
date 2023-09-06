package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.gui.Reel;
import net.isksss.mc.mineslot.model.SlotTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Set;

public class InventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player p = (Player) event.getPlayer();
        Set<String> UserTags = p.getScoreboardTags();
        if(UserTags.contains(Config.CHEST_OPEN_TAG)){
            p.removeScoreboardTag(Config.CHEST_OPEN_TAG);
            p.removeScoreboardTag(Config.SLOT_PLAYING);
            Reel reel = new Reel(event.getInventory());
            SlotTask task = reel.getSlotTaskByPlayerName(p.getName());
            if (task != null){
                reel.removeTask(p.getName());
            }
            p.sendMessage("END: SLOT");
        }
    }
}
