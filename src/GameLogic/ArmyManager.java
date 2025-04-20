package GameLogic;

import Entities.Hero;
import Entities.Unit;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ArmyManager {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(ArmyManager.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Hero armyOwner;

    public ArmyManager(Hero armyOwner){
        this.armyOwner = armyOwner;
    }

    public void formSquad (int firstTarget, int secondTarget){
        firstTarget = crop(firstTarget, armyOwner.getArmy().size());
        secondTarget = crop(secondTarget, armyOwner.getArmy().size());
        Unit a = armyOwner.getArmy().get(firstTarget);
        Unit b = armyOwner.getArmy().get(secondTarget);
        if (a.getType() == b.getType() && firstTarget != secondTarget){
            ArrayList<Unit> squad = new ArrayList<>();
            a.setInSquad(true);
            b.setInSquad(true);
            squad.add(a);
            squad.add(b);
            armyOwner.getArmySquads().add(squad);
            armyOwner.getArmy().remove(firstTarget);
            if (firstTarget < secondTarget)
                armyOwner.getArmy().remove(secondTarget-1);
            else
                armyOwner.getArmy().remove(secondTarget);
            System.out.println("Отряд сформирован!");
        }
        else if (firstTarget == secondTarget) {
            System.out.println("Отряд нельзя формировать из одного и того же воина!");
            LOGGER.log(Level.WARNING, "Попытка создать отряд из одного воина");
        }
        else {
            System.out.println("Воинов разного типа соединять нельзя!");
            LOGGER.log(Level.WARNING, "Попытка создать отряд из разных видов воинов");
        }
    }

    public void addUnitToSquad (int unitIndex, int squadIndex){
        unitIndex = crop(unitIndex, armyOwner.getArmy().size());
        squadIndex = crop(squadIndex, armyOwner.getArmySquads().size());
        Unit unit = armyOwner.getArmy().get(unitIndex);
        ArrayList<Unit> squad = armyOwner.getArmySquads().get(squadIndex);
        if (unit.getType() == squad.get(0).getType()){
            unit.setInSquad(true);
            squad.add(unit);
            armyOwner.getArmy().remove(unitIndex);
            System.out.println("Воин добавлен в отряд!");
        }
        else {
            System.out.println("В отряд нельзя добавлять воина другого типа!");
            LOGGER.log(Level.WARNING, "Попытка создать отряд из разных видов воинов");
        }
    }

    public void mergeSquads(int firstSquadIndex, int secondSquadIndex){
        firstSquadIndex = crop(firstSquadIndex, armyOwner.getArmySquads().size());
        secondSquadIndex = crop(secondSquadIndex, armyOwner.getArmySquads().size());
        ArrayList<Unit> squad1 = armyOwner.getArmySquads().get(firstSquadIndex);
        ArrayList<Unit> squad2 = armyOwner.getArmySquads().get(secondSquadIndex);
        if (squad1.get(0).getType() == squad2.get(0).getType()){
            squad1.addAll(squad2);
            squad2.clear();
            squad2 = null;
            System.out.println("Отряды объединены!");
        }
        else {
            System.out.println("Нельзя соединять отряды разного типа!");
            LOGGER.log(Level.WARNING, "Попытка создать отряд из разных видов воинов");
        }
    }

    public void destroySquad(int squadIndex){
        squadIndex = crop(squadIndex, armyOwner.getArmySquads().size());
        ArrayList<Unit> squad = armyOwner.getArmySquads().get(squadIndex);
        for (Unit u : squad)
            u.setInSquad(false);
        armyOwner.getArmy().addAll(squad);
        armyOwner.getArmySquads().remove(squad);
        System.out.println("Отряд расформирован!");
    }

    public void showArmy(){
        System.out.println("-----------Армия героя " + armyOwner.getName()+ "-----------" + "\nНомер | Имя | Здоровье");
        int num = 0;
        for (Unit u : armyOwner.getArmy()) {
            String toPrint = num + "    " + u.getName() + "    " + u.getHealth() + "/" + u.getMAX_HEALTH();
            System.out.println(toPrint);
            num++;
        }
    }

    public void showSquads(){
        System.out.println("-----Отряды внутри этой армии-----\nНомер | Имя | Здоровье");
        for (ArrayList<Unit> s : armyOwner.getArmySquads()){
            if (!s.isEmpty())
                System.out.println("ОТРЯД " + armyOwner.getArmySquads().indexOf(s));
            int num = 0;
            for (Unit u : s) {
                String toPrint = num + "    " + u.getName() + "    " + u.getHealth() + "/" + u.getMAX_HEALTH();
                System.out.println(toPrint);
                num++;
            }
        }
    }

    private int crop(int cropped, int size){
        if (cropped < 0){
            cropped = 0;
            LOGGER.log(Level.SEVERE, "Выход за границы армии в отрицательные индексы");
        }
        if (cropped >= size && size != 0){
            cropped = size - 1;
            LOGGER.log(Level.SEVERE, "Выход за границы армии в индексы, превышающие размер массива");
        }
        else if (size == 0)
            cropped = 0;
        return cropped;
    }
}
