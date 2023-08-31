package net.isksss.mc.mineslot.model;

public class Chest {
    private int id;
    private int xCoordinate;
    private int yCoordinate;
    private int zCoordinate;
    private int requiredLevel;

    public Chest(int id, int xCoordinate, int yCoordinate, int zCoordinate, int requiredLevel) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.requiredLevel = requiredLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getZCoordinate() {
        return zCoordinate;
    }

    public void setZCoordinate(int zCoordinate) {
        this.zCoordinate = zCoordinate;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public String toString() {
        return "Chest{" +
                "id=" + id +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", zCoordinate=" + zCoordinate +
                ", requiredLevel=" + requiredLevel +
                '}';
    }
}
