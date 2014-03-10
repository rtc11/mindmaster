package no.group3.mindmaster.Model;

import android.graphics.Color;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPeg {


    private Colour colour;

    /**
     * a colorpeg. Contains a single colour
     * @param colour - colour of the peg
     */
    public ColorPeg(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
}
