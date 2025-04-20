package Cells.BattlefieldCells;

import Cells.Cell;

public class Rock extends Cell {
    public Rock(int x, int y){
        canBePassed = false;
        worldMap = false;
        interactable = false;
        look = "\u001B[37m"+ "\u001B[47m" + " X " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
