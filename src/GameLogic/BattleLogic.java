package GameLogic;

import Cells.Cell;
import Entities.Entity;
import Entities.Hero;
import Entities.Unit;
import Entities.Units.Crossbowman;
import Fields.*;
import Fields.Battlefields.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static GameLogic.Movement.entityMoved;
import static GameLogic.Movement.moveParametersReport;

public class BattleLogic {
    public static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(BattleLogic.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void placeUnits(Field battlefield, FieldEditor fe, Hero myHero, Hero enemyHero){
        int x=0, y=0;
        int size = battlefield.getFieldBody().size()-1;
        fe.placeEntity(myHero, x, y);
        fe.placeEntity(enemyHero,size, size);
        x++;
        for (Unit u : myHero.getArmy()){
            fe.placeEntity(u, x, y);
            x++;
            if (x==5){
                y++;
                x=0;
            }
        }
        for (ArrayList<Unit> s : myHero.getArmySquads()){
            fe.placeSquad(s, x, y);
            x++;
            if (x==5){
                y++;
                x=0;
            }
        }
        x=size-1;
        y=size;
        for (Unit u : enemyHero.getArmy()){
            fe.placeEntity(u, x, y);
            x--;
            if (x==0){
                y--;
                x=size;
            }
        }
        for (ArrayList<Unit> s : enemyHero.getArmySquads()){
            fe.placeSquad(s, x, y);
            x--;
            if (x==5){
                y--;
                x=size;
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    public static Field battlefieldSelector(Cell place){
        return switch (place.getType()) {
            case 1 -> new MountatinsField();
            case 3 -> new RiverField();
            case 4 -> new RoadField();
            case 5 -> new SwampField();
            default -> new PlainsField();
        };
    }

    public static void healthReport(Entity e){
        System.out.println(e.getName() + ": " + e.getHealth() + "/" + e.getMAX_HEALTH());
    }

    public static void dealDamage(Unit firstUnit, Unit secondUnit, int damageModifier, int armorModifier){
        double dealtDamage = firstUnit.getAttackDamageAmount() + damageModifier - secondUnit.getArmor() - armorModifier;
        if (dealtDamage < 5)
            dealtDamage = 5;
        if (dealtDamage > 95)
            dealtDamage = 95;
        secondUnit.setHealth(secondUnit.getHealth() - dealtDamage);
        secondUnit.setArmorDurability(secondUnit.getArmorDurability() - 1);
        LOGGER.log(Level.INFO, firstUnit.getName() + " атакует " + secondUnit.getName() + ", урон " + firstUnit.getAttackDamageAmount() + " модур: " + damageModifier + "модбр:" + armorModifier);
        System.out.println(Texts.getUnitsDealtDamageReport(firstUnit, secondUnit, dealtDamage));
    }

    public static void modifyCopies(AftermathMessage msg, ArrayList<Unit> ma, ArrayList<Unit> ea, ArrayList<ArrayList<Unit>> mas, ArrayList<ArrayList<Unit>> eas){
        ma.remove(msg.getMyUnitToBeRemoved());
        ea.remove(msg.getEnemyUnitToBeRemoved());
        mas.remove(msg.getMySquadToBeRemoved());
        eas.remove(msg.getEnemySquadToBeRemoved());
        LOGGER.log(Level.INFO, "Обновлены копии под удаление");
    }

    public static AftermathMessage unitsFight(Unit myUnit, Unit enemyUnit, boolean startedByEnemy, Field field, Hero myHero, Hero enemyHero){
        AftermathMessage aftermath;
        System.out.println(Texts.unitsFightHeader);
        System.out.println(Texts.getFirstStrikeAnnouncement(startedByEnemy));
        int enemyFirstStrikeBonus, myFirstStrikeBonus;
        if (startedByEnemy) {
            enemyFirstStrikeBonus = 10;
            dealDamage(enemyUnit, myUnit, enemyFirstStrikeBonus, 0);
        }
        else {
            myFirstStrikeBonus = 10;
            dealDamage(myUnit, enemyUnit, myFirstStrikeBonus, 0);
        }
        boolean fightContinues = myUnit.getHealth() > 0 && enemyUnit.getHealth() > 0;
        while (fightContinues){
            System.out.println(Texts.fightDecision);
            System.out.println("Ваши войска:");
            healthReport(myUnit);
            System.out.println("Войска врага:");
            healthReport(enemyUnit);
            Scanner s = new Scanner(System.in);
            int decision = s.nextInt();
            System.out.println(myUnit.getName() + ": " + myUnit.getHealth() + "/" + myUnit.getMAX_HEALTH());
            if (decision == 0) {
                dealDamage(myUnit, enemyUnit, 0, 0);
                dealDamage(enemyUnit, myUnit, 0, 0);
            }
            else if (decision == 1){
                dealDamage(enemyUnit, myUnit, 0, 30);
            }
            if (myUnit.getHealth()<=0 || enemyUnit.getHealth()<=0)
                fightContinues = false;
        }
        int receivedGold = 0;
        if ((myUnit.getHealth()<=0) && (enemyUnit.getHealth()<=0)) {
            receivedGold = enemyUnit.getBounty();
            myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
            enemyHero.setGoldAmount(enemyHero.getGoldAmount() + myUnit.getBounty());
            field.getCell(myUnit.getXPos(), myUnit.getYPos()).setOccupant(null);
            field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
            aftermath = new AftermathMessage(myUnit, enemyUnit);
        }
        else if (myUnit.getHealth()<=0){
            enemyHero.setGoldAmount(enemyHero.getGoldAmount()+myUnit.getBounty());
            field.getCell(myUnit.getXPos(), myUnit.getYPos()).setOccupant(null);
            aftermath = new AftermathMessage(false, myUnit);
        }
        else {
            receivedGold = enemyUnit.getBounty();
            myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
            field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
            aftermath = new AftermathMessage(true, enemyUnit);
        }
        System.out.println(Texts.getUnitsFightResult(myUnit, enemyUnit, receivedGold));
        System.out.println(Texts.fightHeaderEnd);
        field.show();
        return aftermath;
    }

    public static AftermathMessage unitSquadFight(Unit myUnit, ArrayList<Unit> enemySquad, boolean startedByEnemy, Field field, Hero myHero, Hero enemyHero){
        AftermathMessage aftermath;
        System.out.println(Texts.unitSquadFightHeader);
        System.out.println(Texts.getFirstStrikeAnnouncement(startedByEnemy));
        int enemyFirstStrikeBonus, myFirstStrikeBonus;
        if (startedByEnemy) {
            enemyFirstStrikeBonus = 10;
            for (Unit enemyUnit : enemySquad) {
                dealDamage(enemyUnit, myUnit, enemyFirstStrikeBonus, 0);
            }
        }
        else {
            myFirstStrikeBonus = 10;
            Unit enemyUnit = enemySquad.get(0);
            dealDamage(myUnit, enemyUnit, myFirstStrikeBonus, 0);
        }
        boolean squadIsOk=false;
        double hSum = 0;
        for (Unit u : enemySquad){
            hSum += u.getHealth();
        }
        if (hSum>0)
            squadIsOk = true;
        boolean fightContinues = myUnit.getHealth() > 0 && squadIsOk;
        while (fightContinues){
            System.out.println(Texts.fightDecision);
            System.out.println("Ваши войска:");
            healthReport(myUnit);
            System.out.println("Войска врага:");
            for (Unit enemyUnit : enemySquad) {
                healthReport(enemyUnit);
            }
            int decision = scanner.nextInt();
            if (decision == 0) {
                dealDamage(myUnit, enemySquad.get(0), 0, 0);
                for (Unit enemyUnit : enemySquad) {
                    dealDamage(enemyUnit, myUnit, 0, 0);
                }
            }
            else if (decision == 1){
                for (Unit enemyUnit : enemySquad) {
                    dealDamage(enemyUnit, myUnit, 0, 30);
                }
            }
            hSum = 0;
            for (Unit u : enemySquad){
                hSum += u.getHealth();
            }
            if (hSum<=0)
                squadIsOk = false;
            if (myUnit.getHealth()<=0 || !squadIsOk)
                fightContinues = false;
        }
        int receivedGold = 0;
        if (!squadIsOk && myUnit.getHealth()<=0){
            for (Unit enemyUnit : enemySquad) {
                receivedGold += enemyUnit.getBounty();
                myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setSquadOccupant(null);
            }
            field.getCell(enemySquad.get(0).getXPos(), enemySquad.get(0).getYPos()).setSquadOccupant(null);
            aftermath = new AftermathMessage(false, true, myUnit, enemySquad);
        }
        else if (myUnit.getHealth()<=0){
            enemyHero.setGoldAmount(enemyHero.getGoldAmount()+myUnit.getBounty());
            field.getCell(myUnit.getXPos(), myUnit.getYPos()).setOccupant(null);
            aftermath = new AftermathMessage(false, myUnit);
        }
        else {
            for (Unit enemyUnit : enemySquad) {
                receivedGold += enemyUnit.getBounty();
                myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setSquadOccupant(null);
            }
            aftermath = new AftermathMessage(true, enemySquad);
        }
        System.out.println(Texts.getUnitSquadFightResult(myUnit, enemySquad, receivedGold));
        System.out.println(Texts.fightHeaderEnd);
        field.show();
        return aftermath;
    }

    public static AftermathMessage squadUnitFight(ArrayList<Unit> mySquad, Unit enemyUnit, boolean startedByEnemy, Field field, Hero myHero, Hero enemyHero){
        AftermathMessage aftermath;
        System.out.println(Texts.unitSquadFightHeader);
        System.out.println(Texts.getFirstStrikeAnnouncement(startedByEnemy));
        int enemyFirstStrikeBonus, myFirstStrikeBonus;
        if (startedByEnemy) {
            enemyFirstStrikeBonus = 10;
            Unit myUnit = mySquad.get(0);
            dealDamage(enemyUnit, myUnit, enemyFirstStrikeBonus, 0);
        }
        else {
            myFirstStrikeBonus = 10;
            for (Unit myUnit : mySquad) {
                dealDamage(myUnit, enemyUnit, myFirstStrikeBonus, 0);
            }
        }
        boolean squadIsOk=false;
        double hSum = 0;
        for (Unit u : mySquad){
            hSum += u.getHealth();
        }
        if (hSum>0)
            squadIsOk = true;
        boolean fightContinues = enemyUnit.getHealth() > 0 && squadIsOk;
        while (fightContinues){
            System.out.println(Texts.fightDecision);
            System.out.println("Ваши войска:");
            for (Unit myUnit :mySquad) {
                healthReport(myUnit);
            }
            System.out.println("Войска врага:");
            healthReport(enemyUnit);
            int decision = scanner.nextInt();
            if (decision == 0) {
                for (Unit myUnit : mySquad) {
                    dealDamage(myUnit, enemyUnit, 0, 0);
                }
                dealDamage(enemyUnit, mySquad.get(0), 0, 0);
            }
            else if (decision == 1){
                dealDamage(enemyUnit, mySquad.get(0), 0, 0);
            }
            hSum = 0;
            for (Unit u : mySquad){
                hSum += u.getHealth();
            }
            if (hSum<=0)
                squadIsOk = false;
            if (enemyUnit.getHealth()<=0 || !squadIsOk)
                fightContinues = false;
        }
        int receivedGold = 0;
        if (enemyUnit.getHealth()<=0 && !squadIsOk){
            receivedGold += enemyUnit.getBounty();
            myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
            field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
            for (Unit myUnit : mySquad) {
                enemyHero.setGoldAmount(enemyHero.getGoldAmount()+myUnit.getBounty());
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setOccupant(null);
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setSquadOccupant(null);
            }
            aftermath = new AftermathMessage(true, false, enemyUnit, mySquad);
        }
        else if (enemyUnit.getHealth()<=0){
            receivedGold += enemyUnit.getBounty();
            myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
            field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
            aftermath = new AftermathMessage(true, enemyUnit);
        }
        else {
            for (Unit myUnit : mySquad) {
                enemyHero.setGoldAmount(enemyHero.getGoldAmount()+myUnit.getBounty());
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setOccupant(null);
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setSquadOccupant(null);
            }
            aftermath = new AftermathMessage(false, mySquad);
        }
        System.out.println(Texts.getSquadUnitFightResult(mySquad, enemyUnit, receivedGold));
        System.out.println(Texts.fightHeaderEnd);
        field.show();
        return aftermath;
    }

    public static AftermathMessage squadsFight(ArrayList<Unit> mySquad, ArrayList<Unit> enemySquad, boolean startedByEnemy, Field field, Hero myHero, Hero enemyHero){
        AftermathMessage aftermath;
        System.out.println(Texts.squadsFightHeader);
        System.out.println(Texts.getFirstStrikeAnnouncement(startedByEnemy));
        int enemyFirstStrikeBonus, myFirstStrikeBonus;
        if (startedByEnemy) {
            enemyFirstStrikeBonus = 10;
            for (Unit enemyUnit : enemySquad) {
                dealDamage(enemyUnit, mySquad.get(0), enemyFirstStrikeBonus, 0);
            }
        }
        else {
            myFirstStrikeBonus = 10;
            for (Unit myUnit : mySquad) {
                dealDamage(myUnit, enemySquad.get(0), myFirstStrikeBonus, 0);
            }
        }
        boolean mySquadIsOk=false, enemySquadIsOk = false;
        double mySum = 0, enemySum = 0;
        for (Unit u : mySquad){
            mySum += u.getHealth();
        }
        for (Unit u : enemySquad){
            enemySum += u.getHealth();
        }
        if (mySum>0)
            mySquadIsOk = true;
        if (enemySum>0)
            enemySquadIsOk = true;
        boolean fightContinues = mySquadIsOk && enemySquadIsOk;
        while (fightContinues){
            System.out.println(Texts.fightDecision);
            System.out.println("Ваши войска:");
            for (Unit myUnit :mySquad) {
                healthReport(myUnit);
            }
            System.out.println("Войска врага:");
            for (Unit enemyUnit : enemySquad) {
                healthReport(enemyUnit);
            }
            int decision = scanner.nextInt();
            if (decision == 0) {
                for (Unit myUnit : mySquad) {
                    dealDamage( myUnit, enemySquad.get(0),0, 0);
                }
                for (Unit enemyUnit : enemySquad) {
                    dealDamage( enemyUnit, mySquad.get(0),0, 0);
                }
            }
            else if (decision == 1){
                for (Unit enemyUnit : enemySquad) {
                    dealDamage( enemyUnit, mySquad.get(0), 0, 30);
                }
            }
            mySum = 0;
            for (Unit u : mySquad){
                mySum += u.getHealth();
            }
            if (mySum<=0)
                mySquadIsOk = false;
            enemySum = 0;
            for (Unit u : enemySquad){
                enemySum += u.getHealth();
            }
            if (enemySum<=0)
                enemySquadIsOk = false;
            if (!enemySquadIsOk || !mySquadIsOk)
                fightContinues = false;
        }
        int receivedGold = 0;
        if (!mySquadIsOk && !enemySquadIsOk) {
            for (Unit myUnit : mySquad) {
                enemyHero.setGoldAmount(enemyHero.getGoldAmount()+myUnit.getBounty());
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setOccupant(null);
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setSquadOccupant(null);
            }
            for (Unit enemyUnit : enemySquad) {
                receivedGold += enemyUnit.getBounty();
                myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setSquadOccupant(null);
            }
            aftermath = new AftermathMessage(mySquad, enemySquad);
        }
        else if (!mySquadIsOk){
            for (Unit myUnit : mySquad) {
                enemyHero.setGoldAmount(enemyHero.getGoldAmount()+myUnit.getBounty());
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setOccupant(null);
                field.getCell(myUnit.getXPos(), myUnit.getYPos()).setSquadOccupant(null);
            }
            aftermath = new AftermathMessage(false, mySquad);
        }
        else {
            for (Unit enemyUnit : enemySquad) {
                receivedGold += enemyUnit.getBounty();
                myHero.setGoldAmount(myHero.getGoldAmount() + receivedGold);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setOccupant(null);
                field.getCell(enemyUnit.getXPos(), enemyUnit.getYPos()).setSquadOccupant(null);
            }
            aftermath = new AftermathMessage(true, enemySquad);
        }

        System.out.println(Texts.getSquadsFightResult(mySquad, enemySquad, receivedGold));
        System.out.println(Texts.fightHeaderEnd);
        field.show();
        return aftermath;
    }

    public static AftermathMessage analyzeUnitMoveResult(Unit u, Message moveResult, Field battlefield, Hero myHero, Hero enemyHero){
        AftermathMessage msg = new AftermathMessage(true);
        int a = u.getMoveCounter();
        switch (moveResult.getCode()){
            case 0:
                u.setMoveCounter(a+1);
                u.setMovePowerCounter(u.getMovePowerCounter() - moveResult.getExtraData());
                break;
            case 2:
                if (battlefield.getCell(moveResult.getData(), moveResult.getAddData()).getSquadOccupant() != null)
                    msg = unitSquadFight(u, battlefield.getCell(moveResult.getData(), moveResult.getAddData()).getSquadOccupant(), u.getIsEnemy(),
                            battlefield, myHero, enemyHero);
                else
                    msg = unitsFight(u, (Unit) battlefield.getCell(moveResult.getData(), moveResult.getAddData()).getOccupant(), u.getIsEnemy(),
                            battlefield, myHero, enemyHero);
                break;
            case 4:
                break;
            case 7:
                u.setMoveCounter(a + 1);
                u.setMovePowerCounter(u.getMovePowerCounter() + moveResult.getData());
                if (u.getMaxMovePower() < u.getMovePowerCounter())
                    u.setMovePowerCounter(u.getMaxMovePower());
                System.out.println("Ожидаем...");
                break;
            case 8:
                System.out.println("Недостаточно очков перемещения!");
                break;
            default:
                System.out.println("Сюда пройти не выйдет!");
                break;
        }
        if (u.getMoveCounter() == u.getMaxMoveCount()) {
            System.out.println("У воина закончились сдвиги!");
            msg.setUnitCanMove(false);
        }
        return msg;
    }

    public static AftermathMessage analyzeSquadMoveResult(ArrayList<Unit> s, Message moveResult, Field battlefield, Hero myHero, Hero enemyHero){
        AftermathMessage msg = new AftermathMessage(true);
        int a = s.get(0).getMoveCounter();
        switch (moveResult.getCode()){
            case 0:
                s.get(0).setMoveCounter(a+1);
                s.get(0).setMovePowerCounter(s.get(0).getMovePowerCounter() - moveResult.getExtraData());
                break;
            case 2:
                if (battlefield.getCell(moveResult.getData(), moveResult.getAddData()).getSquadOccupant() != null)
                    msg = squadsFight(s, battlefield.getCell(moveResult.getData(), moveResult.getAddData()).getSquadOccupant(), s.get(0).getIsEnemy(), battlefield, myHero, enemyHero);
                else
                    msg = squadUnitFight(s, (Unit) battlefield.getCell(moveResult.getData(), moveResult.getAddData()).getOccupant(), s.get(0).getIsEnemy(), battlefield, myHero, enemyHero);
                break;
            case 4:
                break;
            case 7:
                s.get(0).setMoveCounter(a + 1);
                s.get(0).setMovePowerCounter(s.get(0).getMovePowerCounter() + moveResult.getData());
                if (s.get(0).getMaxMovePower() < s.get(0).getMovePowerCounter())
                    s.get(0).setMovePowerCounter(s.get(0).getMaxMovePower());
                System.out.println("Ожидаем...");
                break;
            case 8:
                System.out.println("Недостаточно очков перемещения!");
                break;
            default:
                System.out.println("Сюда пройти не выйдет!");
                break;
        }
        if (s.get(0).getMoveCounter() == s.get(0).getMaxMoveCount()) {
            System.out.println("У воина закончились сдвиги!");
            msg.setUnitCanMove(false);
        }
        return msg;
    }

    public static Message battle(Hero myHero, Hero enemyHero, Cell place, Player player){
        int mpx = myHero.getXPos(),
            mpy = myHero.getYPos(),
            epx = enemyHero.getXPos(),
            epy = enemyHero.getYPos();
        System.out.println(Texts.fightStart);
        Field battlefield = battlefieldSelector(place);
        FieldEditor fe = new FieldEditor(battlefield);
        placeUnits(battlefield, fe, myHero, enemyHero);
        battlefield.show();
        boolean battleContinues = !myHero.getArmy().isEmpty() || !myHero.getArmySquads().isEmpty(),
                unitCanMove,
                battleCommited = battleContinues && (!enemyHero.getArmy().isEmpty() || !enemyHero.getArmySquads().isEmpty()),
                squadCanMove;
        Movement moveOperator = new Movement(battlefield);
        Message moveResult;
        String prevLook;
        AftermathMessage battleAftermath;
        ArrayList<Unit> myArmyCopy;
        ArrayList<ArrayList<Unit>> myArmySquadsCopy;
        ArrayList<Unit> enemyArmyCopy;
        ArrayList<ArrayList<Unit>> enemyArmySquadsCopy;
        while (battleContinues) {
            myArmyCopy = new ArrayList<>(myHero.getArmy());
            myArmySquadsCopy = new ArrayList<>(myHero.getArmySquads());
            enemyArmyCopy = new ArrayList<>(enemyHero.getArmy());
            enemyArmySquadsCopy = new ArrayList<>(enemyHero.getArmySquads());
            if (!myHero.getArmy().isEmpty() || !myHero.getArmySquads().isEmpty()) {
                for (Unit u : myHero.getArmy()) {
                    prevLook = u.getLookName();
                    u.setLookName("\u001B[36m" + "\u001B[41m" + " X " + "\u001B[0m");
                    battlefield.show();
                    unitCanMove = !(u.getHealth() <= 0);
                    System.out.println("===========ХОД АРМИИ ГЕРОЯ " + myHero.getName() + "===========");
                    System.out.println("Ходит " + u.getName() + "!");
                    moveParametersReport(u);
                    while (unitCanMove) {
                        if (u.getType() == 2){
                            Crossbowman temp = new Crossbowman(u);
                            System.out.println("Доступна дальнобойная атака!");
                            System.out.println("Применить?\n0 - да\n1 - нет");
                            if (scanner.hasNextInt() && scanner.nextInt()==0){
                               temp.doRangedAttack(battlefield, myHero, enemyHero);
                            }
                        }
                        if (scanner.hasNextInt() && u.getHealth() > 0) {
                            moveResult = moveOperator.move(u, scanner.nextInt(), true);
                            battleAftermath = analyzeUnitMoveResult(u, moveResult, battlefield, myHero, enemyHero);
                            modifyCopies(battleAftermath, myArmyCopy, enemyArmyCopy,  myArmySquadsCopy, enemyArmySquadsCopy);
                            unitCanMove = battleAftermath.isUnitCanMove();
                            if (u.getMaxMoveCount() != u.getMoveCounter() && u.getHealth() > 0) {
                                moveParametersReport(u);
                            }
                            if (entityMoved(moveResult.getCode()))
                                battlefield.show();
                        }
                        if (u.getHealth() <=0)
                            unitCanMove = false;
                    }
                    u.setLookName(prevLook);
                }
                for (ArrayList<Unit> s : myHero.getArmySquads()) {
                    System.out.println("===========ХОД ОТРЯДОВ АРМИИ ГЕРОЯ " + myHero.getName() + "===========");
                    prevLook = s.get(0).getLookName();
                    s.get(0).setLookName("\u001B[36m" + "\u001B[41m" + " X " + "\u001B[0m");
                    battlefield.show();
                    squadCanMove = !(s.get(0).getHealth()<=0);
                    while (squadCanMove) {
                        if (scanner.hasNextInt()) {
                            moveResult = moveOperator.moveSquad(s, scanner.nextInt(), true);
                            battleAftermath = analyzeSquadMoveResult(s, moveResult, battlefield, myHero, enemyHero);
                            modifyCopies(battleAftermath, myArmyCopy, enemyArmyCopy, myArmySquadsCopy,  enemyArmySquadsCopy);
                            squadCanMove = battleAftermath.isUnitCanMove();
                            if (s.get(0).getMaxMoveCount() != s.get(0).getMoveCounter()) {
                                moveParametersReport(s.get(0));
                            }
                            if (entityMoved(moveResult.getCode()))
                                battlefield.show();
                        }
                        if (s.get(0).getHealth() <=0)
                            squadCanMove = false;
                    }
                    s.get(0).setLookName(prevLook);
                }
                myHero.setArmy(myArmyCopy);
                myHero.setArmySquads(myArmySquadsCopy);
                enemyHero.setArmy(enemyArmyCopy);
                enemyHero.setArmySquads(enemyArmySquadsCopy);
                for (Unit u : myHero.getArmy()) {
                    u.setMoveCounter(0);
                    updateReloadTimer(u);
                    if (player.getOwnedCastle().isStableBuilt())
                        u.setMoveCounter(-1);
                    u.setMovePowerCounter(u.getMaxMovePower());
                }
                for (ArrayList<Unit> s : myHero.getArmySquads()) {
                    for (Unit u : s) {
                        u.setMoveCounter(0);
                        updateReloadTimer(u);
                        if (player.getOwnedCastle().isStableBuilt())
                            u.setMoveCounter(-1);
                        u.setMovePowerCounter(u.getMaxMovePower());
                    }
                }

                //enemy-----------------------------------------------------------------------------------------------------------------------------------------------------------
                for (Unit u : enemyHero.getArmy()) {
                    unitCanMove = !(u.getHealth() <= 0);
                    System.out.println("===========ХОД АРМИИ ВРАГА===========");
                    System.out.println("Ходит " + u.getName() + "!");
                    while (unitCanMove) {
                        if (u.getHealth() > 0) {
                            moveResult = moveOperator.move(u, unitDirectionSelector(u.getXPos(), u.getYPos(), battlefield), true);
                            battleAftermath = analyzeUnitMoveResult(u, moveResult, battlefield, myHero, enemyHero);
                            modifyCopies(battleAftermath, myArmyCopy, enemyArmyCopy,  myArmySquadsCopy, enemyArmySquadsCopy);
                            unitCanMove = battleAftermath.isUnitCanMove();
                        }
                        if (u.getHealth() <=0)
                            unitCanMove = false;
                    }
                }
                for (ArrayList<Unit> s : myHero.getArmySquads()) {
                    System.out.println("===========ХОД ОТРЯДОВ АРМИИ ВРАГА===========");
                    squadCanMove = !(s.get(0).getHealth()<=0);
                    while (squadCanMove) {
                        moveResult = moveOperator.moveSquad(s, unitDirectionSelector(s.get(0).getXPos(), s.get(0).getYPos(), battlefield), true);
                        battleAftermath = analyzeSquadMoveResult(s, moveResult, battlefield, myHero, enemyHero);
                        modifyCopies(battleAftermath, myArmyCopy, enemyArmyCopy, myArmySquadsCopy,  enemyArmySquadsCopy);
                        squadCanMove = battleAftermath.isUnitCanMove();
                        if (s.get(0).getHealth() <=0)
                            squadCanMove = false;
                    }
                }
                myHero.setArmy(myArmyCopy);
                myHero.setArmySquads(myArmySquadsCopy);
                enemyHero.setArmy(enemyArmyCopy);
                enemyHero.setArmySquads(enemyArmySquadsCopy);
                for (Unit u : enemyHero.getArmy()) {
                    u.setMoveCounter(0);
                    u.setMovePowerCounter(u.getMaxMovePower());
                }
                for (ArrayList<Unit> s : enemyHero.getArmySquads()) {
                    for (Unit u : s) {
                        u.setMoveCounter(0);
                        u.setMovePowerCounter(u.getMaxMovePower());
                    }
                }
                battlefield.show();
                if (myHero.getArmy().isEmpty() && myHero.getArmySquads().isEmpty() || enemyHero.getArmy().isEmpty() && enemyHero.getArmySquads().isEmpty())
                    battleContinues = false;
            }
        }
        myHero.setXPos(mpx);
        myHero.setYPos(mpy);
        enemyHero.setXPos(epx);
        enemyHero.setYPos(epy);
        return getBattleMessage(myHero, battleCommited);
    }

    private static void updateReloadTimer(Unit u) {
        u.setReloadTimer(u.getReloadTimer()-1);
        if (u.getReloadTimer() < 0)
            u.setReloadTimer(0);
    }

    private static Message getBattleMessage(Hero myHero, boolean battleCommited) {
        Message battleResult;
        if (battleCommited) {
            if (myHero.getArmy().isEmpty() && myHero.getArmySquads().isEmpty()) {
                battleResult = new Message(50, null, null, null);
            } else {
                battleResult = new Message(51, null, null, null);
            }
        }
        else {
            if (myHero.getArmy().isEmpty() && myHero.getArmySquads().isEmpty()) {
                battleResult = new Message(52, null, null, null);
            } else {
                battleResult = new Message(53, null, null, null);
            }
        }
        return battleResult;
    }

    private static int unitDirectionSelector(int x, int y, Field field){
        int direction = 5;
        int cropY = y-1;
        if (cropY<0)
            cropY = 0;
        if (field.getCell(x, cropY).getOccupant()==null || field.getCell(0, 0).getOccupant()==null ) {
            if (y > 0) {
                direction = 8;
            } else {
                if (x > 0)
                    direction = 4;
            }
        }
        return direction;
    }
}
