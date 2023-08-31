package net.isksss.mc.mineslot;

import net.isksss.mc.mineslot.events.CanselClick;
import net.isksss.mc.mineslot.events.InventoryClose;
import net.isksss.mc.mineslot.events.OpenChest;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineSlot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new OpenChest(),this );
        getServer().getPluginManager().registerEvents(new InventoryClose(),this );
        getServer().getPluginManager().registerEvents(new CanselClick(),this );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
