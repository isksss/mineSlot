package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Set;

public class InventoryClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Set<String> UserTags = event.getPlayer().getScoreboardTags();
        if(UserTags.contains(Config.CHEST_OPEN_TAG)){
            event.getPlayer().removeScoreboardTag(Config.CHEST_OPEN_TAG);
        }
    }
}
