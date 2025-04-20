package Fields.Worlds;

import Cells.WorldCells.Castle;
import Fields.Field;
import GameLogic.WorldEditor;
import GameLogic.WorldGenerator;

public class World extends Field {
    private Castle myCastle;
    private Castle enemyCastle;
    private final WorldEditor worldEditor;

    public Castle getMyCastle() {
        return myCastle;
    }

    public void setMyCastle(Castle myCastle) {
        this.myCastle = myCastle;
    }

    public Castle getEnemyCastle() {
        return enemyCastle;
    }

    public void setEnemyCastle(Castle enemyCastle) {
        this.enemyCastle = enemyCastle;
    }

    public WorldEditor getWorldEditor() {
        return worldEditor;
    }

    public World () {
        initialize(9, 9);
        WorldGenerator worldGenerator = new WorldGenerator(this);
        worldEditor = new WorldEditor(this);
        worldGenerator.generatePreDefinedWorld();
        worldGenerator.generateCastles();
        worldGenerator.generateGoldPacks();
    }
}
