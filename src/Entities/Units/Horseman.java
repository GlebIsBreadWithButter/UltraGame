package Entities.Units;

import Entities.UnitNameGiver;
import Entities.Unit;

public class Horseman extends Unit {
    public Horseman(boolean isEnemy, int inId){
        MAX_HEALTH = 175;
        health = 175;
        attackRange = 2;
        id = inId;
        inSquad = false;
        cost = 70;
        armor = 25;
        bounty = 30;
        this.isEnemy = isEnemy;
        attackDamageAmount = 55;
        movePowerCounter = 8;
        maxMovePower = 8;
        moveCounter = 0;
        armorDurability = 15;
        maxMoveCount = 3;
        type = 4;
        name = "Всадник " + UnitNameGiver.getNewName();
        lookName = "\u001B[41m" + "\u001B[32m" + " B " + "\u001B[0m";
    }

    public Horseman(){
        cost = 70;
        type = 4;
    }
}
