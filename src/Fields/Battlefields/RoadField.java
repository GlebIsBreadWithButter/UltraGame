package Fields.Battlefields;

import Cells.BattlefieldCells.Dirt;
import Cells.BattlefieldCells.Paving;
import Fields.Field;

public class RoadField extends Field {
    public RoadField (){
        initialize(6, 6);
        for (int x = 0; x<xSize; x++){
            for (int y=0; y<ySize; y++) {
                if (((x+y)%4==0) && (x>=1) && (x<=4)){
                    fieldBody.get(x).add(y, new Dirt(x,y));
                }
                else if ((x>=1) && (x<=4))
                    fieldBody.get(x).add(y, new Paving(x, y));
                else
                    fieldBody.get(x).add(y, new Dirt(x,y));
            }
        }
    }
}
