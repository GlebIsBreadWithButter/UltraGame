package Fields.Battlefields;

import Cells.BattlefieldCells.Mud;
import Cells.BattlefieldCells.ShallowWater;
import Fields.Field;

public class SwampField extends Field {
    public SwampField (){
        initialize(6, 6);
        for (int x = 0; x<xSize; x++){
            for (int y=0; y<ySize; y++) {
                if ((x+y)%4==0){
                    fieldBody.get(x).add(y, new ShallowWater(x,y));
                }
                else
                    fieldBody.get(x).add(y, new Mud(x,y));
            }
        }
    }
}
