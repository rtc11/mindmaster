package no.group3.mindmaster.Model;


import android.graphics.Color;

/**
 * Created by Wschive on 06/03/14.
 * Each enum contains an android.graphics.Color int. Red is therefore RED(Color.RED).
 */


public enum Colours {
    RED (Color.RED), GREEN(Color.GREEN), YELLOW(Color.YELLOW), BLUE(Color.BLUE), MAGENTA(Color.MAGENTA), CYAN(Color.CYAN);

    private final int color;
    Colours(int color){
        this.color = color;
    }

}
