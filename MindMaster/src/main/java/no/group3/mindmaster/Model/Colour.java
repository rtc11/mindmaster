package no.group3.mindmaster.Model;


import android.graphics.Color;

/**
 * Created by Wschive on 06/03/14.
 * Each enum contains an android.graphics.Color int. Red is therefore RED(Color.RED).
 */


public enum Colour {
    RED (Color.RED), GREEN(Color.GREEN), YELLOW(Color.YELLOW), BLUE(Color.BLUE), MAGENTA(Color.MAGENTA), CYAN(Color.CYAN), ORANGE(Color.rgb(255, 165, 0));

    private final int color;

    Colour(int color){
        this.color = color;
    }

}
