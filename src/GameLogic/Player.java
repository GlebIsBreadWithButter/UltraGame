package GameLogic;

import Cells.WorldCells.Castle;
import Entities.*;
import Entities.Heroes.HeavyHero;
import Entities.Heroes.LightHero;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Player {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Player.class.getName());
        } catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    private int bankGoldAmount;
    private ArrayList<Hero> heroes;
    private boolean isEnemy;
    private Castle ownedCastle;
    private boolean wormIsDigging;
    private ArrayList<Tunnel> tunnels = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public void addTunnel(Tunnel t){
        tunnels.add(t);
    }

    public ArrayList<Tunnel> getTunnels() {
        return tunnels;
    }

    public Tunnel getTunnel(int target){
        return tunnels.get(target);
    }

    public Tunnel updateWormTimer(){
        Tunnel t = new Tunnel(1, 0,0,0);
        if (!tunnels.isEmpty()) {
            t = tunnels.get(tunnels.size() - 1);
            t.setTimer(t.getTimer() - 1);
            wormIsDigging = t.getTimer() > 0;
        }
        return t;
    }

    public boolean hasTunnels(){
        return !tunnels.isEmpty();
    }

    public boolean isWormIsDigging() {
        return wormIsDigging;
    }

    public void setWormIsDigging(boolean wormIsDigging) {
        this.wormIsDigging = wormIsDigging;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public int getBankGoldAmount() {
        return bankGoldAmount;
    }

    public void setBankGoldAmount(int bankGoldAmount) {
        this.bankGoldAmount = bankGoldAmount;
    }

    public Castle getOwnedCastle() {
        return ownedCastle;
    }

    public void setOwnedCastle(Castle ownedCastle) {
        this.ownedCastle = ownedCastle;
    }

    public Player(boolean isEnemy){
        bankGoldAmount = 0;
        heroes = new ArrayList<Hero>();
        this.isEnemy = isEnemy;
    }

    public Integer buyHero (int chosenType, int inId, Castle inCastle){
        Integer result = null;
        Hero a = chooseHeroToBuy(chosenType, inId, inCastle);
        if (a!= null && (canAfford(a.getCost())) && (a.getType() != -1)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(Texts.getStartText2());
            String name = scanner.nextLine();
            a.setName(name);
            heroes.add(a);
            System.out.println(a.getName() + " - новый герой!");
            LOGGER.log(Level.INFO, "Успешная покупка игроком нового героя");
            spendBankGold(a.getCost());
            result = inId;
        }
        else if (a != null && (!canAfford(a.getCost())) && (a.getType() != -1))
            System.out.println(Texts.getNotEnoughGoldText());
        return result;
    }

    private Hero chooseHeroToBuy(int choice, int inId, Castle inCastle){
        Hero a;
        String name=" ";
        switch (choice){
            case (1):
                a = new HeavyHero(name, isEnemy, inId, inCastle);
                break;
            case (2):
                a = new LightHero(name, isEnemy, inId, inCastle);
                break;
            default:
                a = null;
                LOGGER.log(Level.SEVERE, "Выбран неверный тип героя к покупке");
                System.out.println("Такого героя нет в продаже!");
                break;
        }
        return a;
    }

    private boolean canAfford(int cost){
        return cost <= this.bankGoldAmount;
    }

    private void spendBankGold(int cost) {
        bankGoldAmount -= cost;
        LOGGER.log(Level.INFO, "Баланс казны игрока: " + this.bankGoldAmount);
    }
}
