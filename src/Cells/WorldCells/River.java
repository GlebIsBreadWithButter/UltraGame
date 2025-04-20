package Cells.WorldCells;

import Cells.Cell;

public class River extends Cell {
    public River(int x, int y){
        canBePassed = true;
        movingPenalty = 4;
        type = 3;
        worldMap = true;
        interactable = false;
        look = "\u001B[46m" + "---" + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
