package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.db.ChestDAO;
import net.isksss.mc.mineslot.model.Chest;
import net.isksss.mc.mineslot.utils.GuiMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class OpenChest implements Listener {
    private final ChestDAO chestDAO;
    private final GuiMenu gui;
    public OpenChest() {
        this.chestDAO = new ChestDAO();
        this.gui = new GuiMenu();
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock != null && clickedBlock.getType() == Material.CHEST) {
            Location loc = clickedBlock.getLocation();
            int x_loc = loc.getBlockX();
            int y_loc = loc.getBlockY();
            int z_loc = loc.getBlockZ();

            // チェストが登録されているか確認する処理
            int chestId = chestDAO.getChestIdByCoordinates(x_loc, y_loc, z_loc);
            Player p = event.getPlayer();
            Set<String> UserTags = p.getScoreboardTags();
            if (chestId != -1) {
                // チェストが登録されている場合の処理
                Inventory inv = gui.createSlotMenu();

                // チェストを削除する場合
                if(UserTags.contains(Config.CHEST_DELETE_TAG)){
                    event.setCancelled(true);
                    chestDAO.getChestIdByCoordinates(x_loc,y_loc,z_loc);
                    p.sendMessage("Deleted Chest !!");
                    return;
                }
                // slotをプレイ
                p.openInventory(inv);
                p.addScoreboardTag(Config.CHEST_OPEN_TAG);
            }else{
                //chestを登録するとき
                if(UserTags.contains(Config.CHEST_ADD_TAG)){
                    event.setCancelled(true);
                    Chest c = new Chest(0,x_loc,y_loc,z_loc,10);
                    chestDAO.addChest(c);
                }
            }
        }
    }
}
