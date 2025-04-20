package Entities.Heroes;

import Cells.WorldCells.Castle;
import Entities.Hero;
import Entities.Unit;
import GameLogic.ArmyManager;

import java.util.ArrayList;

public class StartHero extends Hero {
    public StartHero(String input, boolean isEnemy, int inID, Castle inCastle){
        MAX_HEALTH = 350;
        health = 350;
        attackRange = 1;
        this.isEnemy = isEnemy;
        home = inCastle;
        armor = 10;
        id = inID;
        goldAmount = 30;
        healingAmount = 15;
        bounty = 150;
        movePowerCounter = 6;
        maxMovePower = 6;
        moveCounter = 0;
        maxMoveCount = 3;
        type = 50;
        name = input;
        army = new ArrayList<Unit>();
        armySquads = new ArrayList<ArrayList<Unit>>();
        lookName = "\u001B[37m"+ "\u001B[45m" + " " + name.charAt(0) + " " + "\u001B[0m";
        armyManager = new ArmyManager(this);
    }
}
