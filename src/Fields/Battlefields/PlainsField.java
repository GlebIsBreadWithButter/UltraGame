package Fields.Battlefields;

import Cells.BattlefieldCells.Dirt;
import Fields.Field;

public class PlainsField extends Field {
    public PlainsField (){
        initialize(6, 6);
        for (int x = 0; x<xSize; x++){
            for (int y=0; y<ySize; y++) {
                fieldBody.get(x).add(y, new Dirt(x,y));
            }
        }
    }
}
