package net.isksss.mc.mineslot;

import net.isksss.mc.mineslot.commands.AddChest;
import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.db.DatabaseManager;
import net.isksss.mc.mineslot.events.CanselClick;
import net.isksss.mc.mineslot.events.InventoryClose;
import net.isksss.mc.mineslot.events.OpenChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MineSlot extends JavaPlugin {
    public static Plugin plg;
    @Override
    public void onEnable() {
        plg = this;
        // Plugin startup logic
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        String ms = config.getString("Message","default");
        getLogger().info(ms);
        DatabaseManager db = new DatabaseManager();
        db.InitDatabase();

        getServer().getPluginManager().registerEvents(new OpenChest(),this );
        getServer().getPluginManager().registerEvents(new InventoryClose(),this );
        getServer().getPluginManager().registerEvents(new CanselClick(),this );

        Objects.requireNonNull(getCommand(Config.CMD_BASE)).setExecutor(new AddChest());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
