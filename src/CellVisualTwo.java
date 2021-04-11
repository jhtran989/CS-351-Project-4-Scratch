import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellVisualTwo {
    Rectangle cellBackGround;
    Rectangle topLeft;
    Rectangle topRight;
    Rectangle botLeft;
    Rectangle botRight;
    Rectangle left;
    Rectangle top;
    Rectangle right;
    Rectangle bot;

    public CellVisualTwo(int cellSize) {
        this.cellBackGround = new Rectangle(cellSize, cellSize);
        this.cellBackGround.setFill(Color.GREEN);

        this.topLeft = new Rectangle(2, 2);
        this.topLeft.setFill(Color.BLACK);

        this.topRight = new Rectangle(2, 2);
        this.topRight.setFill(Color.BLACK);
        this.topRight.setX(cellSize - 2);

        this.botLeft = new Rectangle(2, 2);
        this.botLeft.setFill(Color.BLACK);
        this.botLeft.setY(cellSize - 2);

        this.botRight = new Rectangle(2, 2);
        this.botRight.setFill(Color.BLACK);
        this.botRight.setX(cellSize - 2);
        this.botRight.setY(cellSize - 2);

        this.top = new Rectangle(cellSize - 4, 2);
        this.top.setFill(Color.BLACK);
        this.top.setX(2);

        this.bot = new Rectangle(cellSize - 4, 2);
        this.bot.setFill(Color.BLACK);
        this.bot.setX(2);
        this.bot.setY(cellSize - 2);

        this.left = new Rectangle(2, cellSize - 4);
        this.left.setFill(Color.BLACK);
        this.left.setY(2);

        this.right = new Rectangle(2, cellSize - 4);
        this.right.setFill(Color.BLACK);
        this.right.setX(cellSize - 2);
        this.right.setY(2);
    }

    public void hideLeft(){
        this.left.setVisible(false);
    }

}
