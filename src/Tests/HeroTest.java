package Tests;

import Cells.WorldCells.Castle;
import Entities.Hero;
import Entities.Unit;
import GameLogic.Texts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {
    Hero h;
    ByteArrayOutputStream outputStream;
    String unitName = "test";

    @BeforeEach
    public void initTest(){
        h = new Hero();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void endTest(){
        outputStream.reset();
        System.setOut(System.out);
    }

    @Test
    public void buyUnitFail1Test(){
        Castle c = new Castle(false,0,0);
        c.setBarracksBuilt(true);
        h.setHome(c);
        h.buyUnit(1, 1500, new Castle(false, 0,0));
        String consoleOutput = outputStream.toString().trim();
        assertEquals(Texts.getNotEnoughGoldText(), consoleOutput);
    }

    @Test
    public void buyUnitFail2Test(){
        h.buyUnit(1500, 1500, new Castle(false, 0,0));
        String consoleOutput = outputStream.toString().trim();
        assertEquals("Такого воина нет в продаже!", consoleOutput);
    }

    @Test
    public void buyUnitPassTest(){
        h.setGoldAmount(1000);
        Castle c = new Castle(false,0,0);
        c.setBarracksBuilt(true);
        h.setHome(c);
        h.setArmy(new ArrayList<>());
        h.buyUnit(1, 1500, new Castle(false, 0,0));
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("вступает в Вашу армию!"));
        assertNotNull(h.getArmy().get(0));
    }

    @Test
    public void buyCastleUpgradeFail1Test(){
        h.buyCastleUpgrade(1, new Castle(false, 0,0));
        String consoleOutput = outputStream.toString().trim();
        assertEquals(Texts.getNotEnoughGoldText(), consoleOutput);
    }

    @Test
    public void buyCastleUpgradeFail2Test(){
        h.buyCastleUpgrade(1500, new Castle(false, 0,0));
        String consoleOutput = outputStream.toString().trim();
        assertEquals("Такого улучшения замка не существует!", consoleOutput);
    }

    @Test
    public void buyCastleUpgradeFail3Test(){
        h.setGoldAmount(10000);
        Castle c = new Castle(false,0,0);
        c.setTowerBuilt(true);
        h.buyCastleUpgrade(1, c);
        String consoleOutput = outputStream.toString().trim();
        assertEquals("Это улучшение замка уже приобретено!", consoleOutput);

    }

    @Test
    public void buyCastleUpgradePassTest() {
        h.setGoldAmount(1000);
        Castle c = new Castle(false, 0, 0);
        h.buyCastleUpgrade(1, c);
        String consoleOutput = outputStream.toString().trim();
        assertEquals("Башня построена!", consoleOutput);
        assertTrue(c.isTowerBuilt());
    }
}
