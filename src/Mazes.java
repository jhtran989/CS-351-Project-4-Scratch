import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import java.io.*;

public class Mazes extends Application {
    private static int mazeSize;
    private static int cellSize;
    private static int numberOfCells;
    private static String algorithm;
    private static String solver;

    public static void main(String[] args) throws IOException {
        readTheFile(args[0]);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mazes");
//        Canvas canvas = new Canvas(mazeSize, mazeSize);
        numberOfCells = (mazeSize/cellSize) * (mazeSize/cellSize);
        System.out.println("Maze size: " + mazeSize);
        System.out.println("Cell size: " + cellSize);
        System.out.println("Number of cells: " + numberOfCells);
//        TilePane tp = new TilePane();
//        tp.setPrefRows(mazeSize/cellSize);
//        tp.setPrefColumns(mazeSize/cellSize);
//        Pane root = new Pane(tp);

        Board b = new Board(mazeSize/cellSize);
//        CellVisualTwo cv = new CellVisualTwo(cellSize);
//        root.getChildren().add(cv);
//        CellVisual cv = new CellVisual(cellSize);
//        root.getChildren().add(cv.getG());
//        cv.removeLeft();
//        cv.removeBot();
//        cv.removeRight();
//        cv.removeTop();
//        for (int i = 0; i < numberOfCells; i++) {
//            tp.getChildren().add(i, new CellVisual(cellSize).getG());
//        }




        if (algorithm.equals("dfs")){
            System.out.println("Start cell: 0");
            System.out.println("Finish cell: " + (numberOfCells - 1));
            b.printBoard();
            System.out.println();
            b.depthFirstSearch(solver);
        }

        if (algorithm.equals("kruskal")){
            b.printBoard();
            System.out.println();
            b.kruskal(solver);
        }

        if (algorithm.equals("prim")){
            b.printBoard();
            System.out.println();
            b.prim(solver);
        }

//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public static void readTheFile(String args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(args))) {
            mazeSize = Integer.parseInt(br.readLine());
            cellSize = Integer.parseInt(br.readLine());
            algorithm = br.readLine();
            solver = br.readLine();
            System.out.println(mazeSize);
            System.out.println(cellSize);
            System.out.println(algorithm);
            System.out.println(solver);
        }
    }
}