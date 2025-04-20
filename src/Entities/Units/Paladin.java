package Entities.Units;

import Entities.UnitNameGiver;
import Entities.Unit;

public class Paladin extends Unit {
    private int armor;
    private int armorDurability;

    public int getArmorDurability() {
        return armorDurability;
    }

    public void setArmorDurability(int armorDurability) {
        this.armorDurability = armorDurability;
    }


    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public Paladin(boolean isEnemy, int inId){
        MAX_HEALTH = 450;
        health = 450;
        id = inId;
        attackRange = 1;
        armor = 90;
        armorDurability = 45;
        inSquad = false;
        this.isEnemy = isEnemy;
        cost = 200;
        bounty = 150;
        moveCounter = 0;
        attackDamageAmount = 90;
        movePowerCounter = 6;
        maxMovePower = 6;
        maxMoveCount = 3;
        type = 5;
        name = "Паладин " + UnitNameGiver.getNewName();
        lookName = "\u001B[41m" + "\u001B[35m" + " П " + "\u001B[0m";
    }

    public Paladin(){
        cost = 200;
        type = 5;
    }
}
