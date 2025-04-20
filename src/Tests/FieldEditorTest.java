package Tests;

import Entities.Entity;
import Entities.Unit;
import Fields.Battlefields.PlainsField;
import Fields.Field;
import GameLogic.FieldEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldEditorTest {
    Field f;
    FieldEditor fe;

    @BeforeEach
    public void initTest(){
        f = new PlainsField();
        fe = new FieldEditor(f);
    }

    @Test
    public void placeEntityTest(){
        Entity e = new Entity();
        fe.placeEntity(e, 1 ,1);
        assertEquals(1, e.getXPos());
        assertEquals(1, e.getYPos());
        assertEquals(e, f.getCell(1,1).getOccupant());
    }

    @Test
    public void placeSquadTest(){
        ArrayList<Unit> s = new ArrayList<>();
        Unit u = new Unit();
        s.add(u);
        fe.placeSquad(s, 1, 1);
        assertEquals(1, s.get(0).getXPos());
        assertEquals(1, s.get(0).getYPos());
        assertEquals(s, f.getCell(1,1).getSquadOccupant());
    }
}
