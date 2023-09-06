package net.isksss.mc.mineslot.commands;

import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.model.Chest;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class AddChest  implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player p)){
            sender.sendMessage("This command is only used player.");
            return false;
        }

        if (!(command.getName().equalsIgnoreCase(Config.CMD_BASE))){
            return false;
        }

        if((args.length == 0)){
            p.sendMessage("Args is not enough.");
            return false;
        }

        String subCmd = args[0];
        Set<String> UserTags = p.getScoreboardTags();

        // ADD MODE
        if (subCmd.equalsIgnoreCase(Config.CMD_ADD)){
            if(UserTags.contains(Config.CHEST_ADD_TAG)){
                p.sendMessage("OFF: ADD MODE.");
                p.removeScoreboardTag(Config.CHEST_ADD_TAG);
                return true;
            }
            if(UserTags.contains(Config.CHEST_DELETE_TAG)){
                p.sendMessage("You are DELETE mode.");
                p.sendMessage("Change mode.");
                p.removeScoreboardTag(Config.CHEST_DELETE_TAG);
                p.addScoreboardTag(Config.CHEST_ADD_TAG);
                return true;
            }
            p.addScoreboardTag(Config.CHEST_ADD_TAG);
            p.sendMessage("ON: ADD MODE.");

            ItemStack stick = new ItemStack(Material.STICK);
            ItemMeta meta = stick.getItemMeta();
            String bet = args[1];
            meta.setDisplayName(bet);
            stick.setItemMeta(meta);
            p.getInventory().addItem(stick);

            return true;
        }

        return true;
    }
}
