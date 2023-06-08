package com.sametkula.src.minefield;

import java.util.Random;
import java.util.Scanner;

public class Minefield {
    private int coll;
    private int row;
    private int[][] board;

    public Minefield(int coll, int row) {
        this.coll = coll;
        this.row = row;
        this.board = new int[row][coll];
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
        for (int i = 0; i < row; i++)
            for (int j = 0; j < coll; j++)
                board[i][j] = 0;
    }

    public boolean checkHolder(int row, int coll){
        if (board[row][coll] == -1)
            return true;

        return false;
    }
    public int getHoldersNearMines(int row, int coll){
        int leftUp = board[row - 1][coll - 1], up = board[row][coll - 1], rightUp = board[row + 1][coll - 1],
                leftDown = board[row - 1][coll + 1], rightDown = board[row + 1][coll + 1], down = board[row][coll + 1],
                left = board[row - 1][coll], right = board[row + 1][coll];

        if (row == 0 && coll == 0)
            return right + rightDown + down;
        if (row == 0)
            return up + right + rightUp + rightDown + down;
        if (coll == 0)
            return left + leftDown + down + right + rightDown;

        if (this.row == row && this.coll == coll)
            return left + leftUp + up;
        if (row == this.row)
            return left + leftUp + leftDown + up + down;
        if (coll == this.coll)
            return left + leftUp + up + right + rightUp;

        return left + leftUp + leftDown + right + rightDown + rightUp + up + down;
    }
    public boolean selectHolder(int row, int coll){
        if (!checkHolder(row, coll)){
            board[row][coll] = getHoldersNearMines(row ,coll) * -1;
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
            System.out.print(String.format("|%2d", board[i][j]));
            System.out.println("|");
        }

    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        Minefield minefield = new Minefield(6, 6);
        boolean game = true;

        minefield.setBoardZero();
        minefield.genereteMine();
        minefield.printBoard();
        while(game){
            System.out.println("\n\n");
            minefield.printBoard();
            System.out.print("row: ");
            int row = Integer.parseInt(kb.nextLine()) - 1;
            System.out.print("coll: ");
            int coll = Integer.parseInt(kb.nextLine()) - 1;

            game = minefield.selectHolder(row, coll);
        }
    }
}
