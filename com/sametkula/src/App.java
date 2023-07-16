package com.sametkula.src;

import com.sametkula.src.minefield.Minefield;

public class App {
    public static void main(String[] args) {
        Minefield minefield = new Minefield(9);

        minefield.run();
    }
}
