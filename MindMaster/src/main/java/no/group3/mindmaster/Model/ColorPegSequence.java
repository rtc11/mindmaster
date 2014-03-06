package no.group3.mindmaster.Model;

import java.util.ArrayList;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSequence {
    ArrayList<ColorPeg> sequence;

    public ColorPegSequence(ArrayList<ColorPeg> sequence) {
        this.sequence = sequence;
    }

    public ArrayList<ColorPeg> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<ColorPeg> sequence) {
        this.sequence = sequence;
    }
}
