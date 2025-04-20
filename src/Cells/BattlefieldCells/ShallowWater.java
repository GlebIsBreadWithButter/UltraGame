package Cells.BattlefieldCells;

import Cells.Cell;

public class ShallowWater extends Cell {
    public ShallowWater(int x, int y){
        canBePassed = true;
        movingPenalty =4;
        worldMap = false;
        interactable = false;
        look = "\u001B[46m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
