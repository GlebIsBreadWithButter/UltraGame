package Tests;

import Cells.WorldCells.Castle;
import Entities.Entity;
import Entities.Hero;
import Entities.Heroes.StartHero;
import Entities.Unit;
import Entities.Units.Paladin;
import Entities.Units.Spearman;
import Fields.Battlefields.PlainsField;
import Fields.Field;
import GameLogic.AftermathMessage;
import GameLogic.BattleLogic;
import GameLogic.FieldEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static GameLogic.BattleLogic.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BattleLogicTest {
    PlainsField f;
    FieldEditor fe;
    BattleLogic bl;
    int x,y;
    Method testUnitDirectionSelector;
    ByteArrayOutputStream outputStream;
    ByteArrayInputStream inputStream;
    String decision;
    Hero mh = new Hero();
    Hero eh = new Hero();

    @BeforeEach
    public void initTest(){
        bl = new BattleLogic();
        f = new PlainsField();
        fe = new FieldEditor(f);
    }

    @Test
    public void placeUnitsTest(){
        Hero h = new StartHero("ff", false, 1, new Castle(false,0,0));
        Hero eh = new StartHero("rr", true, 2, new Castle(true,0,0));
        for (int i=0; i<6;i++)
            h.getArmy().add(new Spearman());
        for (int i=0; i<6;i++)
            eh.getArmy().add(new Spearman());
        BattleLogic.placeUnits(f, fe, h, eh);
        assertNotNull(f.getCell(0,1).getOccupant());
        assertNotNull(f.getCell(5,4).getOccupant());
    }

    @Test
    public void dealDamageTest1(){
        Unit u1 = new Spearman();
        Unit u2 = new Paladin();
        int temp = u2.getArmorDurability();
        BattleLogic.dealDamage(u1,u2, 0,0);
        assertEquals(u1.getMAX_HEALTH(), u1.getHealth());
        assertEquals(u2.getMAX_HEALTH() - 5, u2.getHealth());
        assertEquals(temp-1, u2.getArmorDurability());
    }

    @Test
    public void dealDamageTest2(){
        Unit u1 = new Spearman(false, 2);
        Unit u2 = new Paladin(false, 3);
        BattleLogic.dealDamage(u2,u1, 0,0);
        assertEquals(u1.getMAX_HEALTH() - 90, u1.getHealth());
        assertEquals(u2.getMAX_HEALTH(), u2.getHealth());
    }

    @BeforeEach
    public void initFightTests(){
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

    }

    @Test
    public void unitsFightTest1(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit e = new Spearman(true, 23);
        m.setHealth(1);
        e.setHealth(1);
        Hero mh = new Hero();
        Hero eh = new Hero();
        System.setIn(inputStream);
        AftermathMessage res = unitsFight(m,e,false, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(e, res.getEnemyUnitToBeRemoved());
    }

    @Test
    public void unitsFightTest2(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit e = new Spearman(true, 23);
        m.setHealth(1);
        e.setHealth(1);
        System.setIn(inputStream);
        AftermathMessage res = unitsFight(m,e,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(m, res.getMyUnitToBeRemoved());
    }

    @Test
    public void unitsFightTest3(){
        decision = "1";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit e = new Spearman(true, 23);
        m.setHealth(0);
        e.setHealth(0);
        System.setIn(inputStream);
        AftermathMessage res = unitsFight(m,e,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(m, res.getMyUnitToBeRemoved());
        assertEquals(e, res.getEnemyUnitToBeRemoved());
    }

    @Test
    public void unitSquadFightTest1(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit e = new Spearman(true, 23);
        Unit e1 = new Spearman(true, 2);
        m.setHealth(1);
        e.setHealth(1);
        e1.setHealth(0);
        ArrayList<Unit> es = new ArrayList<>();
        es.add(e);
        es.add(e1);
        System.setIn(inputStream);
        AftermathMessage res = unitSquadFight(m,es,false, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(es, res.getEnemySquadToBeRemoved());
    }

    @Test
    public void unitSquadFightTest2(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit e = new Spearman(true, 23);
        Unit e1 = new Spearman(true, 2);
        m.setHealth(1);
        e.setHealth(1);
        e1.setHealth(1);
        ArrayList<Unit> es = new ArrayList<>();
        es.add(e);
        es.add(e1);
        System.setIn(inputStream);
        AftermathMessage res = unitSquadFight(m,es,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(m, res.getMyUnitToBeRemoved());
    }

    @Test
    public void unitSquadFightTest3(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit e = new Spearman(true, 23);
        Unit e1 = new Spearman(true, 2);
        m.setHealth(0);
        e.setHealth(0);
        e1.setHealth(0);
        ArrayList<Unit> es = new ArrayList<>();
        es.add(e);
        es.add(e1);
        System.setIn(inputStream);
        AftermathMessage res = unitSquadFight(m,es,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(m, res.getMyUnitToBeRemoved());
        assertEquals(es, res.getEnemySquadToBeRemoved());
    }

    @Test
    public void squadUnitFightTest1(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit m1 = new Spearman(false, 32);
        Unit e = new Spearman(true, 23);
        m.setHealth(1);
        m1.setHealth(1);
        e.setHealth(0);
        ArrayList<Unit> ms = new ArrayList<>();
        ms.add(m);
        ms.add(m1);
        System.setIn(inputStream);
        AftermathMessage res = squadUnitFight(ms, e,false, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(e, res.getEnemyUnitToBeRemoved());
    }

    @Test
    public void squadUnitFightTest2(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit m1 = new Spearman(false, 32);
        Unit e = new Spearman(true, 23);
        m.setHealth(0);
        m1.setHealth(1);
        e.setHealth(1);
        ArrayList<Unit> ms = new ArrayList<>();
        ms.add(m);
        ms.add(m1);
        System.setIn(inputStream);
        AftermathMessage res = squadUnitFight(ms, e,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(ms, res.getMySquadToBeRemoved());
    }

    @Test
    public void squadUnitFightTest3(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit m1 = new Spearman(false, 32);
        Unit e = new Spearman(true, 23);
        m.setHealth(0);
        m1.setHealth(0);
        e.setHealth(0);
        ArrayList<Unit> ms = new ArrayList<>();
        ms.add(m);
        ms.add(m1);
        System.setIn(inputStream);
        AftermathMessage res = squadUnitFight(ms, e,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(e, res.getEnemyUnitToBeRemoved());
        assertEquals(ms, res.getMySquadToBeRemoved());
    }

    @Test
    public void squadsFightTest1(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit m1 = new Spearman(false, 32);
        Unit e = new Spearman(true, 23);
        Unit e1 = new Spearman(true, 2);
        m.setHealth(1);
        m1.setHealth(1);
        e.setHealth(1);
        e1.setHealth(0);
        ArrayList<Unit> es = new ArrayList<>();
        es.add(e);
        es.add(e1);
        ArrayList<Unit> ms = new ArrayList<>();
        ms.add(m);
        ms.add(m1);
        System.setIn(inputStream);
        AftermathMessage res = squadsFight(ms, es,false, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(es, res.getEnemySquadToBeRemoved());
    }

    @Test
    public void squadsFightTest2(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit m1 = new Spearman(false, 32);
        Unit e = new Spearman(true, 23);
        Unit e1 = new Spearman(true, 2);
        m.setHealth(1);
        m1.setHealth(0);
        e.setHealth(1);
        e1.setHealth(1);
        ArrayList<Unit> es = new ArrayList<>();
        es.add(e);
        es.add(e1);
        ArrayList<Unit> ms = new ArrayList<>();
        ms.add(m);
        ms.add(m1);
        System.setIn(inputStream);
        AftermathMessage res = squadsFight(ms, es,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(ms, res.getMySquadToBeRemoved());
    }

    @Test
    public void squadsFightTest3(){
        decision = "0";
        inputStream = new ByteArrayInputStream(decision.getBytes());
        Unit m = new Spearman(false, 1);
        Unit m1 = new Spearman(false, 32);
        Unit e = new Spearman(true, 23);
        Unit e1 = new Spearman(true, 2);
        m.setHealth(0);
        m1.setHealth(0);
        e.setHealth(0);
        e1.setHealth(0);
        ArrayList<Unit> es = new ArrayList<>();
        es.add(e);
        es.add(e1);
        ArrayList<Unit> ms = new ArrayList<>();
        ms.add(m);
        ms.add(m1);
        System.setIn(inputStream);
        AftermathMessage res = squadsFight(ms, es,true, f, mh, eh);
        inputStream.readAllBytes();
        assertEquals(es, res.getEnemySquadToBeRemoved());
        assertEquals(ms, res.getMySquadToBeRemoved());
    }

    @Test
    public void unitDirectionSelectorTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        testUnitDirectionSelector = bl.getClass().getDeclaredMethod("unitDirectionSelector", int.class, int.class, Field.class);
        testUnitDirectionSelector.setAccessible(true);
        x=5; y = 5;
        fe.placeEntity(new Entity(), 5,4);
        int res = (int) testUnitDirectionSelector.invoke(bl, x, y, f);
        assertEquals(8,res);
    }

    @Test
    public void unitDirectionSelectorTest2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        testUnitDirectionSelector = bl.getClass().getDeclaredMethod("unitDirectionSelector", int.class, int.class, Field.class);
        testUnitDirectionSelector.setAccessible(true);
        x=5; y = 0;
        fe.placeEntity(new Entity(), 5,4);
        int res = (int) testUnitDirectionSelector.invoke(bl, x, y, f);
        assertEquals(4,res);
    }

    @Test
    public void unitDirectionSelectorTest3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        testUnitDirectionSelector = bl.getClass().getDeclaredMethod("unitDirectionSelector", int.class, int.class, Field.class);
        testUnitDirectionSelector.setAccessible(true);
        x=5; y = 5;
        fe.placeEntity(new Entity(), 0,0);
        fe.placeEntity(new Entity(), 5,4);
        int res = (int) testUnitDirectionSelector.invoke(bl, x, y, f);
        assertEquals(5,res);
    }
}
