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
}
