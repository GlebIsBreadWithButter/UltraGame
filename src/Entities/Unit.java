package Entities;

public class Unit extends Entity{
    protected boolean inSquad;
    protected int armor;
    protected int armorDurability;
    protected int reloadTime;
    protected int reloadTimer;
    protected int rangedAttackDamageAmount;

    public int getReloadTimer() {
        return reloadTimer;
    }

    public void setReloadTimer(int reloadTimer) {
        this.reloadTimer = reloadTimer;
    }

    public int getRangedAttackDamageAmount() {
        return rangedAttackDamageAmount;
    }

    public void setRangedAttackDamageAmount(int rangedAttackDamageAmount) {
        this.rangedAttackDamageAmount = rangedAttackDamageAmount;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getArmorDurability() {
        return armorDurability;
    }

    public void setArmorDurability(int armorDurability) {
        this.armorDurability = armorDurability;
    }

    public boolean isInSquad() {
        return inSquad;
    }

    public void setInSquad(boolean inSquad) {
        this.inSquad = inSquad;
    }

    public Unit(){
        type = -1;
    }
}
