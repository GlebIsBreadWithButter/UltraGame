import Cells.WorldCells.Castle;
import Entities.Hero;
import Entities.Heroes.StartHero;
import Fields.Worlds.World;
import GameLogic.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static GameLogic.BattleLogic.*;
import static GameLogic.EnemyLogic.directionSelector;
import static GameLogic.Movement.entityMoved;
import static GameLogic.Movement.moveParametersReport;
import static GameLogic.Service.*;
import static java.lang.System.exit;

public class Main {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final Classifier idGiver = new Classifier();

    private static World world;
    private static WorldEditor worldEditor;
    private static Player player;
    private static Player enemy;
    private static boolean firstPlay = true,
            enemyParalysis = false,
            gameEnd = false;

    public static void main(String[] args) {
        System.out.println(Texts.getStartText());
        if (scanner.nextInt() == 0){
            LOGGER.log(Level.WARNING, "Выход из игры по желанию пользователя");
            exit(0);
        }
        else{
            System.out.println(Texts.getStartText2());
            LOGGER.log(Level.INFO, "Начало инициализации");
            initializeGame();
            System.out.println(Texts.getStartText4());
            System.out.println(Texts.getStartText3());
            LOGGER.log(Level.INFO, "Переход в режим закупки");
            unitShop(player.getHeroes().get(0));
        }
    }

    private static void initializeGame(){
        scanner.nextLine();
        String name = scanner.nextLine();
        world = new World();
        worldEditor = world.getWorldEditor();
        LOGGER.log(Level.INFO, "Мир сгенерирован");
        player = new Player(false);
        enemy = new Player(true);
        Castle myCastle = world.getMyCastle();
        Castle enemyCastle = world.getEnemyCastle();
        LOGGER.log(Level.INFO, "Созданы замки и инстанции игрока, противника");
        StartHero hero = new StartHero(name, false, 0, myCastle);
        StartHero enemyHero = new StartHero("Король Врагов", true, 1, enemyCastle);
        player.getHeroes().add(hero);
        enemy.getHeroes().add(enemyHero);
        enemyHero.setGoldAmount(75);
        for (int i = 0; i<4; i++)
            enemyHero.buyUnit(1, idGiver.getId(), enemyCastle);
        player.setOwnedCastle(myCastle);
        enemy.setOwnedCastle(enemyCastle);
        worldEditor.placeEntity(hero, myCastle.getX(), myCastle.getY());
        worldEditor.placeEntity(enemyHero, enemyCastle.getX(), enemyCastle.getY());
        LOGGER.log(Level.INFO, "Игра подготовлена");
    }

    public static void unitShop(Hero hero){
        boolean continueUnitShopping = true;
        System.out.println(Texts.getUnitShopText());
        LOGGER.log(Level.INFO, "Показ магазина");
        showUnitShop(hero.getItsHome());
        while (continueUnitShopping) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice != 0) {
                    LOGGER.log(Level.INFO, "Покупка юнита");
                    hero.buyUnit(choice, idGiver.getId(), hero.getItsHome());
                }
                else {
                    continueUnitShopping = false;
                    LOGGER.log(Level.INFO, "Предложен вход в магазины игроку");
                    offerShopsMenuEntering(hero);
                }
            }
            else {
                showBalance(hero);
                LOGGER.log(Level.INFO, "Показан баланс игрока");
            }
        }
        LOGGER.log(Level.INFO, "Выход игрока из замка");
        roam();
    }

    public static void castleShop(Hero hero){
        LOGGER.log(Level.INFO, "Покупка улучшений замка");
        boolean continueCastleShopping = true;
        System.out.println(Texts.getCastleShopText());
        showCastleShop(hero.getItsHome());
        while (continueCastleShopping) {
            if(scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice != 0)
                    hero.buyCastleUpgrade(choice, hero.getItsHome());
                else {
                    continueCastleShopping = false;
                    offerShopsMenuEntering(hero);
                }
            }
            else
                showBalance(hero);
        }
        LOGGER.log(Level.INFO, "Выход игрока из замка");
        roam();
    }

    public static void heroShop(Player player, Hero hero){
        LOGGER.log(Level.INFO, "Покупка героев");
        boolean continueHeroShopping = true;
        System.out.println(Texts.getHeroShopText());
        showHeroShop();
        while (continueHeroShopping) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice != 0) {
                    Integer boughtHeroId = player.buyHero(choice, idGiver.getHeroId(), player.getOwnedCastle());
                    if (boughtHeroId != null) {
                        int x = player.getOwnedCastle().getX() + 1;
                        int y = player.getOwnedCastle().getY();
                        worldEditor.placeEntity(player.getHeroes().get(boughtHeroId), x, y);
                    }
                }
                else {
                    continueHeroShopping = false;
                    offerShopsMenuEntering(hero);
                }
            }
            else
                showBalance(hero);
        }
        LOGGER.log(Level.INFO, "Выход игрока из замка");
        roam();
    }

    public static void wormsDen(Hero hero){
        LOGGER.log(Level.INFO, "Прокладка тоннелей");
        boolean continueWormsDenControlling = true;
        System.out.println(Texts.wormsDenText);
        while (continueWormsDenControlling) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice != 0) {
                    if (choice < 0) {
                        choice = 1;
                        LOGGER.log(Level.SEVERE, "Выход за пределы допустимых для ввода значений, коррекция");
                    }
                    System.out.println(Texts.wormsDenDirection);
                    int direction = scanner.nextInt();
                    System.out.println(Texts.wormsDenLength);
                    int length = scanner.nextInt();
                    if (length>5)
                        length = 5;
                    Tunnel t = new Tunnel(length, direction, player.getOwnedCastle().getX(), player.getOwnedCastle().getY());
                    player.addTunnel(t);
                    LOGGER.log(Level.INFO, "Тоннель создан");
                    System.out.println(Texts.excavationStarted);
                    continueWormsDenControlling = false;
                    offerShopsMenuEntering(hero);
                }
                else {
                    continueWormsDenControlling = false;
                    offerShopsMenuEntering(hero);
                }
            }
        }
        roam();
    }

    public static void tunnelManager(Hero hero){
        LOGGER.log(Level.INFO, "Управление тоннелями");
        boolean continueTunnelManaging = true;
        System.out.println(Texts.tunnelManageText);
        Texts.printTunnelsOffer(player.getTunnels());
        while (continueTunnelManaging) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice != -1) {
                    if(choice < -1) {
                        choice = 0;
                        LOGGER.log(Level.SEVERE, "Выход за пределы допустимых для ввода значений, коррекция");
                    }
                    else if (choice > player.getTunnels().size()-1)
                        choice = player.getTunnels().size()-1;
                    System.out.println("Перемещаемся...");
                    Tunnel t = player.getTunnel(choice);
                    worldEditor.moveEntityToCoords(hero, t.getExitX(), t.getExitY());
                    world.getCell(t.getExitX(), t.getExitY()).setTunnel(t);
                    world.getCell(t.getExitX(), t.getExitY()).modifyLook();
                    LOGGER.log(Level.INFO, "Герой игрока перемещается по тоннелю");
                    roam();
                }
                else {
                    continueTunnelManaging = false;
                    offerShopsMenuEntering(hero);
                }
            }
        }
        LOGGER.log(Level.INFO, "Выход из здания");
        roam();
    }

    public static void roam(){
        System.out.println(Texts.getLeavingText());
        if(firstPlay)
            System.out.println(Texts.getControlsText());
        firstPlay = false;
        System.out.println(Texts.worldMapStatus);
        world.show();
        while (!gameEnd) {
            LOGGER.log(Level.INFO, "Ходит игрок");
            playerTurn();
            if (!enemyParalysis) {
                LOGGER.log(Level.INFO, "Ходит враг");
                enemyTurn();
            }
            heroMoveReplenish();
            enemyMoveReplenish();
        }
    }

    public static void playerTurn(){
        Movement moveOperator = new Movement(world);
        Message moveResult;
        boolean heroCanMove;
        for (Hero h : player.getHeroes()) {
            System.out.println(Texts.getHeroMoveHeader(h.getName()));
            heroCanMove = true;
            moveParametersReport(h);
            while (heroCanMove) {
                if(scanner.hasNextInt()) {
                    moveResult = moveOperator.move(h, scanner.nextInt(), false);
                    if (moveResult.getCode() == 5)
                        gameEnd = true;
                    heroCanMove = analyzeHeroMoveResult(h, moveResult);
                    if (h.getMaxMoveCount() != h.getMoveCounter()) {
                        moveParametersReport(h);
                    }
                    if (entityMoved(moveResult.getCode()))
                        world.show();
                }
                else
                    additionalActionSelector(h);
            }
        }
        LOGGER.log(Level.INFO, "Обновление таймера всех тоннелей");
        Tunnel t = player.updateWormTimer();
        if (t.getTimer() <=0) {
            LOGGER.log(Level.INFO, "Прокопан тоннель");
            world.getCell(t.getExitX(), t.getExitY()).modifyLook();
        }
    }

    public static void additionalActionSelector(Hero h){
        switch (scanner.nextLine()){
            case "h":
                showMyHeroes();
                break;
            case "a":
                showMyHeroesArmies();
                break;
            case "c":
                showMyCastleStatus();
                break;
            case "e":
                editArmy(h);
        }
    }

    public static void enemyTurn(){
        Movement moveOperator = new Movement(world);
        Message moveResult;
        boolean heroCanMove;
        for (Hero h : enemy.getHeroes()) {
            Texts.printEnemyMoveHeader();
            heroCanMove = true;
            while (heroCanMove) {
                moveResult = moveOperator.move(h, directionSelector(h), false);
                if (moveResult.getCode() == 1)
                    gameEnd = true;
                heroCanMove = analyzeHeroMoveResultModified(h, moveResult);
                if (entityMoved(moveResult.getCode())) {
                    world.show();
                }
            }
        }
    }

    public static void heroMoveReplenish(){
        LOGGER.log(Level.INFO, "Восстановление двигательных возможностей героев игрока");
        for (Hero h : player.getHeroes()) {
            h.setMoveCounter(0);
            if(player.getOwnedCastle().isStableBuilt())
                h.setMoveCounter(-1);
            h.setMovePowerCounter(h.getMaxMovePower());
        }
    }

    public static void enemyMoveReplenish(){
        LOGGER.log(Level.INFO, "Восстановление двигательных возможностей героев врага");
        for (Hero h : enemy.getHeroes()) {
            h.setMoveCounter(0);
            if(enemy.getOwnedCastle().isStableBuilt())
                h.setMoveCounter(-1);
            h.setMovePowerCounter(h.getMaxMovePower());
        }
    }

    public static boolean analyzeHeroMoveResult(Hero h, Message moveResult){
        boolean heroCanMove = true;
        int a = h.getMoveCounter();
        switch (moveResult.getCode()){
            case 0:
                LOGGER.log(Level.INFO, "Игрок переходит в пустую клетку");
                h.setMoveCounter(a+1);
                h.setMovePowerCounter(h.getMovePowerCounter() - moveResult.getExtraData());
                break;
            case 1:
                if (moveResult.getData() == 0) {
                    LOGGER.log(Level.INFO, "Игрок переходит в замок");
                    h.setMoveCounter(a + 1);
                    offerShopsMenuEntering(h);
                    h.setMovePowerCounter(h.getMovePowerCounter() - moveResult.getExtraData());
                }
                else {
                    LOGGER.log(Level.INFO, "Враг добрался до замка");
                    defeat(0);
                }
                break;
            case 2:
                LOGGER.log(Level.INFO, "Игрок вступает в бой");
                h.setMoveCounter(0);
                h.setMovePowerCounter(h.getMaxMovePower());
                Message battleResult;
                Hero eh = world.getCell(moveResult.getData(), moveResult.getAddData()).getHeroOccupant();
                battleResult = battle(h, eh, world.getCell(moveResult.getData(), moveResult.getAddData()), player);
                analyzeBattleResult(battleResult, h, eh);
                break;
            case 3:
                LOGGER.log(Level.INFO, "Игрок находит мешок с золотом");
                goldPackEncounter(moveResult, h, a);
                break;
            case 4:
                LOGGER.log(Level.INFO, "Игрок не вступает в бой");
                break;
            case 5:
                LOGGER.log(Level.INFO, "Игрок переходит в замок врага");
                victory(0);
                break;
            case 6:
                LOGGER.log(Level.INFO, "Игрок находит тоннель");
                System.out.println("Здесь есть вход в тоннель! Войти?\n0 - нет\n1 - да");
                if (scanner.hasNextInt() && scanner.nextInt()==1)
                    worldEditor.moveEntityToCoords(h, moveResult.getData(), moveResult.getAddData());
                h.setMoveCounter(h.getMaxMoveCount());
                h.setMovePowerCounter(0);
                world.show();
                offerShopsMenuEntering(h);
                break;
            case 7:
                LOGGER.log(Level.INFO, "Игрок ожидает на месте");
                h.setMoveCounter(a + 1);
                h.setMovePowerCounter(h.getMovePowerCounter() + moveResult.getData());
                if (h.getMaxMovePower() < h.getMovePowerCounter())
                    h.setMovePowerCounter(h.getMaxMovePower());
                System.out.println("Ожидаем...");
                break;
            case 8:
                System.out.println("Недостаточно очков перемещения!");
                break;
            default:
                System.out.println("Сюда пройти не выйдет!");
                break;
        }
        if (h.getMoveCounter() == h.getMaxMoveCount()) {
            System.out.println("У героя закончились сдвиги!");
            heroCanMove = false;
        }
        return heroCanMove;
    }

    public static void goldPackEncounter(Message moveResult, Hero h, int curMoveCount){
        int goldFound = moveResult.getData();
        if (!h.getIsEnemy())
            System.out.println(Texts.getGoldPackMessage(goldFound));
        int g = h.getGoldAmount();
        h.setGoldAmount(g+goldFound);
        h.setMoveCounter(curMoveCount+1);
        h.setMovePowerCounter(h.getMovePowerCounter() - moveResult.getExtraData());
    }

    public static boolean analyzeHeroMoveResultModified(Hero h, Message moveResult){
        boolean heroCanMove = true;
        int a = h.getMoveCounter();
        switch (moveResult.getCode()){
            case 0:
                LOGGER.log(Level.INFO, "Враг переходит в пустую клетку");
                h.setMoveCounter(a+1);
                h.setMovePowerCounter(h.getMovePowerCounter() - moveResult.getExtraData());
                break;
            case 1:
                if (moveResult.getData() != 0) {
                    LOGGER.log(Level.INFO, "Враг переходит в замок игрока");
                    defeat(0);
                }
                break;
            case 2:
                LOGGER.log(Level.INFO, "Враг вступает в бой с игроком");
                h.setMoveCounter(h.getMaxMoveCount());
                h.setMovePowerCounter(0);
                Message battleResult;
                Hero mh = world.getCell(moveResult.getData(), moveResult.getAddData()).getHeroOccupant();
                battleResult = battle(mh, h, world.getCell(moveResult.getData(), moveResult.getAddData()), player);
                analyzeBattleResult(battleResult, mh, h);
                break;
            case 3:
                LOGGER.log(Level.INFO, "Враг находит мешок с золотом");
                goldPackEncounter(moveResult, h, a);
                break;
            case 5:
                LOGGER.log(Level.INFO, "Враг покупает солдата");
                h.buyUnit(1, idGiver.getId(), h.getItsHome());
                break;
            case 7:
                LOGGER.log(Level.INFO, "Враг ожидает на месте");
                h.setMoveCounter(a + 1);
                h.setMovePowerCounter(h.getMovePowerCounter() + moveResult.getData());
                if (h.getMaxMovePower() < h.getMovePowerCounter())
                    h.setMovePowerCounter(h.getMaxMovePower());
                break;
            default:
                break;
        }
        if (h.getMoveCounter() == h.getMaxMoveCount()) {
            heroCanMove = false;
        }
        return heroCanMove;
    }

    public static void offerShopsMenuEntering(Hero hero){
        System.out.println(Texts.getShopsEnteringOffer());
        if (scanner.nextInt() == 1){
            offerMoneyStoring(hero);
            System.out.print(Texts.getShopsChoiceOffer());
            if (player.getOwnedCastle().isWormsDenBuilt() && !player.isWormIsDigging())
                System.out.print("\n"+Texts.wormsDenEnterOffer);
            if (player.getOwnedCastle().isWormsDenBuilt() && player.hasTunnels())
                System.out.print("\n"+Texts.tunnelEnterOffer);
            System.out.print("\n");
            switch (scanner.nextInt()){
                case 1:
                    unitShop(hero);
                    break;
                case 2:
                    castleShop(hero);
                    break;
                case 3:
                    heroShop(player, hero);
                    break;
                case 4:
                    if (player.getOwnedCastle().isWormsDenBuilt() && !player.isWormIsDigging())
                        wormsDen(hero);
                    break;
                case 5:
                    if (player.getOwnedCastle().isWormsDenBuilt() && player.hasTunnels())
                        tunnelManager(hero);
                    break;
            }
        }
    }

    public static void analyzeBattleResult(Message battleResult, Hero myHero, Hero enemyHero){
        switch (battleResult.getCode()){
            case 50:
                worldEditor.moveEntityToCoords(myHero, 3, 0);
                myHero.setMoveCounter(0);
                myHero.setMovePowerCounter(myHero.getMaxMovePower());
                System.out.println(Texts.defeatTextSmall);
                System.out.println(Texts.getHeroRanAwayText(myHero.getName()));
                world.show();
                break;
            case 51:
                worldEditor.moveEntityToCoords(enemyHero, 3, 8);
                enemyHero.setMoveCounter(enemyHero.getMaxMoveCount());
                enemyHero.setMovePowerCounter(0);
                System.out.println(Texts.victoryTextSmall);
                System.out.println(Texts.getEnemyHeroRanAwayText(enemyHero.getName()));
                world.show();
                break;
            case 52:
                worldEditor.removeEntity(myHero);
                player.getHeroes().remove(myHero);
                System.out.println(Texts.defeatTextSmall);
                System.out.println(Texts.getHeroDiedText(myHero.getName()));
                world.show();
                if (player.getHeroes().isEmpty())
                    defeat(1);
                break;
            case 53:
                worldEditor.removeEntity(enemyHero);
                enemy.getHeroes().remove(enemyHero);
                System.out.println(Texts.victoryTextSmall);
                System.out.println(Texts.getEnemyHeroDiedText(enemyHero.getName()));
                world.show();
                if (enemy.getHeroes().isEmpty())
                    victory(1);
                break;
        }
    }

    public static void showMyHeroes(){
        System.out.println(Texts.myHeroesReportHeader);
        for (Hero h : player.getHeroes()){
            String toPrint = h.getId() + "    " + h.getName() + "    " + h.getHealth() + "/" + h.getMAX_HEALTH();
            System.out.println(toPrint);
        }
    }

    public static void showBalance(Hero hero){
        switch (scanner.nextLine()){
            case "g":
                System.out.println("Баланс героя " + hero.getName() + " - " + hero.getGoldAmount() + " ед. золота");
                break;
            case "f":
                showMyCastleStatus();
                break;
            case "redsussybaka":
                System.out.println("МАГИЯ! ВАУ!");
                hero.setGoldAmount(100000);
                player.setBankGoldAmount(100000);
                break;
            case "M1899":
                System.out.println("Враг парализован!");
                enemyParalysis = true;
                break;
            case "M1911":
                System.out.println("Враг снова в движении!");
                enemyParalysis = false;
                break;
        }
    }

    public static void showMyHeroesArmies(){
        System.out.println(Texts.myHeroesArmiesReportHeader);
        for (Hero h : player.getHeroes()){
            h.getArmyManager().showArmy();
            h.getArmyManager().showSquads();
        }
    }

    public static void showMyCastleStatus(){
        System.out.println(Texts.myCastleStatusReportHeader);
        System.out.println(Texts.getMyCastleStatusReport(player));
    }

    public static void victory(int code){
        LOGGER.log(Level.WARNING, "Победа игрока, закрытие программы");
        switch (code) {
            case (0):
                System.out.println(Texts.getVictoryText());
                break;
            case (1):
                System.out.println(Texts.victory1Text);
        }
        exit(0);
    }

    public static void defeat(int code){
        LOGGER.log(Level.WARNING, "Победа врага, закрытие программы");
        switch (code) {
            case (0):
                System.out.println(Texts.getDefeatText());
                break;
            case (1):
                System.out.println(Texts.defeat1Text);
        }
        exit(0);
    }
}