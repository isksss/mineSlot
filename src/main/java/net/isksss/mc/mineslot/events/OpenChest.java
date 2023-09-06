package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.db.ChestDAO;
import net.isksss.mc.mineslot.model.Chest;
import net.isksss.mc.mineslot.gui.GuiMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class OpenChest implements Listener {
    private final ChestDAO chestDAO;
//    private final GuiMenu gui;
    public OpenChest() {
        this.chestDAO = new ChestDAO();
//        this.gui = new GuiMenu();
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
                Chest chest = chestDAO.getChestById(chestId);
                GuiMenu gui = new GuiMenu(chest);
                Inventory inv = gui.createSlotMenu();

                int level = chest.getRequiredLevel();
                // チェストを削除する場合
                if(UserTags.contains(Config.CHEST_DELETE_TAG)){
                    event.setCancelled(true);
                    chestDAO.getChestIdByCoordinates(x_loc,y_loc,z_loc);
                    p.sendMessage("Deleted Chest !!");
                    return;
                }
                if(UserTags.contains(Config.CHEST_ADD_TAG)){
                    p.sendMessage("Already registered.");
                    return;
                }

                event.setCancelled(true);
                p.openInventory(inv);
                p.addScoreboardTag(Config.CHEST_OPEN_TAG);
            }else{
                //chestを登録するとき
                if(UserTags.contains(Config.CHEST_ADD_TAG)){

                    ItemStack offhand = p.getInventory().getItemInOffHand();
                    if(offhand.getType() == Material.STICK){
                        event.setCancelled(true);
                        int bet = Integer.parseInt(offhand.getItemMeta().getDisplayName());
                        Chest c = new Chest(0,x_loc,y_loc,z_loc,bet);
                        chestDAO.addChest(c);
                        p.sendMessage("Add chests.");
                    }

                }
            }
        }
    }
}
