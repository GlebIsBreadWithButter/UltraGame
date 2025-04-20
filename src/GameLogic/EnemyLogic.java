package GameLogic;

import Entities.Hero;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class EnemyLogic {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(EnemyLogic.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int directionSelector(Hero h){
        int direction;
        if (h.getXPos()!=0 && h.getYPos()==0 && h.getXPos() < 3)
            direction=6;
        else if (h.getXPos()==0 && h.getYPos()!=0)
            direction=8;
        else if (h.getXPos()==0 && h.getYPos()==0)
            direction=6;
        else if (h.getXPos()!=0 && h.getYPos()==8)
            direction=4;
        else if (h.getXPos()!=0 && h.getYPos()!=8 && h.getYPos()!=0)
            direction=2;
        else
            direction=5;
        LOGGER.log(Level.INFO, "Враг выбрал направление " + direction);
        return direction;
    }
}
