package com.sametkula.src.minefield;


import java.util.Random;
import java.util.Scanner;

public class Minefield {
    Field[][] fields;
    int boardSpace, boardSize, mineCount, firstRow, firstCol;

    public Minefield(int boardSize) {
        this.boardSize = boardSize;
        this.boardSpace = boardSize * boardSize;
        this.fields = new Field[boardSize][boardSize];
        this.mineCount = boardSize;
    }

    public void run() {
        System.out.println("\n" +
                "░██╗░░░░░░░██╗██╗██████╗░██████╗░██████╗░██╗██████╗░░█████╗░██╗░░░██╗\n" +
                "░██║░░██╗░░██║██║██╔══██╗██╔══██╗██╔══██╗██║██╔══██╗██╔══██╗██║░░░██║\n" +
                "░╚██╗████╗██╔╝██║██████╔╝██║░░██║██████╔╝██║██║░░██║██║░░██║╚██╗░██╔╝\n" +
                "░░████╔═████║░██║██╔══██╗██║░░██║██╔══██╗██║██║░░██║██║░░██║░╚████╔╝░\n" +
                "░░╚██╔╝░╚██╔╝░██║██║░░██║██████╔╝██║░░██║██║██████╔╝╚█████╔╝░░╚██╔╝░░\n" +
                "░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝╚═════╝░╚═╝░░╚═╝╚═╝╚═════╝░░╚════╝░░░░╚═╝░░░");

        Scanner scanner = new Scanner(System.in);
        prepareBoard();
        printBoard();

        takeFirstRowAndCol(scanner);

        addMines();
        updateCounts();

        openFirstPhase();

        System.out.println("\n\n\n\n");

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
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                printBoard();
                System.out.println("you won");
                break;
            }
            System.out.println("\n\n\n\n\n\n\n\n");

        }

    }

    private void takeFirstRowAndCol(Scanner scanner) {
        System.out.println("enter a row and column;");

        System.out.print("row: ");
        firstRow = Integer.parseInt(scanner.nextLine());

        System.out.print("col: ");
        firstCol = Integer.parseInt(scanner.nextLine());
        System.out.println();
    }

    private void addMines() {
        Random rd = new Random();

        int count = 0;

        while (count < boardSize) {
            int r = rd.nextInt(boardSize);
            int c = rd.nextInt(boardSize);

            if (!fields[r][c].isMine && !fields[r][c].isOpen && !(r == firstRow && c == firstCol)) {
                fields[r][c].isMine = true;
                count++;
            }
        }
    }

    private boolean chooseOne(int row, int col, String action) {
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

    private void incraseNearOfMines(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col)
                    continue;
                if (i >= 0 && i < boardSize && j < boardSize && j >= 0)
                    fields[i][j].count += 1;
            }
    }

    private boolean isWin() {
        int result = 0;

        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (fields[i][j].isOpen)
                    result++;

        return result == boardSpace - mineCount;
    }

    private void openAllZeros(int row, int col) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || fields[row][col].isOpen || fields[row][col].count != 0) {
            if (row >= 0 && row < boardSize && col >= 0 && col < boardSize)
                fields[row][col].isOpen = true;
            return;
        }

        fields[row][col].isOpen = true;
        openAllZeros(row + 1, col);
        openAllZeros(row - 1, col);
        openAllZeros(row, col + 1);
        openAllZeros(row, col - 1);
    }

    private void openFirstPhase() {
        for (int i = firstRow - 1; i <= firstRow + 1; i++)
            for (int j = firstCol - 1; j <= firstCol + 1; j++)
                if (i >= 0 && i < boardSize && j < boardSize && j >= 0 && !fields[i][j].isMine)
                    chooseOne(i,j,"choose");
    }

    private void prepareBoard() {
        Random rd = new Random();

        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                fields[i][j] = new Field(i, j);

    }

    private void printBoard() {
        System.out.print("        |");
        for (int i = 0; i < boardSize; i++) {
            System.out.printf("%2s|", i);
        }
        System.out.println();
        for (int i = 0; i < this.boardSize; i++) {
            System.out.printf("|%d's row|", i);
            for (int j = 0; j < this.boardSize; j++) {
                if (fields[i][j].flag && !fields[i][j].isOpen)
                    System.out.print("[+]");
                else if (fields[i][j].isOpen) {
                    System.out.printf("[%s]", fields[i][j].count);
                } else
                    System.out.print("[ ]");
            }
            System.out.println();
        }

    }

    private void updateCounts() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (!fields[i][j].isMine)
                    continue;
                incraseNearOfMines(i, j);
            }
        }
    }


}


