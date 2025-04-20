package Cells.WorldCells;

import Cells.Cell;

public class MegaMountains extends Cell {
    public MegaMountains(int x, int y){
        canBePassed = false;
        type = '1';
        interactable = false;
        worldMap = true;
        look = "\u001B[30m"+ "\u001B[47m" + "XXX" + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
