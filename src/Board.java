import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Board {

    private int boardSize;
    private Cell[][] board;
    Stack<Cell> pathStack = new Stack<Cell>();

    public Board(int boardSize) {
        board = new Cell[boardSize][boardSize];
        this.boardSize = boardSize;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Cell(0, i, j);
            }
        }
    }

    public void printBoard(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j].printCellValue();
            }
            System.out.println();
        }
    }

    public void pickRandomStartCell(){
        Random rand = new Random();
        int row, col;
        row = rand.nextInt(boardSize);
        col = rand.nextInt(boardSize);
        System.out.println("row =  " + row);
        System.out.println("column = " + col);
        board[row][col].visit();
        pathStack.push(board[row][col]);
        //depthFirstSearch();
    }

    public void depthFirstSearch(){
        Cell c = board[0][0];
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
        if (c.getRow() > 0){ //check left
            if (!board[c.getRow() - 1][c.getCol()].isVisited()){
                neighbors.add(board[c.getRow() - 1][c.getCol()]);
                c.setLeftWall(false);
                board[c.getRow() - 1][c.getCol()].setRightWall(false);
                System.out.println("CHECK LEFT WORKED");
            }
        }
        if (c.getRow() < (boardSize - 1)){ //check right
            if (!board[c.getRow() + 1][c.getCol()].isVisited()){
                neighbors.add(board[c.getRow() + 1][c.getCol()]);
                c.setRightWall(false);
                board[c.getRow() + 1][c.getCol()].setLeftWall(false);
                System.out.println("CHECK RIGHT WORKED");
            }
        }
        if (c.getCol() > 0){ //check up
            if (!board[c.getRow()][c.getCol() - 1].isVisited()){
                neighbors.add(board[c.getRow()][c.getCol() - 1]);
                c.setUpWall(false);
                board[c.getRow()][c.getCol() - 1].setDownWall(false);
                System.out.println("CHECK UP WORKED");
            }
        }
        if (c.getCol() < boardSize){ //check down
            if (!board[c.getRow()][c.getCol() + 1].isVisited()){
                neighbors.add(board[c.getRow()][c.getCol() + 1]);
                c.setDownWall(false);
                board[c.getRow()][c.getCol() + 1].setUpWall(false);
                System.out.println("CHECK DOWN WORKED");
            }
        }
        return neighbors;
    }
}
