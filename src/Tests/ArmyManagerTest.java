package Tests;

import Entities.Hero;
import Entities.Unit;
import Entities.Units.Spearman;
import GameLogic.ArmyManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmyManagerTest {
    Hero h;
    ArmyManager testArmyManager;
    ArrayList<Entities.Unit> testArmy;
    ArrayList<ArrayList<Entities.Unit>> testArmySquads;
    ByteArrayOutputStream outputStream;
    String consoleOutput;
    Unit a, b, c, d, e;
    Spearman s, s1;


    @BeforeEach()
    public void initTest(){
        h = new Hero();
        testArmy = new ArrayList<>();
        testArmySquads = new ArrayList<>();
        testArmyManager = new ArmyManager(h);
        h.setArmy(testArmy);
        h.setArmySquads(testArmySquads);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        a = new Entities.Unit();
        b = new Entities.Unit();
        c = new Entities.Unit();
        d = new Entities.Unit();
        e = new Entities.Unit();
        s = new Spearman();
        s1 = new Spearman();
        testArmy.add(a);
        testArmy.add(b);
        testArmy.add(c);
        testArmy.add(d);
    }

    @AfterEach
    public void endTest(){
        outputStream.reset();
        System.setOut(System.out);
    }

    @Test
    public void formSquadTest(){
        testArmyManager.formSquad(0, 1);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Отряд сформирован!", consoleOutput);
    }

    @Test
    public void formSquad2Test(){
        testArmyManager.formSquad(1, 0);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Отряд сформирован!", consoleOutput);
    }

    @Test
    public void formSquad3Test(){
        testArmyManager.formSquad(0, 0);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Отряд нельзя формировать из одного и того же воина!", consoleOutput);
    }

    @Test
    public void formSquad4Test(){
        testArmy.add(s);
        outputStream.reset();
        testArmyManager.formSquad(0, 4);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Воинов разного типа соединять нельзя!", consoleOutput);
    }

    @Test
    public void growSquadTest(){
        testArmy.add(e);
        testArmyManager.formSquad(0,1);
        testArmyManager.formSquad(0,1);
        outputStream.reset();
        testArmyManager.addUnitToSquad(0, 0);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Воин добавлен в отряд!", consoleOutput);
    }

    @Test
    public void growSquad2Test(){
        testArmy.add(e);
        testArmyManager.formSquad(0,1);
        testArmyManager.formSquad(0,1);
        outputStream.reset();
        testArmyManager.addUnitToSquad(0, 0);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Воин добавлен в отряд!", consoleOutput);
        outputStream.reset();
        testArmy.add(s);
        testArmyManager.addUnitToSquad(0, 0);
        consoleOutput = outputStream.toString().trim();
        assertEquals("В отряд нельзя добавлять воина другого типа!", consoleOutput);
    }

    @Test
    public void mergeSquadsTest(){
        testArmyManager.formSquad(0,1);
        testArmyManager.formSquad(0,1);
        outputStream.reset();
        testArmyManager.mergeSquads(0, 1);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Отряды объединены!", consoleOutput);
    }

    @Test
    public void mergeSquads2Test(){
        testArmyManager.formSquad(0,1);
        testArmyManager.formSquad(0,1);
        testArmy.add(s);
        testArmy.add(s1);
        testArmyManager.formSquad(0,1);
        outputStream.reset();
        testArmyManager.mergeSquads(0,2);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Нельзя соединять отряды разного типа!", consoleOutput);
    }

    @Test
    public void destroySquadTest(){
        testArmyManager.formSquad(0,1);
        outputStream.reset();
        testArmyManager.destroySquad(0);
        consoleOutput = outputStream.toString().trim();
        assertEquals("Отряд расформирован!", consoleOutput);
    }
}
