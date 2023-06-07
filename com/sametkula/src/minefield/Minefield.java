package com.sametkula.src.minefield;

import java.util.Random;

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
            if (board[randomRow][randomColl] == 0){
                board[randomRow][randomColl] = 1;
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
        if (board[row][coll] == 1)
            return true;

        return false;
    }
    public int getHoldersNearMines(int row, int coll){
        int leftUp = board[row - 1][coll - 1], up = board[row][coll - 1], rightUp = board[row + 1][coll - 1],
                leftDown = board[row - 1][coll + 1], rightDown = board[row + 1][coll + 1], down = board[row][coll + 1],
                left = board[row - 1][coll], right = board[row + 1][coll];

        return rightDown;
    }
    public void selectHolder(int row, int coll){
        if (checkHolder(row, coll)){

        }
    }
}
