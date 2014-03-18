package no.group3.mindmaster.Model;

import java.util.ArrayList;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSequence {
    ArrayList<ColorPeg> sequence;

    /**
     * @param sequence - Arraylist with a sequence of colorpegs. Must be EXACTLY 4 colorpegs
     */
    public ColorPegSequence(ArrayList<ColorPeg> sequence) {
        System.out.println(sequence.size()+"heihei");
        if(sequence.size() == Globals.SEQUENCELENGTH){
            this.sequence = sequence;
        }
        else{
            //errorhandling
            System.out.println("ColorPegSequence not created. There were not exactly 4 pegs in the list");
            System.err.println("ColorPegSequence not created. There were not exactly 4 pegs in the list");
        }

    }

    public ArrayList<ColorPeg> getSequence() {
        return sequence;
    }

    /**
     * @param sequence - Arraylist with a sequence of colorpegs. Must be EXACTLY 4 colorpegs
     */
    public void setSequence(ArrayList<ColorPeg> sequence) {

        if(sequence.size() == Globals.SEQUENCELENGTH){
            this.sequence = sequence;
        }
        else{
            //errorhandling
            System.out.println("ColorPegSequence not created. There were not exactly 4 pegs in the list");
            System.err.println("ColorPegSequence not created. There were not exactly 4 pegs in the list");
        }

    }
}
