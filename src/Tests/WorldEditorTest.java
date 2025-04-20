package Tests;

import Entities.Entity;
import Fields.Worlds.World;
import GameLogic.WorldEditor;
import GameLogic.WorldGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WorldEditorTest {
    World w = new World();
    WorldGenerator wg = new WorldGenerator(w);
    WorldEditor test = new WorldEditor(w);

    @BeforeEach
    public void generate(){
        wg.generatePreDefinedWorld();
    }

    @Test
    public void placeEntityTest(){
        Entity testEn = new Entity();
        test.placeEntity(testEn, 4,4);
        assertEquals(4, testEn.getXPos());
        assertEquals(4, testEn.getYPos());
        assertEquals(testEn, w.getCell(4,4).getOccupant());
    }

    @Test
    public void moveEntityToCoordsTest(){
        Entity testEn = new Entity();
        test.placeEntity(testEn, 4,4);
        test.moveEntityToCoords(testEn, 6,6);
        assertEquals(6, testEn.getXPos());
        assertEquals(6, testEn.getYPos());
        assertNull(w.getCell(4, 4).getOccupant());
        assertEquals(testEn, w.getCell(6,6).getOccupant());
    }

    @Test
    public void removeEntityTest(){
        Entity testEn = new Entity();
        test.placeEntity(testEn, 4,4);
        test.removeEntity(testEn);
        assertNull(w.getCell(4, 4).getOccupant());
    }
}
