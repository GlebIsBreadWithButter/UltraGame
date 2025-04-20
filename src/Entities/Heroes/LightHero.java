package Entities.Heroes;

import Cells.WorldCells.Castle;
import Entities.Hero;
import Entities.Unit;
import GameLogic.ArmyManager;

import java.util.ArrayList;

public class LightHero extends Hero {
    public LightHero(String input, boolean isEnemy, int inID, Castle inCastle){
        MAX_HEALTH = 500;
        attackRange = 1;
        this.isEnemy = isEnemy;
        home = inCastle;
        armor = 20;
        id = inID;
        goldAmount = 30;
        healingAmount = 35;
        cost = 900;
        bounty = 450;
        movePowerCounter = 9;
        maxMovePower = 9;
        moveCounter = 0;
        maxMoveCount = 5;
        type = 52;
        name = input;
        army = new ArrayList<Unit>();
        armySquads = new ArrayList<ArrayList<Unit>>();
        lookName = "\u001B[33m"+ "\u001B[45m" + " " + name.charAt(0)+ " " + "\u001B[0m";
        armyManager = new ArmyManager(this);
    }
}
