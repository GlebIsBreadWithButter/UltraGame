package Cells.BattlefieldCells;

import Cells.Cell;

public class Dirt extends Cell {
    public Dirt (int x, int y){
        canBePassed = true;
        movingPenalty = 2;
        interactable = false;
        worldMap = false;
        look = "\u001B[43m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
