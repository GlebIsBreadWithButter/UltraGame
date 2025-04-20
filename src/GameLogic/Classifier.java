package GameLogic;

public class Classifier {
    public static final int FIRST_HERO_ID = 1500;
    public static final int FIRST_ENEMY_HERO_ID = 1501;
    public static final int HEAVY_HERO_COST = 1000;
    public static final int LIGHT_HERO_COST = 900;
    public static final int GOLD_PACK_TYPE = 500;
    public static final int MAX_WORLD_SIZE_X = 9;
    public static final int MAX_WORLD_SIZE_Y = 9;

    private int idCounter;
    private int heroIdCounter = 1501;

    public Classifier(){
        idCounter = 0;
    }

    public int getId(){
        idCounter++;
        return idCounter;
    }

    public int getHeroId(){
        heroIdCounter++;
        return heroIdCounter;
    }
}
