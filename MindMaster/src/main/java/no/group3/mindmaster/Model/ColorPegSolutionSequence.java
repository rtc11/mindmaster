package no.group3.mindmaster.Model;

import java.util.ArrayList;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSolutionSequence {

    private ColorPegSequence solution;
    /*
     * Creates a random solutionsequence based on the colours in Colour.java
     */
    public ColorPegSolutionSequence() {
        Colour values[] = Colour.values();
        ArrayList<ColorPeg> sequence = new ArrayList<ColorPeg>();
        for (Colour c : values ) {
        //get a random element from the colour enum and adds it to the sequence
            sequence.add(new ColorPeg( values[(int)(Math.random()*values.length)] ));//extra space to make it easier for the eyes
        }
        this.solution = new ColorPegSequence(sequence);
    }

    public ColorPegSequence getSolution() {
        return solution;
    }

    public ArrayList<KeyPeg> getKeyPegs(ColorPegSequence sequence){


        for (ColorPeg peg : sequence.getSequence()) {

        }
        return null;
    }
}
