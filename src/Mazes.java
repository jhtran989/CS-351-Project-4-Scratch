import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.*;

public class Mazes extends Application {
    private static int mazeSize;
    private static int cellSize;
    private static int rowsCols;

    public static void main(String[] args) throws IOException {
        readTheFile(args[0]);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mazes");
        Canvas canvas = new Canvas(mazeSize, mazeSize);
        Pane root = new Pane(canvas);

        Board b = new Board(mazeSize/cellSize);
        b.printBoard();
        System.out.println();
        b.depthFirstSearch();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void readTheFile(String args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(args))) {
            mazeSize = Integer.parseInt(br.readLine());
            cellSize = Integer.parseInt(br.readLine());
            String algorithm = br.readLine();
            String solver = br.readLine();
            System.out.println(mazeSize);
            System.out.println(cellSize);
            System.out.println(algorithm);
            System.out.println(solver);
        }
    }
}