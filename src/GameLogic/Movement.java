package GameLogic;

import Cells.Cell;
import Entities.Entity;
import Entities.Unit;
import Fields.Field;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Movement {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Movement.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Field field;

    public Movement(Field field){
        this.field = field;
    }

    public Message move(Entity entity, int direction, boolean inBattle) {
        Message movementResult = new Message(-1, null,null, null);
        int directionX = defineDirectionX(direction),
                moveCounter = entity.getMoveCounter(),
                directionY = defineDirectionY(direction),
                targetX = entity.getXPos() + directionX,
                targetY = entity.getYPos() + directionY;
        boolean rightX;
        boolean rightY ;
        if (inBattle) {
           rightX = smallCheckCropX(targetX);
           rightY = smallCheckCropY(targetY);
        }
        else{
            rightX = checkCropX(targetX);
            rightY = checkCropY(targetY);
        }
        if (rightX && rightY) {
            int destinationPenalty = field.getCell(targetX, targetY).getMovingPenalty();
            boolean passable = field.getCell(targetX, targetY).isCanBePassed(),
                    enoughPower = entity.getMovePowerCounter() - destinationPenalty >= 0,
                    hasEnoughMoves = moveCounter < entity.getMaxMoveCount(),
                    enemyInside = checkCell(field.getCell(targetX, targetY)) == 2,
                    goldInside = checkCell(field.getCell(targetX, targetY)) == 3,
                    castle = checkCell(field.getCell(targetX, targetY)) == 1,
                    enemyCastle = checkCell(field.getCell(targetX, targetY)) == 5,
                    tunnel = checkCell(field.getCell(targetX, targetY)) == 6,
                    empty = checkCell(field.getCell(targetX, targetY)) == 0;
            if (passable && enoughPower && hasEnoughMoves && direction!=5) {
                if (empty) {
                    movementResult = new Message(0, null, null, destinationPenalty);
                    shift(entity, targetX, targetY);
                }
                else if (goldInside) {
                    movementResult = new Message(3, field.getFieldBody().get(targetX).get(targetY).getOccupant().getBounty(), null, destinationPenalty);
                    shift(entity, targetX, targetY);
                }
                else if (field.getCell(targetX, targetY).getOccupant() != null && (enemyInside || entity.getIsEnemy() != field.getCell(targetX, targetY).getOccupant().getIsEnemy())) {
                    int proceed = 1;
                    if (!entity.getIsEnemy()) {
                        proceed = makeAWarning();
                    }
                    if (proceed == 1) {
                        movementResult = new Message(2, targetX, targetY, destinationPenalty);
                    } else
                        movementResult = new Message(4, null, null, null);
                }
                else if (tunnel){
                    movementResult = new Message(6, field.getCell(targetX, targetY).getTunnel().getEntranceX(), field.getCell(targetX, targetY).getTunnel().getEntranceY(), destinationPenalty);
                    shift(entity, targetX, targetY);
                }
                else if (castle) {
                    if (entity.getIsEnemy()) {
                        movementResult = new Message(1, 1, null, destinationPenalty);
                    } else {
                        movementResult = new Message(1, 0, null, destinationPenalty);
                    }
                    shift(entity, targetX, targetY);
                }
                else if (enemyCastle) {
                    if (entity.getIsEnemy()) {
                        movementResult = new Message(1, 1, null, destinationPenalty);
                    } else {
                        movementResult = new Message(5, 0, null, destinationPenalty);
                    }
                    shift(entity, targetX, targetY);
                }
            }
            else if(direction ==5) {
                movementResult = new Message(7, 2, null, null);
            }
            else if(!enoughPower){
                movementResult = new Message(8, null, null, null);
            }
        }
        else
            LOGGER.log(Level.SEVERE, "Выход за пределы поля существом " + entity.getName());
        return movementResult;
    }

    public void shift(Entity entity, int targetX, int targetY){
        field.getFieldBody().get(entity.getXPos()).get(entity.getYPos()).setOccupant(null);
        field.getFieldBody().get(targetX).get(targetY).setOccupant(entity);
        entity.setYPos(targetY);
        entity.setXPos(targetX);
        LOGGER.log(Level.INFO, "Сущность " + entity.getName() + " смещена на координаты (" + entity.getXPos() + " ; " + entity.getYPos()+")");
    }

    public Message moveSquad(ArrayList<Unit> squad, int direction, boolean inBattle){
        Message movementResult;
        movementResult = move(squad.get(0), direction, inBattle);
        if (squad.get(0).getXPos() != squad.get(1).getXPos() || squad.get(0).getYPos() != squad.get(1).getYPos()) {
            int targetX = squad.get(0).getXPos();
            int targetY = squad.get(0).getYPos();
            field.getCell(squad.get(1).getXPos(), squad.get(1).getYPos()).setSquadOccupant(null);
            field.getCell(squad.get(1).getXPos(), squad.get(1).getYPos()).setOccupant(null);
            field.getCell(squad.get(0).getXPos(), squad.get(0).getYPos()).setSquadOccupant(squad);
            for(Unit u : squad){
                shift(u, targetX, targetY);
            }
        }
        LOGGER.log(Level.INFO, "Сущности в отряде смещены на координаты (" + squad.get(0).getXPos() + " ; " + squad.get(0).getYPos()+")");
        return movementResult;
    }

    private int defineDirectionX(int direction){
        return switch (direction) {
            case 7, 1, 4 -> -1;
            case 9, 3, 6 -> 1;
            default -> 0;
        };
    }

    private int defineDirectionY(int direction){
        return switch (direction) {
            case 7, 8, 9 -> -1;
            case 1, 2, 3 -> 1;
            default -> 0;
        };
    }

    private boolean checkCropX(int targetX){
        LOGGER.log(Level.SEVERE, "Обнаружен выход за пределы карты мира");
        return targetX >= 0 && targetX <= 8;
    }

    private boolean checkCropY(int targetY){
        LOGGER.log(Level.SEVERE, "Обнаружен выход за пределы карты мира");
        return targetY >= 0 && targetY <= 8;
    }

    private boolean smallCheckCropX(int targetX){
        LOGGER.log(Level.SEVERE, "Обнаружен выход за пределы поля боя");
        return targetX >= 0 && targetX <= 5;
    }

    private boolean smallCheckCropY(int targetY){
        LOGGER.log(Level.SEVERE, "Обнаружен выход за пределы поля боя");
        return targetY >= 0 && targetY <= 5;
    }

    private int checkCell(Cell cell){
        int result = 0;
        if (cell.getOccupant()!=null) {
            if (cell.getOccupant().getIsEnemy()) {
                result = 2;
            }
            else if (cell.getOccupant().getType() == Classifier.GOLD_PACK_TYPE){
                result = 3;
            }
            else
                result = 4;
        }
        else if (cell.getTunnel()!=null){
            result = 6;
        }
        else if (Objects.equals(cell.getLook(), "\u001B[41m" + "|=|" + "\u001B[0m") && cell.isInteractable() && !cell.isEnemy()){
            result = 1;
        }
        else if (Objects.equals(cell.getLook(), "\u001B[41m" + "|=|" + "\u001B[0m") && cell.isInteractable() && cell.isEnemy()){
            result = 5;
        }
        return result;
    }

    private int makeAWarning(){
        int decision;
        System.out.println(Texts.getEnemyInsideWarningText());
        Scanner s = new Scanner(System.in);
        decision = s.nextInt();
        return decision;
    }

    public static boolean entityMoved(int moveResult){
        return switch (moveResult) {
            case 0, 1, 7, 5, 3 -> true;
            default -> false;
        };
    }

    public static void moveParametersReport(Entity e){
        System.out.println("Осталось сдвигов: " + (e.getMaxMoveCount() - e.getMoveCounter()));
        System.out.println("Осталось очков перемещения: " + e.getMovePowerCounter());
    }
}
