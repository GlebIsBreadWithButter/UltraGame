package Entities;

import Cells.WorldCells.Castle;
import Entities.Units.*;
import GameLogic.ArmyManager;
import GameLogic.Texts;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Hero extends Entity {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.txt")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Hero.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected int healingAmount;
    protected int goldAmount;
    protected Castle home;
    protected int armor;
    protected ArrayList<Unit> army;
    protected ArmyManager armyManager;
    protected ArrayList<ArrayList<Unit>> armySquads;

    public ArmyManager getArmyManager() {
        return armyManager;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getArmor() {
        return armor;
    }

    public void setHome(Castle home){
        this.home = home;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
    }

    public Castle getItsHome() {
        return home;
    }

    //SHOPPING
    private boolean canAfford(int cost){
        return cost <= this.goldAmount;
    }

    private void spendGold(int toSpend){
        this.goldAmount -= toSpend;
        LOGGER.log(Level.INFO, "Баланс героя " + this.name + ": " + this.goldAmount);
    }

    public void buyUnit (int chosenType, int inId, Castle castle){
        Unit a = new Unit();
        a = chooseUnitToBuy(chosenType, a, inId);
        if ((canAfford(a.getCost()) && (a.getType()) != -1)) {
            army.add(a);
            if (!castle.isEnemy()) {
                LOGGER.log(Level.INFO, "Успешная покупка солдата");
                System.out.println(a.getName() + " вступает в Вашу армию!");
            }
            spendGold(a.cost);
        }
        else if ((a.getCost() > goldAmount) && (a.getType() != -1)) {
            System.out.println(Texts.getNotEnoughGoldText());
            LOGGER.log(Level.INFO, "Недостаточно золота для покупки солдата");
        }
    }

    private Unit chooseUnitToBuy(int choice, Unit a, int inId){
        switch (choice){
            case (1):
                if (home.isBarracksBuilt()) {
                    a = new Spearman(isEnemy, inId);
                }
                else
                    System.out.println("Такого воина нет в продаже!");
                break;
            case (2):
                if (home.isTowerBuilt()) {
                    a = new Crossbowman(isEnemy, inId);
                }
                else
                    System.out.println("Такого воина нет в продаже!");
                break;
            case (3):
                if (home.isGarrisonBuilt()) {
                    a = new Swordsman(isEnemy, inId);
                }
                else
                    System.out.println("Такого воина нет в продаже!");
                break;
            case (4):
                if (home.isStableBuilt()) {
                    a = new Horseman(isEnemy, inId);
                }
                else
                    System.out.println("Такого воина нет в продаже!");
                break;
            case (5):
                if (home.isCathedralBuilt()) {
                    a = new Paladin(isEnemy, inId);
                }
                else
                    System.out.println("Такого воина нет в продаже!");
                break;
            default:
                LOGGER.log(Level.SEVERE, "Выбран неверный тип солдата к покупке");
                System.out.println("Такого воина нет в продаже!");
                break;
        }
        return a;
    }

    public void buyCastleUpgrade(int chosenUpgrade, Castle castle){
        Integer boughtUpgradeNumber = chooseCastleUpgradeToBuy(chosenUpgrade, castle);
        switch(boughtUpgradeNumber){
            case(1):
                castle.setTowerBuilt(true);
                System.out.println("Башня построена!");
                spendGold(Castle.LV1_UPGRADE_COST);
                break;
            case(2):
                castle.setGarrisonBuilt(true);
                System.out.println("Гарнизон построен!");
                spendGold(Castle.LV2_UPGRADE_COST);
                break;
            case(3):
                castle.setStableBuilt(true);
                System.out.println("Конюшня построена!");
                spendGold(Castle.LV3_UPGRADE_COST);
                break;
            case(4):
                castle.setCathedralBuilt(true);
                System.out.println("Собор построен!");
                spendGold(Castle.LV4_UPGRADE_COST);
                break;
            case(5):
                castle.setWormsDenBuilt(true);
                System.out.println("Берлога Червя построена!");
                spendGold(Castle.LV5_UPGRADE_COST);
        }
    }

    private Integer chooseCastleUpgradeToBuy(int chosenUpgrade, Castle castle){
        int boughtUpgradeNumber = -5;
        int ans = 0;
        switch(chosenUpgrade){
            case(1):
                if(!castle.isTowerBuilt() && canAfford(Castle.LV1_UPGRADE_COST))
                    boughtUpgradeNumber = 1;
                else if (castle.isTowerBuilt())
                    ans = 1;
                else
                    ans = 2;
                break;
            case(2):
                if(!castle.isGarrisonBuilt() && canAfford(Castle.LV2_UPGRADE_COST))
                    boughtUpgradeNumber = 2;
                else if (castle.isGarrisonBuilt())
                    ans = 1;
                else
                    ans = 2;
                break;
            case(3):
                if(!castle.isStableBuilt() && canAfford(Castle.LV3_UPGRADE_COST))
                    boughtUpgradeNumber = 3;
                else if (castle.isStableBuilt())
                    ans = 1;
                else
                    ans = 2;
                break;
            case(4):
                if(!castle.isCathedralBuilt() && canAfford(Castle.LV4_UPGRADE_COST))
                    boughtUpgradeNumber = 4;
                else if (castle.isCathedralBuilt())
                    ans = 1;
                else
                    ans = 2;
                break;
            case(5):
                if(!castle.isWormsDenBuilt() && canAfford(Castle.LV5_UPGRADE_COST))
                    boughtUpgradeNumber = 5;
                else if (castle.isWormsDenBuilt())
                    ans = 1;
                else
                    ans = 2;
                break;
            default:
                LOGGER.log(Level.SEVERE, "Выбран неверный тип улучшения замка к покупке");
                System.out.println("Такого улучшения замка не существует!");
                break;
        }
        if (ans == 1){
            System.out.println("Это улучшение замка уже приобретено!");
        }
        else if (ans == 2){
            System.out.println(Texts.getNotEnoughGoldText());
        }
        return boughtUpgradeNumber;
    }

    public ArrayList<Unit> getArmy() {
        return army;
    }

    public void setArmy(ArrayList<Unit> army) {
        this.army = army;
    }

    public void setArmySquads(ArrayList<ArrayList<Unit>> armySquads) {
        this.armySquads = armySquads;
    }

    public ArrayList<ArrayList<Unit>> getArmySquads(){
        return armySquads;
    }
}
