import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Board {

    private final int BOARD_SIZE;
    private final Cell[][] BOARD;
    Stack<Cell> pathStack = new Stack<>();

    public Board(int boardSize) {
        BOARD = new Cell[boardSize][boardSize];
        this.BOARD_SIZE = boardSize;
        int count = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                BOARD[i][j] = new Cell(0, i, j, count);
                count++;
            }
        }
    }

    public void depthFirstSearch(String solver){
        Cell c = BOARD[0][0];
        pathStack.push(c);
        c.visit();
        ArrayList<Cell> neighbors;
        neighbors = getNeighborsBreakWalls(c);
        Collections.shuffle(neighbors);
        for (Cell neighbor : neighbors) {
            pathStack.push(neighbor);
            neighbor.visit();
        }
        printBoard();
        System.out.println();
        neighbors.clear();
        while(!pathStack.empty()){
            c = pathStack.pop();
            neighbors = getNeighborsBreakWalls(c);
            Collections.shuffle(neighbors);
            for (Cell neighbor : neighbors) {
                pathStack.push(neighbor);
                neighbor.visit();
            }
            printBoard();
            System.out.println();
            neighbors.clear();
        }
        chooseSolver(solver);
    }

    public void kruskal(String solver){
        ArrayList<Edge> edges= getAllEdges();
        Collections.shuffle(edges);
        kruskalHelper(edges, solver);
    }

    public void kruskalHelper(ArrayList<Edge> edges, String solver){
        UnionFind uf = new UnionFind(BOARD_SIZE * BOARD_SIZE);
        while(!edges.isEmpty()){
            if (!uf.connected(edges.get(0).getCELL_ONE().getCELL_ID(),
                    edges.get(0).getCELL_TWO().getCELL_ID())){
                uf.union(edges.get(0).getCELL_ONE().getCELL_ID(),
                        edges.get(0).getCELL_TWO().getCELL_ID());
                if (edges.get(0).isHORIZONTAL()){
                    edges.get(0).takeDownHorizontalWall();
                    edges.get(0).getCELL_ONE().visit();
                    edges.get(0).getCELL_TWO().visit();
                }
                if (edges.get(0).isVERTICAL()){
                    edges.get(0).takeDownVerticalWall();
                    edges.get(0).getCELL_ONE().visit();
                    edges.get(0).getCELL_TWO().visit();
                }
//                System.out.println("edge removed: " + edges.get(0).getCELL_ONE().getCELL_ID() + ", " + edges.get(0).getCELL_TWO().getCELL_ID());
                edges.remove(0);

//                printBoard();
//                System.out.println();
            }
            else{
                System.out.println("EDGE KEPT "  + edges.get(0).getCELL_ONE().getCELL_ID() + ", " + edges.get(0).getCELL_TWO().getCELL_ID());
                edges.remove(0);
            }
        }
        chooseSolver(solver);
    }

    public void prim(String solver){
        Cell c = BOARD[0][0];
        c.visit();
        System.out.println("visit cell " + c.getCELL_ID());
        ArrayList<Cell> neighbors = getNeighbors(c);
        while(!neighbors.isEmpty()){
            printBoard();
            System.out.println();
            Collections.shuffle(neighbors);
            c = neighbors.get(0);
            c.visit();
            System.out.println("visit cell " + c.getCELL_ID());
            if (c.getROW() > 0){ //check up
                if (BOARD[c.getROW() - 1][c.getCOL()].isVisited()){
                    c.setUpWall(false);
                    BOARD[c.getROW() - 1][c.getCOL()].setDownWall(false);
                }
            }
            else if (c.getROW() < BOARD_SIZE - 1){ //check down
                if (BOARD[c.getROW() + 1][c.getCOL()].isVisited()){
                    c.setDownWall(false);
                    BOARD[c.getROW() + 1][c.getCOL()].setUpWall(false);
                }
            }
            else if (c.getCOL() > 0){ //check left
                if (BOARD[c.getROW()][c.getCOL() - 1].isVisited()){
                    c.setLeftWall(false);
                    BOARD[c.getROW()][c.getCOL() - 1].setRightWall(false);
                }

            }
            else if (c.getCOL() < BOARD_SIZE - 1){ //check right
                if (BOARD[c.getROW()][c.getCOL() + 1].isVisited()){
                    c.setRightWall(false);
                    BOARD[c.getROW()][c.getCOL() + 1].setLeftWall(false);
                }
            }
            neighbors.remove(0);
            ArrayList<Cell> temp = getNeighbors(c);
            for (Cell cell : temp) {
                if (!neighbors.contains(cell)) {
                    neighbors.add(cell);
                }
            }
            System.out.println();
        }
        printBoard();
        chooseSolver(solver);
    }

    //# of edges should be 2(n-1)^2 + 2(n-1)
    public ArrayList<Edge> getAllEdges(){
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < (BOARD_SIZE); i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (j < BOARD_SIZE - 1){ //add to right
                    edges.add(new Edge(BOARD[i][j], BOARD[i][j + 1],
                            true, false));
                }
                if (i < BOARD_SIZE - 1){
                    edges.add(new Edge(BOARD[i][j], BOARD[i + 1][j],
                            false, true));
                }
            }
        }
        for (Edge edge : edges) {
            edge.getCELL_ONE().printCellID();
            edge.getCELL_TWO().printCellID();
            System.out.println();
        }
        return edges;
    }

    public ArrayList<Cell> getNeighbors(Cell c){
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (c.getROW() > 0){ //check up
            if (!BOARD[c.getROW() - 1][c.getCOL()].isVisited()){
                neighbors.add(BOARD[c.getROW() - 1][c.getCOL()]);
            }
        }
        if (c.getROW() < (BOARD_SIZE - 1)){ //check down
            if (!BOARD[c.getROW() + 1][c.getCOL()].isVisited()){
                neighbors.add(BOARD[c.getROW() + 1][c.getCOL()]);
            }
        }
        if (c.getCOL() > 0){ //check left
            if (!BOARD[c.getROW()][c.getCOL() - 1].isVisited()){
                neighbors.add(BOARD[c.getROW()][c.getCOL() - 1]);
            }
        }
        if (c.getCOL() < (BOARD_SIZE - 1)){ //check right
            if (!BOARD[c.getROW()][c.getCOL() + 1].isVisited()){
                neighbors.add(BOARD[c.getROW()][c.getCOL() + 1]);
            }
        }
        return neighbors;
    }

    public ArrayList<Cell> getNeighborsBreakWalls(Cell c){
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (c.getROW() > 0){ //check up
            if (!BOARD[c.getROW() - 1][c.getCOL()].isVisited()){
                neighbors.add(BOARD[c.getROW() - 1][c.getCOL()]);
                c.setUpWall(false);
                BOARD[c.getROW() - 1][c.getCOL()].setDownWall(false);
            }
        }
        if (c.getROW() < (BOARD_SIZE - 1)){ //check down
            if (!BOARD[c.getROW() + 1][c.getCOL()].isVisited()){
                neighbors.add(BOARD[c.getROW() + 1][c.getCOL()]);
                c.setDownWall(false);
                BOARD[c.getROW() + 1][c.getCOL()].setUpWall(false);
            }
        }
        if (c.getCOL() > 0){ //check left
            if (!BOARD[c.getROW()][c.getCOL() - 1].isVisited()){
                neighbors.add(BOARD[c.getROW()][c.getCOL() - 1]);
                c.setLeftWall(false);
                BOARD[c.getROW()][c.getCOL() - 1].setRightWall(false);
            }
        }
        if (c.getCOL() < (BOARD_SIZE - 1)){ //check right
            if (!BOARD[c.getROW()][c.getCOL() + 1].isVisited()){
                neighbors.add(BOARD[c.getROW()][c.getCOL() + 1]);
                c.setRightWall(false);
                BOARD[c.getROW()][c.getCOL() + 1].setLeftWall(false);
            }
        }
        return neighbors;
    }

    public void printBoard(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BOARD[i][j].printCellValue();
            }
            System.out.println();
        }
    }

    public void printBoardID(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BOARD[i][j].printCellID();
            }
            System.out.println();
        }
    }

    public void chooseSolver(String solver){
        if (solver.equals("mouse")){
            System.out.println("Solver type chosen is: " + solver);
            mouse();
        }
        if (solver.equals("mouse_thread")){
            System.out.println("Solver type chosen is: " + solver);
        }
        if (solver.equals("wall")){
            System.out.println("Solver type chosen is: " + solver);
        }
        if (solver.equals("wall_thread")){
            System.out.println("Solver type chosen is: " + solver);
        }
    }

    public void mouse(){
        printBoardID();
        Cell c = BOARD[0][0];
        ArrayList<String> openTravelOptions = new ArrayList<>();
        while (c.getCELL_ID() != BOARD[BOARD_SIZE - 1][BOARD_SIZE - 1].getCELL_ID()){
            if (!c.isUpWall()){
                openTravelOptions.add("up");
            }
            if (!c.isRightWall()){
                openTravelOptions.add("right");
            }
            if (!c.isDownWall()){
                openTravelOptions.add("down");
            }
            if (!c.isLeftWall()){
                openTravelOptions.add("left");
            }
            Collections.shuffle(openTravelOptions);
            String directionTraveled = openTravelOptions.get(0);
            openTravelOptions.remove(0);
            switch (directionTraveled) {
                case "up":
                    c = BOARD[c.getROW() - 1][c.getCOL()];
                    System.out.println("MOUSE TRAVELLED UP");
                    System.out.println(c.getCELL_ID());
                    break;
                case "right":
                    c = BOARD[c.getROW()][c.getCOL() + 1];
                    System.out.println("MOUSE TRAVELLED RIGHT");
                    System.out.println(c.getCELL_ID());
                    break;
                case "down":
                    c = BOARD[c.getROW() + 1][c.getCOL()];
                    System.out.println("MOUSE TRAVELLED DOWN");
                    System.out.println(c.getCELL_ID());
                    break;
                case "left":
                    c = BOARD[c.getROW()][c.getCOL() - 1];
                    System.out.println("MOUSE TRAVELLED LEFT");
                    System.out.println(c.getCELL_ID());
                    break;
            }
            openTravelOptions.clear();
        }
        System.out.println("MOUSE MADE IT THROUGH THE MAZE!!!!");
        System.out.println(c.getCELL_ID());
    }
}