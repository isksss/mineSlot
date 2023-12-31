package net.isksss.mc.mineslot.gui;

import net.isksss.mc.mineslot.MineSlot;
import net.isksss.mc.mineslot.config.Config;
import net.isksss.mc.mineslot.model.SlotTask;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reel {

    private Inventory inv;
    private List<Material> materialList = new ArrayList<>();
    private List<Integer> magnification = new ArrayList<>();
    private static List<SlotTask> tasks = new ArrayList<>();

    private Random rand;

    public Reel(Inventory inv){
        //
        this.rand = new Random();
        //
        this.inv = inv;

        //リール作成
        setReel(Material.APPLE, 2);
        setReel(Material.BONE,1);
        setReel(Material.DIAMOND, 10);
        setReel(Material.COAL, 1);
        setReel(Material.END_CRYSTAL,200);
        setReel(Material.GOLDEN_APPLE,20);
    }

    private void setReel(Material material, int mag){
        materialList.add(material);
        magnification.add(mag);
    }

    // リールの回転スタート
    public void StartReel(){
        List<HumanEntity> viewers =  inv.getViewers();
        Player p = (Player) viewers.get(0);
        BukkitTask task1 = goSlot(Config.SLOT_LINE_INDEX_1);
        BukkitTask task2 = goSlot(Config.SLOT_LINE_INDEX_2);
        BukkitTask task3 = goSlot(Config.SLOT_LINE_INDEX_3);
        BukkitTask[] taskList = {task1,task2,task3};
        SlotTask taskBean = new SlotTask(p.getName(),taskList);

        this.tasks.add(taskBean);

        this.setStopButton(inv);
    }

    private BukkitTask goSlot(int slot){
        int speed = rand.nextInt(slot) + 1;
        return new BukkitRunnable(){
            @Override
            public void run(){
                //スロットを回す処理
                Reel reel = new Reel(inv);
//                int speed = rand.nextInt(slot)+1;
                reel.PlayingSlot(slot);
            }
        }.runTaskTimer(MineSlot.plg,0, speed);
    }

    public void PlayingSlot(int line){
        // リールごとの処理
        // 1,2,3で渡される
        int h_i = line + 11; // 12-14の番号
        int m_i = h_i + 9;
        int l_i = h_i + 18;

        ItemStack h = inv.getItem(h_i);
        ItemStack m = inv.getItem(m_i);

        int next_h= materialList.indexOf(h.getType()) + 1;
        int max = materialList.size();
        if(next_h == max ){
            next_h = 0;
        }

        inv.setItem(h_i, new ItemStack(materialList.get(next_h)));
        inv.setItem(m_i, h);
        inv.setItem(l_i, m);

    }
    //リールの初期配置
    public void SetReel(){
        // 12-14
        // 21-23
        // 30-32

        int maxSize = materialList.size();
        for(int x=12;x<15;x++){
            int start = rand.nextInt(maxSize);

            for(int y=0;y<3;y++){
                int index = x + (y*9);
                start++;
                if(start == maxSize){
                    start=0;
                }
                ItemStack item = new ItemStack(materialList.get(start));
                inv.setItem(index,item);
            }
        }
    }

    public SlotTask getSlotTaskByPlayerName(String playerName) {
        for (SlotTask task : tasks) {
            if (task.getUserName().equalsIgnoreCase(playerName)) {
                return task;
            }
        }
        return null; // 該当するタスクが見つからない場合はnullを返す
    }

    public void StopTask(String PlayerName, int line){
        SlotTask task = getSlotTaskByPlayerName(PlayerName);
        BukkitTask[] tasklist = task.getTask();

        if(!tasklist[line].isCancelled()){
            tasklist[line].cancel();
        }

        //すべてのレーンがキャンセルされているか確認
        if(tasklist[0].isCancelled() && tasklist[1].isCancelled() && tasklist[2].isCancelled()){
            task.setDone(true);
            //ここであたり目チェック
            int bet = checkReel(inv);
            task.setReturnLevel(bet);
        }

    }

    private void setStopButton(Inventory inv){
        ItemStack button = new ItemStack(Material.LANTERN);
        for(int i=0;i<3;i++){
            int index=i+48;
            String name = String.valueOf(index);

            ItemMeta meta = button.getItemMeta();
            meta.setDisplayName(name);
            button.setItemMeta(meta);

            inv.setItem(index,button);
        }
    }

    public void removeTask(String playerName){
        SlotTask task = getSlotTaskByPlayerName(playerName);
        tasks.remove(task);
    }

    public int getBet(Inventory inv){
        ItemStack item = inv.getItem(0);
        ItemMeta meta = item.getItemMeta();
        String title = meta.getDisplayName();
        return Integer.parseInt(title);
    }

    private int checkReel(Inventory inv){
        int level = 0;
        if( inv.getItem(21).getType() == inv.getItem(22).getType()  &&
            inv.getItem(22).getType() == inv.getItem(23).getType()  ){
        // 目がそろった時
            level = getBet(inv);

            int indexMaterial = materialList.indexOf(inv.getItem(21).getType());
            level = magnification.get(indexMaterial);
        }

        return level;
    }
}
