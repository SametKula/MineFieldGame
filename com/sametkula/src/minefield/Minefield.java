package com.sametkula.src.minefield;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Minefield {
    private int coll;
    private int row;
    private int[][] board;
    private int[][] fakeBoard;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public Minefield(int coll, int row) {
        this.coll = coll;
        this.row = row;
        this.board = new int[row][coll];
        this.fakeBoard = new int[row][coll];
    }

    private void genereteMine() {
        Random random = new Random();
        int randomColl = random.nextInt(coll), randomRow = random.nextInt(row), mineCount = 0;

        while (mineCount < coll * row / 4){
            if (board[randomRow][randomColl] == 0 && board[randomRow][randomColl] != -1){
                board[randomRow][randomColl] = -1;
                mineCount++;
            }
            randomColl = random.nextInt(coll);
            randomRow = random.nextInt(row);
        }

    }
    private void setBoardZero() {
        board = setBoardZero(board);
        fakeBoard = setBoardZero(fakeBoard);
    }
    private int[][] setBoardZero(int[][] board) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = 0;
        return board;
    }
    public boolean checkHolder(int row, int coll){
        return board[row][coll] == -1;
    }
    private int getHoldersNearMines(int row, int coll) {
        int result = 0;

        for (int i = row - 1; i <= row + 1; i++)
            for (int j = coll - 1; j <= coll + 1; j++){
                if (i == row && j == coll)
                    continue;
                if (hasElementRange(i, j))
                    result += board[i][j];
            }
        System.out.println(result);
        return result;
    }
    public boolean selectHolder(int row, int coll){
        if (!checkHolder(row, coll)){
            fakeBoard[row][coll] = getHoldersNearMines(row ,coll) * -1;
            return true;
        }
        return false;
    }
    private void printBoard(){
        System.out.print("       ");
        for (int i = 1; i <= coll; i++)
            System.out.print("  " + i );
        System.out.println();

        for (int i = 0; i < row; i++) {
            System.out.print("row:" + (i + 1) + "  ");
            for (int j = 0; j < coll; j++)
            System.out.print(String.format((fakeBoard[i][j] > 0 ? ANSI_GREEN : "") + "|%2d" + ANSI_RESET, fakeBoard[i][j]));
            System.out.println("|");
        }
    }
    public boolean hasElementRange(int row, int coll){
        if (row < 0 || coll < 0 || row >= this.row || coll >= this.coll)
            return false;
        return true;
    }
    public void testAllHolders(){
        for (int i = 0; i < row; i++)
            for (int j = 0; j < coll; j++){
                System.out.println("try times: " + (i + j));
                if (board[i][j] == -1)
                    continue;


                selectHolder(i, j);
                printBoard();
            }
    }

    public static void run() {
        Scanner kb = new Scanner(System.in);

        System.out.print("row: ");
        int rowBoard = Integer.parseInt(kb.nextLine());
        System.out.print("coll: ");
        int collBoard = Integer.parseInt(kb.nextLine());
        Minefield minefield = new Minefield(collBoard, rowBoard);
        boolean gameStatus = true;

        minefield.setBoardZero();
        minefield.genereteMine();
        minefield.printBoard();
        while(true){
            System.out.println("\n\n");
            minefield.printBoard();
            System.out.print("row: ");
            int row = Integer.parseInt(kb.nextLine()) - 1;
            System.out.print("coll: ");
            int coll = Integer.parseInt(kb.nextLine()) - 1;

            gameStatus = minefield.selectHolder(row, coll);

            if (!gameStatus){
                System.out.println("game over");

            }
        }
    }
}
