package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;

public class CanselClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Set<String> UserTags = event.getWhoClicked().getScoreboardTags();
        if(UserTags.contains(Config.CHEST_OPEN_TAG)){
            event.getWhoClicked().sendMessage("cansel");
            event.setCancelled(true);
        }
    }
}
