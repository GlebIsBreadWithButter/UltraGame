package Fields.Battlefields;

import Cells.BattlefieldCells.Dirt;
import Cells.BattlefieldCells.Rock;
import Fields.Field;

public class MountatinsField extends Field {
    public MountatinsField (){
        initialize(6, 6);
        for (int x = 0; x<xSize; x++){
            for (int y=0; y<ySize; y++) {
                if (((x+y)%3==0) && ((y%2==0))){
                    fieldBody.get(x).add(y, new Rock(x,y));
                }
                else
                    fieldBody.get(x).add(y, new Dirt(x,y));
            }
        }
    }
}
