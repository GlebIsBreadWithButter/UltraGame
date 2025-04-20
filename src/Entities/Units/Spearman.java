package Entities.Units;

import Entities.UnitNameGiver;
import Entities.Unit;

public class Spearman extends Unit {
    public Spearman(boolean isEnemy, int inId){
        MAX_HEALTH = 100;
        health = 100;
        id = inId;
        movePowerCounter = 3;
        maxMovePower = 3;
        maxMoveCount = 1;
        this.isEnemy = isEnemy;
        attackRange = 1;
        cost = 15;
        inSquad = false;
        moveCounter = 0;
        bounty = 5;
        attackDamageAmount = 30;
        type = 1;
        name = "Копейщик " + UnitNameGiver.getNewName();
        lookName = "\u001B[41m" + " К " + "\u001B[0m";
    }

    public Spearman(){
        cost = 15;
        type = 1;
    }
}
