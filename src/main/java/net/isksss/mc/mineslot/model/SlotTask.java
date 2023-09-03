package net.isksss.mc.mineslot.model;

import org.bukkit.scheduler.BukkitTask;

public class SlotTask {
    private String userName;
    private BukkitTask[] task;

    public SlotTask(String userName, BukkitTask[] task) {
        this.userName = userName;
        this.task = task;
    }

    // userName のゲッター
    public String getUserName() {
        return userName;
    }

    // userName のセッター
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // task のゲッター
    public BukkitTask[] getTask() {
        return task;
    }

    // task のセッター
    public void setTask(BukkitTask[] task) {
        this.task = task;
    }
}
