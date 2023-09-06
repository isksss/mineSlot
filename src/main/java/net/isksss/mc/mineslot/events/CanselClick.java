package net.isksss.mc.mineslot.events;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.gui.Reel;
import net.isksss.mc.mineslot.model.SlotTask;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.Set;

public class CanselClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        Set<String> UserTags = p.getScoreboardTags();
        Location loc = p.getLocation();
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
                    int bet = reel.getBet(inv);
                    int plevel = p.getLevel();
                    if(plevel < bet){
                        p.sendMessage("Your level has not reached the required value.");
                        return;
                    }
                    p.sendMessage("Bet: " + bet +" level.");
                    p.setLevel(plevel - bet);
                    //音を鳴らす
//                    p.getLocation().getWorld().playSound(p, Sound.BLOCK_GRASS_BREAK,1,1);
                    loc.getWorld().playSound(loc,Sound.BLOCK_BEACON_DEACTIVATE,1,2);
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
                    //音W
                    loc.getWorld().playSound(loc,Sound.ITEM_TRIDENT_THROW,1f,2f);

                    if(reel.getSlotTaskByPlayerName(p.getName()).getDone()){
                        //ここで目を確認
                        int bet = reel.getBet(inv);
                        SlotTask task = reel.getSlotTaskByPlayerName(p.getName());
                        int mag =task.getReturnLevel();
                        int returnLevel = bet * mag;

                        if(returnLevel > 0){
                            p.sendMessage("Return: " + returnLevel+" level.");
                            int pl = p.getLevel();
                            loc.getWorld().playSound(loc,Sound.ENTITY_ENDER_DRAGON_GROWL, 1.5f,1f);
                            p.setLevel(pl + returnLevel);
                        }else{
                            loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK,2f,1f);
                        }

                        reel.removeTask(p.getName());
                        UserTags.remove(Config.SLOT_PLAYING);
                    }

                }
            }
        }
    }
}
