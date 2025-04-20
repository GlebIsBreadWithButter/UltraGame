package Tests;

import Cells.WorldCells.Castle;
import GameLogic.Player;
import GameLogic.Texts;
import GameLogic.Tunnel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerTest {
    Player p;
    ByteArrayOutputStream outputStream;
    ByteArrayInputStream inputStream;
    String heroName = "test";

    @BeforeEach
    public void initTest(){
        p = new Player(false);
        Castle c = new Castle(false, 0,0);
        p.setOwnedCastle(c);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        inputStream = new ByteArrayInputStream(heroName.getBytes());
        System.setIn(inputStream);
    }

    @AfterEach
    public void endTest(){
        outputStream.reset();
        System.setOut(System.out);
        inputStream.reset();
        System.setIn(System.in);
    }

    @Test
    public void updateWormTimerTest(){
        Tunnel t = new Tunnel(3, 2, 0,0);
        p.getTunnels().add(t);
        p.updateWormTimer();
        assertEquals(2, t.getTimer());
        assertEquals(2, p.getTunnels().get(0).getTimer());
    }

    @Test
    public void buyHeroFail1Test(){
        p.buyHero(1, 50, p.getOwnedCastle());
        String consoleOutput = outputStream.toString().trim();
        assertEquals(Texts.getNotEnoughGoldText(), consoleOutput);
    }

    @Test
    public void buyHeroFail2Test(){
        p.buyHero(50, 50, p.getOwnedCastle());
        String consoleOutput = outputStream.toString().trim();
        assertEquals("Такого героя нет в продаже!", consoleOutput);
    }

    @Test
    public void buyHeroPassTest(){
        p.setBankGoldAmount(5000);
        p.buyHero(1,50, p.getOwnedCastle());
        inputStream.readAllBytes();
        String consoleOutput = outputStream.toString().trim();
        assertEquals("Назовите своего первого героя...\r\ntest - новый герой!", consoleOutput);
        assertNotNull(p.getHeroes().get(0));
    }
}
