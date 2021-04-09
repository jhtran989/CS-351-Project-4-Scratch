import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Board {

    private final int BOARD_SIZE;
    private final Cell[][] BOARD;
    Stack<Cell> pathStack = new Stack<Cell>();

    public Board(int boardSize) {
        BOARD = new Cell[boardSize][boardSize];
        this.BOARD_SIZE = boardSize;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                BOARD[i][j] = new Cell(0, i, j);
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
    }

    public void depthFirstSearch(){
        Cell c = BOARD[0][0];
        pathStack.push(c);
        c.visit();
        ArrayList<Cell> neighbors;
        neighbors = getNeighbors(c);
        Collections.shuffle(neighbors);
        for (Cell neighbor : neighbors) {
            pathStack.push(neighbor);
            neighbor.visit();
        }
        printBoard();
        neighbors.clear();
        while(!pathStack.empty()){
            c = pathStack.pop();
            neighbors = getNeighbors(c);
            Collections.shuffle(neighbors);
            for (Cell neighbor : neighbors) {
                pathStack.push(neighbor);
                neighbor.visit();
            }
            printBoard();
            neighbors.clear();
        }
    }

    public ArrayList<Cell> getNeighbors(Cell c){
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (c.getROW() > 0){ //check left
            if (!BOARD[c.getROW() - 1][c.getCOL()].isVisited()){
                neighbors.add(BOARD[c.getROW() - 1][c.getCOL()]);
                c.setLeftWall(false);
                BOARD[c.getROW() - 1][c.getCOL()].setRightWall(false);
            }
        }
        if (c.getROW() < (BOARD_SIZE - 1)){ //check right
            if (!BOARD[c.getROW() + 1][c.getCOL()].isVisited()){
                neighbors.add(BOARD[c.getROW() + 1][c.getCOL()]);
                c.setRightWall(false);
                BOARD[c.getROW() + 1][c.getCOL()].setLeftWall(false);
            }
        }
        if (c.getCOL() > 0){ //check up
            if (!BOARD[c.getROW()][c.getCOL() - 1].isVisited()){
                neighbors.add(BOARD[c.getROW()][c.getCOL() - 1]);
                c.setUpWall(false);
                BOARD[c.getROW()][c.getCOL() - 1].setDownWall(false);
            }
        }
        if (c.getCOL() < (BOARD_SIZE - 1)){ //check down
            if (!BOARD[c.getROW()][c.getCOL() + 1].isVisited()){
                neighbors.add(BOARD[c.getROW()][c.getCOL() + 1]);
                c.setDownWall(false);
                BOARD[c.getROW()][c.getCOL() + 1].setUpWall(false);
            }
        }
        return neighbors;
    }
}
