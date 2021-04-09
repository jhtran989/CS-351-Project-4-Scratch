public class Cell {
    private int cellValue;
    private boolean visited;
    private boolean upWall;
    private boolean rightWall;
    private boolean downWall;
    private boolean leftWall;
    private int row;
    private int col;

    public Cell(int value, int row, int col) {
        this.cellValue = value;
        this.row = row;
        this.col = col;
        visited = false;
        upWall = true;
        rightWall = true;
        downWall = true;
        leftWall = true;
    }

    public void printCellValue(){
        System.out.print(cellValue + " ");
    }

    public void visit(){
        cellValue = 1;
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isUpWall() {
        return upWall;
    }

    public void setUpWall(boolean upWall) {
        this.upWall = upWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public boolean isDownWall() {
        return downWall;
    }

    public void setDownWall(boolean downWall) {
        this.downWall = downWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }
}
