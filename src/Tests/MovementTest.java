package Tests;

import Cells.Cell;
import Cells.WorldCells.Castle;
import Entities.Entity;
import Entities.Objects.GoldPack;
import Entities.Units.Spearman;
import Fields.Battlefields.PlainsField;
import GameLogic.FieldEditor;
import GameLogic.Message;
import GameLogic.Movement;
import GameLogic.Tunnel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MovementTest {
    ByteArrayInputStream inputStream;
    Movement testMovement;
    PlainsField testField;
    Method testCheckCell;
    Method testMove;
    int checkCellResult;
    int direction = 2;
    String battleDecision="1";
    Message moveResult;
    Entity e2;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        testField = new PlainsField();
        testMovement = new Movement(testField);
        testMove = testMovement.getClass().getDeclaredMethod("move", Entity.class, int.class, boolean.class);
        testCheckCell = testMovement.getClass().getDeclaredMethod("checkCell", Cell.class);
        testCheckCell.setAccessible(true);
    }

    @Test
    public void checkCellTest1() throws InvocationTargetException, IllegalAccessException {
        checkCellResult = (int) testCheckCell.invoke(testMovement, testField.getCell(0,0));
        assertEquals(0, checkCellResult);
    }

    @Test
    public void checkCellTest2() throws InvocationTargetException, IllegalAccessException {
        testField.getCell(0,0).setOccupant(new GoldPack(0));
        checkCellResult = (int) testCheckCell.invoke(testMovement, testField.getCell(0,0));
        assertEquals(3, checkCellResult);
    }

    @Test
    public void checkCellTest3() throws InvocationTargetException, IllegalAccessException {
        Entity e = new Entity();
        e.setIsEnemy(true);
        testField.getCell(0,0).setOccupant(e);
        checkCellResult = (int) testCheckCell.invoke(testMovement, testField.getCell(0,0));
        assertEquals(2, checkCellResult);
    }

    @Test
    public void checkCellTest4() throws InvocationTargetException, IllegalAccessException {
        testField.getCell(0,0).setTunnel(new Tunnel(0,0,0,0));
        testField.getCell(0,0).setOccupant(null);
        checkCellResult = (int) testCheckCell.invoke(testMovement, testField.getCell(0,0));
        assertEquals(6, checkCellResult);
    }

    @Test
    public void checkCellTest5() throws InvocationTargetException, IllegalAccessException {
        testField.setCell(0,0, new Castle(false, 0,0));
        checkCellResult = (int) testCheckCell.invoke(testMovement, testField.getCell(0,0));
        assertEquals(1, checkCellResult);
    }

    @Test
    public void checkCellTest6() throws InvocationTargetException, IllegalAccessException {
        testField.setCell(0,0, new Castle(true, 0,0));
        checkCellResult = (int) testCheckCell.invoke(testMovement, testField.getCell(0,0));
        assertEquals(5, checkCellResult);
    }

    @Test
    public void shiftTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method testShift;
        testShift = testMovement.getClass().getDeclaredMethod("shift", Entity.class, int.class, int.class);
        Entity e = new Entity();
        testField.getCell(0,0).setOccupant(e);
        e.setYPos(0);
        e.setXPos(0);
        testShift.invoke(testMovement, e, 1,1);
        assertEquals(1, e.getXPos());
        assertEquals(1, e.getYPos());
        assertNull(testField.getCell(0, 0).getOccupant());
        assertEquals(e, testField.getCell(1,1).getOccupant());
    }
    @AfterEach
    public void endTests(){
        testField = new PlainsField();
    }

    @BeforeEach
    public void initMoveTests(){
        testField = new PlainsField();
        e2 = new Entity();
        e2.setMoveCounter(0);
        e2.setMaxMoveCount(50);
        e2.setMovePowerCounter(20);
        e2.setMaxMovePower(20);
        e2.setIsEnemy(false);
        FieldEditor fe = new FieldEditor(testField);
        fe.placeEntity(e2,0,0);
        direction = 2;
    }

    @Test
    public void moveTest0() throws InvocationTargetException, IllegalAccessException {
        e2.setMoveCounter(0);
        e2.setMaxMoveCount(50);
        e2.setMovePowerCounter(10);
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(0, moveResult.getCode());
        assertEquals(2, moveResult.getExtraData());
    }

    @Test
    public void moveTest1() throws InvocationTargetException, IllegalAccessException {
        testField.setCell(0,1, new Castle(false,0,1));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(1, moveResult.getCode());
        assertEquals(0, moveResult.getData());
        assertEquals(1, moveResult.getExtraData());
    }

    @Test
    public void moveTest1ver2() throws InvocationTargetException, IllegalAccessException {
        e2.setIsEnemy(true);
        testField.setCell(0,1, new Castle(false,0,1));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(1, moveResult.getCode());
        assertEquals(1, moveResult.getData());
        assertEquals(1, moveResult.getExtraData());
    }

    @Test
    public void moveTest1ver3() throws InvocationTargetException, IllegalAccessException {
        e2.setIsEnemy(true);
        testField.setCell(0,1, new Castle(true,0,1));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(1, moveResult.getCode());
        assertEquals(1, moveResult.getData());
        assertEquals(1, moveResult.getExtraData());
    }

    @Test
    public void moveTest2() throws InvocationTargetException, IllegalAccessException {
        battleDecision = "1";
        inputStream = new ByteArrayInputStream(battleDecision.getBytes());
        System.setIn(inputStream);
        testField.getCell(0,1).setOccupant(new Spearman(true, 100));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        inputStream.readAllBytes();
        assertEquals(2, moveResult.getCode());
        assertEquals(0, moveResult.getData());
        assertEquals(1, moveResult.getAddData());
        assertEquals(2, moveResult.getExtraData());
        inputStream.reset();
        System.setIn(System.in);
    }

    @Test
    public void moveTest3() throws InvocationTargetException, IllegalAccessException {
        testField.getCell(0,1).setOccupant(new GoldPack(10));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(3, moveResult.getCode());
        assertEquals(10, moveResult.getData());
        assertEquals(2, moveResult.getExtraData());
    }

    @Test
    public void moveTest4() throws InvocationTargetException, IllegalAccessException {
        battleDecision = "0";
        inputStream = new ByteArrayInputStream(battleDecision.getBytes());
        System.setIn(inputStream);
        testField.getCell(0,1).setOccupant(new Spearman(true, 100));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        inputStream.readAllBytes();
        assertEquals(4, moveResult.getCode());
        inputStream.reset();
        System.setIn(System.in);
    }

    @Test
    public void moveTest5() throws InvocationTargetException, IllegalAccessException {
        testField.setCell(0,1, new Castle(true,0,1));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(5, moveResult.getCode());
        assertEquals(0, moveResult.getData());
        assertEquals(1, moveResult.getExtraData());
    }

    @Test
    public void moveTest6() throws InvocationTargetException, IllegalAccessException {
        testField.getCell(0,1).setTunnel(new Tunnel(1,2,0,0));
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(6, moveResult.getCode());
        assertEquals(0, moveResult.getData());
        assertEquals(0, moveResult.getAddData());
        assertEquals(2, moveResult.getExtraData());
    }

    @Test
    public void moveTest7() throws InvocationTargetException, IllegalAccessException {
        direction = 5;
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(7, moveResult.getCode());
        assertEquals(2, moveResult.getData());
    }

    @Test
    public void moveTest8() throws InvocationTargetException, IllegalAccessException {
        e2.setMoveCounter(0);
        e2.setMaxMoveCount(15);
        e2.setMovePowerCounter(0);
        e2.setMaxMovePower(20);
        moveResult = (Message) testMove.invoke(testMovement, e2, direction, false);
        assertEquals(8, moveResult.getCode());
    }

    @AfterAll
    public static void endMoveTest(){

    }
}
