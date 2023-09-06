package net.isksss.mc.mineslot.model;

import org.bukkit.scheduler.BukkitTask;

public class SlotTask {
    private String userName;
    private BukkitTask[] task;

    private boolean done = false;

    private int returnLevel = 0;

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

    public boolean getDone() {
        return done;
    }

    // task のセッター
    public void setDone(boolean done) {
        this.done = done;
    }

    public int getReturnLevel() {
        return returnLevel;
    }

    public void setReturnLevel(int returnLevel) {
        this.returnLevel = returnLevel;
    }
}
