package Fields.Battlefields;

import Cells.BattlefieldCells.DeepWater;
import Cells.BattlefieldCells.Dirt;
import Cells.BattlefieldCells.ShallowWater;
import Fields.Field;

public class RiverField extends Field {
    public RiverField (){
        initialize(6, 6);
        for (int x = 0; x<xSize; x++){
            for (int y=0; y<ySize; y++) {
                if (y>=1 && y<=4) {
                    if ((y+x)%3==0)
                        fieldBody.get(x).add(y, new ShallowWater(x,y));
                    else
                        fieldBody.get(x).add(y, new DeepWater(x,y));
                }
                else
                    fieldBody.get(x).add(y, new Dirt(x,y));
            }
        }
    }
}
