package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
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
            p.sendMessage("END: SLOT");
        }
    }
}
