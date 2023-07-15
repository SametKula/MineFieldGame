package com.sametkula.src.minefield;

public class Field {
    int col, row,count;
    boolean isMine, flag, isOpen;

    public Field(int row, int col){
        this.col = col;
        this.row = row;
    }
}
