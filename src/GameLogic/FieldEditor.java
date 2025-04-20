package GameLogic;

import Entities.Entity;
import Entities.Unit;
import Fields.Field;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FieldEditor {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(FieldEditor.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field fieldBeingEdited;

    public FieldEditor(Field field){
        fieldBeingEdited = field;
    }

    public void placeEntity(Entity entity, int targetX, int targetY){
        entity.setXPos(targetX);
        entity.setYPos(targetY);
        fieldBeingEdited.getCell(targetX, targetY).setOccupant(entity);
        LOGGER.log(Level.INFO, "Сущность " + entity.getName() + " размещена по координатам (" + entity.getXPos() + " ; " + entity.getYPos()+")");
    }

    public void placeSquad(ArrayList<Unit> squad, int targetX, int targetY){
        squad.get(0).setXPos(targetX);
        squad.get(0).setYPos(targetY);
        for (Entity e : squad){
            e.setXPos(targetX);
            e.setYPos(targetY);
        }
        fieldBeingEdited.getCell(targetX, targetY).setSquadOccupant(squad);
        LOGGER.log(Level.INFO, "Отряд сущностей размещён по координатам (" + squad.get(0).getXPos() + " ; " + squad.get(0).getYPos()+")");
    }
}
