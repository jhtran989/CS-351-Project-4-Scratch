import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
    private int cellValue;
    private final int CELL_SIZE;
    private boolean visited;
    private boolean solverTravelled;
    private boolean upWall;
    private boolean rightWall;
    private boolean downWall;
    private boolean leftWall;
    private final int ROW;
    private final int COL;
    private final int CELL_ID;
    private Group cellVisual;

    public Cell(int value, int row, int col, int cellID, int cellSize) {
        this.cellValue = value;
        this.CELL_ID = cellID;
        this.ROW = row;
        this.COL = col;
        this.CELL_SIZE = cellSize;
        visited = false;
        solverTravelled = false;
        upWall = true;
        rightWall = true;
        downWall = true;
        leftWall = true;
        cellVisual = drawCell();
    }

    public Group drawCell() {
        Group cell = new Group();

        Rectangle cellBackGroundUntraveled = new Rectangle(CELL_SIZE, CELL_SIZE);
        cellBackGroundUntraveled.setFill(Color.GREEN);

        Rectangle cellBackGroundTraveled = new Rectangle(CELL_SIZE, CELL_SIZE);
        cellBackGroundUntraveled.setFill(Color.GREEN);

        Rectangle topLeft = new Rectangle(2, 2);
        topLeft.setFill(Color.BLACK);

        Rectangle topRight = new Rectangle(2, 2);
        topRight.setFill(Color.BLACK);
        topRight.setX(CELL_SIZE - 2);

        Rectangle botLeft = new Rectangle(2, 2);
        botLeft.setFill(Color.BLACK);
        botLeft.setY(CELL_SIZE - 2);

        Rectangle botRight = new Rectangle(2, 2);
        botRight.setFill(Color.BLACK);
        botRight.setX(CELL_SIZE - 2);
        botRight.setY(CELL_SIZE - 2);

        Rectangle top = new Rectangle(CELL_SIZE - 4, 2);
        top.setFill(Color.BLACK);
        top.setX(2);

        Rectangle bot = new Rectangle(CELL_SIZE - 4, 2);
        bot.setFill(Color.BLACK);
        bot.setX(2);
        bot.setY(CELL_SIZE - 2);

        Rectangle left = new Rectangle(2, CELL_SIZE - 4);
        left.setFill(Color.BLACK);
        left.setY(2);

        Rectangle right = new Rectangle(2, CELL_SIZE - 4);
        right.setFill(Color.BLACK);
        right.setX(CELL_SIZE - 2);
        right.setY(2);

        cell.getChildren().addAll(cellBackGroundUntraveled, topLeft, topRight,
                botLeft, botRight);

        if (this.upWall){
            cell.getChildren().add(top);
        }
        if (this.downWall){
            cell.getChildren().add(bot);
        }
        if (this.leftWall){
            cell.getChildren().add(left);
        }
        if (this.rightWall){
            cell.getChildren().add(right);
        }

        return cell;
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

    public void travelToCell(){
        solverTravelled = true;
    }

    public boolean isSolverTravelled() {
        return solverTravelled;
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

    public boolean isUpWall() {
        return upWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public boolean isDownWall() {
        return downWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }
}
