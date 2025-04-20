package GameLogic;

import Entities.Unit;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AftermathMessage {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(AftermathMessage.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean unitCanMove;
    private Unit myUnitToBeRemoved;
    private Unit enemyUnitToBeRemoved;
    private ArrayList<Unit> mySquadToBeRemoved;
    private ArrayList<Unit> enemySquadToBeRemoved;
    private String type;

    public ArrayList<Unit> getEnemySquadToBeRemoved() {
        return enemySquadToBeRemoved;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Unit> getMySquadToBeRemoved() {
        return mySquadToBeRemoved;
    }

    public Unit getEnemyUnitToBeRemoved() {
        return enemyUnitToBeRemoved;
    }

    public Unit getMyUnitToBeRemoved() {
        return myUnitToBeRemoved;
    }

    public boolean isUnitCanMove() {
        return unitCanMove;
    }

    public void setUnitCanMove(boolean unitCanMove) {
        this.unitCanMove = unitCanMove;
    }

    public AftermathMessage(boolean c){
        unitCanMove = c;
    }

    public AftermathMessage(Unit mu, Unit eu){
        myUnitToBeRemoved = mu;
        enemyUnitToBeRemoved = eu;
        LOGGER.log(Level.INFO, "К удалению: солдат врага, солдат игрока");
        type = "uu";
    }

    public AftermathMessage(ArrayList<Unit> ms, ArrayList<Unit> es){
        mySquadToBeRemoved = ms;
        enemySquadToBeRemoved = es;
        LOGGER.log(Level.INFO, "К удалению: отряд врага, отряд игрока");
        type = "ss";
    }

    public AftermathMessage(boolean e, Unit u){
        if (e) {
            enemyUnitToBeRemoved = u;
            LOGGER.log(Level.INFO, "К удалению: солдат врага");
        }
        else {
            myUnitToBeRemoved = u;
            LOGGER.log(Level.INFO, "К удалению: солдат игрока");
        }
        type = "u";
    }

    public AftermathMessage(boolean e, ArrayList<Unit> s){
        if (e) {
            enemySquadToBeRemoved = s;
            LOGGER.log(Level.INFO, "К удалению: отряд врага");
        }
        else {
            mySquadToBeRemoved = s;
            LOGGER.log(Level.INFO, "К удалению: отряд игрока");
        }
        type = "s";
    }

    public AftermathMessage(boolean ue, boolean se, Unit u, ArrayList<Unit> s){
        if (ue) {
            enemyUnitToBeRemoved = u;
            LOGGER.log(Level.INFO, "К удалению: солдат врага");
        }
        else {
            myUnitToBeRemoved = u;
            LOGGER.log(Level.INFO, "К удалению: солдат игрока");
        }
        if (se) {
            enemySquadToBeRemoved = s;
            LOGGER.log(Level.INFO, "К удалению: отряд врага");
        }
        else {
            mySquadToBeRemoved = s;
            LOGGER.log(Level.INFO, "К удалению: отряд игрока");
        }
        type = "us";
    }
}
