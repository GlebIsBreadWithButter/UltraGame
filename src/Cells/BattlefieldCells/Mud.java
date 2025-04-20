package Cells.BattlefieldCells;

import Cells.Cell;

public class Mud extends Cell {
    public Mud(int x, int y){
        canBePassed = true;
        movingPenalty = 3;
        worldMap = false;
        interactable = false;
        look = "\u001B[42m" + "   " + "\u001B[0m";
        this.x = x;
        this.y = y;
    }
}
