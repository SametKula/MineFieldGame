package com.sametkula.src;
import com.sametkula.src.minefield.Minefield;
import org.junit.Test;

public class App {
    public static void main(String[] args) {
        Minefield minefield = new Minefield(7);
        minefield.run();
    }
    @Test
    public void test(){
        Minefield minefield = new Minefield(7);
        minefield.run();

    }
}
