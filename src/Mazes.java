import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Mazes extends Application {
    private static int mazeSize;
    private static int cellSize;
    private static int dimension;
    private static int numberOfCells;
    private static String algorithm;
    private static String solver;
    private static Board initialBoard;
    private static Board actionBoard;
    private static TilePane tp;
    private static ArrayList<Integer> actionList;
    private static ArrayList<Integer> trackerLocation;

    public static void main(String[] args) throws IOException {
        readTheFile(args[0]);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MyTimer timer = new MyTimer();

        dimension = mazeSize / cellSize;
        numberOfCells = dimension * dimension;
        initialBoard = new Board(dimension, cellSize);
        actionBoard = new Board(dimension, cellSize);
        actionList = new ArrayList<>();
        trackerLocation = new ArrayList<>();

        primaryStage.setTitle("Mazes");
        tp = new TilePane();

        tp.setPrefRows(dimension);
        tp.setPrefColumns(dimension);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Cell c = initialBoard.getCell(i, j);
                Group g = c.drawCell();
                tp.getChildren().add(g);
            }
        }

        Pane root = new Pane(tp);
        root.setMaxSize(mazeSize, mazeSize);

        if (algorithm.equals("dfs")){
            System.out.println("Start cell: 0");
            System.out.println("Finish cell: " + (numberOfCells - 1));
            initialBoard.depthFirstSearch(solver);
            actionList = initialBoard.getActionList();
            trackerLocation = initialBoard.getSolverLocationList();
        }

        if (algorithm.equals("kruskal")){
            System.out.println("Start cell: 0");
            System.out.println("Finish cell: " + (numberOfCells - 1));
            initialBoard.kruskal(solver);
            actionList = initialBoard.getActionList();
            trackerLocation = initialBoard.getSolverLocationList();
        }

        if (algorithm.equals("prim")){
            System.out.println("Start cell: 0");
            System.out.println("Finish cell: " + (numberOfCells - 1));
            initialBoard.prim(solver);
            actionList = initialBoard.getActionList();
            trackerLocation = initialBoard.getSolverLocationList();
        }

        timer.start();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void takeOutWalls(Board board, ArrayList<Integer> cellIDs){
        if (cellIDs.get(0) == cellIDs.get(1) - 1){ //check right neighbor
            board.getCellFromID(cellIDs.get(0)).setRightWall(false);
            board.getCellFromID(cellIDs.get(1)).setLeftWall(false);
        }
        else if (cellIDs.get(0) == cellIDs.get(1) + 1){ //check left neighbor
            board.getCellFromID(cellIDs.get(0)).setLeftWall(false);
            board.getCellFromID(cellIDs.get(1)).setRightWall(false);
        }
        else if (cellIDs.get(0) < cellIDs.get(1)){ //check down neighbor
            board.getCellFromID(cellIDs.get(0)).setDownWall(false);
            board.getCellFromID(cellIDs.get(1)).setUpWall(false);
        }
        else if (cellIDs.get(0) > cellIDs.get(1)){ //check up neighbor
            board.getCellFromID(cellIDs.get(0)).setUpWall(false);
            board.getCellFromID(cellIDs.get(1)).setDownWall(false);
        }
        cellIDs.remove(0);
        cellIDs.remove(0);
    }

    public void updateLocation(Board board, ArrayList<Integer> location){
        if (location.size() > 1){
            board.getCellFromID(location.get(1)).travelToCell();
            board.getCellFromID(location.get(0)).leaveCell();
            location.remove(0);
        }
        else{
            board.getCellFromID(location.get(0)).travelToCell();
            location.remove(0);
        }

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

    private class MyTimer extends AnimationTimer {
        private long prevTime = 0;

        @Override
        public void handle(long now) {
            boolean goSolve = false;
            long dt = now - prevTime;
            if (dt > (1e7)) {
                prevTime = now;
                if (!actionList.isEmpty()){
                    tp.getChildren().clear();
                    takeOutWalls(actionBoard, actionList);
                    for (int i = 0; i < dimension; i++) {
                        for (int j = 0; j < dimension; j++) {
                            Cell c = actionBoard.getCell(i, j);
                            Group g = c.drawCell();
                            tp.getChildren().add(g);
                        }
                    }
                }
                if (actionList.isEmpty()){
                    if (!trackerLocation.isEmpty()){
                        tp.getChildren().clear();
                        updateLocation(actionBoard, trackerLocation);
                        for (int i = 0; i < dimension; i++) {
                            for (int j = 0; j < dimension; j++) {
                                Cell c = actionBoard.getCell(i, j);
                                Group g = c.drawCell();
                                tp.getChildren().add(g);
                            }
                        }
                    }
                }
            }
        }
    }

}