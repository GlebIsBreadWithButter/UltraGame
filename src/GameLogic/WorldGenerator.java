package GameLogic;

import Cells.*;
import Cells.WorldCells.*;
import Entities.Objects.GoldPack;
import Fields.Worlds.World;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {
    private static World worldBeingGenerated;
    private static final int ENEMY_CASTLE_X = 4;
    private static final int ENEMY_CASTLE_Y = 8;
    private static final int MY_CASTLE_X = 4;
    private static final int MY_CASTLE_Y = 0;

    public WorldGenerator(World world){
        worldBeingGenerated = world;
    }

    public void generatePreDefinedWorld(){
        ArrayList<ArrayList<Cell>> fieldBeingGeneratedBody =  worldBeingGenerated.getFieldBody();
        for (int x = 0; x < worldBeingGenerated.getxSize(); x++) {
            for (int y = 0; y < worldBeingGenerated.getySize(); y++) {
                if (x == 0)
                    fieldBeingGeneratedBody.get(x).add(y, new Road(x, y));
                else if ((y == 0 || y == 8) && x <= 3)
                    fieldBeingGeneratedBody.get(x).add(y, new Road(x, y));
                else if ((x == 7) || (x == 8) && (y!=4))
                    fieldBeingGeneratedBody.get(x).add(y, new Mountains(x,y));
                else if (y == 3 || y == 5)
                    fieldBeingGeneratedBody.get(x).add(y, new Swamp(x,y));
                else if (y == 2 || y == 6)
                    fieldBeingGeneratedBody.get(x).add(y, new River(x,y));
                else if (y==4 && x!=3)
                    fieldBeingGeneratedBody.get(x).add(y, new MegaMountains(x,y));
                else
                    fieldBeingGeneratedBody.get(x).add(y, new Plains(x,y));
            }
        }
        worldBeingGenerated.setFieldBody(fieldBeingGeneratedBody);
    }

    public void generateCastles(){
        Castle my = new Castle(false,MY_CASTLE_X, MY_CASTLE_Y);
        Castle enemy = new Castle(true,ENEMY_CASTLE_X,ENEMY_CASTLE_Y);
        worldBeingGenerated.getFieldBody().get(4).set(0, my);
        worldBeingGenerated.getFieldBody().get(4).set(8, enemy);
        worldBeingGenerated.setMyCastle(my);
        worldBeingGenerated.setEnemyCastle(enemy);
    }

    public void generateGoldPacks(){
        Random r = new Random();
        int x, y, value;
        for (int i=0; i<10; i++) {
            x = r.nextInt(8);
            y = r.nextInt(8);
            if (y>=1 && x>=1 && y!=4) {
                value = r.nextInt(30);
                if (value < 15)
                    value += value*2;
                worldBeingGenerated.getFieldBody().get(x).get(y).setOccupant(new GoldPack(value));
            }
        }
    }
}
