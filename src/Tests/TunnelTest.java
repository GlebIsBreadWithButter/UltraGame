package Tests;

import GameLogic.Tunnel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TunnelTest {
    Tunnel test = new Tunnel(5, 0,0,0);
    int x, y, direction;

    @Test
    public void calculationsTest1(){
        direction = 2;
        x = test.calculateExitX(direction, 5, 0);
        y = test.calculateExitY(direction, 5,0);
        assertEquals(0 , x);
        assertEquals(5, y);
    }

    @Test
    public void calculationsTest2(){
        direction = 1;
        x = test.calculateExitX(direction, 5, 0);
        y = test.calculateExitY(direction, 5,0);
        assertEquals(0, x);
        assertEquals(5, y);
    }

    @Test
    public void calculationsTest3(){
        direction = 4;
        x = test.calculateExitX(direction, 5, 0);
        y = test.calculateExitY(direction, 5,0);
        assertEquals(0 , x);
        assertEquals(0, y);
    }

    @Test
    public void calculationsTest4(){
        direction = 6;
        x = test.calculateExitX(direction, 5, 0);
        y = test.calculateExitY(direction, 5,0);
        assertEquals(5 , x);
        assertEquals(0, y);
    }

    @Test
    public void calculationsTest5(){
        direction = 3;
        x = test.calculateExitX(direction, 5, 0);
        y = test.calculateExitY(direction, 5,0);
        assertEquals(5 , x);
        assertEquals(5, y);
    }
}
