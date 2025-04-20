package GameLogic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static GameLogic.Classifier.MAX_WORLD_SIZE_X;
import static GameLogic.Classifier.MAX_WORLD_SIZE_Y;

public class Tunnel {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Tunnel.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int timer;
    private int entranceX;
    private int entranceY;
    private final int exitX;
    private final int exitY;

    public int getEntranceX() {
        return entranceX;
    }

    public void setEntranceX(int entranceX) {
        this.entranceX = entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public void setEntranceY(int entranceY) {
        this.entranceY = entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Tunnel(int length, int direction, int startX, int startY){
        entranceX = startX;
        entranceY = startY;
        timer = length;
        exitX = calculateExitX(direction, length, startX);
        exitY = calculateExitY(direction, length, startY);
    }

    public int calculateExitX(int direction, int length, int sx){
        int exX=0;
        switch (direction){
            case (4), (1):
                exX = sx-length;
                if (exX<0) {
                    LOGGER.log(Level.SEVERE, "Выход тоннеля за нижнюю границу мира по оси абсцисс");
                    exX = 0;
                }
                break;
            case(2):
                exX = sx;
                break;
            case (3), (6):
                exX = sx+length;
                if (exX>MAX_WORLD_SIZE_X-1) {
                    exX = MAX_WORLD_SIZE_X - 1;
                    LOGGER.log(Level.SEVERE, "Выход тоннеля за верхнюю границу мира по оси абсцисс");
                }
                break;
            default:
                LOGGER.log(Level.SEVERE, "Выбрано невозможное направление проходки тоннеля");
                break;
        }
        return exX;
    }

    public int calculateExitY(int direction, int length, int sy){
        int exY=0;
        switch (direction){
            case (4), (6):
                exY = sy;
                break;
            case (1), (3), (2):
                exY = sy+length;
                if (exY>MAX_WORLD_SIZE_Y-1) {
                    exY = MAX_WORLD_SIZE_Y - 1;
                    LOGGER.log(Level.SEVERE, "Выход тоннеля за верхнюю границу мира по оси ординат");
                }
                break;
            default:
                LOGGER.log(Level.SEVERE, "Выбрано невозможное направление проходки тоннеля");
                break;
        }
        return exY;
    }
}
