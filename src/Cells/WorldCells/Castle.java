package Cells.WorldCells;

import Cells.Cell;

public class Castle extends Cell {
    public final static int LV1_UPGRADE_COST = 100;
    public final static int LV2_UPGRADE_COST = 200;
    public final static int LV3_UPGRADE_COST = 300;
    public final static int LV4_UPGRADE_COST = 500;
    public static final int LV5_UPGRADE_COST = 1000;

    private boolean tavernBuilt;
    private boolean stableBuilt;
    private boolean barracksBuilt;
    private boolean towerBuilt;
    private boolean garrisonBuilt;
    private boolean cathedralBuilt;
    public boolean wormsDenBuilt;

    public boolean isWormsDenBuilt() {
        return wormsDenBuilt;
    }

    public void setWormsDenBuilt(boolean wormsDenBuilt) {
        this.wormsDenBuilt = wormsDenBuilt;
    }

    public boolean isCathedralBuilt() {
        return cathedralBuilt;
    }

    public void setCathedralBuilt(boolean cathedralBuilt) {
        this.cathedralBuilt = cathedralBuilt;
    }

    public boolean isGarrisonBuilt() {
        return garrisonBuilt;
    }

    public void setGarrisonBuilt(boolean garrisonBuilt) {
        this.garrisonBuilt = garrisonBuilt;
    }

    public boolean isBarracksBuilt() {
        return barracksBuilt;
    }

    public void setBarracksBuilt(boolean barracksBuilt) {
        this.barracksBuilt = barracksBuilt;
    }

    public boolean isTavernBuilt() {
        return tavernBuilt;
    }

    public void setTavernBuilt(boolean tavernBuilt) {
        this.tavernBuilt = tavernBuilt;
    }

    public boolean isTowerBuilt() {
        return towerBuilt;
    }

    public void setTowerBuilt(boolean towerBuilt) {
        this.towerBuilt = towerBuilt;
    }

    public boolean isStableBuilt() {
        return stableBuilt;
    }

    public void setStableBuilt(boolean stableBuilt) {
        this.stableBuilt = stableBuilt;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public Castle(boolean isEnemy, int x, int y){
        this.x = x;
        this.y = y;
        tavernBuilt = true;
        barracksBuilt = true;
        canBePassed = true;
        towerBuilt = false;
        garrisonBuilt = false;
        cathedralBuilt = false;
        interactable = true;
        stableBuilt = false;
        this.isEnemy = isEnemy;
        type = 0;
        movingPenalty = 1;
        look = "\u001B[41m" + "|=|" + "\u001B[0m";
    }
}
