public class Cell {
    private int cellValue;
    private boolean visited;
    private boolean upWall;
    private boolean rightWall;
    private boolean downWall;
    private boolean leftWall;
    private final int ROW;
    private final int COL;
    private final int CELL_ID;

    public Cell(int value, int row, int col, int cellID) {
        this.CELL_ID = cellID;
        this.cellValue = value;
        this.ROW = row;
        this.COL = col;
        visited = false;
        upWall = true;
        rightWall = true;
        downWall = true;
        leftWall = true;
    }

    public void printCellValue(){
        System.out.print(cellValue + " ");
    }

    public void printCellID(){
        System.out.print(CELL_ID + " ");
    }

    public void visit(){
        cellValue = 1;
        visited = true;
    }

    public int getCELL_ID() {
        return CELL_ID;
    }

    public boolean isVisited() {
        return visited;
    }

    public int getROW() {
        return ROW;
    }

    public int getCOL() {
        return COL;
    }

    public void setUpWall(boolean upWall) {
        this.upWall = upWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public void setDownWall(boolean downWall) {
        this.downWall = downWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }
}
