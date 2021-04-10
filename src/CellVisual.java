import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellVisual {
    Group g;

    public CellVisual(int cellSize) {
        this.g = makeVisual(cellSize);
    }

    public Group makeVisual(int cellSize){
        Group cell = new Group();

        Rectangle cellBackGround = new Rectangle(cellSize, cellSize);
        cellBackGround.setFill(Color.GREEN);

        Rectangle topLeft = new Rectangle(2, 2);
        topLeft.setFill(Color.BLACK);

        Rectangle topRight = new Rectangle(2, 2);
        topRight.setFill(Color.BLACK);
        topRight.setX(cellSize - 2);

        Rectangle botLeft = new Rectangle(2, 2);
        botLeft.setFill(Color.BLACK);
        botLeft.setY(cellSize - 2);

        Rectangle botRight = new Rectangle(2, 2);
        botRight.setFill(Color.BLACK);
        botRight.setX(cellSize - 2);
        botRight.setY(cellSize - 2);

        Rectangle top = new Rectangle(cellSize - 4, 2);
        top.setFill(Color.BLACK);
        top.setX(2);

        Rectangle bot = new Rectangle(cellSize - 4, 2);
        bot.setFill(Color.BLACK);
        bot.setX(2);
        bot.setY(cellSize - 2);

        Rectangle left = new Rectangle(2, cellSize - 4);
        left.setFill(Color.BLACK);
        left.setY(2);

        Rectangle right = new Rectangle(2, cellSize - 4);
        right.setFill(Color.BLACK);
        right.setX(cellSize - 2);
        right.setY(2);

        cell.getChildren().addAll(cellBackGround, topLeft, topRight, botLeft,
                botRight, left, top, right, bot);

        return cell;
    }

    public Group getG() {
        return g;
    }
}
