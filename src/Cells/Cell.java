package Cells;

import Entities.Entity;
import Entities.Hero;
import Entities.Unit;
import GameLogic.Tunnel;

import java.util.ArrayList;

public class Cell {
    protected boolean interactable;
    protected boolean worldMap;
    protected Entity occupant;
    protected ArrayList<Unit> squadOccupant;
    protected int movingPenalty;
    protected int type;
    protected boolean canBePassed;
    protected boolean isEnemy;
    protected String look;
    protected int x;
    protected int y;
    protected Tunnel tunnel;

    public Tunnel getTunnel() {
        return tunnel;
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    public ArrayList<Unit> getSquadOccupant() {
        return squadOccupant;
    }

    public void setSquadOccupant(ArrayList<Unit> squadOccupant) {
        this.squadOccupant = squadOccupant;
    }

    public int getType() {
        return type;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public Entity getOccupant() {
        return occupant;
    }

    public Hero getHeroOccupant() {
        return (Hero) occupant;
    }

    public void setOccupant(Entity occupant) {
        this.occupant = occupant;
    }

    public boolean isInteractable() {
        return interactable;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCanBePassed() {
        return canBePassed;
    }

    public int getMovingPenalty() {
        return movingPenalty;
    }

    public boolean isWorldMap() {
        return worldMap;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public void modifyLook(){
        look = "\u001B[41m" + "|Т|" + "\u001B[0m";
    }
}
