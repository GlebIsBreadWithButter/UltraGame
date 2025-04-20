package Cells.WorldCells;

import Cells.Cell;

public class Swamp extends Cell {
    public Swamp(int x, int y){
        canBePassed = true;
        movingPenalty = 4;
        worldMap = true;
        type = 5;
        interactable = false;
        look = "\u001B[42m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
