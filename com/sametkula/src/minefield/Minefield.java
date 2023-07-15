package com.sametkula.src.minefield;


import org.junit.Test;

import java.util.*;

public class Minefield {
    Field[][] fields;
    int boardSpace, boardSize, mineCount;

    public Minefield(int boardSize) {
        this.boardSize = boardSize;
        this.boardSpace = boardSize * boardSize;
        this.fields = new Field[boardSize][boardSize];
    }

    public void run() {
        prepareBoard();
        addMines();
        updateCounts();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Info : choose a row and collumb from on board if you want to put a flag in the you enter -1 on the row phase");

        while (true) {
            printBoard();
            System.out.print("row: ");
            int row = Integer.parseInt(scanner.nextLine());

            if (row == -1) {
                System.out.println("Flag put mode; ");
                System.out.print("row: ");
                row = Integer.parseInt(scanner.nextLine());

                System.out.print("collumb: ");
                int col = Integer.parseInt(scanner.nextLine());
                chooseOne(row, col, "flag");
                continue;
            }

            System.out.print("collumb: ");
            int col = Integer.parseInt(scanner.nextLine());

            if (chooseOne(row, col, "choose")) {
                System.out.println("you lose");
                break;
            }

            if (isWin()) {
                System.out.println("you won");
                break;
            }

        }

    }

    private void prepareBoard() {
        Random rd = new Random();

        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                fields[i][j] = new Field(i, j);

    }

    private void openAllZeros(int row, int col) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || fields[row][col].isOpen || fields[row][col].count != 0) {
            if (row >= 0 && row < boardSize && col >= 0 && col < boardSize )
                fields[row][col].isOpen = true;
            return;
        }

        fields[row][col].isOpen = true;
        openAllZeros(row + 1, col);
        openAllZeros(row - 1, col);
        openAllZeros(row, col + 1);
        openAllZeros(row, col - 1);
    }

    public boolean isWin() {
        int result = 0;

        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (fields[i][j].isOpen)
                    result++;

        return result == boardSpace - mineCount;
    }

    private void addMines() {
        Random rd = new Random();

        int count = 0;

        while (count < boardSpace / 7) {
            int r = rd.nextInt(boardSize);
            int c = rd.nextInt(boardSize);

            if (!fields[r][c].isMine) {
                fields[r][c].isMine = true;
                count++;
            }
        }
        mineCount = count;
    }

    private void updateCounts() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (!fields[i][j].isMine)
                    continue;
                updateConts(i, j);
            }
        }
    }

    private void updateConts(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col)
                    continue;
                if (i >= 0 && i < boardSize && j < boardSize && j >= 0)
                    fields[i][j].count += 1;
            }
    }

    public boolean chooseOne(int row, int col, String action) {
        switch (action) {
            case "flag":
                fields[row][col].flag = true;
                break;
            case "choose":
                if (fields[row][col].count == 0)
                    openAllZeros(row, col);
                fields[row][col].isOpen = true;
                break;
        }
        return fields[row][col].isMine;
    }

    public void printBoard() {
        System.out.print("        |");
        for (int i = 0; i < boardSize; i++) {
            System.out.printf("%2s|", i);
        }
        System.out.println();
        for (int i = 0; i < this.boardSize; i++) {
            System.out.printf("|%d's row|", i);
            for (int j = 0; j < this.boardSize; j++) {
                if (fields[i][j].flag)
                    System.out.print("[+]");
                else if (fields[i][j].isOpen) {
                    System.out.printf("[%s]", fields[i][j].count);
                }
                else
                    System.out.print("[ ]");
            }
            System.out.println();
        }

    }


}


