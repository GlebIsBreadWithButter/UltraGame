package Tests;

import Entities.Hero;
import GameLogic.EnemyLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyLogicTest {
    Hero h;
    Integer direction;

    @BeforeEach
    public void initTest(){
        h = new Hero();
        direction = null;
    }

    @Test
    public void dirSelectorTest88(){
        h.setPos(8,8);
        direction = EnemyLogic.directionSelector(h);
        assertEquals(4, direction);
    }

    @Test
    public void dirSelectorTest08(){
        h.setPos(0,8);
        direction = EnemyLogic.directionSelector(h);
        assertEquals(8, direction);
    }

    @Test
    public void dirSelectorTest00(){
        h.setPos(0,0);
        direction = EnemyLogic.directionSelector(h);
        assertEquals(6, direction);
    }

    @Test
    public void dirSelectorTest55(){
        h.setPos(5,5);
        direction = EnemyLogic.directionSelector(h);
        assertEquals(2, direction);
    }

    @Test
    public void dirSelectorTest50(){
        h.setPos(5,0);
        direction = EnemyLogic.directionSelector(h);
        assertEquals(5, direction);
    }

    @Test
    public void dirSelectorTest10(){
        h.setPos(1,0);
        direction = EnemyLogic.directionSelector(h);
        assertEquals(6, direction);
    }
}
