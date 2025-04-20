package Tests;

import Cells.Cell;
import Entities.Hero;
import Entities.Heroes.StartHero;
import Entities.Units.Crossbowman;
import Entities.Units.Paladin;
import Entities.Units.Spearman;
import Fields.Battlefields.PlainsField;
import Fields.Field;
import GameLogic.FieldEditor;
import GameLogic.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CrossbowmanTest {
    PlainsField f;
    Crossbowman c;
    ByteArrayOutputStream outputStream;
    FieldEditor fe;
    Method testScanForEnemy;

    @BeforeEach
    public void initTest() throws NoSuchMethodException {
        f = new PlainsField();
        c = new Crossbowman(false, 124);
        fe = new FieldEditor(f);
        fe.placeEntity(c, 1, 1);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        testScanForEnemy = c.getClass().getDeclaredMethod("scanForEnemy", Field.class);
        testScanForEnemy.setAccessible(true);
    }

    @AfterEach
    public void endTest(){
        outputStream.reset();
        System.setOut(System.out);
    }

    @Test
    public void scanForEnemyTest1() throws InvocationTargetException, IllegalAccessException {
        Message res;
        res = (Message) testScanForEnemy.invoke(c, f);
        assertEquals(0, res.getCode());
    }

    @Test
    public void scanForEnemyTest2() throws InvocationTargetException, IllegalAccessException {
        Message res;
        fe.placeEntity(new Spearman(true, 100), 0,0);
        res = (Message) testScanForEnemy.invoke(c, f);
        assertEquals(1, res.getCode());
    }

    @Test
    public void scanForEnemyTest3() throws InvocationTargetException, IllegalAccessException {
        Message res;
        fe.placeEntity(new Spearman(true, 100), 0,2);
        res = (Message) testScanForEnemy.invoke(c, f);
        assertEquals(1, res.getCode());
    }

    @Test
    public void scanForEnemyTest4() throws InvocationTargetException, IllegalAccessException {
        Message res;
        fe.placeEntity(new Spearman(true, 100), 2,0);
        res = (Message) testScanForEnemy.invoke(c, f);
        assertEquals(1, res.getCode());
    }

    @Test
    public void scanForEnemyTest5() throws InvocationTargetException, IllegalAccessException {
        Message res;
        fe.placeEntity(new Spearman(true, 100), 2,2);
        res = (Message) testScanForEnemy.invoke(c, f);
        assertEquals(1, res.getCode());
    }

    @Test
    public void doRangedAttackTest1(){
        Spearman s = new Spearman(true, 100);
        fe.placeEntity(s,2,2);
        Hero en = new Hero();
        en.setArmy(new ArrayList<>());
        en.getArmy().add(s);
        Hero h = new Hero();
        c.doRangedAttack(f, h, en);
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("Попадание"));
        assertEquals(s.getMAX_HEALTH() - c.getRangedAttackDamageAmount(), s.getHealth());
    }

    @Test
    public void doRangedAttackTest2(){
        Spearman s = new Spearman(true, 100);
        s.setHealth(1);
        fe.placeEntity(s,2,2);
        Hero en = new Hero();
        en.setArmy(new ArrayList<>());
        en.getArmy().add(s);
        Hero h = new Hero();
        c.doRangedAttack(f, h, en);
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("уничтожает"));
        assertNull(f.getCell(2, 2).getOccupant());
    }

    @Test
    public void doRangedAttackTest3(){
        Hero en = new Hero();
        Hero h = new Hero();
        c.doRangedAttack(f, h, en);
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("не найден"));
    }
}
