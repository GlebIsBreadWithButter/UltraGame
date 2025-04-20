package Entities.Units;

import Entities.UnitNameGiver;
import Entities.Unit;

public class Swordsman extends Unit {
    public Swordsman(boolean isEnemy, int inId){
        MAX_HEALTH = 250;
        health = 250;
        attackRange = 1;
        id = inId;
        this.isEnemy = isEnemy;
        armor = 50;
        armorDurability = 35;
        cost = 85;
        moveCounter = 0;
        bounty = 65;
        attackDamageAmount = 40;
        movePowerCounter = 6;
        maxMovePower = 6;
        maxMoveCount = 2;
        type = 3;
        name = "Мечник " + UnitNameGiver.getNewName();
        lookName = "\u001B[41m" + "\u001B[37m" + " М " + "\u001B[0m";
        inSquad = false;
    }

    public Swordsman(){
        cost = 85;
        type = 3;
    }
}
