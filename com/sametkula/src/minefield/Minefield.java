package com.sametkula.src.minefield;


import java.util.*;

public class Minefield {
    ArrayList<ArrayList<Field>> map;
    int boardSize;

    public Minefield(int boardSize){
        this.boardSize = boardSize * boardSize;
    }

    public void run(){
        prepareBoard();

    }

    private void prepareBoard(){
        Random rd = new Random();

        int height = boardSize / boardSize;
        map = new ArrayList<>();

        for (int i = 0; i < height; i++){
            List dummyList = new ArrayList();
            for (int j = 0; j < height; j++){
                Field field = new Field(j,i);
                dummyList.add(j, field);
            }
            map.add(dummyList);
        }

    }

}
