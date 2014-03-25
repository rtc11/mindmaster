package no.group3.mindmaster.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSolutionSequence{

    private static final String TAG = "MindMaster.ColorPegSolutionSequence";
    private ColorPegSequence solution = null;
    private static ColorPegSolutionSequence instance = null;


    /**
     * Creates a random solutionSequence if the caller is the creator of the game, or returns the
     * current solution if not.
     *
     * @param isGameCreator - true if the caller is the creator if the game, false if not.
     */
    private ColorPegSolutionSequence(boolean isGameCreator) {
        // If creator - create new random solution sequence
        if (isGameCreator) {
            Colour values[] = Colour.values();
            ArrayList<ColorPeg> sequence = new ArrayList<ColorPeg>();
            for(int i = 0; i<Globals.SEQUENCELENGTH; i++){
                sequence.add(new ColorPeg( values[(int)(Math.random()*values.length)] ));
            }
            this.solution = new ColorPegSequence(sequence);
            this.solution.setSequence(sequence);
        }
    }

    public ColorPegSequence generateSolution(){
        Colour values[] = Colour.values();
        ArrayList<ColorPeg> sequence = new ArrayList<ColorPeg>();
        for(int i = 0; i<Globals.SEQUENCELENGTH; i++){
            sequence.add(new ColorPeg( values[(int)(Math.random()*values.length)] ));
        }
        this.solution = new ColorPegSequence(sequence);
        this.solution.setSequence(sequence);

        return this.solution;
    }

    public static ColorPegSolutionSequence getInstance(boolean isGameCreator){
        if(instance == null){
            synchronized (ColorPegSolutionSequence.class){
                instance = new ColorPegSolutionSequence(isGameCreator);
            }
        }
        return instance;
    }

    public ColorPegSequence getSolution() {
        return this.solution;
    }

    /**
     * Should be called by the client of the game, when a solution is received by the creator.
     *
     * @param solutionSequence The ColorPegSequence received from the creator.
     */
    public void setSolution(ColorPegSequence solutionSequence) {
        this.solution = solutionSequence;
    }







    /**
     * Puts the occurences of each colour in a hashmap, map.get(Colour) gives then the times that colour has appeared
     * @return a hashmap, indexed by colour, containing the occurences of each colour.
     *
     */
    private Colour[] getColoursInSolution(){

        Colour[] lo = new Colour[solution.getSequence().size()];

        for(int i = 0; i < solution.getSequence().size(); i++) {
            Colour cp = solution.getSequence().get(i).getColour();
            lo[i] = cp;

        }

        return lo;
    }

    public ArrayList<KeyPeg> getKeyPegs(ColorPegSequence guess){
        Colour[] availableColors = getColoursInSolution();
        ArrayList<KeyPeg> result = new ArrayList<KeyPeg>();
        Colour[] tempGuess = new Colour[Globals.SEQUENCELENGTH];
        for (int h = 0; h < Globals.SEQUENCELENGTH; h ++) {
            tempGuess[h] = guess.getSequence().get(h).getColour();
        }

        int blackPegs = 0, whitePegs = 0;

        for (int j = 0; j < Globals.SEQUENCELENGTH; j++) {
            ColorPeg cp = guess.getSequence().get(j);

            if(cp.getColour().toInt() == (solution.getSequence().get(j).getColour().toInt())){
                blackPegs++;
                availableColors[j]= null;
                tempGuess[j] = null;
                result.add(KeyPeg.BLACK);
            }
        }

        for (int i = 0; i < Globals.SEQUENCELENGTH; i++) {
            if (tempGuess[i] != null) {
                for(int k = 0; k < availableColors.length; k++){
                    if(availableColors[k] != null && availableColors[k].toInt() == tempGuess[i].toInt()){
                        whitePegs++;
                        availableColors[k] = null;
                        result.add(KeyPeg.WHITE);
                    }
                }
            }
        }

        int nr = 4;
        nr -= whitePegs;
        nr -= blackPegs;
        for(int i = 0; i<nr; i++){
            result.add(KeyPeg.TRANSPARENT);
        }

        return result;
    }

}
