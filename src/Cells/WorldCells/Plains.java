package Cells.WorldCells;

import Cells.Cell;

public class Plains extends Cell {
    public Plains(int x, int y){
        canBePassed = true;
        movingPenalty = 2;
        type = 2;
        worldMap = true;
        interactable = false;
        look = "\u001B[43m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
