package Cells.BattlefieldCells;

import Cells.Cell;

public class Paving extends Cell {
    public Paving(int x, int y){
        canBePassed = true;
        movingPenalty = 1;
        worldMap = false;
        interactable = false;
        look = "\u001B[40m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
