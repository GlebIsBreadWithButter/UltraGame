package Entities.Units;

import Entities.Hero;
import Entities.UnitNameGiver;
import Entities.Unit;
import Fields.Field;
import GameLogic.AftermathMessage;
import GameLogic.Message;

import java.util.ArrayList;

public class Crossbowman extends Unit {


    public Crossbowman(boolean isEnemy, int inId){
        MAX_HEALTH = 50;
        health = 50;
        armor = 20;
        attackRange = 2;
        id = inId;
        this.isEnemy = isEnemy;
        cost = 25;
        reloadTime = 1;
        reloadTimer = 0;
        movePowerCounter = 4;
        maxMovePower = 4;
        moveCounter = 0;
        maxMoveCount = 2;
        armorDurability = 5;
        rangedAttackDamageAmount = 65;
        attackDamageAmount = 20;
        inSquad = false;
        bounty = 20;
        type = 2;
        name = "Арбалетчик " + UnitNameGiver.getNewName();
        lookName = "\u001B[41m" + "\u001B[33m" + " A " + "\u001B[0m";
    }

    public Crossbowman(){
        cost = 25;
        type = 2;
    }

    public Crossbowman(Unit u){
        this.MAX_HEALTH = u.getMAX_HEALTH();
        this.health = u.getHealth();
        this.armor = u.getArmor();
        this.attackRange = (int) u.getAttackRange();
        this.id = u.getId();
        this.isEnemy = u.getIsEnemy();
        this.cost = u.getCost();
        this.reloadTime = u.getReloadTime();
        this.reloadTimer = u.getReloadTimer();
        this.movePowerCounter = u.getMovePowerCounter();
        this.maxMovePower = u.getMaxMovePower();
        this.moveCounter = u.getMoveCounter();
        this.maxMoveCount = u.getMaxMoveCount();
        this.armorDurability = u.getArmorDurability();
        this.rangedAttackDamageAmount = u.getRangedAttackDamageAmount();
        this.attackDamageAmount = u.getAttackDamageAmount();
        this.inSquad = u.isInSquad();
        this.bounty = u.getBounty();
        this.type = u.getType();
        this.name = u.getName();
        this.lookName = u.getLookName();
    }

    public void doRangedAttack(Field battlefield, Hero hero, Hero enemyHero) {
        Message result = scanForEnemy(battlefield);
        if (result.getCode() == 1){
            Unit enemy = (Unit) battlefield.getCell(result.getData(), result.getAddData()).getOccupant();
            ArrayList<Unit> enemySquad = battlefield.getCell(result.getData(), result.getAddData()).getSquadOccupant();
            if (enemySquad != null) {
                Unit enemyFromSquad = enemySquad.get(0);
                enemyFromSquad.setHealth(enemyFromSquad.getHealth() - rangedAttackDamageAmount);
            }
            else {
                enemy.setHealth(enemy.getHealth() - rangedAttackDamageAmount);
                enemyHero.getArmy().remove(enemy);
                System.out.println("Попадание! Нанесено " + rangedAttackDamageAmount + " урона!");
                if (enemy.getHealth() <= 0) {
                    int receivedGold = enemy.getBounty();
                    hero.setGoldAmount(hero.getGoldAmount() + receivedGold);
                    battlefield.getCell(enemy.getXPos(), enemy.getYPos()).setOccupant(null);
                    System.out.println("Ваш " + this.name + " уничтожает врага " + enemy.getName() + " метким выстрелом из арбалета!");
                }
            }
            System.out.println(this.name + " уходит на перезарядку длиной в " + this.reloadTime + " х. !");
            reloadTimer += reloadTime;
        }
        else
            System.out.println("Враг не найден в области видимости арбалетчика!");
    }

    private Message scanForEnemy(Field battlefield){
        Message result = new Message();
        boolean gotcha = false;
        int startX = xPos - attackRange;
        int startY = yPos - attackRange;
        int endX = xPos + attackRange;
        int endY = yPos + attackRange;
        if (startX<0)
            startX=0;
        if (startY<0)
            startY=0;
        if (startX>battlefield.getxSize()-1)
            startX=battlefield.getxSize()-1;
        if (startY>battlefield.getySize()-1)
            startY=battlefield.getySize()-1;
        for (int i=startX; i<=endX; i++){
            for (int j=startY; j<=endY; j++){
                if (battlefield.getCell(i,j).getOccupant() != null && battlefield.getCell(i,j).getOccupant().getIsEnemy()) {
                    result = new Message(1, i, j, null);
                    gotcha = true;
                }
            }
        }
        if (!gotcha)
            result = new Message(0, null, null , null);
        return result;
    }
}
