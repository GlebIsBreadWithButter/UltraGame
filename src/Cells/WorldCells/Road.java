package Cells.WorldCells;

import Cells.Cell;

public class Road extends Cell {
    public Road(int x, int y){
        canBePassed = true;
        movingPenalty = 1;
        type = 4;
        worldMap = true;
        interactable = false;
        look = "\u001B[40m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
