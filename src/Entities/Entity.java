package Entities;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Entity {
    protected double health;
    protected double MAX_HEALTH;
    protected double attackDamageAmount;
    protected int maxMovePower;
    protected int maxMoveCount;
    protected int attackRange;
    protected int type;
    protected int id;
    protected int xPos;
    protected int yPos;
    protected int bounty;
    protected int movePowerCounter;
    protected int cost;
    protected String name;
    protected boolean isEnemy;
    protected int moveCounter;
    protected String lookName;

    public void setLookName(String lookName) {
        this.lookName = lookName;
    }

    public String getLookName() {
        return lookName;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public int getId() {
        return id;
    }

    public int getBounty() {
        return bounty;
    }

    public int getCost() {
        return cost;
    }

    public int getMaxMoveCount() {
        return maxMoveCount;
    }

    public void setMaxMoveCount(int maxMoveCount){
        this.maxMoveCount = maxMoveCount;
    }

    public boolean getIsEnemy() {
        return isEnemy;
    }

    public void setIsEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    public double getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public void setMAX_HEALTH(double MAX_HEALTH) {
        this.MAX_HEALTH = MAX_HEALTH;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttackDamageAmount() {
        return attackDamageAmount;
    }

    public int getMaxMovePower() {
        return maxMovePower;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void setPos(int xPos, int yPos){
        setXPos(xPos);
        setYPos(yPos);
    }

    public int getMovePowerCounter() {
        return movePowerCounter;
    }

    public void setMovePowerCounter(int movePowerCounter) {
        this.movePowerCounter = movePowerCounter;
    }

    public void setMaxMovePower(int maxMovePower) {
        this.maxMovePower = maxMovePower;
    }
}
