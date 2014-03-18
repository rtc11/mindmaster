package no.group3.mindmaster.Model;


import android.graphics.Color;

/**
 * Created by Wschive on 06/03/14.
 * Each enum contains an android.graphics.Color int. Red is therefore RED(Color.RED).
 */


public enum Colour {
    RED (1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), ORANGE(7);

    private final int color;

    Colour(int color){
        this.color = color;
    }

    public int toInt(){
        return this.color;
    }
}
