package Fields;

import Cells.Cell;

import java.util.ArrayList;

public class Field {
    protected ArrayList<ArrayList<Cell>> fieldBody;
    protected int xSize;
    protected int ySize;

    public ArrayList<ArrayList<Cell>> getFieldBody() {
        return fieldBody;
    }

    public void setFieldBody(ArrayList<ArrayList<Cell>> fieldBody) {
        this.fieldBody = fieldBody;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    protected void initialize (int inXsize, int inYsize){
        xSize = inXsize;
        ySize = inYsize;
        fieldBody = new ArrayList<>();
        for (int k = 0; k < xSize; k++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            fieldBody.add(row);
        }
    }

    public void show(){
        for (int y=0; y<ySize; y++){
            for (int x=0; x<xSize; x++){
                String toPrint;
                Cell c = fieldBody.get(x).get(y);
                if (c.getOccupant() == null && c.getHeroOccupant() == null && c.getSquadOccupant() == null) {
                    toPrint = fieldBody.get(x).get(y).getLook();
                }
                else if (fieldBody.get(x).get(y).getSquadOccupant() != null) {
                    toPrint = fieldBody.get(x).get(y).getSquadOccupant().get(0).getLookName();
                }
                else
                    toPrint = fieldBody.get(x).get(y).getOccupant().getLookName();
                System.out.print(toPrint);
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    public Cell getCell(int targetX, int targetY){
        return this.getFieldBody().get(targetX).get(targetY);
    }

    public void setCell(int targetX, int targetY, Cell cell){
        this.getFieldBody().get(targetX).set(targetY, cell);
    }
}
