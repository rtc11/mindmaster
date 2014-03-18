package no.group3.mindmaster.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSolutionSequence{

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
            for(int i = 0; i<4; i++){
                sequence.add(new ColorPeg( values[(int)(Math.random()*values.length)] ));
            }
            this.solution = new ColorPegSequence(sequence);
            this.solution.setSequence(sequence);
        }
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
    private HashMap<Colour, Integer> getColoursInSolution(){
        HashMap<Colour, Integer> colours = new HashMap<Colour, Integer>();
        for (ColorPeg peg : this.solution.getSequence()) {
            colours.put(peg.getColour(), colours.get(peg.getColour()) + 1);
        }
        return colours;
    }

    /**
     *
     * @param guess - the guessed ColorPegSequence
     * @return ArrayList<KeyPeg> containing the answer
     */
    public ArrayList<KeyPeg> getKeyPegs(ColorPegSequence guess){

        HashMap<Colour, Integer> colours = getColoursInSolution();
        ArrayList<KeyPeg> answer = new ArrayList<KeyPeg>();

        for (int i = 0; i < Globals.SEQUENCELENGTH; i++) {
            ColorPeg peg = guess.getSequence().get(i);
            //if the guess is at the correct spot with correct colour
            if (this.solution.getSequence().get(i).getColour().equals(peg.getColour())){
                answer.add(KeyPeg.BLACK);
                colours.put(peg.getColour(), colours.get(peg.getColour()) - 1);
            }
        }
        //fills in the values from the hashmap
        for (Colour colour : colours.keySet()) {
            for (int i = 0; i < colours.get(colour); i++) {
                answer.add(KeyPeg.WHITE);
            }
        }
        //if answer is 4, task completed
        if(answer.size() == Globals.SEQUENCELENGTH){
            return answer;
        }
        //if answer is less than 4, fill in with blank keypegs
        else if (answer.size() < Globals.SEQUENCELENGTH){
            for (int i = 0; i < Globals.SEQUENCELENGTH -answer.size(); i++) {
                answer.add(KeyPeg.TRANSPARENT);
            }
            return answer;
        }
        //error since the answer list is too large. I.e. more than 4
        else{
            System.err.println("answer too big");
            return null;
        }


    }
}
