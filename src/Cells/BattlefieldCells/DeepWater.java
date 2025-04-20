package Cells.BattlefieldCells;

import Cells.Cell;

public class DeepWater extends Cell {
    public DeepWater(int x, int y) {
        interactable = false;
        canBePassed = false;
        worldMap = false;
        look = "\u001B[44m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }

}
