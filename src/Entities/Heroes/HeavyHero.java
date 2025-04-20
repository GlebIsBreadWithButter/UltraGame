package Entities.Heroes;

import Cells.WorldCells.Castle;
import Entities.Hero;
import Entities.Unit;
import GameLogic.ArmyManager;

import java.util.ArrayList;

public class HeavyHero extends Hero {
    public HeavyHero(String input, boolean isEnemy, int inID, Castle inCastle){
        MAX_HEALTH = 1000;
        attackRange = 1;
        this.isEnemy = isEnemy;
        home = inCastle;
        armor = 50;
        id = inID;
        goldAmount = 30;
        healingAmount = 25;
        cost = 900;
        bounty = 450;
        movePowerCounter = 7;
        maxMovePower = 7;
        moveCounter = 0;
        maxMoveCount = 4;
        type = 51;
        name = input;
        army = new ArrayList<Unit>();
        armySquads = new ArrayList<ArrayList<Unit>>();
        lookName = "\u001B[36m"+ "\u001B[45m" + " " + name.charAt(0) + " " + "\u001B[0m";
        armyManager = new ArmyManager(this);
    }
}
