package GameLogic;

import Entities.Entity;
import Fields.Worlds.World;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WorldEditor {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(WorldEditor.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static World worldBeingEdited;

    public WorldEditor(World world){
        worldBeingEdited = world;
    }

    public void placeEntity(Entity entity, int targetX, int targetY){
        entity.setXPos(targetX);
        entity.setYPos(targetY);
        worldBeingEdited.getCell(targetX, targetY).setOccupant(entity);
        LOGGER.log(Level.INFO, "Сущность " + entity.getName() + " размещена по координатам (" + entity.getXPos() + " ; " + entity.getYPos()+")");
    }

    public void moveEntityToCoords(Entity entity, int targetX, int targetY){
        worldBeingEdited.getCell(entity.getXPos(), entity.getYPos()).setOccupant(null);
        entity.setXPos(targetX);
        entity.setYPos(targetY);
        worldBeingEdited.getCell(targetX, targetY).setOccupant(entity);
        LOGGER.log(Level.INFO, "Сущность " + entity.getName() + " смещена на координаты (" + entity.getXPos() + " ; " + entity.getYPos()+")");
    }

    public void removeEntity(Entity entity){
        worldBeingEdited.getCell(entity.getXPos(), entity.getYPos()).setOccupant(null);
        LOGGER.log(Level.INFO, "Сущность " + entity.getName() + " убрана с поля");
        entity = null;
    }
}
