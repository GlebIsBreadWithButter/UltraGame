package Cells.WorldCells;

import Cells.Cell;

public class Mountains extends Cell {
    public Mountains(int x, int y){
        canBePassed = true;
        movingPenalty = 3;
        type = '1';
        interactable = false;
        worldMap = true;
        look = "\u001B[47m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
