import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import java.io.*;

public class Mazes extends Application {
    private static int mazeSize;
    private static int cellSize;
    private static int dimension;
    private static int numberOfCells;
    private static String algorithm;
    private static String solver;

    public static void main(String[] args) throws IOException {
        readTheFile(args[0]);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        dimension = mazeSize / cellSize;
        numberOfCells = dimension * dimension;
        Board b = new Board(dimension, cellSize);

        primaryStage.setTitle("Mazes");
        TilePane tp = new TilePane();

        tp.setPrefRows(dimension);
        tp.setPrefColumns(dimension);

        Pane root = new Pane(tp);

        if (algorithm.equals("dfs")){
            System.out.println("Start cell: 0");
            System.out.println("Finish cell: " + (numberOfCells - 1));
            b.depthFirstSearch(solver);
        }

        if (algorithm.equals("kruskal")){
            System.out.println("Start cell: 0");
            System.out.println("Finish cell: " + (numberOfCells - 1));
            b.kruskal(solver);
        }

        if (algorithm.equals("prim")){
            System.out.println("Start cell: 0");
            System.out.println("Finish cell: " + (numberOfCells - 1));
            b.prim(solver);
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Cell c = b.getCell(i, j);
                Group g = c.drawCell();
                tp.getChildren().add(g);
            }
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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