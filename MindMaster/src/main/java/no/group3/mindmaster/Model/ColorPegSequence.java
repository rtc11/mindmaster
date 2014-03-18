package no.group3.mindmaster.Model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSequence {
    private final String TAG = "MindMaster.ColorPegSequence";
    private ArrayList<ColorPeg> sequence;

    /**
     * @param sequence - Arraylist with a sequence of colorpegs.
     */
    public ColorPegSequence(ArrayList<ColorPeg> sequence) {
        System.out.println(sequence.size()+"heihei");
        if(sequence.size() == Globals.SEQUENCELENGTH){
            this.sequence = sequence;
        }
        else{
            Log.d(TAG, "ColorPegSequence not created. There were not exactly 4 pegs in the list");
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
            Log.d(TAG, "ColorPegSequence not created. There were not exactly 4 pegs in the list");
        }

    }

    /**
     * Takes a ColorPegSequence and returns a String starting with "peg" followed by the first
     * letter of the four pegs.
     *
     * @return The String with the first letters
     */
    @Override
    public String toString () {
        if(this.getSequence() == null){
            return null;
        }
        ArrayList<ColorPeg> pegSequence = this.getSequence();
        StringBuilder message = new StringBuilder();
        message.append("peg");
        for (int i = 0; i < pegSequence.size(); i++) {
            Colour c = pegSequence.get(i).getColour();
            if (c == Colour.BLUE) {
                message.append("b");
            }
            else if (c == Colour.CYAN) {
                message.append("c");
            }
            else if (c == Colour.GREEN) {
                message.append("g");
            }
            else if (c == Colour.MAGENTA) {
                message.append("m");
            }
            else if (c == Colour.RED) {
                message.append("r");
            }
            else if (c == Colour.YELLOW) {
                message.append("y");
            }
        }
        return message.toString();
    }


}
