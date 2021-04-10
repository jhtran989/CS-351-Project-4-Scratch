import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Board {
    private final int BOARD_SIZE;
    private final Cell[][] BOARD;
    Stack<Cell> pathStack = new Stack<>();

    /**
     * FIXME: board size also needs to account for the EDGES (walls in general)
     *
     * if no outline of walls is used, then
     * cellBoardDimension = ceiling(BOARD_SIZE / 2)
     * if there is an outline
     * cellBoardDimension = floor(BOARD_SIZE / 2)
     *
     * @param boardSize
     */
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

    public void kruskal(){
        ArrayList<Edge> edges= getAllEdges();
        Collections.shuffle(edges);
        kruskalHelper(edges);
    }

    public void prim(){
        //ArrayList<Edge> edges = getAllEdges();
        Cell c = BOARD[0][0];
        c.visit();
        ArrayList<Cell> neighbors = getNeighbors(c);
        while(!neighbors.isEmpty()){
            printBoard();
            System.out.println();
            Collections.shuffle(neighbors);
            c = neighbors.get(0);
            c.visit();
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
            neighbors.addAll(temp);
        }
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

    public void kruskalHelper(ArrayList<Edge> edges){
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
                edges.remove(0);
                System.out.println("edge removed");
                printBoard();
                System.out.println();
            }
            else{
                System.out.println("EDGE KEPT, NOT REMOVED");
                edges.remove(0);
            }

        }
    }

    public void printBoard(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BOARD[i][j].printCellValue();
            }
            System.out.println();
        }

        System.out.println();
    }

    public void printBoardID(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BOARD[i][j].printCellID();
            }
            System.out.println();
        }
    }

    public void printBoardAddresses() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(BOARD[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void depthFirstSearch(){
        // FIXME: not random yet...
        // added dummy cell
        DummyCell dummyCell = new DummyCell();
        dummyCell.addToDummy(BOARD[0][0]);
        Cell c = dummyCell.getFromDummy();
        c.setPreviousCell(c); // important for backtracking

        System.out.println("Original " + BOARD[0][0]);
        System.out.println("New " + dummyCell.getFromDummy());
        // FIXME: helps with the GUI...update edges
//        ArrayList<Edge> edges = getAllEdges();

        pathStack.push(c);
        c.visit();
        ArrayList<Cell> neighbors;
        neighbors = getNeighborsBreakWalls(c);
        Collections.shuffle(neighbors);
        for (Cell neighbor : neighbors) {
            neighbor.setPreviousCell(dummyCell.getFromDummy());
            pathStack.push(neighbor);
            //neighbor.visit();
            neighbor.onlyVisit();
        }
        printBoard();
        neighbors.clear();
        while(!pathStack.empty()){
            dummyCell.addToDummy(pathStack.pop());
            c = dummyCell.getFromDummy();
            //c.setPreviousCell(dummyCell.getFromDummy());
            if (c.isVisited()) {
                c.updateCellPath();
            }
            printBoard();
            neighbors = getNeighborsBreakWalls(c);
            Collections.shuffle(neighbors);
            for (Cell neighbor : neighbors) {
                neighbor.setPreviousCell(dummyCell.getFromDummy());
                pathStack.push(neighbor);
                //neighbor.visit();
                neighbor.onlyVisit();
            }
            if (!pathStack.isEmpty()) {
                Cell nextCell;
                nextCell = pathStack.pop();
                dummyCell.addToDummy(nextCell);
                pathStack.push(nextCell);

                if (neighbors.isEmpty()) {
                    c.updateCellPathBackTrack();
                    nextCell = pathStack.pop();
                    System.out.println("Next cell " + nextCell);
                    Cell returnCell = nextCell.getPreviousCell();
                    Cell previousCell = c.getPreviousCell();
                    // FIXME
                    printBoardAddresses();
                    System.out.println("Current cell " + c);
                    System.out.println("Return cell " + returnCell);
                    // FIXME
                    System.out.println("Previous cell " + previousCell);
                    while (previousCell != returnCell) {
                        previousCell.updateCellPathBackTrack();
                        previousCell = previousCell.getPreviousCell();
                        // FIXME
                        System.out.println("Previous cell " + previousCell);
                    }
                    pathStack.push(nextCell);
                    // FIXME: also update all cells along path to last cell with
                    //  more neighbors to check...maybe add previous cell field
                    //  to each cell so we "connect" each cell with the cell that
                    //  connects to it
                    printBoard();
                }
            } else {
                System.out.println("Last backtrack...");
                System.out.println("Current " + c);
                c.updateCellPathBackTrack();
                printBoard();
            }
//            printBoard();
            neighbors.clear();
        }
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
}
